package com.folkestone.bzcx.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.user.R_DepartmentDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUITree;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.DepartmentVo;
import com.folkestone.bzcx.bean.bean_vo.user.OperLogVo;
import com.folkestone.bzcx.common.util.DateFormatUtil;
import com.folkestone.bzcx.common.util.JurisdictionUtil;
import com.folkestone.bzcx.mapper.user.RDepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class DepartmentService {
	@Autowired
	private RDepartmentMapper departmentMapper;
	
	
	public DepartmentVo logindepartment(Query query) {
		return departmentMapper.logindepartment(query);
	}
	
	/**
	 * 根据Query条件查询多条日志
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage getDepartment(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<DepartmentVo> department = departmentMapper.getDepartment(query);
		// 得到结果
		PageInfo<DepartmentVo> pageInfo = new PageInfo<DepartmentVo>(department);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	
	/**
	 * 根据题录标准id查询部门
	 * @param StandardId
	 * @return
	 */
	public DepartmentVo getDepartmentByStandard (String StandardId) {
		return departmentMapper.getDepartmentByStandard(StandardId);
	}
	/**
	 * 插入一个部门
	 * @param department
	 * @return
	 */
	public int insert(R_DepartmentDo department){
		return departmentMapper.insert(department);
	}
	/**
	 * 删除一个部门 及其子部门
	 * @param departmentId
	 * @return
	 */
	public int deleteById(String departmentId) {
		int row = departmentMapper.deleteById(departmentId);
		return row;
	}
	/**
	 * 编辑部门
	 * @param department
	 * @return
	 */
	public int update(R_DepartmentDo department) {
		return departmentMapper.update(department);
	}
	/**
	 * 根据ID查询一个部门
	 * @param departmentId
	 * @return
	 */
	public DepartmentVo getDepartmentById(String departmentId) {
		return departmentMapper.getDepartmentById(departmentId);
	}
	/**
	 * 查询最父级部门
	 * @return
	 */
	public DepartmentVo getParentestDepartment(HttpServletRequest req) {
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		query.put("parentId", "0");
		return departmentMapper.listDepartmentByQuery(query).get(0);
	}
	/**
	 * 查询所给的parentId的直接子级
	 * @param parentId
	 * @return
	 */
	public List<DepartmentVo> listDepartmentByParentId(String parentId){
		Query query = new Query();
		query.put("parentId", parentId);
		List<DepartmentVo> departmentList = departmentMapper.listDepartmentByQuery(query);
		return departmentList;
	}
	/**
	 * 查询部门树
	 * @return
	 */
	public List<EasyUITree> getDepartmentTree() {
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		// 先查出最父级部门
		List<DepartmentVo> departmentList = listDepartmentByParentId("0");
		for(DepartmentVo departmentVo : departmentList){
			EasyUITree tree = new EasyUITree();
			tree.setId(departmentVo.getId() + "");
			tree.setText(departmentVo.getDepartmentName());
			recursive(tree);
			treeList.add(tree);
		}
		return treeList;
	}
	/**
	 * 递归拼接树
	 * @param tree
	 * @param query
	 */
	public void recursive(EasyUITree tree){
		List<DepartmentVo> departmentList = listDepartmentByParentId(tree.getId());
		List<EasyUITree> child = new ArrayList<EasyUITree>();
		if(departmentList != null && departmentList.size() >= 0)
		{
			for(DepartmentVo department : departmentList)
			{
				EasyUITree childTree = new EasyUITree();
				childTree.setId(department.getId() + "");
				childTree.setText(department.getDepartmentName());
				child.add(childTree);
				recursive(childTree);
			}
			if(child.size() >= 1){
				tree.setChildren(child);
			}
		}
	}
	/**
	 * 查出所有的二级单位
	 * @return
	 */
	public List<DepartmentVo> secondUnitList(String order,HttpServletRequest req) {
		Query query = new Query();
		query.put("order", order);
		query.put("sort", "desc");
		DepartmentVo departmentVo = getParentestDepartment(req);
		query.put("parentId", departmentVo.getId());
		return departmentMapper.listDepartmentByQuery(query);
	}
	/**
	 * 查询二级单位，根据其下拥有的标准数量排序
	 * @return
	 */
	public List<DepartmentVo> secondUnitOrderByStandardCount(HttpServletRequest req) {
		DepartmentVo departmentVo = getParentestDepartment(req);
		return departmentMapper.secondUnitOrderByStandardCount(departmentVo.getId());
	}
	/**
	 * 按照二级单位活跃度有高到底查询二级单位列表
	 * @return
	 */
	public List<DepartmentVo> secondUnitOrderByActive() {
		Query query = new Query();
		Date startTime = new Date();
		Date endTime = DateFormatUtil.subMonth(startTime, -1);
		query.put("endTime", DateFormatUtil.date2Str(endTime));
		query.put("startTime", DateFormatUtil.date2Str(startTime));
		return departmentMapper.secondUnitOrderByActive(query);
	}
	/**
	 * 根据体系的ID查询部门
	 * @param architectureId
	 * @return
	 */
	public DepartmentVo getDepartmentByArchitectureId(String architectureId) {
		return departmentMapper.getDepartmentByArchitectureId(architectureId);
	}
	
}
