package com.folkestone.bzcx.mapper.dm;

import com.folkestone.bzcx.bean.bean_do.dm.File_LawsDO;

public interface FileLawsMapper {
	/**
	 * 添加
	 * @param fileLaws
	 * @return
	 */
	int insert(File_LawsDO fileLaws);
	/**
	 * 通过原文id去删除
	 * @param stdFileId
	 * @return
	 */
	int delete(String _parameter);

}
