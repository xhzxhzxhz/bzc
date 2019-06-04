package com.folkestone.bzcx.common.listen;

import java.text.DecimalFormat;
/**
 * 封装文件上传进度的实体类
 * @author smallking
 *
 */
public class Progress {
	
	// 已经上传的字节数
	private long pBytesRead;
	// 已经解析的字节数
	private long parseByte;
    // 上传第几个文件
    private long pItems;
    // 解析第几个文件
    private long parseItems;
    // 上传百分比
    @SuppressWarnings("unused")
	private String percent;
    // 解析百分比
    private String parsePercent;
    // 总字节数
    private long pContentLength; 
   
    DecimalFormat format = new DecimalFormat("0.00%");
    
  	public String getPercent() {
  		String percentFormat = format.format((float)pBytesRead/pContentLength);
  		return percentFormat;
  	}
  	 
  	public String getParsePercent() {
  		
  		return parsePercent;
	}
  	public void setParsePercent(String parsePercent) {
		this.parsePercent = parsePercent;
	}
    public long getParseByte() {
		return parseByte;
	}
	public void setParseByte(long parseByte) {
		this.parseByte = parseByte;
	}
	public long getParseItems() {
		return parseItems;
	}
	public void setParseItems(long parseItems) {
		this.parseItems = parseItems;
	}
	
	
	public DecimalFormat getFormat() {
		return format;
	}
	public void setFormat(DecimalFormat format) {
		this.format = format;
	}
  
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public long getpBytesRead() {
		return pBytesRead;
	}
	public long getpContentLength() {
		return pContentLength;
	}
	public long getpItems() {
		return pItems;
	}
	public void setpBytesRead(long pBytesRead) {
		this.pBytesRead = pBytesRead;
	}
	public void setpContentLength(long pContentLength) {
		this.pContentLength = pContentLength;
	}
	public void setpItems(long pItems) {
		this.pItems = pItems;
	}

	@Override
	public String toString() {
		return "Progress [pBytesRead=" + pBytesRead + ", parseByte=" + parseByte + ", pItems=" + pItems
				+ ", parseItems=" + parseItems + ", percent=" + this.getPercent() + ", parsePercent=" + parsePercent
				+ ", pContentLength=" + pContentLength + ", format=" + format + "]";
	}

	
	
	
    
	
}
