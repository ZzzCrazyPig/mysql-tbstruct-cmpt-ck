package com.sfexpress.tools.mysql.tbstructck.entity;

import java.io.Serializable;

public class Meta implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String pvHost;
	private String pvPort;
	private String pvDb;
	private String pvUser;
	private String pvPwd;
	
	private String gvHost;
	private String gvPort;
	private String gvDb;
	private String gvUser;
	private String gvPwd;
	
	private String tables;

	public String getPvHost() {
		return pvHost;
	}

	public void setPvHost(String pvHost) {
		this.pvHost = pvHost;
	}

	public String getPvPort() {
		return pvPort;
	}

	public void setPvPort(String pvPort) {
		this.pvPort = pvPort;
	}

	public String getPvDb() {
		return pvDb;
	}

	public void setPvDb(String pvDb) {
		this.pvDb = pvDb;
	}

	public String getPvUser() {
		return pvUser;
	}

	public void setPvUser(String pvUser) {
		this.pvUser = pvUser;
	}

	public String getPvPwd() {
		return pvPwd;
	}

	public void setPvPwd(String pvPwd) {
		this.pvPwd = pvPwd;
	}

	public String getGvHost() {
		return gvHost;
	}

	public void setGvHost(String gvHost) {
		this.gvHost = gvHost;
	}

	public String getGvPort() {
		return gvPort;
	}

	public void setGvPort(String gvPort) {
		this.gvPort = gvPort;
	}

	public String getGvDb() {
		return gvDb;
	}

	public void setGvDb(String gvDb) {
		this.gvDb = gvDb;
	}

	public String getGvUser() {
		return gvUser;
	}

	public void setGvUser(String gvUser) {
		this.gvUser = gvUser;
	}

	public String getGvPwd() {
		return gvPwd;
	}

	public void setGvPwd(String gvPwd) {
		this.gvPwd = gvPwd;
	}

	public String getTables() {
		return tables;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}
	
}
