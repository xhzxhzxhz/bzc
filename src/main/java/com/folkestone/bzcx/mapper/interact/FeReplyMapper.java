package com.folkestone.bzcx.mapper.interact;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.interact.Fe_ReplyDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.FeReplyVo;

/**
 * 意见反馈回复mapper
 * @author Administrator
 *
 */
public interface FeReplyMapper {
    /**
     * 保存意见反馈回复信息
     * @param replyDo
     * @return
     */
	int insertReply(Fe_ReplyDo replyDo);

	/**
	 * 查询意见反馈回复信息列表
	 * @param query
	 * @return
	 */
	List<FeReplyVo> listReplyByQuery(Query query);

	/**
	 * 查询意见反馈回复id，意见id，回复人id，被回复人id
	 * @param replyId
	 * @return
	 */
	List<FeReplyVo> getReplyById(Fe_ReplyDo replyDo);
	
	/**
	 * 根据反馈人ID查询回复表对象
	 * @param feedbackId
	 * @return
	 */
	List<FeReplyVo> selectReceiverUserByFeedbackId(String feedbackId);
    
	/**
	 * 
	 * query参数的集合回复信息
	 * @param query
	 * @return
	 */
	List<FeReplyVo> listqueryByFeedbackId(Query query);
	
	
	/**
	 * 根据feedbackid查询一条回复
	 * @param feedbackId
	 * @return
	 */
	FeReplyVo justOneReply(String feedbackId);
}