package com.datacenter.dams.input.redis.worker.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.junit.Test;

import com.datacenter.dams.input.hbase.entity.BillInfoEntity;
import com.datacenter.dams.util.JsonUtil;

public class HbaseUtilTest {

	@Test
	public void test() throws Exception {
		Configuration config = HBaseConfiguration.create();
		// HbaseUtil.createTable(config,"HbaseOperationInfo","operation_data");
		String data = "{\"userID\":3454,\"destinationUserID\":3456,\"roomID\":19999,\"time\":\"1464002924\",\"productID\":1003100000000000001,\"productCount\":1,\"productPrice\":50,\"number\":50,\"orderId\":\"4400146400292401542\",\"clientType\":\"6\",\"version\":\"1.0\",\"dataType\":\"tb_consume\"}";
		System.out.println(JsonUtil.getObjectToJson(data));
		try {

			HbaseUtil.addRow("HbaseOperationInfo", "146396941000023454", "operation_data", "operation_data", data, config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test2() throws Exception {

		Configuration config = HBaseConfiguration.create();
		 HbaseUtil.getRowsByStartEndTime("HbaseOperationInfo", "1463932800", "1464019199", config);
		

	}

	
}
