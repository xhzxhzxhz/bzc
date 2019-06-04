package com.folkestone.bzcx.bean.bean_do.interact;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 意见反馈实体类
 * @author Administrator
 *
 */
public class Fe_FeedbackDo {
    /**
     * 意见id
     */
	private String feedbackId= UUIDUtil.getUUID("FK");

	/**
	 * //反馈人
	 */
    private String userId;

    private String feed;
    
    /**
     * //反馈内容
     */
    private Object content;

    /**
     * //反馈时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date feedbackDate;

    /**
     * //状态
     */
    private Short status;

    
    private String username;
    
    
	public String getFeed() {
		return feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

}