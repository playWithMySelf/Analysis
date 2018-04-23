package com.founder.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 公有工具类
 *
 * @author yhb
 *
 */
public class DateUtil {

	/**
	 * 获取当前时间
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 获取当前日期
	 * yyyyMMdd
	 * @return
	 */
	public static String getDateNum() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	/**
	 * 获取年
	 * yyyy
	 * @return
	 */
	public static String getDateYear() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}

	/**
	 * 获取月
	 * MM
	 * @return
	 */
	public static String getDateMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(new Date());
	}

	/**
	 * 获取年月
	 * yyyy-MM
	 * @return
	 */
	public static String getDateMon_Year() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(new Date());
	}
	public static String getDateUMon_Year() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date tt = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(tt);
		cal.add(Calendar.MONTH, -1);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取当前时间
	 * yyyy-MM-dd
	 * @return
	 */
	public static String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间
	 * yyyy年MM月dd日
	 * @return
	 */
	public static String getNowCNDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(new Date());
	}

	/**
	 * 获取当前时间
	 * yyyy年MM月dd日 星期
	 * @return
	 */
	public static String getCNDate() {
		Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);// 获取年份
		int month = ca.get(Calendar.MONTH)+1;// 获取月份
		int day = ca.get(Calendar.DATE);// 获取日
		int week = ca.get(Calendar.DAY_OF_WEEK);
		String wk = "";
		switch (week) {
		case 1:
			wk = "星期日";
			break;
		case 2:
			wk = "星期一";
			break;
		case 3:
			wk = "星期二";
			break;
		case 4:
			wk = "星期三";
			break;
		case 5:
			wk = "星期四";
			break;
		case 6:
			wk = "星期五";
			break;
		default:
			wk = "星期日";
			break;
		}
		return year + "年" + month + "月" + day + "日" + " " + wk;
	}

	/**
	 * 将string转成date
	 * @param date
	 * @return
	 */
	public static Date parseToDate(String date){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(date);
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 将yyyy-MM-dd转
	 * yyyy年MM月dd日
	 * @param date
	 * @return
	 */
	public static String parseToString(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try{
			if("".equals(date) || date == null){
				return sdf.format(new Date());
			}else{
				SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(sd1.parse(date));
			}
		}catch(Exception e){
			return sdf.format(new Date());
		}
	}

	/**
	 * 将string转成date
	 * @param date
	 * @return
	 */
	public static Date parseToTime(String date){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.parse(date);
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 将string转成date
	 * @param date
	 * @return
	 */
	public static Date parseToTime2(String date){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			return sdf.parse(date);
		}catch(Exception e){
			return null;
		}
	}

}
