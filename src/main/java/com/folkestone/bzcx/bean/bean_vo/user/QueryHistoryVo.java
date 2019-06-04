package com.folkestone.bzcx.bean.bean_vo.user;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * Describe：检索历史对象 对应的Vo
 * 
 * @author smallking
 *
 *  2017年11月13日
 */
public class QueryHistoryVo {
	/**
	 * 主键ID
	 */
    private String searchId = UUIDUtil.getUUID("Query");
    /**
     * 检索人的ID
     */
    private String searchUser;
    /**
     * 检索的URL
     */
    private String searchUrl;
    /**
     * 检索那个模块
     */
    private Object moudle;
	/**
	 * 检索的关键词
	 */
    private Object keyword;
    /**
     * 检索日期
     */
    private Date queryDate;
    /**
     * 客户端类型
     */
    private Short clientType;
    /**
     * 发送检索请求的IP地址
     */
    private String ip;

    private int state;
    
    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId == null ? null : searchId.trim();
    }

    public String getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(String searchUser) {
        this.searchUser = searchUser == null ? null : searchUser.trim();
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl == null ? null : searchUrl.trim();
    }

    public Object getMoudle() {
        return moudle;
    }

    public void setMoudle(Object moudle) {
        this.moudle = moudle;
    }

    public Object getKeyword() {
        return keyword;
    }

    public void setKeyword(Object keyword) {
        this.keyword = keyword;
    }

    public Date getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(Date queryDate) {
        this.queryDate = queryDate;
    }

    public Short getClientType() {
        return clientType;
    }

    public void setClientType(Short clientType) {
        this.clientType = clientType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}