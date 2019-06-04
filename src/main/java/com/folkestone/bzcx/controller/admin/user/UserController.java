package com.folkestone.bzcx.controller.admin.user;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.user.R_UserDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.user.UserService;

/***
 * 处理和用户信息相关的controller
 * @author smallking
 */
@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController
{
	@Autowired
	private UserService userService;
	
	/**
	 * 获取当前用户
	 * @param req
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/getCurrUser",method=RequestMethod.POST)
	public Result getCurrUser(HttpServletRequest req) {
		UserVo user = (UserVo)req.getSession().getAttribute("loginUser");
		Result result = new Result();
		result.setData(user);
		return result;
	}
	/**
	 * 查询全部用户,不包含管理员(按最近聊天的倒叙)
	 * @param param
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/chatList",method=RequestMethod.POST)
	public EasyUISeparatPage listUserAll(@RequestParam Map<String, Object> param) {
		Query query = new Query(param);
		EasyUISeparatPage data = userService.listUserAll(query);
		return data;
	}
	/**
	 * 根据部门查询多个用户
	 * @param param
	 * param 中的参数 ：departmentId
	 * 				    start
	 *  				limit
	 *  				roleId
	 *  			或者keyword
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public EasyUISeparatPage listUserByQuery(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage data = userService.listUserByQuery(query);
		return data;
	}
	
	/**
	 * 查询一个用户
	 * @param param
	 * param
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/detail",method=RequestMethod.POST)
	public Result getUserById(String userId){
		Result result = new Result(true);
		UserVo userVo = userService.getUserById(userId);
		// String[] dateArray = userVo.getBrithday().split(" ")[0].split("-");
		// userVo.setBrithdayVo(dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0]);
		result.setData(userVo);
		return result;
	}
	/**
	 * 新增一个用户
	 * @param user
	 * @param roleIds 用户的角色组成的数组
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public Result insert(HttpServletRequest req, R_UserDo user, String[] roleIds, 
			@RequestParam(value="audit", defaultValue="false")String audit)
	{
		Result result = new Result(false);
		Query query = new Query();
		query.put("account", user.getAccount());
		UserVo operUser = super.getUser(req);
		if(Boolean.parseBoolean(audit)){
			user.setStatus(2);
			user.setLastoperateType("3");
		}else{
			user.setStatus(1);
			user.setLastoperateType("4");
		}
		user.setLastoperateTime(new Date());
		user.setLastoperateUser(operUser.getId());
		user.setCreateTime(new Date());
		// 新增之前 先看看 此用户是否已经存在
		UserVo checkUser = userService.getUserByQuery(query);
		if(checkUser != null){
			if(checkUser.getStatus()==4) {
				user.setId(checkUser.getId());
			int a=	userService.update(user, roleIds);
			if(a == 1)
			{
				result.setResult(true);
			}
			return result;
			}else {
				result.setMsg("该账号已存在");
				return result;
			}
		}
		try{
			int row = userService.insert(user, roleIds);
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
	 * 更新用户信息(后台更新，不包含更新用户头像)
	 * @param user
	 * @param userBrithday
	 * @param roleIds
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@ResponseBody 
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public Result updateBack(R_UserDo user, String[] roleIds, String userBrithday, HttpServletRequest req) throws IllegalArgumentException, IllegalAccessException
	{
		Result result = new Result(false);
		try{
			int row = userService.update(user, roleIds);
			if(row == 1)
			{
				result.setResult(true);
			}else if(row == -1){
				result.setMsg("该部门已经存在领导，请重新选择用户角色");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除用户
	 * @param roleId
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public Result delete(HttpServletRequest req, String audit, String userId)
	{
		Result result = new Result(true);
		UserVo operUser = super.getUser(req);
		try
		{
			R_UserDo user = new R_UserDo();
			user.setId(userId);
			if(Boolean.parseBoolean(audit)){
				user.setLastoperateType("6");
			}else{
				user.setStatus(4);
				user.setLastoperateType("7");
			}
			user.setLastoperateTime(new Date());
			user.setLastoperateUser(operUser.getId());
			userService.delete(user);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 审核用户(二级单位管理员删除用户，添加用户都需要总管理员审核)
	 * @param req
	 * @param userId 待审核的用户ID
	 * @param pass true：通过
	 * 				false：驳回
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/audit",method=RequestMethod.POST)
	public Result auditUser(HttpServletRequest req, String userId, 
			@RequestParam(value="pass", defaultValue="false")String pass){
		Result result = new Result(false);
		UserVo operUser = super.getUser(req);
		// 先查出当前用户
		UserVo userVo = userService.getUserById(userId);
		R_UserDo userDo = new R_UserDo();
		userDo.setId(userId);
		if(Boolean.parseBoolean(pass)){
			// 最后操作类型为0的，代表注册申请
			if(userVo.getLastoperateType().equals("0")){
				userDo.setStatus(1);
				userDo.setLastoperateType("1");
			}else if(userVo.getLastoperateType().equals("3")){
				userDo.setStatus(1);
				userDo.setLastoperateType("4");
			}else if(userVo.getLastoperateType().equals("6")){
				userDo.setStatus(4);
				userDo.setLastoperateType("7");
			}
		}else{
			// 最后操作类型为0的，代表注册申请
			if(userVo.getLastoperateType().equals("0")){
				userDo.setStatus(3);
				userDo.setLastoperateType("2");
			// 最后操作类型为1的，代表二级管理员新增申请
			}else if(userVo.getLastoperateType().equals("3")){
				userDo.setStatus(3);
				userDo.setLastoperateType("5");
			// 最后操作类型为2的	
			}else if(userVo.getLastoperateType().equals("6")){
				userDo.setStatus(1);
				userDo.setLastoperateType("8");
			}
		}
		userDo.setLastoperateTime(new Date());
		userDo.setLastoperateUser(operUser.getId());
		// 修改用户状态
		try {
			int row = userService.update(userDo, null);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 检查一个人是不是管理员或者文档管理员
	 * @param req
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/check/role",method=RequestMethod.POST)
	public Result checkIsManager(HttpServletRequest req){
		Result result = new Result(true);
		Object obj = req.getSession().getAttribute("loginUser");
		UserVo userVo = (UserVo)obj;
		String userId = userVo.getId();
		boolean isManager = userService.isManager(userId);
		if(isManager){
			result.setData("true");
		}else{
			result.setData("false");
		}
		return result;
	}

}
