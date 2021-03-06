package com.ttmv.datacenter.InfStatistics.util;

import java.util.List;

import org.apache.log4j.Logger;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

@SuppressWarnings("serial")
public class MessageScheme implements Scheme {

	private static Logger logger = Logger.getLogger(MessageScheme.class);

	public List<Object> deserialize(byte[] bytes) {
		try {
			String msg = new String(bytes, "UTF-8");
			// logger.info("get one message is" + msg);
			return new Values(msg);
		} catch (Exception e) {
			logger.error("Cannot parse the provided message!");
		}
		return null;
	}

	public Fields getOutputFields() {
		return new Fields("msg");
	}
}
