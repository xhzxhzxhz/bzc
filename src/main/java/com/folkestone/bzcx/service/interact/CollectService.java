package com.folkestone.bzcx.service.interact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.interact.R_MessagesDo;
import com.folkestone.bzcx.bean.bean_do.interact.R_Req_CollectDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.ReqCollectVo;
import com.folkestone.bzcx.mapper.interact.RMessagesMapper;
import com.folkestone.bzcx.mapper.interact.RReqCollectMapper;
import com.folkestone.bzcx.service.dm.ArchitectureNodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class CollectService {

	@Autowired
	private RReqCollectMapper rReqCollectMapper;
	@Autowired
	private RMessagesMapper rMessagesMapper;
	
	/**
	 * 标准提案的删除
	 * @param reqIds
	 */
	public void delete(String[] reqIds) {
		for (String reqId : reqIds) {
			rReqCollectMapper.delete(reqId);
		}
	}

	/**
	 * 保存标准提案
	 * @param collectDo
	 * @return
	 */
	public int insert(R_Req_CollectDo collectDo) {
		
		return rReqCollectMapper.insert(collectDo);
	}

	/**
	 * 保存标准提案回复
	 * @param messagesDo
	 * @return
	 */
	public int insertReply(R_MessagesDo messagesDo) {
		
		return rMessagesMapper.insert(messagesDo);
	}

	/**
	 * 标准提案列表
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listCollectByQuery(Query query) {
		Object ob = query.get("page");
		Integer rows = query.getRows();
		if(ob == null){
			ob = "1";
		}
		Integer page = new Integer(ob.toString());
		// 设置分页参数
		PageHelper.startPage(page, rows);
		// 执行查询
		List<ReqCollectVo> listCollectByQuery = rReqCollectMapper.listCollectByQuery(query);
		// 得到结果
		PageInfo<ReqCollectVo> pageInfo = new PageInfo<ReqCollectVo>(listCollectByQuery);
		EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return result;
        }

	/**
	 * 标准提案回复表列表
	 * @param query
	 * @return
	 */
	/*public EasyUISeparatPage replyListCollectByQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(1, 10);
		// 执行查询
		List<R_MessagesDo> replyListCollectByQuery = rMessagesMapper.replyListCollectByQuery(query);
		// 得到结果
		PageInfo<R_MessagesDo> pageInfo = new PageInfo<R_MessagesDo>(replyListCollectByQuery);
		EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return result;
	  }*/
	
	/**
	 * 标准提案回复列表
	 * 返回list形式
	 */
	public List<ReqCollectVo> replyListCollectByQuerylist(Query query) {
		
		
		return rMessagesMapper.replyListCollectByQuerymess(query);
	}
	
	/**
	 * 标准提案的审核
	 * 其实就是修改标准提案的状态
	 * @param collectDo
	 * @return
	 */
	public int update(R_Req_CollectDo collectDo) {
		
		return rReqCollectMapper.updateByReqId(collectDo);
	   }

	/**
	 * 标准提案的搜索
	 * @param keyword
	 * @return
	 */
	public List<R_Req_CollectDo> collectService(String keyword) {
	
		    return  rReqCollectMapper.selectReqCollec(keyword);
    	}
	
	}
