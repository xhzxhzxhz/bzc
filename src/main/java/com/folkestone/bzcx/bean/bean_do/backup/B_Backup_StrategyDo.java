package com.folkestone.bzcx.bean.bean_do.backup;

import java.util.Date;

import com.folkestone.bzcx.common.util.UUIDUtil;

/**
 * 备份策略对应数据库实体类
 * @author folkestone-6
 *
 */
public class B_Backup_StrategyDo {
    private String baskupStrategyId = UUIDUtil.getUUID("B_B_S");
    /**
	 * 周期（每天，每周，隔几天）
	 */
	private Integer bCycle;
	/**
	 * 每周几
	 */
	private Integer week;
	/**
	 * 隔几天
	 */
	private Integer num;
	/**
	 * 首次执行时间
	 */
	private Date fristTime;
	/**
	 * 备份类别
	 */
	private Integer bGroup;

    public String getBaskupStrategyId() {
        return baskupStrategyId;
    }

    public void setBaskupStrategyId(String baskupStrategyId) {
        this.baskupStrategyId = baskupStrategyId == null ? null : baskupStrategyId.trim();
    }

    public Integer getbCycle() {
        return bCycle;
    }

    public void setbCycle(Integer bCycle) {
        this.bCycle = bCycle;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getFristTime() {
        return fristTime;
    }

    public void setFristTime(Date fristTime) {
        this.fristTime = fristTime;
    }

    public Integer getbGroup() {
        return bGroup;
    }

    public void setbGroup(Integer bGroup) {
        this.bGroup = bGroup;
    }
}