package com.folkestone.bzcx.controller.admin.dm;

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
import org.springframework.web.multipart.MultipartFile;

import com.folkestone.bzcx.bean.bean_do.dm.A_Architecture_ManageDo;
import com.folkestone.bzcx.bean.bean_do.dm.A_Architecture_NodeDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUITree;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureNodeVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.FileUtil;
import com.folkestone.bzcx.common.util.POIUtil;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.dm.ArchitectureManageService;
import com.folkestone.bzcx.service.dm.ArchitectureNodeService;
import com.folkestone.bzcx.service.user.UserService;
/**
 * Describe：处理后台标准体系操作。
 * 
 * @author smallking
 *
 * 2017年10月30日
 */
@Controller
@RequestMapping(value = "/admin/architecture")
public class AdminArchitectureController extends BaseController{
	
	@Autowired
	private ArchitectureManageService architectureManageService;
	@Autowired
	private ArchitectureNodeService architectureNodeService;
	/**
	 * 查询体系集合
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method={RequestMethod.POST} )
	public EasyUISeparatPage select(@RequestParam Map<String, Object> param,HttpServletRequest req){
		Query query = new Query(param);
		UserVo user = super.getUser(req);
		if(!user.getDepartmentParent().equals("0")) {
			query.put("publishUnit", user.getDepartmentId());
		}
		EasyUISeparatPage data = architectureManageService.listManageByQuery(query);
		return data;
	}
	
	/**
	 * 查询单条体系
	 * @param architectureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method={RequestMethod.POST} )
	public Result detail(String architectureId){
		Result result = new Result(true);
		ArchitectureManageVo manageVo = architectureManageService.getManageById(architectureId);
		result.setData(manageVo);
		return result;
	}
	
	/**
	 * 插入一条标准体系
	 * @param architectureDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method={RequestMethod.POST})
	public Result insert(A_Architecture_ManageDo architectureDo){
		Result result = new Result(false);
		// 先查询当前的部门是否已经有标准体系
		boolean exist = architectureManageService.checkPublishUnit(architectureDo.getPublishUnit());
		if(exist){
			result.setCode(10006);
			result.setMsg("当前部门下已经存在体系，请勿重复创建。");
			return result;
		}
		try {
			int row = architectureManageService.insert(architectureDo);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 体系导入
	 * @param architectureDo
	 * @param mFile
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "/import", method={RequestMethod.POST})
	public Result importArchitecture(A_Architecture_ManageDo architectureDo, 
			@RequestParam(value = "architectureFile") MultipartFile mFile){
		System.out.println("体系导入--------");
		Result result = new Result(false);
		try {
			int row = architectureManageService.insert(architectureDo);
			if(row == 1){
				// 第一步，先把文件存起来
				Map<String, String> fileInfo = FileUtil.saveFile(mFile);
				if(fileInfo == null){
					return result;
				}
				// 第二步，将文件传给poi类，解析其中内容，返回体系节点
				List<A_Architecture_NodeDo> nodeList = POIUtil.importNode(fileInfo.get("filePath"));
				// 先找出最顶级节点 因为导出的时候 排序过了，这儿就偷个懒，不排序了
				List<A_Architecture_NodeDo> parentList = new ArrayList<A_Architecture_NodeDo>();
				int length = nodeList.get(0).getNodeCode().length();
					for (int i = 0; i < nodeList.size(); i++) {
						A_Architecture_NodeDo node= nodeList.get(i);
							node.setArchitectureId(architectureDo.getArchitectureId());
							if(node.getNodeCode().length() == length && node.getNodeCode().substring(0,1).equals("1")){
								node.setNodeSort(i);
								parentList.add(node);
								nodeList.remove(i);
								i--;
						}
				}
					if(parentList==null) {
						result.setMsg("缺少公司节点");
						result.setResult(false);
					}else {
				for(A_Architecture_NodeDo node : parentList){
					// 201 变301  202  变302
					node.setParentId("0");
					node.setPath(node.getNodeId());
					String a = node.getNodeCode().split("[.]")[0];
					nodeImportInsert(nodeList, node ,(Integer.parseInt(a)+100)+".");	
				}
				result.setResult(true);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新一条标准体系
	 * @param architectureDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method={RequestMethod.POST})
	public Result update(A_Architecture_ManageDo architectureDo,HttpServletRequest req){
		Result result = new Result(false);
		UserVo userVo = super.getUser(req);
		try {
			int row = architectureManageService.update(architectureDo,userVo);
			if(row == 1){
				result.setResult(true);
			}else if(row == -1) {
				result.setCode(900);
				result.setMsg("没有权限修改");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除一个标准体系
	 * @param architectureId ：主键ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method={RequestMethod.POST})
	public Result delete(String[] architectureId,HttpServletRequest req){
		Result result = new Result(false);
		UserVo userVo = super.getUser(req);
		try {
			
			String deleteByIds = architectureManageService.deleteByIds(architectureId,userVo);
			result.setResult(true);
			result.setMsg(deleteByIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 检查当前查看的体系，是否是当前登录人所在部门的体系
	 * @param architectureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method={RequestMethod.POST})
	public Result check(HttpServletRequest req, String architectureId){
		Result result = new Result(true);
		UserVo user = super.getUser(req);
		if(user!=null) {
			ArchitectureManageVo manageById = architectureManageService.getManageById(architectureId);
			//查看是否有权限
			if(manageById.getPublishUnit().equals(user.getDepartmentId()) || user.getDepartmentParent().equals("0")) {
			if(user.getRoleName().equals("系统管理员")) {
				result.setData(true);
			}else {
				//查询角色 是不是系统管理员
				ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(user.getDepartmentId());
				if(manageVo.getArchitectureId().equals(architectureId)){
					result.setData(true);
				}else{
					result.setData(false);
				}
			}
			}else {
				result.setData(false);
			}
		}
		return result;
	}
	
	/**
	 * 根据给定的体系ID获取体系的树形json
	 * @param architectureId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/tree", method={RequestMethod.POST})
	public List<EasyUITree> nodeTree(String architectureId, HttpServletRequest req){
		if(StringUtil.isEmpty(architectureId)){
	    List<ArchitectureManageVo>  manageVo = architectureManageService.getManageByDepartment();
	    return architectureNodeService.getAllNodeTree(manageVo, "0",false,req);
	   }else {
		    return architectureNodeService.getNodeTree(architectureId, "0",false,req);
	   }
	}
	/**
	 * 得到一个节点的子级节点
	 * @param nodeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/child", method={RequestMethod.POST})
	public List<ArchitectureNodeVo> childNode(String nodeId){
		ArchitectureNodeVo nodeVo = architectureNodeService.getNodeById(nodeId);
		// 根据NodeCode 到总体系下查询他的子级
		// 第一：得到总体系的ID
		ArchitectureManageVo manageVo = architectureManageService.getParentestManage();
		Query query = new Query();
		String nodeCode = nodeVo.getNodeCode();
		nodeCode = (Integer.valueOf(nodeCode.substring(0, 1)) + 1) + nodeCode.substring(1, nodeCode.length());
		query.put("path", nodeCode);
		query.put("architectureId", manageVo.getArchitectureId());
		return architectureNodeService.listNodeByQuery(query);
	}
	
	/**
	 * 查询一条节点详情
	 * @param nodeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/detail", method={RequestMethod.POST})
	public Result nodeDetail(String nodeId){
		Result result = new Result(true);
		ArchitectureNodeVo node = architectureNodeService.getNodeById(nodeId);
		result.setData(node);
		return result;
	}
	/**
	 * 插入一个体系节点
	 * @param nodeDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/insert", method={RequestMethod.POST})
	public Result insertNode(A_Architecture_NodeDo nodeDo){
		Result result = new Result(false);
		if(nodeDo.getParentId().equals("0")){
			nodeDo.setPath(nodeDo.getNodeId());
		}else{
			ArchitectureNodeVo parentNode = architectureNodeService.getNodeById(nodeDo.getParentId());
			nodeDo.setPath(parentNode.getPath() + "," + nodeDo.getNodeId());
		}
		try {
			int row = architectureNodeService.insert(nodeDo);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除一个体系节点
	 * @param nodeDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/delete", method={RequestMethod.POST})
	public Result deleteNode(String nodeId){
		Result result = new Result(false);
		try {
			int row = architectureNodeService.deleteNodeById(nodeId);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新一个体系节点
	 * @param nodeDo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/update", method={RequestMethod.POST})
	public Result deleteNode(A_Architecture_NodeDo nodeDo){
		Result result = new Result(false);
		try {
			int row = architectureNodeService.update(nodeDo);
			if(row == 1){
				result.setResult(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 将题录和节点绑定
	 * @param nodeId
	 * @param standardIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/bind", method={RequestMethod.POST})
	public Result bindByStandardIds(String nodeId, String[] standardIds, String architectureId){
		Result result = new Result(true);
		try {
			architectureNodeService.bindByStandardIds(nodeId, standardIds, architectureId);
		} catch (Exception e) {
			result.setResult(false);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 将题录和节点解绑
	 * @param nodeId
	 * @param standardIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/unbind", method={RequestMethod.POST})
	public Result unbindByStandardIds(String nodeId, String[] standardIds, String architectureId){
		Result result = new Result(true);
		try {
			architectureNodeService.unbindByStandardIds(nodeId, standardIds);
		} catch (Exception e) {
			result.setResult(false);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 题录导出
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/export", method={RequestMethod.GET, RequestMethod.POST} )
	public Object export(String architectureId){
		Result result = new Result(false);
		// 第一步 得到导出的题录的集合
		Query query = new Query();
		query.put("architectureId", architectureId);
		List<ArchitectureNodeVo> architectureNodeList = architectureNodeService.listNodeByQuery(query);
		// 第二步 将集合传给Excel工具类。导出excel
		try {
			String filePath = POIUtil.exportArchitecture("xlsx", architectureNodeList);
			ArchitectureManageVo manageVo = architectureManageService.getManageById(architectureId);
			return super.downloadEcel(filePath, "体系-" + manageVo.getArchitectureCnName() + ".xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 递归插入标准咨询
	 * @param nodeList
	 */
	private void nodeImportInsert(List<A_Architecture_NodeDo> nodeList, A_Architecture_NodeDo parentNode,String nodeCode) {
		int row=-1;
		ArchitectureNodeVo atv=	architectureNodeService.getNodeById(parentNode.getNodeId());
		if(atv==null) {
			 row = architectureNodeService.insert(parentNode);
		}else {
			row =1 ;
		}
		if(row == 1){
			//for(A_Architecture_NodeDo node : nodeList){
			for (int i = 0; i < nodeList.size(); i++) {
				A_Architecture_NodeDo node =nodeList.get(i);
				if(node.getNodeCode().startsWith(nodeCode)){
					node.setNodeSort(i);
					node.setParentId(parentNode.getNodeId());
					node.setPath(parentNode.getPath() + "," + node.getNodeId());
					String no = node.getNodeCode();
					String n = no.replaceFirst(no.substring(0,1), String.valueOf(Integer.parseInt(no.substring(0,1))+1));
					nodeImportInsert(nodeList, node, n+".");
				}
			}
		}
	}
}
