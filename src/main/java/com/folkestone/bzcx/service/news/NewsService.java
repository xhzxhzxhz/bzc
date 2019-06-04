
package com.folkestone.bzcx.service.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.news.N_NewsDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.news.NewsVo;
import com.folkestone.bzcx.mapper.news.NewsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Describe：newsService
 * 
 * @author smallking
 *
 *  2017年11月3日
 */
@Service
public class NewsService {

	@Autowired
	private NewsMapper newsMapper;
	/**
	 * 插入咨询
	 * @param newsDo
	 * @return
	 */
	public int insert(N_NewsDo newsDo) {
		return newsMapper.insert(newsDo);
	}
	/**
	 * 根据条件查询咨询集合(状态不用转换)
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listNewsByQuery(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		List<NewsVo> newsList = newsMapper.listNewsByQuery(query);
		PageInfo<NewsVo> pageInfo = new PageInfo<NewsVo>(newsList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	
	public EasyUISeparatPage listNewsBySthing(Query query){
		PageHelper.startPage(query.getPage(), query.getRows());
		List<NewsVo> newsList = newsMapper.listNewsBySthing(query);
		PageInfo<NewsVo> pageInfo = new PageInfo<NewsVo>(newsList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
public EasyUISeparatPage listPicture(Query query){
	PageHelper.startPage(query.getPage(), query.getRows());
	List<NewsVo> newsList = newsMapper.listPicture(query);
	PageInfo<NewsVo> pageInfo = new PageInfo<NewsVo>(newsList);
	EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
	return data;
	}
	
	/**
	 * 根据Id批量删除
	 * @param newsIds
	 */
	public void deleteByIds(String[] newsIds) {
		for(String newsId : newsIds){
			deleteById(newsId);
		}
	}
	/**
	 * 根据ID删除单个
	 * @param newsId
	 */
	private int deleteById(String newsId) {
		return newsMapper.deleteById(newsId);
	}
	
	/**
	 * 查询一条资讯详情
	 * @param newsId
	 * @return
	 */
	public NewsVo getNewsById(String newsId) {
		return newsMapper.getNewsById(newsId);
	}
	/**
	 * @param newsDo
	 * @return
	 */
	public int update(N_NewsDo newsDo) {
		return newsMapper.update(newsDo);
	}
	
	/**
	 * 
	 * 下一条
	 * @param newsid
	 * @return
	 */
	public NewsVo downNews(String newsid){
		return newsMapper.downNews(newsid);
	}
	/**
	 * 
	 * 上一条
	 * @param newsid
	 * @return
	 */
	public NewsVo upNews(String newsid){
		return newsMapper.upNews(newsid);
	}
}
