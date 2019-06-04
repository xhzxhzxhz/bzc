package com.folkestone.bzcx.bean.bean_do.backup;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;
/**
 * 备份详情表对应的实体类
 * @author folkestone-6
 *
 */
public class B_BackupDo {
    private String backupId = UUIDUtil.getUUID("back");
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 恢复时间
     */
    private Date recoveryDate;
    /**
     * 类别（1.题录2.原文3.体系4.资源）
     */
    private Integer bgroup;
    /**
     * 恢复时的操作人
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

    public Integer getBgroup() {
        return bgroup;
    }

    public void setBgroup(Integer bgroup) {
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
        this.path = path == null ? null : path.trim();
    }
}