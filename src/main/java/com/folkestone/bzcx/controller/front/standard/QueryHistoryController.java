package com.folkestone.bzcx.controller.front.standard;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkestone.bzcx.bean.bean_do.user.U_Query_HistoryDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.user.QueryHistoryService;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

/**
 * Describe：返给前台查询历史，热点词的Controller
 * 
 * @author smallking
 *
 *  2017年11月13日
 */
@Controller
@RequestMapping(value = "/history")
public class QueryHistoryController extends BaseController{
	@Autowired
	private QueryHistoryService queryHistoryService;
	
	/**
	 *热门检索词
	 * @param param
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/hotword", method={RequestMethod.POST,RequestMethod.GET})
	public List<String> search(@RequestParam Map<String, Object> param) throws JsonParseException, JsonMappingException, IOException{
		Query query = new Query(param);
		List<String> list;
		Jedis jedis = new Jedis("localhost");
		String string = jedis.get("hotword"+query.get("moudle")+query.getPage().toString()+query.get("typeName"));
		System.out.println(string);
		char[] strs=null;
		if(string!=null) {
		strs=string.toCharArray();
			
		}
		if(string==null ||strs.length<=2 ) {
			if(query.get("typeName")!=null && !"".equals(query.get("typeName").toString())) {
				if(query.get("typeName").toString().equals("标准信息")) {
					query.put("searchClass", "1");
				}else {
					query.put("searchClass", "0");
				}
			}
			list = queryHistoryService.listHotByQuery(query);
			JSONArray jsonObject = JSONArray.fromObject(list);
	        //将json转换为json字符串
	        String str = jsonObject.toString();
	       // jedis.set(key, str);
	        jedis.set("hotword"+query.get("moudle")+query.getPage().toString()+query.get("typeName"), str, "NX", "EX", 1800);
		}else {
			ObjectMapper mapper = new ObjectMapper(); 
			list = mapper.readValue(string , List.class);
		}
		return list;
	}
	
	/**
	 *历史检索词
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userhistory", method={RequestMethod.POST,RequestMethod.GET})
	public List<String> userhistory(HttpServletRequest req ,@RequestParam Map<String, Object> param){
		Query query = new Query(param);
		UserVo user = super.getUser(req);
		query.put("useropenid", user.getId());
		if(query.get("typeName")!=null && !"".equals(query.get("typeName").toString())) {
			if(query.get("typeName").toString().equals("标准信息")) {
				query.put("searchClass", "1");
			}else {
				query.put("searchClass", "0");
			}
		}
		List<String> list = queryHistoryService.listHistoryByQuery(query);
		return list;
	}
	
	
	/**
	 *历史检索词
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method={RequestMethod.POST})
	public Result update(HttpServletRequest req ){
		Result re = new Result(false);
		UserVo user = super.getUser(req);
		U_Query_HistoryDo queryHistoryDo = new U_Query_HistoryDo();
		queryHistoryDo.setSearchUser(user.getId());
		queryHistoryDo.setState(1);
		int update = queryHistoryService.update(queryHistoryDo);
		if(update > 0) {
			re.setResult(true);
		}
		return re;
	}
	
}
