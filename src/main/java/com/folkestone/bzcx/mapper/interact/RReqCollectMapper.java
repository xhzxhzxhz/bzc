package com.folkestone.bzcx.mapper.interact;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.interact.R_Req_CollectDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.ReqCollectVo;


public interface RReqCollectMapper {
	
	
	
	/**
	 * 前台显示
	 * @param reqId
	 * @return
	 */
	int changeShow(String reqId);

	/**
	 * 需求征集的删除
	 * @param reqId
	 * @return
	 */
	int delete(String reqId);

	/**
	 * 保存需求征集
	 * @param collectDo
	 * @return
	 */
	int insert(R_Req_CollectDo collectDo);

	/**
	 * 需求征集列表
	 * @param query
	 * @return
	 */
	List<ReqCollectVo> listCollectByQuery(Query query);

	/**
	 * 需求征集的審核
	 * @param collectDo
	 * @return
	 */
	int update(R_Req_CollectDo collectDo);

	/**
	 * 根据需求征集id修改需求征集的状态
	 * @param collectDo
	 * @return
	 */
	int updateByReqId(R_Req_CollectDo collectDo);

	/**
	 * 根据关键字对需求征集的搜索
	 * @param keyword
	 * @return
	 */
	List<R_Req_CollectDo> selectReqCollec(String keyword);
  
}