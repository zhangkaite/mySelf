package com.datacenter.dams.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.datacenter.dams.input.hbase.util.DateRange;

public class DateUtil {

	private static final Integer DATE_DAY = 1;
	private static final Integer DATE_HOUR = 2;
	private static final Integer DATE_MINUE = 3;
	private static final Integer DATE_SECOND = 4;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 计算当前时间差 type：1:按天计算 2：按小时计算
	 * 
	 * @param type
	 * @param timeValue
	 * @return
	 */
	public static Date getQueryTime(Integer type, Integer timeValue) {
		if (type != null && timeValue != null) {
			Calendar calendar = Calendar.getInstance();
			/* 按天天计算 */
			if (type.equals(DATE_DAY)) {
				calendar.add(Calendar.DAY_OF_MONTH, timeValue);
			}
			/* 按小时计算 */
			else if (type.equals(DATE_HOUR)) {
				calendar.add(Calendar.HOUR_OF_DAY, timeValue);
			}
			/* 按分钟计算 */
			else if (type.equals(DATE_MINUE)) {
				calendar.add(Calendar.MINUTE, timeValue);
			}
			/* 按秒计算 */
			else if (type.equals(DATE_SECOND)) {
				calendar.add(Calendar.SECOND, timeValue);
			}
			return calendar.getTime();
		}
		return new Date();
	}

	/**
	 * 计算按期日预置数据 type：1:按天计算 2：按小时计算
	 * 
	 * @param type
	 * @param timeValue
	 * @return
	 */
	public static Date getQueryFixedTime(Date currentTime, Integer type, Integer timeValue) {
		if (type != null && timeValue != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentTime);
			/* 按天天计算 */
			if (type.equals(DATE_DAY)) {
				calendar.add(Calendar.DAY_OF_MONTH, timeValue);
			}
			/* 按小时计算 */
			else if (type.equals(DATE_HOUR)) {
				calendar.add(Calendar.HOUR_OF_DAY, timeValue);
			}
			/* 按分钟计算 */
			else if (type.equals(DATE_MINUE)) {
				calendar.add(Calendar.MINUTE, timeValue);
			}
			/* 按秒计算 */
			else if (type.equals(DATE_SECOND)) {
				calendar.add(Calendar.SECOND, timeValue);
			}
			return calendar.getTime();
		}
		return new Date();
	}

	/**
	 * 转换Date为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getStringTime(Date date) {
		if (date != null) {
			return sdf.format(date);
		}
		return null;

	}

	public static Date getDate(String date) {
		if (null != date) {
			try {
				if (date.length() == 13) {
					return new Date(Long.parseLong(date));
				} else {
					return new Date(Long.parseLong(date) * 1000);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String getLongDate(String date) {
		if (null != date) {
			try {
				long timeStart = sdf.parse(date).getTime();
				return String.valueOf(timeStart);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String getUnixDate(String date) throws Exception {
		if (null != date) {
			try {
				long timeStart = sdf.parse(date).getTime() / 1000;
				return String.valueOf(timeStart);
			} catch (Exception e) {
				throw e;
			}
		}
		return null;
	}

	public static long getUnixDate(Date date) throws Exception {
		if (null != date) {
			try {
				long timeStart = date.getTime() / 1000;
				return timeStart;
			} catch (Exception e) {
				throw e;
			}
		}
		return 0L;

	}
	
	
	 /** 
     * 根据年 月 获取对应的月份 天数 
     * */  
    public static int getDaysByYearMonth(int year, int month) {  
          
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }   
    
    /**
	 * 获取上个月的时间范围
	 * @return
	 */
	public static DateRange getLastMonth(){
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.MONTH, -1);
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		setMinTime(startCalendar);
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.MONTH, -1);
		endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setMaxTime(endCalendar);
		
		return new DateRange(startCalendar.getTime(), endCalendar.getTime());
	}
	
	private static void setMinTime(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	private static void setMaxTime(Calendar calendar){
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
	}


	public static void main(String[] args) {
		DateRange dateRange=getLastMonth();
		System.out.println(sdf.format(dateRange.getStart())+","+sdf.format(dateRange.getEnd()));
	}

}
