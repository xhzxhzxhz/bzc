package com.folkestone.bzcx.controller.admin.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.user.OperLogService;

@Controller
@RequestMapping("/admin/operlog")
public class UserOperLogController extends BaseController {
	
	@Autowired
	private OperLogService operLogService;
	
	@ResponseBody 
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public EasyUISeparatPage listUserByQuery(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage data = operLogService.listOperLogByQuery(query);
		return data;
	}
	
	@ResponseBody 
	@RequestMapping(value="/deleByDate",method=RequestMethod.POST)
	public int listUserByQuery(String startTime){
		int row = operLogService.deleteByDate(startTime);
		return row;
	}

}
