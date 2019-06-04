package com.folkestone.bzcx.service.dm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.folkestone.bzcx.bean.bean_do.dm.A_Standard_NodeDo;
import com.folkestone.bzcx.bean.bean_do.dm.S_StandardDo;
import com.folkestone.bzcx.bean.bean_dto.arch.StandardStatistic;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureManageVo;
import com.folkestone.bzcx.bean.bean_vo.dm.ArchitectureNodeVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardNodeVo;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.bean.bean_vo.user.DepartmentVo;
import com.folkestone.bzcx.bean.bean_vo.user.UserVo;
import com.folkestone.bzcx.common.util.ContantFinalUtil;
import com.folkestone.bzcx.common.util.StringUtil;
import com.folkestone.bzcx.mapper.dm.AStandardNodeMapper;
import com.folkestone.bzcx.mapper.dm.SStandardMapper;
import com.folkestone.bzcx.mapper.user.RDepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Describe：处理题录的Service
 * 
 * @author smallking
 *
 *  2017年11月1日
 */
@Service
public class StandardService {
	
	@Autowired
	private SStandardMapper standardMapper;
	@Autowired
	private  ArchitectureNodeService architectureNodeService;
	@Autowired
	private  ArchitectureManageService architectureManageService;
	@Autowired
	private AStandardNodeMapper standardNodeMapper;
	@Autowired
	private RDepartmentMapper deparmapper;
	
	private static String fileURl = ContantFinalUtil.BASE_PATH;
	
	private static String ftlname = ContantFinalUtil.STATIC_STANDARDNAME;
	
	public List<String> listPinyin(){
		return standardMapper.listPinyin();
	}
	
	/**
	 * 题录静态化生成
	 * @param fileURL
	 * @param htmlurl
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void state (S_StandardDo stand,String htmlurl) throws IOException, TemplateException {
		//1.创建配置类

				Configuration configuration=new Configuration(Configuration.getVersion());

				//2.设置模板所在的目录

				configuration.setDirectoryForTemplateLoading(new File(fileURl));

				//3.设置字符集

				configuration.setDefaultEncoding("utf-8");

				//4.加载模板"test.ftl"

				Template template = configuration.getTemplate(ftlname);

				//5.创建数据模型

				Map map=new HashMap();
				//标准号
				/*map.put("name", "aa");
				map.put("message", "assss");
				map.put("StandardNo",stand.getStandardNo());*/
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(stand.getStandardId() == null) {
					map.put("standardId","无");
				}else {
					map.put("standardId",stand.getStandardId());
				}
				if(stand.getStandardNo() == null) {
					map.put("StandardNo","无");
				}else {
					map.put("StandardNo",stand.getStandardNo());
				}
				//标准名称
				if(stand.getStandardCnName() == null) {
					map.put("StandardCnName","无");
				}else {
					map.put("StandardCnName",stand.getStandardCnName());
				}
				//起草单位
				if(stand.getDraftUnit() == null) {
					map.put("DraftUnit","无");
				}else {
					map.put("DraftUnit",stand.getDraftUnit());
				}
				//发布单位
				if(stand.getPubOrg() == null) {
					map.put("pubOrg","无");
				}else {
					map.put("pubOrg",stand.getPubOrg());
				}
				//发布日期
				if(stand.getPublishDate() == null) {
					map.put("publishDate","无");
				}else {
					map.put("publishDate",sdf.format(stand.getPublishDate()));
				}
				//实施日期
				if(stand.getImplementationDate() == null) {
					map.put("implementationDate","无");
				}else {
					map.put("implementationDate",sdf.format(stand.getImplementationDate()));
				}
				//归口单位
				if(stand.getBelongToUnit() == null) {
					map.put("belongToUnit","无");
				}else {
					map.put("belongToUnit",stand.getBelongToUnit());
				}
				//起草人
				if(stand.getDraftStaff() == null) {
					map.put("draftStaff","无");
				}else {
					map.put("draftStaff",stand.getDraftStaff());
				}
				//引用标准
				if(stand.getQuoteStandard() == null) {
					map.put("quoteStandard","无");
				}else {
					map.put("quoteStandard",stand.getQuoteStandard());
				}
				//代替标准
				if(stand.getReplaceStandard() == null) {
					map.put("replaceStandard","无");
				}else {
					map.put("replaceStandard",stand.getReplaceStandard());
				}
				//技术领域/适用范围
				if(stand.getTechnicalField() == null) {
					map.put("technicalField","无");
				}else {
					map.put("technicalField",stand.getTechnicalField());
				}
				//原文链接
				map.put("originaltext","aaa");
				
				
				//6.创建Writer对象
				
				Writer out =new FileWriter(new File(htmlurl));
				
				//7.输出

				template.process(map,out);

				//8.关闭Writer对象

				out.close();
	}
	
	
	/**
	 * 题录静态化生成
	 * @param fileURL
	 * @param htmlurl
	 * @throws IOException
	 * @throws TemplateException
	 */
	public static void state (StandardVo stand,String htmlurl) throws IOException, TemplateException {
		//1.创建配置类

				Configuration configuration=new Configuration(Configuration.getVersion());

				//2.设置模板所在的目录

				configuration.setDirectoryForTemplateLoading(new File(fileURl));

				//3.设置字符集

				configuration.setDefaultEncoding("utf-8");

				//4.加载模板"test.ftl"

				Template template = configuration.getTemplate(ftlname);

				//5.创建数据模型

				Map map=new HashMap();
				//标准号
				/*map.put("name", "aa");
				map.put("message", "assss");
				map.put("StandardNo",stand.getStandardNo());*/
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if(stand.getStandardId() == null) {
					map.put("standardId","无");
				}else {
					map.put("standardId",stand.getStandardId());
				}
				if(stand.getStandardNo() == null) {
					map.put("StandardNo","无");
				}else {
					map.put("StandardNo",stand.getStandardNo());
				}
				//标准名称
				if(stand.getStandardCnName() == null) {
					map.put("StandardCnName","无");
				}else {
					map.put("StandardCnName",stand.getStandardCnName());
				}
				//起草单位
				if(stand.getDraftUnit() == null) {
					map.put("DraftUnit","无");
				}else {
					map.put("DraftUnit",stand.getDraftUnit());
				}
				//发布单位
				if(stand.getPubOrg() == null) {
					map.put("pubOrg","无");
				}else {
					map.put("pubOrg",stand.getPubOrg());
				}
				//发布日期
				if(stand.getPublishDate() == null) {
					map.put("publishDate","无");
				}else {
					map.put("publishDate","无");
				}
				//实施日期
				if(stand.getImplementationDate() == null) {
					map.put("implementationDate","无");
				}else {
					map.put("implementationDate","无");
				}
				//归口单位
				if(stand.getBelongToUnit() == null) {
					map.put("belongToUnit","无");
				}else {
					map.put("belongToUnit",stand.getBelongToUnit());
				}
				//起草人
				if(stand.getDraftStaff() == null) {
					map.put("draftStaff","无");
				}else {
					map.put("draftStaff",stand.getDraftStaff());
				}
				//引用标准
				if(stand.getQuoteStandard() == null) {
					map.put("quoteStandard","无");
				}else {
					map.put("quoteStandard",stand.getQuoteStandard());
				}
				//代替标准
				if(stand.getReplaceStandard() == null) {
					map.put("replaceStandard","无");
				}else {
					map.put("replaceStandard",stand.getReplaceStandard());
				}
				//技术领域/适用范围
				if(stand.getTechnicalField() == null) {
					map.put("technicalField","无");
				}else {
					map.put("technicalField",stand.getTechnicalField());
				}
				//原文链接
				map.put("originaltext","aaa");
				
				
				//6.创建Writer对象
				
				Writer out =new FileWriter(new File(htmlurl));
				
				//7.输出

				template.process(map,out);

				//8.关闭Writer对象

				out.close();
	}
	
	
	/**
	 * 录入一个标准题录
	 * @param standardDo
	 * @return
	 */
	public int insert(S_StandardDo standardDo) {
		int row = standardMapper.insert(standardDo);
		if(row == 1 && !StringUtil.isEmpty(standardDo.getNodeCode())){
			// 如果体系代码不是空，则将题录和体系节点绑定
			//应先查询是否存在关系
			try {
				ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(standardDo.getUploadUnit());
				architectureNodeService.bindByNodeCode(standardDo.getNodeCode(), standardDo.getStandardId(), manageVo.getArchitectureId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	/**
	 * 根据Query条件查询多条题录
	 * @param query
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public EasyUISeparatPage listStandardByQuery(Query query) throws JsonParseException, JsonMappingException, IOException {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<StandardVo> standardVoList = standardMapper.listStandardByQuery(query);
		// 得到结果
		PageInfo<StandardVo> pageInfo = new PageInfo<StandardVo>(standardVoList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	/**
	 * 二次检索
	 * @param query
	 * @return 
	 */
	public EasyUISeparatPage listStandardBySecondQuery(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<StandardVo> standardVoList = standardMapper.listStandardBySecondQuery(query);
		// 得到结果
		PageInfo<StandardVo> pageInfo = new PageInfo<StandardVo>(standardVoList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	/**
	 * 根据主键ID删除题录
	 * @param standardId
	 * @return
	 */
	public int deleteById(String standardId) {
		int row = standardMapper.deleteById(standardId);
		if(row == 1){
			// 删除成功之后再删除其与节点的绑定关系
			try{
				architectureNodeService.unbind(null, standardId);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	/**
	 * 根据ID查询
	 * @param standardId
	 * @return
	 */
	public StandardVo getStandardById(String standardId) {
		return standardMapper.getStandardById(standardId);
	}
	/**
	 * 修改标准题录
	 * @param standardDo
	 * @param nodeCode 
	 * @return
	 */
	public int update(S_StandardDo standardDo) {
		// 删除此题录和当前体系下节点的关联关系，重新配置
		ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(standardDo.getUploadUnit());
		Query query = new Query();
		query.put("architectureId", manageVo.getArchitectureId());  //获取部门ID
		//String nodeCode = getNodeCode(standardDo.getStandardId(), manageVo.getArchitectureId());
		query.put("nodeCode", standardDo.getNodeCode());
	    //查询 节点是否存在
		List<ArchitectureNodeVo> nodeList = architectureNodeService.listNodeByQuery(query);
		if(!nodeList.isEmpty()) {
			for (ArchitectureNodeVo architectureNodeVo : nodeList) {
			query.clear();
			query.put("nodeId", architectureNodeVo.getNodeId());
			query.put("standardId",standardDo.getStandardId());
			//查询之前是否存在  体系节点-题录是否存在
			List<StandardNodeVo> li=standardNodeMapper.listByList(query);
			//不存在
			if(li.isEmpty()) {
				 //插入
				A_Standard_NodeDo standardNodeDo = new A_Standard_NodeDo();
				standardNodeDo.setNodeId(architectureNodeVo.getNodeId());
				standardNodeDo.setStandardId(standardDo.getStandardId());
				standardNodeDo.setCreateDate(new Date());
				standardNodeMapper.insert(standardNodeDo);
			}
		   
		}
		}
//		// 重新配置新的关联关系
//		architectureNodeService.bindByNodeCode(standardDo.getNodeCode(), standardDo.getStandardId(), manageVo.getArchitectureId());
		return standardMapper.update(standardDo);
	}
	
	
	/**
	 * 修改标准题录，权限配置
	 * @param standardDo
	 * @param nodeCode 
	 * @return
	 */
	public int update(S_StandardDo standardDo,UserVo user) {
		DepartmentVo departmentByStandard = deparmapper.getDepartmentByStandard(standardDo.getStandardId());
		if(departmentByStandard.getId().equals(user.getDepartmentId()) || user.getDepartmentParent().equals("0")) {
		// 删除此题录和当前体系下节点的关联关系，重新配置
		ArchitectureManageVo manageVo = architectureManageService.getManageByDepartment(user.getDepartmentId());
		//查询当前部门下 该题录--节点是否存在 然后删除
		boolean re = delNodeCode(standardDo.getStandardId(), manageVo.getArchitectureId());
		Query query = new Query();
		query.put("architectureId", manageVo.getArchitectureId());  //获取部门ID
		query.put("nodeCode", standardDo.getNodeCode());
	    //查询 节点是否存在
		List<ArchitectureNodeVo> nodeList = architectureNodeService.listNodeByQuery(query);
		if(!nodeList.isEmpty()) {
			for (ArchitectureNodeVo architectureNodeVo : nodeList) {
			query.clear();
			query.put("nodeId", architectureNodeVo.getNodeId());
			query.put("standardId",user.getDepartmentId());
			//查询之前是否存在  体系节点-题录是否存在
			List<StandardNodeVo> li=standardNodeMapper.listByList(query);
			//不存在
			if(li.isEmpty()) {
				 //插入
				A_Standard_NodeDo standardNodeDo = new A_Standard_NodeDo();
				standardNodeDo.setNodeId(architectureNodeVo.getNodeId());
				standardNodeDo.setStandardId(standardDo.getStandardId());
				standardNodeDo.setCreateDate(new Date());
				standardNodeMapper.insert(standardNodeDo);
			}
		   
		}
		}
//		// 重新配置新的关联关系
//		architectureNodeService.bindByNodeCode(standardDo.getNodeCode(), standardDo.getStandardId(), manageVo.getArchitectureId());
		return standardMapper.update(standardDo);
		}
		return -1;
	}
	//删除本单位体系节点与标准的绑定
	private boolean delNodeCode(String standardId, String architectureId) {
		int li=-1;
		boolean re=false;
		Query query = new Query();
		query.put("architectureId", architectureId);  //获取部门ID
		query.put("standardId", standardId);
		 li =standardNodeMapper.delNodeCode(query);
		if(li>1) {
			re=true;
		}
		return re;
	}
	/**
	 * 检查当前标准号是否存在
	 * @param standardNo
	 * @return
	 */
	public boolean checkStandarNo(S_StandardDo standardDo) {
		// 先检查当前录入的题录是否已经存在
		Query query = new Query();
		query.put("standardNo", standardDo.getStandardNo());
		List<StandardVo> standardVoList = standardMapper.listStandardByQuery(query);
		if(standardVoList.size() >= 1 && !standardVoList.get(0).getStandardId().equals(standardDo.getStandardId())){
			return true;
		}
		return false;
	}
	/**
	 * 查询所给nodeId下的所有标准 
	 * 查询所有标准题录 
	 * query中的参数 nodeId
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage listStandardByNodeId(Query query) {
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<StandardVo> standardVoList = standardMapper.listStandardByNodeId(query);
		// 得到结果
		PageInfo<StandardVo> pageInfo = new PageInfo<StandardVo>(standardVoList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	
	/**
	 * 根据所给的标准ID，和体系Id 得到标准的nodeCode
	 * @param standardId
	 * @param architectureId
	 * @return
	 */
	public String getNodeCode(String standardId, String architectureId){
		String nodeCode = architectureNodeService.getNodeCode(standardId, architectureId);
		return nodeCode;
	}
	/**
	 * 根据标准号来查询题录
	 * @param standardNo
	 * @return
	 */
	public StandardVo getStandardByNo(String standardNo) {
		return standardMapper.getStandardByNo(standardNo);
	}
	/**
	 * 根据制空标准号来查询题录
	 * @param standardNo
	 * @return
	 */
	public StandardVo getStandardByNoTrim(String noTrim) {
		return standardMapper.getStandardByNoTrim(noTrim);
	}
	/**
	 * 得到全库的所有标准
	 * @return
	 */
	public List<StandardVo> listAllStandard() {
		Query query = new Query();
		query.put("isback", "标记");
		
		return standardMapper.listStandardByQuery(query);
	}
	/**
	 * 标准统计,统计所给List中，
	 * 国标，企标，行标每种标准各有多少个
	 * @param list 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public StandardStatistic statistics(List<?> dataList,Boolean haveArchCount){
		StandardStatistic statistic = new StandardStatistic();
		// 全库的标准数量信息
		statistic.setTotalCount(dataList.size());
		//先看看需不需要统计标准体系数量
		// 先看看当前标准是不是一条体系标准
		if(haveArchCount) {
			Integer number = 0;
			//先算出，大于900的要循环多少次,mysql只能装1000条
			if(dataList.size()%900 != 0) {
				number = dataList.size()/900 + 1;
			}
			//用的数据库的list，最大数量只有1000，所以现在不知道总数，用递归加一下
			Integer ArchCountNum = 0;
			//多于900条的就分开查，类似于分页
			for (int i = 0; i < number; i++) {
				List<StandardVo> subList;
				if(i == (number-1)) {
					subList = (List<StandardVo>) dataList.subList(900*i, dataList.size());
				}else {
					subList = (List<StandardVo>)dataList.subList(900*i, 900+900*i);
				}
				List<String> listArchCount = architectureNodeService.listArchCount(subList);
				ArchCountNum += listArchCount.size();
			}
			statistic.setArchCount(ArchCountNum);
		}
		for(Object obj : dataList){
			StandardVo standardVo = (StandardVo)obj;
			/*if(haveArchCount) {
				boolean flag = architectureNodeService.checkStandardByArch(standardVo.getStandardId());		
				if(flag){
					statistic.setArchCount(statistic.getArchCount() + 1);
				}
			}*/
			String standardType = standardVo.getStandardType() + "";
			if(StringUtil.isEmpty(standardType)){
				statistic.setOtherCount(statistic.getOtherCount() + 1);
				continue;
			}
			if(standardType.equals("G")){
				statistic.setGbCount(statistic.getGbCount() + 1);
			}
			if(standardType.equals("Q")){
				statistic.setQbCount(statistic.getQbCount() + 1);
			}
			if(standardType.equals("H")){
				statistic.setHbCount(statistic.getHbCount() + 1);
			}
			if(standardType.equals("S")){
				statistic.setOtherCount(statistic.getOtherCount() + 1);
			}
		}
		return statistic;
	}
	
	/**
	 * 查出符合条件的题录 不分页
	 * @param query
	 * @return
	 */
	public List<StandardVo> listStandardByQueryNoPage(Query query) {
		return standardMapper.listStandardByQuery(query);
	}
	/**
	 * 根据体系节点查出其下符合条件的题录 不分页
	 * @param query
	 * @return
	 */
	public List<StandardVo> listStandardByNodeIdNoPage(Query query) {
		List<StandardVo> standardVoList = standardMapper.listStandardByNodeId(query);
		return standardVoList;
	}
	/**
	 * 只查询当前节点关联的题录
	 * @param query
	 * @return
	 */
	public List<StandardVo> StandardByNodeId(Query query) {
		List<StandardVo> standardVoList = standardMapper.StandardByNodeId(query);
		return standardVoList;
	}
	/**
	 * @param standardId
	 */
	public String getNodeByStandardId(String standardId) {
		return architectureNodeService.getNodeByStandardId(standardId);
	}
	public int  searnull(String str ) {
		return 	standardMapper.searnull(str);
    }
	
	public  List<String> getdepartment(Query query ) {
		return 	standardMapper.getdepartment(query);
	}
	public  List<StandardVo> getdeparUnit(Query query) {
		return 	standardMapper.getdeparUnit(query);
	}

	public List<String> showType() {
		// TODO Auto-generated method stub
		return standardMapper.showType();
	}

	public List<String> showStatus() {
		// TODO Auto-generated method stub
		return standardMapper.showStatus();
	}

	public List<String> showNature() {
		// TODO Auto-generated method stub
		return standardMapper.showNature();
	}
	/**
	 * 这个是高级查询的二次筛选
	 * @param paramMap
	 * @return
	 */
	public List<StandardVo> secondSearchAndSelect(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return standardMapper.secondSearchAndSelect(paramMap);
	}
	/**
	 * 通过输入内容进行内容联想
	 * @param query
	 * @return
	 */
	public 	List<Map<String,Object>> getContentName(Query query) {
		// TODO Auto-generated method stub
		return standardMapper.getContentName(query);
	}
	/**
	 * 通过id去查询文章是否存在
	 * @param id
	 * @return
	 */
	public StandardVo findstandardById(String id) {
		// TODO Auto-generated method stub
		return standardMapper.findStandardById(id);
	}
	/**
	 * 通过标准题录中的标准编号去查询
	 * @param standardNo
	 * @return
	 */

	public StandardVo findByStandardNo(String standardNo) {
		// TODO Auto-generated method stub
		return standardMapper.findByStandardNo(standardNo);
	}
	/**
	 * 分类查询，就是将通过关联表将标准题录表和分类表进行关联
	 * @param query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public EasyUISeparatPage listStandardByIcsCode(Query query) {
		// 设置分页参数
				PageHelper.startPage(query.getPage(), query.getRows());
				// 执行查询
				List<StandardVo> standardVoList = standardMapper.listStandardByIcsCode(query);
				// 得到结果
				PageInfo<StandardVo> pageInfo = new PageInfo<StandardVo>(standardVoList);
				EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
				return data;
	}
}
