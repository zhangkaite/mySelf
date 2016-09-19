package com.ttmv.datacenter.paycenter.test;

import java.util.Random;

public class testBigDecimal {

	public static void main(String[] args) {
		/*BigDecimal a = new BigDecimal(21);
		BigDecimal b = new BigDecimal(12);
		BigDecimal c = a.subtract(b);
		System.out.println(c);*/
		Random random = new Random();
		int a =(int) (random.nextDouble()*100000);
		System.out.println(a);
	}
}
