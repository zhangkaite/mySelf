package com.ttmv.datacenter.da.storm.calcLevel.bolt;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.calcLevel.rpc.LevelHttpRequest;
import com.ttmv.datacenter.da.storm.calcLevel.service.FlowerIntegralService;
import com.ttmv.datacenter.da.storm.calcLevel.service.HeartIntegralService;
import com.ttmv.datacenter.da.storm.calcLevel.service.OnlineTimeIntegralService;
import com.ttmv.datacenter.da.storm.calcLevel.service.Service;
import com.ttmv.datacenter.da.storm.calcLevel.service.TdouIntegralService;
import com.ttmv.datacenter.da.storm.common.util.JsonUtil;
import com.ttmv.datacenter.da.storm.common.util.RedisUtil;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

@SuppressWarnings({ "serial", "rawtypes","unchecked" })
public class RedisDataLevelBolt extends BaseRichBolt {

	private static Logger logger = Logger.getLogger(RedisDataLevelBolt.class);
	private OutputCollector collector;

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;

	}

	public void execute(Tuple tuple) {
		String dataType = tuple.getStringByField("dataType");
		logger.debug("[[一级bolt]]从spout获取的数据类型是:"+dataType);
		List<Object> dataList=null;
		try {
		    dataList = tuple.select(new Fields("dataList"));
		} catch (Exception e) {
			logger.error("[[一级bolt]] 获取dataList失败,失败的原因是:",e);
		}
		
		/*for (Object object : dataList) {
			logger.debug("[[一级bolt]]从spout获取的数据："+object.toString());
		}*/
		// http请求获取不同的业务对应的不同经验值
		String responseData = LevelHttpRequest.getLevelData();
		try {
			JSONObject	json = new JSONObject(responseData);
			String resultCode = json.getString("resultCode");
			if (Constant.RESULTCODE_SUCCESS.equals(resultCode)) {
				JSONObject jsonObject = json.getJSONObject("resData");
				logger.debug("[[一级bolt]]从ocms获取的等级计算规则数据:"+jsonObject.toString());
				
				// 将responseData解析成Map
				Map responseMap =null;
				try {
					responseMap = (Map) JsonUtil.getObjectFromJson(jsonObject.toString(), Map.class);
					if (Constant.TDOUTYPE.equals(dataType)) {
						Service tdouIntegralService=new TdouIntegralService(collector);
						tdouIntegralService.perform(dataList, responseMap);
					} else if (Constant.FLOWERTYPE.equals(dataType)) {
						Service flowerIntegralService=new FlowerIntegralService(collector);
						flowerIntegralService.perform(dataList, responseMap);
					} else if (Constant.HEARTTYPE.equals(dataType)) {
						Service heartIntegralService=new HeartIntegralService(collector);
						heartIntegralService.perform(dataList, responseMap);
					} else if (Constant.ONLINETYPE.equals(dataType)) {
						Service onlineTimeIntegralService=new OnlineTimeIntegralService(collector);
						onlineTimeIntegralService.perform(dataList, responseMap);
					}else {
						logger.warn("[[一级bolt]]没有该类型的数据");
					}
					collector.ack(tuple);
				} catch (Exception e) {
					logger.error("[[一级bolt]]对获取的数据进行计算失败，失败的原因是:" , e);
				}
			}else{
				logger.error("[[一级bolt]]http请求ocms返回的规则计算数据ERROR500");
				for (Object object : dataList) {
					try {
						logger.info("[[一级bolt]]因ocms系统异常未处理的数据:"+object.toString());
					} catch (Exception e) {
						logger.error("[[一级bolt]]http请求ocms返回的规则计算数据解析失败");
					}
				}
			}
		} catch (Exception e1) {
			logger.error("[[一级bolt]]http请求ocms返回的规则计算数据解析失败,失败的原因是：",e1);
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("datasList"));
	}

}
