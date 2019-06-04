package com.folkestone.bzcx.mapper.user;

import com.folkestone.bzcx.bean.bean_do.user.R_ResourceDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.ResourceVo;

import java.util.List;

public interface RResourceMapper {
	/**
	 * 查询多个资源
	 * @param query
	 * @return
	 */
    List<ResourceVo> listResourceByQuery(Query query);
    /**
     * 查询一个资源
     * @param query
     * @return
     */
    ResourceVo getResourceByQuery(Query query);
    /**
     * 修改一个资源
     * @param resource
     * @return
     */
    int update(R_ResourceDo resource);
    /**
     * 根据角色查询资源
     * @return
     */
	List<ResourceVo> listResourceByRoleId(String roleId);
	/**
	 * 根据ID查询资源
	 * @return
	 */
	ResourceVo getResourceById(String resourcId);
	/**
	 * 插入资源
	 * @param resourcDo
	 * @return
	 */
	int insert(R_ResourceDo resourcDo);
}