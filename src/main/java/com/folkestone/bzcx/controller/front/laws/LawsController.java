package com.folkestone.bzcx.controller.front.laws;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.LawsService;
import com.github.pagehelper.PageInfo;
/**
 * 法律法规的控制层
 * @author zqm
 *
 */
@RestController
@RequestMapping("/laws")
public class LawsController extends BaseController {
	@Autowired
	private LawsService lawsService;
	/**
	 * 高级查询中的二次筛选的第一次查询的参数的展示
	 * @return
	 */
	@RequestMapping(value="/showParam" ,method=RequestMethod.GET)
	public Result showParam(HttpServletRequest req) {
		Result result = new Result();
		Map<String,Object> map = (Map<String, Object>) req.getSession().getAttribute("param_laws");
		if(map!=null) {
			result.setCode(200);
			result.setData(map);
		}else {
			result.setCode(500);
			result.setMsg("在session中没有得到数据，请检查代码的正确性!");
		}
		return result;
	}
	/**
	 * 高级查询的第二次筛选，从session域中获取我们保存的数据
	 * @param req
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/second/search",method=RequestMethod.POST)
	public EasyUISeparatPage secondSearch(HttpServletRequest req,@RequestParam Map<String,Object> param)  {
		try {
			super.setParam(req, param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		List<LawsVo> resultList=null;
		try {
			Map<String,Object> paramMap= new HashMap<String,Object>();
			//从session中获取我们的第一次查询的请求参数
			Map<String,Object> map=(Map<String, Object>) req.getSession().getAttribute("param_laws");
			//然后将其装成新的参数，然后放到Map中
			if(param!=null) {
				for(Map.Entry<String, Object> entry : param.entrySet()){
					 String mapKey = entry.getKey();
					 Object mapValue =  entry.getValue();
					 paramMap.put(mapKey, mapValue);
				}
				paramMap.put("lawsCnName", param.get("paramCnName"));
			}
			
			if(map!=null) {
				paramMap.put("lawsCnName02", map.get("paramCnName"));
				paramMap.put("pubOrg02", map.get("pubOrg"));
				paramMap.put("lawsNo02", map.get("paramNo"));
				paramMap.put("effectivenessLevel02", map.get("effectivenessLevel"));
				paramMap.put("relation", map.get("relation"));
				
				if(param!=null) {
					Date begDate=null;
					Date endDate=null;
					Date beginImpl=null;
					Date endUmpl=null;
					Date secBeDate=null;
					Date secEndDate=null;
					Date secImplBe=null;
					Date secImplEnd=null;
						//前台传过来的是一个字符串
					String date=(String) param.get("publishDatebegin");
					String endDa=(String) param.get("publishDateend");
					String implBegin=(String) param.get("implementationDatebegin");
					String implend=(String) param.get("implementationDateend");
					//这点报错 就是类型转化不一致
					//将这个字符串转化成日期对象然后将其 转化成毫秒值 进行大小的比较
					//2019-03-28 08:37:38
					DateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if(date!=null && !"".equals(date)) {
						begDate=(Date) sdf.parse(date);
						
					}
					if(endDa!=null && !"".equals(endDa)) {
						//int beginDa=Integer.parseInt(date);
						 endDate=(Date) sdf.parse(endDa);
						
					}
					if(implBegin!=null && !"".equals(implBegin)) {
						begDate=(Date) sdf.parse(date);
						//int endDa=Integer.parseInt(endDate);
						 beginImpl=(Date) sdf.parse(implBegin);
						//int implBe=Integer.parseInt(implBegin);
					}
					if(implend!=null && !"".equals(implend)) {
						endUmpl=(Date) sdf.parse(implend);
						//int implen=Integer.parseInt(implend);
						
					}
					//二次
					String mapBe=(String) map.get("publishDatebegin");
					String mapEn=(String) map.get("publishDateend");
					String mapImBe=(String) map.get("implementationDatebegin");
					String mapImen=(String) map.get("implementationDateend");
					if(mapBe!=null&& !"".equals(mapBe)) {
					 secBeDate=(Date) sdf.parse(mapBe);
					}
					if(mapEn!=null && !"".equals(mapEn)) {
						secEndDate=(Date) sdf.parse(map.get("publishDateend").toString());
					}
					if(mapImBe!=null && !"".equals(mapImBe)) {
					 secImplBe=(Date) sdf.parse(map.get("implementationDatebegin").toString());
					}
					if(mapImen!=null && !"".equals(mapImen)) {
						 secImplEnd=(Date) sdf.parse(map.get("implementationDateend").toString());
					}
					if(begDate!=null && secBeDate!=null) {
						if(begDate.after(secBeDate)) {
							paramMap.put("publishDatebegin",date );
						}else {
							paramMap.put("publishDatebegin",map.get("publishDatebegin").toString());
					}	
					}
					if(endDate!=null &&secEndDate!=null ) {
						if(endDate.after(secEndDate)) {
							paramMap.put("publishDateend",endDate );
							
						}else {
							paramMap.put("publishDateend",map.get("publishDateend").toString() );
						}
					}
					
					if(beginImpl!=null && secImplBe!=null ) {
						if(beginImpl.after(secImplBe)) {
							paramMap.put("implementationDatebegin", implBegin);
						}else {
							paramMap.put("implementationDatebegin",map.get("implementationDatebegin").toString() );
						}
					}
					if(endUmpl!=null && secImplEnd!=null ) {

						if(endUmpl.after(secImplEnd)) {
							paramMap.put("implementationDatebegin", implend);
						}else {
							paramMap.put("implementationDateend",map.get("implementationDateend").toString() );
						}
					}
					
					
				}
				String order=(String) param.get("order");
				if(order!=null && !"".equals(order)) {
					if(("标题名称").equals(order)) {
						paramMap.put("order","LAWS_CN_NAME");
					}
					if("实施日期".equals(order)) {
						paramMap.put("order","IMPLEMENTATION_DATE");
					}
				
					
				}
			
			
			}

			resultList=lawsService.secondSearch(paramMap);	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		PageInfo<LawsVo> pageInfo = new PageInfo<LawsVo>(resultList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		
		
		return data;
	}
	

}
