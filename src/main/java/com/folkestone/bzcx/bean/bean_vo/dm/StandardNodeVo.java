package com.folkestone.bzcx.bean.bean_vo.dm;

import java.util.Date;

public class StandardNodeVo {
    /**
     * 主键ID
     */
	private String id;
	/**
	 * 标准的ID
	 */
    private String standardId;
    /**
     * 节点的ID
     */
    private String nodeId;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 节点的Code
     */
    private String nodeCode;
    /**
     * 节点的Name
     */
    private String nodeName;
    
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

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
}