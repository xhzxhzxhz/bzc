package com.folkestone.bzcx.bean.bean_vo.user;

import java.util.Date;

import com.folkestone.bzcx.common.util.DateFormatUtil;
/**
 * Describe：用户操作记录返回给界面的展示类
 * 
 * @author smallking
 *
 *  2017年11月23日
 */
public class OperLogVo {
	/**
	 * 主键ID
	 */
    private String operId;
    /**
     * 操作人的ID(外键 user的ID)
     */
    private String operUser;
    /**
     * 操作日期
     */
    private Date operDate;
    /**
     * 操作类型 如：下载，上传，预览，备份等。自定义一个字符串
     */
    private String operType;
    /**
     * 操作细节 如果是下载，就是下载的原文的名称，搜索就是搜索的关键词等
     */
    private Object operDetail;
    /**
     * 不是必填的。操作的实体对应的ID，如果是下载，就是下载的文件的ID。其他类似
     */
    private String mappingId;
    /**
     * 客户端类型。1：电脑端。 2：移动端。 不填写默认是
     */
    private Short clientType;
    /**
     * 请求的url
     */
    private String operUrl;
    
    /**
     * 用户名称
     */
    private String userName;
    
    private String operName;
    
    
    public String getOperName() {
    	if("1001".equals(operType)) {
    		operName = "登 录/访问";
    	}else if("1002".equals(operType)) {
    		operName = "收藏";
    	}else if("1004".equals(operType)) {
    		operName = "查看题录";
    	}else if("1003".equals(operType)) {
    		operName = "查看原文";
    	}else if("1005".equals(operType)) {
    		operName = "下载原文";
    	}
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId == null ? null : operId.trim();
    }

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser == null ? null : operUser.trim();
    }

    public String getOperDate() {
        return DateFormatUtil.date2Str(operDate);
    }

    public void setOperDate(Date operDate) {
        this.operDate = operDate;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public Object getOperDetail() {
        return operDetail;
    }

    public void setOperDetail(Object operDetail) {
        this.operDetail = operDetail;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId == null ? null : mappingId.trim();
    }

    public Short getClientType() {
        return clientType;
    }

    public void setClientType(Short clientType) {
        this.clientType = clientType;
    }

	public String getOperUrl() {
		return operUrl;
	}

	public void setOperUrl(String operUrl) {
		this.operUrl = operUrl;
	}
    
}