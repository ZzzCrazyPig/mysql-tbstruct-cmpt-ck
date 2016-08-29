package com.sfexpress.tools.mysql.tbstructck.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 兼容性检查结果
 * 
 * @author CrazyPig
 *
 */
public class CmptCheckResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private ConnInfo pvConnInfo;
	private String table;
	private ConnInfo gvConnInfo;
	
	private boolean compatible = true; // 是否兼容
	
	private List<ConflictItem> conflictItems = new ArrayList<ConflictItem>(); // 冲突项
	private List<ColumnInfo> newItems = new ArrayList<ColumnInfo>(); // 新增项
	private List<ColumnInfo> deletedItems = new ArrayList<ColumnInfo>(); // 删除项
	
	public static class ConflictItem {
		
		private ColumnInfo pvColInfo;
		private ColumnInfo gvColInfo;
		
		public ConflictItem() {
			
		}
		
		public ConflictItem(ColumnInfo pvColInfo, ColumnInfo gvColInfo) {
			this.pvColInfo = pvColInfo;
			this.gvColInfo = gvColInfo;
		}
		
		public ColumnInfo getPvColInfo() {
			return pvColInfo;
		}
		public void setPvColInfo(ColumnInfo pvColInfo) {
			this.pvColInfo = pvColInfo;
		}
		public ColumnInfo getGvColInfo() {
			return gvColInfo;
		}
		public void setGvColInfo(ColumnInfo gvColInfo) {
			this.gvColInfo = gvColInfo;
		}
		
	}
	
	public ConnInfo getPvConnInfo() {
		return pvConnInfo;
	}
	public void setPvConnInfo(ConnInfo pvConnInfo) {
		this.pvConnInfo = pvConnInfo;
	}
	public ConnInfo getGvConnInfo() {
		return gvConnInfo;
	}
	public void setGvConnInfo(ConnInfo gvConnInfo) {
		this.gvConnInfo = gvConnInfo;
	}
	public boolean isCompatible() {
		return compatible;
	}
	public void setCompatible(boolean isCompatible) {
		this.compatible = isCompatible;
	}
	public List<ColumnInfo> getNewItems() {
		return newItems;
	}
	public void setNewItems(List<ColumnInfo> newItems) {
		this.newItems = newItems;
	}
	public List<ColumnInfo> getDeletedItems() {
		return deletedItems;
	}
	public void setDeletedItems(List<ColumnInfo> deletedItems) {
		this.deletedItems = deletedItems;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public List<ConflictItem> getConflictItems() {
		return conflictItems;
	}
	public void setConflictItems(List<ConflictItem> conflictItems) {
		this.conflictItems = conflictItems;
	}
	
}
