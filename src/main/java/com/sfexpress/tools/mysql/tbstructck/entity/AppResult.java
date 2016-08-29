package com.sfexpress.tools.mysql.tbstructck.entity;

import java.io.Serializable;
import java.util.List;

public class AppResult<E> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private boolean success;
	private String errMsg;
	private int errCode;
	private E row;
	private List<E> rows;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public E getRow() {
		return row;
	}
	public void setRow(E row) {
		this.row = row;
	}
	public List<E> getRows() {
		return rows;
	}
	public void setRows(List<E> rows) {
		this.rows = rows;
	}
	
}
