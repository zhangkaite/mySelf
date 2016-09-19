package com.datacenter.dams.input.redis.worker.BusinessStatistics;

import com.datacenter.dams.input.redis.entity.UserLoginEntity;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zkt on 2016/4/5.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserLoginStatistics extends AbstractBusStatisticsService {

	private static Logger logger = LogManager.getLogger(UserLoginStatistics.class);

	private String statisticHdfsPath;
	private String queueName;
	private String date;
	private String busType;
	private String dataType;

	public void setParams(String statisticHdfsPath, String queueName, String date, String busType, String dataType) {
		this.statisticHdfsPath = statisticHdfsPath;
		this.queueName = queueName;
		this.date = date;
		this.busType = busType;
		this.dataType = dataType;
	}

	@Override
	public void doBusStatistics() {
		Map dataMap = null;
		try {
			dataMap = readStatisticsHdfsData(statisticHdfsPath);
		} catch (Exception e) {
			logger.error("读取hdfs上" + statisticHdfsPath + "运营用户登录业务统计计算结果数据失败，失败的原因是：", e);
			return;
		}
		if (null != dataMap && dataMap.size() > 0) {
			try {
				sendDataToMQ(queueName, dataMap, date, 0);
			} catch (Exception e) {
				logger.error("运营用户登录业务统计结果推送dams MQ失败，失败的原因是:", e);
			}
		}

	}

	@Override
	public Map readStatisticsHdfsData(String statisticHdfsPath) throws Exception {
		List<String> ls = HadoopFSOperations.readHdfsDate(statisticHdfsPath);
		Map resultMap = getData(ls);
		return resultMap;
	}

	@Override
	public void sendDataToMQ(String queueName, Map resultData, String date, Integer num) throws Exception {
		List ls = new ArrayList();
		for (Object key : resultData.keySet()) {
			UserLoginEntity userLoginEntity = new UserLoginEntity();
			userLoginEntity.setClientType(key.toString());
			userLoginEntity.setNumber(resultData.get(key).toString());
			userLoginEntity.setType(busType);
			userLoginEntity.setTime(date);
			userLoginEntity.setDataType(dataType);
			ls.add(userLoginEntity);
		}
		SendActiveMqMessage(queueName, JsonUtil.getObjectToJson(ls));
		logger.info("运营用户" + busType + "统计向SDMS ActiveMQ队列名称:" + queueName + "推送消息,内容:" + JsonUtil.getObjectToJson(ls));
	}

	

	public Map getData(List<String> ls) throws Exception {
		Map resultMap = new HashMap();
		for (String data : ls) {
			String[] datas = data.split("\\s+");
			String key = datas[0];
			String value = datas[1];
			resultMap.put(key, value);
		}
		return resultMap;

	}

}
