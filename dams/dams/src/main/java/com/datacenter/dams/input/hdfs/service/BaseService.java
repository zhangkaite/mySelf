package com.datacenter.dams.input.hdfs.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.datacenter.dams.input.hdfs.entity.StatisticsDataEntity;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.dams.util.JsonUtil;

public abstract class BaseService {

	private static Logger logger = LogManager.getLogger(BaseService.class);

	private JmsTemplate jmsTemplate;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	// 公共方法hdfs文件读取
	public List<String> getHdfsData(String dataPath) throws Exception {
		List<String> dataList = null;
		try {
			dataList = HadoopFSOperations.readHdfsDate(dataPath);
			Map<String, BigDecimal> dataMap = analysisHdfsDatas(dataList);
			pushDataToMqQueue(dataMap);
		} catch (Exception e) {
			logger.error("从hdfs读取文件失败，失败的原因是:", e);
		}
		return dataList;
	}

	public abstract Map<String, BigDecimal> analysisHdfsDatas(List<String> dataList) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public void pushDataToMqQueue(Map<String, BigDecimal> dataMap) throws Exception {
		Set<String> keySet = dataMap.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			BigDecimal value = dataMap.get(key);
			StatisticsDataEntity statisticsDataEntity = new StatisticsDataEntity();
			statisticsDataEntity.setDataType(key);
			statisticsDataEntity.setSumData(value);
			statisticsDataEntity.setTime(DateUtil.getQueryFixedTime(new Date(), 1, -1) );
			send(JsonUtil.getObjectToJson(statisticsDataEntity));
		}
	}

	public void send(final String message) throws Exception {
		logger.debug("推送到SDMS系统[[" + ConsumeSpendConstant.ACTIVEMQTBTQQUEUE + "]]队列,数据是:" + message);
		try {
			jmsTemplate.send(ConsumeSpendConstant.ACTIVEMQTBTQQUEUE, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			throw e;
		}
	}

}
