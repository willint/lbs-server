package com.lbs.nettyserver.utils.sysutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeUtil {
	/**
	 * 获取当前中国标准时间字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getChinaLocalDateTimeNowStr() {
		TimeZone gmtTz = TimeZone.getTimeZone("GMT");
		// 设置目标时间为中国标准时
		TimeZone desTz = TimeZone.getTimeZone("Asia/Shanghai");
		GregorianCalendar rightNow = new GregorianCalendar(gmtTz);
		Date mydate = rightNow.getTime();
		// 设置时间字符串格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置目的时间时区
		df.setTimeZone(desTz);
		return df.format(mydate);
	}
	/**
	 * 获取当前中国标准时间，格式为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getChinaLocalDateTimeNow() {
		TimeZone gmtTz = TimeZone.getTimeZone("GMT");
		// 设置目标时间为中国标准时
		TimeZone desTz = TimeZone.getTimeZone("Asia/Shanghai");
		GregorianCalendar rightNow = new GregorianCalendar(gmtTz);
		Date mydate = rightNow.getTime();
		// 设置时间字符串格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置目的时间时区
		df.setTimeZone(desTz);
		
		Date resultDate=null;
		try {
			resultDate= df.parse(df.format(mydate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultDate;
	}

	/**
	 * 时间格式化为yyyy-MM-dd HH:mm:ss结构的字符串
	 * @param date
	 * @return
	 */
	public static  String dateTimeFormatToString (Date date){
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return timeFormat.format(date);
	}
	/**
	 * 时间格式化为timeFormatStr指定结构的字符串
	 * @param date
	 * @return
	 */
	public static  String dateTimeFormatToString (Date date,String timeFormatStr){
		SimpleDateFormat timeFormat=new SimpleDateFormat(timeFormatStr);
		return timeFormat.format(date);
	}
	
	/**
	 * 时间格式化为yyyy-MM-dd HH:mm:ss结构
	 * @param date
	 * @return
	 */
	public static  Date dateTimeFormat (Date date){
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return timeFormat.parse(timeFormat.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取昨天的日期
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date getChinaLocalDateTimeYesterday(){
		Date nowTime=getChinaLocalDateTimeNow();
		Calendar  calendar  =  new  GregorianCalendar(); 
	    calendar.setTime(nowTime); 
	    calendar.add(Calendar.DATE,-1);
	    return calendar.getTime();  
	}
}
