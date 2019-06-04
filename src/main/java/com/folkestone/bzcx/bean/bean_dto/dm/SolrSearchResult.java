package com.folkestone.bzcx.bean.bean_dto.dm;

/**
 * 用来接收solr查询结果的实体类
 * @author smallking
 *
 */
public class SolrSearchResult {
	/**
	 * solr的主键ID，和标准原文的ID一样
	 */
	private String fileId;
	/**
	 * 标准原文的名称
	 */
	private String fileName;
	/**
	 * 原文的类型 原文分为PDF和Txt类型
	 */
	private String fileType;
	/**
	 * 标准原文的内容
	 */
	private String fileContent;
	/**
	 * 标准号 这个标准号没有随着原文作为一个字段
	 * 被初始化到solr里面， 而是从S_STD_FILE表中查询出来的字段
	 */
	private String standardNo;
	/**
	 * 标准名称
	 */
	private String standardName;
	/**
	 * 标准的ID
	 */
	private String standardId;
	
	private String htmlUrl;
	/**
	 * 每一个文章中的关键字出现的次数
	 */
	private String freq;
	
	
	public String getFreq() {
		return freq;
	}

	public void setFreq(String freq) {
		this.freq = freq;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String getStandardNo() {
		return standardNo;
	}

	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null){
			SolrSearchResult solr = (SolrSearchResult)obj;
			if(solr.standardNo.equals(this.standardNo)){
				return true;
			}
		}
		return super.equals(obj);
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	
}
