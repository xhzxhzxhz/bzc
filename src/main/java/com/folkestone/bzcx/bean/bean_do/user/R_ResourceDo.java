package com.folkestone.bzcx.bean.bean_do.user;


import com.folkestone.bzcx.common.util.UUIDUtil;

public class R_ResourceDo {
    private String id = UUIDUtil.getUUID("res");

    private String name;

    private String url;

    private String parentId;

    private String type;

    private Integer status;

    private String note;

    private String path;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

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
        this.type = type == null ? null : type.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
}