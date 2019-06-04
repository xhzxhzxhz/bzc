package com.folkestone.bzcx.mapper.interact;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.folkestone.bzcx.bean.bean_do.interact.Fe_FeedbackDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.FeFeedbackVo;

public interface FeFeedbackMapper {
	/**
	 * 保存反馈信息
	 * @param feedbackDo
	 * @return
	 */
	int insert(Fe_FeedbackDo feedbackDo);
	/**
	 * 根据反馈id删除反馈
	 * @param feedbackId
	 * @return
	 */
	int deleteByFeedbackId(String feedbackId);

	
	/**
	 * 意见反馈信息列表
	 * @param query
	 * @return
	 */
	List<FeFeedbackVo> listFeedbackByQuery(Query query);

	/**
	 * 查询单条反馈信息详情
	 * @param feedbackId
	 * @return
	 */
	FeFeedbackVo getFeedbackById(String feedbackId);

	/**
	 * 搜索反馈信息
	 * @param keyword
	 * @return
	 */
	List<FeFeedbackVo> selectFeedback(String keyword);

	/**
	 * 意见反馈的修改（将未读状态标记为已读状态,将反馈状态修改为已删除）
	 * @param feedbackDo
	 * @return
	 */
	int update(Fe_FeedbackDo feedbackDo);

	
	FeFeedbackVo firstLogin(@Param("feedbackId")String feedbackId,@Param("userId")String userId);
	
	String getFeedabckuserid(String Userid);
}