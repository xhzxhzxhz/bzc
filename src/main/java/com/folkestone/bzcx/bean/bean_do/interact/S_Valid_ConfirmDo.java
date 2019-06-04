package com.folkestone.bzcx.bean.bean_do.interact;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 有效确认维护实体类
 * @author Administrator
 *
 */
public class S_Valid_ConfirmDo {
	/**
	 * 主键：标准有效性确认ID
	 */
    private String validConfirmId= UUIDUtil.getUUID("Vc");

    /**
     * 外键：题录ID
     */
    private String standardId;

    /**
     * 建议状态。1：作废；2：替代；3：缺失
     */
    private Short suggestStatus;
    
    /**
     * 描述
     */
    private Object comment;

    /**
     * 请求确认人
     */
    private Object applyUser;

    /**
     * 审核人
     */
    private Object auditUser;

    /**
     * 请求时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;

    /**
     * 审核时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditDate;

    /**
     * 状态：1：待审核；2：审核通过；3：驳回；4：忽略
     */
    private Short status;
    
    /**
     * 审核人意见
     * @return
     */
    private Object auditComment;
    
    /**
     * 当前状态，1：现行；2：暂行；3：废止
     */
    private Short nowStatus;
    
    /**
     * 前台显示
     */
    private Short show;
    
    
    private String replaystandName;
    
    private String replaystandNo;
    
    private String standardName;
    
    private String standardNo;
    
    public String getReplaystandName() {
		return replaystandName;
	}

	public void setReplaystandName(String replaystandName) {
		this.replaystandName = replaystandName;
	}

	public String getReplaystandNo() {
		return replaystandNo;
	}

	public void setReplaystandNo(String replaystandNo) {
		this.replaystandNo = replaystandNo;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getStandardNo() {
		return standardNo;
	}

	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}

	public Short getShow() {
		return show;
	}

	public void setShow(Short show) {
		this.show = show;
	}

	public Short getNowStatus() {
		return nowStatus;
	}

	public void setNowStatus(Short nowStatus) {
		this.nowStatus = nowStatus;
	}

    public Object getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(Object auditComment) {
		this.auditComment = auditComment;
	}


    public String getValidConfirmId() {
        return validConfirmId;
    }

    public void setValidConfirmId(String validConfirmId) {
        this.validConfirmId = validConfirmId == null ? null : validConfirmId.trim();
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId == null ? null : standardId.trim();
    }

    public Short getSuggestStatus() {
        return suggestStatus;
    }

    public void setSuggestStatus(Short suggestStatus) {
        this.suggestStatus = suggestStatus;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Object getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Object applyUser) {
        this.applyUser = applyUser;
    }

    public Object getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Object auditUser) {
        this.auditUser = auditUser;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}