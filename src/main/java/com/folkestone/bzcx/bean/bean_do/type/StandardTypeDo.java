package com.folkestone.bzcx.bean.bean_do.type;

import com.folkestone.bzcx.common.util.UUIDUtil;

public class StandardTypeDo {
    private String typeId = UUIDUtil.getUUID("SType");

    private String typeName;

    private String typeLetter;

    private String comment;
    
    
    public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment== null ? null : comment.trim();
	}
    
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeLetter() {
        return typeLetter;
    }

    public void setTypeLetter(String typeLetter) {
        this.typeLetter = typeLetter == null ? null : typeLetter.trim();
    }
}