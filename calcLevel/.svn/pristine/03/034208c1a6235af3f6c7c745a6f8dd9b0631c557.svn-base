package com.ttmv.datacenter.da.storm.calcLevel.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.common.domain.ConSumeEntity;
import com.ttmv.datacenter.da.storm.common.util.JsonUtil;

import backtype.storm.task.OutputCollector;

/**
 * Created by zbs on 16/2/22.
 */
public class TdouIntegralService extends Service {
	private static Logger logger = Logger.getLogger(TdouIntegralService.class);
    private Integer userRule;
    private Integer starRule;

    public TdouIntegralService(OutputCollector outputCollector) throws Exception {
        super(outputCollector);
    }

    @Override
    protected boolean verify(Map<String, Integer> rules) {
    	if (null==starRule||null==userRule) {
			return false;
		}
        return true;
    }

    @Override
    protected void getRule(Map<String, Integer> rules) {
        starRule =  rules.get("starTq")!=null?Integer.valueOf(rules.get("starTq")):null;
        userRule =  rules.get("userTq")!=null?Integer.valueOf(rules.get("userTq")):null;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    protected List<Object> headle(Object object) throws JSONException {
        if (object == null )
            return null;
        logger.debug("[[一级bolt]]获取的消费数据："+object.toString());
        String data = object.toString();
        List<Object> result = new ArrayList<Object>();
		try {
			JSONArray jsonArray=new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				ConSumeEntity conSumeEntity = (ConSumeEntity) JsonUtil.getObjectFromJson(jsonArray.get(i).toString(), ConSumeEntity.class);
				BigDecimal initAnchorNum = new BigDecimal(0);
				BigDecimal initUserNum = new BigDecimal(0);
				BigDecimal initNum = new BigDecimal(0);
				// 2、判断消费类型为TB消费还是TQ消费
				if (null != conSumeEntity.getTb()) {
					BigDecimal tbConsume = new BigDecimal(conSumeEntity.getTb()).multiply(new BigDecimal(1000));
					initNum = tbConsume;
				}
				if (null != conSumeEntity.getTq()) {
					BigDecimal tqConsume = new BigDecimal(conSumeEntity.getTq());
					initNum = tqConsume;
				}

				// 3、计算主播、用户的经验值
				String anchorID = conSumeEntity.getSpendToId();
				initAnchorNum = new BigDecimal(starRule).multiply(initNum);
				Map anchorMap = new HashMap();
				anchorMap.put(Constant.ANCHORFLAG+"_"+anchorID+"_"+conSumeEntity.getTime(), initAnchorNum);
				result.add(anchorMap);
				String userID = conSumeEntity.getSpendId();
				initUserNum = new BigDecimal(userRule).multiply(initNum);
				Map userMap = new HashMap();
				userMap.put(Constant.USERFLAG+"_"+userID+"_"+conSumeEntity.getTime(), initUserNum);
				result.add(userMap);
			}
			
		}catch(Exception e){
			logger.error("[[一级bolt]]对获取的消费数据json解析失败，失败的原因是：" , e);
		}
        return result;
    }

}
