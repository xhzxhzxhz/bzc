package com.folkestone.bzcx.mapper.dm;
import java.util.List;

import com.folkestone.bzcx.bean.bean_do.dm.S_Std_FileDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.StdFileVo;

public interface SStdFileMapper {
	/**
	 * 插入标准原文
	 * @param stdFileDo 
	 * @return
	 */
	int insert(S_Std_FileDo stdFileDo);

	/**
	 * 根据文件名，查询标准原文
	 * @param stdFileName
	 * @return
	 */
	StdFileVo getStdFileByFileName(String stdFileName);

	/**
	 * 根据ID删除标准原文
	 * @param stdFileId
	 * @return
	 */
	int delteById(String stdFileId);

	/**
	 * 根据条件查询标准原文集合
	 * @param query
	 * @return
	 */
	List<StdFileVo> listStdFileByQuery(Query query);

	/**
	 * 根据Id查询单条
	 * @param stdFileId
	 * @return
	 */
	StdFileVo getStdFileById(String stdFileId);

	/**
	 * @param stdFileDo
	 * @return
	 */
	int update(S_Std_FileDo stdFileDo);
	/**
	 * 通过标准号去查询标准原文
	 * @param standardNo
	 * @return
	 */
	StdFileVo findStdFileByStdNo(String standardNo);
	/**
	 * 通过法律法规id去查询标准原文
	 * @param lawsId
	 * @return
	 */
	StdFileVo findStdFileByLawsNo(String lawsId);

	List<StdFileVo> recommend();

	StdFileVo findById(String stdFileId);

	

}