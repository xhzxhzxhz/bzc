package com.folkestone.bzcx.bean.bean_vo.dm;

import java.util.Date;

import com.folkestone.bzcx.common.util.DateFormatUtil;
/**
 * Describe：体系表返给前台的Vo实体。
 * 
 * @author smallking
 *
 *  2017年10月30日
 */
public class ArchitectureManageVo {

	/**
	 * 体系管理主键ID
	 */
    private String architectureId;
    /**
     * 体系中文名称
     */
    private Object architectureCnName;
    /**
     * 体系英文名称
     */
    private Object architectureEnName;
    /**
     * 体系代码
     */
    private String architectureCode;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 发布时间
     */
    private Date publishDate;
    /**
     * 实施时间
     */
    private Date effectDate;
    /**
     * 废止时间
     */
    private Date invalidDate;
    /**
     * 状态
     */
    private int status;
    /**
     * 发布单位
     */
    private String publishUnit;

    private String departmentName;
    
    /**
     * 父级部门id
     */
    private String parentid;
    
    public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getArchitectureId() {
        return architectureId;
    }

    public void setArchitectureId(String architectureId) {
        this.architectureId = architectureId == null ? null : architectureId.trim();
    }

    public Object getArchitectureCnName() {
        return architectureCnName;
    }

    public void setArchitectureCnName(Object architectureCnName) {
        this.architectureCnName = architectureCnName;
    }

    public Object getArchitectureEnName() {
        return architectureEnName;
    }

    public void setArchitectureEnName(Object architectureEnName) {
        this.architectureEnName = architectureEnName;
    }

    public String getArchitectureCode() {
        return architectureCode;
    }

    public void setArchitectureCode(String architectureCode) {
        this.architectureCode = architectureCode == null ? null : architectureCode.trim();
    }

    public String getCreateDate() {
        return DateFormatUtil.date2Str(createDate);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPublishDate() {
        return DateFormatUtil.date2Str(publishDate);
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getEffectDate() {
        return DateFormatUtil.date2Str(effectDate);
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public String getInvalidDate() {
        return DateFormatUtil.date2Str(invalidDate);
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPublishUnit() {
        return publishUnit;
    }

    public void setPublishUnit(String publishUnit) {
        this.publishUnit = publishUnit == null ? null : publishUnit.trim();
    }

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
    
}