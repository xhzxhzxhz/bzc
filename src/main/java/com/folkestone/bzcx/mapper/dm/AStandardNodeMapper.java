package com.folkestone.bzcx.mapper.dm;


import java.util.List;

import com.folkestone.bzcx.bean.bean_do.dm.A_Standard_NodeDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardNodeVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;

public interface AStandardNodeMapper {

	/**
	 * @param standardNodeDo
	 * @return
	 */
	int insert(A_Standard_NodeDo standardNodeDo);

	/**
	 * 根据nodeId或者standardId删除绑定关系
	 * @param query
	 */
	int deleteByQuery(Query query);

	/**
	 * 根据nodeId或者standardId查询关联关系集合
	 * @param query
	 */
	List<StandardNodeVo> listByQuery(Query query);
	List<StandardNodeVo> listByList(Query query);

	int delNodeCode(Query query);
	
	
	List<String> listArchCount(List<StandardVo> list);
}