package com.folkestone.bzcx.bean.bean_do.user;

import java.util.Date;
import java.util.UUID;

public class R_RoleDo {
    private String id = UUID.randomUUID().toString().replaceAll("-", "");

    private String name;

    private String roleDescribe;
    			    
    private String note;
    
    private String serial;

    private Integer status;

    private Date lastoperateTime;

    private Integer lastoperateUser;

    private String lastoperateType;
    private String userType;
    

    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public String getRoleDescribe() {
		return roleDescribe;
	}

	public void setRoleDescribe(String roleDescribe) {
		this.roleDescribe = roleDescribe;
	}

	public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial == null ? null : serial.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastoperateTime() {
        return lastoperateTime;
    }

    public void setLastoperateTime(Date lastoperateTime) {
        this.lastoperateTime = lastoperateTime;
    }

    public Integer getLastoperateUser() {
        return lastoperateUser;
    }

    public void setLastoperateUser(Integer lastoperateUser) {
        this.lastoperateUser = lastoperateUser;
    }

    public String getLastoperateType() {
        return lastoperateType;
    }

    public void setLastoperateType(String lastoperateType) {
        this.lastoperateType = lastoperateType == null ? null : lastoperateType.trim();
    }
}