package com.folkestone.bzcx.controller.admin.login;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.folkestone.bzcx.bean.bean_do.user.R_UserDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.DepartmentVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.user.DepartmentService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.UserService;

/**
 * Describe：用户注册接口
 * 
 * @author smallking
 *
 *  2017年12月28日
 */
@Controller
@RequestMapping(value = "/registe")
public class RegisteController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private OperLogService operLogService;
	/**
	 * 用户注册接口
	 * @param req
	 * @param userDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/in")
	public Object register(HttpServletRequest req, R_UserDo userDo){
		System.out.println("注册请求");
		Result result = new Result(false);
		// 先检查当前注册的账号是否已经存在
		String account = userDo.getAccount();
		Query query = new Query();
		query.put("account", account);
		UserVo user = userService.getUserByQuery(query);
		if(user != null){
			result.setCode(10008);
			result.setMsg("当前账号已存在。");
			return result;
		}
		// 注册的用户 默认就是昆仑能源有限公司的
		DepartmentVo departmentVo = departmentService.getParentestDepartment(req);
		userDo.setDepartmentId(departmentVo.getId());
		// 注册的角色 默认就是外部初级用户
		String[] roleId = {ContantFinalUtil.BASE_ROLE_LIST.get(1)};
		try {
			userDo.setCreateTime(new Date());
			userDo.setLastoperateTime(new Date());
			userDo.setLastoperateUser(userDo.getId());
			userDo.setLastoperateType("0");
			userDo.setStatus(2);
			int row = userService.insert(userDo, roleId);
			if(row == 1){
				result.setResult(true);
				operLogService.insert(super.getOperLog(req, "1000", userDo.getRealname(), userDo.getId()));
				// 调用登录接口
				/* 因为需要审核 所以不能直接跳转登录接口
				System.out.println("内部转发---");
				ModelAndView model = new ModelAndView();
				model.setViewName("forward:/login/in");
				return model;*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			 
		return result;
	}
}
