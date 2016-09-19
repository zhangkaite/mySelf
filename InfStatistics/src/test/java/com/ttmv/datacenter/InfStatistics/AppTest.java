package com.ttmv.datacenter.InfStatistics;

import java.math.BigDecimal;
import java.math.BigInteger;

import clojure.main;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest  {
	public static void main(String[] args) {
		BigInteger succNum = new BigInteger("6429213");
		BigInteger sumNum = new BigInteger("6434326");
		
		System.out.println(succNum.doubleValue()/sumNum.doubleValue());
	}

}
