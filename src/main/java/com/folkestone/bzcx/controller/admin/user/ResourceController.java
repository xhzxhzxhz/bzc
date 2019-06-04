package com.folkestone.bzcx.controller.admin.user;

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

import com.folkestone.bzcx.bean.bean_do.user.R_ResourceDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUITree;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.ResourceVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.user.ResourceService;

@Controller
@RequestMapping("/admin/resource")
public class ResourceController extends BaseController{
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 获得权限资源树 编辑角色的时候 
	 * 需要知道树节点那个是被默认选中的
	 * @param req
	 * @param resp
	 * @param roleId 角色ID
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/tree", method = {RequestMethod.POST})
	public List<EasyUITree> documentTree(HttpServletRequest req, HttpServletResponse resp, String roleId) throws IOException{
		if(roleId != null){
			return resourceService.getResourceTreeByRoleId(roleId);
		}else {
			return resourceService.getResourceTree();
		}
	}
	
	/**
	 * 获取资源列表 编辑角色的时候，用户查询资源列表，也是用此接口
	 * @param param
	 * param 中的参数是 start
	 * 					limit
	 * 					keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = {RequestMethod.POST})
	public EasyUISeparatPage listResource(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage data = resourceService.listResourceByQuery(query);
		// 将用户当前拥有的资源的checked设置为true
		if(query.get("roleId")!= null){/*
			RoleVo roleVo = roleService.getRoleByQuery(query, true);
			List<ResourceVo> roleResourceList= roleVo.getResourceList();
			for(ResourceVo roleResource : roleResourceList){
				for(ResourceVo resource : resourceList){
					if(resource.getId() == roleResource.getId()){
						resource.setChecked(true);
					}
				}
			}
		*/}
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert", method = {RequestMethod.POST})
	public Result insert(R_ResourceDo resourcDo){
		Result result = new Result(false);
		if(resourcDo.getParentId().equals("0")){
			resourcDo.setPath(resourcDo.getId());
		}else{
			ResourceVo resourceVo = resourceService.getResouceById(resourcDo.getParentId());
			resourcDo.setPath(resourceVo.getPath() + "," + resourcDo.getId());
		}
		try {
			int row = resourceService.insert(resourcDo);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据ID查询一条资源的
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = {RequestMethod.POST})
	public Result resourceDetail(@RequestParam Map<String, Object> param){
		Result result = new Result(true);
		Query query = new Query(param);
		ResourceVo resource = resourceService.getResourceByQuery(query);
		result.setData(resource);
		return result;
	}
	
	/**
	 * 更新资源状态
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = {RequestMethod.POST})
	public Result updateResource(R_ResourceDo resource){
		Result result = new Result(false);
		try {
			int row = resourceService.update(resource);
			if(row == 1)
			{
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
