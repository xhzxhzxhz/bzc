package com.folkestone.bzcx.bean.bean_vo.interact;

import java.util.Date;

import com.folkestone.bzcx.common.util.DateFormatUtil;
/**
 * 需求征集实体类
 * @author Administrator
 *
 */
public class ReqCollectVo {
    /**
     * 需求征集ID
     */
	private String reqId;

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
    private int reqLevel;

    /**
     * 预期完成时间
     */
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
    private Date suggestTime;

    /**
     * 审核时间
     */
    private Date auditDate;

    /**
     * 状态
     */
    private int status;
    
    /**
     * 审核意见
     */
    private String auditComment;
    
    /**
     * 前台显示
     */
    private int show;
    
    public int getShow() {
		return show;
	}

	public void setShow(int show) {
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

    public int getReqLevel() {
        return reqLevel;
    }

    public void setReqLevel(int reqLevel) {
        this.reqLevel = reqLevel;
    }

    public String getPlanDate() {
    	return DateFormatUtil.date2Str(planDate);
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

    public String getSuggestTime() {
    	return DateFormatUtil.date2Str(suggestTime);
    }

    public void setSuggestTime(Date suggestTime) {
        this.suggestTime = suggestTime;
    }

    public String getAuditDate() {
        return DateFormatUtil.date2Str(auditDate);
    }

    public void setAuditDate(Date auditDate) {
    	this.auditDate = auditDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}