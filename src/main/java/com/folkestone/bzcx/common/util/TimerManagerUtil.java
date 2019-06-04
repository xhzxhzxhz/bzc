package com.folkestone.bzcx.common.util;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 备份策略定时器
 * 
 * @author folkestone-6
 *
 */
public class TimerManagerUtil {

	/**
	 * 首次执行时间
	 */
	public static Date date;
	/**
	 * 间隔时间
	 */
	public static int periodDay;
	/**
	 * 执行任务
	 */
	public static TimerTask task;
	
	
	@SuppressWarnings("static-access")
	public TimerManagerUtil() {
		System.out.println("TimerManagerUtil=============");
		// Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.HOUR_OF_DAY, hour);
		// calendar.set(Calendar.MINUTE, minute);
		// calendar.set(Calendar.SECOND, second);
		
//		try {
//			Context ctx = (Context) new javax.naming.InitialContext().lookup("java:comp/env");
//			timer = (Timer)ctx.lookup("timer");
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		timer.cancel();
		if(this.date != null) {
			startTimer(new Timer());
		}
		
	}
	//间隔天数换算成秒
	public long intConvertLong(int periodDay) {
		long periodsecond = 24 * 60 * 60 * 1000 * periodDay;
		return periodsecond;
	}
	public Timer startTimer(Timer timer) {
		// 安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		timer.schedule(task, date, intConvertLong(periodDay));
		return timer;
	}

}
