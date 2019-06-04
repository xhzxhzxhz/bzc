package com.folkestone.bzcx.bean.bean_vo.interact;

import java.util.Date;

public class RInformationVo {
    private String informationId;

    private String informationName;

    private String informationUserid;

    private String informationTel;

    private Integer informationQq;

    private String informationEmail;

    private String informationCompany;

    private String informationDepartment;

    private String informationDuty;

    private String informationWechat;

    private String informationCount;

    private Date informationDate;
    
    private String usernickname;
    
    private String realname;
    
    private int state;
    
    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUsernickname() {
		return usernickname;
	}

	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Date getInformationDate() {
        return informationDate;
    }

    public void setInformationDate(Date informationDate) {
        this.informationDate = informationDate;
    }

	public String getInformationId() {
        return informationId;
    }

    public void setInformationId(String informationId) {
        this.informationId = informationId == null ? null : informationId.trim();
    }

    public String getInformationName() {
        return informationName;
    }

    public void setInformationName(String informationName) {
        this.informationName = informationName == null ? null : informationName.trim();
    }

    public String getInformationUserid() {
        return informationUserid;
    }

    public void setInformationUserid(String informationUserid) {
        this.informationUserid = informationUserid == null ? null : informationUserid.trim();
    }

    public String getInformationTel() {
        return informationTel;
    }

    public void setInformationTel(String informationTel) {
        this.informationTel = informationTel == null ? null : informationTel.trim();
    }

    public Integer getInformationQq() {
        return informationQq;
    }

    public void setInformationQq(Integer informationQq) {
        this.informationQq = informationQq;
    }

    public String getInformationEmail() {
        return informationEmail;
    }

    public void setInformationEmail(String informationEmail) {
        this.informationEmail = informationEmail == null ? null : informationEmail.trim();
    }

    public String getInformationCompany() {
        return informationCompany;
    }

    public void setInformationCompany(String informationCompany) {
        this.informationCompany = informationCompany == null ? null : informationCompany.trim();
    }

    public String getInformationDepartment() {
        return informationDepartment;
    }

    public void setInformationDepartment(String informationDepartment) {
        this.informationDepartment = informationDepartment == null ? null : informationDepartment.trim();
    }

    public String getInformationDuty() {
        return informationDuty;
    }

    public void setInformationDuty(String informationDuty) {
        this.informationDuty = informationDuty == null ? null : informationDuty.trim();
    }

    public String getInformationWechat() {
        return informationWechat;
    }

    public void setInformationWechat(String informationWechat) {
        this.informationWechat = informationWechat == null ? null : informationWechat.trim();
    }

    public String getInformationCount() {
        return informationCount;
    }

    public void setInformationCount(String informationCount) {
        this.informationCount = informationCount == null ? null : informationCount.trim();
    }
}