package com.folkestone.bzcx.service.interact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.interact.RInformationDo;
import com.folkestone.bzcx.mapper.interact.RInformationMapper;

@Service
public class RInformationService {
	@Autowired
	private RInformationMapper infor;
	
	public int insert(RInformationDo record) {
		return infor.insert(record);
	}
	
	public int update(RInformationDo record) {
		return infor.update(record);
	}
	
	public int delete(String informationId) {
		return infor.delete(informationId);
	}
}
