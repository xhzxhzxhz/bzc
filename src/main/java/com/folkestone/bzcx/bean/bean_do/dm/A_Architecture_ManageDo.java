package com.folkestone.bzcx.bean.bean_do.dm;

import java.util.Date;



import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * Describe：体系管理表和数据库所对应的实体类
 * 
 * @author smallking
 *
 *  2017年10月30日
 */
public class A_Architecture_ManageDo {
	/**
	 * 体系管理主键ID
	 */
    private String architectureId = UUIDUtil.getUUID("A_A_M");
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
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    /**
     * 实施时间
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date effectDate;
    /**
     * 废止时间
     */
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invalidDate;
    /**
     * 状态
     */
    //private Short status;
    /**
     * 发布单位
     */
    private String publishUnit;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

//    public Short getStatus() {
//        return status;
//    }

//    public void setStatus(Short status) {
//        this.status = status;
//    }

    public String getPublishUnit() {
        return publishUnit;
    }

    public void setPublishUnit(String publishUnit) {
        this.publishUnit = publishUnit == null ? null : publishUnit.trim();
    }
}