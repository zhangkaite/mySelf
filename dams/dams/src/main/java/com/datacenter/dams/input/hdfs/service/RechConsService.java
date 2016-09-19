package com.datacenter.dams.input.hdfs.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.redis.worker.BusinessStatistics.AbstractBusStatisticsService;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.util.JsonUtil;

/***
 * 将TB日/月充值消费人数统计结果推送到sdms
 * 
 * @author kate
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RechConsService extends AbstractBusStatisticsService {
	private static Logger logger = LogManager.getLogger(RechConsService.class);
	private String statisticHdfsPath;
	private String queueName;
	private String date;
	private String dateType;

	public void setParams(String statisticHdfsPath, String queueName, String date, String dateType) {
		this.statisticHdfsPath = statisticHdfsPath;
		this.queueName = queueName;
		this.date = date;
		this.dateType = dateType;

	}

	@Override
	public void doBusStatistics() {
		Map dataMap = null;
		try {
			dataMap = readStatisticsHdfsData(statisticHdfsPath);
		} catch (Exception e) {
			logger.error("读取hdfs上" + statisticHdfsPath + "TB日/月充值消费人数统计结果数据失败，失败的原因是：", e);
			return;
		}
		if (null != dataMap && dataMap.size() > 0) {
			try {
				sendDataToMQ(queueName, dataMap, date, 0);
			} catch (Exception e) {
				logger.error("TB日/月充值消费人数统计结果推送Sdms MQ失败，失败的原因是:", e);
			}
		}
	}

	@Override
	public Map readStatisticsHdfsData(String statisticHdfsPath) throws Exception {
		List<String> ls = HadoopFSOperations.readHdfsDate(statisticHdfsPath);
		Map resultMap = getDataMap(ls);
		return resultMap;
	}

	@Override
	public void sendDataToMQ(String queueName, Map resultData, String date, Integer num) throws Exception {
		List ls = new ArrayList();
		for (Object key : resultData.keySet()) {
			Map dataMap=new HashMap();
			dataMap.put("busType", dateType);
			dataMap.put("sumData", new BigDecimal(resultData.get(key).toString()));
			dataMap.put("dataType", key.toString());
			dataMap.put("time", date);
			ls.add(dataMap);
		}
		SendActiveMqMessage(queueName, JsonUtil.getObjectToJson(ls));
		logger.info("运营TB日/月充值消费人数统计向SDMS ActiveMQ队列名称:" + queueName + "推送消息,内容:" + JsonUtil.getObjectToJson(ls));
	}

	public Map getDataMap(List<String> ls) throws Exception {
		Map resultMap = new HashMap();
		for (String data : ls) {
			String[] datas = data.split("\\s+");
			String key = datas[0];
			String value = datas[1].split("_")[1];
			resultMap.put(key, value);
		}
		return resultMap;
	}

}
