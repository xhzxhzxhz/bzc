package com.folkestone.bzcx.service.user;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.user.U_Oper_LogDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.user.OperLogVo;
import com.folkestone.bzcx.mapper.user.UOperLogMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class OperLogService {
	@Autowired
	private UOperLogMapper operLogMapper; 
	/**
	 * 插入一条操作日志
	 * @param operDo
	 * @return 
	 */
	public int insert(U_Oper_LogDo operDo){
		
		return operLogMapper.insert(operDo);
	}
	
	/**
	 * 根据Query条件查询多条日志
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listOperLogByQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<OperLogVo> operLogList = operLogMapper.listOperLogByQuery(query);
		// 得到结果
		PageInfo<OperLogVo> pageInfo = new PageInfo<OperLogVo>(operLogList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	/**
	 * 删除起始时间到目前的所有数据
	 * @param satrtTime
	 * @return
	 */
	public int deleteByDate(String satrtTime) {
		int row = operLogMapper.deleteByDate(satrtTime);
		return row;
	}
}
