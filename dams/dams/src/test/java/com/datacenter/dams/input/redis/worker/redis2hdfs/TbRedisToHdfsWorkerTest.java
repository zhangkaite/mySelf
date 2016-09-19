package com.datacenter.dams.input.redis.worker.redis2hdfs;

import java.util.Date;

import org.junit.Test;

import com.datacenter.dams.util.ConsumeSpendConstant;

public class TbRedisToHdfsWorkerTest {

	@Test
	public void test() {
		TbRedisToHdfsWorker tbRedisToHdfsWorker=new TbRedisToHdfsWorker();
		//ConsumeSpendConstant.DAYSTATISTIC, ConsumeSpendConstant.STATISTICUSERDAYHBASEPATH,
		//hadoop jar /opt/countSpending.jar countStarSpending /datacenter/payment/score/end_day_star_score_20160316 /datacenter/payment/spending/spend_20160316
		//hadoop jar /opt/countSpending.jar countSpending /datacenter/payment/score/end_day_score_20160316 /datacenter/payment/spending/spend_20160316
		String command=tbRedisToHdfsWorker.getCommand(ConsumeSpendConstant.DAYSTATISTIC, ConsumeSpendConstant.STATISTICUSERDAYHBASEPATH,
					new Date());
		System.out.println(command);
	}

}
