package com.folkestone.bzcx.service.backup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.backup.B_BackupDo;
import com.folkestone.bzcx.bean.bean_dto.common.EasyUISeparatPage;
import com.folkestone.bzcx.bean.bean_dto.common.Query;
import com.folkestone.bzcx.bean.bean_vo.backup.BackupVo;
import com.folkestone.bzcx.mapper.backup.BBackupMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 备份service
 * @author folkestone-6
 *
 */
@Service
public class BackupSerivce {
	
	@Autowired
	private BBackupMapper backupMapper;
	/**
	 * 按类别查询备份
	 * @param bgroup
	 * @return
	 */
	public EasyUISeparatPage selectByGroup(Query query,Integer bgroup){
		// 设置分页参数
		PageHelper.startPage(query.getPage(), query.getRows());
		// 执行查询
		List<BackupVo> backupList = backupMapper.selectByGroup(bgroup);
		// 得到结果
		PageInfo<BackupVo> pageInfo = new PageInfo<BackupVo>(backupList);
		EasyUISeparatPage data = new EasyUISeparatPage(pageInfo.getTotal(), pageInfo.getList());
		return data;
	}
	/**
	 * 删除一个对象
	 * @param backupId
	 * @return
	 */
	public int delete(String backupId) {
		return backupMapper.delete(backupId);
	}
	/**
	 * 添加一个对象
	 */
	public int insert(B_BackupDo record) {
		return backupMapper.insert(record);
	}
	
	/**
	 * 更新一个对象
	 */
	public int update(B_BackupDo record) {
		return backupMapper.update(record);
	}

}
