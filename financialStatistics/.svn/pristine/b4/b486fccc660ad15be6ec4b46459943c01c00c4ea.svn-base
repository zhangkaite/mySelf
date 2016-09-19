package com.ttmv.financialStatistics;

import java.text.SimpleDateFormat;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	public static void main(String[] args) {
		String data="/datacenter/paycenter/all/allout_20160321/20160321";
		
		String[] dateTime=data.split("/");
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			System.out.println(DateUtil.getUnixDate(sdf.parse(dateTime[5])));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
 
}
