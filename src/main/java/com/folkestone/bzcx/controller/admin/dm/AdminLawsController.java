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
import com.folkestone.bzcx.bean.bean_do.dm.L_LawsDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.FileUtil;
import com.folkestone.bzcx.common.util.JurisdictionUtil;
import com.folkestone.bzcx.common.util.POIUtil;
import com.folkestone.bzcx.common.util.PathUntil;
import com.folkestone.bzcx.common.util.StandStatic;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.ArchitectureManageService;
import com.folkestone.bzcx.service.dm.LawsService;
import com.folkestone.bzcx.service.dm.StandardService;
import com.folkestone.bzcx.service.interact.ValidatService;
import com.folkestone.bzcx.service.user.OperLogService;

/**
 * 法律法规题录——控制层
 * @author zqm
 *
 */
@Controller
@RequestMapping("/admin/laws")
public class AdminLawsController extends BaseController {
	@Autowired
	private LawsService lawsService;
	@Autowired
	private OperLogService operLogService;
	@Autowired 
	private ValidatService validat;
	@Autowired
	private ArchitectureManageService architectureManageService;
	@Autowired
	private StandardService standardService;
	/**
	 * 录入一条法律题录,并在添加的时候生成静态页面
	 * @param req
	 * @param standardDo
	 * @param nodeCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Result insert(HttpServletRequest req,L_LawsDo lawsDo){
		
		System.out.println(lawsDo.getLawsCnName());
		Result result = new Result(false);
		if(lawsService.checkLawsNo(lawsDo)){
			result.setCode(10000);
			return result;
		}
		// 设置上传单位为当前用户所在的部门
		UserVo user = super.getUser(req);
		if(user != null){
			lawsDo.setUploadUnit(user.getDepartmentId());
		}
		if(user.getDepartmentParent().equals("0")) {
			try {
				String htmlurl = ContantFinalUtil.STATIC_STANDARDURL +"img\\"+ System.currentTimeMillis() + ".html";
				lawsDo.setHtmlUrl(htmlurl);
				int row  = lawsService.insert(lawsDo);
				if(row == 1){
					
					result.setResult(true);
					LawsVo lawsVo = lawsService.findLawsById(lawsDo.getLawsId());
					String filename = PathUntil.cutPathindexof(lawsVo.getHtmlUrl(), "\\", false);
					File file = new File(ContantFinalUtil.STATIC_STANDARDURL+filename);
					if(file.exists()) {
						file.delete();
					}
					//创建静态页
					StandStatic.lawsState(lawsVo, htmlurl);
					operLogService.insert(super.getOperLog(req, "1006", "题录名称:" + lawsDo.getLawsCnName(), null));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			result.setCode(9000);
			result.setMsg("没有权限添加");
		}
		return result;
	}
	/**
	 * 查询一条题录详情
	 * @param standardId
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = { RequestMethod.POST })
	public Result select(String lawsId, HttpServletRequest req) {
		Result result = new Result(true);
		LawsVo lawsVo = lawsService.findLawsById(lawsId);
		UserVo userVo = super.getUser(req);
		ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(userVo.getDepartmentId());
		lawsVo.setNodeCode(lawsService.getNodeCode(lawsId, manageVo.getArchitectureId()));
		result.setData(lawsVo);
		return result;
	}
	/**
	 * 修改题录,并同步删除旧的静态页生成新的静态页面，由于没有模板文件，暂时先注释掉
	 * @param standardDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method={RequestMethod.POST})
	public Result update(HttpServletRequest req, L_LawsDo lawsDo){
		Result result = new Result(false);
		/*//检查标准号是否存在
		if(lawsService.checkLawsNo(lawsDo)){
			result.setCode(10000);
			return result;
		}*/
		UserVo user = super.getUser(req);
		//当这个为空的时候去模拟一个管理员用户
		if(user==null) {
			UserVo person= new UserVo();
			person.setAccount("admin1");
			person.setPassword("e10adc3949ba59abbe56e057f20f883e");
			person.setId("user-5590cf2e466b427787153f7dcb4ec60c");
			person.setDepartmentId("depart-3a1281e9b1ec49b8acb93791f61e85b9");
			user=person;
		}
		try {
			String htmlurl = ContantFinalUtil.STATIC_STANDARDURL +"img\\"+ System.currentTimeMillis() + ".html";
			lawsDo.setHtmlUrl(htmlurl);
			int row = lawsService.update(lawsDo,user);
			if(row == 1){
				result.setResult(true);
				//LawsVo lawsVo = lawsService.findLawsById(lawsDo.getLawsId());
				/*String filename = PathUntil.cutPathindexof(lawsVo.getHtmlUrl(), "\\", false);
				File file = new File(ContantFinalUtil.STATIC_STANDARDURL+filename);
				if(file.exists()) {
					file.delete();
				}
				//创建静态页
				StandStatic.lawsState(lawsVo, htmlurl);*/
			}else if(row == -1){
				result.setCode(900);
				result.setMsg("没有权限修改");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除题录(删除之前要检查当前操作人是否有删除题录的权限,
	 * 			题录有个上传单位字段,只有上传单位的标准管理员才有当前题录的删除权限)
	 * @param standardIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method={RequestMethod.POST} )
	public Result select(HttpServletRequest req,  @RequestParam String[] lawsIds){
		Result result = new Result(true);
		UserVo user = super.getUser(req);
		String msg = "";
		
		try {
			if(lawsIds!=null &&lawsIds.length>0) {

				
				for(String lawsId : lawsIds){
					// 先检查当前登录人是否有删除当前题录的权限
					LawsVo standardVo = lawsService.getStandardById(lawsId);
					//user.getDepartmentId().equals(standardVo.getUploadUnit()) || 
					if(user.getDepartmentParent().equals("0")){
						int row = lawsService.deleteById(lawsId);
						//String filename = PathUntil.cutPathindexof(standardVo.getHtmlUrl(), "\\", false);
						/*if(row == 1){
							//删除对题录的需求征集
							int deleteByStandId = validat.deleteByStandId(standardId);
							if(deleteByStandId > 0) {
								File file = new File(ContantFinalUtil.STATIC_STANDARDURL+filename);
								if(file.exists()) {
									file.delete();
								}
							}
							
						}*/
						operLogService.insert(super.getOperLog(req, "1008", lawsId, null));
					}
					else{
						msg += standardVo.getLawsNo() + "</br>";	
					}
				}
			
			}
		} catch (Exception e) {
			result.setResult(false);
			e.printStackTrace();
		}
		if(!msg.equals("")){
			result.setCode(10009);
			result.setMsg("没有以下题录的删除权限: </br>" + msg);
		}
		return result;
	}
	/**
	 * 题录导入
	 * 重复导入的处理方案目前是这样的
	 * 1：属性覆盖，保证Id不变，而不是删除记录之后重新插入
	 * 2：那个单位导入了题录，只改变题录在改单位下的绑定，不改变整体的
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
		List<?> lawsList = POIUtil.importLaws(fileInfo.get("filePath"));
		if (lawsList == null) {
			return result;
		} else if (lawsList.size() == 1) {
			String str = lawsList.get(0).toString();
			if (str.startsWith("对不起")) {
				result.setResult(true);
				result.setCode(400);
				result.setMsg(str);
				return result;
			}
		}
		// 第三步,执行数据库插入
		Map<String, Integer> map = new HashMap<String, Integer>();
		int totalCount = lawsList.size();
		int sucCount = 0;
		int failCount = 0;
		int repeatCount = 0;
		for (Object obj1 : lawsList) {
			L_LawsDo lawsDo = (L_LawsDo) obj1;
			lawsDo.setUploadUnit(user.getDepartmentId());
			try {
				// 插入之前， 先看看这条题录是否已经存在 getStandardByNoTrim
				LawsVo lawsVO = lawsService.getStandardByNoTrim(lawsDo.getLawsNoTrim());
				int row;
				String htmlurl = ContantFinalUtil.STATIC_STANDARDURL + System.currentTimeMillis() + ".html";
				lawsDo.setHtmlUrl(htmlurl);
				if (lawsVO != null) {
					// 如果已经存在 则覆盖。
					lawsDo.setLawsId(lawsVO.getLawsId());
					// 把之前生成的静态页面删除
					row = lawsService.update(lawsDo,null);
					/*if (row == 1) {
						if (null != lawsVO.getHtmlUrl()) {
							File fi = new File(lawsVO.getHtmlUrl());
							if (fi.exists()) {
								fi.delete();
							}
						}
						StandStatic.lawsState(lawsDo, htmlurl);
						repeatCount++;
					}*/
				} else {
					row = lawsService.insert(lawsDo);
					/*if (row == 1) {
						StandStatic.lawsState(lawsDo, htmlurl);
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
		//得到当前登录人的信息
		UserVo userVo = super.getUser(req);
		//去体系表中查询
		ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(userVo.getDepartmentId());
		// 第一步 得到导出的题录的集合
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		query.setRows(100000);
		query.put("uploadUnit", userVo.getDepartmentId());
		EasyUISeparatPage lawsVoList = lawsService.listLawsByQuery(query);
		for (Object tandardVo : lawsVoList.getRows()) {
			LawsVo lawsVo = (LawsVo) tandardVo;
			//通过当前的法律题录id去获取法律节点相对应的
			String nodeCode = lawsService.getNodeCode(lawsVo.getLawsId(), manageVo.getArchitectureId());
			lawsVo.setNodeCode(nodeCode);
		}
		// 第二步 将集合传给Excel工具类。导出excel
		try {
			String filePath = POIUtil.exportLaws("xlsx", lawsVoList.getRows());
			return super.downloadEcel(filePath, "题录.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 分页展示所有的题录
	 * @param request
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value="/show" ,method=RequestMethod.GET )
	public Result show(HttpServletRequest request) {
		return null;
	}

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
		return lawsService.listLawsByQuery(query);
	}

	
}
