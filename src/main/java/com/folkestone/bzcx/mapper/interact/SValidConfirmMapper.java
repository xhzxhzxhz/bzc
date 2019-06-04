package com.folkestone.bzcx.mapper.interact;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.interact.S_Valid_ConfirmDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.ValidConfirmVo;


/**
 * 标准标准意见反馈Mapper
 * @author Administrator
 *
 */

public interface SValidConfirmMapper {

	/**
	 * 标准意见反馈单的删除
	 * @param validConfirmId
	 */
	void deleteByIds(String validConfirmId);
	
	int deleteBystanIds(String standardId);

	/**
	 * 标准意见反馈单列表
	 * @param query
	 * @return
	 */
	List<ValidConfirmVo> listByQuery(Query query);

	/**
	 * 标准意见反馈单的查看
	 * @param validConfirmDo
	 * @return
	 */
	int updateByValidConfirmId(S_Valid_ConfirmDo validConfirmDo);
	
	/**
	 * 标准意见反馈单提交
	 * @author Administrator
	 *
	 */
	int insert(S_Valid_ConfirmDo valid);
	
	
	List<ValidConfirmVo> OneByValid(Query query);
}