package com.ttmv.datacenter.agent.lockcenter.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月30日
 */
public class HbaseAgent {
	
	private static final Logger logger = LogManager.getLogger(HbaseAgent.class);
	
	private static final String tableName = "lock";
	Configuration configuration;

	public HbaseAgent(String host, String port) {
		configuration = HBaseConfiguration.create();	
		configuration.set("hbase.zookeeper.property.clientPort", "2181");
		configuration.set("hbase.zookeeper.quorum", host);
		configuration.set("hbase.master", host+":"+port);
	}

	// 是否在hb上存在
	public boolean exists(String key) {
		try {
			HTable table = new HTable(configuration, tableName);
			Get scan = new Get(key.getBytes()); //根据什么查询
			Result r = table.get(scan);
			return r.size() > 0 ? true : false; 
		} catch (Exception e) {
			logger.error("Query hbase failed. \n"+e);
			return true;
		}
	}

	// 在hbase上删除。
	public boolean deleteData(String key) {
		try{
			HTable table = new HTable(configuration,tableName);
			Delete deleteRow = new Delete(key.getBytes());
			table.delete(deleteRow);
			return true;
		}catch(Exception e){
			logger.error("Delete failed from hbase. \n"+e);
			return false;
		}
	}
	
	
}
