package com.sfexpress.tools.mysql.tbstructck.entity;

/**
 * 
 * MysqlConnInfo构建者
 * 
 * @author CrazyPig
 *
 */
public class ConnInfoBuilder {
	
	private String host;
	private String port;
	private String user;
	private String password;
	private String db;
	
	public ConnInfoBuilder host(String host) {
		this.host = host;
		return this;
	}
	
	public ConnInfoBuilder port(String port) {
		this.port = port;
		return this;
	}
	
	public ConnInfoBuilder user(String user) {
		this.user = user;
		return this;
	}
	
	public ConnInfoBuilder password(String password) {
		this.password = password;
		return this;
	}
	
	public ConnInfoBuilder db(String db) {
		this.db = db;
		return this;
	}
	
	public ConnInfo build() {
		return new ConnInfo(host, port, user, password, db);
	}
}
