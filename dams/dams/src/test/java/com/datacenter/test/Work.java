package com.datacenter.test;

import java.util.Hashtable;

public class Work {
	
	public static Hashtable  flagDate=new Hashtable<>();
	
	public static void run(int data) throws InterruptedException{
		if (null==flagDate.get("flagDate")) {
			flagDate.put("flagDate", 1);
		}else {
			System.out.println("当前调用的线程是："+data);
			Thread.sleep(4000);
			System.out.println(flagDate.get("flagDate"));
			int num=(Integer)flagDate.get("flagDate");
			num++;
			flagDate.put("flagDate", num);
		}
	}

}
