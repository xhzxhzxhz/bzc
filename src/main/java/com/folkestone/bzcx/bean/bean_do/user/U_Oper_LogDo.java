package com.folkestone.bzcx.bean.bean_do.user;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * Describe：用户操作记录表U_OPER_LOG对应的实体类
 * 
 * @author smallking
 *
 *  2017年11月23日
 */
public class U_Oper_LogDo {
	/**
	 * 主键ID
	 */
    private String operId = UUIDUtil.getUUID("U_O_L");
    /**
     * 操作人的ID(外键 user的ID)
     */
    private String operUser;
    /**
     * 操作日期
     */
    private Date operDate = new Date();
    /**
     * 操作类型 如：1：下载，2：上传:3：预览，4：备份等。自定义一个未使用的数字
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
    
    public U_Oper_LogDo() {
		super();
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

    public Date getOperDate() {
        return operDate;
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