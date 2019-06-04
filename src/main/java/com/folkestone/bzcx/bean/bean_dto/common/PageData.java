package com.folkestone.bzcx.bean.bean_dto.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Describe：本类是用来封装返回给前台的分页结果的实体
 * 包含分页信息和数据集合
 * 
 * @author smallking
 *
 *  2017年10月30日
 */
public class PageData {
	
	private int code = 0;
	
	private String msg = "查询成功";
	
	private boolean isFront = false;
	
	/**
	 * 后台分页所需参数
	 */
	private Long total;
	/**
	 * 前台分页所需数据
	 */
	private List<?> data;
	/**
	 * 其他的一些属性
	 */
	private Map<String, Object> attr = new HashMap<String, Object>();
	
	public PageData() {
		super();
	}
	public PageData(Long total, List<?> data) {
		super();
		this.total = total;
		this.data = data;
	}
	public int getCode() {
		if(data != null && data.size() >= 1){
			if(isFront) {
				return 200;
			}
			return code;
		}else{
			return 200;
		}
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		if(getCode() != 200){
			return "";
		}
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<?> getData() {
		return data;
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
	public void setFront(boolean isFront) {
		this.isFront = isFront;
	}
}
