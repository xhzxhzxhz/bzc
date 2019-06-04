package com.folkestone.bzcx.mapper.collection;


import java.util.List;
import java.util.Map;

import com.folkestone.bzcx.bean.bean_do.collection.RCollectionDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.collection.RCollectionVo;

public interface RCollectionMapper {

	List<RCollectionVo> listByQuery(Query query);
	
    int delete(String collectionId);

    int insert(RCollectionDo record);

    int update(RCollectionDo record);

	List<Map<String,Object>> show(Query query);
}