package com.folkestone.bzcx.bean.bean_vo.collection;

import java.util.Date;
import java.util.List;

import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;

public class RCollectionVo {
    private String collectionId;

    private String standardId;

    private String userId;

    private Date collectionDate;
    
    private List<StandardVo> stand;

    private int deleteState;
    
    private int viewTimes;
    
    public int getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(int deleteState) {
		this.deleteState = deleteState;
	}

	public int getViewTimes() {
		return viewTimes;
	}

	public void setViewTimes(int viewTimes) {
		this.viewTimes = viewTimes;
	}

	public List<StandardVo> getStand() {
		return stand;
	}

	public void setStand(List<StandardVo> stand) {
		this.stand = stand;
	}

	public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId == null ? null : collectionId.trim();
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId == null ? null : standardId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }
}