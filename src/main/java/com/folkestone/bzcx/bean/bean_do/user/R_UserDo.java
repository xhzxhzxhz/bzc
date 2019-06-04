package com.folkestone.bzcx.bean.bean_do.user;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 用户表r_user对应的实体类
 * @author smallking
 *
 */
public class R_UserDo {
	
	private String id = UUIDUtil.getUUID("user");

	private String account;

	private String password;

	private String icon;

	private String realname;

	private Integer sex;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date brithday;

	private String email;

	private String tel;

	private String officeTel;

	private String qq;
	
	private String note;

	private String departmentId;

	private String duty;
	
	private String officeLocation;

	private Integer status;

	private Date createTime;

	private Date lastoperateTime;

	private String lastoperateUser;

	private String lastoperateType;
	
	 
    private String openid;
    
    private String nickname;
    
    private String country;
    
    private String province;
    
    private String city;
    
    private String company;
    
    private String department;
    
    private String WeChat;
    
	public String getWeChat() {
		return WeChat;
	}

	public void setWeChat(String weChat) {
		WeChat = weChat;
	}
    
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
    
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname == null ? null : realname.trim();
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel == null ? null : officeTel.trim();
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq == null ? null : qq.trim();
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation == null ? null : officeLocation.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastoperateTime() {
		return lastoperateTime;
	}

	public void setLastoperateTime(Date lastoperateTime) {
		this.lastoperateTime = lastoperateTime;
	}

	
	public String getLastoperateUser() {
		return lastoperateUser;
	}

	public void setLastoperateUser(String lastoperateUser) {
		this.lastoperateUser = lastoperateUser;
	}

	public String getLastoperateType() {
		return lastoperateType;
	}

	public void setLastoperateType(String lastoperateType) {
		this.lastoperateType = lastoperateType == null ? null : lastoperateType.trim();
	}
}
