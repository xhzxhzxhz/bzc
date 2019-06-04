package com.folkestone.bzcx.bean.bean_do.type;

import com.folkestone.bzcx.common.util.UUIDUtil;

public class IcsClassificationDo {
    private String icsId =  UUIDUtil.getUUID("ics");

    private String icsName;

    private String icsParendid;

    private String icsCode;
    private String type;
    /**
     * 这个表示各个分类的父子关系
     */
    private String parentId;
    

    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcsId() {
        return icsId;
    }

    public void setIcsId(String icsId) {
        this.icsId = icsId == null ? null : icsId.trim();
    }

    public String getIcsName() {
        return icsName;
    }

    public void setIcsName(String icsName) {
        this.icsName = icsName == null ? null : icsName.trim();
    }

    public String getIcsParendid() {
        return icsParendid;
    }

    public void setIcsParendid(String icsParendid) {
        this.icsParendid = icsParendid == null ? null : icsParendid.trim();
    }

    public String getIcsCode() {
        return icsCode;
    }

    public void setIcsCode(String icsCode) {
        this.icsCode = icsCode == null ? null : icsCode.trim();
    }
}