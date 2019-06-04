package com.folkestone.bzcx.bean.bean_vo.interact;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.DateFormatUtil;
/**
 * 意见回复表实体类
 * @author Administrator
 *
 */
public class FeReplyVo {
	/**
	 * 意见反馈回复ID
	 */
    private String replyId;

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
    
    private FeFeedbackVo fefeedbackvo;

    public FeFeedbackVo getFefeedbackvo() {
		return fefeedbackvo;
	}

	public void setFefeedbackvo(FeFeedbackVo fefeedbackvo) {
		this.fefeedbackvo = fefeedbackvo;
	}

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

    public String getReplyDate() {
        return DateFormatUtil.date2Str(replyDate);
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }
}