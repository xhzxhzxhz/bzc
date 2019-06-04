package com.folkestone.bzcx.mapper.type;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.folkestone.bzcx.bean.bean_do.type.StandardTypeDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.type.StandardTypeVo;

public interface StandardTypeMapper {
	
	List<StandardTypeVo> selectDictionforLetter(@Param(value = "dictionariesName") String dictionariesName);
	
	List<StandardTypeVo> selectAll(Query query);
	
	List<StandardTypeVo> selectAll();
	
	List<StandardTypeVo> selectByQuery(Query query);
	
    int delete(String[] typeId);

    int insert(StandardTypeDo record);

    int update(StandardTypeDo record);
}