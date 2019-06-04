package com.folkestone.bzcx.bean.bean_do.user;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 角色和资源的对应表R_Role_Resource对应的实体类
 * @author smallking
 *
 */
public class R_Role_ResourceDo {
    private String id = UUIDUtil.getUUID("roleres");

    private String roleId;

    private String resourceId;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}