package com.folkestone.bzcx.controller.admin.user;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.user.R_DepartmentDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUITree;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.DepartmentVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.user.DepartmentService;

/**
 * Describe：处理和部门相关的controller
 * 
 * @author smallking
 *
 *  2017年11月18日
 */
@Controller
@RequestMapping("/admin/department")
public class DepartmentController extends BaseController{
	@Autowired
	private DepartmentService departmentService;
	
	

	@ResponseBody
	@RequestMapping(value = "/list", method = {RequestMethod.POST})
	public EasyUISeparatPage listDepartmentByQuery(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage data = departmentService.getDepartment(query);
		return data;
	}
	
	/**
	 * 递归得到树json
	 * @param userId 编辑用户的时候 需要知道树节点那个是被默认选中的
	 * @return 
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/tree", method = {RequestMethod.POST})
	public List<EasyUITree> documentTree(HttpServletResponse resp, String userId, HttpServletRequest req ) throws IOException{
		UserVo user = (UserVo)req.getSession().getAttribute("loginUser");
		if(user.getRoleName().equals("标准管理员")) {
			DepartmentVo departmentVo = departmentService.getDepartmentById(user.getDepartmentId());
			List<EasyUITree> treeList = new ArrayList<EasyUITree>();
			EasyUITree tree = new EasyUITree();
			tree.setId(departmentVo.getId() + "");
			tree.setText(departmentVo.getDepartmentName());
			treeList.add(tree);
			return treeList;
		}else {
			List<EasyUITree> treeList = departmentService.getDepartmentTree();
			return treeList;
		}
	}
	/**
	 * 查询一个部门的详情
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = {RequestMethod.POST})
	public Result detail(String departmentId){
		Result result = new Result(true);
		DepartmentVo departmentVo =  departmentService.getDepartmentById(departmentId);
		result.setData(departmentVo);
		return result;
	}
	/**
	 * 插入一个部门
	 * @param department
	 * @param nodeId
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method = {RequestMethod.POST})
	public Result insert(R_DepartmentDo department, HttpServletRequest req){
		Result result = new Result(false);
		try
		{
			// 先查出待新增体系的父节点的path
			if(department.getParentId().equals("0")){
				department.setPath(department.getId());
			}else{
				DepartmentVo departmentVo = departmentService.getDepartmentById(department.getParentId());
				department.setPath(departmentVo.getPath() + "," + department.getId());
			}
			department.setCreateTime(new Date());
			int row = departmentService.insert(department);
			if(row == 1)
			{
				result.setResult(true);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除部门
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = {RequestMethod.POST})
	public Result delete(String departmentId){
		Result result = new Result(false);
		try
		{
			int row = departmentService.deleteById(departmentId);
			if(row >= 1)
			{
				result.setResult(true);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 编辑部门
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = {RequestMethod.POST})
	public Result update(R_DepartmentDo department, HttpServletRequest req){
		Result result = new Result(false);
		try {
			int row = departmentService.update(department);
			if(row >= 1)
			{
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
