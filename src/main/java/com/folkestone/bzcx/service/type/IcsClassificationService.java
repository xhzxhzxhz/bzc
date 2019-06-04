package com.folkestone.bzcx.service.type;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.type.IcsClassificationDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.type.IcsClassificationVo;
import com.folkestone.bzcx.mapper.type.IcsClassificationMapper;

@Service
public class IcsClassificationService {
	
	@Autowired
	private IcsClassificationMapper icsclass;
	
	public int insert(IcsClassificationDo record) {
		return icsclass.insert(record);
	}
	
	public List<IcsClassificationVo> listAll() {
		return icsclass.listAll();
	}
	
	public List<IcsClassificationVo> listByQuery(Query query) {
		return icsclass.listByQuery(query);
	}
	
	public List<IcsClassificationVo> listTree(Query query) {
		return icsclass.listTree(query);
	}
	/**
	 * 展示所有的分类
	 * @return
	 */
	public List<IcsClassificationVo> findAll() {
		// TODO Auto-generated method stub
		return icsclass.listAll();
	}
}
