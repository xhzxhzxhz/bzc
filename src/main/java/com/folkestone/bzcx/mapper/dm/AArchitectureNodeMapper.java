package com.folkestone.bzcx.mapper.dm;

import com.folkestone.bzcx.bean.bean_do.dm.A_Architecture_NodeDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureNodeVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AArchitectureNodeMapper {

	/**
	 * 插入一个体系节点
	 * @param nodeDo
	 * @return
	 */
	int insert(A_Architecture_NodeDo nodeDo);

	/**
	 * 更新一个体系节点
	 * @param nodeDo
	 * @return
	 */
	int update(A_Architecture_NodeDo nodeDo);

	/**
	 * 根据Query查询多条
	 * @param query
	 * @return
	 */
	List<ArchitectureNodeVo> listNodeByQuery(Query query);

	/**
	 * 根据query多条件删除节点
	 * @param query
	 * @return
	 */
	int deleteNodeByQuery(Query query);

	/**
	 * 根据ID删除体系节点
	 * @param nodeId
	 * @return
	 */
	int deleteNodeById(String nodeId);

	/**
	 * 根据ID查询体系节点
	 * @param nodeId
	 * @return
	 */
	ArchitectureNodeVo getNodeById(String nodeId);
}