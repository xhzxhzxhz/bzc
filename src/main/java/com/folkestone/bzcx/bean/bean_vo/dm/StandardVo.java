package com.folkestone.bzcx.bean.bean_vo.dm;

import java.util.Date;

import com.folkestone.bzcx.common.util.DateFormatUtil;

public class StandardVo {
	/**
	 * 主键ID
	 */
    private String standardId;
    /**
     * 标准号
     */
    private String standardNo;
    /**
     * 标准中文名
     */
    private Object standardCnName;
    /**
     * 标准英文名
     */
    private Object standardEnName;
    /**
     * 标准的
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
    private Date publishDate;
    /**
     * 实施日期
     */
    private Date implementationDate;
    /**
     * 确认日期
     */
    private Date confirmDate;
    /**
     * 截止日期
     */
    private Date endDate;
    /**
     * 废止日期
     */
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

    private String nodeCode;
    /**
     * 最后操作时间
     */
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
     * 上传单位
     */
    private String uploadUnit;
    /**
     * 体系节点名称
     */
    private String archNodeName;
    
    /**
     * 去空标准号
     */
    private String standardNoTrim;
    
    
    private String htmlUrl;
    
    private String icsCode;
    
    private String pinyin;
    
    /**
     * 起始发布日期
     */
    private String publishDatebegin;
    
    /**
     * 结束发布日期
     */
    private String publishDateend;
    
    private String implementationDatebegin;
    
    private String implementationDateend;
    /**
     * 性质
     */
    private String nature;
    /**
     * 主管部门
     * @return
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

    
    public String getImplementationDatebegin() {
		return implementationDatebegin;
	}

	public void setImplementationDatebegin(String implementationDatebegin) {
		this.implementationDatebegin = implementationDatebegin;
	}

	public String getImplementationDateend() {
		return implementationDateend;
	}

	public void setImplementationDateend(String implementationDateend) {
		this.implementationDateend = implementationDateend;
	}

	public String getPublishDatebegin() {
		return publishDatebegin;
	}

	public void setPublishDatebegin(String publishDatebegin) {
		this.publishDatebegin = publishDatebegin;
	}

	public String getPublishDateend() {
		return publishDateend;
	}

	public void setPublishDateend(String publishDateend) {
		this.publishDateend = publishDateend;
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

	public String getStandardNoTrim() {
		return standardNoTrim;
	}

	public void setStandardNoTrim(String standardNoTrim) {
		this.standardNoTrim = standardNoTrim;
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
        this.standardNo = standardNo == null ? null : standardNo.trim();
    }

    public Object getStandardCnName() {
    	if(standardCnName == null){
    		return "";
    	}
        return standardCnName;
    }

    public void setStandardCnName(Object standardCnName) {
        this.standardCnName = standardCnName;
    }

    public Object getStandardEnName() {
    	if(standardEnName == null){
    		return "";
    	}
        return standardEnName;
    }

    public void setStandardEnName(Object standardEnName) {
        this.standardEnName = standardEnName;
    }

    public Object getStandardFileName() {
    	if(standardFileName == null){
    		return "";
    	}
        return standardFileName;
    }

    public void setStandardFileName(Object standardFileName) {
        this.standardFileName = standardFileName;
    }

    public Object getPubOrg() {
    	if(pubOrg == null){
    		return "";
    	}
        return pubOrg;
    }

    public void setPubOrg(Object pubOrg) {
        this.pubOrg = pubOrg;
    }

    public Object getAdoptRelation() {
    	if(adoptRelation == null){
    		return "";
    	}
        return adoptRelation;
    }

    public void setAdoptRelation(Object adoptRelation) {
        this.adoptRelation = adoptRelation;
    }

    public Object getReplaceStandard() {
    	if(replaceStandard == null){
    		return "";
    	}
        return replaceStandard;
    }

    public void setReplaceStandard(Object replaceStandard) {
        this.replaceStandard = replaceStandard;
    }

    public Object getReplacedStandard() {
    	if(replacedStandard == null){
    		return "";
    	}
        return replacedStandard;
    }

    public void setReplacedStandard(Object replacedStandard) {
        this.replacedStandard = replacedStandard;
    }

    public Object getQuoteStandard() {
    	if(quoteStandard == null){
    		return "";
    	}
        return quoteStandard;
    }

    public void setQuoteStandard(Object quoteStandard) {
        this.quoteStandard = quoteStandard;
    }

    public Object getModifyObject() {
    	if(modifyObject == null){
    		return "";
    	}
        return modifyObject;
    }

    public void setModifyObject(Object modifyObject) {
        this.modifyObject = modifyObject;
    }

    public Object getSupplementaryObject() {
    	if(supplementaryObject == null){
    		return "";
    	}
        return supplementaryObject;
    }

    public void setSupplementaryObject(Object supplementaryObject) {
        this.supplementaryObject = supplementaryObject;
    }

    public String getPublishDate() {
        return DateFormatUtil.date2Str(publishDate);
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getImplementationDate() {
        return DateFormatUtil.date2Str(implementationDate);

    }

    public void setImplementationDate(Date implementationDate) {
        this.implementationDate = implementationDate;
    }

    public String getConfirmDate() {
        return DateFormatUtil.date2Str(confirmDate);
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getEndDate() {
        return DateFormatUtil.date2Str(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getInvalidDate() {
        return DateFormatUtil.date2Str(invalidDate);
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    public Object getBelongToUnit() {
    	if(belongToUnit == null){
    		return "";
    	}
        return belongToUnit;
    }

    public void setBelongToUnit(Object belongToUnit) {
        this.belongToUnit = belongToUnit;
    }

    public Object getDraftUnit() {
    	if(draftUnit == null){
    		return "";
    	}
        return draftUnit;
    }

    public void setDraftUnit(Object draftUnit) {
        this.draftUnit = draftUnit;
    }

    public Object getStandardType() {
    	if(standardType == null){
    		return "";
    	}
        return standardType;
    }

    public void setStandardType(Object standardType) {
        this.standardType = standardType;
    }

    public Object getStandardLevel2() {
    	if(standardLevel2 == null){
    		return "";
    	}
        return standardLevel2;
    }

    public void setStandardLevel2(Object standardLevel2) {
        this.standardLevel2 = standardLevel2;
    }

    public Object getThemeCnName() {
    	if(themeCnName == null){
    		return "";
    	}
        return themeCnName;
    }

    public void setThemeCnName(Object themeCnName) {
        this.themeCnName = themeCnName;
    }

    public Object getThemeEnName() {
    	if(themeEnName == null){
    		return "";
    	}
        return themeEnName;
    }

    public void setThemeEnName(Object themeEnName) {
        this.themeEnName = themeEnName;
    }

    public Object getFreeCnName() {
    	if(freeCnName == null){
    		return "";
    	}
        return freeCnName;
    }

    public void setFreeCnName(Object freeCnName) {
        this.freeCnName = freeCnName;
    }

    public String getLanguage() {
    	if(this.language != null){
    		if(this.language == 1){
        		return "中文";
        	}else if(this.language == 2){
        		return "英文";
        	}else if(this.language == 3){
        		return "其他";
        	}
    	}
        return "";
    }

    public void setLanguage(Short language) {
        this.language = language;
    }

    public Object getCountry() {
    	if(country == null){
    		return "";
    	}
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getInternationClass() {
    	if(internationClass == null){
    		return "";
    	}
        return internationClass;
    }

    public void setInternationClass(Object internationClass) {
        this.internationClass = internationClass;
    }

    public Object getChineseClass() {
    	if(chineseClass == null){
    		return "";
    	}
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

    public String getProjectStartDate() {
        return DateFormatUtil.date2Str(projectStartDate);
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Object getTechnicalField() {
    	if(technicalField == null){
    		return "";
    	}
        return technicalField;
    }

    public void setTechnicalField(Object technicalField) {
        this.technicalField = technicalField;
    }

    public Object getDraftStaff() {
    	if(draftStaff == null){
    		return "";
    	}
        return draftStaff;
    }

    public void setDraftStaff(Object draftStaff) {
        this.draftStaff = draftStaff;
    }

    public Object getContactWay() {
    	if(contactWay == null){
    		return "";
    	}
        return contactWay;
    }

    public void setContactWay(Object contactWay) {
        this.contactWay = contactWay;
    }

    public Object getJurisdiction() {
    	if(jurisdiction == null){
    		return "";
    	}
        return jurisdiction;
    }

    public void setJurisdiction(Object jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Object getTranslation() {
    	if(translation == null){
    		return "";
    	}
        return translation;
    }

    public void setTranslation(Object translation) {
        this.translation = translation;
    }

    public Object getUdc() {
    	if(udc == null){
    		return "";
    	}
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
    	if(publisher == null){
    		return "";
    	}
        return publisher;
    }

    public void setPublisher(Object publisher) {
        this.publisher = publisher;
    }

    public String getPubDate() {
        return DateFormatUtil.date2Str(pubDate);
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Object getIsbn() {
    	if(isbn == null){
    		return "";
    	}
        return isbn;
    }

    public void setIsbn(Object isbn) {
        this.isbn = isbn;
    }

    public Object getDigest() {
    	if(digest == null){
    		return "";
    	}
		return digest;
	}

	public void setDigest(Object digest) {
		this.digest = digest;
	}

	public String getStandardStatus() {
		if(this.standardStatus != null){
			if(this.standardStatus == 1){
				return "现行";
			}else if(this.standardStatus == 2){
				return "暂行";
			}else if(this.standardStatus == 3){
				return "废止";
			}
		}
		return "";
	}

    public void setStandardStatus(Short standardStatus) {
        this.standardStatus = standardStatus;
    }

    public String getLastOperDate() {
        return DateFormatUtil.date2Str(lastOperDate);
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
    	if(lastOperUser == null){
    		return "";
    	}
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

	public String getArchNodeName() {
		return archNodeName;
	}

	public void setArchNodeName(String archNodeName) {
		this.archNodeName = archNodeName;
	}

	@Override
	public String toString() {
		return "StandardVo [standardId=" + standardId + ", standardNo=" + standardNo + ", standardCnName="
				+ standardCnName + ", standardEnName=" + standardEnName + ", standardFileName=" + standardFileName
				+ ", pubOrg=" + pubOrg + ", adoptRelation=" + adoptRelation + ", replaceStandard=" + replaceStandard
				+ ", replacedStandard=" + replacedStandard + ", quoteStandard=" + quoteStandard + ", modifyObject="
				+ modifyObject + ", supplementaryObject=" + supplementaryObject + ", publishDate=" + publishDate
				+ ", implementationDate=" + implementationDate + ", confirmDate=" + confirmDate + ", endDate=" + endDate
				+ ", invalidDate=" + invalidDate + ", belongToUnit=" + belongToUnit + ", draftUnit=" + draftUnit
				+ ", standardType=" + standardType + ", standardLevel2=" + standardLevel2 + ", themeCnName="
				+ themeCnName + ", themeEnName=" + themeEnName + ", freeCnName=" + freeCnName + ", language=" + language
				+ ", country=" + country + ", internationClass=" + internationClass + ", chineseClass=" + chineseClass
				+ ", standardLevel=" + standardLevel + ", projectStartDate=" + projectStartDate + ", technicalField="
				+ technicalField + ", draftStaff=" + draftStaff + ", contactWay=" + contactWay + ", jurisdiction="
				+ jurisdiction + ", translation=" + translation + ", udc=" + udc + ", pageNumber=" + pageNumber
				+ ", publisher=" + publisher + ", pubDate=" + pubDate + ", isbn=" + isbn + ", digest=" + digest
				+ ", standardStatus=" + standardStatus + ", nodeCode=" + nodeCode + ", lastOperDate=" + lastOperDate
				+ ", lastOperType=" + lastOperType + ", lastOperUser=" + lastOperUser + ", uploadUnit=" + uploadUnit
				+ ", archNodeName=" + archNodeName + "]";
	}
	
}