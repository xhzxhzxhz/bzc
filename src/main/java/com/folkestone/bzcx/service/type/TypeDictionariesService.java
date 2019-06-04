package com.folkestone.bzcx.service.type;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.type.TypeDictionariesDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.mapper.type.TypeDictionariesMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TypeDictionariesService {
	
	@Autowired
	private TypeDictionariesMapper diction;
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int insert(TypeDictionariesDo record) {
		int insert = diction.insert(record);
		return insert;
	}
	
	/**
	 * 字典批量删除
	 * @param dictionariesId
	 * @return
	 */
	public int delete(String[] dictionariesId) {
		int delete = diction.delete(dictionariesId);
		return delete;
	}
	
	/**
	 * 字典类型批量删除
	 * @param typeId
	 * @return
	 */
	public int deleteBytype(String[] typeId) {
		int delete = diction.deleteBytype(typeId);
		return delete;
	}
	
	/**
	 * 修改
	 * @param record
	 * @return
	 */
	public int update(TypeDictionariesDo record) {
		int update = diction.update(record);
		return update;
	}
	
	
	/**
	 * 条件分页查询
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage selectByQueryforPage(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		List<TypeDictionariesDo> selectByQuery = diction.selectByQuery(query);
		PageInfo<TypeDictionariesDo> pageInfo = new PageInfo<TypeDictionariesDo>(selectByQuery);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	
	/**
	 * 条件不分页查询
	 * @param query
	 * @return
	 */
	public List<TypeDictionariesDo> selectByQuery(Query query) {
		List<TypeDictionariesDo> selectByQuery = diction.selectByQuery(query);
		return selectByQuery;
	}
	
}
