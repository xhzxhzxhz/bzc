package com.folkestone.bzcx.bean.bean_do.dm;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;

public class S_StandardDo {
	/**
	 * 主键ID
	 */
    private String standardId = UUIDUtil.getUUID("S_S");
    /**
     * 标准号
     */
    private String standardNo;
    /**
     * 去空标准号
     */
    private String standardNoTrim;
    /**
     * 标准中文名
     */
    private Object standardCnName;
    /**
     * 标准英文名
     */
    private Object standardEnName;
    /**
     * 标准的原文名称
     */
    private Object standardFileName;
    /**
     * 发布单位
     */
    private Object pubOrg;
    /**
     * 采用关系
     */
    private Object adoptRelation;
    /**
     * 代替标准
     */
    private Object replaceStandard;
    /**
     * 被代替标准
     */
    private Object replacedStandard;
    /**
     * 引用标准
     */
    private Object quoteStandard;
    /**
     * 修改件
     */
    private Object modifyObject;
    /**
     * 补充件
     */
    private Object supplementaryObject;
    /**
     * 发布日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    /**
     * 实施日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date implementationDate;
    /**
     * 确认日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date confirmDate;
    /**
     * 截止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    /**
     * 废止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date invalidDate;
    /**
     * 归口单位
     */
    private Object belongToUnit;
    /**
     * 起草单位
     */
    private Object draftUnit;
    /**
     * 标准类型
     */
    private Object standardType;
    /**
     * 标准水平
     */
    private Object standardLevel2;
    /**
     * 中文主题词
     */
    private Object themeCnName;
    /**
     * 英文主题词
     */
    private Object themeEnName;
    /**
     * 中文自由词
     */
    private Object freeCnName;
    /**
     * 正文语种
     */
    private Short language;
    /**
     * 国别
     */
    private Object country;
    /**
     * 国际标准分类法
     */
    private Object internationClass;
    /**
     * 中国标准分类法
     */
    private Object chineseClass;
    /**
     * 标准级别
     */
    private Short standardLevel;
    /**
     * 立项日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date projectStartDate;
    /**
     * 技术领域
     */
    private Object technicalField;
    /**
     * 起草人员或者专家
     */
    private Object draftStaff;
    /**
     * 联系方式
     */
    private Object contactWay;
    /**
     * 管辖权
     */
    private Object jurisdiction;
    /**
     * 译文
     */
    private Object translation;
    /**
     * UDC号
     */
    private Object udc;
    /**
     * 页数
     */
    private Integer pageNumber;
    /**
     * 出版社
     */
    private Object publisher;
    /**
     * 出版时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pubDate;
    /**
     * 书号
     */
    private Object isbn;
    /**
     * 摘要
     */
    private Object digest;
    /**
     * 标准状态
     */
    private Short standardStatus;
    /**
     * 最后操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastOperDate;
    /**
     * 最后操作类型
     */
    private Short lastOperType;
    /**
     * 最后操作人
     */
    private Object lastOperUser;
    /**
     * 体系代码
     */
    private String nodeCode;
    
    private String uploadUnit;
    
    private String htmlUrl;
    
    private String icsCode;
    
    private String pinyin;
    /**
     * 性质
     */
    private String nature;
    /**
     * 主管部门
     */
    private String competentDepartment;
    
    
    public String getCompetentDepartment() {
		return competentDepartment;
	}

	public void setCompetentDepartment(String competentDepartment) {
		this.competentDepartment = competentDepartment;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
    
    public String getIcsCode() {
		return icsCode;
	}

	public void setIcsCode(String icsCode) {
		this.icsCode = icsCode;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
    
	public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId == null ? null : standardId.trim();
    }

    public String getStandardNo() {
        return standardNo;
    }

    public void setStandardNo(String standardNo) {
    	  StringBuffer sb = new StringBuffer();
    	String standardNoTrim =standardNo.replaceAll("[^a-zA-Z0-9]", "").replaceAll("^", "");
        char[] c = standardNoTrim.toCharArray();
    	  for(int i=0;i<c.length;i++){
    	   if(c[i]>=97){
    	    sb.append((c[i]+"").toUpperCase());
    	   }else {
			   sb.append(c[i]);
		   }
    	  }
    	setStandardNoTrim(sb.toString());
        this.standardNo = standardNo == null ? null : standardNo.trim();
    }

    public String getStandardNoTrim() {
		return standardNoTrim;
	}

	public void setStandardNoTrim(String standardNoTrim) {
		this.standardNoTrim = standardNoTrim;
	}
    
    
    public Object getStandardCnName() {
        return standardCnName;
    }

    public void setStandardCnName(Object standardCnName) {
        this.standardCnName = standardCnName;
    }

    public Object getStandardEnName() {
        return standardEnName;
    }

    public void setStandardEnName(Object standardEnName) {
        this.standardEnName = standardEnName;
    }

    public Object getStandardFileName() {
        return standardFileName;
    }

    public void setStandardFileName(Object standardFileName) {
        this.standardFileName = standardFileName;
    }

    public Object getPubOrg() {
        return pubOrg;
    }

    public void setPubOrg(Object pubOrg) {
        this.pubOrg = pubOrg;
    }

    public Object getAdoptRelation() {
        return adoptRelation;
    }

    public void setAdoptRelation(Object adoptRelation) {
        this.adoptRelation = adoptRelation;
    }

    public Object getReplaceStandard() {
        return replaceStandard;
    }

    public void setReplaceStandard(Object replaceStandard) {
        this.replaceStandard = replaceStandard;
    }

    public Object getReplacedStandard() {
        return replacedStandard;
    }

    public void setReplacedStandard(Object replacedStandard) {
        this.replacedStandard = replacedStandard;
    }

    public Object getQuoteStandard() {
        return quoteStandard;
    }

    public void setQuoteStandard(Object quoteStandard) {
        this.quoteStandard = quoteStandard;
    }

    public Object getModifyObject() {
        return modifyObject;
    }

    public void setModifyObject(Object modifyObject) {
        this.modifyObject = modifyObject;
    }

    public Object getSupplementaryObject() {
        return supplementaryObject;
    }

    public void setSupplementaryObject(Object supplementaryObject) {
        this.supplementaryObject = supplementaryObject;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getImplementationDate() {
        return implementationDate;
    }

    public void setImplementationDate(Date implementationDate) {
        this.implementationDate = implementationDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public Object getBelongToUnit() {
        return belongToUnit;
    }

    public void setBelongToUnit(Object belongToUnit) {
        this.belongToUnit = belongToUnit;
    }

    public Object getDraftUnit() {
        return draftUnit;
    }

    public void setDraftUnit(Object draftUnit) {
        this.draftUnit = draftUnit;
    }

    public Object getStandardType() {
        return standardType;
    }

    public void setStandardType(Object standardType) {
        this.standardType = standardType;
    }

    public Object getStandardLevel2() {
        return standardLevel2;
    }

    public void setStandardLevel2(Object standardLevel2) {
        this.standardLevel2 = standardLevel2;
    }

    public Object getThemeCnName() {
        return themeCnName;
    }

    public void setThemeCnName(Object themeCnName) {
        this.themeCnName = themeCnName;
    }

    public Object getThemeEnName() {
        return themeEnName;
    }

    public void setThemeEnName(Object themeEnName) {
        this.themeEnName = themeEnName;
    }

    public Object getFreeCnName() {
        return freeCnName;
    }

    public void setFreeCnName(Object freeCnName) {
        this.freeCnName = freeCnName;
    }

    public Short getLanguage() {
        return language;
    }

    public void setLanguage(Short language) {
        this.language = language;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getInternationClass() {
        return internationClass;
    }

    public void setInternationClass(Object internationClass) {
        this.internationClass = internationClass;
    }

    public Object getChineseClass() {
        return chineseClass;
    }

    public void setChineseClass(Object chineseClass) {
        this.chineseClass = chineseClass;
    }

    public Short getStandardLevel() {
        return standardLevel;
    }

    public void setStandardLevel(Short standardLevel) {
        this.standardLevel = standardLevel;
    }

    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Object getTechnicalField() {
        return technicalField;
    }

    public void setTechnicalField(Object technicalField) {
        this.technicalField = technicalField;
    }

    public Object getDraftStaff() {
        return draftStaff;
    }

    public void setDraftStaff(Object draftStaff) {
        this.draftStaff = draftStaff;
    }

    public Object getContactWay() {
        return contactWay;
    }

    public void setContactWay(Object contactWay) {
        this.contactWay = contactWay;
    }

    public Object getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(Object jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Object getTranslation() {
        return translation;
    }

    public void setTranslation(Object translation) {
        this.translation = translation;
    }

    public Object getUdc() {
        return udc;
    }

    public void setUdc(Object udc) {
        this.udc = udc;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Object getPublisher() {
        return publisher;
    }

    public void setPublisher(Object publisher) {
        this.publisher = publisher;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Object getIsbn() {
        return isbn;
    }

    public void setIsbn(Object isbn) {
        this.isbn = isbn;
    }

    public Object getDigest() {
		return digest;
	}

	public void setDigest(Object digest) {
		this.digest = digest;
	}

	public Short getStandardStatus() {
        return standardStatus;
    }

    public void setStandardStatus(Short standardStatus) {
        this.standardStatus = standardStatus;
    }

    public Date getLastOperDate() {
        return lastOperDate;
    }

    public void setLastOperDate(Date lastOperDate) {
        this.lastOperDate = lastOperDate;
    }

    public Short getLastOperType() {
        return lastOperType;
    }

    public void setLastOperType(Short lastOperType) {
        this.lastOperType = lastOperType;
    }

    public Object getLastOperUser() {
        return lastOperUser;
    }

    public void setLastOperUser(Object lastOperUser) {
        this.lastOperUser = lastOperUser;
    }

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getUploadUnit() {
		return uploadUnit;
	}

	public void setUploadUnit(String uploadUnit) {
		this.uploadUnit = uploadUnit;
	}

	@Override
	public String toString() {
		return "S_StandardDo [standardId=" + standardId + ", standardNo=" + standardNo + ", standardNoTrim="
				+ standardNoTrim + ", standardCnName=" + standardCnName + ", standardEnName=" + standardEnName
				+ ", standardFileName=" + standardFileName + ", pubOrg=" + pubOrg + ", adoptRelation=" + adoptRelation
				+ ", replaceStandard=" + replaceStandard + ", replacedStandard=" + replacedStandard + ", quoteStandard="
				+ quoteStandard + ", modifyObject=" + modifyObject + ", supplementaryObject=" + supplementaryObject
				+ ", publishDate=" + publishDate + ", implementationDate=" + implementationDate + ", confirmDate="
				+ confirmDate + ", endDate=" + endDate + ", invalidDate=" + invalidDate + ", belongToUnit="
				+ belongToUnit + ", draftUnit=" + draftUnit + ", standardType=" + standardType + ", standardLevel2="
				+ standardLevel2 + ", themeCnName=" + themeCnName + ", themeEnName=" + themeEnName + ", freeCnName="
				+ freeCnName + ", language=" + language + ", country=" + country + ", internationClass="
				+ internationClass + ", chineseClass=" + chineseClass + ", standardLevel=" + standardLevel
				+ ", projectStartDate=" + projectStartDate + ", technicalField=" + technicalField + ", draftStaff="
				+ draftStaff + ", contactWay=" + contactWay + ", jurisdiction=" + jurisdiction + ", translation="
				+ translation + ", udc=" + udc + ", pageNumber=" + pageNumber + ", publisher=" + publisher
				+ ", pubDate=" + pubDate + ", isbn=" + isbn + ", digest=" + digest + ", standardStatus="
				+ standardStatus + ", lastOperDate=" + lastOperDate + ", lastOperType=" + lastOperType
				+ ", lastOperUser=" + lastOperUser + ", nodeCode=" + nodeCode + ", uploadUnit=" + uploadUnit + "]";
	}
	
}