package com.folkestone.bzcx.mapper.dm;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.dm.A_Laws_NodeDo;
import com.folkestone.bzcx.bean.bean_do.dm.L_LawsDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsNodeVo;

public interface LawsNodeMapper {
	/**
	 * 通过条件进行查询
	 * @param query1
	 * @return
	 */
	List<LawsNodeVo> listByList(Query query1);
	/**
	 * 添加
	 * @param lawsNodeDo
	 */
	void insert(A_Laws_NodeDo lawsNodeDo);
	/**
	 * 删除
	 * @param query
	 * @return
	 */
	int delNodeCode(Query query);
	/**
	 * 修改题录
	 * @param lawsDo
	 * @return
	 */
	int update(L_LawsDo lawsDo);
	/**
	 * 通过法律法规去获取相对应的节点
	 * @param lawsId
	 * @return
	 */
	String getNodeCode(String lawsId);
		
	

}
