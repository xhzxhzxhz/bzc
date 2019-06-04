package com.folkestone.bzcx.service.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.user.U_Query_HistoryDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.mapper.user.UQueryHistoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class QueryHistoryService {
	@Autowired
	private UQueryHistoryMapper queryHistoryMapper;
	
	public int insert(U_Query_HistoryDo queryHistoryDo){
		return queryHistoryMapper.insert(queryHistoryDo);
	}
	
	public int update(U_Query_HistoryDo queryHistoryDo){
		return queryHistoryMapper.update(queryHistoryDo);
	}

	/**
	 * 根据moudle user等参数查询检索历史
	 * @param query
	 * @return
	 */
	public List<String> listHistoryByQuery(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		List<String> historyList = queryHistoryMapper.listHistoryByQuery(query);
		PageInfo<String> pageInfo = new PageInfo<String>(historyList);
		return pageInfo.getList();
	}
	
	/**
	 * 根据moudle user等参数查询检索历史
	 * @param query
	 * @return
	 */
	public List<String> listHotByQuery(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		List<String> historyList = queryHistoryMapper.listHotByQuery(query);
		PageInfo<String> pageInfo = new PageInfo<String>(historyList);
		return pageInfo.getList();
	}

	/**
	 * 查询全文检索界面上显示的相关搜索和为你推荐的词汇
	 * @param keyword
	 * @return
	 */
	public List<String> listRelationWord(String keyword) {
		return queryHistoryMapper.listRelationWord(keyword);
	}
}
