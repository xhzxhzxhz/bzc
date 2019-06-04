package com.folkestone.bzcx.controller.front.collection;

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

import com.folkestone.bzcx.bean.bean_do.collection.RCollectionDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.collection.RCollectionService;
import com.folkestone.bzcx.service.dm.LawsService;
import com.folkestone.bzcx.service.dm.StandardService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/collection")
public class RCollectionController extends BaseController{
	
	@Autowired
	private RCollectionService collection;
	@Autowired
	private StandardService standardService;
	@Autowired
	private LawsService lawsService;
	
	/**
	 * 列表
	 * @param req
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method={RequestMethod.POST,RequestMethod.GET})
	public EasyUISeparatPage search(HttpServletRequest req, @RequestParam Map<String, Object> param){
		Query query = new Query(param);
		UserVo user = getUser(req);
		query.put("userId", user.getId());
		EasyUISeparatPage listByQueryPage = collection.listByQueryPage(query);
		return listByQueryPage;
	}
	/**
	 * 用户收藏展示
	 * @param req
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/show", method={RequestMethod.POST,RequestMethod.GET})
	public EasyUISeparatPage show(HttpServletRequest req){
		Query query = new Query();
		UserVo user = getUser(req);
		query.put("userId", user.getId());
		//EasyUISeparatPage listByQueryPage = collection.listByQueryPage(query);
		//直接通过当前的用户去查询所有的收藏
		List<Map<String,Object> > list =collection.show(query);
		//通过当前用户的id去查询所有的收藏的内容，而收藏列表中的数据是只有标准中的数据，而没有法律法规中的数据，所有，我们需要去在数据库中查询出当前用户所有的收藏列表的 数据，然后将其
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		if(list!=null && list.size()>0) {
			//然后我们就可以遍历这个list，然后将满足条件的数据展示在哪
			for(Map<String,Object> map:list) {
				String standardId=(String)map.get("STANDARD_ID");
				String collectionId=(String) map.get("COLLECTION_ID");
				StandardVo standardVo=	standardService.findstandardById(standardId);
			
				if(standardVo!=null) {
					Map<String,Object> map1 = new HashMap<String,Object>();
					map1.put("standardId", standardVo.getStandardId());
					map1.put("standardCnName", standardVo.getStandardCnName());
					map1.put("collectionId", collectionId);
					map1.put("publishDate", standardVo.getPublishDate());
					map1.put("paramNo", standardVo.getStandardNo());
					map1.put("effectivenessLevel", "无");
					map1.put("type","standard");
					resultList.add(map1);
				}else {
					//String noTirm=StringUtil.trim(standardId);
					//LawsVo lawsVo=	lawsService.findByLawsNoTrim(noTirm);
					LawsVo lawsVo=	lawsService.findLawsById(standardId);
					if(lawsVo!=null) {
						Map<String,Object> map1 = new HashMap<String,Object>();
						map1.put("standardId", lawsVo.getLawsId());
						map1.put("standardCnName", lawsVo.getLawsCnName());
						map1.put("collectionId", collectionId);
						map1.put("publishDate", lawsVo.getPublishDate());
						map1.put("paramNo", lawsVo.getLawsNo());
						map1.put("effectivenessLevel", lawsVo.getEffectivenessLevel());
						map1.put("type","lawsVo");
						resultList.add(map1);
					}
				}
			}
		}
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(resultList);
		EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		
		return result;
	}
	
	
	/**
	 * 添加收藏
	 * @param req
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method={RequestMethod.POST})
	public Result insert(HttpServletRequest req, RCollectionDo record){
		Result re = new Result(false);
		UserVo user = getUser(req);
		record.setUserId(user.getId());
		record.setCollectionDate(new Date());
		record.setDeleteState(0);
		record.setViewTimes(1);
		int insert = collection.insert(record);
		if(insert > 0) {
			re.setCode(200);
			re.setResult(true);
			re.setData(record.getCollectionId());
		}else {
			re.setCode(203);
			re.setMsg("添加失败");
		}
		return re;
	}
	
	@ResponseBody
	@RequestMapping(value = "/update", method={RequestMethod.POST})
	public Result update(HttpServletRequest req, RCollectionDo record){
		return  returnResult(collection.update(record), "添加成功", "添加失败", "添加失败");
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete", method={RequestMethod.POST})
	public Result delete(HttpServletRequest req, String collectionId){
		System.out.println(collectionId);
		return  returnResult(collection.delete(collectionId), "删除成功", "删除失败", "删除失败");
	}
	
	@ResponseBody
	@RequestMapping(value = "/deletes", method={RequestMethod.POST})
	public Result deletes(HttpServletRequest req,@RequestParam(value = "collectionId[]") String[] collectionId){
		return  returnResult(collection.delete(collectionId), "添加成功", "添加失败", "添加失败");
	}
	
}
