package com.folkestone.bzcx.mapper.news;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.news.N_NewsDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.news.NewsVo;

public interface NewsMapper {
	
	/**
	 * 插入咨询
	 * @param newsDo
	 * @return
	 */
	int insert(N_NewsDo newsDo);
	/**
	 * 根据query查询咨询集合
	 * @param query
	 * @return
	 */
	List<NewsVo> listNewsByQuery(Query query);
	
	/**
	 * 下一条
	 * @param newsid
	 * @return
	 */
	NewsVo downNews(String newsid);
	
	/**
	 * 
	 * 上一条
	 * @param newsid
	 * @return
	 */
	NewsVo upNews(String newsid);
	/**
	 * 条件查询
	 * @param query
	 * @return
	 */
	List<NewsVo> listNewsBySthing(Query query);
	
	/**
	 * 
	 * 用于查询图片
	 * @param query
	 * @return
	 */
	List<NewsVo> listPicture(Query query);
	
	/**
	 * 根据ID删除
	 * @param newsId
	 * @return
	 */
	int deleteById(String newsId);
	/**
	 * 查询一条资讯详情
	 * @param newsId
	 * @return
	 */
	NewsVo getNewsById(String newsId);
	/**
	 * 更新一条资讯
	 * @param newsDo
	 * @return
	 */
	int update(N_NewsDo newsDo);

}