package com.folkestone.bzcx.controller.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkestone.bzcx.bean.bean_do.user.U_Oper_LogDo;
import com.folkestone.bzcx.bean.bean_do.user.U_Query_HistoryDo;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.service.user.QueryHistoryService;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

public class BaseController {
	/**
	 * 设置字符集
	 * @param resp
	 */
	protected void setCharSet(HttpServletResponse resp)
	{
		resp.setContentType("text/html;charset=UTF-8");
		resp.setCharacterEncoding("utf-8");
	}
	
	
	protected List<?> haveRedis(List<?> data,String key) throws JsonParseException, JsonMappingException, IOException{
		Jedis jedis = new Jedis("localhost");
		String string = jedis.get(key);
		if(string != null) {
			ObjectMapper mapper = new ObjectMapper(); 
	        List<?> stu = mapper.readValue(string , List.class);
			return stu;
		}else {
			JSONObject jsonObject = JSONObject.fromObject(data);
	        //将json转换为json字符串
	        String str = jsonObject.toString();
	       // jedis.set(key, str);
	        jedis.set(key, str, "NX", "EX", 1800);
	        //jedis.expire(key,1800);
	        return data;
		}
	}
	
	protected Result returnResult(int bo,String truemesg,String have,String falsemesg) {
		Result result = new Result(false);
		if(bo > 0) {
			result.setResult(true);
			result.setMsg(truemesg);
		}else if(bo == -1){
			result.setCode(203);
			result.setMsg(have);
		}else{
			result.setCode(402);
			result.setMsg(falsemesg);
		}
		return result;
	} 
	
	protected Result returnResult(Object obj,int bo,String truemesg,String have,String falsemesg) {
		Result result = new Result(false);
		if(bo > 0) {
			result.setData(obj);
			result.setResult(true);
			result.setMsg(truemesg);
		}else if(bo == -1){
			result.setCode(203);
			result.setMsg(have);
		}else{
			result.setCode(402);
			result.setMsg(falsemesg);
		}
		return result;
	} 
	
	/**
	 * 将vo的值 复制到do上
	 * @param fileVo
	 * @param fileDo
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected void copy(Object voObj, Object doObj) throws IllegalArgumentException, IllegalAccessException {
		Field[] voFields = voObj.getClass().getDeclaredFields();
		Field[] doFields = doObj.getClass().getDeclaredFields();
		for(Field voField : voFields)
		{
			voField.setAccessible(true);
			for(Field doField : doFields){
				doField.setAccessible(true);
				if(doField.getName().equals(voField.getName()) && voField.get(voObj) != null){
					doField.set(doObj, voField.get(voObj));
				}
			}
		}
	}
	/**
	 * Spring下载文件公用
	 * @param fileFullPath ：服务器上的文件路径
	 * @param fileName ：返给客户端的文件名称
	 * @return 
	 * @throws IOException 
	 */
	public ResponseEntity<byte[]> download(String fileFullPath, String fileName) throws IOException {
		/*if(fileFullPath!=null) {
			  int idx = fileFullPath.lastIndexOf("\\");
			  fileFullPath = fileFullPath.substring(idx + 1, fileFullPath.length());
		}*/
		File file = new File(ContantFinalUtil.BASE_PATH+fileFullPath);
		HttpHeaders headers = new HttpHeaders();
		fileName = new String((fileName).getBytes("UTF-8"), "iso-8859-1");
		// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}
	
	/**
	 * Spring下载文件公用
	 * @param fileFullPath ：服务器上的角对文件路径 
	 * @param fileName ：返给客户端的文件名称
	 * @return 
	 * @throws IOException 
	 */
	public ResponseEntity<byte[]> downloadEcel(String fileFullPath, String fileName) throws IOException {
		/*if(fileFullPath!=null) {
			  int idx = fileFullPath.lastIndexOf("\\");
			  fileFullPath = fileFullPath.substring(idx + 1, fileFullPath.length());
		}*/
		File file = new File(fileFullPath);
		HttpHeaders headers = new HttpHeaders();
		fileName = new String((fileName).getBytes("UTF-8"), "iso-8859-1");
		// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}
	/**
	 * 获得当前登录的人的对象
	 * @param req
	 */
	protected UserVo getUser(HttpServletRequest req) {
		Object obj = req.getSession().getAttribute("loginUser");
		if(obj != null){
			return (UserVo)obj;
		}
		return null;
	}
	/**
	 * 将前台分页插件封装到Request PayLoad 里面的参数
	 * 通过流读取出来，然后设置到Query里面
	 * @param req
	 * @param query
	 * @return
	 * @throws IOException 
	 */
	protected void setParam(HttpServletRequest req, Map<String, Object> param) throws IOException{
		StringBuilder str = new StringBuilder();
    	BufferedReader reader = req.getReader();
        char[]buff = new char[1024];
        int len;
        while((len = reader.read(buff)) != -1){
             str.append(buff,0, len);
        }
        String paramStr = str.toString();
        if(StringUtil.isEmpty(paramStr)){
        	return;
        }
        String[] paramArray = paramStr.split("&");
		for(String data : paramArray){
			String[] split = data.split("=");
			if(split.length == 2){
				String key = data.split("=")[0];
				String value = data.split("=")[1];
				param.put(key, value);
			}
		}
	}

	/**
	 * 得到当前运行的主机地址和端口号
	 * @param req
	 * @return
	 */
	protected String getAddr(HttpServletRequest req) {
		// getLocalAddr
		String address = "http://" + req.getServerName() + ":" + req.getLocalPort(); 
		return address;
	}
	/**
	 * 得到当前请求的完整的url
	 * @return
	 */
	protected String getFullUrl(HttpServletRequest req){
		String url = "http://" + req.getServerName() + ":"   
					+ req.getServerPort()	// 服务器端口
					+ req.getContextPath() //项目名称  
					+ req.getServletPath(); //请求页面或其他地址  
		return url;
	}
	/**
	 * 得到用户当前操作日志的对象
	 * @param req
	 * @param string
	 * @param string2
	 * @param object
	 * @return
	 */
	/*
	 * 日志规则
	 * 当前拥有的操作为登录、查询、查看题录、查看原文
	 * OPER_TYPE 操作类型：
	 * 1001登录页面，OPER_DETAIL为访问的页面，MAPPING_ID为登录人的id
	 * 2001查看题录OPER_DETAIL为题录的标准名称，MAPPING_ID为题录标准号
	 * 2002查看原文OPER_DETAIL为原文的标准号，MAPPING_ID为原文文件路径名称
	 * 3001下载原文保留
	 * *4001简单查询，OPER_DETAIL为查询的关键词，MAPPING_ID为查询出的标准数量
	 * 4002高级查询 ，
	 * 4003分类查询
	 * 4004全文查询
	 * 4005二次检索
	 * 5001意见反馈
	 * */
	protected U_Oper_LogDo getOperLog(HttpServletRequest req, String operType, String operDetail, String mappingId) {
		U_Oper_LogDo log = new U_Oper_LogDo();
		String url = getFullUrl(req);
		Object obj = req.getSession().getAttribute("loginUser");
		if(obj != null){
			UserVo user = (UserVo)obj;
			log.setOperUser(user.getId());
		}else{
			log.setOperUser("未知用户");
		}
		
		log.setOperUrl(url);
		log.setMappingId(mappingId);
		log.setOperType(operType);
		if(!StringUtil.isEmpty(operDetail)){
			log.setOperDetail(operDetail);
		}
		return log;
	}
	
	
	/**
	 * 记录用户的检索历史
	 * @param user 
	 * @param request
	 */
	protected void recordQueryHistory(HttpServletRequest req, Map<String, Object> paramMap, QueryHistoryService queryHistoryService) {
		Object obj = req.getSession().getAttribute("loginUser");
		if(obj == null){
			return ;
		}
		UserVo user = (UserVo)obj;
		List<String> keywordList = new ArrayList<String>();
		for(String key : paramMap.keySet()){
			String value = paramMap.get(key) + "";
			if(StringUtil.isEmpty(value)){
				continue;
			}
			if(!ContantFinalUtil.PARAMTER_FILTER_LIST.contains(key) && !keywordList.contains(value) && !StringUtil.isEmpty(value)){
				keywordList.add(value);
			}
		}
		/***4001简单查询，OPER_DETAIL为查询的关键词，MAPPING_ID为查询出的标准数量
	 * 4002高级查询
	 * 4003分类查询
	 * 4004全文查询
	 * 4005二次检索
	 * 5001意见反馈*/
		String moudle = paramMap.get("moudle") + "";
		String ip = req.getRemoteAddr();
		String url = req.getRequestURI();
		for(String keyword : keywordList){
			U_Query_HistoryDo queryHistory = new U_Query_HistoryDo();
			queryHistory.setIp(ip);
			queryHistory.setKeyword(keyword);
			queryHistory.setMoudle(moudle);
			queryHistory.setQueryDate(new Date());
			queryHistory.setSearchUrl(url);
			queryHistory.setSearchUser(user.getId());
			queryHistory.setState(0);
			queryHistoryService.insert(queryHistory);
		}
		
	}
	
}
