package com.folkestone.bzcx.bean.bean_vo.type;

import java.util.List;

public class IcsClassification2Vo {
    private String icsId;

    private String icsName;

    private String icsParendid;

    private String icsCode;

    private List<IcsClassification2Vo> threeIcs;
    
    private int icsSum;
    
    public int getIcsSum() {
		return icsSum;
	}

	public void setIcsSum(int icsSum) {
		this.icsSum = icsSum;
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