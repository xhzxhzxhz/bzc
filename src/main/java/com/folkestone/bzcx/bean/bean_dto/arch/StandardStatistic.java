package com.folkestone.bzcx.bean.bean_dto.arch;
/**
 * Describe：封装标准体系下 标准统计的实体类
 * 
 * @author smallking
 *
 *  2017年11月17日
 */
public class StandardStatistic {
	/**
	 * 体系名称
	 */
	private String nodeName;
	/**
	 * 体系标准的数量
	 */
	private int archCount = 0;
	/**
	 * 国标数量
	 */
	private int gbCount = 0;
	/**
	 * 企标数量
	 */
	private int qbCount = 0;
	/**
	 * 行标数量
	 */
	private int hbCount = 0;
	/**
	 * 其他标准的数量
	 */
	private int otherCount = 0;
	/**
	 * 标准总数量
	 */
	private int totalCount;
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public int getArchCount() {
		return archCount;
	}
	public void setArchCount(int archCount) {
		this.archCount = archCount;
	}
	public int getGbCount() {
		return gbCount;
	}
	public void setGbCount(int gbCount) {
		this.gbCount = gbCount;
	}
	public int getQbCount() {
		return qbCount;
	}
	public void setQbCount(int qbCount) {
		this.qbCount = qbCount;
	}
	public int getHbCount() {
		return hbCount;
	}
	public void setHbCount(int hbCount) {
		this.hbCount = hbCount;
	}
	public int getOtherCount() {
		return otherCount;
	}
	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
