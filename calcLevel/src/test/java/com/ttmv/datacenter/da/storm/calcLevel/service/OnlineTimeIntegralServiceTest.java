package com.ttmv.datacenter.da.storm.calcLevel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;

public class OnlineTimeIntegralServiceTest {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void test() {
		String object ="[{\"star\":[1,2,3],\"user\":null,\"step\":1,\"time\":1234567890}]";
		String data = object.toString();
		System.out.println("[[RedisDataLevelBolt]]获取的在线时长数据：" + data);
		List<Object> result = new ArrayList<Object>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			JSONObject jsonData = new JSONObject(jsonArray.get(0).toString());
			Object star=jsonData.get("star");
			Object user=jsonData.get("user");
			JSONArray startArray =null;
			JSONArray userArray =null;
			if (null!=star&&star instanceof JSONArray) {
				 startArray = jsonData.getJSONArray("star");
			}
			if (null!=user&&user instanceof JSONArray) {
				userArray = jsonData.getJSONArray("user");
			}
			
			Integer step = jsonData.getInt("step");
			Long time = jsonData.getLong("time");
			if (null != startArray && !"null".equals(startArray)) {
				Map anchorMap = new HashMap();
				for (int i = 0; i < startArray.length(); i++) {
					anchorMap.put(Constant.ANCHORFLAG + "_" + startArray.get(i) + "_" + time,
							new BigDecimal(step).multiply(new BigDecimal(10)));
				}
				result.add(anchorMap);
			}

			if (null != userArray && !"null".equals(userArray)) {
				Map userMap = new HashMap();
				for (int i = 0; i < userArray.length(); i++) {
					userMap.put(Constant.USERFLAG + "_" + userArray.get(i) + "_" + time,
							new BigDecimal(step).multiply(new BigDecimal(10)));
				}
				result.add(userMap);
			}
		} catch (JSONException e) {
			System.out.println("[[RedisDataLevelBolt]]获取的在线时长数据 json解析失败，失败的原因是:" + e);
		}
	}

}
