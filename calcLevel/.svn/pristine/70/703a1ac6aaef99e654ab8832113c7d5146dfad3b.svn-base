package com.ttmv.datacenter.da.storm.calcLevel.service;

import backtype.storm.task.OutputCollector;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zbs on 16/2/22.
 */
public class OnlineTimeIntegralService extends Service {
	private static Logger logger = Logger.getLogger(OnlineTimeIntegralService.class);
	private Integer userRule;
	private Integer starRule;

	public OnlineTimeIntegralService(OutputCollector outputCollector) throws Exception {
		super(outputCollector);
	}

	@Override
	protected boolean verify(Map<String, Integer> rules) {
		return true;
	}

	@Override
	protected void getRule(Map<String, Integer> rules) {
		starRule = (Integer) rules.get("starOnlineTime");
		userRule = (Integer) rules.get("userOnlineTime");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected List<Object> headle(Object object) {
		if (object == null)
			return null;
		String data = object.toString();
		logger.info("[[一级bolt]]获取的在线时长数据：" + data);
		List<Object> result = new ArrayList<Object>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(data);
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject jsonData = new JSONObject(jsonArray.get(j).toString());
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
								new BigDecimal(step).multiply(new BigDecimal(starRule)));
					}
					result.add(anchorMap);
				}

				if (null != userArray && !"null".equals(userArray)) {
					Map userMap = new HashMap();
					for (int i = 0; i < userArray.length(); i++) {
						userMap.put(Constant.USERFLAG + "_" + userArray.get(i) + "_" + time,
								new BigDecimal(step).multiply(new BigDecimal(userRule)));
					}
					result.add(userMap);
				}
			}
			
		} catch (JSONException e) {
			logger.error("[[一级bolt]]获取的在线时长数据 json解析失败，失败的原因是:" , e);
		}

		return result;
	}

}
