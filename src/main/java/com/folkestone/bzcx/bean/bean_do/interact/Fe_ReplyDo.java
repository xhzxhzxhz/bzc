package com.folkestone.bzcx.bean.bean_do.interact;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 意见回复表实体类
 * @author Administrator
 *
 */
public class Fe_ReplyDo {
	/**
	 * 意见反馈回复ID
	 */
    private String replyId= UUIDUtil.getUUID("RI");

    /**意见ID
     * 
     */
    private String feedbackId;

    /**
     * 回复人
     */
    private String replyUser;

    /**
     * 被回复人
     */
    private String receiverUser;

    /**
     * 内容
     */
    private Object content;

    /**
     * 回复时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date replyDate;

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId == null ? null : replyId.trim();
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId == null ? null : feedbackId.trim();
    }

    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser == null ? null : replyUser.trim();
    }

    public String getReceiverUser() {
        return receiverUser;
    }

    public void setReceiverUser(String receiverUser) {
        this.receiverUser = receiverUser == null ? null : receiverUser.trim();
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