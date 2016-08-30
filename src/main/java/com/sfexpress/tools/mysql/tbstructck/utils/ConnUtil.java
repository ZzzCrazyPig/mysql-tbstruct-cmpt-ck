package com.sfexpress.tools.mysql.tbstructck.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sfexpress.tools.mysql.tbstructck.entity.ConnInfo;

public class ConnUtil {
	
	private static final String FLAG_IP = "${ip}";
	private static final String FLAG_PORT = "${port}";
	private static final String FLAG_DB = "${db}";
	private static String jdbcStrTemplate = "jdbc:mysql://${ip}:${port}/${db}?characterEncoding=utf8";
	
	public static Connection getConn(ConnInfo connInfo) throws SQLException {
		Connection conn = DriverManager.getConnection(
				getJdbcUrl(connInfo.getHost(), connInfo.getPort(), connInfo.getDb()), 
				connInfo.getUser(), connInfo.getPassword());
		return conn;
	}
	
	public static String getJdbcUrl(String host, String port, String db) {
		String jdbcUrl = jdbcStrTemplate.replace(FLAG_IP, host)
											.replace(FLAG_PORT, port)
											.replace(FLAG_DB, db);
		return jdbcUrl;
	}

}
