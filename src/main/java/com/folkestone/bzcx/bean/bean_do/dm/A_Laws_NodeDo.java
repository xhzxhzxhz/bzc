package com.folkestone.bzcx.bean.bean_do.dm;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;

public class A_Laws_NodeDo {
	  private String id = UUIDUtil.getUUID("A_S_N");

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
