package com.folkestone.bzcx.bean.bean_vo.type;

import java.io.Serializable;

public class StandardTypeVo  implements Serializable{
    private String typeId;

    private String typeName;

    private String typeLetter;

    private String comment;
    
    private String dictionariesName;
    
    public String getDictionariesName() {
		return dictionariesName;
	}

	public void setDictionariesName(String dictionariesName) {
		this.dictionariesName = dictionariesName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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