package com.game.helper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.text.TextUtils;
import android.text.format.Time;

public class TimeUtil {
	public static final String TIME_FORMAT_XXXX_XX_XX_HH_MM = "yyyy-MM-dd HH:mm";
	
	public static final String TIME_FORMAT_MM_DD = "MM-dd";
	public static final String TIME_FORMAT_XXXX_XX_XX = "yyyy-MM-dd";
	public static final String TIME_FORMAT_XX_XX = "HH:mm";
	public static final String TIME_FORMAT_FULLTT = "yyyyMMddHHmmss";
	public static final String TIME_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT_DAY_START = "yyyy-MM-dd 00:00:00";
	public static final String TIME_FORMAT_DAY_END = "yyyy-MM-dd 23:59:59";

	/**
	 * 日期比较
	 * 
	 * @param strData1
	 * @param strData2
	 * @return 2--异常 0--日期相同 1--日期1大于日期2 -1--日期1小于日期2
	 */
	public static int parserTime(String format, String strData1, String strData2) {

		int result = 2;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			Date data1 = formatter.parse(strData1);
			Date data2 = formatter.parse(strData2);
			result = data1.compareTo(data2);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String changeTimeFormat(String time, String objectFormat) {
		try {
			if (TextUtils.isEmpty(time))
				return "";
			Date date = null;
			SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_FULL);
			date = sdf.parse(time);
			SimpleDateFormat sdf2 = new SimpleDateFormat(objectFormat);
			return sdf2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 与当前时间比较
	 * 
	 * @param targetTime
	 * @return 2--异常 0--日期相同 1--日期大于當前時間 -1--日期小于當前時間
	 */
	public static int parserCurTime(String format, String targetTime) {

		int result = 2;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date curDate = new Date(System.currentTimeMillis());
		try {
			Date data1 = formatter.parse(targetTime);
			result = data1.compareTo(curDate);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getTime(String format, String time) {
		if (TextUtils.isEmpty(time))
			return "";
		Calendar cal = Calendar.getInstance();
		Date date = new Date(time);
		cal.setTime(date);
		String yesterday = new SimpleDateFormat(TIME_FORMAT_DAY_START)
				.format(cal.getTime());
		return yesterday;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return String类型的当前时间
	 */
	public static String getCurTime(String format) {
		// 获取当前时间
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date curDate = new Date(System.currentTimeMillis());
		return formatter.format(curDate);
	}

	public static String getTime() {
		return getCurTime(TIME_FORMAT_FULL);
	}
	/**
	 * 目标时间距离当前时间几秒
	 * 
	 * @param format
	 *            时间格式
	 * @param time
	 *            目标时间
	 * @return
	 */
	public static long beforeTime1(String format, String time, String sysTime) {

		long result = 0;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			result = (formatter.parse(sysTime).getTime() - formatter
					.parse(time).getTime()) / 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 目标时间距离当前时间几分钟
	 * 
	 * @param format
	 *            时间格式
	 * @param time
	 *            目标时间
	 * @return
	 */
	public static long beforeTime(String format, String time, String sysTime) {

		long result = 0;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			result = (formatter.parse(sysTime).getTime() - formatter
					.parse(time).getTime()) / 60000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 目标时间距离当前时间几小时几分钟
	 * 
	 * @param format
	 *            时间格式
	 * @param time
	 *            目标时间
	 * @return
	 */
	public static String getBeforeTime(String format, String time, String sysTime) {
        
		long result = 0;
		long  mins=0;
		long  hours=0;
		long   mmins=0;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			result = (formatter.parse(sysTime).getTime() - formatter
					.parse(time).getTime()) / 60000;
			mmins= (formatter.parse(sysTime).getTime() - formatter
					.parse(time).getTime())/1000%60;
			hours=result/60;
			mins=result%60;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(hours>0){
			return hours+"小时"+mins+"分钟"+mmins+"秒";
		}else if(mins>0){
			return mins+"分钟"+mmins+"秒";
		}else{
			return mmins+"秒";
		}
		
	}
	
	
	public static String getTime(Date date, String timeFormat) {
		return new SimpleDateFormat(timeFormat).format(date.getTime());
	}

	/**
	 * 获取今天的最初时间,上一天的0时0分0秒开始
	 */
	public static String getTodayTime() {
		Calendar cal = Calendar.getInstance();
		return getTime(cal.getTime(), TIME_FORMAT_DAY_START);
	}
	/**
	 * 获取今天的最初时间,上一天的0时0分0秒开始
	 */
	public static String getTodayEndTime() {
		Calendar cal = Calendar.getInstance();
		return getTime(cal.getTime(), TIME_FORMAT_DAY_END);
	}

	/**
	 * 获取昨天的最初时间,上一天的0时0分0秒开始
	 */
	public static String getYestTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return getTime(cal.getTime(), TIME_FORMAT_DAY_START);
	}

	/**
	 * 获取昨天的最后时间,上一天的0时0分0秒开始
	 */
	public static String yesterdayLastTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return getTime(cal.getTime(), TIME_FORMAT_DAY_END);
	}

	/**
	 * 获取本周开始时间，从周一的0时0分0秒开始
	 * 
	 */
	public static String getWeekTime() {
		int mondayPlus;
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
			if (mondayPlus > 0) {// 周日情况的处理
				mondayPlus = -6;
			}

		}
		GregorianCalendar currentDate = new GregorianCalendar();
		SimpleDateFormat formatter = new SimpleDateFormat(
				TIME_FORMAT_XXXX_XX_XX);
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		String preMonday = formatter.format(monday);
		return preMonday + " 00:00:00";
	}

	/**
	 * 获取本月开始时间，从本月初的0时0分0秒开始
	 */
	public static String getMonthTime() {
		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_XXXX_XX_XX);
		String firstDayOfThisMonth = formatter.format(cal_1.getTime());
		return firstDayOfThisMonth + " 00:00:00";
	}
	/**
	 * 获得上个月的最后一天
	 */
	public static String getLastMonthEndDay() {

		Calendar calendar = Calendar.getInstance();  
		int month = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  

		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_DAY_END);
		String firstDayOfThisMonth = formatter.format(calendar.getTime());
		return firstDayOfThisMonth ; 

	}
	/**
	 * 获得上个月的第一天
	 */
	public static String getLastMonthStartDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_XXXX_XX_XX);
		String firstDayOfThisMonth = formatter.format(calendar.getTime());
		return firstDayOfThisMonth + " 00:00:00"; 

	}
	/**
	 * 获得获取上周一
	 */
	public static String getLastWeekFirstDay(){

		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.add(Calendar.DATE, -7);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_XXXX_XX_XX);
		String firstDayOfThisMonth = formatter.format(calendar.getTime());
		return firstDayOfThisMonth + " 00:00:00"; 

	}
	/**
	 * 获取上周日
	 */
	public static String getLastWeekEndDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.add(Calendar.DATE, -7);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT_DAY_END);
		String firstDayOfThisMonth = formatter.format(calendar.getTime());
		return firstDayOfThisMonth;

	}
	 /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
    	SimpleDateFormat sf = null;
    	Date d = new Date(time);
        sf = new SimpleDateFormat(TIME_FORMAT_FULL);
        return sf.format(d);
    }
                                      
    /*将字符串转为时间戳,返回秒*/
    public static long getStringToDate(String time) {
    	SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_FULL);
        Date date = new Date();
        try{
            date = sdf.parse(time);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return date.getTime()/1000;
    }
	
}
