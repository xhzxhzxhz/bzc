package com.folkestone.bzcx.bean.bean_do.type;

import com.folkestone.bzcx.common.util.UUIDUtil;

public class TypeDictionariesDo {
    private String dictionariesId = UUIDUtil.getUUID("TDiction");;

    private String typeId;

    private String dictionariesName;

    public String getDictionariesId() {
        return dictionariesId;
    }

    public void setDictionariesId(String dictionariesId) {
        this.dictionariesId = dictionariesId == null ? null : dictionariesId.trim();
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getDictionariesName() {
        return dictionariesName;
    }

    public void setDictionariesName(String dictionariesName) {
        this.dictionariesName = dictionariesName == null ? null : dictionariesName.trim();
    }
}