package com.ttmv.datacenter.da.storm.calcLevel.spout;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.common.util.RedisUtil;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/***
 * 
 * @author kate 从redis队列里面获取T币、T券、点心、鲜花、主播在线时长数据、用户在线时长
 *
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class GetRedisDataSpout extends BaseRichSpout {

	private static Logger logger = Logger.getLogger(GetRedisDataSpout.class);
	private SpoutOutputCollector collector;

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	/**
	 * 循环从各个redis队列获取数据，并将获取的数据发射到不同的bolt进行计算处理
	 * 
	 */
	@Override
	public void nextTuple() {
		Utils.sleep(Constant.STORMSLEEPTIME);
		try {
			List<Object> tdouList = RedisUtil.getValueBatch(Constant.TBREDISQUEUENAME, Constant.TDOUNUM);
			List<Object> heartList = RedisUtil.getValueBatch(Constant.HEARTREDISQUEUENAME, Constant.HEARTNUM);
			List<Object> flowerList = RedisUtil.getValueBatch(Constant.FLOWERREDISQUEUENAME, Constant.FLOWERNUM);
			List<Object> onlineList = RedisUtil.getValueBatch(Constant.ANCHORONLINEREDISQUEUENAME, Constant.ONLINENUM);
			if (tdouList != null && tdouList.size() > 0) {
				collector.emit(new Values(Constant.TDOUTYPE,tdouList));
				logger.info("[[一级spout]]发射消费数据到指定的bolt");
			}
			if (heartList != null && heartList.size() > 0) {
				collector.emit(new Values(Constant.HEARTTYPE, heartList));
				logger.info("[[一级spout]]发射心跳数据到指定的bolt");
			}
			if (flowerList != null && flowerList.size() > 0) {
				
				collector.emit(new Values(Constant.FLOWERTYPE, flowerList));
				logger.info("[[一级spout]]发射鲜花数据到指定的bolt");
			}
			if (onlineList != null && onlineList.size() > 0) {
				
				collector.emit(new Values(Constant.ONLINETYPE, onlineList));
				logger.info("[[一级spout]]发射主播在线数据到指定的bolt");
			}

		} catch (Exception e) {
			logger.error("[[GetRedisDataSpout]]从redis里获取数据失败，失败的原因是:" , e);
		}

	}

	/**
	 * 声明要发射的不同字段的数据，对应不同的bolt处理
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("dataType", "dataList"));
	}

}
