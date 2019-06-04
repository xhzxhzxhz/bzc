package com.folkestone.bzcx.controller.admin.interaction;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.interact.S_Valid_ConfirmDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.interact.ValidatService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.UserService;

/**
 * 处理后台标准意见反馈维护
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/admin/validat")
public class ValidatController extends BaseController{
	@Autowired
	private ValidatService validatService;
	
	@Autowired
	private OperLogService operLogService;
	
	@Autowired
	private UserService userservice;
	
	/**
	 * 标准意见反馈单列表
	 * 加载数据
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method={RequestMethod.POST} )
	public EasyUISeparatPage replyList(@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage listByQuery = validatService.listByQuery(query);
		return listByQuery;
	}
	
	
	/**
	 * 标准意见反馈单的读取
	 * status   '1：未读；2：已读；';
	 * @param collectDo
	 * @return
	 * 
	 */ 
	@ResponseBody
	@RequestMapping(value = "/check", method={RequestMethod.POST} )
	public Result check(S_Valid_ConfirmDo validConfirmDo,HttpServletRequest req){
		Result result = new Result(false);
		
		return result;
	}
	
	
	/**
	 * 标准意见反馈单的删除，可批量
	 * @param validConfirmIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method={RequestMethod.POST} )
	public Result delete(String[] validConfirmIds,HttpServletRequest req){
		Result result = new Result(false);
		try {
			validatService.deleteByIds(validConfirmIds);
			result.setResult(true);
			for (String string : validConfirmIds) {
				operLogService.insert(super.getOperLog(req, "1024", "有效性删除" ,string));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
