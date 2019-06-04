package com.folkestone.bzcx.controller.admin.type;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.type.StandardTypeDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.type.StandardTypeVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.type.StandardTypeService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/admin/Type")
public class StandardTypeController extends BaseController{


	@Autowired
	private StandardTypeService StandardType;
	
	/**
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/select",method = { RequestMethod.POST })
	public EasyUISeparatPage selectlist(HttpServletRequest req, @RequestParam Map<String, Object> param){
		Query query = new Query(param);
		@SuppressWarnings("unchecked")
		List<StandardTypeVo> attribute = (List<StandardTypeVo>) req.getSession().getAttribute("stanType");
		PageInfo<StandardTypeVo> pageInfo = new PageInfo<StandardTypeVo>(attribute);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	
	/**
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add",method = { RequestMethod.POST })
	public Result addType(HttpServletRequest req, StandardTypeDo record){
		int insert = StandardType.insert(record);
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
		EasyUISeparatPage selectAllforPage = StandardType.selectAllforPage(query);
		return selectAllforPage;
	}
	
	/**
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete",method = { RequestMethod.POST })
	public Result delete(HttpServletRequest req, String[] typeId){
		int delete = StandardType.delete(typeId);
		return returnResult(delete, "添加成功", "标识已存在", "添加失败");
	}
	
	/**
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update",method = { RequestMethod.POST })
	public Result update(HttpServletRequest req, StandardTypeDo record){
		int update = StandardType.update(record);
		return returnResult(update, "添加成功", "标识已存在", "添加失败");
	}
}
