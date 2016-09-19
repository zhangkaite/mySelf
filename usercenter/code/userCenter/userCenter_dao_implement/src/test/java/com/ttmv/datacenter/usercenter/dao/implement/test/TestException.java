package com.ttmv.datacenter.usercenter.dao.implement.test;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class TestException {

	private final static Logger log = LogManager.getLogger(TestException.class);
	
	public static void main(String[] args) {
		int a = 0;
		int b = 10;
		Integer num = null;
		try {
			//num.toString();
			int c = b/a;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
