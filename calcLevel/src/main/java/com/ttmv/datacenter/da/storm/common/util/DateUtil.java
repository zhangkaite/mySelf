package com.ttmv.datacenter.da.storm.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
		if ((null != date) && (date.length() == 10)) {
			try {
				return new Date(Long.valueOf(date).longValue() * 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				return new Date(Long.valueOf(date).longValue());
			} catch (Exception e) {
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


}
