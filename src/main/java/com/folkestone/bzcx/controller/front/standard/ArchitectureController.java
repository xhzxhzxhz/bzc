package com.folkestone.bzcx.controller.front.standard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_dto.arch.StandardStatistic;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUITree;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureNodeVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.JurisdictionUtil;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.ArchitectureManageService;
import com.folkestone.bzcx.service.dm.ArchitectureNodeService;
import com.folkestone.bzcx.service.dm.StandardService;
/**
 * Describe：处理前台标准体系查询的controller。
 * 
 * @author smallking
 *
 * 2017年11月17日
 */
@Controller
@RequestMapping(value = "/architecture")
public class ArchitectureController extends BaseController{	
	@Autowired
	private ArchitectureManageService architectureManageService;
	@Autowired
	private ArchitectureNodeService architectureNodeService;
	@Autowired
	private StandardService standardService;
	/**
	 * 当所给architectureId不为空时候，返回nodeId的所有子节点组成的树
	 * 否则返回昆仑体系的树或者本单位的树
	 * @param req
	 * @param type max 或者 user
 	 * @param architectureId
	 * @param nodeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tree", method={RequestMethod.POST})
	public List<EasyUITree> maxTree(HttpServletRequest req, String type, String architectureId,
			@RequestParam(value="nodeId", defaultValue="0") String nodeId){
		if(StringUtil.isEmpty(architectureId) && type == null){
			return new ArrayList<EasyUITree>();
		}
		if(!StringUtil.isEmpty(architectureId)){
			return  architectureNodeService.getfaNodeTree(architectureId, nodeId,false,req);
		}
		if(type.equals("max")){
			ArchitectureManageVo manageVo = architectureManageService.getParentestManage();
			return  architectureNodeService.getfaNodeTree(manageVo.getArchitectureId(), nodeId,false,req);
		}
		if(type.equals("user")){
			UserVo userVo = super.getUser(req);
			ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(userVo.getDepartmentId());
			if(manageVo == null) {
				return null;
			}
			return  architectureNodeService.getfaNodeTree(manageVo.getArchitectureId(), nodeId,false,req);
		}
		return null;
	}
	/**
	 * 返回所给体系ID下的体系树的最父级节点
	 * 如果architectureId为null，则返回昆仑体系或者本单位体系
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/list", method={RequestMethod.POST})
	public List<ArchitectureNodeVo> nodeList(HttpServletRequest req, String type, String architectureId){
		Query query = new Query();
		query.put("parentId", "0");
		if(!StringUtil.isEmpty(architectureId)){
			query.put("architectureId", architectureId);
		}else if(!StringUtil.isEmpty(type)){
			if( type.equals("max")) {
				ArchitectureManageVo manageVo = architectureManageService.getParentestManage();
				query.put("architectureId", manageVo.getArchitectureId());
			}else if(type.equals("user")){
				UserVo userVo = super.getUser(req);
				ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(userVo.getDepartmentId());
				if(manageVo == null) {
					return null;
				}
				query.put("architectureId", manageVo.getArchitectureId());
			}
		}
		return architectureNodeService.listNodeByQuery(query);
	}
	
	/**
	 * 查询体系节点下的标准，需要参数nodeId
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method={RequestMethod.POST} )
	public EasyUISeparatPage list(@RequestParam Map<String, Object> param, HttpServletRequest req){
		Query query = new Query(param);
		// 根据权限 查询当前用户能否查询企标
		if(StringUtil.isEmpty(req.getSession().getAttribute(ContantFinalUtil.SEARCH_QB_RIGHT) + "")){
			param.put("standardType", "G");
		}
		// 如果是根据节点查 就调用节点的方法
		if(query.get("nodeId") == null){
			return null;
		}
		return standardService.listStandardByNodeId(query);
	}
	
	/**
	 * 统计一个节点及其直接的子节点下
	 * 每种标准都有多少
	 * @param nodeId
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "/statistic", method={RequestMethod.POST} )
	public EasyUISeparatPage statistic(HttpServletRequest req, @RequestParam Map<String, Object> param) throws IOException{
		EasyUISeparatPage data = new EasyUISeparatPage();
		List<StandardStatistic> resultList = new ArrayList<StandardStatistic>();
		List<StandardStatistic> resultList1 = new ArrayList<StandardStatistic>();
		super.setParam(req, param);
		String nodeId = param.get("nodeId") + "";
		if(StringUtil.isEmpty(nodeId)){
			StandardStatistic statistic = new StandardStatistic();
			statistic.setNodeName("");
			resultList.add(statistic);
			data.setData(resultList);
			return data;
		}
		//是否有体系标准的统计
		Boolean haveArchCount = false;
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		// 查出本节点
		ArchitectureNodeVo node = architectureNodeService.getNodeById(nodeId);
		//查询这个节点下的所有标准  作为合计
		if(node!=null) {
		query.put("nodeId", node.getNodeId());
		query = JurisdictionUtil.searchJurisdiction(req, query);
		List<StandardVo> standardList1 = standardService.listStandardByNodeIdNoPage(query);
		StandardStatistic statistic1 = standardService.statistics(standardList1,haveArchCount);
		statistic1.setNodeName(node.getNodeCnName() + "(" + node.getNodeCode() + ")");
		resultList1.add(statistic1);
		query.clear();
		// 查出其直接子节点
		query.put("parentId", nodeId);
		query = JurisdictionUtil.searchJurisdiction(req, query);
		List<ArchitectureNodeVo> nodeList = architectureNodeService.listNodeByQuery(query);
		
		if(nodeList.isEmpty()) {
		nodeList.add(0, node);
		}
		
		query.remove("parentId");
		query = JurisdictionUtil.searchJurisdiction(req, query);
		System.out.println("node...:......   "+nodeList.size());
		for(ArchitectureNodeVo nodeVo : nodeList){
			System.out.println(nodeVo.getNodeCnName());
			String Nid = "";
			if(nodeVo.getNodeId().equals(nodeId)) {
				Nid=nodeId;
				//这是一个父级节点   查询他自己名下的标准 
				query.put("nodeId", Nid);
				query = JurisdictionUtil.searchJurisdiction(req, query);
				List<StandardVo> standardList = standardService.StandardByNodeId(query);
				StandardStatistic statistic = standardService.statistics(standardList,haveArchCount);
				statistic.setNodeName(nodeVo.getNodeCnName() + "(" + nodeVo.getNodeCode() + ")");
				resultList.add(statistic);
			}else {
				Nid=nodeId+","+nodeVo.getNodeId();
				query.put("nodeId", Nid);
				query = JurisdictionUtil.searchJurisdiction(req, query);
				List<StandardVo> standardList = standardService.listStandardByNodeIdNoPage(query);
				StandardStatistic statistic = standardService.statistics(standardList,haveArchCount);
				statistic.setNodeName(nodeVo.getNodeCnName() + "(" + nodeVo.getNodeCode() + ")");
				resultList.add(statistic);
			}
		  }
		}
		data.setTaotaldata(resultList1);
		data.setTotal(Long.valueOf(resultList.size()));
		data.setData(resultList);
		return data;
	}
}
