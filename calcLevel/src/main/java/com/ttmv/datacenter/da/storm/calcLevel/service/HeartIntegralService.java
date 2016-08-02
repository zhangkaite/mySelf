package com.ttmv.datacenter.da.storm.calcLevel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.common.domain.HeartEntity;
import com.ttmv.datacenter.da.storm.common.util.JsonUtil;

import backtype.storm.task.OutputCollector;

/**
 * Created by zbs on 16/2/22.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class HeartIntegralService extends Service {
	private static Logger logger = Logger.getLogger(HeartIntegralService.class);
	private Integer userRule;
	private Integer starRule;

	public HeartIntegralService(OutputCollector outputCollector) throws Exception {
		super(outputCollector);
	}

	@Override
	protected boolean verify(Map<String, Integer> rules) {
		return true;
	}

	@Override
	protected void getRule(Map<String, Integer> rules) {
		starRule = (Integer) rules.get("starHeart");
		userRule = (Integer) rules.get("usetHeart");
	}

	@Override
	protected List<Object> headle(Object object) {
		if (object == null )
			return null;
		String data =object.toString();
		logger.debug("[[一级bolt]]获取的点心数据:"+data);
		BigDecimal initAnchorNum = new BigDecimal(0);
		BigDecimal initUserNum = new BigDecimal(0);
		List<Object> result = new ArrayList();
		try {
			JSONArray jsonArray=new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
			HeartEntity heartEntity = (HeartEntity) JsonUtil.getObjectFromJson(jsonArray.get(i).toString(), HeartEntity.class);
			String anchorID = heartEntity.getSpendToId();
			String userID = heartEntity.getSpendId();
			initAnchorNum = new BigDecimal(starRule).multiply(new BigDecimal(heartEntity.getSum()));
			Map anchorMap = new HashMap();
			anchorMap.put(Constant.ANCHORFLAG+"_"+anchorID+"_"+heartEntity.getTime(), initAnchorNum);
			result.add(anchorMap);
			initUserNum = new BigDecimal(userRule).multiply(new BigDecimal(heartEntity.getSum()));
			Map userMap = new HashMap();
			userMap.put(Constant.USERFLAG+"_"+userID+"_"+heartEntity.getTime(), initUserNum);
			result.add(userMap);
			}
		} catch (Exception e) {
			logger.error("[[一级bolt]]对获取的送心数据json解析失败，失败的原因是：" , e);
		}
		return result;
	}

}
