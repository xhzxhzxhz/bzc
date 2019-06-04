package com.folkestone.bzcx.bean.bean_vo.interact;

import java.util.Date;


import java.util.List;

import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.DateFormatUtil;
/**
 * 意见反馈实体类
 * @author Administrator
 *
 */
public class FeFeedbackVo {
    /**
     * 意见id
     */
	private String feedbackId;

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
    private Date feedbackDate;

    /**
     * //状态
     */
    private int status;

	public String getFeed() {
		return feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}

	public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId == null ? null : feedbackId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getFeedbackDate() {
    	 return DateFormatUtil.date2Str(feedbackDate);
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}