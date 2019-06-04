package com.folkestone.bzcx.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Describe：时间操作工具类
 * 
 * @author smallking
 *
 *  2017年11月24日
 */
public class DateFormatUtil {

	private DateFormatUtil()
	{
		
	}
	/**
	 * 字符串转化成时间
	 * @param sourStr
	 * @param patter
	 * @return
	 */
	public static Date str2Date(String sourStr, String patter) 
	{
		if(sourStr == null){
			return null;
		}
		if(StringUtil.isEmpty(patter)){
			patter = "yyyy-MM-dd";
		}
		SimpleDateFormat format = new SimpleDateFormat(patter);
		try {
			Date date = format.parse(sourStr);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		return null; 
	}
	/**
	 * 时间转成字符串
	 * @param sourDate
	 * @return
	 */
	public static String date2Str(Date sourDate)
	{
		if(sourDate != null){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String str = format.format(sourDate);	
			return str; 
		}else{
			return "";
		}
	}
	/**
	 * 将日期的月份加减
	 * 加一个月或者减一个月
	 * @param dateStr
	 * @param subCount
	 */
	public static Date subMonth(Date date, int subCount)
	{
		Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, subCount);  
        date = cl.getTime(); 
        return date;
	}
	/**
	 * 获得两个日期之间的天数。
	 * 如果type是年，包含当前年和结束年
	 * 如果type是月，包含起始月和结束月
	 * @param date
	 * @param date2
	 * @param type
	 * @return
	 */
	public static int getBetweenDays(Date date, Date date2)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day1 = calendar.get(Calendar.DAY_OF_YEAR);
	    calendar.setTime(date2);
	    int day2 = calendar.get(Calendar.DAY_OF_YEAR);
	    return day1 - day2;
	}
	/**
	 * 获得两个日期之间的月数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getBetweenMonth(String startTime, String endTime)
	{
		int startYear = Integer.valueOf(startTime.substring(0, 4));
		int startMonth = Integer.valueOf(startTime.substring(5, 7));
		int endYear = Integer.valueOf(endTime.substring(0, 4));
		int endMonth = Integer.valueOf(endTime.substring(5, 7));
		int subMonth = (endYear - startYear) * 12 + (endMonth - startMonth) + 1;
		return subMonth;
	}
	/**
	 * 获取给定月数的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthLastDay(int year, int month)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year,month - 1,1);
		int last = cal.getActualMaximum(Calendar.DATE);
		return last;
	}
	/**
	 * 将当前的日期向后推指定的时间
	 * 例如： type=year,number=20
	 * 则是将当前日期后推20年
	 * @param type 可选值 year month day
	 * @param number 
	 */
	public static Date getLaterDay(String type, int number){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if(type.equals("year")){
			calendar.add(Calendar.YEAR, number);
		}else if(type.equals("month")){
			calendar.add(Calendar.MONTH, number);
		}else if(type.equals("day")){
			calendar.add(Calendar.DATE, number);
		}
		return calendar.getTime();
	}
}
