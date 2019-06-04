package com.folkestone.bzcx.service.dm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.dm.A_Architecture_ManageDo;
import com.folkestone.bzcx.bean.bean_do.dm.A_Architecture_NodeDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureNodeVo;
import com.folkestone.bzcx.bean.bean_vo.user.DepartmentVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.JurisdictionUtil;
import com.folkestone.bzcx.mapper.dm.AArchitectureManageMapper;
import com.folkestone.bzcx.service.user.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Describe：体系管理的service
 * 
 * @author smallking
 *
 *  2017年10月30日
 */
@Service
public class ArchitectureManageService {

	@Autowired
	private AArchitectureManageMapper architectureManageMapper;
	@Autowired
	private ArchitectureNodeService architectureNodeService;
	@Autowired
	private DepartmentService departmentService;
	/**
	 * 插入一条体系
	 * @param architectureDo
	 * @return
	 */
	public int insert(A_Architecture_ManageDo architectureDo) {
		int row = architectureManageMapper.insert(architectureDo);
		return row;
	}
	/**
	 * 查询体系列表
	 * @return
	 */
	public EasyUISeparatPage listManageByQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<ArchitectureManageVo> listManageByQuery = architectureManageMapper.listManageByQuery(query);
		// 得到结果
		PageInfo<ArchitectureManageVo> pageInfo = new PageInfo<ArchitectureManageVo>(listManageByQuery);
		EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return result;
	}
	
	/**
	 * 根据主键查询体系
	 * @param architectureId
	 * @return
	 */
	public ArchitectureManageVo getManageById(String architectureId) {
		return architectureManageMapper.getManageById(architectureId);
	}
	
	/**
	 * 更新一条体系
	 * @param architectureDo
	 * @return
	 */
	public int update(A_Architecture_ManageDo architectureDo,UserVo user) {
		if(architectureDo.getPublishUnit().equals(user.getDepartmentId()) || user.getDepartmentParent().equals("0")) {
			int row = architectureManageMapper.update(architectureDo);
			// 更新成功之后，更新这个体系下的最父级节点
			if(row == 1){
				Query query = new Query();
				query.put("architectureId", architectureDo.getArchitectureId() + "");
				query.put("parentId", "0");
				List<ArchitectureNodeVo> nodeList = architectureNodeService.listNodeByQuery(query);
				ArchitectureNodeVo nodeVo = nodeList.get(0);
				A_Architecture_NodeDo nodeDo = new A_Architecture_NodeDo();
				nodeDo.setNodeId(nodeVo.getNodeId());
				nodeDo.setCreateDate(architectureDo.getCreateDate());
				nodeDo.setNodeCode(architectureDo.getArchitectureCode());
				nodeDo.setNodeCnName(architectureDo.getArchitectureCnName());
				nodeDo.setNodeEnName(architectureDo.getArchitectureEnName());
				return architectureNodeService.update(nodeDo);
			}
			return 0;
		}
		return -1;
	}
	/**
	 * 删除一个标准体系
	 * @param architectureId
	 * @return
	 */
	public int deleteById(String architectureId,UserVo user) {
		ArchitectureManageVo manageById = architectureManageMapper.getManageById(architectureId);
		if(manageById.getPublishUnit().equals(user.getDepartmentId()) || user.getDepartmentParent().equals("0")) {
			int row = architectureManageMapper.deleteManageById(architectureId);
			if(row == 1){
				Query query = new Query();
				query.put("architectureId", architectureId);
				architectureNodeService.deleteNodeByQuery(query);
			}
			return row;
		}
		return -1;
	}
	/**
	 * 根据Ids批量删除 不报错就默认删除成功
	 * @param architectureId
	 * @return
	 */
	public String deleteByIds(String[] architectureIds,UserVo userVo) {
		Map<String, Integer> ma = new HashMap<>();
		ma.put("success",0);
		ma.put("no",0);
		ma.put("fail",0);
		ma.put("suc",0);
		for(String architectureId : architectureIds){
			int deleteById = deleteById(architectureId,userVo);
			if(deleteById > 0) {
				//删除成功
				ma.put("success", (ma.get("success")+1));
			}else if(deleteById == -1) {
				//没有权限
				ma.put("no", (ma.get("no")+1));
			}else {
				//删除失败
				ma.put("fail", (ma.get("fail")+1));
			}
		}
		StringBuffer str = new StringBuffer();
		if(ma.get("success") != 0) {
			str.append("成功删除"+ma.get("success")+"个");
		}
		if(ma.get("fail") != 0) {
			if(ma.get("success") > 0) {
				str.append(",");
			}
			str.append("删除失败"+ma.get("fail")+"个");
		}
		if(ma.get("no") != 0) {
			if(ma.get("success") > 0 || ma.get("fail") > 0) {
				str.append(",");
			}
			str.append(ma.get("no")+"个没有权限删除");
		}
		return str.toString();
	}
	/**
	 * 查询一个部门的体系
	 * @param departmentId
	 * @return
	 */
	public ArchitectureManageVo getManageByDepartment(String departmentId) {
		Query query = new Query();
		query.put("publishUnit", departmentId);
		List<ArchitectureManageVo> manageList = architectureManageMapper.listManageByQuery(query);
		if(manageList.size()>0) {
			return	manageList.get(0);
		}
		return null;
	}
	/**
	 * 管理员查询一个部门的体系
	 * @param departmentId
	 * @return
	 */
	public List<ArchitectureManageVo> getManageByDepartment() {
		Query query = new Query();
		query.put("publishUnit", null);
		List<ArchitectureManageVo> manageList = architectureManageMapper.listManageByQuery(query);
		return manageList;
	}
	/**
	 * 查询一个部门的体系的父级
	 * @param departmentId
	 * @return
	 */
	public ArchitectureManageVo getManageParent(String departmentId,HttpServletRequest req) {
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		query.put("publishUnit", departmentId);
		List<ArchitectureManageVo> manageList = architectureManageMapper.getmanageParend(query);
		if(manageList.size() >= 1){
			return manageList.get(0);
		}
		return null;
	}
	
	/**
	 * 查询最父级部门的体系
	 * @return
	 */
	public ArchitectureManageVo getParentestManage() {
		List<DepartmentVo> departmentList = departmentService.listDepartmentByParentId("0");
		String departmentId = departmentList.get(0).getId();
		ArchitectureManageVo manageVo = getManageByDepartment(departmentId);
		return manageVo;
	}
	/**
	 * 检查当前部门下，是否已经存在体系
	 * @param publishUnit
	 * @return
	 */
	public boolean checkPublishUnit(String publishUnit){
		Query query = new Query();
		query.put("publishUnit", publishUnit);
		List<ArchitectureManageVo> list = architectureManageMapper.listManageByQuery(query);
		if(list.size() >= 1){
			return true;
		}
		return false;
	}
}
