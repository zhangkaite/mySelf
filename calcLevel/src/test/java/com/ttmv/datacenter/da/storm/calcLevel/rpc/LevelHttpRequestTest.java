package com.ttmv.datacenter.da.storm.calcLevel.rpc;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.common.util.JsonUtil;

import junit.framework.TestCase;

public class LevelHttpRequestTest extends TestCase {

	public void TestHttpRequest() throws Exception {
		LevelHttpRequest LevelHttpRequest = new LevelHttpRequest();
		String responseData = LevelHttpRequest.getLevelData();
		JSONObject json = new JSONObject(responseData);
		String resultCode = json.getString("resultCode");
		if (Constant.RESULTCODE_SUCCESS.equals(resultCode)) {
			JSONObject jsonObject = json.getJSONObject("resData");
			// 将responseData解析成Map
			Map responseMap = new HashMap();
			responseMap = (Map) JsonUtil.getObjectFromJson(jsonObject.toString(), Map.class);
			System.out.println("hello world");
			/*responseMap.put("usetHeart", jsonObject.getInt("usetHeart"));
			responseMap.put("starHeart", jsonObject.getInt("starHeart"));
			responseMap.put("userTq", jsonObject.getInt("userTq"));
			responseMap.put("starTq", jsonObject.getInt("starTq"));
			responseMap.put("userFlowers", jsonObject.getInt("userFlowers"));
			responseMap.put("starOnlineTime", jsonObject.getInt("starOnlineTime"));
			responseMap.put("userOnlineTime", jsonObject.getInt("userOnlineTime"));
			responseMap.put("starFlowers", jsonObject.getInt("starFlowers"));*/
		}
	}

}
