package com.folkestone.bzcx.bean.bean_do.dm;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;

public class A_Standard_NodeDo {
    private String id = UUIDUtil.getUUID("A_S_N");

    private String standardId;

    private String nodeId;

    private Date createDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId == null ? null : standardId.trim();
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}