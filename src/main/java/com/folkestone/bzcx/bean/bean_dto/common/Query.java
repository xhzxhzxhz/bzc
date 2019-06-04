package com.folkestone.bzcx.bean.bean_dto.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Describe：用来将界面的请求参数 封装成map的实体
 * 
 * @author smallking
 *
 *  2017年10月13日
 */
public class Query extends LinkedHashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;
	
	private Integer page = 1;
	
	private Integer rows = 20;
	
	public Query()
	{
		super();
	}
	
	public Query(Map<String, Object> query)
	{
		for(String key : query.keySet()){
			Object value = query.get(key);
			if(value instanceof String){
				if(!(value.toString().trim().equals(""))){
					this.put(key, value);
				}
			}else{
				this.put(key, "");
			}
		}
		// 因为前台和后台用了不同的表格插件 一个是EasyUi的表格插件
		// 一个是GridManager这个表格插件，这两个表格分页时候所给的分页参数不一样
		// 所以在这个地方做一下处理，将参数统一
		Object obj1 = query.get("page");
		Object obj2 = query.get("rows");
		Object obj3 = query.get("cPage");
		Object obj4 = query.get("pSize");
		if(obj3 != null){
			obj1 = obj3;
		}
		if(obj4 != null){
			obj2 = obj4;
		}
		if(obj1 != null){
			this.page = Integer.valueOf((String)obj1);
			if(this.page == 0){
				this.page = 1;
			}
		}
		if(obj2 != null)
		{
			this.rows = Integer.valueOf((String)obj2);
			if(this.rows == 0){
				this.rows = 20;
			}
		}
		if(query.get("relation") == null){
			this.put("relation", "and");
		}
		
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/*public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}*/

	
}
