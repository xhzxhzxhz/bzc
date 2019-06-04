package com.folkestone.bzcx.controller.front.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.FileStatisticVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.DateFormatUtil;
import com.folkestone.bzcx.common.util.ExcelUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.FileStatisticsService;
import net.sf.json.JSONObject;

/**
 * Describe：统计分析页面 main_bot数据请求
 * 
 * @author jyp
 *
 *  2017年11月28日
 */
@Controller
@RequestMapping(value = "/statistics")
public class FileStatisticsController extends BaseController{
	@Autowired
	private FileStatisticsService fileStatisticsService;
	@ResponseBody
	@RequestMapping(value = "/liststatistics", method = RequestMethod.POST)
	public Map<String, List<Object>> list(@RequestParam Map<String, Object> param){  //返回一个json  
		   List<Object> list = new ArrayList<Object>();
		   Query query = new Query(param);
		   Map<String, List<Object>> SystemAccess = new HashMap<String,List<Object>>(); 
		   list.add(fileStatisticsService.StatisticAnalysis());
		   SystemAccess.put("StatisticAnalysis", list);                                 //查询单个值 ，放入json 
		  SystemAccess.put("SystemAccess", fileStatisticsService.SystemAccess()); //1访问量排行
		   SystemAccess.put("StandardClassifica", fileStatisticsService.StandardClassificalis());  //2标准分类统计
		   SystemAccess.put("SearchWordHeat", fileStatisticsService.SearchWordHeatlis(query));  //3检索词热度排行
		   /* SystemAccess.put("TwoLevelQuantity", fileStatisticsService.TwoLevelQuantitylis(query,"1")); //4二级单位标准数量排行
		   SystemAccess.put("TwoLevelUser", fileStatisticsService.TwoLevelUserlist(query,"1")); 	//6二级单位用户数排行
		   SystemAccess.put("TwoLevelAccess", fileStatisticsService.TwoLevelAccesslis());*/ //5二级单位访问量排行
		   SystemAccess.put("StandardUsage", fileStatisticsService.StandardUsage(query)); 	//7标准使用率排行
		 /*  SystemAccess.put("RetWords", fileStatisticsService.RetWords(query)); */	//8二级单位与检索词统计
		     return SystemAccess;
	}
	@ResponseBody
	@RequestMapping(value = "/SearchWord", method = RequestMethod.POST)
	public Map<String, List<Object>> SearchWord(@RequestParam Map<String, Object> param){  //返回一个json  
		   Query query = new Query(param);
		   Map<String, List<Object>> SystemAccess = new HashMap<String,List<Object>>(); 
		   SystemAccess.put("SearchWordHeat", fileStatisticsService.SearchWordHeatlis(query));  //3检索词热度排行
		     return SystemAccess;
	}
	/*@ResponseBody
	@RequestMapping(value = "/TwoLevelQuantitylis", method = RequestMethod.POST)
	public Map<String, List<Object>> TwoLevelQuantitylis(@RequestParam Map<String, Object> param){  //返回一个json  
		   Query query = new Query(param);
		   Map<String, List<Object>> SystemAccess = new HashMap<String,List<Object>>(); 
		   SystemAccess.put("TwoLevelQuantity", fileStatisticsService.TwoLevelQuantitylis(query,"1")); //4二级单位标准数量排行
		     return SystemAccess;
	}*/
	/*@ResponseBody
	@RequestMapping(value = "/TwoLevelUserlist", method = RequestMethod.POST)
	public Map<String, List<Object>> TwoLevelUserlist(@RequestParam Map<String, Object> param){  //返回一个json  
		   Query query = new Query(param);
		   Map<String, List<Object>> SystemAccess = new HashMap<String,List<Object>>(); 
		   SystemAccess.put("TwoLevelUser", fileStatisticsService.TwoLevelUserlist(query,"1")); 	//6二级单位用户数排行
		     return SystemAccess;
	}*/
	@ResponseBody
	@RequestMapping(value = "/StandardUsage", method = RequestMethod.POST)
	public Map<String, List<Object>> StandardUsage(@RequestParam Map<String, Object> param){  //返回一个json  
		   Query query = new Query(param);
		   Map<String, List<Object>> SystemAccess = new HashMap<String,List<Object>>(); 
		   SystemAccess.put("StandardUsage", fileStatisticsService.StandardUsage(query)); 	//7标准使用率排行
		     return SystemAccess;
	}

	/*@ResponseBody
	@RequestMapping(value = "/RetWords", method = RequestMethod.POST)
	public Map<String, List<Object>> RetWords(@RequestParam Map<String, Object> param){  //返回一个json  
		   Query query = new Query(param);
		   Map<String, List<Object>> SystemAccess = new HashMap<String,List<Object>>(); 
		   SystemAccess.put("RetWords", fileStatisticsService.RetWords(query)); 	//8二级单位与检索词统计
		     return SystemAccess;
	}*/
	/*@ResponseBody
	@RequestMapping(value = "/More", method = RequestMethod.POST)
	public JSONObject listMore(@RequestParam Map<String, Object> param){  //返回一个json  
		   List<Object> list = new ArrayList<Object>();
		   Query query = new Query(param);
		   Map<String, List<Object>> SystemAccess = new HashMap<String,List<Object>>(); 
		   list.add(fileStatisticsService.StatisticAnalysis());
		   SystemAccess.put("TwoLevelQuantity", fileStatisticsService.TwoLevelQuantitylis(query,"2")); //4二级单位标准数量排行
		   SystemAccess.put("TwoLevelUser", fileStatisticsService.TwoLevelUserlist(query,"2")); 	//6二级单位用户数排行
		   JSONObject jsb=JSONObject.fromObject(SystemAccess);
		       return jsb;
	}*/
	@ResponseBody
	@RequestMapping(value = "/Export",method={RequestMethod.GET, RequestMethod.POST})
		
		 public void ExcelTest(HttpServletResponse response){
		 /* List<AuContract> list = new ArrayList<AuContract>();
          list= service.findAuContractList();//获得数据库所有的合同集合
          Map<String,List<AuContract>> map = new HashMap<String, List<AuContract>>();  
          map.put("infoList", list);  
          ExcelView ve = new ExcelView();  
          return new ModelAndView(ve,map);  */
		  //构建数据源
		 Map<String, List<FileStatisticVo>> SystemAccess = new HashMap <String, List<FileStatisticVo>>(); 
		 SystemAccess=fileStatisticsService.ExcelTest();
       
        //每页的名称
        String [] sheetNameStrings={"概况","系统访问量排行","标准分类统计","检索词热度排行","二级单位标准数量排行","二级单位访问量排行","二级单位用户数排行","标准使用率排行","二级单位与检索词统计"};
        String modelURLString=ContantFinalUtil.BASE_PATH+"\\model\\model.xls";
        String Excelname  = "统计分析数据导出（"+DateFormatUtil.date2Str(new Date())+"）" ;
        try {
            ExcelUtil.ExcelByModel(Excelname, modelURLString, SystemAccess, response, sheetNameStrings);
        } catch (Exception e) {
            e.printStackTrace();
        }
		        
		    }
	
}
