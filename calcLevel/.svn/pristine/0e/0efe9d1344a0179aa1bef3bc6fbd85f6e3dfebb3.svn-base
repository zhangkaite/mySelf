package com.ttmv.datacenter.da.storm.calcLevel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.common.domain.FlowerEntity;
import com.ttmv.datacenter.da.storm.common.util.JsonUtil;

import backtype.storm.task.OutputCollector;

/**
 * Created by zbs on 16/2/22.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FlowerIntegralService extends Service {
	private static Logger logger = Logger.getLogger(FlowerIntegralService.class);
	private Integer userRule;
	private Integer starRule;

	public FlowerIntegralService(OutputCollector outputCollector) throws Exception {
		super(outputCollector);
	}

	@Override
	protected boolean verify(Map<String, Integer> rules) {
		if (null == starRule || null == userRule) {
			return false;
		}
		return true;
	}

	@Override
	protected void getRule(Map<String, Integer> rules) {
		starRule = rules.get("starTq") != null ? Integer.valueOf(rules.get("starFlowers")) : null;
		userRule = rules.get("userTq") != null ? Integer.valueOf(rules.get("userFlowers")) : null;
	}

	
	@Override
	protected List<Object> headle(Object object) throws JSONException {
		if (object == null )
			return null;
		String data = object.toString();
		logger.debug("[[一级bolt]]获取送花数据:"+data);
		BigDecimal initAnchorNum = new BigDecimal(0);
		BigDecimal initUserNum = new BigDecimal(0);
		List<Object> result = new ArrayList();
		try {
			JSONArray jsonArray=new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
			FlowerEntity flowerEntity = (FlowerEntity) JsonUtil.getObjectFromJson(jsonArray.get(i).toString(), FlowerEntity.class);
			String anchorID = flowerEntity.getSpendToId();
			initAnchorNum = new BigDecimal(starRule).multiply(new BigDecimal(flowerEntity.getSum()));
			Map anchorMap = new HashMap();
			anchorMap.put(Constant.ANCHORFLAG+"_"+anchorID+"_"+flowerEntity.getTime(), initAnchorNum);
			result.add(anchorMap);
			String userID = flowerEntity.getSpendId();
			initUserNum = new BigDecimal(userRule).multiply(new BigDecimal(flowerEntity.getSum()));
			Map userMap = new HashMap();
			userMap.put(Constant.USERFLAG+"_"+userID+"_"+flowerEntity.getTime(), initUserNum);
			result.add(userMap);
			}
		} catch (Exception e) {
			logger.error("[[一级bolt]]对获取的刷花数据json解析失败，失败的原因是：" , e);
		}

		return result;
	}

}
