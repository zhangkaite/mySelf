package com.ttmv.datacenter.da.storm.common.util;

import java.math.BigDecimal;

public class Number2StringUtil {
	
	public static String numberToString(String number){
		BigDecimal db = new BigDecimal(number);
		String result=db.toPlainString();
		if (result.contains(".")) {
			return result.split("\\.")[0];
		}
		return result;
	}
	
	/*public static void main(String[] args) {
		String result="59129999.99999999";
		System.out.println(new BigInteger("59129999.99999999"));
		if (result.contains(".")) {
			System.out.println(result.split("\\.")[0]);
			System.out.println(result.split("\\.")[1]);
		}
	}*/

}
