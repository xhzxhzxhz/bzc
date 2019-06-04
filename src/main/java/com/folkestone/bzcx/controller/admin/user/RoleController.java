package com.folkestone.bzcx.controller.admin.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.user.R_RoleDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.RoleVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.service.user.RoleService;


@Controller
@RequestMapping("/admin/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	@ResponseBody
	@RequestMapping(value = "/insert", method = {RequestMethod.POST})
	public Result insert(R_RoleDo role, String[] resourceIds){
		Result result = new Result(false);
		try{
			role.setLastoperateTime(new Date());
			int row = roleService.insert(role, resourceIds);
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
	 * 查询角色列表
	 * 编辑用户的时候 查询的角色列表 需要有默认选中
	 * @param param
	 * param 中的参数：
	 * start
	 * limit
	 * userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = {RequestMethod.POST})
	public EasyUISeparatPage listRoleByQuery(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage data = roleService.listRoleByQuery(query);
		if(query.get("userId") != null){
			// 当前用户的列表需要默认选中
			List<RoleVo> roleList = roleService.listRoleByUser(query.get("userId") + "");
				for(RoleVo role1 : roleList){
					for(Object obj : data.getRows()){
						RoleVo role2 = (RoleVo)obj;
						if(role1.getId().equals(role2.getId())){
							role2.setChecked(true);
						}
					}
				}
		}
		return data;
	}
	/**
	 * 查询角色详情
	 * @param param
	 * param 中的参数 roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = {RequestMethod.POST})
	public Result getRoleByQuery(String roleId){
		Result result = new Result(true);
		RoleVo role = roleService.getRoleById(roleId);
		result.setData(role);
		return result;
	}
	/**
	 * 更新
	 * @param role
	 * @param resourceId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = {RequestMethod.POST})
	public Result update(R_RoleDo role, String[] resourceIds){
		Result result = new Result(false);
		try
		{
			int row = roleService.update(role, resourceIds);
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
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = {RequestMethod.POST})
	public Result delete(String roleId)
	{
		Result result = new Result(false);
		// 最基础的几个角色 不能删除
		if(ContantFinalUtil.BASE_ROLE_LIST.contains(roleId)){
			result.setCode(10007);
			result.setMsg("初始角色，不可删除。");
			return result;
		}
		try {
			int row = roleService.delete(roleId);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
