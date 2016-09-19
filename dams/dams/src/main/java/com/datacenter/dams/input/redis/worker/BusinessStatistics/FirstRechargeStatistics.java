package com.datacenter.dams.input.redis.worker.BusinessStatistics;

import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zkt on 2016/3/29.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FirstRechargeStatistics extends AbstractBusStatisticsService {
	private static Logger logger = LogManager.getLogger(FirstRechargeStatistics.class);
	private String historyHdfsPath;
	private String statisticHdfsPath;
	private String targetHdfsPath;
	private String queueName;
	private String date;
	private String hbaseTableName;

	public void setParams(String historyHdfsPath, String statisticHdfsPath, String targetHdfsPath, String queueName,
			String date, String hbaseTableName) {
		this.historyHdfsPath = historyHdfsPath;
		this.statisticHdfsPath = statisticHdfsPath;
		this.targetHdfsPath = targetHdfsPath;
		this.queueName = queueName;
		this.date = date;
		this.hbaseTableName = hbaseTableName;
	}

	@Override
	public void doBusStatistics() {
		List ls = null;
		Map dataMap = null;
		try {
			ls = readHistoryHdfsData(historyHdfsPath);
		} catch (Exception e) {
			logger.error("读取hdfs上" + historyHdfsPath + "运营首充业务统计历史数据失败，失败的原因是：", e);
			return;
		}
		try {
			dataMap = readStatisticsHdfsData(statisticHdfsPath);
		} catch (Exception e) {
			logger.error("读取hdfs上" + statisticHdfsPath + "运营首充业务统计计算结果数据失败，失败的原因是：", e);
			return;
		}

		if (null != dataMap && dataMap.size() > 0) {
			for (Object data : ls) {
				if (dataMap.containsKey(data)) {
					dataMap.remove(data);
				}
			}
			try {
				sendDataToMQ(queueName, dataMap, date, 0);

			} catch (Exception e) {
				logger.error("运营首充业务统计结果推送dams MQ失败，失败的原因是:", e);
			}

			try {
				insertDataToHbase(dataMap, date, hbaseTableName);
			} catch (Exception e) {
				logger.error("运营业务统计结果写入hbase失败，失败的原因是:", e);
			}

			for (Object data : ls) {
				dataMap.put(data, "0");
			}
			try {
				writeDataToHdfs(targetHdfsPath, dataMap);
			} catch (Exception e) {
				logger.error("运营业务统计结果数据写入hdfs路径" + targetHdfsPath + "失败，失败的原因是:", e);
			}
		}
	}

	@Override
	public Map readStatisticsHdfsData(String statisticHdfsPath) throws Exception {
		List<String> ls = HadoopFSOperations.readHdfsDate(statisticHdfsPath);
		Map resultMap = getHbaseStoreData(ls, ConsumeSpendConstant.FISTERRECHARGE);
		return resultMap;
	}

	@Override
	public void sendDataToMQ(String queueName, Map resultData, String date, Integer num) throws Exception {
		BigDecimal initData = new BigDecimal(0);
		for (Object key : resultData.keySet()) {
			BigDecimal currentData = new BigDecimal(String.valueOf(resultData.get(key.toString())).split("_")[0]);
			initData = initData.add(currentData);
		}
		Map postMap = new HashMap();
		postMap.put("size", String.valueOf(resultData.size()));
		postMap.put("number", String.valueOf(initData));
		postMap.put("time", date);
		postMap.put("type", "SC");
		SendActiveMqMessage(queueName, JsonUtil.getObjectToJson(postMap));
		logger.info("运营首充统计向SDMS ActiveMQ队列名称:" + queueName + "推送消息,内容:" + JsonUtil.getObjectToJson(postMap));
	}

}
