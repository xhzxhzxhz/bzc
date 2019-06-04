package com.folkestone.bzcx.controller.admin.interaction;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.interact.R_MessagesDo;
import com.folkestone.bzcx.bean.bean_do.interact.R_Req_CollectDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureNodeVo;
import com.folkestone.bzcx.bean.bean_vo.interact.ReqCollectVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.ArchitectureNodeService;
import com.folkestone.bzcx.service.interact.CollectService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.UserService;




/**
 * 处理后台标准提案维护
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/collect")
public class CollectController extends BaseController{
	@Autowired
	private CollectService collectService;
	
	@Autowired
	private OperLogService operLogService;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private ArchitectureNodeService archnode;
	
	/**
	 * 标准提案的搜索  ,根据用户输入的关键字进行搜索
	 * @param feedbackId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method={RequestMethod.POST} )
	public Result search(String keyword){
		Result result = new Result(true);
		List<Map<Object,Object>> list = new ArrayList<Map<Object, Object>>();
		List<R_Req_CollectDo> collectDos = collectService.collectService(keyword);
		for (R_Req_CollectDo collectDo : collectDos) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			Object content = collectDo.getContent();
			map.put("content", content);
			list.add(map);
		}
		result.setData(list);
		return result;
	}
	
	
	
	/**
	 * 保存标准提案
	 * @param collectDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method={RequestMethod.POST})
	public Result insert(R_Req_CollectDo collectDo,HttpServletRequest req){
		Result result = new Result(false);
		Object attribute = req.getSession().getAttribute("loginUser");
		UserVo user=(UserVo) attribute;
		collectDo.setSuggestUser(user.getId());
		collectDo.setSuggestTime(new Date());
		try {
			int row = collectService.insert(collectDo);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 保存标准提案回复
	 * @param architectureDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insertReply", method={RequestMethod.POST})
	public Result insertReply(R_MessagesDo messagesDo){
		Result result = new Result(false);
		messagesDo.setReplyDate(new Date());
		messagesDo.setReplyUser("ADMIN_ID");//暂时定义为管理员回复，此处管理员id
		try {
			int row = collectService.insertReply(messagesDo);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 标准提案列表
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method={RequestMethod.POST} )
	public EasyUISeparatPage select(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage data = collectService.listCollectByQuery(query);
		for (Object obj : data.getRows()) {
			ReqCollectVo message = (ReqCollectVo) obj;
			UserVo usersugges = userservice.getUserById(message.getSuggestUser());
			message.setSuggestUser(usersugges.getAccount());
			if(message.getAuditUser() != null){
				UserVo useraudit = userservice.getUserById(message.getAuditUser());
				message.setAuditUser(useraudit.getAccount());
			}
			if(message.getBelongArchitecture() != null){
				ArchitectureNodeVo ar = archnode.getNodeById(message.getBelongArchitecture());
				if(ar !=null) {
					message.setBelongArchitecture(ar.getNodeCnName().toString());
				}
			}
		}
		return data;
	}
	
	/**
	 * 标准提案回复表列表
	 * 
	 * 使用json把数据传回前端
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/replyList", method={RequestMethod.POST} )
	public EasyUISeparatPage replyList(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage easy = collectService.listCollectByQuery(query);
		for (Object obj : easy.getRows()) {
			ReqCollectVo reqcollect = (ReqCollectVo) obj;
			UserVo usersugges = userservice.getUserById(reqcollect.getSuggestUser());
			reqcollect.setSuggestUser(usersugges.getAccount());
			if(reqcollect.getAuditUser() != null){
				UserVo useraudit = userservice.getUserById(reqcollect.getAuditUser());
				reqcollect.setAuditUser(useraudit.getAccount());
			}
		}
		return easy;
	}
	
	
	/**
	 * 标准提案的删除
	 * @param reqId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method={RequestMethod.POST} )
	public Result delete(String[] reqId,HttpServletRequest req){
		Result result = new Result(false);
		try {
			collectService.delete(reqId);
			result.setResult(true);
			for (String string : reqId) {
				operLogService.insert(super.getOperLog(req, "1021", "需求删除" ,string));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 标准提案的审核
	 * 其实就是修改标准提案的状态    
	 * STATUS:1：提出；2：有建设性的意见；3：忽略
	 * @param collectDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method={RequestMethod.POST} )
	public Result check(R_Req_CollectDo collectDo,HttpServletRequest req){
		Result result = new Result(false);
		UserVo user = (UserVo) req.getSession().getAttribute("loginUser");
		collectDo.setAuditDate(new Date());
		collectDo.setAuditUser(user.getId());
		try {
			int row = collectService.update(collectDo);
			if(row == 1){
				result.setResult(true);
				operLogService.insert(super.getOperLog(req, "1016", "需求审核" ,collectDo.getReqId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
