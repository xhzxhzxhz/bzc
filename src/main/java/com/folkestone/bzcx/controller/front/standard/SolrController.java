package com.folkestone.bzcx.controller.front.standard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.dm.SolrSearchResult;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StdFileVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.JurisdictionUtil;
import com.folkestone.bzcx.common.util.PaginationUtil;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.SolrService;
import com.folkestone.bzcx.service.dm.StandardFileService;
import com.folkestone.bzcx.service.dm.StandardService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.QueryHistoryService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="/solr")
public class SolrController extends BaseController{
	
	@Autowired
	private SolrService solrService;
	@Autowired
	private StandardFileService standardFileService;
	@Autowired
	private QueryHistoryService queryHistoryService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private OperLogService operLogService;
	/**
	 * 标准的全文检索
	 * @param req
	 * @param keyword
	 * @return
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@ResponseBody
	@RequestMapping(value = "/search")
	public EasyUISeparatPage globalSearch(HttpServletRequest req, String keyword, 
			@RequestParam(value = "page", defaultValue = "1")Integer start, 
			@RequestParam(value = "rows", defaultValue = "10")Integer limit) throws IOException, SolrServerException{
		// UserVo user = super.getUser(req);
		 keyword =escapeQueryCharsTemp(keyword);
		 System.out.println("start:"+start);
		 System.out.println("limit:"+limit);
		// 查出solr库中所有符合关键词的数据(当前查询没有加入权限控制) 加权限就在query里面加条件就可以了
		// 查出符合条件的题录的原文记录
		 Map<String, Object> param = new HashMap<>();
		 param = JurisdictionUtil.searchJurisdiction(req, param);
		Query query = new Query(param);
		query.put("standardCas", "1");
		/*if(StringUtil.isEmpty(req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) + "")){
			query.put("standardType", "G");
		}*/
		List<StdFileVo> stdFileList = standardFileService.listStdFileQueryNoPage(query);
		String idStr = "";
		for(Object obj : stdFileList){
			StdFileVo stdFileVo = (StdFileVo)obj; 
			if(StringUtil.isEmpty(stdFileVo.getStandardId())){
				continue;
			}
			idStr += "id:" + stdFileVo.getStdFileId() + " OR ";
		}
		if(idStr.length() >= 3){
			idStr = idStr.substring(0, idStr.length() - 3);
		}
		if(stdFileList == null || stdFileList.size() == 0){
			return new EasyUISeparatPage();
		}
		// 查询出的没有过滤的solr记录
		List<SolrSearchResult> dataList = solrService.selectContentFormSolr(req,idStr, keyword,limit,start);
		// 存储最终结果的集合
		List<SolrSearchResult> resultList = new ArrayList<SolrSearchResult>();
		// 预览时是pdf文件 检索是txt文件
		for(SolrSearchResult row : dataList){
			List<SolrSearchResult> tempList = getDataFromList(dataList, row);
				for(SolrSearchResult tempData : tempList){
					if(tempData.getFileName().toLowerCase().endsWith("pdf") || tempData.getFileName().toLowerCase().endsWith("word")){
						String clean = tempData.getFileName();
						/*if(clean.lastIndexOf(".")>0) {
							clean = clean.substring(0, clean.lastIndexOf("."));
						}*/
						row.setFileName(clean);
					}else if(tempData.getFileName().toLowerCase().endsWith("txt")){
						row.setFileContent(tempData.getFileContent());
					}
				}
			if(!resultList.contains(row)){	
				StandardVo standardVo = standardService.getStandardByNo(row.getStandardNo());
				String clean = standardVo.getStandardCnName()+"";
				if(clean.lastIndexOf(".")>0) {
					clean = clean.substring(0, clean.lastIndexOf("."));
				}
				row.setStandardName(clean);
				resultList.add(row);
			}
		}
		UserVo user = getUser(req);
		//EasyUISeparatPage pagination = PaginationUtil.pagination(resultList, start, limit);
		Object attribute = req.getSession().getAttribute("solrtotal");
		int size=0;
		if(attribute != null) {
			size = (int) attribute;
		}
		// 这个地方没有用solr的分页，因为要对solr的检索结果做处理，所以只有手动分页
		PageInfo<SolrSearchResult> pageInfo = new PageInfo<SolrSearchResult>(resultList);
		EasyUISeparatPage pagination = new EasyUISeparatPage(Long.valueOf(size), pageInfo.getList());
		//pagination.setTotal(Long.valueOf(size));
		Map<String, Object> map = new HashMap<String, Object>();
		// 记录全文检索历史
		map.put("keyword", keyword);
		map.put("moudle", "4004");
		operLogService.insert(super.getOperLog(req, "4004", keyword, user.getId()));
		super.recordQueryHistory(req, map, queryHistoryService);
		return pagination;
	}
	/**
	 * 得到用户搜索的相关词汇和为你推荐
	 * 从题录名称和检索历史中得到10个匹配词汇
	 * 1-5显示在为你推荐， 1-10显示在相关搜索
	 * @param req
	 * @param keyword
	 * @param start
	 * @param limit
	 * @return
	 * @throws IOException
	 * @throws SolrServerException
	 */
	@ResponseBody
	@RequestMapping(value = "/relation/word")
	public List<String> relationWord(HttpServletRequest req, String keyword) throws IOException, SolrServerException{
		List<String> list = solrService.listRelationWord(keyword);
		return list;
	}
	/**
	 * 找出dataList中 和row的A100一样的数据
	 * @param dataList
	 * @param row
	 * @return
	 */
	private List<SolrSearchResult> getDataFromList(List<SolrSearchResult> dataList, SolrSearchResult row) {
		List<SolrSearchResult> tempList = new ArrayList<SolrSearchResult>();
		for(SolrSearchResult searchResult : dataList){
			if(searchResult.getStandardNo().equals(row.getStandardNo())){
				tempList.add(searchResult);
			}
		}
		return tempList;
	}
//	public static String escapeQueryChars(String s) {  
//	    StringBuilder sb = new StringBuilder();  
//	    for (int i = 0; i < s.length(); i++) {  
//	      char c = s.charAt(i);  
//	      // These characters are part of the query syntax and must be escaped  
//	      if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'  
//	        || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'  
//	        || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'  
//	        || Character.isWhitespace(c)) {  
//	        sb.append('\\');  
//	      }  
//	      sb.append(c);  
//	    }  
//	    return sb.toString();  
//	  } 
	public static String escapeQueryCharsTemp(String s) {  
	    StringBuilder sb = new StringBuilder();  
	    for (int i = 0; i < s.length(); i++) {  
	      char c = s.charAt(i);  
	      // These characters are part of the query syntax and must be escaped  
	      if (c == '\\' || c == '+' || c == '-' || c == '!'  || c == '(' || c == ')' || c == ':'  
	        || c == '^' || c == '[' || c == ']' || c == '\"' || c == '{' || c == '}' || c == '~'  
	        || c == '*' || c == '?' || c == '|' || c == '&'  || c == ';' || c == '/'  
	        || Character.isWhitespace(c)) {  
//	        sb.append('\\');  
	      } else {
	    	  sb.append(c);   
	      }
	    }  
	    return sb.toString();  
	  }
}
