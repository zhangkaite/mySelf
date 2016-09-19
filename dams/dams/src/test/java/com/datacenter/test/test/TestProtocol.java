package com.datacenter.test.test;

import java.math.BigInteger;
import java.util.Map;

import org.junit.Test;

import com.datacenter.dams.util.JsonUtil;
import com.datacenter.domain.protocol.da.Present;
import com.datacenter.domain.protocol.da.StarActivityInfo;

public class TestProtocol {

	@Test
	@SuppressWarnings("rawtypes")
	public void testProtocol() throws Exception{
		String json ="{\"userID\": 100,\"ranking\": 1,\"activityID\": 22,\"levelLogoPC\":\"a.jpg\",\"levelLogoMC\":\"b.jpg\",\"score\": "+1000+",\"levelName\":\"第一关\",\"Gifts\":"+
				"[{\"productID\": 1,\"sum\": 98,\"limitSum\": 100},{\"productID\": 2,\"sum\": 80,\"limitSum\": 100}]}";
		Map star = (Map) JsonUtil.getObjectFromJson(json, Map.class);
		System.out.println(star.get("Gifts"));
	}
	
	@Test
	public void testShowProtocol()throws Exception{
		StarActivityInfo info = new StarActivityInfo();
		Present p = new Present();
		info.setActivityID(new BigInteger("55"));
		p.setLimitSum(10);
		info.Gifts.add(p);
		System.out.println(JsonUtil.getObjectToJson(info));
	}
}
