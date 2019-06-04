package com.folkestone.bzcx.mapper.type;


import java.util.List;

import com.folkestone.bzcx.bean.bean_do.type.TypeDictionariesDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;

public interface TypeDictionariesMapper {
	
	List<TypeDictionariesDo> selectByQuery(Query query);

    int delete(String[] dictionariesId);
    
    int deleteBytype(String[] typeId);

    int insert(TypeDictionariesDo record);

    int update(TypeDictionariesDo record);
}