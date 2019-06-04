package com.folkestone.bzcx.service.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.user.R_RoleDo;
import com.folkestone.bzcx.bean.bean_do.user.R_Role_ResourceDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.ResourceVo;
import com.folkestone.bzcx.bean.bean_vo.user.RoleVo;
import com.folkestone.bzcx.mapper.user.RRoleMapper;
import com.folkestone.bzcx.mapper.user.RRoleResourceMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service
public class RoleService {
	@Autowired
	private RRoleMapper roleMapper;
	@Autowired
	private RRoleResourceMapper roleResourceMapper;
	@Autowired
	private ResourceService resourceService;
	/**
	 * 插入角色
	 * @param role
	 * @param resourceIds
	 * @return
	 */
	public int insert(R_RoleDo role, String[] resourceIds) {
		// 先插入角色 
		int row = roleMapper.insert(role);
		if(row == 1 && resourceIds != null)
		{
			// 插入成功 再给角色配置资源
			for(String resourceId : resourceIds)
			{
				R_Role_ResourceDo roleResource = new R_Role_ResourceDo();
				roleResource.setCreateTime(new Date());
				roleResource.setResourceId(resourceId);
				roleResource.setRoleId(role.getId());
				roleResourceMapper.insert(roleResource);
			}
		}
		return row;
	}
	/**
	 * 查询角色列表
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listRoleByQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<RoleVo> userList = roleMapper.listRoleByQuery(query);
		// 得到结果
		PageInfo<RoleVo> pageInfo = new PageInfo<RoleVo>(userList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	/**
	 * 查询一个人的所有角色
	 * @param userId
	 * @return
	 */
	public List<RoleVo> listRoleByUser(String userId){
		return roleMapper.listRoleByUserId(userId);
	}
	/**
	 * 查询单个角色
	 * @param query
	 * @param withResource false不查询这个角色的资源 true 查询资源
	 * @return
	 */
	public RoleVo getRoleByQuery(Query query, boolean withResource) {
		RoleVo role = roleMapper.getRoleByQuery(query);
		if(withResource)
		{
			// 查询这个角色有哪些资源
			List<ResourceVo> resourceList = resourceService.listResourceByRoleId(role.getId());
			role.setResourceList(resourceList);
		}
		return role;
	}
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public int update(R_RoleDo role, String[] resourceIds) {
		Query query = new Query();
		query.put("roleId", role.getId());
		int row = roleMapper.update(role);
		if(row == 1){
			// 删除之前的角色和资源的关联关系
			roleResourceMapper.deleteByQuery(query);
			// 配置新的关联关系
			for(String resourceId : resourceIds)
			{
				R_Role_ResourceDo roleResource = new R_Role_ResourceDo();
				roleResource.setCreateTime(new Date());
				roleResource.setResourceId(resourceId);
				roleResource.setRoleId(role.getId());
				roleResourceMapper.insert(roleResource);
			}
		}
		return row;
	}
	/**
	 * 根据Id删除角色
	 * @param valueOf
	 * @return
	 */
	public int delete(String roleId) {
		int row = roleMapper.delete(roleId);
		if(row == 1)
		{
			// 删除角色和资源的对应关系
			Query query = new Query();
			query.put("roleId", roleId);
			roleResourceMapper.deleteByQuery(query);
		}
		return row;
	}
	/**
	 * 根据Id查询角色
	 * @param roleId
	 * @return
	 */
	public RoleVo getRoleById(String roleId) {
		RoleVo role = roleMapper.getRoleById(roleId);
		List<ResourceVo> resourceList = resourceService.listResourceByRoleId(role.getId());
		role.setResourceList(resourceList);
		return role;
	}
	
}
