package com.folkestone.bzcx.service.dm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.dm.File_LawsDO;
import com.folkestone.bzcx.mapper.dm.FileLawsMapper;
/**
 * 标准原文表和法律法规题录关联表
 * @author zqm
 *
 */
@Service
public class FileLawsService {
	@Autowired 
	private FileLawsMapper fileLawsMapper;
	public int insert(File_LawsDO fileLaws) {
		
		return fileLawsMapper.insert(fileLaws);
	}

}
