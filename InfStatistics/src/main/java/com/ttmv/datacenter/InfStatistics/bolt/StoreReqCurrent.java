package com.ttmv.datacenter.InfStatistics.bolt;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ttmv.datacenter.InfStatistics.service.StoreSumDataService;
import com.ttmv.datacenter.InfStatistics.util.Constants;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
@SuppressWarnings({ "rawtypes", "serial" })
public class StoreReqCurrent extends BaseRichBolt{

	private static Logger logger = Logger.getLogger(StoreReqCurrent.class);
	private OutputCollector collector;
	
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}
	@Override
	public void execute(Tuple tuple) {
		try {
			String interfJson = tuple.getStringByField(Constants.FILTER_FIELD_NAME);
			long time1 = System.currentTimeMillis();
			StoreSumDataService.addEveryInterfMinReqSum(interfJson);
			long time2 = System.currentTimeMillis();
			long period = time2 - time1;
			logger.info("StoreReqCurrent 完成一次业务花费的时间:" + period);
			collector.emit(new Values(interfJson));
		} catch (Exception e) {
			logger.error("每分钟接口调用情况统计hbase数据操作失败，失败的原因是:", e);
			//collector.fail(tuple);
		} 
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(Constants.SEND_EVERY_MIN_REQSUM_BOLT));
		
	}

}
