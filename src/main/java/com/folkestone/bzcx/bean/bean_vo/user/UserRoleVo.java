package com.folkestone.bzcx.bean.bean_vo.user;

import java.util.Date;
/**
 * 角色和人员的关联表
 * @author smallking
 *
 */
public class UserRoleVo {
	/**
	 * 主键Id
	 */
    private String id;
    /**
     * 角色Id
     */
    private String roleId;
    /**
     * 分配类型 1 ：人员 2 ：部门
     */
    private Integer allowType;
    /**
     * 被分配人员或者部门的ID
     */
    private String allowId;
    
    private Date lastoperateTime;

    private String lastoperateUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public Integer getAllowType() {
        return allowType;
    }

    public void setAllowType(Integer allowType) {
        this.allowType = allowType;
    }

    public String getAllowId() {
        return allowId;
    }

    public void setAllowId(String allowId) {
        this.allowId = allowId == null ? null : allowId.trim();
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
        this.lastoperateUser = lastoperateUser == null ? null : lastoperateUser.trim();
    }
}