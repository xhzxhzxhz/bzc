package com.folkestone.bzcx.service.dm;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.dm.L_LawsDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.mapper.dm.LawsMapper;
import com.folkestone.bzcx.mapper.dm.LawsNodeMapper;
import com.folkestone.bzcx.mapper.user.RDepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 法律法规的服务层
 * @author zqm
 *
 */
@Service
public class LawsService {
	@Autowired
	private  LawsMapper lawsMapper;
	@Autowired 
	private ArchitectureManageService architectureManageService;
	@Autowired
	private  ArchitectureNodeService architectureNodeService;
	@Autowired
	private RDepartmentMapper deparmapper;
	@Autowired
	private LawsNodeMapper lawsNodeMapper;

	public List<LawsVo> findAllLaws() {
		// TODO Auto-generated method stub
		return lawsMapper.findAllLaaws();
	}
	/**
	 * 条件查询
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listLawsByQuery(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		// TODO Auto-generated method stub
		List<LawsVo> list=lawsMapper.listLawsByQuery(query);
		PageInfo<LawsVo> pageInfo = new PageInfo<LawsVo>(list);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	public List<LawsVo> listStandardByQueryNoPage(Query query) {
		// TODO Auto-generated method stub
		return lawsMapper.listLawsByQuery(query);
	}
	/**
	 * 我们在高级查询中进行的二次查询
	 * @param paramMap
	 * @return
	 */
	public List<LawsVo> secondSearch(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return lawsMapper.secondSearch(paramMap);
	}
	public Map<String, Object> getContentName(Query query) {
		// TODO Auto-generated method stub
		return lawsMapper.getContentName(query);
	}
	public LawsVo findByLawsNoTrim(String noTrim) {
		// TODO Auto-generated method stub
		return lawsMapper.findByLawsNoTrim(noTrim);
	}
	/**
	 * 通过id去查询数据得到我们
	 * @param id
	 * @return
	 */
	public LawsVo findLawsById(String id) {
		// TODO Auto-generated method stub
		return lawsMapper.findLawsById(id);
	}
	/**
	 * 通过法律名称和法律文号去查询
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> findLawsListByQuery(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return lawsMapper.findLawsListByQuery(paramMap);
	}
	/**
	 * 查询法律题录中是否有这个题录
	 * @param lawsDo
	 * @return
	 */
	public boolean checkLawsNo(L_LawsDo lawsDo) {
		// TODO Auto-generated method stub
		Query query= new Query();
		query.put("lawsNo", lawsDo.getLawsNo());
		List<LawsVo> list=lawsMapper.listLawsByQuery(query);
		if(list.size()>=1 && ! list.get(0).getLawsId().equals(lawsDo)) {
			return true;
		}
		return false;
	}
	/**
	 * 添加一条题录
	 * @param lawsDo
	 * @return
	 */
	public  int insert(L_LawsDo lawsDo) {
		if(lawsDo.getLawsNo()!=null) {
		String trim=	StringUtil.trim(lawsDo.getLawsNo());
		lawsDo.setLawsNoTrim(trim);
		}
		int row = lawsMapper.insert(lawsDo);
		if(row == 1 && !StringUtil.isEmpty(lawsDo.getNodeCode())){
			// 如果体系代码不是空，则将题录和体系节点绑定
			//应先查询是否存在关系
			try {
				
				ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(lawsDo.getUploadUnit());
				architectureNodeService.bindByNodeCodeLaws(lawsDo.getNodeCode(), lawsDo.getLawsId(), manageVo.getArchitectureId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	/**
	 * 修改一条题录
	 * @param lawsDo
	 * @param user
	 * @return
	 */
	public int update(L_LawsDo lawsDo, UserVo user) {
		//通过法律的上传单位去和我们的数据库匹配找到数据
		//DepartmentVo departmentByLaws = deparmapper.getDepartmentByLawsid(lawsDo.getLawsId());
		//表示登录的用户和我们正在修改的法律法规题录是同一家公司的
		//if(departmentByLaws.getId().equals(user.getDepartmentId()) || user.getDepartmentParent().equals("0")) {
		// 删除此题录和当前体系下节点的关联关系，重新配置
			//得到当前体系
		//ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(user.getDepartmentId());
		//查询当前部门下 该题录--节点是否存在 然后删除
		//boolean re = delNodeCode(lawsDo.getLawsId(), manageVo.getArchitectureId());
		//Query query = new Query();
		//query.put("architectureId", manageVo.getArchitectureId());  //获取部门ID
		//query.put("nodeCode", lawsDo.getNodeCode());
	    //查询 节点是否存在
		//List<ArchitectureNodeVo> nodeList = architectureNodeService.listNodeByQuery(query);
		//if(!nodeList.isEmpty()) {
			//for (ArchitectureNodeVo architectureNodeVo : nodeList) {
			//query.clear();
			//query.put("nodeId", architectureNodeVo.getNodeId());
			//query.put("lawsId",lawsDo.getLawsId());
			//查询之前是否存在  体系节点-题录是否存在
			//List<LawsNodeVo> li=lawsNodeMapper.listByList(query);
			//不存在
			//if(li.isEmpty()) {
				 //插入
				//A_Laws_NodeDo lawsNodeDo = new A_Laws_NodeDo();
				//lawsNodeDo.setNodeId(architectureNodeVo.getNodeId());
				//lawsNodeDo.setLawsId(lawsDo.getLawsId());
				//lawsNodeDo.setCreateDate(new Date());
				//lawsNodeMapper.insert(lawsNodeDo);
			//}
		   
		//}
		//}
//		// 重新配置新的关联关系
//	architectureNodeService.bindByNodeCode(standardDo.getNodeCode(), standardDo.getStandardId(), manageVo.getArchitectureId());
		//修改题录
		if(lawsDo!=null) {
			return lawsMapper.update(lawsDo);
		}
		
		
		return -1;
	}
	//删除本单位体系节点与标准的绑定
	private boolean delNodeCode(String lawsId, String architectureId) {
		int li=-1;
		boolean re=false;
		Query query = new Query();
		query.put("architectureId", architectureId);  //获取部门ID
		query.put("standardId", lawsId);
		 li =lawsNodeMapper.delNodeCode(query);
		if(li>1) {
			re=true;
		}
		return re;
	}
	/**
	 * 通过id去查询法律题录
	 * @param lawsId
	 * @return
	 */
	public  LawsVo getStandardById(String lawsId) {
		// TODO Auto-generated method stub
		return  lawsMapper.findLawsById(lawsId);
	}
	public int deleteById(String lawsId) {
		// TODO Auto-generated method stub
		return lawsMapper.deleteById(lawsId);
	}
	/**
	 * 通过法律法规的文号去查询当前的法律题录
	 * @param lawsNoTrim
	 * @return
	 */
	public LawsVo getStandardByNoTrim(String lawsNoTrim) {
		// TODO Auto-generated method stub
		return lawsMapper.findLawsByNoTrim(lawsNoTrim);
	}

	/**
	 * 通过我们的法律题录id，和法律体系id去确定我们的节点编码，应该是去我们的法律节点表中去查询,
	 * @param lawsId
	 * @param architectureId
	 * @return
	 */
	public String getNodeCode(String lawsId, String architectureId) {
		// TODO Auto-generated method stub
		return lawsNodeMapper.getNodeCode(lawsId);
	}
	/**
	 * 
	 * @param stdFileId
	 * @return
	 */
	public LawsVo findLawsByStdId(String stdFileId) {
		// TODO Auto-generated method stub
		return lawsMapper.findLawsByStdFileId(stdFileId);
	}
	/**
	 * 分类查询
	 * 
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listLawsByIcsCode(Query query) {
		PageHelper.startPage(query.getPage(), query.getRows());
		// TODO Auto-generated method stub
		List<LawsVo> list=lawsMapper.listLawsByIcsCode(query);
		PageInfo<LawsVo> pageInfo = new PageInfo<LawsVo>(list);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	
}
