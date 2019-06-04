package com.folkestone.bzcx.mapper.user;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.user.R_DepartmentDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.DepartmentVo;

public interface RDepartmentMapper {
	
	
	DepartmentVo logindepartment(Query query);
	
	/**
	 * 查询部门
	 * @return
	 */
	List<DepartmentVo> getDepartment(Query query);
	/**
	 * 根据题录标准id，查询部门
	 * @param department
	 * @return
	 */
	DepartmentVo getDepartmentByStandard(String StandardId);
	
	/**
	 * 新增一个部门
	 * @param department
	 * @return
	 */
	int insert(R_DepartmentDo department);
	/**
	 * 根据父级部门 查询下一级子部门
	 * @param query
	 * @return
	 */
	List<DepartmentVo> listDepartmentByQuery(Query query);
	/**
	 * 编辑部门
	 * @param department
	 * @return
	 */
	int update(R_DepartmentDo department);
	/**
	 * 根据ID查询一个部门
	 * @return
	 */
	DepartmentVo getDepartmentById(String departmentId);
	/**
	 * 查询一个部门的领导
	 * @param departmentId
	 * @return
	 */
	List<String> listDepartmentLeader(Query query);
	/**
	 * @param departmentId
	 * @return
	 */
	int deleteById(String departmentId);
	/**
	 * 查询部门，根据其下标准数量排序
	 * @return
	 */
	List<DepartmentVo> secondUnitOrderByStandardCount(String parentId);
	/**
	 * 查询部门，根据其活跃度排序
	 * @param query 
	 * @return
	 */
	List<DepartmentVo> secondUnitOrderByActive(Query query);
	/**
	 * 根据体系ID查询部门
	 * @param architectureId
	 * @return
	 */
	DepartmentVo getDepartmentByArchitectureId(String architectureId);

	DepartmentVo getDepartmentByLawsid(String lawsId);
}