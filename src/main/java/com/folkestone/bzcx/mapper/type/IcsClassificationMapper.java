package com.folkestone.bzcx.mapper.type;


import java.util.List;

import com.folkestone.bzcx.bean.bean_do.type.IcsClassificationDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.type.IcsClassificationVo;

public interface IcsClassificationMapper {

	List<IcsClassificationVo> listAll();
	
	List<IcsClassificationVo> listByQuery(Query query);
	
	List<IcsClassificationVo> listTree(Query query);
	
    int insert(IcsClassificationDo record);
}