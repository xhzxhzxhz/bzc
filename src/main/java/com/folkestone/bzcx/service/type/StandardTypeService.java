package com.folkestone.bzcx.service.type;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.type.StandardTypeDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.type.StandardTypeVo;
import com.folkestone.bzcx.mapper.type.StandardTypeMapper;
import com.folkestone.bzcx.mapper.type.TypeDictionariesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class StandardTypeService {
	
	@Autowired
	private StandardTypeMapper standType;
	@Autowired
	private TypeDictionariesMapper diction;
	
	
	public List<StandardTypeVo> selectDictionforLetter(String dictionariesName){
		return standType.selectDictionforLetter(dictionariesName);
	}
	/**
	 * 批量删除
	 * @param typeId
	 * @return
	 */
	public int delete(String[] typeId) {
		int delete = standType.delete(typeId);
		//删除的时候也删掉下面的字典
		if(delete > 0) {
			diction.deleteBytype(typeId);
		}
		return delete;
	}
	
	/**
	 * 修改
	 * @param record
	 * @return
	 */
	public int update(StandardTypeDo record) {
		return standType.update(record);
	}
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int insert(StandardTypeDo record) {
		//插入的时候判断有没有标识相同的，不允许标识相同
		Query quer = new Query();
		quer.put("typeLetter", record.getTypeLetter());
		List<StandardTypeVo> selectByQuery = standType.selectByQuery(quer);
		if(selectByQuery.size() > 0) {
			return -1;
		}
		int insert = standType.insert(record);
		return insert;
	}
	
	
	/**
	 * 不添加条件分页查询
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage selectAllforPage(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		List<StandardTypeVo> selectAll = standType.selectAll(query);
		PageInfo<StandardTypeVo> pageInfo = new PageInfo<StandardTypeVo>(selectAll);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	/**
	 * 不分页不添加条件查询
	 * @return
	 */
	public List<StandardTypeVo> selectAll() {
		return standType.selectAll();
	}
	
	
	/**
	 * 添加条件分页查询
	 * 主要条件为
	 * typeId
	 * typeName 类型名字模糊查询
	 * TYPE_LETTER 类型标识查询
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage selectByQueryforPage(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		List<StandardTypeVo> selectAll = standType.selectAll(query);
		PageInfo<StandardTypeVo> pageInfo = new PageInfo<StandardTypeVo>(selectAll);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	/**
	 * 添加条件不分页查询
	 * 主要条件为
	 * typeId
	 * typeName 类型名字模糊查询
	 * TYPE_LETTER 类型标识查询
	 * @param query
	 * @return
	 */
	public List<StandardTypeVo> selectByQuery(Query query) {
		return standType.selectByQuery(query);
	}
	
}
