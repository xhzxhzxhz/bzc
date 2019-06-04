package com.folkestone.bzcx.service.interact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.interact.Fe_FeedbackDo;
import com.folkestone.bzcx.bean.bean_do.interact.Fe_ReplyDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.FeFeedbackVo;
import com.folkestone.bzcx.bean.bean_vo.interact.FeReplyVo;
import com.folkestone.bzcx.mapper.interact.FeFeedbackMapper;
import com.folkestone.bzcx.mapper.interact.FeReplyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 *  后台互动维护service
 * @author Administrator
 *  
 */
@Service
public class InteractService {

	@Autowired
	private FeFeedbackMapper feFeedbackMapper;
	@Autowired
	private FeReplyMapper feReplyMapper;
	
	
	/*public int headShow(String[] feedbackIds){
		int i = 0;
		for (String feedbackId : feedbackIds) {
			Fe_FeedbackDo fedo = new Fe_FeedbackDo();
			fedo.setFeedbackId(feedbackId);
			fedo.setShow(Short.valueOf("1"));
			feFeedbackMapper.update(fedo);
			i++;
		}
		return i;
	}*/
	/**
	 * 保存意见反馈信息
	 * @param feedbackDo
	 * @return
	 */
	public  int insert(Fe_FeedbackDo feedbackDo) {
		
		return feFeedbackMapper.insert(feedbackDo);
	}
	
   /**
    * 保存意见反馈回复信息
    * @param replyDo
    * @return
    */
	public int insertReply(Fe_ReplyDo replyDo) {
		
		return feReplyMapper.insertReply(replyDo);
	}
	
	/**
	 * 根据反馈id删除反馈
	 * @param id  反馈id
	 * @return
	 */
	public  int deleteInteract(String id) {
		
		return feFeedbackMapper.deleteByFeedbackId(id);
	}
	
	/**
	 * 查询单条意见反馈回复详情
	 * @param feedbackId
	 * @return
	 */
	public FeFeedbackVo getFeedbackById(String feedbackId) {
		return feFeedbackMapper.getFeedbackById(feedbackId);
	}
	

	/**
	 * 查询意见反馈信息列表
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listFeedbackByQuery(Query query) {
		Object ob = query.get("page");
		Integer rows = query.getRows();
		if(ob == null){
			ob = "1";
		}
		Integer page = new Integer(ob.toString());
		// 设置分页参数
		PageHelper.startPage(page, rows);
		// 执行查询
		List<FeFeedbackVo> listFeedbackByQuery = feFeedbackMapper.listFeedbackByQuery(query);
		// 得到结果
		PageInfo<FeFeedbackVo> pageInfo = new PageInfo<FeFeedbackVo>(listFeedbackByQuery);
		EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return result;
	}

	/**
	 * 查询反馈回复列表
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listReplyByQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(1, 10);
		// 执行查询
		List<FeReplyVo> listReplyByQuery = feReplyMapper.listReplyByQuery(query);
		// 得到结果
		PageInfo<FeReplyVo> pageInfo = new PageInfo<FeReplyVo>(listReplyByQuery);
		EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return result;
	}

	/**
	 * 查询条反馈回复信息
	 * @param replyId
	 * @return
	 */
	public EasyUISeparatPage listReplyByQueryOrder(Query query) {
		//getReplyById原来名字
		// 设置分页参数
				PageHelper.startPage(1, 10);
				// 执行查询
				List<FeReplyVo> listFeedbackByQuery = feReplyMapper.listqueryByFeedbackId(query);
				// 得到结果
				PageInfo<FeReplyVo> pageInfo = new PageInfo<FeReplyVo>(listFeedbackByQuery);
				EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
				return result;
	}

	/**
	 * 搜索反馈信息
	 * @param keywords
	 * @return
	 */
	public List<FeFeedbackVo> selectFeedback(String keyword) {
	
		return  feFeedbackMapper.selectFeedback(keyword);
	}

	/**
	 * 根据反馈id批量删除反馈信息
	 * @param feedbackIds
	 */
	public void deleteByIds(String[] feedbackIds) {
		for (String feedbackId : feedbackIds) {
			feFeedbackMapper.deleteByFeedbackId(feedbackId);
		}
	}

	/**
	 * 根据反馈ID查询回复表对象
	 * @param feedbackId
	 * @return
	 */
	public List<FeReplyVo> selectReceiverUserByFeedbackId(String feedbackId) {
		
		return feReplyMapper.selectReceiverUserByFeedbackId(feedbackId);
	}

	/**
	 * 意见反馈的修改（将未读状态标记为已读状态）
	 * @param feedbackDo
	 * @return
	 */
	public int update(Fe_FeedbackDo feedbackDo) {
		
		
		return feFeedbackMapper.update(feedbackDo);
	}
	
	public FeFeedbackVo firstLogin(String feedback_id,String userId){
		return feFeedbackMapper.firstLogin(feedback_id, userId);
	}
	
	/**
	 * 查询意见反馈回复id，意见id，回复人id，被回复人id
	 * @param replyId
	 * @return
	 */
	public List<FeReplyVo> selectFereply(Fe_ReplyDo replyDo){
		return feReplyMapper.getReplyById(replyDo);
	}
	
	public String getFeedabckuserid(String Userid){
		return feFeedbackMapper.getFeedabckuserid(Userid);
	}
	
	public FeReplyVo justOneReply(String feedbackid){
		return feReplyMapper.justOneReply(feedbackid);
	}
}
