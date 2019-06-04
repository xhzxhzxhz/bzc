package com.folkestone.bzcx.controller.front.department;

import java.io.IOException;
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

import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.DepartmentVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.user.DepartmentService;

/**
 * Describe：处理前台二级单位的controller
 * @author smallking
 * 2017年11月18日
 */
@Controller
@RequestMapping(value="/department")
public class SecondUnitController extends BaseController{

	@Autowired
	private DepartmentService departmentService;
	
	@ResponseBody
	@RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	public Result login(String departid,String account,String password,HttpServletRequest req){
		Result re = new Result(false);
		Query query = new Query();
		query.put("id", departid);
		query.put("account", account);
		DepartmentVo logindepartment = departmentService.logindepartment(query);
		if(null != logindepartment) {
			query.put("password", password);
			DepartmentVo department = departmentService.logindepartment(query);
			if(null != department) {
				re.setResult(true);
			}else {
				re.setCode(202);
			}
		}else {
			//201无效用户名，202.密码错误
			re.setCode(201);
		}
		return re;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/parent", method = {RequestMethod.POST})
	public Result secondUnitList(HttpServletRequest req){
		Result res = new Result(false);
		DepartmentVo parentestDepartment = departmentService.getParentestDepartment(req);
		if(parentestDepartment != null) {
			res.setData(parentestDepartment);
			res.setResult(true);
		}
		return res;
	}
	
	/**
	 * 查出所有的二级单位
	 * @param resp
	 * @param order
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/second/unit", method = {RequestMethod.POST})
	public List<DepartmentVo> secondUnitList(HttpServletResponse resp, HttpServletRequest req,
			@RequestParam(value="order", defaultValue="DEPARTMENT_NAME")String order) throws IOException{
		if(order.equals("DEPARTMENT_NAME")){
			return departmentService.secondUnitList(order,req);
		}
		if(order.equals("standardCount")){
			return departmentService.secondUnitOrderByStandardCount(req);
		}
		if(order.equals("active")){
			// 按照活跃度排序
			return departmentService.secondUnitOrderByActive();
		}
		return null;
	}
	/**
	 * 查询部门 
	 * @param type user：查询当前登录人的部门
	 * 			    list：查询部门列表，最大的部门放在上面并且其他的按照活跃度来排行
	 * 				max：只查询最父级部门
	 * @param architectureId ：部门对应的体系ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = {RequestMethod.POST})
	public Result getDepartmentById(HttpServletRequest req, String type, String architectureId){
		Result result = new Result(true);
		if(architectureId != null){
			DepartmentVo departmentVo = departmentService.getDepartmentByArchitectureId(architectureId);
			result.setData(departmentVo);
			return result;
		}
		if(type.equals("list")){
			List<DepartmentVo> list = departmentService.secondUnitList(null,req);
			DepartmentVo parentestDepartment = departmentService.getParentestDepartment(req);
			list.add(0, parentestDepartment);
			result.setData(list);
			return result;
		}
		if(type.equals("max")){
			DepartmentVo parentestDepartment = departmentService.getParentestDepartment(req); 
			result.setData(parentestDepartment);
			return result;
		}
		if(type.equals("user")){
			UserVo user = super.getUser(req);
			String departmentId = user.getDepartmentId();
			DepartmentVo departmentVo = departmentService.getDepartmentById(departmentId);
			result.setData(departmentVo);
			return result;
		}
		return null;
	}
}
