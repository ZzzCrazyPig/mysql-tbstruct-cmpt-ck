package com.sfexpress.tools.mysql.tbstructck;

public enum DataType {

	TINYINT, SMALLINT, MEDIUMINT, INT, INTEGER, BIGINT,
	DECIMAL, NUMERIC,
	FLOAT, DOUBLE,
	
	CHAR, VARCHAR,
	TEXT, LONGTEXT,
	
	DATE, DATETIME, TIMESTAMP,;
	
	public static DataType getDataType(String dataType) {
		return valueOf(dataType.toUpperCase());
	}
	
}
