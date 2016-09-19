package com.ttmv.datacenter.InfStatistics.bolt;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ttmv.datacenter.InfStatistics.service.StoreSumDataService;
import com.ttmv.datacenter.InfStatistics.util.Constants;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

@SuppressWarnings("serial")
public class SendReqStatusBolt extends BaseRichBolt {
	private static Logger logger = Logger.getLogger(SendReqStatusBolt.class);
	@SuppressWarnings("unused")
	private OutputCollector collector;

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple tuple) {
		try {
			String interfJson = tuple.getStringByField(Constants.SEND_REQSTATUS_BOLT);
			// 统计接口请求总量
			long time1 = System.currentTimeMillis();
			StoreSumDataService.sendReqStatus(interfJson,"");
			long time2 = System.currentTimeMillis();
			long period = time2 - time1;
			logger.info("SendReqStatusBolt 完成一次业务花费的时间:" + period);
		} catch (Exception e) {
			logger.error("json串解析失败", e);
			// collector.fail(tuple);
		}

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

}
