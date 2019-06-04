package com.folkestone.bzcx.controller.admin.dm;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.folkestone.bzcx.bean.bean_do.dm.S_StandardDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.FileUtil;
import com.folkestone.bzcx.common.util.JurisdictionUtil;
import com.folkestone.bzcx.common.util.POIUtil;
import com.folkestone.bzcx.common.util.PathUntil;
import com.folkestone.bzcx.common.util.StandStatic;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.ArchitectureManageService;
import com.folkestone.bzcx.service.dm.StandardService;
import com.folkestone.bzcx.service.interact.ValidatService;
import com.folkestone.bzcx.service.user.OperLogService;

/**
 * Describe：处理题录相关的Controller
 * 
 * @author smallking
 *
 *         2017年11月1日
 */
@Controller
@RequestMapping("/admin/standard")
public class AdminStandardController extends BaseController {
	@Autowired
	private StandardService standardService;
	@Autowired
	private ArchitectureManageService architectureManageService;
	@Autowired
	private OperLogService operLogService;
	@Autowired
	private ValidatService validat;

	/**
	 * 查询题录或者 查询所给nodeId下的所有的标准
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	public EasyUISeparatPage list(@RequestParam Map<String, Object> param)
			throws JsonParseException, JsonMappingException, IOException {
		param.put("isback", "true");
		Query query = new Query(param);
		// 如果是根据节点查 就调用节点的方法
		if (query.get("nodeId") != null) {
			return standardService.listStandardByNodeId(query);
		}
		return standardService.listStandardByQuery(query);
	}

	/**
	 * 查询一条题录详情
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = { RequestMethod.POST })
	public Result select(String standardId, HttpServletRequest req) {
		Result result = new Result(true);
		StandardVo standardVo = standardService.getStandardById(standardId);
		UserVo userVo = super.getUser(req);
		ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(userVo.getDepartmentId());
		standardVo.setNodeCode(standardService.getNodeCode(standardId, manageVo.getArchitectureId()));
		result.setData(standardVo);
		return result;
	}

	/**
	 * 发布单位
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getdeparUnit", method = { RequestMethod.GET })
	public Result getdeparUnit(String key) {
		Query query = new Query();
		query.setRows(10);
		query.put("key", key);
		Result result = new Result(true);
		List<StandardVo> standardVo = standardService.getdeparUnit(query);
		result.setData(standardVo);
		return result;
	}

	/**
	 * 起草单位
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getdepartment", method = { RequestMethod.GET })
	public Map<String, Object> getdepartment(String key) {
		Map<String, Object> map = new HashMap<>();

		Query query = new Query();
		query.setRows(10);
		query.put("key", key);
		List<String> standardVo = standardService.getdepartment(query);
		map.put("q", "1");
		map.put("p", false);
		map.put("s", standardVo);
		return map;
	}

	/**
	 * 录入一条标准题录
	 * 
	 * @param standardDo
	 * @param nodeCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method = { RequestMethod.POST })
	public Result insert(HttpServletRequest req, S_StandardDo standardDo, String nodeCode) {
		Result result = new Result(false);
		if (standardService.checkStandarNo(standardDo)) {
			result.setCode(10000);
			return result;
		}
		// 设置上传单位为当前用户所在的部门
		UserVo user = super.getUser(req);
		if (user != null) {
			standardDo.setUploadUnit(user.getDepartmentId());
		}
		if (user.getDepartmentParent().equals("0")) {
			try {
				int row = standardService.insert(standardDo);
				if (row == 1) {
					result.setResult(true);
					operLogService
							.insert(super.getOperLog(req, "1006", "题录名称:" + standardDo.getStandardCnName(), null));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			result.setCode(9000);
			result.setMsg("没有权限添加");
		}
		return result;
	}

	/**
	 * 修改题录
	 * 
	 * @param standardDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	public Result update(HttpServletRequest req, S_StandardDo standardDo) {
		Result result = new Result(false);
		// 检查标准号是否存在
		if (standardService.checkStandarNo(standardDo)) {
			result.setCode(10000);
			return result;
		}
		UserVo user = super.getUser(req);
		try {
			String htmlurl = ContantFinalUtil.STATIC_STANDARDURL + "img\\" + System.currentTimeMillis() + ".html";
			standardDo.setHtmlUrl(htmlurl);
			int row = standardService.update(standardDo, user);
			if (row == 1) {
				result.setResult(true);
				StandardVo standardVo = standardService.getStandardById(standardDo.getStandardId());
				String filename = PathUntil.cutPathindexof(standardVo.getHtmlUrl(), "\\", false);
				File file = new File(ContantFinalUtil.STATIC_STANDARDURL + filename);
				if (file.exists()) {
					file.delete();
				}
				StandStatic.state(standardVo, htmlurl);
			} else if (row == -1) {
				result.setCode(900);
				result.setMsg("没有权限修改");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/demo",
	 * method={RequestMethod.POST,RequestMethod.GET} ) public void
	 * select(HttpServletRequest req) throws IOException, TemplateException{
	 * StandardVo standardById =
	 * standardService.getStandardById("S_S-0b6a81c59af746a2bd5f1ce19f0bf417");
	 * StandardService.state(standardById,"D:\\2\\1\\2.html"); }
	 */

	/**
	 * 删除题录(删除之前要检查当前操作人是否有删除题录的权限, 题录有个上传单位字段,只有上传单位的标准管理员才有当前题录的删除权限)
	 * 
	 * @param standardIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public Result select(HttpServletRequest req, String[] standardIds) {
		Result result = new Result(true);
		UserVo user = super.getUser(req);
		String msg = "";
		try {
			for (String standardId : standardIds) {
				// 先检查当前人是否有删除当前题录的权限
				StandardVo standardVo = standardService.getStandardById(standardId);
				// user.getDepartmentId().equals(standardVo.getUploadUnit()) ||
				if (user.getDepartmentParent().equals("0")) {
					int row = standardService.deleteById(standardId);
					String filename = PathUntil.cutPathindexof(standardVo.getHtmlUrl(), "\\", false);
					if (row == 1) {
						// 删除对题录的需求征集
						int deleteByStandId = validat.deleteByStandId(standardId);
						if (deleteByStandId > 0) {
							File file = new File(ContantFinalUtil.STATIC_STANDARDURL + filename);
							if (file.exists()) {
								file.delete();
							}
						}
						operLogService.insert(super.getOperLog(req, "1008", standardId, null));
					}
				} else {
					msg += standardVo.getStandardNo() + "</br>";
				}
			}
		} catch (Exception e) {
			result.setResult(false);
			e.printStackTrace();
		}
		if (!msg.equals("")) {
			result.setCode(10009);
			result.setMsg("没有以下题录的删除权限: </br>" + msg);
		}
		return result;
	}

	/**
	 * 题录导入 重复导入的处理方案目前是这样的 1：属性覆盖，保证Id不变，而不是删除记录之后重新插入
	 * 2：那个单位导入了题录，只改变题录在改单位下的绑定，不改变整体的
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/import", method = { RequestMethod.GET, RequestMethod.POST })
	public Object importStandard(@RequestParam(value = "standardFile") MultipartFile mFile, HttpServletRequest req) {
		Result result = new Result(false);
		UserVo user = super.getUser(req);
		// 第一步，将文件保存在本地，返回一个文件路径。
		Map<String, String> fileInfo = FileUtil.saveFile(mFile);
		if (fileInfo == null) {
			return result;
		}
		// 第二步，解析Excel，得到题录对象集合
		List<?> standardList = POIUtil.importStandard(fileInfo.get("filePath"));
		if (standardList == null) {
			return result;
		} else if (standardList.size() == 1) {
			String str = standardList.get(0).toString();
			if (str.startsWith("对不起")) {
				result.setResult(true);
				result.setCode(400);
				result.setMsg(str);
				return result;
			}
		}
		// 第三步,执行数据库插入
		Map<String, Integer> map = new HashMap<String, Integer>();
		int totalCount = standardList.size();
		int sucCount = 0;
		int failCount = 0;
		int repeatCount = 0;
		for (Object obj1 : standardList) {
			S_StandardDo standardDo = (S_StandardDo) obj1;
			standardDo.setUploadUnit(user.getDepartmentId());
			try {
				// 插入之前， 先看看这条题录是否已经存在 getStandardByNoTrim
				StandardVo standardVo = standardService.getStandardByNoTrim(standardDo.getStandardNoTrim());
				// 这是标准类型规则
				/*
				 * String dictionaries = Dictionaries.dictionaries(standardDo.getStandardNo());
				 * System.out.println("========"+dictionaries); List<StandardTypeVo>
				 * selectDictionforLetter = StandardType.selectDictionforLetter(dictionaries);
				 * if(selectDictionforLetter.size() > 0) {
				 * standardDo.setStandardType(selectDictionforLetter.get(0).getTypeLetter()); }
				 */
				int row;
				String htmlurl = ContantFinalUtil.STATIC_STANDARDURL + System.currentTimeMillis() + ".html";
				standardDo.setHtmlUrl(htmlurl);
				if (standardVo != null) {
					// 如果已经存在 则覆盖。
					standardDo.setStandardId(standardVo.getStandardId());
					// 把之前生成的静态页面删除
					row = standardService.update(standardDo);
					/*if (row == 1) {
						if (null != standardVo.getHtmlUrl()) {
							File fi = new File(standardVo.getHtmlUrl());
							if (fi.exists()) {
								fi.delete();
							}
						}
						StandStatic.state(standardDo, htmlurl);
						repeatCount++;
					}*/
				} else {
					row = standardService.insert(standardDo);
					/*if (row == 1) {
						StandStatic.state(standardDo, htmlurl);
						sucCount++;
					}*/
				}
			} catch (Exception e) {
				e.printStackTrace();
				failCount++;
			}
		}
		map.put("total", totalCount);
		map.put("suc", sucCount);
		map.put("fail", failCount);
		map.put("repeat", repeatCount);
		result.setResult(true);
		result.setData(map);
		return result;
	}

	/**
	 * 题录导出
	 * 
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@ResponseBody
	@RequestMapping(value = "/export", method = { RequestMethod.GET, RequestMethod.POST })
	public Object export(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		Result result = new Result(false);
		UserVo userVo = super.getUser(req);
		ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(userVo.getDepartmentId());
		// 第一步 得到导出的题录的集合
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		query.setRows(100000);
		query.put("uploadUnit", userVo.getDepartmentId());
		EasyUISeparatPage standardList = standardService.listStandardByQuery(query);
		for (Object tandardVo : standardList.getRows()) {
			StandardVo standardVo = (StandardVo) tandardVo;
			String nodeCode = standardService.getNodeCode(standardVo.getStandardId(), manageVo.getArchitectureId());
			standardVo.setNodeCode(nodeCode);
		}
		// 第二步 将集合传给Excel工具类。导出excel
		try {
			String filePath = POIUtil.exportStandard("xlsx", standardList.getRows());
			return super.downloadEcel(filePath, "题录.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
