package com.sfexpress.tools.mysql.tbstructck.entity;

/**
 * 
 * mysql列信息, 根据information_schema COLUMNS表得出
 * 
 * @author CrazyPig
 *
 */
public class ColumnInfo {
	
	private String columnName;
	private String columnDefault;
	private String isNullable;
	private String dataType;
	
	private int charMaxLen;
	private int numericPrecision;
	private int numericScale;
	private int datetimePrecision;
	
	private String columnType;
	private String columnKey;
	private String extra;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[")
			.append("columnName = " + this.columnName + ", ")
			.append("columnDefault = " + this.columnDefault + ", ")
			.append("isNullable = " + this.isNullable + ", ")
			.append("dataType = " + this.dataType + ", ")
			.append("charMaxLen = " + this.charMaxLen + ", ")
			.append("numericPrecision = " + this.numericPrecision + ", ")
			.append("numericScale = " + this.numericScale + ", ")
			.append("datetimePrecision = " + this.datetimePrecision + ", ")
			.append("columnType = " + this.columnType + ", ")
			.append("columnKey = " + this.columnKey + ", ")
			.append("extra = " + this.extra + "]");
		return sb.toString(); 
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnDefault() {
		return columnDefault;
	}
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	public String getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public int getCharMaxLen() {
		return charMaxLen;
	}
	public void setCharMaxLen(int charMaxLen) {
		this.charMaxLen = charMaxLen;
	}
	public int getNumericPrecision() {
		return numericPrecision;
	}
	public void setNumericPrecision(int numericPrecision) {
		this.numericPrecision = numericPrecision;
	}
	public int getNumericScale() {
		return numericScale;
	}
	public void setNumericScale(int numericScale) {
		this.numericScale = numericScale;
	}
	public int getDatetimePrecision() {
		return datetimePrecision;
	}
	public void setDatetimePrecision(int datetimePrecision) {
		this.datetimePrecision = datetimePrecision;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
	
}
