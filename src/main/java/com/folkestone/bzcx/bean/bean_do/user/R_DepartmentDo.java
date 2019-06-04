package com.folkestone.bzcx.bean.bean_do.user;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 部门表r_department对应的实体类
 * @author smallking
 *
 */
public class R_DepartmentDo {
    /**
     * 主键ID
     */
	private String id = UUIDUtil.getUUID("depart");
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 描述
     */
    private String describe;
    /**
     * 联系人ID
     */
    private String contactUserId;
    /**
     * 联系方式
     */
    private String contact;
    /**
     * 地址
     */
    private String address;
    /**
     * 父级部门的ID
     */
    private String parentId;
    /**
     * 排序码
     */
    private String sortCode;
    /**
     * 创建时间
     */
    private Date createTime;

    private Date lastoperateTime;

    private String lastoperateUser;

    private String lastoperateType;
    /**
     * 标识该部门所在树中的位置，由parentId所组成的一个字符串
     */
    private String path;
    
    /**
   	 * 账号
   	 */
       private String account;
       /**
        * 密码
        */
       private String password;
       
       
   	public String getAccount() {
   		return account;
   	}

   	public void setAccount(String account) {
   		this.account = account;
   	}
   	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getContactUserId() {
		return contactUserId;
	}

	public void setContactUserId(String contactUserId) {
		this.contactUserId = contactUserId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastoperateTime() {
        return lastoperateTime;
    }

    public void setLastoperateTime(Date lastoperateTime) {
        this.lastoperateTime = lastoperateTime;
    }

    public String getLastoperateUser() {
        return lastoperateUser;
    }

    public void setLastoperateUser(String lastoperateUser) {
        this.lastoperateUser = lastoperateUser;
    }

    public String getLastoperateType() {
        return lastoperateType;
    }

    public void setLastoperateType(String lastoperateType) {
        this.lastoperateType = lastoperateType == null ? null : lastoperateType.trim();
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
}