package com.folkestone.bzcx.mapper.user;

import com.folkestone.bzcx.bean.bean_do.user.R_Role_ResourceDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;

public interface RRoleResourceMapper {
	/**
	 * 新增角色和资源的对应关系
	 * @param resource
	 * @return
	 */
   int insert(R_Role_ResourceDo roleResource);
   /**
    * 依照条件删除角色和资源的对应关系
    * @param query
    * @return
    */
   int deleteByQuery(Query query);
   /**
    * 依照条件查询关联关系
    * @return
    */
   int listByQuery(Query query);
}