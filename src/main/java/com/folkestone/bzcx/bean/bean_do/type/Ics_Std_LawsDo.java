package com.folkestone.bzcx.bean.bean_do.type;

public class Ics_Std_LawsDo {
	/**
	 * 分类和题录关联表
	 */
	private String id;
	/**
	 * 题录id
	 */
	private String standardId;
	/**
	 * 分类编号
	 */
	private String icsCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStandardId() {
		return standardId;
	}
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	public String getIcsCode() {
		return icsCode;
	}
	public void setIcsCode(String icsCode) {
		this.icsCode = icsCode;
	}

}
