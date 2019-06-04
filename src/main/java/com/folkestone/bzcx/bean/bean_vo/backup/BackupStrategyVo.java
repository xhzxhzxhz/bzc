package com.folkestone.bzcx.bean.bean_vo.backup;

/**
 * 备份策略表和前台所对应的实体类
 * @author folkestone-6
 *
 */
public class BackupStrategyVo {
	/**
	 * 备份策略ID
	 */
    private String baskupStrategyId;
    /**
     * 备份周期（每天，每周，隔几天）
     */
    private Integer cycle;
    /**
     * 每周几
     */
    private Integer week;
    /**
     * 隔几天
     */
    private Integer num;
    /**
     * 时分秒
     */
    private Integer hour;
    private Integer minute;
    private Integer second;
    /**
     * 类别（标准题录，标准原文等）
     */
    private Integer group;

    public String getBaskupStrategyId() {
        return baskupStrategyId;
    }

    public void setBaskupStrategyId(String baskupStrategyId) {
        this.baskupStrategyId = baskupStrategyId == null ? null : baskupStrategyId.trim();
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
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

	public Integer getGroup() {
		return group;
	}

	public void setGroup(Integer group) {
		this.group = group;
	}

	public Integer getHour() {
		return hour;
	}

	public void setHour(Integer hour) {
		this.hour = hour;
	}

	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}
	
	
    
}