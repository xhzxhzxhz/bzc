package com.folkestone.bzcx.bean.bean_vo.backup;

import java.util.Date;

/**
 * 备份表和数据库所对应的实体类
 * @author folkestone-6
 *
 */
public class BackupVo {
	/**
	 * 备份ID
	 */
    private String backupId;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 恢复日期
     */
    private Date recoveryDate;
    /**
     * 备份类别
     */
    private Integer bgroup;
    /**
     * 操作人
     */
    private String operuser;
    /**
     * 备份路径
     */
    private String path;

    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId == null ? null : backupId.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(Date recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public Integer getGroup() {
        return bgroup;
    }

    public void setGroup(Integer bgroup) {
        this.bgroup = bgroup;
    }

    public String getOperuser() {
        return operuser;
    }

    public void setOperuser(String operuser) {
        this.operuser = operuser == null ? null : operuser.trim();
    }

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
    
}