package com.folkestone.bzcx.bean.bean_vo.interact;

import java.util.Date;





import org.springframework.format.annotation.DateTimeFormat;

import com.folkestone.bzcx.bean.bean_vo.dm.StandardVo;
import com.folkestone.bzcx.common.util.DateFormatUtil;
/**
 * 有效确认维护实体类
 * @author Administrator
 *
 */
public class ValidConfirmVo {
	/**
	 * 主键：标准有效性确认ID
	 */
    private String validConfirmId;

    /**
     * 外键：题录ID
     */
    private String standardId;

    /**
     * 建议状态。1：现行；2：暂行；3：废止
     */
    private int suggestStatus;

    /**
     * 描述
     */
    private Object comment;

    /**
     * 请求确认人
     */
    private Object applyUser;

    /**
     * 请求时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;


    /**
     * 状态：1：待审核；2：审核通过；3：驳回；4：忽略
     */
    private int status;
    
    /**
     * 标准题录表
     */
    private StandardVo standar;
    
    private String stanNo;
    
    
    private String replaystandName;
    
    private String replaystandNo;
    
    private String standardName;
    
    private String standardNo;
    
    private String nickname;
    
    private String realname;
    
    public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getReplaystandName() {
		return replaystandName;
	}

	public void setReplaystandName(String replaystandName) {
		this.replaystandName = replaystandName;
	}

	public String getReplaystandNo() {
		return replaystandNo;
	}

	public void setReplaystandNo(String replaystandNo) {
		this.replaystandNo = replaystandNo;
	}

	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	public String getStandardNo() {
		return standardNo;
	}

	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}
    
    
    public String getStanNo() {
		return stanNo;
	}

	public void setStanNo(String stanNo) {
		this.stanNo = stanNo;
	}

    public StandardVo getStandar() {
		return standar;
	}

	public void setStandar(StandardVo standar) {
		this.standar = standar;
	}


    public String getValidConfirmId() {
        return validConfirmId;
    }

    public void setValidConfirmId(String validConfirmId) {
        this.validConfirmId = validConfirmId == null ? null : validConfirmId.trim();
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId == null ? null : standardId.trim();
    }

    public int getSuggestStatus() {
        return suggestStatus;
    }

    public void setSuggestStatus(int suggestStatus) {
        this.suggestStatus = suggestStatus;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public Object getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(Object applyUser) {
        this.applyUser = applyUser;
    }


    public String getApplyDate() {
    	return DateFormatUtil.date2Str(applyDate);
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}