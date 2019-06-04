package com.folkestone.bzcx.bean.bean_vo.message;

public class message {
	/**
	 * 消息归属
	 */
	String namemessage;
	/**
	 * 消息条数
	 */
	String summessage;
	
	public String getNamemessage() {
		return namemessage;
	}
	public void setNamemessage(String namemessage) {
		this.namemessage = namemessage;
	}
	public String getSummessage() {
		return summessage;
	}
	public void setSummessage(String summessage) {
		this.summessage = summessage;
	}
	@Override
	public String toString() {
		return "message [namemessage=" + namemessage + ", summessage="
				+ summessage + "]";
	}
	
}
