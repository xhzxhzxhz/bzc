package com.folkestone.bzcx.controller.front.standard;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folkestone.bzcx.bean.bean_do.dm.L_LawsDo;
import com.folkestone.bzcx.bean.bean_do.dm.S_Std_FileDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_dto.dm.SolrSearchResult;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StdFileVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.JurisdictionUtil;
import com.folkestone.bzcx.common.util.PaginationUtil;
import com.folkestone.bzcx.common.util.StandStatic;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.ArchitectureNodeService;
import com.folkestone.bzcx.service.dm.FileLawsService;
import com.folkestone.bzcx.service.dm.LawsService;
import com.folkestone.bzcx.service.dm.SolrService;
import com.folkestone.bzcx.service.dm.StandardFileService;
import com.folkestone.bzcx.service.dm.StandardService;
import com.folkestone.bzcx.service.user.OperLogService;
import com.folkestone.bzcx.service.user.QueryHistoryService;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * Describe：前台检索所对应的controller
 * 
 * @author smallking
 *
 *         2017年11月13日
 */
@Controller
@RequestMapping(value = "/standard")
public class StandardController extends BaseController {
	@Autowired
	private StandardService standardService;
	@Autowired
	private StandardFileService standardFileService;
	@Autowired
	private ArchitectureNodeService architectureNodeService;
	@Autowired
	private OperLogService operLogService;
	@Autowired
	private QueryHistoryService queryHistoryService;
	@Autowired
	private LawsService lawsService;
	@Autowired
	private SolrService solrService;
	@Autowired
	private FileLawsService lawsFileService;

	/*
	 * @Autowired private ArchitectureManageService architectureManageService;
	 */

	// 校验静态页面存不存在
	@ResponseBody
	@RequestMapping(value = "/existence", method = { RequestMethod.POST })
	public Result Fileexistence(HttpServletRequest req, String filename) {
		Result re = new Result(false);
		File file = new File(ContantFinalUtil.STATIC_STANDARDURL + filename);
		if (file.exists()) {
			re.setResult(true);
		}
		return re;
	}
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/free", method={RequestMethod.POST,
	 * RequestMethod.GET}) public void free(HttpServletRequest req) throws
	 * IOException, TemplateException{ //1.创建配置类
	 * 
	 * Configuration configuration=new Configuration(Configuration.getVersion());
	 * 
	 * //2.设置模板所在的目录
	 * 
	 * configuration.setDirectoryForTemplateLoading(new File("D:\\bzcx\\ffmpeg\\"));
	 * 
	 * //3.设置字符集
	 * 
	 * configuration.setDefaultEncoding("utf-8");
	 * 
	 * //4.加载模板
	 * 
	 * Template template = configuration.getTemplate("test.ftl");
	 * 
	 * //5.创建数据模型
	 * 
	 * Map map=new HashMap();
	 * 
	 * map.put("name","张三 ");
	 * 
	 * map.put("message","欢迎来到神奇的品优购世界！");
	 * 
	 * //6.创建Writer对象
	 * 
	 * Writer out =new FileWriter(new File("D:\\bzcx\\ffmpeg\\test.html"));
	 * 
	 * //7.输出
	 * 
	 * template.process(map,out);
	 * 
	 * //8.关闭Writer对象
	 * 
	 * out.close(); }
	 */

	/**
	 * 
	 * 图形检索
	 * 
	 * @param req
	 * @param param
	 * @return
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/graphicalSearch", method={RequestMethod.POST})
	 * public EasyUISeparatPage GraphicalSearch(HttpServletRequest req,@RequestParam
	 * Map<String, Object> param) { EasyUISeparatPage data = new
	 * EasyUISeparatPage(); UserVo uer = super.getUser(req); ArchitectureManageVo
	 * manageByDepartment = null; if(uer.getDepartmentParent().equals("0")) {
	 * manageByDepartment =
	 * architectureManageService.getManageParent(uer.getDepartmentId(),req); }else {
	 * manageByDepartment =
	 * architectureManageService.getManageParent(uer.getDepartmentParent(),req); }
	 * String architectureId = manageByDepartment.getArchitectureId();
	 * List<EasyUITree> nodeTree =
	 * architectureNodeService.getfaNodeTree(architectureId, "0",true,req);
	 * data.setData(nodeTree); return data; }
	 */
	@ResponseBody
	@RequestMapping(value = "/letter", method = { RequestMethod.POST })
	public Result pinyin(HttpServletRequest req, @RequestParam Map<String, Object> param) {
		Result res = new Result(false);
		List<String> listPinyin = standardService.listPinyin();
		if (listPinyin.size() > 0) {
			res.setResult(true);
			res.setData(listPinyin);
		} else {
			res.setMsg("没有字母索引");
		}
		return res;
	}

	/**
	 * 前台的简单，高级，分类检索全都调用此接口， 具体的查询参数，排序依据，关键词之间的查询关系(and 还是 or) 现在的文件分为 国标G 企标Q 航标H
	 * 三化S 都由前台发送过来
	 * 
	 * @param req
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = { RequestMethod.POST })
	public EasyUISeparatPage search(HttpServletRequest req, Map<String, Object> param)
			throws JsonParseException, JsonMappingException, IOException {
		try {
			super.setParam(req, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 根据权限 查询当前用户能否查询企标
		// param = JurisdictionUtil.searchJurisdiction(req, param);
		Query query = new Query(param);
		EasyUISeparatPage data;
		List<StandardVo> dataList = null;

		if (query.get("nodeId") != null) {
			data = standardService.listStandardByNodeId(query);
			if (query.get("moudle") != null && !query.get("moudle").toString().equals("体系查询")) {
				dataList = standardService.listStandardByNodeIdNoPage(query);
			}
			/*
			 * ArchitectureNodeVo node =
			 * architectureNodeService.getNodeById(query.get("nodeId") + ""); if(node !=
			 * null) { data.put("nodeName", node.getNodeCnName() + "(" + node.getNodeCode()
			 * + ")"); }
			 */
		} else {
			if (query.get("moudle").equals("4001") && query.get("standardNo") == null
					&& query.get("standardCnName") == null) {
				Jedis jedis = new Jedis("localhost");
				String string = jedis.get(query.get("moudle") + query.getPage().toString());
				if (string == null && query.get("standardCnName") == null) {
					data = standardService.listStandardByQuery(query);
					JSONObject jsonObject = JSONObject.fromObject(data);
					// 将json转换为json字符串
					String str = jsonObject.toString();
					// jedis.set(key, str);
					jedis.set(query.get("moudle") + query.getPage().toString(), str, "NX", "EX", 1800);
				} else {
					ObjectMapper mapper = new ObjectMapper();
					data = mapper.readValue(string, EasyUISeparatPage.class);
				}
			} else {
				data = standardService.listStandardByQuery(query);
			}
			if (query.get("moudle") != null && !query.get("moudle").equals("4001")) {
				dataList = standardService.listStandardByQueryNoPage(query);
			}
		}
		if (query.get("moudle") != null && !query.get("moudle").toString().equals("体系查询")) {
			// 将检索结果放到session里面，供二次检索使用
			req.getSession().setAttribute("dataList", dataList);
		}
		// 如果是全库检索的话，就要做标准统计
		/*
		 * if(query.get("moudle") != null &&
		 * query.get("moudle").toString().equals("游览全库")){ List<StandardVo> standardList
		 * = standardService.listAllStandard(); Boolean haveArchCount = true;
		 * data.put("total", standardService.statistics(standardList,haveArchCount));
		 * data.put("result", standardService.statistics(dataList,haveArchCount)); }
		 */
		/***
		 * 4001简单查询，OPER_DETAIL为查询的关键词，MAPPING_ID为查询出的标准数量 4002高级查询 4003分类查询 4004全文查询
		 * 4005二次检索 5001意见反馈
		 */
		operLogService.insert(super.getOperLog(req, (String) query.get("moudle"), "" + param.get("standardNo"),
				data.getTotal().toString()));
		param.remove("icsCode");
		param.remove("standardType");
		super.recordQueryHistory(req, param, queryHistoryService);
		return data;
	}

	/*
	 * System.out.println("==-=-=-=-=-=-="+query.get("standardNo"));
	 * List<StandardVo> standardVoList = null; if(query.get("moudle").equals("4001")
	 * && query.get("standardNo") == null && query.get("standardCnName") == null) {
	 * Jedis jedis = new Jedis("localhost"); String string =
	 * jedis.get(query.get("moudle")+query.getPage().toString()); if(string == null)
	 * { standardVoList = standardMapper.listStandardByQuery(query); JSONObject
	 * jsonObject = JSONObject.fromObject(standardVoList); //将json转换为json字符串 String
	 * str = jsonObject.toString(); // jedis.set(key, str);
	 * jedis.set(query.get("moudle")+query.getPage().toString(), str, "NX", "EX",
	 * 1800); }else { ObjectMapper mapper = new ObjectMapper(); standardVoList =
	 * (List<StandardVo>) mapper.readValue(string , StandardVo.class); } }else {
	 */

	/**
	 * 二次检索
	 * 
	 * @param req
	 * @param param
	 * @param queryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/second/search", method = { RequestMethod.POST })
	public EasyUISeparatPage secondSearch(HttpServletRequest req, @RequestParam Map<String, Object> param) {
		try {
			super.setParam(req, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 得到存在session里面的值
		Object obj = req.getSession().getAttribute("dataList");
		if (obj == null) {
			return null;
		}
		String keyword = param.get("standardNo") + "";
		@SuppressWarnings("unchecked")
		ArrayList<Object> list = (ArrayList<Object>) obj;
		List<StandardVo> resultList = new ArrayList<StandardVo>();
		// 遍历存储的结果，选取合适的记录
		for (Object item : list) {
			StandardVo standardVo = (StandardVo) item;
			if (standardVo.getStandardCnName().toString().contains((keyword))
					|| standardVo.getStandardNo().toString().contains((keyword))) {
				resultList.add(standardVo);
			}
		}
		// 将resultList给分页
		Object cPage = param.get("page");
		Object pSize = param.get("rows");
		if (cPage == null) {
			cPage = 1;
		}
		if (pSize == null) {
			pSize = 10;
		}
		EasyUISeparatPage data = PaginationUtil.pagination(resultList, Integer.valueOf(cPage + ""),
				Integer.valueOf(pSize + ""));
		req.getSession().setAttribute("dataList", resultList);
		if (!StringUtil.isEmpty(keyword)) {
			operLogService.insert(super.getOperLog(req, "4005", keyword, data.getTotal().toString()));
		}
		super.recordQueryHistory(req, param, queryHistoryService);
		return data;
	}

	/**
	 * 检查当前用户是否可以浏览题录信息 如果可以，就将题录信息给返回去;否则的话，提示无权限.
	 * 
	 * @param standardId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = { RequestMethod.POST })
	public Result checkResult(HttpServletRequest req, String standardId) {
		Result result = new Result(true);
		// 如果当前用户没有查看企标的权限 而且当前的标准是企标 那么就返回没有权限的提示码
		// 检查一下 当前用户所在部门的体系下，有没有这个标准
		/*
		 * UserVo user = super.getUser(req); boolean exist =
		 * architectureNodeService.checkStandardByDepartment(user.getDepartmentId(),
		 * standardId); if(!exist){ result.setCode(10001);
		 * result.setMsg("此标准未纳入您所在单位标准体系，没有查看权限，请联系本单位标准化管理人员。"); return result; }
		 */
		StandardVo standardVo = standardService.getStandardById(standardId);
		// 查询当前标准昆仑体系的那个体系节点下
		String nodeName = standardService.getNodeByStandardId(standardVo.getStandardId());
		standardVo.setArchNodeName(nodeName);
		result.setData(standardVo);
		operLogService.insert(super.getOperLog(req, "2001", standardVo.getStandardCnName() + "", standardId));
		return result;
	}

	/**
	 * 查看一个人是否有查看当前题录原文的权限， 如果有查看原文的权限，而且当前原文存在pdf预览版，则将预览路径返回去
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/preview", method = { RequestMethod.POST })
	public Result checkPreviewOriginal(String standardNo, HttpServletRequest req) {
		Result result = new Result(true);
		/*
		 * UserVo user = super.getUser(req); StandardVo standardVo =
		 * standardService.getStandardByNoTrim(StringUtil.StrCha(standardNo)); boolean
		 * exist =
		 * architectureNodeService.checkStandardByDepartment(user.getDepartmentId(),
		 * standardVo.getStandardId()); if(!exist){ result.setCode(10001);
		 * result.setMsg("此标准未纳入您所在单位标准体系，没有查看权限，请联系本单位标准化管理人员。"); return result; }
		 * boolean flasg = true; switch (standardVo.getStandardType().toString()) { case
		 * "G": if(JurisdictionUtil.previewJurisdiction(req,
		 * ContantFinalUtil.PREVIEW_GB_RIGHT)) { flasg = false; } break; case "Q":
		 * if(JurisdictionUtil.previewJurisdiction(req,
		 * ContantFinalUtil.PREVIEW_QB_RIGHT)) { flasg = false; } break; case "H":
		 * if(JurisdictionUtil.previewJurisdiction(req,
		 * ContantFinalUtil.PREVIEW_HB_RIGHT)) { flasg = false; } break; case "S":
		 * if(JurisdictionUtil.previewJurisdiction(req,
		 * ContantFinalUtil.PREVIEW_SB_RIGHT)) { flasg = false; } break; } if(flasg){
		 * result.setCode(10002); result.setMsg("没有查看当前原文的权限。"); return result; }
		 */
		// 如果有权限的话，就返回原文的预览路径
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		query.put("standardNo", standardNo);
		EasyUISeparatPage data = standardFileService.listStdFileByQuery(query);
		for (Object obj : data.getRows()) {
			StdFileVo stdFileVo = (StdFileVo) obj;
			if (stdFileVo.getPdfFile() != null) {
				File fil = new File(ContantFinalUtil.BASE_PATH
						+ stdFileVo.getPdfFile().substring(1, stdFileVo.getPdfFile().length()));
				if (!fil.exists()) {
					result.setCode(10004);
					return result;
				}
				String previewUrl = super.getAddr(req) + stdFileVo.getPdfFile();
				result.setData(previewUrl);
				operLogService.insert(super.getOperLog(req, "2002", stdFileVo.getStandardNo() + "", previewUrl));
				return result;
			}
		}
		result.setCode(10004);
		result.setMsg("暂无PDF预览版原文，请联系管理员。");
		return result;
	}

	/**
	 * 根据原文的ID的到原文的名称
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searnull", method = { RequestMethod.POST })
	public Result searnull() {
		Result result = new Result(true);
		List<String> lis = new ArrayList<String>();
		String[] Ssardand = { "STANDARD_NO", "STANDARD_EN_NAME", "QUOTE_STANDARD", "PUB_ORG", "TECHNICAL_FIELD",
				"ADOPT_RELATION", "STANDARD_CN_NAME", "REPLACE_STANDARD", "DRAFT_UNIT", "PUBLISH_DATE",
				"ADOPT_RELATION" };
		for (String str : Ssardand) {
			int a = standardService.searnull(str);
			if (a < 2) {
				lis.add(str);
			}
		}

		result.setData(lis);
		return result;
	}

	/**
	 * 查询题录表 某一字段是否有值
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/file/name", method = { RequestMethod.POST })
	public String getStdFileName(String stdFileId) {
		StdFileVo stdFile = standardFileService.getStdFileById(stdFileId);
		return stdFile.getStdFileName() + "";
	}

	/**
	 * 下载原文，当参数是standardId的时候，查看下载权限，当参数是stdFileId的时候下载原文
	 * 
	 * @param req
	 * @param standardId
	 * @param stdFileId
	 * @param clientType
	 *            1：pc端 2：移动端
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/download", method = { RequestMethod.POST, RequestMethod.GET })
	public Object download(HttpServletRequest req, String standardId, String stdFileId, String clientType) {
		if (!StringUtil.isEmpty(stdFileId)) {
			System.out.println("--------------------");
			StdFileVo stdFileVo = standardFileService.getStdFileById(stdFileId);
			String filePath = null;
			if (!StringUtil.isEmpty(stdFileVo.getPdfFile())) {
				filePath = stdFileVo.getPdfFile() + "";
			} else {
				filePath = stdFileVo.getStdFilePath() + "";
			}
			String savePath = filePath.split("\\.")[0] + ".pdf";
			String fileName = (String) stdFileVo.getStdFileName();
			// String fileN= fileName.substring(0,fileName.lastIndexOf(".")+1);
			try {
				operLogService.insert(super.getOperLog(req, "1005", stdFileVo.getStdFileName() + "", stdFileId));
				return super.download(savePath, fileName);
			} catch (IOException e) {
				e.printStackTrace();
				return "下载失败，稍后再试。";
			}
		}
		// 查看是否有下载权限
		Result result = new Result(true);
		UserVo user = super.getUser(req);
		// StandardVo standardVobu =
		// standardService.getStandardByNoTrim(StringUtil.StrCha(standardId));
		StandardVo standardVo = standardService.getStandardById(standardId);
		boolean exist = architectureNodeService.checkStandardByDepartment(user.getDepartmentId(),
				standardVo.getStandardId());
		if (!exist) {
			result.setCode(10001);
			result.setMsg("此标准未纳入您所在单位标准体系，没有查看权限，请联系本单位标准化管理人员。");
			return result;
		}
		boolean flasg = true;
		switch (standardVo.getStandardType().toString()) {
		case "G":
			if (JurisdictionUtil.downlJurisdiction(req, ContantFinalUtil.DOWNLOAD_GB_RIGHT)) {
				flasg = false;
			}
			break;
		case "Q":
			if (JurisdictionUtil.downlJurisdiction(req, ContantFinalUtil.DOWNLOAD_QB_RIGHT)) {
				flasg = false;
			}
			break;
		case "H":
			if (JurisdictionUtil.downlJurisdiction(req, ContantFinalUtil.DOWNLOAD_HB_RIGHT)) {
				flasg = false;
			}
			break;
		case "S":
			if (JurisdictionUtil.downlJurisdiction(req, ContantFinalUtil.DOWNLOAD_SB_RIGHT)) {
				flasg = false;
			}
			break;
		}
		/*
		 * boolean flagG = standardVo.getStandardType().equals("G") &&
		 * JurisdictionUtil.downlJurisdiction(req, ContantFinalUtil.DOWNLOAD_GB_RIGHT);
		 * boolean flagQ = standardVo.getStandardType().equals("Q") &&
		 * JurisdictionUtil.downlJurisdiction(req, ContantFinalUtil.DOWNLOAD_QB_RIGHT);
		 * boolean flagH = standardVo.getStandardType().equals("H") &&
		 * JurisdictionUtil.downlJurisdiction(req, ContantFinalUtil.DOWNLOAD_HB_RIGHT);
		 * boolean flagS = standardVo.getStandardType().equals("S") &&
		 * JurisdictionUtil.downlJurisdiction(req, ContantFinalUtil.DOWNLOAD_SB_RIGHT);
		 */
		if (flasg) {
			result.setCode(10003);
			result.setMsg("没有下载当前原文的权限。");
			return result;
		}
		// 如果有权限的话，就返回原文ID
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		query.put("standardNo", standardVo.getStandardNo());
		EasyUISeparatPage data = standardFileService.listStdFileByQuery(query);
		for (Object obj : data.getRows()) {
			StdFileVo stdFileVo = (StdFileVo) obj;
			if (stdFileVo.getFileType().equals("pdf") || stdFileVo.getFileType().equals("doc")
					|| stdFileVo.getFileType().equals("docx")) {
				if (clientType != null && clientType.equals("0")) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("stdFileId", stdFileVo.getStdFileId());
					map.put("standardFileName", stdFileVo.getStdFileName() + "");
					result.setData(map);
				} else {
					File fil = new File(ContantFinalUtil.BASE_PATH + stdFileVo.getPdfFile());
					if (fil.exists()) {
						result.setData(stdFileVo.getStdFileId());
					} else {
						result.setCode(10005);
					}
				}
				return result;
			}
		}
		result.setCode(10005);
		result.setMsg("当前原文没有PDF预览版，请联系管理员。");
		return result;
	}

	/**
	 * 下载pdf阅读器
	 * 
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/pdfreader/download", method = { RequestMethod.POST, RequestMethod.GET })
	public Object downloadPdfReader() throws IOException {
		// TODO下载pdf阅读器
		return super.download(ContantFinalUtil.PDF_READER_PATH, "Baidu_FoxitReader_8.3.3.26761.exe");
	}

	/**
	 * 简单查的搜索框中的数据的类型选择
	 */
	@RequestMapping(value = "/showType", method = { RequestMethod.POST })
	public EasyUISeparatPage checkType(String type) {
		EasyUISeparatPage data = null;
		if (type != null && !"".equals(type)) {

			if ("标准信息".equals(type)) {// 展示所有的标准信息
				List<StandardVo> standardVoList = standardService.listAllStandard();
				// 将其封装到前端返回的实体类中
				PageInfo<StandardVo> pageInfo = new PageInfo<StandardVo>(standardVoList);
				data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
			}
			if ("法律法规".equals(type)) {// 展示所有的法律法规的信息
				// 法律法规的发挥结果
				List<LawsVo> list = lawsService.findAllLaws();
				PageInfo<LawsVo> pageInfo = new PageInfo<LawsVo>(list);
				data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());

			}
		}
		return data;
	}

	/**
	 * 前台的简单，高级，分类检索全都调用此接口， 具体的查询参数，排序依据，关键词之间的查询关系(and 还是 or) 现在的文件分为 国标G 企标Q 航标H
	 * 三化S 都由前台发送过来
	 * 
	 * @param req
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/searchSecond", method = { RequestMethod.POST })
	public EasyUISeparatPage searchSecondToOne(HttpServletRequest req, @RequestParam Map<String, Object> param)
			throws JsonParseException, JsonMappingException, IOException {
		try {
			super.setParam(req, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 根据权限 查询当前用户能否查询企标
		// param = JurisdictionUtil.searchJurisdiction(req, param);
		Query query = new Query(param);
		EasyUISeparatPage data = null;
		List<?> dataList = null;
		// 根据我们定义的typeName去判断是标准信息还是法律法规,而这个type必须在前台的参数中定义或者是给一个默认的值
		if (query.get("typeName") == null) {// 先判断是这样当我们的typeName 必须有值，当没有值的时候，直接将结果返回回去
			return null;
		} else {

			if (query.get("typeName").toString().equals("标准信息")) {// 这个是标准信息
				query.put("standardNo", query.get("paramNo"));
				query.put("standardCnName", query.get("paramCnName"));

				if (query.get("nodeId") != null) {
					data = standardService.listStandardByNodeId(query);
					if (query.get("moudle") != null && !query.get("moudle").toString().equals("体系查询")) {
						dataList = standardService.listStandardByNodeIdNoPage(query);
					}
					/*
					 * ArchitectureNodeVo node =
					 * architectureNodeService.getNodeById(query.get("nodeId") + ""); if(node !=
					 * null) { data.put("nodeName", node.getNodeCnName() + "(" + node.getNodeCode()
					 * + ")"); }
					 */
				} else {
					if (query.get("moudle").equals("4001") && query.get("standardNo") == null
							&& query.get("standardCnName") == null) {
						Jedis jedis = new Jedis("localhost");
						String string = jedis.get(query.get("moudle") + query.getPage().toString() + "standard");
						if (string == null && query.get("standardCnName") == null) {
							data = standardService.listStandardByQuery(query);
							JSONObject jsonObject = JSONObject.fromObject(data);
							// 将json转换为json字符串
							String str = jsonObject.toString();
							// jedis.set(key, str);
							jedis.set(query.get("moudle") + query.getPage().toString() + "standard", str, "NX", "EX",
									1800);
						} else {
							ObjectMapper mapper = new ObjectMapper();
							data = mapper.readValue(string, EasyUISeparatPage.class);
						}
					} else {
						if(query.get("moudle").equals("4003") ) {//表示是分类查询，然后我们就需要去调用另一个方法，去将我们的数据展示
							data=standardService.listStandardByIcsCode(query);
							
						}else {
							data = standardService.listStandardByQuery(query);
						}
					
						
					}
					if (query.get("moudle") != null && !query.get("moudle").equals("4001")) {
						if (query.get("moudle").equals("4002")) {// 如果是高级查询，就将我们的请求参数放到session中
							req.getSession().setAttribute("param", param);
						}
						//dataList = standardService.listStandardByQueryNoPage(query);
					}
				}
				/*if (query.get("moudle") != null && !query.get("moudle").toString().equals("体系查询")) {
					// 将检索结果放到session里面，供二次检索使用
					req.getSession().setAttribute("dataList", dataList);
				}*/
				// 如果是全库检索的话，就要做标准统计
				/*
				 * if(query.get("moudle") != null &&
				 * query.get("moudle").toString().equals("游览全库")){ List<StandardVo> standardList
				 * = standardService.listAllStandard(); Boolean haveArchCount = true;
				 * data.put("total", standardService.statistics(standardList,haveArchCount));
				 * data.put("result", standardService.statistics(dataList,haveArchCount)); }
				 */
				/***
				 * 4001简单查询，OPER_DETAIL为查询的关键词，MAPPING_ID为查询出的标准数量 4002高级查询 4003分类查询 4004全文查询
				 * 4005二次检索 5001意见反馈
				 */
				operLogService.insert(super.getOperLog(req, (String) query.get("moudle"), "" + param.get("standardNo"),
						data.getTotal().toString()));
				param.remove("icsCode");
				param.remove("standardType");
				super.recordQueryHistory(req, param, queryHistoryService);

			} else {// 表示的是法律法规查询
				query.put("lawsNo", query.get("paramNo"));
				query.put("lawsCnName", query.get("paramCnName"));
				// 判断我们传过来的值是否为空，先从Redis中获取值，当为空的时候 我们从数据库中查找，然后将值存储在Redis中
				if (query.get("moudle").equals("4001") && query.get("lawsNo") == null
						&& query.get("lawsCnName") == null) {

					Jedis jedis = new Jedis("localhost");
					String string = jedis.get(query.get("moudle") + query.getPage().toString() + "laws");
					if (string == null && query.get("lawsCnName") == null) {
						data = lawsService.listLawsByQuery(query);
						// JSONObject jsonObject = JSONObject.fromObject(data);
						JSONObject jsonObject = JSONObject.fromObject(data);
						// 将json转换为json字符串
						String str = jsonObject.toString();
						// jedis.set(key, str);
						jedis.set(query.get("moudle") + query.getPage().toString() + "laws", str, "NX", "EX", 1800);
					} else {// 直接到Redis中获取
						ObjectMapper mapper = new ObjectMapper();
						data = mapper.readValue(string, EasyUISeparatPage.class);
					}
				} else {// 当不为空，或者不是4001 简单查询的时候，直接去数据库中查询，然后将这些的标题展示出来
					if(query.get("moudle").equals("4003")) {
						data = lawsService.listLawsByIcsCode(query);
					}else {
						data = lawsService.listLawsByQuery(query);
					}
				}
				if (query.get("moudle") != null && !query.get("moudle").equals("4001")) {
					//dataList = lawsService.listStandardByQueryNoPage(query);
					// 将其放到session域中，然后让其提供二次检索的效果
					if (query.get("moudle").equals("4002")) {// 如果是高级查询，就将我们的请求参数放到session中
						req.getSession().setAttribute("param_laws", param);
					}
					// req.getSession().setAttribute("dataList_laws", dataList);
				}
				// 将操作添加到日志中
				operLogService.insert(super.getOperLog(req, (String) query.get("moudle"), "" + param.get("lawsNo"),
						data.getTotal().toString()));
				param.remove("icsCode");
				param.remove("standardType");
				super.recordQueryHistory(req, param, queryHistoryService);

			}
		}

		return data;
	}

	/**
	 * 分类查询——展示所有的类别
	 */
	@ResponseBody
	@RequestMapping(value = "/showType", method = RequestMethod.GET)
	public List<String> showType() {
		List<String> list = standardService.showType();
		return list;
	}

	/**
	 * 分类查询——展示所有的状态
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showStatus", method = RequestMethod.GET)
	public List<String> showStatus() {
		return standardService.showStatus();
	}

	/**
	 * 展示所有的性质
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showNature", method = RequestMethod.GET)
	public List<String> showNature() {
		return standardService.showNature();
	}

	/**
	 * 当点击二次筛选的时候，将我们高级查询的请求参数内容展示，和结果的展示
	 * 
	 * @return
	 */
	@RequestMapping(value = "/showParam", method = RequestMethod.GET)
	public Result showParam(HttpServletRequest req) {
		Result result = new Result();
		// 从session中获取我们的请求参数
		Map<String, Object> param = (Map<String, Object>) req.getSession().getAttribute("param");
		if (param != null) {
			result.setCode(200);
			result.setData(param);
		} else {
			result.setCode(500);
			result.setMsg("session域中未存入值，请检查代码的有效性");
		}
		return result;
	}

	/**
	 * 二次检索,并将数据展示
	 * 
	 * @param req
	 * @param param
	 * @param queryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/second/searchToOne", method = { RequestMethod.POST })
	public EasyUISeparatPage secondSearchAndSelect(HttpServletRequest req, @RequestParam Map<String, Object> param) {
		// 先获取我们在session中的第一次检索的请求参数
		List<StandardVo> list = null;
		try {
			Map<String, Object> map = (Map<String, Object>) req.getSession().getAttribute("param");
			// 创建一个新的集合
			Map<String, Object> paramMap = new HashMap<String, Object>();
			// 将这次的请求参数，放到新的集合
			if (param != null) {
				for (Map.Entry<String, Object> entry : param.entrySet()) {
					paramMap.put(entry.getKey(), entry.getValue());
				}
			}
			if (map != null) {
				paramMap.put("standardCnName02", map.get("paramCnName"));
				paramMap.put("standardNo02", map.get("standardNo"));
				paramMap.put("standardType02", map.get("standardType"));
				paramMap.put("competentDepartment02", map.get("competentDepartment"));
				paramMap.put("standardStatus02", map.get("standardStatus"));
				paramMap.put("nature02", map.get("nature"));
				paramMap.put("pubOrg02", map.get("pubOrg"));
				paramMap.put("standardNo02", map.get("paramNo"));
				paramMap.put("effectivenessLevel02", map.get("effectivenessLevel"));
				paramMap.put("relation", map.get("relation"));

				if (param != null) {
					Date begDate = null;
					Date endDate = null;
					Date beginImpl = null;
					Date endUmpl = null;
					Date secBeDate = null;
					Date secEndDate = null;
					Date secImplBe = null;
					Date secImplEnd = null;
					// 前台传过来的是一个字符串
					String date = (String) param.get("publishDatebegin");
					String endDa = (String) param.get("publishDateend");
					String implBegin = (String) param.get("implementationDatebegin");
					String implend = (String) param.get("implementationDateend");
					// 这点报错 就是类型转化不一致
					// 将这个字符串转化成日期对象然后将其 转化成毫秒值 进行大小的比较
					// 2019-03-28 08:37:38
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if (date != null && !"".equals(date)) {
						begDate = (Date) sdf.parse(date);

					}
					if (endDa != null && !"".equals(endDa)) {
						// int beginDa=Integer.parseInt(date);
						endDate = (Date) sdf.parse(endDa);

					}
					if (implBegin != null && !"".equals(implBegin)) {
						// begDate=(Date) sdf.parse(date);
						// int endDa=Integer.parseInt(endDate);
						beginImpl = (Date) sdf.parse(implBegin);
						// int implBe=Integer.parseInt(implBegin);
					}
					if (implend != null && !"".equals(implend)) {
						endUmpl = (Date) sdf.parse(implend);
						// int implen=Integer.parseInt(implend);

					}
					// 二次
					String mapBe = (String) map.get("publishDatebegin");
					String mapEn = (String) map.get("publishDateend");
					String mapImBe = (String) map.get("implementationDatebegin");
					String mapImen = (String) map.get("implementationDateend");
					if (mapBe != null && !"".equals(mapBe)) {
						secBeDate = (Date) sdf.parse(mapBe);
					}
					if (mapEn != null && !"".equals(mapEn)) {
						secEndDate = (Date) sdf.parse(map.get("publishDateend").toString());
					}
					if (mapImBe != null && !"".equals(mapImBe)) {
						secImplBe = (Date) sdf.parse(map.get("implementationDatebegin").toString());
					}
					if (mapImen != null && !"".equals(mapImen)) {
						secImplEnd = (Date) sdf.parse(map.get("implementationDateend").toString());
					}

					if (begDate != null && secBeDate != null) {
						if (begDate.after(secBeDate)) {
							paramMap.put("publishDatebegin", date);
						} else {
							paramMap.put("publishDatebegin", map.get("publishDatebegin").toString());
						}
					}
					if (endDate != null && secEndDate != null) {
						if (endDate.after(secEndDate)) {
							paramMap.put("publishDateend", endDa);

						} else {
							paramMap.put("publishDateend", map.get("publishDateend").toString());
						}
					}

					if (beginImpl != null && secImplBe != null) {
						if (beginImpl.after(secImplBe)) {
							paramMap.put("implementationDatebegin", implBegin);
						} else {
							paramMap.put("implementationDatebegin", map.get("implementationDatebegin").toString());
						}
					}
					if (endUmpl != null && secImplEnd != null) {

						if (endUmpl.after(secImplEnd)) {
							paramMap.put("implementationDatebegin", implend);
						} else {
							paramMap.put("implementationDateend", map.get("implementationDateend").toString());
						}
					}

				}
				String order = (String) param.get("order");
				if (order != null && !"".equals(order) && order.equals("标准名称")) {
					if (("标准名称").equals(order)) {
						paramMap.put("order", "STANDARD_CN_NAME");
					}
					if ("发布日期".equals(order)) {
						paramMap.put("order", "PUBLISH_DATE");
					}
					if ("标准状态".equals(order)) {
						paramMap.put("order", "STANDARD_STATUS");
					}

				}

			}
			list = standardService.secondSearchAndSelect(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
		}
		PageInfo<StandardVo> pageInfo = new PageInfo<StandardVo>(list);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;

	}

	@ResponseBody
	@RequestMapping(value = "/findFtl", method = RequestMethod.GET)
	public String findFtl() {

		String str = ContantFinalUtil.BASE_PATH;

		System.out.println(str);
		return str;
		//
	}

	/**
	 * 精准查询——输入框的内容联想 不分标准信息和法律法规（得到题录的标准号，标准名称和标准id）（二次查询 也是调用当前方法，还有传入 时间分类，状态）
	 */
	@ResponseBody
	@RequestMapping(value = "/preciseQuery", method = RequestMethod.POST)
	public Result preciseQuery(HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Result result = new Result();
		if (map != null) {
			String type = (String) map.get("typeName");

			String queryType = (String) map.get("queryType");
			// 根据这个查询类型去区别是标题查询还是文章查询

			if (queryType.equals("标题检索")) {// 表示这个是标题检索 我们通过在两张表中查询数据 然后将其组装在
				List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				// 当我们输入内容的时候就可以进行检索
				// 得到检索内容中的标准号和标准名称
				// String standardNo = (String) map.get("paramNo");
				String param = (String) map.get("param");
				Query query = new Query();
				query.put("standardNo", param);
				query.put("standardCnName", param);
				// 去标准题录中查询 返回结果（标准名称/标准号，id）
				// 查询得到list
				List<Map<String, Object>> resultMap = standardService.getContentName(query);
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("lawsCnName", param);
				paramMap.put("lawsNo", param);
				List<Map<String, Object>> list = lawsService.findLawsListByQuery(paramMap);
				// 遍历两个结果集 然后将其组装在一个中这个样我们就可以将结果返回了

				if (resultMap != null && resultMap.size() > 0) {
					for (Map<String, Object> maps : resultMap) {
						resultList.add(maps);
					}
				}
				if (list != null && list.size() > 0) {
					for (Map<String, Object> listMap : list) {
						resultList.add(listMap);
					}
				}
				result.setCode(200);
				result.setData(resultList);
				return result;
			}
			if (queryType.equals("文章检索")) {
				// 文章检索，我们是通过关键字在solr服务器中查找，将查找到含有我们这个词的关键字进行高亮展示，并将这个关键词所在文章中的次数，也显示出来，并将最多关键字的页码展示出来
				String keyword = (String) map.get("keyword");
				String rowsStr = (String) map.get("rows");
				String startStr = (String) map.get("start");

				Integer rows = Integer.valueOf(rowsStr);
				Integer start = Integer.valueOf(startStr);
				// 将string转化成数字
				// 将solr中查询到的结果进行组装
				List<SolrSearchResult> resultList = solrService.selectContentFormSolr(request, null, keyword, rows,
						start);
				// 结果中的数据是所有的数据 包含原文章和其字文章
				// 我们需要遍历，然后将所有的文章中找到原文章放到list中，然后在遍历所有，并将这个文章下的所有的字文章放到
				List<Map<String, Object>> endList = new ArrayList<Map<String, Object>>();

				// List<SolrSearchResult> yuanList = new ArrayList<SolrSearchResult>();
				// 需要我们在遍历以后，将里面的值获取出来，
				for (SolrSearchResult re : resultList) {
					// 获取每一个的对象的id
					String fileId = re.getFileId();
					String[] fileIds = fileId.split(".pdf");
					String yuanId = fileIds[0];
					// 获得原文id
					String[] yuanIds = yuanId.split("\\+");
					// 这个就是我们原文的id
					Map<String, Object> childMap = new HashMap<String, Object>();
					// 这个中存放的是切割后的pdf的solr的查询结果对象
					List<SolrSearchResult> list = new ArrayList<SolrSearchResult>();
					// 这个是我们原文章的id
					yuanId = yuanIds[0];

					// 然后在遍历将满足条件的存放到list中
					for (SolrSearchResult solrRe : resultList) {
						String id = solrRe.getFileId();

						if (id.contains(yuanId) && !id.equals(yuanId)) {
							list.add(solrRe);
						}
					}
					SolrSearchResult sr = new SolrSearchResult();
					for (SolrSearchResult solrRe : resultList) {
						String id = solrRe.getFileId();
						if (id != null && id.equals(yuanId)) {
							// 表示这个是原文
							sr = solrRe;
						}
					}
					// 不需要一个子文章的集合，而是需要的是他的里面次数最多的文章
					Collections.sort(list, new Comparator<SolrSearchResult>() {
						@Override
						public int compare(SolrSearchResult o1, SolrSearchResult o2) {
							// TODO 按照次数排序
							Integer i = null;
							Integer j = null;
							String freq1 = o1.getFreq();
							String freq2 = o2.getFreq();
							if (o1 != null && o2 != null) {
								if (freq1 != null) {
									i = Integer.valueOf(freq1);
								}
								if (freq2 != null) {
									j = Integer.valueOf(freq2);
								}
								if (i != null && i != null) {
									if (i > j) {
										return -1;
									}
									if (i == j) {
										return 0;
									}
								}
							}
							return 1;
						}
					});
					// 降序排列后的list,然后就是取第一个 我们就可以得到里面的数据，是一个对象 表示的是在原文章中的关键字最多的其中一页，并且还有其次数和页码数
					if (list != null && list.size() > 0) {
						SolrSearchResult ss = list.get(0);
						String smlId = ss.getFileId().split("\\.")[0];
						String[] ids = smlId.split("\\+");
						// 降序排列后的list,然后就是取第一个 我们就可以得到里面的数据，是一个对象 表示的是在原文章中的关键字最多的其中一页，并且还有其次数和页码数

						// 最后取到的就是我们需要的当前的页码数
						String page = ids[ids.length - 1];
						childMap.put("yuanId", yuanId);
						// 原文章的标题
						childMap.put("fileName", sr.getFileName());
						// 切割后的小的文章的内容
						childMap.put("fileContent", ss.getFileContent());
						// 表示切割后的出现次数最多的页面的内容
						childMap.put("fileId", ss.getFileId());
						// 关键字出现的次数
						childMap.put("maxFreq", list.get(0).getFreq());
						// 出现次数最多的页数
						childMap.put("maxPage", page);
						endList.add(childMap);
						// 当我们返回的内容中将切割后的fileId 传过去，然后当点击这篇文章的时候，就会直接去进入到这一页中，不会显示关键词的次数,和高亮，当是可以就进行
						// 上下也的翻页和搜索页面跳转
					}
				}
				result.setCode(200);
				// 对结果去重
				for (int i = 0; i < endList.size() - 1; i++) {
					for (int j = endList.size() - 1; j > i; j--) {
						if (endList.get(j).equals(endList.get(i))) {
							endList.remove(j);
						}
					}
				}

				result.setData(endList);
			}
			// 将该词设置为热搜词
			operLogService.insert(super.getOperLog(request, (String) map.get("moudle"), "" + map.get("paramNo"), null));
			map.remove("icsCode");
			map.remove("standardType");
			super.recordQueryHistory(request, map, queryHistoryService);
		}

		return result;
	}

	/**
	 * 二次检索——通过我们选择的过滤条件 去输出相应的结果集
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/secondQuery", method = RequestMethod.POST)
	public Result secondQuery(HttpServletRequest request, @RequestParam Map<String, Object> map) {
		Result result = new Result();
		String queryType = (String) map.get("queryType");
		String typeName = (String) map.get("typeName");
		if (queryType.equals("文章检索")) {
			// 文章检索，我们是通过关键字在solr服务器中查找，将查找到含有我们这个词的关键字进行高亮展示，并将这个关键词所在文章中的次数，也显示出来，并将最多关键字的页码展示出来
			String keyword = (String) map.get("keyword");
			String rowsStr = (String) map.get("rows");
			String startStr = (String) map.get("start");
			String icsCode = (String) map.get("icsCode");
			String publishBeginDate = (String) map.get("publishBeginDate");
			String publishEndDate = (String) map.get("publishEndDate");
			String status = (String) map.get("status");
			String pubOrg = (String) map.get("pubOrg");
			// 获取我们的状态，时间 和 我们的分类
			StringBuffer strs = new StringBuffer();
			// 封装我们的过滤条件，
			if (icsCode != null) {
				strs.append("icsCode:");
				strs.append(icsCode);
			}
			if (status != null) {
				strs.append("AND  status:");
				strs.append(status);
			}
			if (pubOrg != null) {
				strs.append(" AND  pubOrg:");
				strs.append(pubOrg);
			}
			/*
			 * if(publishBeginDate!=null) {
			 * strs.append("and publishDate: ["+publishBeginDate + "To * ]" ); }
			 * if(publishEndDate!=null) { strs.append("and publishDate: [ 0"+
			 * "To "+publishEndDate ); }
			 */
			if (publishBeginDate != null && publishEndDate != null) {
				// 表示在整个区间里
				strs.append("    	 AND publishDate:[" + publishBeginDate + " TO " + publishEndDate + "]");
				// strs.append("and publishDate: [ *"+ "To "+publishEndDate );
			}
			Integer rows = Integer.valueOf(rowsStr);
			Integer start = Integer.valueOf(startStr);
			// 将string转化成数字
			// 将solr中查询到的结果进行组装

			List<SolrSearchResult> resultList = solrService.selectContentFormSolr(request, strs.toString(), keyword,
					rows, start);
			// 结果中的数据是所有的数据 包含原文章和其字文章
			// 我们需要遍历，然后将所有的文章中找到原文章放到list中，然后在遍历所有，并将这个文章下的所有的字文章放到
			List<Map<String, Object>> endList = new ArrayList<Map<String, Object>>();

			// List<SolrSearchResult> yuanList = new ArrayList<SolrSearchResult>();
			// 需要我们在遍历以后，将里面的值获取出来，
			for (SolrSearchResult re : resultList) {
				// 获取每一个的对象的id
				String fileId = re.getFileId();
				String[] fileIds = fileId.split(".pdf");
				String yuanId = fileIds[0];
				// 获得原文id
				String[] yuanIds = yuanId.split("\\+");
				// 这个就是我们原文的id
				Map<String, Object> childMap = new HashMap<String, Object>();
				// 这个中存放的是切割后的pdf的solr的查询结果对象
				List<SolrSearchResult> list = new ArrayList<SolrSearchResult>();
				// 这个是我们原文章的id
				yuanId = yuanIds[0];

				// 然后在遍历将满足条件的存放到list中
				for (SolrSearchResult solrRe : resultList) {
					String id = solrRe.getFileId();

					if (id.contains(yuanId) && !id.equals(yuanId)) {
						list.add(solrRe);
					}
				}
				SolrSearchResult sr = new SolrSearchResult();
				for (SolrSearchResult solrRe : resultList) {
					String id = solrRe.getFileId();
					if (id != null && id.equals(yuanId)) {
						// 表示这个是原文
						sr = solrRe;
					}
				}
				// 不需要一个子文章的集合，而是需要的是他的里面次数最多的文章
				Collections.sort(list, new Comparator<SolrSearchResult>() {
					@Override
					public int compare(SolrSearchResult o1, SolrSearchResult o2) {
						// TODO 按照次数排序
						Integer i = null;
						Integer j = null;
						String freq1 = o1.getFreq();
						String freq2 = o2.getFreq();
						if (o1 != null && o2 != null) {
							if (freq1 != null) {
								i = Integer.valueOf(freq1);
							}
							if (freq2 != null) {
								j = Integer.valueOf(freq2);
							}
							if (i != null && i != null) {
								if (i > j) {
									return -1;
								}
								if (i == j) {
									return 0;
								}
							}
						}
						return 1;
					}
				});
				// 降序排列后的list,然后就是取第一个 我们就可以得到里面的数据，是一个对象 表示的是在原文章中的关键字最多的其中一页，并且还有其次数和页码数
				if (list != null && list.size() > 0) {
					SolrSearchResult ss = list.get(0);
					String smlId = ss.getFileId().split("\\.")[0];
					String[] ids = smlId.split("\\+");
					// 降序排列后的list,然后就是取第一个 我们就可以得到里面的数据，是一个对象 表示的是在原文章中的关键字最多的其中一页，并且还有其次数和页码数

					// 最后取到的就是我们需要的当前的页码数
					String page = ids[ids.length - 1];
					childMap.put("yuanId", yuanId);
					// 原文章的标题
					childMap.put("fileName", sr.getFileName());
					// 切割后的小的文章的内容
					childMap.put("fileContent", ss.getFileContent());
					// 表示切割后的出现次数最多的页面的内容
					childMap.put("fileId", ss.getFileId());
					// 关键字出现的次数
					childMap.put("maxFreq", list.get(0).getFreq());
					// 出现次数最多的页数
					childMap.put("maxPage", page);
					endList.add(childMap);
					// 当我们返回的内容中将切割后的fileId 传过去，然后当点击这篇文章的时候，就会直接去进入到这一页中，不会显示关键词的次数,和高亮，当是可以就进行
					// 上下也的翻页和搜索页面跳转
				}
			}
			result.setCode(200);
			// 对结果去重
			for (int i = 0; i < endList.size() - 1; i++) {
				for (int j = endList.size() - 1; j > i; j--) {
					if (endList.get(j).equals(endList.get(i))) {
						endList.remove(j);
					}
				}
			}

			result.setData(endList);
		}
		return result;
	}

	/**
	 * 标题查询——通过我们选择的内容进行查询到精确的某一个文章(在内容联想中我们查询到了一篇题录的)，因此需要标准原文id 所以 我们换需要去查询
	 * 我们的标准原文表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/titleSearch", method = RequestMethod.POST)
	public Result titleSearch(@RequestParam Map<String, Object> map) {
		// 接收发送过来的请求 文章的id查看我们的数据 库中是否有这一篇文章，存在提示 搜索，不存在，显示，暂无原文
		String typeName = (String) map.get("typeName");
		String id = (String) map.get("id");
		Result result = new Result();
		// if (typeName != null && typeName.equals("标准信息")) {
		// 通过id去查询数据
		if (id != null) {
			StandardVo standard = standardService.findstandardById(id);
			if (standard != null) {
				// 查询标准原文表，通过标准号
				String standardNo = standard.getStandardNo();
				if (standardNo != null) {
					StdFileVo stdFile = standardFileService.findStandFileByStdNo(standardNo);
					// 表名查询到数据
					if (stdFile != null) {
						result.setCode(200);
						result.setData(stdFile);
					} else {
						result.setCode(202);
						result.setMsg("暂无原文！");
					}

				}
			} else {
				LawsVo laws = lawsService.findLawsById(id);
				if (laws != null) {
					// 通过法律题录去查询第三张表
					String lawsId = laws.getLawsId();
					StdFileVo stdFile = standardFileService.findStdFileByLawsId(lawsId);
					if (stdFile != null) {
						result.setCode(200);
						result.setData(stdFile);
					} else {
						result.setCode(202);
						result.setMsg("暂无原文！");
					}
				} else {
					result.setCode(500);
					result.setMsg("查询数据库错误，请检查数据的合法性");
				}
			}
		}
		return result;
	}

	/**
	 * 精准查询——标题检索/文章推荐
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recommend", method = RequestMethod.GET)
	public Result recommend() {
		Result result = new Result();
		// 按照标准原文中的浏览次数排序，然后分页得到前六个
		List<StdFileVo> list = standardFileService.recommend();
		// 然后遍历查询，将其关联的题录展示在哪
		List<Object> resultList = new ArrayList<Object>();
		for (StdFileVo std : list) {
			if (std != null) {
				StandardVo standard = standardService.findByStandardNo(std.getStandardNo());
				if (standard == null) {// 表示在标准题录总没有查询到，现在去到法律题录中去查询
					LawsVo lawsVo = lawsService.findLawsByStdId(std.getStdFileId());
					if (lawsVo != null) {
						resultList.add(standard);
					}
				} else {
					resultList.add(standard);
				}

			}

		}
		result.setCode(200);
		result.setData(resultList);
		return result;
	}

	/**
	 * 标题查询——通过标准原文id去将标准原文下 包含关键字的对象返回
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/searchContent", method = RequestMethod.GET)
	public Result searchContent(HttpServletRequest request, String stdFileId, String keyword) {
		Result result = new Result();
		try {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (keyword == null) {
				keyword = "*";
			}
			// 然后将solr库中id包含原文id的数据取出来 得到一个集合
			// 设置过滤参数的组装
			String strQuery = "id:" + stdFileId + "*";
			List<SolrSearchResult> resultList = solrService.selectContentForm2Solr(request, strQuery, keyword, 5, 1);
			// 遍历，将集合中对象包含包含id 但是不等于id的值 取出来,即时切割后的小的文章的集合
			// List<SolrSearchResult> list = new ArrayList<SolrSearchResult>();
			for (SolrSearchResult res : resultList) {
				String fileId = res.getFileId();
				// if (id.contains(yuanId) && !id.equals(yuanId)) {
				if (fileId.contains(stdFileId) && !fileId.equals(stdFileId)) {
					Map<String, Object> map = new HashMap<String, Object>();
					// map.put(key, value)
					// 获取页码
					String smlId = res.getFileId().split(".pdf")[0];
					String[] ids = smlId.split("\\+");
					// 最后取到的就是我们需要的当前的页码数
					String page = ids[ids.length - 1];
					// 获取次数
					String freq = res.getFreq();
					// 获取内容
					String fileContent = res.getFileContent();
					// 获取id
					String id = ids[0];
					map.put("page", page);
					map.put("freq", freq);
					map.put("fileContent", fileContent);
					map.put("stdFileId", smlId);
					list.add(map);
				}

			}
			// 对list中的对象进行排序
			Collections.sort(list, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					// TODO 按照次数排序
					Integer i = null;
					Integer j = null;
					String freq1 = (String) o1.get("freq");
					String freq2 = (String) o2.get("freq");

					if (freq1 != null) {
						i = Integer.valueOf(freq1);
					}
					if (freq2 != null) {
						j = Integer.valueOf(freq2);
					}
					if (i > j) {
						return -1;
					}
					if (i == j) {
						return 0;
					}
					return 1;
				}
			});
			result.setCode(200);
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("查询错误，请检查代码的正确性！");
		}

		return result;
	}

	/**
	 * 1. 精准查询——当选中某一页时，跳转到这一页对应的HTML,查询，跳转到具体的HTML页面,
	 * 2、在这获取到这篇文章的stdFileId，然后我就可以得到这个的相关题录，然后在题录后面展示当前文章被检索的次数， 然后在统计
	 * 以便于在文章推荐的时候可以通过这个去排序然后展示前几个文章
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/goHtml", method = RequestMethod.GET)
	public Result goHtml(String stdFileId) {
		Result result = new Result();
		// 通过我们传的id去查询找那一页 ，就是去将我们的HTML文件的路径找到即可
		Map<String, Object> map = new HashMap<String, Object>();
		String page = null;
		if (stdFileId != null) {
			System.out.println("请求参数是：" + stdFileId);
			// 对这个请求参数进行判断，当满足条件的时候，当没有后面的页码的时候，默认让他展示第一页，当进行翻页的时候，就可以直接调用下面的方法来实现，翻页的效果
			String[] ids = stdFileId.split("\\+");
			// 得到标准原文id，然后在我们得到次数上加一
			String id = ids[0];
			StdFileVo stdFileVo = standardFileService.getStdFileById(id);
			if (stdFileVo != null) {
				//这个是搜索次数，每调用一次就会在原有的基础上加1
				Integer number = stdFileVo.getCheckNumber();
				if(number==null) {
					number=0;
				}
				Integer newNumber = number + 1;
				S_Std_FileDo stdFileDo = new S_Std_FileDo();
				stdFileDo.setCheckNumber(newNumber);
				stdFileDo.setStdFileId(id);
				standardFileService.updateById(stdFileDo);
			}
			if (ids.length == 1) {
				// 表示是从简单查询过来的 然后让他默认展示第一页
				stdFileId = ids[0] + "+1";
				page = "1";
			} else {
				page = ids[ids.length - 1];
			}
			String URL = "F:\\bzcx\\pdf\\html\\" + stdFileId + ".html";
			map.put("htmlUrl", URL);
			map.put("page", page);
			map.put("stdFileId",  ids[0].toString());
			map.put("resultFileId", stdFileId);
			result.setCode(200);
			result.setData(map);
		}
		return result;
	}

	/**
	 * 精准查询——当选中某一页时,然后查看当前文章的具体的某一页时，可以上下翻页,上下翻页 就是输入+1 或者是-1，也可以跳页
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getHtml", method = RequestMethod.GET)
	public Result getHtml(String stdFileId, String page) {
		Result result = new Result();
		// 通过我们传的id去查询找那一页 ，就是去将我们的HTML文件的路径找到即可
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("请求参数是：" + stdFileId);
		if (stdFileId != null) {
			// 将当前传过来的值进行切割，然后在拼接返回 相应的值即可
			String[] ids = stdFileId.split(".html");
			String idsPage = ids[0];
			// 然后在切割
			String[] idsNum = idsPage.split("\\+");
			String id = idsNum[0];
			// 通过这个去获取这个的总页数，然后当我们的totalPage大于或者等于总页数时，在提示，当前页数是最后一页
			if (id != null) {
				StdFileVo stdFileVo = standardFileService.getStdFileById(id);
				if (stdFileVo != null) {
					int pageNumber = stdFileVo.getPageNumber();
					String pageNum = idsNum[idsNum.length - 1];
					int pages = Integer.valueOf(page);
					int pageNums = Integer.valueOf(pageNum);
					int totalPage = pages + pageNums;
					if (pageNumber <= totalPage) {
						// 表示当前页是最后一页
						totalPage = pageNumber;
						result.setMsg("当前页是最后一页!");
					}
					if (totalPage == 1) {
						// 表示是第一页
						result.setMsg("当前页是第一页！");
					}
					// 拼接我们需要的字符串
					String URL = "F:\\bzcx\\pdf\\html\\" + id + "+" + totalPage + ".html";
					map.put("htmlUrl", URL);
					map.put("page", totalPage);
					map.put("totalPage", pageNumber);
					map.put("resultFileId", id + "+" + totalPage);
					result.setCode(200);
					result.setData(map);
				}
			}
		}
		return result;
	}

	/**
	 * 简单分类高级查——从题录列表到题录的内容（需要的是STDFileId）
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getStdFileId", method = RequestMethod.POST)
	public Result getStdFileId(@RequestParam Map<String, Object> map) {
		Result result = new Result();
		String stdFileId = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (map != null) {
			String typeName = (String) map.get("typeName");
			if (typeName.equals("法律法规")) {
				// 需要去标准原文中去查询，然后返回我们需要的stdFileId
				String noTrim = (String) map.get("noTrim");
				//String trim=StringUtil.StrCha(noTrim);
				StdFileVo stdFileVo = standardFileService.findStandFileByStdNo(noTrim);
				if (stdFileVo != null) {
					stdFileId = stdFileVo.getStdFileId();
					// String pageNumber=(String)map.get("pageNumber");
					result.setCode(200);
					resultMap.put("pageNumber", stdFileVo.getPageNumber());
					resultMap.put("stdFileId", stdFileId);
					result.setData(resultMap);
				} else {
					result.setCode(500);
					result.setMsg("暂无原文！");
				}
			} else {
				String noTrim = (String) map.get("noTrim");
				//String trim=StringUtil.StrCha(noTrim);
				StdFileVo stdFileVo = standardFileService.findStandFileByStdNo(noTrim);
				if (stdFileVo != null) {
					stdFileId = stdFileVo.getStdFileId();
					result.setCode(200);
					resultMap.put("pageNumber", stdFileVo.getPageNumber());
					resultMap.put("stdFileId", stdFileId);
					result.setData(resultMap);
				} else {
					result.setCode(500);
					result.setMsg("标准原文不存在！");
				}
			}
		}
		return result;
	}

	/**
	 * 通过传入我们的收藏的时候的id去将我们要展示的内容展示在相应的位置上
	 * 
	 * @param standardId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/collect", method = RequestMethod.GET)
	public Result collectByHtml(String standardId) {
		// 先去我们的法律法规中去查找找到之后直接将该法律法规下的原文的id找到返回回去
		// 如果没有找到就去我们的标准题录中去查找，然后得到题录对象，然后将其中标准号得到，然后去标准原文中去查找得到标准原文id，然后将其返回
		Result result = new Result();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (standardId != null) {
			LawsVo lawsVo = lawsService.findLawsById(standardId);
			if (lawsVo != null) {
				StdFileVo stdFileVo = standardFileService.findStdFileByLawsId(standardId);
				if (stdFileVo != null) {
					String stdFileId = stdFileVo.getStdFileId();
					// String pageNumber=(String)map.get("pageNumber");
					result.setCode(200);
					resultMap.put("pageNumber", stdFileVo.getPageNumber());
					resultMap.put("stdFileId", stdFileId);
					result.setData(resultMap);
				} else {
					result.setCode(500);
					result.setMsg("暂无原文！");
				}
			} else {
				StandardVo standardVo = standardService.findstandardById(standardId);
				if (standardVo != null) {
					String standardNo = standardVo.getStandardNo();
					if (standardNo != null) {
						StdFileVo stdFileVo = standardFileService.findStandFileByStdNo(standardNo);
						if (stdFileVo != null) {
							String stdFileId = stdFileVo.getStdFileId();
							result.setCode(200);
							resultMap.put("pageNumber", stdFileVo.getPageNumber());
							resultMap.put("stdFileId", stdFileId);
							result.setData(resultMap);
						} else {
							result.setCode(500);
							result.setMsg("标准原文不存在！");
						}
					}
				}else {
					result.setCode(501);
					result.setMsg("不存在此题录！");
				}
			}
		}
		return result;
	}

	/**
	 * 通过原文的id去获取题录中的HTMLURL 然后将其定位到相应的题录的静态页，从标准原文到标准题录（需要的是题录中的HTMLURL属性）
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCatalogue", method = RequestMethod.GET)
	public Result getCatalogue(String stdFileId) {
		Result result = new Result();
		// 先通过我们的STDFileId去查询 当在我们的标准题录中查询，当不存在的时候，就在我们的法律法规中查询，然后获取到我们的HTMLURL
		String htmlUrl = null;
		if (stdFileId != null) {
			//StdFileVo stdFileVo = standardFileService.getStdFileById(stdFileId);findById
			StdFileVo stdFileVo = standardFileService.findById(stdFileId);
			if (stdFileVo != null) {
				// 获取标准号，和标准id
				String stdNo = stdFileVo.getStandardNo();
				StandardVo standardVo = standardService.getStandardByNo(stdNo);
				if (standardVo != null) {
					Map<String,Object> map= new HashMap<String,Object>();
					map.put("type", "1");
					map.put("standardVo", standardVo);
					result.setCode(200);
					result.setData(map);
					
				} else {// 表示在标准题录中没有查到数据，去法律题录中去查询
					String standardNo = stdFileVo.getStandardNo();
					String noTirm=	StringUtil.StrCha(standardNo);
					LawsVo lawsVo = lawsService.findByLawsNoTrim(noTirm);
					if (lawsVo != null) {
						Map<String,Object> map= new HashMap<String,Object>();
						map.put("type", "0");
						map.put("lawsVo", lawsVo);
						result.setCode(200);
						result.setData(map);
					} else {
						result.setCode(500);
						result.setMsg("请添加该原文的题录！");
					}
				}
			}
		}
		return result;
	}

	/**
	 * 精准查询——当选中某一页时,然后查看当前文章的具体的某一页时，可以上下翻页,上下翻页 就是输入+1 或者是-1，也可以跳页
	 * 
	 * @param stdFileId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/jumpPage", method = RequestMethod.GET)
	public Result jumpPage(String stdFileId, String page) {
		Result result = new Result();
		// 通过我们传的id去查询找那一页 ，就是去将我们的HTML文件的路径找到即可
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("请求参数是：" + stdFileId);
		if (stdFileId != null) {
			// 将当前传过来的值进行切割，然后在拼接返回 相应的值即可
			String[] ids = stdFileId.split(".html");
			String idsPage = ids[0];
			// 然后在切割
			String[] idsNum = idsPage.split("\\+");
			String id = idsNum[0];
			// 直接加起来 即可

			// 通过这个去获取这个的总页数，然后当我们的totalPage大于或者等于总页数时，在提示，当前页数是最后一页
			if (id != null) {
				StdFileVo stdFileVo = standardFileService.getStdFileById(id);
				if (stdFileVo != null) {
					int pageNumber = stdFileVo.getPageNumber();
					//String pageNum = idsNum[idsNum.length - 1];
					int pages = Integer.valueOf(page);
					// int pageNums = Integer.valueOf(pageNum);
					int totalPage = pages;
					if (pageNumber <= totalPage) {
						// 表示当前页是最后一页
						totalPage = pageNumber;
						result.setMsg("当前页是最后一页!");
					}
					if (totalPage <= 1) {
						totalPage=1;
						// 表示是第一页
						result.setMsg("当前页是第一页！");
					}
					// 拼接我们需要的字符串
					String URL = "F:\\bzcx\\pdf\\html\\" + id + "+" + totalPage + ".html";
					map.put("htmlUrl", URL);
					map.put("page", totalPage);
					map.put("totalPage", pageNumber);
					result.setCode(200);
					result.setData(map);
				}
			}
		}
		return result;
	}
	/**
	 * 通过标准原文id去展示相应的题录
	 * @param stdFileId
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value="/showData",method=RequestMethod.POST)
	public void showData(File_LawsDO fileLaws) {
		
		int flag=  lawsFileService.insert(fileLaws);
		System.out.println(flag);
	}
*/
	/**
	 * 生成一条题录的静态页面
	 */
	@ResponseBody
	@RequestMapping(value = "/insertTemplete", method = RequestMethod.POST)
	public Result insertTemplete(@RequestParam Map<String, Object> map) {
		Result result = new Result();
		L_LawsDo laws = new L_LawsDo();
		String htmlUrl = (String) map.get("htmlUrl");
		String lawsId = (String) map.get("lawsId");
		String lawsNo = (String) map.get("lawsNo");
		String lawsCnName = (String) map.get("lawsCnName");
		String pubOrg = (String) map.get("pubOrg");
		// Date publishDate=(Date) map.get("publishDate");
		// Date implementationDate=(Date) map.get("implementationDate");
		// String effectivenessLevel=(String) map.get("effectivenessLevel");
		laws.setLawsNo(lawsNo);
		laws.setLawsCnName(lawsCnName);
		laws.setPubOrg(pubOrg);
		laws.setLawsId(lawsId);
		// 调用静态生成页
		try {
			StandStatic.lawsState(laws, htmlUrl);
			result.setCode(200);
			result.setMsg("生成静态页面成功");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setCode(500);
			result.setMsg("生成失败!");
		}

		return result;
	}
	/**
	 * 分类查询，当我们点击某一个具体的分类的时候，就会去找这个分类然后将当前分类下的子分类查找出来，然后将其在数据库中查询，然后进行展示
	 * 		而我们的分类条件查询，先按照当前分类的分类码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/icsClassification",method= {RequestMethod.GET,RequestMethod.POST})
	public Result icsClass() {
		return null;
	}

}
