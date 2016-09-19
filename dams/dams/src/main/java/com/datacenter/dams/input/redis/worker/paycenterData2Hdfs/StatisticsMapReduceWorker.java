package com.datacenter.dams.input.redis.worker.paycenterData2Hdfs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.redis.worker.util.MrCallUtil;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.worker.worker.QuartzWorker;

/***
 * 将mr调度统计的结果 推送给sdms
 * 
 * @author kate
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
// 设置work调度上次执行完成才执行下次work
@DisallowConcurrentExecution
public class StatisticsMapReduceWorker extends QuartzWorker<Object> {

	private static Logger logger = LogManager.getLogger(StatisticsMapReduceWorker.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@Override
	protected List getData(int num) {
		List ls = new ArrayList();
		ls.add(new Date());
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		logger.info("[[StatisticsMapReduceWorker]]财务统计mr调用开始"+new Date());
		String command=ConsumeSpendConstant.MAPREDUCECOMMOND+ConsumeSpendConstant.STATISTICSJAR;
		String inputPath=ConsumeSpendConstant.PAYCENTERALLDATA+sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		String outputPath=ConsumeSpendConstant.PAYCENTERALLOUTPUTDATA+sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1))+ File.separator+sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		logger.info("财务统计远程调用命令:"+command+inputPath+" "+outputPath);
		MrCallUtil.execMr(command+inputPath+" "+outputPath);
		logger.info("[[StatisticsMapReduceWorker]]财务统计mr调用结束"+new Date());
	}

	
	@Override
	protected String toLog(Object arg0) {
		return null;
	}

}
