package com.folkestone.bzcx.bean.bean_do.dm;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;

public class S_Std_FileDo {
	@Override
	public String toString() {
		return "S_Std_FileDo [stdFileId=" + stdFileId + ", standardNo=" + standardNo + ", stdFileName=" + stdFileName
				+ ", fileType=" + fileType + ", pdfFile=" + pdfFile + ", docSource=" + docSource + "]";
	}
	/**
	 * 文件的分类
	 */
	private String icsCode;
	
	/**
	 * 标准原文主键ID
	 */
    private String stdFileId = UUIDUtil.getUUID("S_S_F-");
    /**
     * 所对应的标准号
     */
    private String standardNo;
    /**
     * 标准原文的名称
     */
    private Object stdFileName;
    /**
     * 批号
     */
    private Object batchNo;
    /**
     * 原文大小 KB为单位
     */
    private Integer stdFileSize;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 原文页数
     */
    private Integer pageNumber;
    /**
     * 语言
     */
    private Short language;
    /**
     * 内容
     */
    private Object comment;
    /**
     * 上传日期
     */
    private Date uploadDate;
    /**
     * 上传单位
     */
    private Object uploadUnit;
    /**
     * 存放位置
     */
    private Object storePlace;
    /**
     * 在服务器的存放路径
     */
    private Object stdFilePath;
    /**
     * 最后操作时间
     */
    private Date lastOperDate;
    /**
     * 最后操作类型
     */
    private Short lastOperType;
    /**
     * 最后操作人
     */
    private String lastOperUser;
    
    private String pdfFile;
    
    private Integer docSource;
    /**
     * 查看这篇文章的次数
     */
    private Integer checkNumber;
    
    

	public Integer getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(Integer checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getIcsCode() {
		return icsCode;
	}

	public void setIcsCode(String icsCode) {
		this.icsCode = icsCode;
	}

	public String getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}

	public Integer getDocSource() {
		return docSource;
	}

	public void setDocSource(Integer docSource) {
		this.docSource = docSource;
	}

	public String getStdFileId() {
        return stdFileId;
    }

    public void setStdFileId(String stdFileId) {
        this.stdFileId = stdFileId == null ? null : stdFileId.trim();
    }

    public String getStandardNo() {
		return standardNo;
	}

	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo.replace("／", "/");
	}
	
    public Object getStdFileName() {
        return stdFileName;
    }

    public void setStdFileName(Object stdFileName) {
        this.stdFileName = ((String) stdFileName).replace("／", "/");
    }

    public Object getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Object batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getStdFileSize() {
        return stdFileSize;
    }

    public void setStdFileSize(Integer stdFileSize) {
        this.stdFileSize = stdFileSize;
    }
    
    public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Short getLanguage() {
        return language;
    }

    public void setLanguage(Short language) {
        this.language = language;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Object getUploadUnit() {
        return uploadUnit;
    }

    public void setUploadUnit(Object uploadUnit) {
        this.uploadUnit = uploadUnit;
    }

    public Object getStorePlace() {
        return storePlace;
    }

    public void setStorePlace(Object storePlace) {
        this.storePlace = storePlace;
    }

    public Object getStdFilePath() {
        return stdFilePath;
    }

    public void setStdFilePath(Object stdFilePath) {
        this.stdFilePath = stdFilePath;
    }

    public Date getLastOperDate() {
        return lastOperDate;
    }

    public void setLastOperDate(Date lastOperDate) {
        this.lastOperDate = lastOperDate;
    }

    public Short getLastOperType() {
        return lastOperType;
    }

    public void setLastOperType(Short lastOperType) {
        this.lastOperType = lastOperType;
    }

    public String getLastOperUser() {
        return lastOperUser;
    }

    public void setLastOperUser(String lastOperUser) {
        this.lastOperUser = lastOperUser == null ? null : lastOperUser.trim();
    }

}