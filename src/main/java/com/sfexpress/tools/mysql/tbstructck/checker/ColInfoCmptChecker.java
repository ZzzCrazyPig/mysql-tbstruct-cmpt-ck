package com.sfexpress.tools.mysql.tbstructck.checker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.sfexpress.tools.mysql.tbstructck.entity.CmptCheckResult;
import com.sfexpress.tools.mysql.tbstructck.entity.CmptCheckResult.ConflictItem;
import com.sfexpress.tools.mysql.tbstructck.entity.ColumnInfo;
import com.sfexpress.tools.mysql.tbstructck.entity.ConnInfo;
import com.sfexpress.tools.mysql.tbstructck.utils.SetUtil;

/**
 * 列兼容性校验类
 * @author CrazyPig
 * @since 2016-08-29
 *
 */
public class ColInfoCmptChecker {
	
	private static final String FLAG_IP = "${ip}";
	private static final String FLAG_PORT = "${port}";
	private static final String FLAG_DB = "${db}";
	private static String jdbcStrTemplate = "jdbc:mysql://${ip}:${port}/${db}?characterEncoding=utf8";
	
	private static Logger logger = LoggerFactory.getLogger(ColInfoCmptChecker.class);
	
	private TableExistChecker tbExistChecker = new TableExistChecker();
	
	public List<CmptCheckResult> check(ConnInfo pvConnInfo, ConnInfo gvConnInfo, String[] tables) throws Exception {
		
		List<CmptCheckResult> resultList = new ArrayList<CmptCheckResult>();
		Connection pvConn = getConn(pvConnInfo);
		Connection gvConn = getConn(gvConnInfo);

		// 检查表是否存在
		for(String table : tables) {
			if(!tbExistChecker.check(pvConn, pvConnInfo.getDb(), table)) {
				throw new SQLException("表" + table + "在生产库中不存在!");
			}
			if(!tbExistChecker.check(gvConn, gvConnInfo.getDb(), table)) {
				throw new SQLException("表" + table + "在灰度库中不存在!");
			}
		}
		
		for(String table : tables) {
			List<ColumnInfo> pvColumns = getColumnInfo(pvConn, pvConnInfo.getDb(), table);
			List<ColumnInfo> gvColumns = getColumnInfo(gvConn, gvConnInfo.getDb(), table);
			CmptCheckResult result = checkCompatibility(pvColumns, gvColumns);
			result.setTable(table);
			result.setPvConnInfo(pvConnInfo);
			result.setGvConnInfo(gvConnInfo);
			resultList.add(result);
		}
		try {
			DbUtils.close(pvConn);
		} finally {
			DbUtils.close(gvConn);
		}
		
		return resultList;
	}
	
	private CmptCheckResult checkCompatibility(List<ColumnInfo> pvColumns, List<ColumnInfo> gvColumns)
			throws Exception {
		
		CmptCheckResult result = new CmptCheckResult();
		
		Map<String, ColumnInfo> pvColumnMap = getColumnInfoMap(pvColumns);
		Map<String, ColumnInfo> gvColumnMap = getColumnInfoMap(gvColumns);
		
		Set<String> pvColumnSet = pvColumnMap.keySet();
		Set<String> gvColumnSet = gvColumnMap.keySet();
		// 检查新增和缺失
		Set<String> newItems = SetUtil.getDiffSet(gvColumnSet, pvColumnSet);
		if(newItems.size() > 0) { // has new items
			logger.warn("检测到校验表存在新列 : " + Arrays.toString(newItems.toArray()));
			for(String newItem : newItems) {
				result.getNewItems().add(gvColumnMap.get(newItem));
			}
		}
		Set<String> deletedItems = SetUtil.getDiffSet(pvColumnSet, gvColumnSet);
		if(deletedItems.size() > 0) { // has delete items
			logger.warn("检测到校验表缺失列 : " + Arrays.toString(deletedItems.toArray()));
			for(String delItem : deletedItems) {
				result.getDeletedItems().add(pvColumnMap.get(delItem));
			}
		}
		Set<String> commItems = SetUtil.getInterSet(pvColumnSet, gvColumnSet);
		for(String commItem : commItems) {
			ColumnInfo pvColInfo = pvColumnMap.get(commItem);
			ColumnInfo gvColInfo = gvColumnMap.get(commItem);
			if(!checkCompatibility(pvColInfo, gvColInfo)) {
				// 检测到不兼容的列
//				String content = StringFormatter.format("列 {} 数据类型 {}(灰度版本) 无法兼容 {}(生产版本)", 
//						commItem,
//						gvColInfo.getColumnType(),
//						pvColInfo.getColumnType());
				result.getConflictItems().add(new ConflictItem(pvColInfo, gvColInfo));
				result.setCompatible(false);
			}
		}
		
		return result;
	}
	
	private boolean checkCompatibility(ColumnInfo pvColInfo, ColumnInfo gvColInfo) throws Exception {
		String dataType = gvColInfo.getDataType();
		DataTypeCmptChecker dtCmptChecker = getDataTypeChecker(dataType);
		return dtCmptChecker.check(pvColInfo, gvColInfo);
	}
	
	private DataTypeCmptChecker getDataTypeChecker(String dataType) {
		if("varchar".equalsIgnoreCase(dataType)) {
			return new VarcharCmptChecker();
		} else if("char".equalsIgnoreCase(dataType)) {
			return new CharCmptChecker();
		} else if("decimal".equalsIgnoreCase(dataType)) {
			return new DecimalCmptChecker();
		} else if("double".equalsIgnoreCase(dataType)) {
			return new DoubleCmptChecker();
		} else if("float".equalsIgnoreCase(dataType)) {
			return new FloatCmptChecker();
		} else {
			return new DefaultDataTypeCmptChecker(dataType);
		}
	}
	
	private Map<String, ColumnInfo> getColumnInfoMap(List<ColumnInfo> columns) {
		return Maps.uniqueIndex(columns, new Function<ColumnInfo, String>() {

			@Override
			public String apply(ColumnInfo item) {
				return item.getColumnName();
			}
		});
	}
	
	/**
	 * 获取列定义信息,从INFORMATION.COLUMNS表获取
	 * @param conn
	 * @param db
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	private List<ColumnInfo> getColumnInfo(Connection conn, String db, String table) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "SELECT "
				+ "COLUMN_NAME AS columnName, "
				+ "COLUMN_DEFAULT AS columnDefault,"
				+ "IS_NULLABLE AS isNullable,"
				+ "DATA_TYPE AS dataType,"
				+ "CHARACTER_MAXIMUM_LENGTH AS charMaxLen,"
				+ "NUMERIC_PRECISION AS numericPrecision,"
				+ "NUMERIC_SCALE AS numericScale,"
				+ "DATETIME_PRECISION AS datetimePrecision," 
				+ "COLUMN_TYPE AS columnType,"
				+ "COLUMN_KEY AS columnKey,"
				+ "EXTRA AS extra"
				+ "  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
		Object[] params = new Object[]{ db, table };
		List<ColumnInfo> columnInfoList = qr.query(conn, sql, new BeanListHandler<ColumnInfo>(ColumnInfo.class), params);
		conn.close();
		return columnInfoList;
	}
	
	private Connection getConn(ConnInfo connInfo) throws SQLException {
		Connection conn = DriverManager.getConnection(
				getJdbcUrl(connInfo.getHost(), connInfo.getPort(), connInfo.getDb()), 
				connInfo.getUser(), connInfo.getPassword());
		return conn;
	}
	
	private String getJdbcUrl(String host, String port, String db) {
		String jdbcUrl = jdbcStrTemplate.replace(FLAG_IP, host)
											.replace(FLAG_PORT, port)
											.replace(FLAG_DB, db);
		return jdbcUrl;
	}
	
	/**
	 * 
	 * 数据类型兼容性检查
	 * 
	 * @author CrazyPig
	 *
	 */
	public static abstract class DataTypeCmptChecker {
		
		protected String dataType;
		
		public DataTypeCmptChecker(String dataType) {
			this.dataType = dataType;
		}
		
		public boolean check(ColumnInfo pvColumn, ColumnInfo gvColumn) {
			boolean isCmpt = true;
			if(!getDataType().equalsIgnoreCase(pvColumn.getDataType())) {
				isCmpt = false;
			}
			return isCmpt;
		}
		
		public String getDataType() {
			return this.dataType;
		}
		
	}
	
	/**
	 * 
	 * 一般数据类型兼容性检查
	 * 
	 * @author CrazyPig
	 *
	 */
	public static class DefaultDataTypeCmptChecker extends DataTypeCmptChecker {

		public DefaultDataTypeCmptChecker(String dataType) {
			super(dataType);
		}
		
	}
	
	/**
	 * 
	 * char数据类型兼容性检查
	 * 
	 * @author CrazyPig
	 *
	 */
	public static class CharCmptChecker extends DataTypeCmptChecker {
		
		public static final String DATA_TYPE = "char";
		
		public CharCmptChecker() {
			super(DATA_TYPE);
		}
		
		@Override
		public boolean check(ColumnInfo pvColumn, ColumnInfo gvColumn) {
			
			if(super.check(pvColumn, gvColumn)) {
				if(pvColumn.getCharMaxLen() == gvColumn.getCharMaxLen()) {
					return true;
				}
			}
			return false;
		
		}
		
	}
	
	/**
	 * 
	 * varchar数据类型兼容性检查
	 * 
	 * @author CrazyPig
	 *
	 */
	public static class VarcharCmptChecker extends DataTypeCmptChecker {
		
		public static final String DATA_TYPE = "varchar";
		
		public VarcharCmptChecker() {
			super(DATA_TYPE);
		}
		
		@Override
		public boolean check(ColumnInfo pvColumn, ColumnInfo gvColumn) {
			
			if(super.check(pvColumn, gvColumn)) {
				if(pvColumn.getCharMaxLen() <= gvColumn.getCharMaxLen()) {
					return true;
				}
			}
			return false;
		
		}
		
	}
	
	/**
	 * 
	 * decimal数据类型兼容性检查
	 * 
	 * @author CrazyPig
	 *
	 */
	public static class DecimalCmptChecker extends DataTypeCmptChecker {

		public static final String DATA_TYPE = "decimal";
		
		public DecimalCmptChecker() {
			super(DATA_TYPE);
		}
		
		@Override
		public boolean check(ColumnInfo pvColumn, ColumnInfo gvColumn) {
			
			if(super.check(pvColumn, gvColumn)) {
				if(pvColumn.getNumericPrecision() <= gvColumn.getNumericPrecision() && 
						pvColumn.getNumericScale() <= gvColumn.getNumericScale()) {
					return true;
				}
			}
			return false;
		
		}
		
	}
	
	/**
	 * 
	 * double数据类型兼容性检查
	 * 
	 * @author CrazyPig
	 *
	 */
	public static class DoubleCmptChecker extends DataTypeCmptChecker {
		
		public static final String DATA_TYPE = "double";
		
		public DoubleCmptChecker() {
			super(DATA_TYPE);
		}
		
		@Override
		public boolean check(ColumnInfo pvColumn, ColumnInfo gvColumn) {
			
			if(super.check(pvColumn, gvColumn)) {
				if(pvColumn.getNumericPrecision() <= gvColumn.getNumericPrecision() && 
						pvColumn.getNumericScale() <= gvColumn.getNumericScale()) {
					return true;
				}
			}
			return false;
		
		}
		
	}
	
	/**
	 * 
	 * float数据类型兼容性检查
	 * 
	 * @author CrazyPig
	 *
	 */
	public static class FloatCmptChecker extends DataTypeCmptChecker {
		
		public static final String DATA_TYPE = "float";
		
		public FloatCmptChecker() {
			super(DATA_TYPE);
		}
		
		@Override
		public boolean check(ColumnInfo pvColumn, ColumnInfo gvColumn) {
			
			if(super.check(pvColumn, gvColumn)) {
				if(pvColumn.getNumericPrecision() <= gvColumn.getNumericPrecision() && 
						pvColumn.getNumericScale() <= gvColumn.getNumericScale()) {
					return true;
				}
			}
			return false;
		
		}
		
	}

}
