package com.folkestone.bzcx.controller.admin.login;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.type.StandardTypeVo;
import com.folkestone.bzcx.bean.bean_vo.user.ResourceVo;
import com.folkestone.bzcx.bean.bean_vo.user.RoleVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.type.StandardTypeService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.UserService;


/**
 * Describe：登录注册Controller
 * 
 * @author smallking
 *
 *  2017年11月10日
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController{

	@Autowired
	private UserService userService;
	@Autowired
	private OperLogService operLogService;
	@Autowired
	private StandardTypeService StandardType;
	/**
	 * 登录接口
	 * @param account
	 * @param password
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/in", method = {RequestMethod.POST, RequestMethod.GET})
	public Object loginIn( String password,String account,HttpServletRequest req){
		if(req.getSession().getAttribute("stanType") == null) {
			List<StandardTypeVo> selectAll = StandardType.selectAll();
			req.getSession().setAttribute("stanType", selectAll);
		}
		Result result = new Result(false);
		if(account == null || account.trim().equals(""))
		{ 
			result.setMsg("用户名不能为空。");
			return result;
		}
		Query query = new Query();
		query.put("account", account);
		UserVo user = userService.getUserByQuery(query);
		if(user == null)
		{
			result.setMsg("该用户不存在");
			return result;	
		}
		if(!user.getPassword().equals(password))
		{
			result.setMsg("密码不正确");
			return result;
		}
		if(user.getStatus() != 1){
			result.setMsg("用户状态异常，禁止登陆。");
			return result;
		}
		result.setResult(true);
		result.setData(user);
		// 登录成功之后，将其权限信息查出来，放到session中
		// 查权限信息				   
		req.getSession().setAttribute("loginUserstate", true);
		req.getSession().setAttribute("loginUser", user);
		req.getSession().setAttribute("resourceList", user.getResourceList());
		for(ResourceVo resourceVo : user.getResourceList()){
			// 将权限信息放到session里面
			req.getSession().setAttribute(resourceVo.getId(),"true");
		}
		operLogService.insert(super.getOperLog(req, "1001", user.getRealname(), user.getId()));
		return result;
	}
	
	
	/**
	 * 修改密码接口
	 * @param account
	 * @param password
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/repassword", method = {RequestMethod.POST, RequestMethod.GET})
	public Object repassword(String repassword, String password, HttpServletRequest req){
		Result result = new Result(false);
		UserVo user=  (UserVo) req.getSession().getAttribute("loginUser");
		if(repassword.equals(user.getPassword())) {
		    int a=-1;
			Query query = new Query();
			query.put("id", user.getId());
			query.put("password", password);
			 a=  userService.repassword(query);
			if(a>0) {
				//修改成功之后放入session
				req.getSession().setAttribute("loginUser", user);
				req.getSession().setAttribute("resourceList", user.getResourceList());
				for(ResourceVo resourceVo : user.getResourceList()){
					// 将权限信息放到session里面
					req.getSession().setAttribute(resourceVo.getId(), "true");
				}
				result.setCode(200);
				result.setResult(true);
			}
			
		}else{
			result.setMsg("原始密码错误，请重试");
			return result;
		}
		return result;
	}
	/**
	 * 检查当前用户是否登录
	 * 没登录返回false否则返回用户的信息。
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method = {RequestMethod.POST})
	public Result checkLogin(HttpServletRequest req){
		Result result = new Result(false);
		result.setMsg("未登录。");
		Object obj = req.getSession().getAttribute("loginUser");
		UserVo user=  (UserVo)obj;
		if(user==null) {
			return result;
		}else {
			List<Integer> li = new ArrayList<>();
			for (ResourceVo iterable_element : user.getResourceList()) {
				if(iterable_element.getName().equals("标准题录维护")) {
					li.add(1);
				}
				if(iterable_element.getName().equals("标准原文维护")) {
					li.add(2);
				}
				if(iterable_element.getName().equals("标准体系维护")) {
					li.add(3);
				}
				if(iterable_element.getName().equals("资源共享维护")) {
					li.add(4);
				}
			}
			if(!li.isEmpty()) {
				Collections.sort(li);
				user.setShowadmin(li.get(0));	
			}
			
		}
		if(obj != null){
			result.setResult(true);
			result.setMsg("已经登录。");
			result.setData(obj);
		}
		return result;
	}
	/**
	 * 注销登录
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/out")
	public String loginOut(HttpServletRequest req){
		try {
		UserVo user = (UserVo) req.getSession().getAttribute("loginUser");
		req.getSession().setAttribute("resourceList", user.getResourceList());
		for(ResourceVo resourceVo : user.getResourceList()){
			// 将权限信息放到session里面
			req.getSession().removeAttribute(resourceVo.getId());
		}
		req.getSession().removeAttribute("loginUser");
		req.getSession().removeAttribute("resourceList");
		@SuppressWarnings("unchecked")
		List<ResourceVo> resourceList = (ArrayList<ResourceVo>)req.getSession().getAttribute("resourceList");
		if(resourceList != null){
			for(ResourceVo resourceVo : resourceList){
				req.getSession().removeAttribute(resourceVo.getId());
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "redirect:/admin/login.html";
	}
	/**
	 * 注销登录(前台)
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user/out")
	public void loginUserOut(HttpServletRequest req){
		UserVo user = (UserVo) req.getSession().getAttribute("loginUser");
		req.getSession().setAttribute("resourceList", user.getResourceList());
		for(ResourceVo resourceVo : user.getResourceList()){
			// 将权限信息放到session里面
			req.getSession().removeAttribute(resourceVo.getId());
		}
		req.getSession().removeAttribute("loginUser");
		req.getSession().removeAttribute("resourceList");
	}
}
