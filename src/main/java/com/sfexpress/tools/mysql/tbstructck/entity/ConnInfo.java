package com.sfexpress.tools.mysql.tbstructck.entity;

/**
 * 
 * 数据库连接信息
 * 
 * @author CrazyPig
 *
 */
public class ConnInfo {
	
	private String host;
	private String port;
	private String user;
	private String password;
	private String db;
	
	// import for serializing
	public ConnInfo() {
		
	}
	
	public ConnInfo(String host, String port, String user, String password, String db) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.db = db;
	}
	
	public String getId() {
		return this.host + ":" + this.port + "/" + this.db;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	
}
