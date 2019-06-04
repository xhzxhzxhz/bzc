package com.folkestone.bzcx.controller.front.interaction;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.folkestone.bzcx.bean.bean_do.interact.S_Valid_ConfirmDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.websocket.WebSocket;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.StandardService;
import com.folkestone.bzcx.service.interact.ValidatService;
import com.folkestone.bzcx.service.user.OperLogService;


@Controller
@RequestMapping("/validatform")
public class HeadValidatControllerForm extends BaseController{
	
	@Autowired
	private ValidatService validatService;
	
	@Autowired
	private OperLogService operLogService;
	
	@Autowired
	private StandardService standardservice;
	
	private Session session;
	
	/**
	 * 有效性确认单提交\
	 * 传uuidvalidConfirmId，外键指向题录的idstandardId，标准状态suggestStatus，描述comment，
	 * 因为一般是用户提交，所以使用session得到用户id
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/addvalida", method={RequestMethod.POST})
	public Result addvalida(HttpServletRequest req,S_Valid_ConfirmDo valid){
		Result result = new Result(false);
		//获得用户信息
		Object attribute = req.getSession().getAttribute("loginUser");
		UserVo user=(UserVo) attribute;
		if(user != null){
			valid.setStatus(Short.valueOf("1"));
			valid.setApplyUser(user.getId());
			valid.setApplyDate(new Date());
			int num = validatService.addValida(valid);
			if(num == 1){
				result.setResult(true);
				operLogService.insert(super.getOperLog(req, "1013", valid.getStandardId()+ "" ,valid.getValidConfirmId()));
			}
		}
		return result;
	}
	
	/**
	 * 
	 * 搜索题录
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method={RequestMethod.POST})
	public EasyUISeparatPage Search(@RequestParam Map<String, Object> param) throws JsonParseException, JsonMappingException, IOException{
		Query query = new Query(param);
		query.put("isback", "back");
		EasyUISeparatPage listStandard = standardservice.listStandardByQuery(query);
		return listStandard;
	}
	
	@ResponseBody
	@RequestMapping(value = "/message", method={RequestMethod.POST})
	public Result getSocketMessage(){
		Result result = new Result(true);
		WebSocket webs = new WebSocket();
		webs.onMessage( "validat" , session);
		return result;
	}
}
