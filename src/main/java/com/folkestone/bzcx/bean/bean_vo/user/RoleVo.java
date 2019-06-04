package com.folkestone.bzcx.bean.bean_vo.user;

import java.util.Date;
import java.util.List;

import com.folkestone.bzcx.common.util.DateFormatUtil;


public class RoleVo {
    private String id;

    private String name;

    private String roleDescribe;
    
    private String note;
    
    private String serial;

    private Integer status;

    private Date lastoperateTime;

    private String lastoperateUserName;
    
    private String lastoperateType;
    
    private List<ResourceVo> resourceList;
    /**
     * 前台编辑用户 需要用到角色多选框 checked为
     * true时候 代表用户属于当前角色
     */
    private boolean checked;
    
	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
	public List<ResourceVo> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<ResourceVo> resourceList) {
		this.resourceList = resourceList;
	}

	public String getLastoperateUserName() {
		return lastoperateUserName;
	}

	public void setLastoperateUserName(String lastoperateUserName) {
		this.lastoperateUserName = lastoperateUserName;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLastoperateTime() {
        return DateFormatUtil.date2Str(lastoperateTime);
    }

    public void setLastoperateTime(Date lastoperateTime) {
        this.lastoperateTime = lastoperateTime;
    }

    public String getLastoperateType() {
        return lastoperateType;
    }

    public void setLastoperateType(String lastoperateType) {
        this.lastoperateType = lastoperateType == null ? null : lastoperateType.trim();
    }
}