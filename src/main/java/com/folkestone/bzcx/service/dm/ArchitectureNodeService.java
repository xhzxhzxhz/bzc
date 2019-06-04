package com.folkestone.bzcx.service.dm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.dm.A_Architecture_NodeDo;
import com.folkestone.bzcx.bean.bean_do.dm.A_Laws_NodeDo;
import com.folkestone.bzcx.bean.bean_do.dm.A_Standard_NodeDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUITree;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureNodeVo;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsNodeVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardNodeVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.common.util.JurisdictionUtil;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.mapper.dm.AArchitectureNodeMapper;
import com.folkestone.bzcx.mapper.dm.AStandardNodeMapper;
import com.folkestone.bzcx.mapper.dm.LawsNodeMapper;
import com.folkestone.bzcx.mapper.dm.SStandardMapper;

/**
 * Describe：体系节点Service
 * 
 * @author smallking
 *
 *  2017年10月30日
 */
@Service
public class ArchitectureNodeService {
	@Autowired
	private ArchitectureManageService architectureManageService;
	@Autowired
	private AArchitectureNodeMapper architectureNodeMapper;
	@Autowired
	private AStandardNodeMapper standardNodeMapper;
	@Autowired
	private SStandardMapper standemapper;
	@Autowired
	private LawsNodeMapper lawsNodeMapper;
	
	/**
	 * 插入一个体系节点
	 * @param nodeDo
	 * @return
	 */
	public int insert(A_Architecture_NodeDo nodeDo) {
		return architectureNodeMapper.insert(nodeDo);
	}

	/**
	 * 更新一个体系节点
	 * @param nodeDo
	 * @return
	 */
	public int update(A_Architecture_NodeDo nodeDo) {
		return architectureNodeMapper.update(nodeDo);
	}

	/**
	 * 根据Query 查询多条节点
	 * @param query
	 * @return
	 */
	public List<ArchitectureNodeVo> listNodeByQuery(Query query) {
		
		return architectureNodeMapper.listNodeByQuery(query);
	}

	/**
	 * 根据Query 删除体系节点
	 * @param query
	 * @return
	 */
	public int deleteNodeByQuery(Query query) {
		return architectureNodeMapper.deleteNodeByQuery(query);
	}

	/**
	 * 根据主键ID删除体系节点
	 * @param nodeId
	 * @return
	 */
	public int deleteNodeById(String nodeId) {
		// 先删除该体系和题录的绑定关系
		Query query = new Query();
		query.put("nodeId", nodeId);
		standardNodeMapper.deleteByQuery(query);
		return architectureNodeMapper.deleteNodeById(nodeId);
	}

	/**
	 * 根据主键查询一个节点
	 * @param nodeId
	 * @return
	 */
	public ArchitectureNodeVo getNodeById(String nodeId) {
		ArchitectureNodeVo node = architectureNodeMapper.getNodeById(nodeId);
		if(node!=null) {
			ArchitectureNodeVo parentNode = architectureNodeMapper.getNodeById(node.getParentId());
			node.setParentNode(parentNode);
		}
		return node;
	}

	/**
	 * 返回指定的体系和体系节点下的属性结构
	 * @param architectureId 体系ID
	 * @param parentId 体系父级ID
	 * @return
	 */
	public List<EasyUITree> getNodeTree(String architectureId, String parentId,Boolean bo,HttpServletRequest req) {
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		query.put("architectureId", architectureId);
		query.put("parentId", parentId);
		List<ArchitectureNodeVo> nodeList = architectureNodeMapper.listNodeByQuery(query);
		for(ArchitectureNodeVo nodeVo : nodeList){
			EasyUITree tree = new EasyUITree();
			tree.setId(nodeVo.getNodeId());
			tree.setText(nodeVo.getNodeCnName()+ "(" + nodeVo.getNodeCode() + ")");
			query.put("parentId", nodeVo.getNodeId());
			jionChild(query, tree,bo,req);
			treeList.add(tree);
		}
		// 其实到这个地方，树已经拼好了， 下面只是检查，当拥有多个最父级节点的时候，
		// 这些父级节点是不是在一个节点下
		ArchitectureNodeVo nodeVo = architectureNodeMapper.getNodeById(parentId);
		if(nodeVo != null){
			EasyUITree tree = new EasyUITree();
			tree.setId(nodeVo.getNodeId());
			tree.setText(nodeVo.getNodeCnName() + "(" + nodeVo.getNodeCode() + ")");
			tree.setChildren(treeList);
			List<EasyUITree> list = new ArrayList<EasyUITree>();
			list.add(tree);
			return list;
		}
		return treeList;
	}
	/**
	 * 前台返回指定的体系和体系节点下的属性结构（无节点）
	 * @param architectureId 体系ID
	 * @param parentId 体系父级ID
	 * @return
	 */
	public List<EasyUITree> getfaNodeTree(String architectureId, String parentId,Boolean bo,HttpServletRequest req) {
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		Query query = new Query();
		query = JurisdictionUtil.searchJurisdiction(req, query);
		query.put("architectureId", architectureId);
		query.put("parentId", parentId);
		List<ArchitectureNodeVo> nodeList = architectureNodeMapper.listNodeByQuery(query);
		for(ArchitectureNodeVo nodeVo : nodeList){
			EasyUITree tree = new EasyUITree();
			tree.setId(nodeVo.getNodeId());
			tree.setText(nodeVo.getNodeCnName()+"");
			query.put("parentId", nodeVo.getNodeId());
			jionfaChild(query, tree,bo,req);
			treeList.add(tree);
		}
		// 其实到这个地方，树已经拼好了， 下面只是检查，当拥有多个最父级节点的时候，
		// 这些父级节点是不是在一个节点下
		ArchitectureNodeVo nodeVo = architectureNodeMapper.getNodeById(parentId);
		if(nodeVo != null){
			EasyUITree tree = new EasyUITree();
			tree.setId(nodeVo.getNodeId());
			tree.setText(nodeVo.getNodeCnName() + "");
			tree.setChildren(treeList);
			List<EasyUITree> list = new ArrayList<EasyUITree>();
			list.add(tree);
			return list;
		}
		return treeList;
	}
	/**
	 * 显示所有体系下对应的
	 * @param standardId
	 */
	public List<EasyUITree> getAllNodeTree(List<ArchitectureManageVo> manageVo, String parentId, boolean bo,
			HttpServletRequest req) {
		List<EasyUITree> treeList = new ArrayList<EasyUITree>();
		for (int i = 0; i < manageVo.size(); i++) {
			Query query = new Query();
			query = JurisdictionUtil.searchJurisdiction(req, query);
			query.put("architectureId", manageVo.get(i).getArchitectureId());
			query.put("parentId", parentId);
			List<ArchitectureNodeVo> nodeList = architectureNodeMapper.listNodeByQuery(query);
			for(ArchitectureNodeVo nodeVo : nodeList){
				EasyUITree tree = new EasyUITree();
				tree.setId(nodeVo.getNodeId());
				tree.setText(nodeVo.getNodeCnName() + "(" + nodeVo.getNodeCode() + ")");
				query.put("parentId", nodeVo.getNodeId());
				jionChild(query, tree,bo,req);
				treeList.add(tree);
			}
			// 其实到这个地方，树已经拼好了， 下面只是检查，当拥有多个最父级节点的时候，
			// 这些父级节点是不是在一个节点下
			ArchitectureNodeVo nodeVo = architectureNodeMapper.getNodeById(parentId);
			if(nodeVo != null){
				EasyUITree tree = new EasyUITree();
				tree.setId(nodeVo.getNodeId());
				tree.setText(nodeVo.getNodeCnName() + "(" + nodeVo.getNodeCode() + ")");
				tree.setChildren(treeList);
				List<EasyUITree> list = new ArrayList<EasyUITree>();
				list.add(tree);
				return list;
			}
		}
		return treeList;
	}
	
	
	/**
	 * 循环拼接树形结构(无节点)
	 * @param query
	 * @param tree
	 * @param nodeList
	 */
	private void jionfaChild(Query query, EasyUITree tree,Boolean bo,HttpServletRequest req){
		List<ArchitectureNodeVo> nodeList = architectureNodeMapper.listNodeByQuery(query);
		List<EasyUITree> childList = new ArrayList<EasyUITree>();
		tree.setChildren(childList);
		if(nodeList.size() != 0){
			for(ArchitectureNodeVo nodeVo : nodeList){
				EasyUITree childTree = new EasyUITree();
				childTree.setId(nodeVo.getNodeId());
				childTree.setText(nodeVo.getNodeCnName()+ "");
				childList.add(childTree);
				query.put("parentId", nodeVo.getNodeId());
				jionfaChild(query, childTree,bo,req);
			}
		}else {
			//如果为0的话就去查下面的标准,如果需要查的话
			if(bo) {
				String object = (String) query.get("parentId");
				Map<String, Object> parma = new HashMap<>();
				//查询权限
				parma = JurisdictionUtil.searchJurisdiction(req,parma);
					Query qu = new Query(parma);
					qu.put("nodeId", object);
					List<StandardVo> selectBnodeId = standemapper.selectBnodeId(qu);
					for (StandardVo standardVo : selectBnodeId) {
						EasyUITree childTree = new EasyUITree();
						childTree.setId(standardVo.getStandardId());
						childTree.setText(standardVo.getStandardNo());
						childTree.setChecked(true);
						childList.add(childTree);
					}
					qu.clear();
			}
		}
	}
	
	
	/**
	 * 循环拼接树形结构
	 * @param query
	 * @param tree
	 * @param nodeList
	 */
	private void jionChild(Query query, EasyUITree tree,Boolean bo,HttpServletRequest req){
		List<ArchitectureNodeVo> nodeList = architectureNodeMapper.listNodeByQuery(query);
		List<EasyUITree> childList = new ArrayList<EasyUITree>();
		tree.setChildren(childList);
		if(nodeList.size() != 0){
			for(ArchitectureNodeVo nodeVo : nodeList){
				EasyUITree childTree = new EasyUITree();
				childTree.setId(nodeVo.getNodeId());
				childTree.setText(nodeVo.getNodeCnName()+ "(" + nodeVo.getNodeCode() + ")");
				childList.add(childTree);
				query.put("parentId", nodeVo.getNodeId());
				jionChild(query, childTree,bo,req);
			}
		}else {
			//如果为0的话就去查下面的标准,如果需要查的话
			if(bo) {
				String object = (String) query.get("parentId");
				Map<String, Object> parma = new HashMap<>();
				//查询权限
				parma = JurisdictionUtil.searchJurisdiction(req,parma);
					Query qu = new Query(parma);
					qu.put("nodeId", object);
					List<StandardVo> selectBnodeId = standemapper.selectBnodeId(qu);
					for (StandardVo standardVo : selectBnodeId) {
						EasyUITree childTree = new EasyUITree();
						childTree.setId(standardVo.getStandardId());
						childTree.setText(standardVo.getStandardNo());
						childTree.setChecked(true);
						childList.add(childTree);
					}
					qu.clear();
			}
		}
	}

	/**
	 * 将标准题录和体系节点绑定（根据nodeCode绑定）
	 * @param nodeCode
	 * @param standardId
	 */
	public void bindByNodeCode(String nodeCode, String standardId, String architectureId) {
		// 先查出节点代码和nodeCode相匹配的节点
		Query query = new Query();
		query.put("nodeCode", nodeCode);
		query.put("architectureId", architectureId);
		List<ArchitectureNodeVo> nodeVoList = architectureNodeMapper.listNodeByQuery(query);
		for(ArchitectureNodeVo nodeVo : nodeVoList){
			//查询这个关系是否存在
			Query query1 = new Query();
			query1.put("nodeId", nodeVo.getNodeId());
			query1.put("standardId", standardId);
			List<StandardNodeVo> li = standardNodeMapper.listByList(query1);
			if(li.isEmpty()) {
				A_Standard_NodeDo standardNodeDo = new A_Standard_NodeDo();
				standardNodeDo.setNodeId(nodeVo.getNodeId());
				standardNodeDo.setStandardId(standardId);
				standardNodeDo.setCreateDate(new Date());
				standardNodeMapper.insert(standardNodeDo);
			}
				
		}
	}
	
	/**
	 * 解绑：将节点和题录的关联关系删除。
	 * @param nodeId ：节点ID（为空的时候，不列入删除条件） 
	 * @param standardId : 标准ID(为空的时候，不列入删除条件)
	 */
	public void unbind(String nodeId, String standardId) {
		if(StringUtil.isEmpty(nodeId) && StringUtil.isEmpty(standardId)){
			return;
		}
		Query query = new Query();
		query.put("nodeId", nodeId);
		query.put("standardId", standardId);
		standardNodeMapper.deleteByQuery(query);
	}

	/**
	 * 将节点和题录绑定(根据ID绑定)
	 * @param nodeId
	 * @param standardIds
	 * @param architectureId 
	 * @return
	 */
	public int bindByStandardIds(String nodeId, String[] standardIds, String architectureId) {
		for(String standardId : standardIds){
			Query query = new Query();
			query.put("architectureId", architectureId);
			query.put("standardId", standardId);
			// 先查询之前是否有过绑定关系了
			List<StandardNodeVo> standardNodeList = standardNodeMapper.listByQuery(query);
			if(standardNodeList.size() >= 1){
				// 有的话 直接跳过
				continue;
			}
			A_Standard_NodeDo standardNodeDo = new A_Standard_NodeDo();
			standardNodeDo.setCreateDate(new Date());
			standardNodeDo.setNodeId(nodeId);
			standardNodeDo.setStandardId(standardId);
			standardNodeMapper.insert(standardNodeDo);
		}
		return 0;
	}

	/**
	 * 解除绑定
	 * @param nodeId
	 * @param standardIds
	 */
	public int unbindByStandardIds(String nodeId, String[] standardIds) {
		Query query = new Query();
		query.put("nodeId", nodeId);
		for(String standarId : standardIds){
			query.put("standardId", standarId);
			standardNodeMapper.deleteByQuery(query);
		}
		return 0;
	}

	/**
	 * 根据standardId 查询其在某个体系下对应的nodeCode
	 * @param standardId
	 */
	public String getNodeCode(String standardId, String architectureId) {
		Query query = new Query();
		query.put("standardId", standardId);
		query.put("architectureId", architectureId);
		List<StandardNodeVo> list = standardNodeMapper.listByQuery(query);
		if(list.size() == 0){
			return "";
		}
		return list.get(0).getNodeCode();
	}
	
	/**
	 * 检查当前部门的体系下 是否有某个标准
	 * @param departmentId
	 * @param standardId
	 * @return
	 */
	public boolean checkStandardByDepartment(String departmentId, String standardId) {
		Query query = new Query();
		query.put("departmentId", departmentId);
		query.put("standardId", standardId);
		List<StandardNodeVo> list = standardNodeMapper.listByQuery(query);
		if(list != null && list.size() >= 1){
			return true;
		}
		return false;
	}
	/**
	 * 检查某个标准是不是在一个体系下
	 * @return
	 */
	public boolean checkStandardByArch(String standardId){
		Query query = new Query();
		query.put("standardId", standardId);
		List<StandardNodeVo> list = standardNodeMapper.listByQuery(query);
		if(list != null && list.size() >= 1){
			return true;
		}
		return false;
	}
	/**
	 * 查询某个标准在昆仑体系下对应的节点
	 * @return
	 */
	public String getNodeByStandardId(String standardId){
		ArchitectureManageVo manageVo = architectureManageService.getParentestManage();
		Query query = new Query();
		query.put("standardId", standardId);
		query.put("architectureId", manageVo.getArchitectureId());
		List<StandardNodeVo> list = standardNodeMapper.listByQuery(query);
		if(list.size() >= 1){
			return list.get(0).getNodeName();
		}
		return "";
	}

	public List<String> listArchCount(List<StandardVo> li){
		return standardNodeMapper.listArchCount(li);
	}
	/**
	 * 将法律法规题录和体系节点绑定（根据nodeCode绑定）
	 * @param nodeCode
	 * @param standardId
	 */
	public void bindByNodeCodeLaws(String nodeCode, String standardId, String architectureId) {
		// 先查出节点代码和nodeCode相匹配的节点
		Query query = new Query();
		query.put("nodeCode", nodeCode);
		query.put("architectureId", architectureId);
		List<ArchitectureNodeVo> nodeVoList = architectureNodeMapper.listNodeByQuery(query);
		for(ArchitectureNodeVo nodeVo : nodeVoList){
			//查询这个关系是否存在
			Query query1 = new Query();
			query1.put("nodeId", nodeVo.getNodeId());
			query1.put("lawsId", standardId);
			List<LawsNodeVo> li = lawsNodeMapper.listByList(query1);
			if(li.isEmpty()) {
				A_Laws_NodeDo lawsNodeDo = new A_Laws_NodeDo();
				lawsNodeDo.setNodeId(nodeVo.getNodeId());
				lawsNodeDo.setLawsId(standardId);
				lawsNodeDo.setCreateDate(new Date());
				lawsNodeMapper.insert(lawsNodeDo);
			}
				
		}
	}
}
