package com.folkestone.bzcx.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.user.R_ResourceDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUITree;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.ResourceVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserRoleVo;
import com.folkestone.bzcx.mapper.user.RResourceMapper;
import com.folkestone.bzcx.mapper.user.RUserRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ResourceService {
	@Autowired
	private RResourceMapper resourceMapper;
	@Autowired
	private RUserRoleMapper userRoleMapper;
	/**
	 * 按照条件查询多个资源 
	 * @return
	 */
	public EasyUISeparatPage listResourceByQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<ResourceVo> userList = resourceMapper.listResourceByQuery(query);
		// 得到结果
		PageInfo<ResourceVo> pageInfo = new PageInfo<ResourceVo>(userList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	/**
	 * 根据ID查询资源
	 * @param resourceId
	 * @return
	 */
	public ResourceVo getResouceById(String resourceId){
		return resourceMapper.getResourceById(resourceId);
	}
	/**
	 * 修改资源的状态
	 * @param resource
	 * @return
	 */
	public int update(R_ResourceDo resource) {
		return resourceMapper.update(resource);
	}
	/**
	 * 得到一个角色的所有资源
	 * @param roleId
	 * @return
	 */
	public List<ResourceVo> listResourceByRoleId(String roleId) {
		return resourceMapper.listResourceByRoleId(roleId);
	}
	/**
	 * 查询一个用户的所有资源
	 * @return
	 */
	public List<ResourceVo> listResourceByUserId(String userId) {
		List<ResourceVo> resourceList = new ArrayList<ResourceVo>();
		Query query = new Query();
		query.put("userId", userId);
		List<UserRoleVo> userRoleVoList = userRoleMapper.listUserRoleByQuery(query);
		for(UserRoleVo userRoleVo : userRoleVoList){
			List<ResourceVo> resourceVoListt = resourceMapper.listResourceByRoleId(userRoleVo.getRoleId());
			for(ResourceVo resourceVo : resourceVoListt){
				if(!resourceList.contains(resourceVo)){
					resourceList.add(resourceVo);
				}
			}
		}
		return resourceList;
	}
	
	/**
	 * 查询一条资源
	 * @param query
	 * @return
	 */
	public ResourceVo getResourceByQuery(Query query) {
		return resourceMapper.getResourceByQuery(query);
	}
	
	/**
	 * 插入一个资源
	 * @param resourcDo
	 * @return
	 */
	public int insert(R_ResourceDo resourcDo) {
		return resourceMapper.insert(resourcDo);
	}

	/**
	 * 根据role的Id来查询资源树，其中用户拥有的权限，默认选中
	 * @param roleId
	 * @return
	 */
	public List<EasyUITree> getResourceTreeByRoleId(String roleId) {
		List<ResourceVo> resourceList = listResourceByRoleId(roleId);
		return getTree(resourceList);
	}
	
	/**
	 * 得到系统资源拼接成的资源树
	 * @param roleId
	 * @return
	 */
	public List<EasyUITree> getResourceTree() {
		return getTree(null);
	}
	
	/**
	 * 查询数据库，拼接资源树
	 * @param resourceList 
	 * @param resourceList 
	 * @return
	 */
	private List<EasyUITree> getTree(List<ResourceVo> resourceList) {
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		// 先查出来所有的一级菜单
		Query query = new Query();
		query.put("parentId", "0");
		List<ResourceVo> resList = resourceMapper.listResourceByQuery(query);
		for(ResourceVo resource : resList){
			EasyUITree tree = new EasyUITree();
			tree.setId(resource.getId());
			tree.setText(resource.getName());
			if(resourceList != null && resourceList.contains(resource)){
				tree.setChecked(true);
			}
			joinChilds(tree, resourceList);
			treeList.add(tree);
		}
		return treeList;
	}

	/**
	 * 递归添加子节点
	 * @param tree
	 * @param resourceList 
	 * @param query
	 */
	private void joinChilds(EasyUITree tree, List<ResourceVo> resourceList) {
		Query query = new Query();
		query.put("parentId", tree.getId());
		List<ResourceVo> resList = resourceMapper.listResourceByQuery(query);
		List<EasyUITree> child = new ArrayList<EasyUITree>();
		for(ResourceVo resourceVo : resList){
			EasyUITree childTree = new EasyUITree();
			childTree.setId(resourceVo.getId());
			childTree.setText(resourceVo.getName());
			if(resourceList != null && resourceList.contains(resourceVo)){
				childTree.setChecked(true);
			}
			joinChilds(childTree, resourceList);
			child.add(childTree);
		}
		tree.setChildren(child);
	}
	
}
