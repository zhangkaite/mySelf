package com.ttmv.datacenter.InfStatistics;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.ttmv.datacenter.InfStatistics.bolt.StoreReqSumBolt;
import com.ttmv.datacenter.InfStatistics.bolt.DataFilterBolt;
import com.ttmv.datacenter.InfStatistics.bolt.SendEveryReqSumBolt;
import com.ttmv.datacenter.InfStatistics.bolt.SendReqCurrentBolt;
import com.ttmv.datacenter.InfStatistics.bolt.SendReqStatusBolt;
import com.ttmv.datacenter.InfStatistics.bolt.SendReqSumBolt;
import com.ttmv.datacenter.InfStatistics.bolt.StoreEveryReqSumBolt;
import com.ttmv.datacenter.InfStatistics.bolt.StoreReqCurrent;
import com.ttmv.datacenter.InfStatistics.bolt.StoreReqStatusBolt;
import com.ttmv.datacenter.InfStatistics.util.Constants;
import com.ttmv.datacenter.InfStatistics.util.MessageScheme;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

public class Main {
	private static Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		BrokerHosts hosts = new ZkHosts("n1:2181,n2:2181,n3:2181");
		String topic_name = "infStatistics";
		String zkRoot = "/" + topic_name;
		SpoutConfig spoutConfig = new SpoutConfig(hosts, topic_name, zkRoot, UUID.randomUUID().toString());
		spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
		KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
		builder.setSpout(Constants.SPOUT_NAME, kafkaSpout, 3);
		builder.setBolt(Constants.DATA_FILTER_BOLT_NAME, new DataFilterBolt(), 3).shuffleGrouping(Constants.SPOUT_NAME);

		builder.setBolt(Constants.STORE_REQ_SUM_BOLT_NAME, new StoreReqSumBolt(), 9)
				.shuffleGrouping(Constants.DATA_FILTER_BOLT_NAME);
		builder.setBolt(Constants.STORE_REQ_STATUS_BOLT_NAME, new StoreReqStatusBolt(), 9)
				.shuffleGrouping(Constants.DATA_FILTER_BOLT_NAME);
		builder.setBolt(Constants.DATA_STORE_EVERY_REQSUM_BOLT_NAME, new StoreEveryReqSumBolt(), 9)
				.shuffleGrouping(Constants.DATA_FILTER_BOLT_NAME);
		builder.setBolt(Constants.DATA_STORE_EVERY_REQ_BOLT_NAME, new StoreReqCurrent(), 9)
				.shuffleGrouping(Constants.DATA_FILTER_BOLT_NAME);

		builder.setBolt(Constants.SEND_REQSUM_BOLT, new SendReqSumBolt(), 9).shuffleGrouping(Constants.STORE_REQ_SUM_BOLT_NAME);
		builder.setBolt(Constants.SEND_REQSTATUS_BOLT, new SendReqStatusBolt(), 9)
				.shuffleGrouping(Constants.STORE_REQ_STATUS_BOLT_NAME);
		builder.setBolt(Constants.SEND_EVERY_REQSUM_BOLT, new SendEveryReqSumBolt(), 9)
				.shuffleGrouping(Constants.DATA_STORE_EVERY_REQSUM_BOLT_NAME);
		builder.setBolt(Constants.SEND_EVERY_MIN_REQSUM_BOLT, new SendReqCurrentBolt(), 9)
				.shuffleGrouping(Constants.DATA_STORE_EVERY_REQ_BOLT_NAME);

		Config conf = new Config();
		conf.setNumWorkers(10);
		// conf.setMaxSpoutPending(5000);
		try {
			StormSubmitter.submitTopology("calcInterfReq", conf, builder.createTopology());
		} catch (AlreadyAliveException e) {
			logger.error("calcInterfReq 的topology 已存在" + e);
		} catch (InvalidTopologyException e) {
			logger.error("calcInterfReq 的topology 无效" + e);
		}

	}

}
