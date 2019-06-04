package com.folkestone.bzcx.mapper.backup;

import com.folkestone.bzcx.bean.bean_do.backup.B_Backup_StrategyDo;


public interface BBackupStrategyMapper {

    int delete(String baskupStrategyId);

    int insert(B_Backup_StrategyDo record);

    int update(B_Backup_StrategyDo record);
    
    B_Backup_StrategyDo selectByBGroup(int GROUP);
}