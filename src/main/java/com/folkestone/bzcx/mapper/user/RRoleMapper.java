package com.folkestone.bzcx.mapper.user;

import com.folkestone.bzcx.bean.bean_do.user.R_RoleDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.RoleVo;

import java.util.List;

public interface RRoleMapper {
	 /**
	    * 新增一个角色
	    * @param role
	    * @return
	    */
		int insert(R_RoleDo role);
		/**
		 * 查询多个角色
		 * @param query
		 * @return
		 */
		List<RoleVo> listRoleByQuery(Query query);
		/**
		 * 查询一个用户的所有角色
		 * @param userId
		 * @return
		 */
		List<RoleVo> listRoleByUserId(String userId);
		/**
		 * 查询一个角色
		 * @param query
		 * @return
		 */
		RoleVo getRoleByQuery(Query query);
		/**
		 * 删除一个角色
		 * @param roleId
		 * @return
		 */
		int delete(String roleId);
		/**
		 * 更新角色
		 * @param role
		 * @return
		 */
		int update(R_RoleDo role);
		/**
		 * 根据Id查询角色
		 * @param roleId
		 * @return
		 */
		RoleVo getRoleById(String roleId);
		
}