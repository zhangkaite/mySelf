package com.datacenter.dams.input.redis.worker.util;

import org.junit.Test;

public class HadoopFSOperationsTest {

	@Test
	public void test() {
		try {
			// HadoopFSOperations.readHdfsDate(ConsumeSpendConstant.HDFSSPENDINGPATH+"20160314");
			HadoopFSOperations.createNewHDFSFile("/test/aaa",
					"{\"roomID\":\"111\",\"userID\":5,\"destinationUserID\":5,\"productID\":\"1\",\"productCount\":66,\"productPrice\":\"1000\",\"number\":\"100\",\"time\":\"1458038126\",\"orderId\":\"test123456789\",\"clientType\":\"1\",\"version\":\"v3.1\" ,\"userType\":\"1\",\"dataType\":\"tb_consume\" }");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
