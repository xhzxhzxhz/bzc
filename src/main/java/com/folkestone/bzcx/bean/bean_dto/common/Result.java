package com.folkestone.bzcx.bean.bean_dto.common;
/**
 * 封装和界面交互的数据的类
 * @author smallking
 *
 */
public class Result {

	private Integer code;
	
	private String msg;
	
	private Object data;

	
	public Result(boolean flag){
		if(flag == false)
		{
			this.code = 500;
			this.msg = "操作失败，请稍后再试。";
		}else
		{
			this.code = 200;
			this.msg = "操作成功。";
		}
	}
	public void setResult(boolean flag){
		if(flag == false)
		{
			this.code = 500;
			this.msg = "操作失败，请稍后再试。";
		}else
		{
			this.code = 200;
			this.msg = "操作成功。";
		}
	}
	public Result(){
		
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
