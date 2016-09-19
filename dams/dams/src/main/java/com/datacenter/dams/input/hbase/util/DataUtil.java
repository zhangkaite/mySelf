package com.datacenter.dams.input.hbase.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.util.DateUtil;

public class DataUtil {

	private final static String START_MILLS = " 00:00:00.000";
	private final static String END_MILLS = " 23:59:59.999";

	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static final Integer DATE_DAY = 1;
	private static final Integer DATE_HOUR = 2;
	private static final Integer DATE_MINUE = 3;
	private static final Integer DATE_SECOND = 4;
	private static final Logger log = LogManager.getLogger(DateUtil.class);

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

	/***
	 * 获取当前时间指定的前几天时间
	 * 
	 * @param currentData
	 * @param interval
	 * @return
	 * @throws ParseException
	 */
	public static String getData(Date currentData, int interval) throws ParseException {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(currentData);
		cal.add(java.util.Calendar.DATE, -interval); // 向前一周；如果需要向后一周，用正数即可
		return sdf.format(cal.getTime());
	}

	public static Date getIntervalData(Date currentData, int interval) throws Exception {
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(currentData);
		cal.add(java.util.Calendar.DATE, -interval); // 向前一周；如果需要向后一周，用正数即可
		return cal.getTime();
	}

	/***
	 * 获取指定时间起始、结束时间戳精确到毫秒
	 * 
	 * @param currentDate
	 * @return
	 * @throws ParseException
	 */
	/*
	 * public static String[] getStartEndTime(String currentDate, int interval)
	 * throws ParseException { String[] times = new String[2]; String time =
	 * DataUtil.getData(sdf.parse(currentDate), interval) +START_MILLS; Date
	 * start_date = format.parse(time); times[0] =
	 * String.valueOf(start_date.getTime()).substring(0, 10); String end_time =
	 * currentDate + END_MILLS; Date end_date = format.parse(end_time); times[1]
	 * = String.valueOf(end_date.getTime()).substring(0, 10); return times;
	 * 
	 * }
	 */

	public static String[] getStartAndEndTime(Date date) throws Exception {
		String[] times = new String[2];
		String time = sdf.format(date) + START_MILLS;
		log.info("用户贡献榜起始时间:" + time);
		Date start_date = format.parse(time);
		log.info("用户贡献榜格式化后的起始时间:" + start_date);
		times[0] = String.valueOf(start_date.getTime()).substring(0, 10);
		String end_time = sdf.format(date) + END_MILLS;
		Date end_date = format.parse(end_time);
		times[1] = String.valueOf(end_date.getTime()).substring(0, 10);
		return times;

	}

	//获取当前时间为这个月的第几天
	public static int getWeeksByChooseDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	
	
	
}
