package com.folkestone.bzcx.bean.bean_do.interact;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 需求征集回复实体类
 * @author Administrator
 *
 */
public class R_MessagesDo {
    /**
     * 需求征集回复ID
     */
	private String reqMsgId=UUIDUtil.getUUID("Zj");

	/**
	 * 需求征集ID
	 */
    private String reqId;

    /**
     * 回复人
     */
    private String replyUser;

    /**
     * 被回复人
     */
    private String receivedUser;

    /**
     * 内容
     */
    private Object content;

    /**
     * 回复时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date replyDate;


	public String getReqMsgId() {
        return reqMsgId;
    }

    public void setReqMsgId(String reqMsgId) {
        this.reqMsgId = reqMsgId == null ? null : reqMsgId.trim();
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId == null ? null : reqId.trim();
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser == null ? null : replyUser.trim();
    }

    public String getReceivedUser() {
        return receivedUser;
    }

    public void setReceivedUser(String receivedUser) {
        this.receivedUser = receivedUser == null ? null : receivedUser.trim();
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }
}