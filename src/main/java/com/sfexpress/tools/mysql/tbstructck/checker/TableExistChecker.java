package com.sfexpress.tools.mysql.tbstructck.checker;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class TableExistChecker {
	
	/**
	 * 检查表是否存在
	 * @param conn
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public boolean check(Connection conn, String schemaName, String tableName) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
		Object[] params = new Object[]{schemaName, tableName};
		String result = qr.query(conn, sql, new BeanHandler<String>(String.class), params);
		return result == null ? false : true;
	}

}
