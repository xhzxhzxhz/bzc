package com.folkestone.bzcx.mapper.dm;

import java.util.List;

import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.FileStatisticVo;
public interface FileStatisticsMapper {
	List<FileStatisticVo> getTodayAcess();
	List<FileStatisticVo> getCumulaAcess();
	/*List<FileStatisticVo> getActiveUserNumber();*/
	List<FileStatisticVo> getTotalUserNumber();
	List<FileStatisticVo> getTodayRetrieval();
	List<FileStatisticVo> getTotalRetrieval();
	List<FileStatisticVo> getStandardView();
	List<FileStatisticVo> getStandardDownload();
	List<FileStatisticVo> getSystemAccess();
	List<FileStatisticVo> getStandardClassificalis();
	List<FileStatisticVo> getSearchWordHeatlis(Query query);
	/*List<FileStatisticVo> getTwoLevelQuantitylis(Query query);*/
	/*List<FileStatisticVo> getTwoLevelAccesslis();*/
	/*List<FileStatisticVo> getTwoLevelUserlist(Query query);*/
	List<FileStatisticVo> getStandardUsage();
	/*List<FileStatisticVo> getRetWords();*/
	}