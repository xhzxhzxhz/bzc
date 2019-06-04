package com.folkestone.bzcx.mapper.user;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.user.R_UserDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;

public interface RUserMapper {

	/**
	 * 查询全部用户,不包含当前用户(按最近聊天的倒叙)
	 * @return
	 */
	List<UserVo> listUserAll(Query query);
	
	/**
	 * 查询多个用户
	 * @param query
	 * @return
	 */
	List<UserVo> listUserByQuery(Query query);
	/**
	 * 查询一个用户
	 * @param query
	 * @return
	 */
	UserVo getUserByQuery(Query query);
	/**
	 * 插入一个用户
	 * @param user
	 * @return
	 */
	int insert(R_UserDo user);
	/**
	 * 更改用户
	 * @param user
	 * @return
	 */
	int update(R_UserDo user);
	/**
	 * 删除用户根据Id
	 * @param query
	 * @return
	 */
	int deleteById(int userId);
	/**
	 * 根据其他条件删除用户
	 * @param query
	 * @return
	 */
	int deleteByQuery(Query query);
	
	List<UserVo> listUserByDepartmentId(Query query);
	/**
	 * 根据ID查询用户
	 * @param userId
	 * @return
	 */
	UserVo getUserById(String userId);
	/**
	 * 根据用户的电话查询用户
	 * @param query
	 * @return
	 */
	UserVo findUserByTel(Query query);

	UserVo findUserByAccount(String account);
}