package com.folkestone.bzcx.controller.admin.backup;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.folkestone.bzcx.bean.bean_do.backup.B_Backup_StrategyDo;
import com.folkestone.bzcx.bean.bean_dto.common.Result;
import com.folkestone.bzcx.bean.bean_vo.backup.BackupStrategyVo;
import com.folkestone.bzcx.common.util.TimerManagerUtil;
import com.folkestone.bzcx.controller.base.BaseController;
import com.folkestone.bzcx.service.backup.AdminStandardTimerTaskSerivce;
import com.folkestone.bzcx.service.backup.ArchitectureTimerTaskService;
import com.folkestone.bzcx.service.backup.BackupStrategyService;
import com.folkestone.bzcx.service.backup.ResourcesTimerTaskService;
import com.folkestone.bzcx.service.backup.StandardFileTimerTaskService;

/**
 * 备份策略control
 * @author folkestone-6
 *
 */
@Controller
@RequestMapping(value = "/admin/backup")
public class BackupStrtegyController extends BaseController {
	
	@Autowired
	private BackupStrategyService backupStrategyService;
	
	
	@ResponseBody
	@RequestMapping(value = "/selectByGroup", method={RequestMethod.POST} )
	public BackupStrategyVo selectByGroup(Integer group) {
		B_Backup_StrategyDo b_Backup_StrategyDo= backupStrategyService.selectByBGroup(group);
		BackupStrategyVo voObj = new BackupStrategyVo();
		copyBackupStrategy(voObj, b_Backup_StrategyDo);
		return voObj;
	}
	
	
	@SuppressWarnings("static-access")
	@ResponseBody
	@RequestMapping(value = "/strategy", method={RequestMethod.POST} )
	public Result backup(BackupStrategyVo backupStrategyVo,HttpServletRequest request) {
		
		System.out.println("数据备份策略------------");
		Result result = new Result(false);
		// 把前台传的时分秒赋给定时器
		Date nowDate = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(nowDate);
		calendar.set(Calendar.HOUR_OF_DAY, backupStrategyVo.getHour());
		calendar.set(Calendar.MINUTE, backupStrategyVo.getMinute());
		calendar.set(Calendar.SECOND, backupStrategyVo.getSecond());
		TimerManagerUtil timerManagerUtil = null;
		// 当选择每天的时候，间隔天数是1天,首次执行时间是当前日期
		if (backupStrategyVo.getCycle() == 1) {
			Date fristDate = calendar.getTime();
			timerManagerUtil.periodDay = 1;
			timerManagerUtil.date = fristDate;
		}
		// 当选择每周的时候，间隔天数是7天,首次执行时间是用户输入的星期
		if (backupStrategyVo.getCycle() == 2) {
			int nowWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			System.out.println("今天周几：" + nowWeek);
			calendar.set(calendar.DAY_OF_WEEK, backupStrategyVo.getWeek());
			Date fristDate = calendar.getTime();
			timerManagerUtil.periodDay = 7;
			timerManagerUtil.date = fristDate;
		}
		// 当选择隔几天的时候
		if (backupStrategyVo.getCycle() == 3) {
			Date fristDate = calendar.getTime();
			timerManagerUtil.periodDay = backupStrategyVo.getNum();
			timerManagerUtil.date = fristDate;
		}

		System.out.println("before 方法比较：" + timerManagerUtil.date.before(new Date()));
		// 如果第一次执行定时任务的时间 小于 当前的时间
		// 此时要在 第一次执行定时任务的时间 加对应策略的天数，以便此任务在下个时间点执行。如果不加，任务会立即执行。循环执行的周期则以当前时间为准
		if (timerManagerUtil.date.before(new Date())) {
			if (backupStrategyVo.getCycle() == 1) {
				timerManagerUtil.date = this.addDay(timerManagerUtil.date, 1);
			}
			if (backupStrategyVo.getCycle() == 2) {
				timerManagerUtil.date = this.addDay(timerManagerUtil.date, 7);
			}
			if (backupStrategyVo.getCycle() == 3) {
				timerManagerUtil.date = this.addDay(timerManagerUtil.date, backupStrategyVo.getNum());
			}
		}

		// 备份题录
		if (backupStrategyVo.getGroup() == 1) {
			timerManagerUtil.task = new AdminStandardTimerTaskSerivce(); 
		}
		// 备份原文
		if (backupStrategyVo.getGroup() == 2) {
			timerManagerUtil.task = new StandardFileTimerTaskService();
		}
		// 备份体系
		if (backupStrategyVo.getGroup() == 3) {
			timerManagerUtil.task = new ArchitectureTimerTaskService();
		}
		// 备份资源
		if (backupStrategyVo.getGroup() == 4) {
			timerManagerUtil.task = new ResourcesTimerTaskService();
		}
		//启动定时器
		timerManagerUtil = new TimerManagerUtil();
		//vo转成do
		B_Backup_StrategyDo b_Backup_StrategyDo = new B_Backup_StrategyDo();
		b_Backup_StrategyDo.setFristTime(timerManagerUtil.date);
		b_Backup_StrategyDo.setbCycle(backupStrategyVo.getCycle());
		b_Backup_StrategyDo.setbGroup(backupStrategyVo.getGroup());
		b_Backup_StrategyDo.setNum(backupStrategyVo.getNum());
		b_Backup_StrategyDo.setWeek(backupStrategyVo.getWeek());

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println("首次执行时间：" + formatter.format(b_Backup_StrategyDo.getFristTime()));
		System.out.println("执行周期：" + b_Backup_StrategyDo.getbCycle());
		System.out.println("备份类型：" + b_Backup_StrategyDo.getbGroup());
		System.out.println("隔几天：" + b_Backup_StrategyDo.getNum());
		if (b_Backup_StrategyDo.getWeek() != null) {
			int aa = b_Backup_StrategyDo.getWeek() - 1;
			System.out.println("每周几：" + aa);
		}
		// 持久化备份策略
		B_Backup_StrategyDo old = backupStrategyService.selectByBGroup(backupStrategyVo.getGroup());
		if(old != null) {
//			Timer timer = (Timer)application.getAttribute("timer");
//			timer.cancel();
			b_Backup_StrategyDo.setBaskupStrategyId(old.getBaskupStrategyId());
			backupStrategyService.update(b_Backup_StrategyDo);
		}else {
			backupStrategyService.insert(b_Backup_StrategyDo);
		}
		result.setResult(true);
		return result;
	}
	
	// 增加或减少天数
	public Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}
	
	/**
	 * DO转成VO
	 * @param voObj  BackupStrategyVo
	 * @param doObj  B_Backup_StrategyDo
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public BackupStrategyVo copyBackupStrategy(BackupStrategyVo voObj, B_Backup_StrategyDo doObj){
		voObj.setBaskupStrategyId(doObj.getBaskupStrategyId());
		voObj.setCycle(doObj.getbCycle());
		voObj.setGroup(doObj.getbGroup());
		voObj.setNum(doObj.getNum());
		voObj.setWeek(doObj.getWeek());
		Date date = doObj.getFristTime();
	 	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		voObj.setHour(calendar.get(Calendar.HOUR_OF_DAY));
		voObj.setMinute(calendar.get(Calendar.MINUTE));
		voObj.setSecond(calendar.get(Calendar.SECOND));
		return voObj;
	}
}
