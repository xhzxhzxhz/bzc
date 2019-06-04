package com.folkestone.bzcx.mapper.dm;

import java.util.List;

import com.folkestone.bzcx.bean.bean_do.dm.F_File_ShareDo;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.FileShareVo;

/**
 * 资源共享维护Mapping
 * @author Administrator
 *
 */
public interface FFileShareMapper {

	/**
	 * 资源共享维护列表
	 * @param query
	 * @return
	 */
	List<FileShareVo> listResourceByQuery(Query query);

	/**
	 * 查询资源共享
	 * @param fileId
	 * @return
	 */
	List<FileShareVo> getResourceById(Query query);
	
	
	/**
	 * 查询下载次数最多的资源
	 * @return
	 */
	List<FileShareVo> listhostupload();
	/**
	 * 资源共享的删除
	 * @param fileId
	 * @return 
	 */
	int deleteByIds(String fileId);

	/**
	 * 资源共享审核
	 * @param shareFileDo
	 * @return
	 */
	int update(F_File_ShareDo shareFileDo);

	/**
	 * 根据文件名，查询资源共享
	 * @param string
	 * @return
	 */
	FileShareVo getShareFileByFileName(String shareFileName);

	/**
	 * 插入资源共享
	 * @param shareFileDo
	 * @return
	 */
	int insert(F_File_ShareDo shareFileDo);
     
	
	/**
	 * 通过id查询文件信息
	 * @param id
	 * @return
	 */
	FileShareVo getInfoByid(String id);
}