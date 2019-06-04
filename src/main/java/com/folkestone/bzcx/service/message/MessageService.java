package com.folkestone.bzcx.service.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_vo.message.message;
import com.folkestone.bzcx.mapper.message.messageMapper;

@Service
public class MessageService {
	
	@Autowired
	private messageMapper mess;
	
	public List<message> Summessage(){
		return mess.SumMessage();
	}
	
}
