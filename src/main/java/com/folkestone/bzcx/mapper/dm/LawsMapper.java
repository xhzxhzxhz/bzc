package com.folkestone.bzcx.mapper.dm;

import java.util.List;
import java.util.Map;

import com.folkestone.bzcx.bean.bean_do.dm.L_LawsDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.LawsVo;

public interface LawsMapper {

	 List<LawsVo> findAllLaaws();

	List<LawsVo> listLawsByQuery(Query query);

	List<LawsVo> secondSearch(Map<String, Object> paramMap);

	Map<String, Object> getContentName(Query query);

	LawsVo findByLawsNoTrim(String noTrim);

	LawsVo findLawsById(String lawsId);

	List<Map<String, Object>> findLawsListByQuery(Map<String, Object> paramMap);

	int insert(L_LawsDo lawsDo);

	int deleteById(String lawsId);
	/**
	 * 通过法律文号去获取法律法规题录
	 * @param lawsNoTrim
	 * @return
	 */
	LawsVo findLawsByNoTrim(String lawsNoTrim);

	int update(L_LawsDo lawsDo);

	LawsVo findLawsByStdFileId(String stdFileId);

	List<LawsVo> listLawsByIcsCode(Query query);

}
