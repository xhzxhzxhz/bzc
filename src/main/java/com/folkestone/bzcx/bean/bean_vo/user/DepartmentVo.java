package com.folkestone.bzcx.bean.bean_vo.user;

import java.util.Date;
/**
 * 封装数据展示到界面的实体类
 * @author smallking
 *
 */
public class DepartmentVo {
    private String id;

    private String userName;
    
    private String departmentName;

    private String describe;

    private String parentId;
    
    private String contact;
    
    private String address;
    
    private String sortCode;
    
    private Date lastoperateTime;

    private String lastoperateUser;

    private String lastoperateType;

    private String contactUserName;
    
    private Date createTime;
    
    private String path;
    /**
     * 当前部门所对应的体系ID
     */
    private String architectureId;
    
    /**
	 * 账号
	 */
    private String account;
    /**
     * 密码
     */
    private String password;
    
    
    private int usertotal;
    /**
     * 改变状态
     */
    private String changeState;
    
    
	public int getUsertotal() {
		return usertotal;
	}

	public void setUsertotal(int usertotal) {
		this.usertotal = usertotal;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContactUserName() {
		return contactUserName;
	}

	public void setContactUserName(String contactUserName) {
		this.contactUserName = contactUserName;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getArchitectureId() {
		return architectureId;
	}

	public void setArchitectureId(String architectureId) {
		this.architectureId = architectureId;
	}

	@Override
	public String toString() {
		return "DepartmentVo [id=" + id + ", userName=" + userName + ", departmentName=" + departmentName
				+ ", describe=" + describe + ", parentId=" + parentId + ", contact=" + contact + ", address=" + address
				+ ", sortCode=" + sortCode + ", lastoperateTime=" + lastoperateTime + ", lastoperateUser="
				+ lastoperateUser + ", lastoperateType=" + lastoperateType + ", contactUserName=" + contactUserName
				+ ", path=" + path + ", architectureId=" + architectureId + "]";
	}

	public String getChangeState() {
		return changeState;
	}

	public void setChangeState(String changeState) {
		this.changeState = changeState;
	}

}