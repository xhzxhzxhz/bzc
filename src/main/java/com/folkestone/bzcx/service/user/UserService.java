package com.folkestone.bzcx.service.user;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.user.R_UserDo;
import com.folkestone.bzcx.bean.bean_do.user.R_User_RoleDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.ResourceVo;
import com.folkestone.bzcx.bean.bean_vo.user.RoleVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.mapper.user.RUserMapper;
import com.folkestone.bzcx.mapper.user.RUserRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserService {
	@Autowired
	private RUserMapper userMapper;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RUserRoleMapper userRoleMapper;
	@Autowired
	private RoleService roleService;
	
	/**
	 * 查询除了管理员以外的用户(按最近聊天的倒叙)
	 * @return
	 */
	public EasyUISeparatPage listUserAll(Query query){
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<UserVo> userList = userMapper.listUserAll(query);
		// 得到结果
		PageInfo<UserVo> pageInfo = new PageInfo<UserVo>(userList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	/**
	 * 根据条件查询多个用户
	 * @param query
	 * @param isTotal 是否查询总数
	 * @return
	 */
	public EasyUISeparatPage listUserByQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<UserVo> userList = userMapper.listUserByQuery(query);
		// 得到结果
		PageInfo<UserVo> pageInfo = new PageInfo<UserVo>(userList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	/**
	 * 根据条件 查询一个用户
	 * @param query
	 * @return
	 */
	public UserVo getUserByQuery(Query query) {
		UserVo user = userMapper.getUserByQuery(query);
		// 在查这个角色的资源
		if(user != null){
			List<ResourceVo> resourceList = resourceService.listResourceByUserId(user.getId());
			user.setResourceList(resourceList);
			System.out.println("资源数量:" + resourceList.size());
		}
		return user;
	}
	/**
	 * 新增一个用户
	 * @param user
	 * @param roleIds 
	 * @return
	 */
	public int insert(R_UserDo user, String[] roleIds) {
		int row = userMapper.insert(user);
		if(row == 1 && roleIds != null){
			// 然后给用户分配角色
			R_User_RoleDo userRoleDo = new R_User_RoleDo();
			for (String roleId : roleIds) {
				userRoleDo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				userRoleDo.setRoleId(roleId);
				userRoleDo.setAllowId(user.getId());
				userRoleDo.setAllowType(1);
				userRoleDo.setLastoperateTime(new Date());
				userRoleMapper.insert(userRoleDo);
			}
		}
		return row;
	}
	/**
	 * 编辑用户
	 * @param user
	 * @param roleIds 
	 * @return
	 */
	public int update(R_UserDo user, String[] roleIds) {
		int row = userMapper.update(user);
		if(row == 1 && roleIds!= null){
			Query query = new Query();
			query.put("userId", user.getId());
			// 删除此用户的所有角色
			userRoleMapper.deleteByQuery(query);
			// 重新给此用户分配新的角色
			R_User_RoleDo userRoleDo = new R_User_RoleDo();
			for (String roleId : roleIds) {
				userRoleDo.setId(UUID.randomUUID().toString().replaceAll("-", ""));
				userRoleDo.setRoleId(roleId);
				userRoleDo.setAllowId(user.getId());
				userRoleDo.setAllowType(1);
				userRoleDo.setLastoperateTime(new Date());
				userRoleMapper.insert(userRoleDo);
			}
		}
		return row;
	}
	/**
	 * 更新密码
	 * @param user
	 * @param roleIds 
	 * @return
	 */
	public int repassword(Query query) {
		R_UserDo user =new R_UserDo();
		user.setId((String) query.get("id"));
		user.setPassword((String) query.get("password"));
		return userMapper.update(user);
	}
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	public int delete(R_UserDo user) {
		int row = userMapper.update(user);
		return row;
	}
	
	/**
	 * 根据ID查询用户
	 * @param valueOf
	 * @return
	 */
	public UserVo getUserById(String userId) {
		UserVo user = userMapper.getUserById(userId);
		if(user != null){
			List<ResourceVo> resourceList = resourceService.listResourceByUserId(user.getId());
			user.setResourceList(resourceList);
			System.out.println("查询角色:" + resourceList.size());
		}
		return user;
	}
	/**
	 * 比较纯粹的查询一个部门下直属有多少人
	 * 这些人不包过这个部门的子部门下的人
	 * @param query
	 * @return
	 */
	public List<UserVo> listUserByDepartmentId(Query query) {
		return userMapper.listUserByQuery(query);
	}
	/**
	 * 验证一个用户是否是管理员
	 * @param userId
	 * @return
	 */
	public boolean isManager(String userId) {
		List<RoleVo> roleList = roleService.listRoleByUser(userId);
		for(RoleVo roleVo : roleList){
			if("".equals(roleVo.getId())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 通过用户电话去查询用户信息
	 * @param query
	 * @return
	 */
	public UserVo findUserByTel(Query query) {
		// TODO Auto-generated method stub
		return userMapper.findUserByTel(query);
	}

	public void updateUser(R_UserDo us) {
		// TODO Auto-generated method stub
		userMapper.update(us);
	}

	public UserVo findUserByAccount(String account) {
		// TODO Auto-generated method stub
		return userMapper.findUserByAccount(account);
	}
	
}
