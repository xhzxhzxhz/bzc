package com.folkestone.bzcx.bean.bean_vo.dm;

import java.util.Date;

import com.folkestone.bzcx.common.util.DateFormatUtil;

public class FileShareVo {
	/**
	 * 资源共享主键ID
	 */
    private String fileId;
    
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
    private Date uploadDate;

    /**
	 * 审核时间
	 */
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
    /**
     * 预览路径
     */
    @SuppressWarnings("unused")
	private String previewPath;
    
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

    public String getPreviewPath() {
		try {
			String fileName = (filePath + "").split("\\.")[0].substring(8);
			return "/BZCX/admin/pdf/web/viewer.html?a=" + fileName + ".pdf";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public void setPreviewPath(String previewPath) {
		this.previewPath = previewPath;
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

    public String getUploadDate() {
    	 return DateFormatUtil.date2Str(uploadDate);
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAuditDate() {
    	return DateFormatUtil.date2Str(auditDate);
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

    public String getLastOperDate() {
        return DateFormatUtil.date2Str(lastOperDate);
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