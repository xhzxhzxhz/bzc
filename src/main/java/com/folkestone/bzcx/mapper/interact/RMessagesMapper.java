package com.folkestone.bzcx.mapper.interact;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.interact.R_MessagesDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.ReqCollectVo;


/**
 * 需求征集回复
 * @author Administrator
 *
 */
public interface RMessagesMapper {
    /**
     * 保存需求征集回复
     * @param messagesDo
     * @return
     */
	int insert(R_MessagesDo messagesDo);

	/**
	 * 需求征集回复列表
	 * @param query
	 * @return
	 */
	List<ReqCollectVo> replyListCollectByQuerymess(Query query);
  
}