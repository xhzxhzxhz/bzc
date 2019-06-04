package com.folkestone.bzcx.bean.bean_do.interact;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 需求征集实体类
 * @author Administrator
 *  
 */
public class R_Req_CollectDo {
    /**
     * 需求征集ID
     */
	private String reqId=UUIDUtil.getUUID("Zj");

	/**
     * 标题
     */
    private Object reqTitle;

    /**
     * 课题名称
     */
    private Object subjectName;

    /**
     * 重要性
     */
    private Short reqLevel;

    /**
     * 预期完成时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planDate;

    /**
     * 所属标准体系
     */
    private String belongArchitecture;

    /**
     * 提出人
     */
    private String suggestUser;

    /**
     * 需求描述
     */
    private Object content;

    /**
     * 审核人
     */
    private String auditUser;
    
    /**
     * 提出时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date suggestTime;

    /**
     * 审核时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date auditDate;

    /**
     * 状态
     */
    private Short status;
    
    /**
     * 审核意见
     */
    private String auditComment;
    
    /**
     * 前台显示
     */
    private Short show;
    
    public Short getShow() {
		return show;
	}

	public void setShow(Short show) {
		this.show = show;
	}

	public String getAuditComment() {
		return auditComment;
	}

	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}

	public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId == null ? null : reqId.trim();
    }

    public Object getReqTitle() {
        return reqTitle;
    }

    public void setReqTitle(Object reqTitle) {
        this.reqTitle = reqTitle;
    }

    public Object getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(Object subjectName) {
        this.subjectName = subjectName;
    }

    public Short getReqLevel() {
        return reqLevel;
    }

    public void setReqLevel(Short reqLevel) {
        this.reqLevel = reqLevel;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getBelongArchitecture() {
        return belongArchitecture;
    }

    public void setBelongArchitecture(String belongArchitecture) {
        this.belongArchitecture = belongArchitecture == null ? null : belongArchitecture.trim();
    }

    public String getSuggestUser() {
        return suggestUser;
    }

    public void setSuggestUser(String suggestUser) {
        this.suggestUser = suggestUser == null ? null : suggestUser.trim();
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

    public Date getSuggestTime() {
        return suggestTime;
    }

    public void setSuggestTime(Date suggestTime) {
        this.suggestTime = suggestTime;
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

	@Override
	public String toString() {
		return "R_Req_CollectDo [reqId=" + reqId + ", reqTitle=" + reqTitle
				+ ", subjectName=" + subjectName + ", reqLevel=" + reqLevel
				+ ", planDate=" + planDate + ", belongArchitecture="
				+ belongArchitecture + ", suggestUser=" + suggestUser
				+ ", content=" + content + ", auditUser=" + auditUser
				+ ", suggestTime=" + suggestTime + ", auditDate=" + auditDate
				+ ", status=" + status + "]";
	}
    
}