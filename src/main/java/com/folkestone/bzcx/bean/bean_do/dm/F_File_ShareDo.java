package com.folkestone.bzcx.bean.bean_do.dm;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;

public class F_File_ShareDo {
	/**
	 * 资源共享主键ID
	 */
    private String fileId= UUIDUtil.getUUID("FS");
    
    /**
	 * 文件名称
	 */
    private Object fileName;

    /**
	 * 文件大小，以KB为单位的int数
	 */
    private Integer fileSize;

    /**
	 * 文件类型
	 */
    private String fileType;

    /**
	 * 文件在服务器的存储路径
	 */
    private Object filePath;

    /**
	 * 上传人对自己文件的描述
	 */
    private Object comment;

    /**
	 * 上传人
	 */
    private String uploadUser;

    /**
	 * 审核人
	 */
    private String auditUser;

    /**上传时间
	 * 
	 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;

    /**
	 * 审核时间
	 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditDate;

    /**
	 * 管理员对文件的备注
	 */
    private Object note;

    /**
	 * 状态    1：待审核；2：正常；3：驳回；4：下架；
	 */
    private Short status;

    /**
	 * 最后操作时间
	 */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
     * 比较数字的大小，用于用户排序
     */
    private Integer sortNum;
    
    public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public Object getFileName() {
        return fileName;
    }

    public void setFileName(Object fileName) {
        this.fileName = fileName;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public Object getFilePath() {
        return filePath;
    }

    public void setFilePath(Object filePath) {
        this.filePath = filePath;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getUploadUser() {
        return uploadUser;
    }

    public void setUploadUser(String uploadUser) {
        this.uploadUser = uploadUser == null ? null : uploadUser.trim();
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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