package com.folkestone.bzcx.bean.bean_do.dm;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * Describe：数据库体系节点表，对应的实体类。
 * 
 * @author smallking
 *
 *  2017年10月30日
 */
public class A_Architecture_NodeDo {
	/**
	 * 节点ID
	 */
    private String nodeId = UUIDUtil.getUUID("A_A_N");
    /**
     * 节点中文名
     */
    private Object nodeCnName;
    /**
     * 节点英文名
     */
    private Object nodeEnName;
    /**
     * 节点代码
     */
    private String nodeCode;
    /**
     * 父级ID
     */
    private String parentId;
    /**
     * 节点所在位置
     */
    private Object path;
    /**
     * 所在体系的ID
     */
    private String architectureId;
    /**
     * 备注
     */
    private Object note;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    
    /**
     * 排序
     */
    private Integer nodeSort;
    
    public Integer getNodeSort() {
		return nodeSort;
	}

	public void setNodeSort(Integer nodeSort) {
		this.nodeSort = nodeSort;
	}

	public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public Object getNodeCnName() {
        return nodeCnName;
    }

    public void setNodeCnName(Object nodeCnName) {
        this.nodeCnName = nodeCnName;
    }

    public Object getNodeEnName() {
        return nodeEnName;
    }

    public void setNodeEnName(Object nodeEnName) {
        this.nodeEnName = nodeEnName;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode == null ? null : nodeCode.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    public String getArchitectureId() {
        return architectureId;
    }

    public void setArchitectureId(String architectureId) {
        this.architectureId = architectureId == null ? null : architectureId.trim();
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public Date getCreateDate() {
    	
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}