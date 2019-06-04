package com.folkestone.bzcx.mapper.user;

import com.folkestone.bzcx.bean.bean_do.user.R_User_RoleDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.UserRoleVo;

import java.util.List;

public interface RUserRoleMapper {

	int insert(R_User_RoleDo userRoleDo);

	int deleteByQuery(Query query);

	List<UserRoleVo> listUserRoleByQuery(Query query);
}