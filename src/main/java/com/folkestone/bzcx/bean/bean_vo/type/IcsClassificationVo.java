package com.folkestone.bzcx.bean.bean_vo.type;

import java.util.List;

public class IcsClassificationVo {
    private String icsId;

    private String icsName;

    private String icsParendid;

    private String icsCode;
    /**
     * 各个分类中具有父子关系的分类
     */
    private String parentId;
    /**
     * 子的集合
     */
    private List<IcsClassificationVo> secondChildren;

    public List<IcsClassificationVo> getSecondChildren() {
		return secondChildren;
	}

	public void setSecondChildren(List<IcsClassificationVo> secondChildren) {
		this.secondChildren = secondChildren;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	private List<IcsClassification2Vo> threeIcs;
    private List<IcsClassificationVo> children;
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String type;
    
    
	public List<IcsClassificationVo> getChildren() {
		return children;
	}

	public void setChildren(List<IcsClassificationVo> children) {
		this.children = children;
	}

	public List<IcsClassification2Vo> getThreeIcs() {
		return threeIcs;
	}

	public void setThreeIcs(List<IcsClassification2Vo> threeIcs) {
		this.threeIcs = threeIcs;
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