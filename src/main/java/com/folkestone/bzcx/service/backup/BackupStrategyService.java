package com.folkestone.bzcx.service.backup;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.folkestone.bzcx.bean.bean_do.backup.B_Backup_StrategyDo;
import com.folkestone.bzcx.mapper.backup.BBackupStrategyMapper;

/**
 * 备份策略服务
 * @author folkestone-6
 *
 */
@Service
public class BackupStrategyService {
	
	/**
	 * 备份策略mapper
	 */
	@Autowired
	private BBackupStrategyMapper backupStrategyMapper;
	
	/**
	 * 添加一个对象
	 */
	public int insert(B_Backup_StrategyDo record) {
		return backupStrategyMapper.insert(record);
	}
	
	/**
	 * 根据类别查询一个对象
	 */
	public B_Backup_StrategyDo selectByBGroup(Integer GROUP) {
		return backupStrategyMapper.selectByBGroup(GROUP);
	}
	/**
	 * 更新一个对象
	 */
	public int update(B_Backup_StrategyDo record) {
		return backupStrategyMapper.update(record);
	}

}
