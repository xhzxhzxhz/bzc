package com.folkestone.bzcx.mapper.user;

import com.folkestone.bzcx.bean.bean_do.user.U_Oper_LogDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.OperLogVo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UOperLogMapper {
	/**
	 * 插入一条操作日志
	 * @param operDo
	 * @return
	 */
	int insert(U_Oper_LogDo operDo);
	
	/**
	 * 根据集合查找多条日志
	 * @param query
	 * @return
	 */
	List<OperLogVo> listOperLogByQuery(Query query);
	
	/**
	 * 根据主键删除一条日志
	 * @param operLogId
	 * @return
	 */
	int deleteById(String operLogId);
	/**
	 * 删除起始时间到目前的所有数据
	 * @param satrtTime
	 * @return
	 */
	int deleteByDate(String satrtTime);
	
}