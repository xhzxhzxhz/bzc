package com.folkestone.bzcx.mapper.dm;

import java.util.List;
import java.util.Map;

import com.folkestone.bzcx.bean.bean_do.dm.S_StandardDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;

public interface SStandardMapper {
	
	List<String> listPinyin();

	/**
	 * 插入一条题录
	 * @param standardDo
	 * @return
	 */
	int insert(S_StandardDo standardDo);

	/**
	 * 根据集合查询多条题录
	 * @param query
	 * @return
	 */
	List<StandardVo> listStandardByQuery(Query query);
	List<StandardVo> listStandardByQuery();
	/**
	 * 二次检索
	 * @param query
	 * @return
	 */
	List<StandardVo> listStandardBySecondQuery(Query query);
	/**
	 * 根据主键ID删除题录
	 * @param standardId
	 * @return
	 */
	int deleteById(String standardId);

	/**
	 * 根据ID查询标准题录
	 * @param standardId
	 * @return
	 */
	StandardVo getStandardById(String standardId);

	/**
	 * 修改题录
	 * @param standardDo
	 * @return
	 */
	int update(S_StandardDo standardDo);

	/**
	 * 根据节点查询题录
	 * @param query
	 * @return
	 */
	List<StandardVo> listStandardByNodeId(Query query);
	/**
	 * 根据节点查询当前题录
	 * @param query
	 * @return
	 */
	List<StandardVo> StandardByNodeId(Query query);
	/**
	 * 根据标准号来查询标准
	 * @param standardNo
	 * @return
	 */
	StandardVo getStandardByNo(String standardNo);
	/**
	 * 根据置空标准号来查询标准
	 * @param standardNo
	 * @return
	 */
	StandardVo getStandardByNoTrim(String noTrim);
    int searnull(String str);

    List<StandardVo> selectBnodeId (Query query);

     List<String> getdepartment(Query query);
	 List<StandardVo> getdeparUnit(Query query);

	List<String> showType();

	List<String> showStatus();

	List<String> showNature();
	/**
	 * 高级查询之二次筛选
	 * @param paramMap
	 * @return
	 */
	List<StandardVo> secondSearchAndSelect(Map<String, Object> paramMap);
	/**
	 * 通过输入内容进行内容联想
	 * @param query
	 * @return
	 */
	List<Map<String,Object>> getContentName(Query query);
	/**
	 * 通过题录的id去查询文章的内容
	 * @param id
	 * @return
	 */
	StandardVo findStandardById(String id);
	/**
	 * 通过标准编号去查询
	 * @param standardNo
	 * @return
	 */
	StandardVo findByStandardNo(String standardNo);

	List<StandardVo> listStandardByIcsCode(Query query);
}