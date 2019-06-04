package com.folkestone.bzcx.mapper.dm;
import com.folkestone.bzcx.bean.bean_do.dm.A_Architecture_ManageDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AArchitectureManageMapper {

	/**
	 * 插入一条体系
	 * @param architectureDo
	 * @return
	 */
	int insert(A_Architecture_ManageDo architectureDo);

	//查询父部门节点的信息
	List<ArchitectureManageVo> getmanageParend(Query query);
	
	/**
	 * 分页查询体系
	 * @return
	 */
	List<ArchitectureManageVo> listManageByQuery(Query query);

	/**
	 * 根据主键ID查询体系
	 * @param architectureId
	 * @return
	 */
	ArchitectureManageVo getManageById(String architectureId);

	/**
	 * 更新体系
	 * @param architectureDo
	 */
	int update(A_Architecture_ManageDo architectureDo);

	/**
	 * 删除体系
	 * @param architectureId 主键ID
	 * @return
	 */
	int deleteManageById(String architectureId);
}