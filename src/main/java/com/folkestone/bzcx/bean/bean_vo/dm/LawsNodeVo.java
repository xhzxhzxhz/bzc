package com.folkestone.bzcx.bean.bean_vo.dm;

import java.util.Date;

public class LawsNodeVo {
	private String id;
	private String lawsId;

	private String nodeId;

	private Date createDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLawsId() {
		return lawsId;
	}

	public void setLawsId(String lawsId) {
		this.lawsId = lawsId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}
