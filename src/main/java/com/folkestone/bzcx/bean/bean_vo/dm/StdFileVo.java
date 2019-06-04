package com.folkestone.bzcx.bean.bean_vo.dm;

import java.util.Date;

import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.DateFormatUtil;
import com.folkestone.bzcx.common.util.StringUtil;

/**
 * Describe：标准原文对应的vo
 * 
 * @author smallking
 *
 *  2017年11月4日
 */
public class StdFileVo {
	/**
	 * 标准原文主键ID
	 */
    private String stdFileId;
    /**
     * 标准题录的ID 外键
     */
    private String standardId;
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
    /**
     *是否关联成功 
     */
    @SuppressWarnings("unused")
	private String associated;
    /**
     * 对应的标准原文的标准号
     */
    private String standardNo;
    /**
     * 预览路径
     */
    @SuppressWarnings("unused")
	private String previewPath;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 标准名称
     */
    private String standardName;
    
    private String pdfFile;
    private Integer docSource;
    
    private String htmlUrl;
    /**
     * 查看次数
     */
    private Integer checkNumber;
    
    public Integer getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(Integer checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public Integer getDocSource() {
		return docSource;
	}

	public void setDocSource(Integer docSource) {
		this.docSource = docSource;
	}

	public String getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}

	public String getStdFileId() {
        return stdFileId;
    }

    public void setStdFileId(String stdFileId) {
        this.stdFileId = stdFileId == null ? null : stdFileId.trim();
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId == null ? null : standardId.trim();
    }

    public Object getStdFileName() {
        return stdFileName;
    }

    public void setStdFileName(Object stdFileName) {
        this.stdFileName = stdFileName;
    }

    public Object getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Object batchNo) {
        this.batchNo = batchNo;
    }

    public String getStdFileSize() {
    	String fileSize = Math.ceil(stdFileSize/1024) + "";
    	return fileSize.substring(0, fileSize.lastIndexOf(".")) + " KB";
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

    public String getLanguage() {
        return ContantFinalUtil.MAPPING.get("stdFileLanguage-" + language);
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

    public String getUploadDate() {
    	return DateFormatUtil.date2Str(uploadDate);
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

	public String getAssociated() {
		if(StringUtil.isEmpty(standardId)){
			return "否";
		}
		return "是";
	}

	public void setAssociated(String associated) {
		this.associated = associated;
	}

	public String getStandardNo() {
		return standardNo;
	}

	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}

	public String getPreviewPath() {
		try {
			if(!StringUtil.isEmpty(pdfFile)) {
				return "admin/pdf/web/viewer.html?a=" + pdfFile;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	@Override
	public String toString() {
		return "StdFileVo [stdFileId=" + stdFileId + ", standardId=" + standardId + ", stdFileName=" + stdFileName
				+ ", batchNo=" + batchNo + ", stdFileSize=" + stdFileSize + ", fileType=" + fileType + ", pageNumber="
				+ pageNumber + ", language=" + language + ", comment=" + comment + ", uploadDate=" + uploadDate
				+ ", uploadUnit=" + uploadUnit + ", storePlace=" + storePlace + ", stdFilePath=" + stdFilePath
				+ ", lastOperDate=" + lastOperDate + ", lastOperType=" + lastOperType + ", lastOperUser=" + lastOperUser
				+ ", associated=" + associated + ", standardNo=" + standardNo + ", previewPath=" + previewPath
				+ ", departmentName=" + departmentName + ", standardName=" + standardName + ", pdfFile=" + pdfFile
				+ ", docSource=" + docSource + "]";
	}
	
}