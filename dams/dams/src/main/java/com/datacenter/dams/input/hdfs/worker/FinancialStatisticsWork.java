package com.datacenter.dams.input.hdfs.worker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.hdfs.service.HdfsConsumeService;
import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.input.redis.worker.redis2hdfs.JobTypeEntity;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;
/***
 * 统计TB、TQ充值消费work
 * 从
 * @author kate
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class FinancialStatisticsWork extends QuartzWorker<Object> {
	private static Logger logger = LogManager.getLogger(FinancialStatisticsWork.class);
	private String redisConsumeQueue = ConsumeSpendConstant.REDISCONSUMEQUEUE;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	protected List getData(int num) {
		logger.debug("通过work获取mr统计结果已完成的业务");
		List<String> ls = null;
		try {
			ls = TbRedisService.getRedisQueueImpi().getValueBatch(redisConsumeQueue, 1);
		} catch (Exception e) {
			logger.error("从mr完成的redis队列获取数据失败，失败的原因是:", e);
		}
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		//调用三个统计业务
		String data = (String) obj;
		JobTypeEntity jobTypeEntity = (JobTypeEntity) JsonUtil.getObjectFromJson(data, JobTypeEntity.class);
		Date jobStatisticTime = jobTypeEntity.getJobStatisticTime();
		String date = sdf.format(jobStatisticTime);
		HdfsConsumeService.getTbTqConsumeService().getHdfsData(ConsumeSpendConstant.HDFSSPENDINGPATH+date);
		HdfsConsumeService.getTbRechargeService().getHdfsData(ConsumeSpendConstant.HDFSTBRECHARGEPATH+date);
		HdfsConsumeService.getTqRechargeService().getHdfsData(ConsumeSpendConstant.HDFSTQRECHARGEPATH+date);
		
	}

	@Override
	protected String toLog(Object arg0) {
		return null;
	}

}
