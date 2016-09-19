package com.datacenter.dams.input.hdfs.service;

import org.junit.Test;

import com.datacenter.dams.util.ConsumeSpendConstant;

public class HdfsConsumeServiceTest {

	@Test
	public void test() {
		try {
			HdfsConsumeService.getTbTqConsumeService().getHdfsData(ConsumeSpendConstant.HDFSSPENDINGPATH+20160314);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
