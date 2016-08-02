package com.ttmv.datacenter.da.storm.calcLevel.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ttmv.datacenter.da.storm.common.util.Number2StringUtil;

import junit.framework.TestCase;

public class LevelHbaseDataServiceTest extends TestCase {
	
	
	
	public void test(){
		
		
	}

	public void testHttpRequest() {
		LevelHbaseDataService LevelHbaseDataService = new LevelHbaseDataService();
		LevelHbaseDataService.getLevelConfigData("star", new BigInteger("80000"));
	}

	public void calHbaseData() {
		LevelHbaseDataService LevelHbaseDataService = new LevelHbaseDataService();
		try {
			LevelHbaseDataService.calHbaseData("user_10000_1456392845", Number2StringUtil.numberToString("59129999.99999999"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void calCurrentLevel() {
		LevelHbaseDataService LevelHbaseDataService = new LevelHbaseDataService();
		LevelHbaseDataService.calCurrentLevel("user_126331_1456392845");
	}

	public void pushLevelDataToDams() {

		LevelHbaseDataService LevelHbaseDataService = new LevelHbaseDataService();
		try {
			/*LevelHbaseDataService.pushLevelDataToDams(new BigInteger("88888"), new BigInteger("5"), new BigInteger("1"),
					"star");*/
			LevelHbaseDataService.pushLevelDataToIm(new BigInteger("88888"), new BigInteger("5"), new BigInteger("1"),
					new BigInteger("100000"), "star");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
