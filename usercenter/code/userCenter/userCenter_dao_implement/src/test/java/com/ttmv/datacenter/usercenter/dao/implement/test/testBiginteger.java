package com.ttmv.datacenter.usercenter.dao.implement.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class testBiginteger {

	public static void main(String[] args) {
//		List<BigInteger> list = new ArrayList<BigInteger>();
//		BigInteger a = new BigInteger("1");
//		BigInteger b = new BigInteger("2");
//		list.add(a);
//		list.add(b);
//		try {
//			String json = JsonUtil.getObjectToJson(list);
//			System.out.println(json);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		System.out.println(sdf.format(new Date()));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -2);
		System.out.println(sdf.format(c.getTime()));
		System.out.println(Long.MAX_VALUE);
		
	}
}
