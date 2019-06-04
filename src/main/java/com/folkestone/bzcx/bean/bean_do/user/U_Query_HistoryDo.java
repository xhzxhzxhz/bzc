package com.folkestone.bzcx.bean.bean_do.user;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * Describe：数据库检索历史表所对应的数据实体
 * 
 * @author smallking
 *
 *  2017年11月13日
 */
public class U_Query_HistoryDo {
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
    /**
     *当这个为1的时候表示的是将这个历史记录删除
     */
    
    private int state;
    /**
     * 这个判断的是我们操作的是标准信息还是法律法规
     */
    private String searchClass;
    
    public String getSearchClass() {
		return searchClass;
	}

	public void setSearchClass(String searchClass) {
		this.searchClass = searchClass;
	}

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