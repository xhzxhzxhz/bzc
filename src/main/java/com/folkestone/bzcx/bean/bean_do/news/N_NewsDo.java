package com.folkestone.bzcx.bean.bean_do.news;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * Describe：新闻类N_News对应的实体
 * 
 * @author smallking
 *
 *  2017年11月3日
 */
public class N_NewsDo {
	/**
	 * 主键ID
	 */
    private String newsId = UUIDUtil.getUUID("News");
    /**
     * 新闻标题
     */
    private Object title;
    /**
     * 新闻作者
     */
    private Object author;
    /**
     * 来源
     */
    private Object newsSource;
    /**
     * 排序码
     */
    private Integer sortCode;
    /**
     * 点击次数
     */
    private Short hits;
    /**
     * 是否热门 0：不是；1：是
     */
    private Short hot;
    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    /**
     * 新闻状态 0:正常； 1：删除
     */
    private Short status;
    /**
     * 轮播图片的预览路径
     */
    private Object imgPath;

    private Object property2;
    /**
     * 新闻类别
     * 1.标准化工作
	 * 2.通知公告
	 * 3.城镇燃气标准动态
	 * 4.中国石油标准动态
	 * 5.国家标准动态
	 * 6.行业标准动态
	 * 7.国外标准动态
	 * 8.其他信息
     */
    private Short newsCate;
    /**
     * 新闻内容
     */
    private String content;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId == null ? null : newsId.trim();
    }

    public Object getTitle() {
        return title;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getAuthor() {
        return author;
    }

    public void setAuthor(Object author) {
        this.author = author;
    }

    public Object getNewsSource() {
        return newsSource;
    }

    public void setNewsSource(Object newsSource) {
        this.newsSource = newsSource;
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public Short getHits() {
        return hits;
    }

    public void setHits(Short hits) {
        this.hits = hits;
    }

    public Short getHot() {
        return hot;
    }

    public void setHot(Short hot) {
        this.hot = hot;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    

    public Object getImgPath() {
		return imgPath;
	}

	public void setImgPath(Object imgPath) {
		this.imgPath = imgPath;
	}

	public Object getProperty2() {
        return property2;
    }

    public void setProperty2(Object property2) {
        this.property2 = property2;
    }

    public Short getNewsCate() {
        return newsCate;
    }

    public void setNewsCate(Short newsCate) {
        this.newsCate = newsCate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}