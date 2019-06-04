package com.folkestone.bzcx.bean.bean_vo.user;

import java.util.Date;
import java.util.List;

import com.folkestone.bzcx.common.util.DateFormatUtil;

/**
 * Describe：返回给界面json的用户类
 * 
 * @author smallking
 *
 * 2017年12月26日
 */
public class UserVo {
	private boolean isonlineLimit;
    public boolean isIsonlineLimit() {
		return isonlineLimit;
	}

	public void setIsonlineLimit(boolean isonlineLimit) {
		this.isonlineLimit = isonlineLimit;
	}

	private int onlineLimit;
		public int getOnlineLimit() {
		return onlineLimit;
	}

	public void setOnlineLimit(int onlineLimit) {
		this.onlineLimit = onlineLimit;
	}

		/**
		 * 主键ID
		 */
		private String id;
		/**
		 * 账号
		 */
	    private String account;
	    /**
	     * 密码
	     */
	    private String password;
	    /**
	     * 头像路径（可以在界面预览的路径）
	     */
	    private String icon;
	    /**
	     * 真实姓名
	     */
	    private String realname;
	    /**
	     * 性别
	     */
	    private Integer sex;
	    /**
	     * 生日
	     */
	    private Date brithday;
	    /**
	     * 邮箱
	     */
	    private String email;
	    /**
	     * 电话
	     */
	    private String tel;
	    /**
	     * 电话号码
	     */
	    private String officeTel;
	    /**
	     * QQ号码
	     */
	    private String qq;
	    /**
	     * 角色备注
	     */
	    private String note;
	    /**
	     * 当前用户的所拥有的角色集合
	     */
	    private List<RoleVo> roleList;
	    /**
	     * 所属部门ID
	     */
	    private String departmentId;
	    /**
	     * 职务
	     */
	    private String duty;
	    /**
	     * 办公地址
	     */
	    private String officeLocation;
	    /**
	     * 用户状态 
		 * 0：禁用；
		 * 1：正常；
		 * 2：待审核；
	     */
	    private Integer status;
	    /**
	     * 创建时间
	     */
	    private Date createTime;
	    /**
	     * 最后操作时间
	     */
	    private Date lastoperateTime;
	    /**
	     * 最后操作人
	     */
	    private String lastoperateUser;
	    /**
	     * 最后操作类型
	     */
	    private String lastoperateType;
	    /**
	     * 用户角色名称
	     */
	    private String roleName;
	    /**
	     * 部门名称
	     */
	    private String departmentName;
	    /**
	     * 最后操作人的名称
	     */
	    private String lastoperateUserName;
	    /**
	     * 该用户所拥有的资源列表
	     */
	    private List<ResourceVo> resourceList;
	    /**
	     * 界面显示的生日时间字符串
	     */
	    private String brithdayVo;
	    /**
	     * 后台管理修改文档类型的时候 用到的checked属性
	     */
	    private boolean checked;
	    
	    
	    /**
	     * 部门的父级
	     */
	    private String departmentParent;
	    
	    /**
	     * 后台显示哪个界面
	     */
	    private Integer showadmin;
	    
	    
	    private String openid;
	    
	    private String nickname;
	    
	    private String country;
	    
	    private String province;
	    
	    private String city;
	    
	    private String company;
	    
	    private String department;
	    
	    private String WeChat;
	    
	    private Integer subscribe;
	    
	    private Date subscribe_time;
	    
	    private String subscribe_scene;
	    
	    
		public Integer getSubscribe() {
			return subscribe;
		}

		public void setSubscribe(Integer subscribe) {
			this.subscribe = subscribe;
		}

		public Date getSubscribe_time() {
			return subscribe_time;
		}

		public void setSubscribe_time(Date subscribe_time) {
			this.subscribe_time = subscribe_time;
		}

		public String getSubscribe_scene() {
			return subscribe_scene;
		}

		public void setSubscribe_scene(String subscribe_scene) {
			this.subscribe_scene = subscribe_scene;
		}

		public String getWeChat() {
			return WeChat;
		}

		public void setWeChat(String weChat) {
			WeChat = weChat;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}

		public String getDepartment() {
			return department;
		}

		public void setDepartment(String department) {
			this.department = department;
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

		public Integer getShowadmin() {
			return showadmin;
		}

		public void setShowadmin(Integer showadmin) {
			this.showadmin = showadmin;
		}

		public String getDepartmentParent() {
			return departmentParent;
		}

		public void setDepartmentParent(String departmentParent) {
			this.departmentParent = departmentParent;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public String getBrithdayVo() {
			return brithdayVo;
		}

		public void setBrithdayVo(String brithdayVo) {
			this.brithdayVo = brithdayVo;
		}

	    public List<ResourceVo> getResourceList() {
			return resourceList;
		}

		public void setResourceList(List<ResourceVo> resourceList) {
			this.resourceList = resourceList;
		}
	    
	    public String getLastoperateUserName() {
			return lastoperateUserName;
		}

		public void setLastoperateUserName(String lastoperateUserName) {
			this.lastoperateUserName = lastoperateUserName;
		}

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
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
	    	return "/img/icon/" + icon;
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

	    public String getBrithday() {
	    	if(brithday != null){
	    		return DateFormatUtil.date2Str(brithday);
	    	}
	    	return null;
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
	    
	    public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public List<RoleVo> getRoleList() {
	        return roleList;
	    }

	    public void setRoleList(List<RoleVo> roleList) {
	        this.roleList = roleList;
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

	    public String getCreateTime() {
	        return DateFormatUtil.date2Str(createTime);
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public String getLastoperateTime() {
	        return DateFormatUtil.date2Str(lastoperateTime);
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

		public UserVo(String id) {
			this.id = id;
		}
		public UserVo() {
			
		}

		@Override
		public String toString() {
			return "UserVo [id=" + id + ", account=" + account + ", password=" + password + ", icon=" + icon
					+ ", realname=" + realname + ", sex=" + sex + ", brithday=" + brithday + ", email=" + email
					+ ", tel=" + tel + ", officeTel=" + officeTel + ", qq=" + qq + ", note=" + note + ", roleList="
					+ roleList + ", departmentId=" + departmentId + ", duty=" + duty + ", officeLocation="
					+ officeLocation + ", status=" + status + ", createTime=" + createTime + ", lastoperateTime="
					+ lastoperateTime + ", lastoperateUser=" + lastoperateUser + ", lastoperateType=" + lastoperateType
					+ ", roleName=" + roleName + ", departmentName=" + departmentName + ", lastoperateUserName="
					+ lastoperateUserName + ", resourceList=" + resourceList + ", brithdayVo=" + brithdayVo
					+ ", checked=" + checked + "]";
		}
		
	    
}
