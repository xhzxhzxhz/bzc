package com.folkestone.bzcx.controller.admin.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_vo.message.message;
import com.folkestone.bzcx.service.message.MessageService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/admin/message")
public class MessageController {
	
	@Autowired
	private MessageService mess;
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/Sum",method = { RequestMethod.POST })
	public EasyUISeparatPage SumMessage(){
		List<message> sum = mess.Summessage();
		PageInfo<message> pageInfo = new PageInfo<message>(sum);
		EasyUISeparatPage easy = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return easy;
	}
}
