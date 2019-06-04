package com.folkestone.bzcx.service.dm;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.dm.F_File_ShareDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.dm.FileShareVo;
import com.folkestone.bzcx.mapper.dm.FFileShareMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * 资源共享
 * @author Administrator
 *
 */
@Service
public class FileShareService {
	
	@Autowired
	private FFileShareMapper fileshare;
	
	
	
	/**
	 * 
	 * 通过：id,文件名，文件描述，状态，查询共享资源
	 * @param query
	 * @return
	 */
	public EasyUISeparatPage getInfo(Query query){
		Object ob = query.get("page");
		Integer rows = query.getRows();
		if(ob == null){
			ob = "1";
		}
		Integer page = new Integer(ob.toString());
		// 设置分页参数
		PageHelper.startPage(page, rows);
				// 执行查询
				List<FileShareVo> listFeedbackByQuery = fileshare.getResourceById(query);
				// 得到结果
				PageInfo<FileShareVo> pageInfo = new PageInfo<FileShareVo>(listFeedbackByQuery);
				EasyUISeparatPage result = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return result;
	}
	
	public List<FileShareVo> hostUpload(){
		return fileshare.listhostupload();
	}
	
	/**
	 * 
	 * 更改共享资源信息
	 * @param filesharedo
	 * @return
	 */
	public int changeInfo(F_File_ShareDo filesharedo){
		return  fileshare.update(filesharedo);
	}
	
	
	/**
	 * 
	 * 用于上传文件时创建
	 * @param filedao
	 * @return
	 */
	public int addfileInfo(F_File_ShareDo filedao){
		return fileshare.insert(filedao);
	} 
	
	
	/**
	 * 
	 * 用于通过文件id查询文件信息
	 * @param st
	 * @return
	 */
	public FileShareVo getInfoByid(String st){
		return fileshare.getInfoByid(st);
	}
	
	public int deleteByIds(String[] ids){
		int num = 0;
		for (int i = 0; i < ids.length; i++) {
			fileshare.deleteByIds(ids[i]);
			num++;
		}
		return num;
	}
}
