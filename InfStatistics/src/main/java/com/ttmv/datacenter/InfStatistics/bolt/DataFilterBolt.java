package com.ttmv.datacenter.InfStatistics.bolt;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ttmv.datacenter.InfStatistics.util.Constants;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/***
 * 数据过滤bolt 根据日志埋点过滤需要的日志
 * @author kate
 *
 */
@SuppressWarnings({"serial","rawtypes"})
public class DataFilterBolt extends BaseRichBolt {

	private static Logger logger = Logger.getLogger(DataFilterBolt.class);
	private OutputCollector collector;

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;

	}

	public void execute(Tuple input) {
		String word = (String) input.getValue(0);
		if (word.contains("日志监控数据埋点")) {
			String[] jsonData = word.split("@@");
			logger.info("DataFilterBolt Filter after Data" + jsonData[1]);
			collector.emit(new Values(jsonData[1]));
		}

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields(Constants.FILTER_FIELD_NAME));
	}

}
