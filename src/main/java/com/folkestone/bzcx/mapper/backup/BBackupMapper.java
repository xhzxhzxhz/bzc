package com.folkestone.bzcx.mapper.backup;
import com.folkestone.bzcx.bean.bean_do.backup.B_BackupDo;
import com.folkestone.bzcx.bean.bean_vo.backup.BackupVo;

import java.util.List;

public interface BBackupMapper {
	List<BackupVo> selectByGroup(Integer bGroup);
	
    int delete(String backupId);

    int insert(B_BackupDo record);

    int update(B_BackupDo record);
}