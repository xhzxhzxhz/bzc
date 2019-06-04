package com.folkestone.bzcx.mapper.user;

import com.folkestone.bzcx.bean.bean_do.user.U_Query_HistoryDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;

import java.util.List;

public interface UQueryHistoryMapper {
	/**
	 * 插入一条检索历史
	 * @param queryHistoryDo
	 * @return
	 */
	int insert(U_Query_HistoryDo queryHistoryDo);
	
	int update(U_Query_HistoryDo queryHistoryDo);

	/**
	 * 根据条件查询多条检索历史
	 * @param query
	 * @return
	 */
	List<String> listHistoryByQuery(Query query);

	
	/**
	 * 热门
	 * @param query
	 * @return
	 */
	List<String> listHotByQuery(Query query);
	
	/**
	 * 查询检索历史和题录表中和关键词类似的词汇
	 * @param keyword
	 * @return
	 */
	List<String> listRelationWord(String keyword);
}