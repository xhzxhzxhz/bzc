package com.folkestone.bzcx.bean.bean_dto.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Describe：本类是用来封装返回给EasyUI前台的分页实体
 * 
 * @author smallking
 *
 *  2017年10月30日
 */
public class EasyUISeparatPage {
	
	/**
	 * 结果标识位
	 */
	private String status = "success";
	/**
	 * 后台分页所需参数
	 */
	private Long total;
	/**
	 * 后台分页数据实体
	 */
	private List<?> rows;
	
	/**
	 * 后台统计列
	 */
	private List<?> taotaldata;
	
	/**
	 * 前台分页所需参数
	 */
	@SuppressWarnings("unused")
	private Long totals;
	/**
	 * 前台分页所需数据
	 */
	private List<?> data;
	/**
	 * 其他的一些属性
	 */
	private Map<String, Object> attr = new HashMap<String, Object>();
	
	public EasyUISeparatPage() {
		super();
	}
	public EasyUISeparatPage(Long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public List<?> getRows() {
		if(data == null || data.size() == 0){
			return rows;
		}else{
			return data;
		}
	}
	
	public List<?> getTaotaldata() {
		return taotaldata;
	}
	public void setTaotaldata(List<?> taotaldata) {
		this.taotaldata = taotaldata;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getTotals() {
		return total;
	}
	public void setTotals(Long totals) {
		this.totals = totals;
	}
	public List<?> getData() {
		if(data == null || data.size() == 0){
			return rows;
		}else{
			return data;
		}
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	public Map<String, Object> getAttr() {
		return attr;
	}
	public void setAttr(Map<String, Object> attr) {
		this.attr = attr;
	}
	public Object put(String key, Object value){
		return attr.put(key, value);
	}
	public Object get(String key){
		return attr.get(key);
	}
}
