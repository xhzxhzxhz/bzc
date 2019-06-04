package com.folkestone.bzcx.controller.admin.type;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.type.TypeDictionariesDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.type.TypeDictionariesService;

@Controller
@RequestMapping("/admin/dictionaries")
public class TypeDictionariesController extends BaseController{

	@Autowired
	private TypeDictionariesService diction;
	
	/**
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add",method = { RequestMethod.POST })
	public Result addType(HttpServletRequest req, TypeDictionariesDo record){
		int insert = diction.insert(record);
		return returnResult(insert, "添加成功", "标识已存在", "添加失败");
	}
	
	
	/**
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list",method = { RequestMethod.POST })
	public EasyUISeparatPage list(HttpServletRequest req, @RequestParam Map<String, Object> param){
		Query query = new Query(param);
		EasyUISeparatPage selectAllforPage = diction.selectByQueryforPage(query);
		return selectAllforPage;
	}
	
	/**
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete",method = { RequestMethod.POST })
	public Result delete(HttpServletRequest req, String[] dictionariesId){
		int delete = diction.delete(dictionariesId);
		return returnResult(delete, "添加成功", "标识已存在", "添加失败");
	}
	
	/**
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update",method = { RequestMethod.POST })
	public Result update(HttpServletRequest req, TypeDictionariesDo record){
		int update = diction.update(record);
		return returnResult(update, "添加成功", "标识已存在", "添加失败");
	}
	
}
