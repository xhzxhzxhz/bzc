package com.folkestone.bzcx.mapper.interact;


import java.util.List;

import com.folkestone.bzcx.bean.bean_do.interact.RInformationDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.interact.RInformationVo;

public interface RInformationMapper {

	List<RInformationVo> listAll(Query query);
	
	int delete(String informationId);
	
    int insert(RInformationDo record);

    int update(RInformationDo record);
    
}