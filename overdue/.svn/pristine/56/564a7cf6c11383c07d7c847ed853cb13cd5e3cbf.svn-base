package com.ttmv.service.tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Damon
 * 
 * @explain 工具类
 */
public class UtilBean {
	

	/**
	 * Unix时间戳转 java date
	 * @param 2015年11月10日14:19:07
	 * @throws ParseException
	 */
	public static Date unixTimeFmt(long time) throws ParseException{
		String dt = new SimpleDateFormat
				("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(time * 1000));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(dt);
	}

}
