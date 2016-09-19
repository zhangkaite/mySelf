package com.datacenter.dams.input.hdfs.worker;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.hbase.util.DataUtil;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.input.redis.worker.util.MrCallUtil;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.worker.worker.QuartzWorker;

/***
 * TB日/月充值/消费 人数统计
 *
 * @author kate
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
// 设置work调度上次执行完成才执行下次work
@DisallowConcurrentExecution
public class RechConsMapReduceWorker extends QuartzWorker<Object> {

	private static Logger logger = LogManager.getLogger(RechConsMapReduceWorker.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private String MRCOMMAND = "hadoop jar /opt/RechConsBus.jar ";
	private static String INPUTPATHPREFIX = "/datacenter/paycenter/all/allinput_";
	private String OUTPATHPREFIX = "/datacenter/paycenter/statistics/rechcons_";
	@Override
	protected List getData(int num) {
		List ls = new ArrayList();
		ls.add(new Date());
		return ls;
	}

	@Override
	protected void process(Object obj) {
		// mr调度用户登录
		try {
			execDayStatistic();
		} catch (Exception e) {
			logger.error("TB日充值/消费 人数统计mr调用失败，失败的原因是:",e);
		}
		try {
			execMonthStatistic();
		} catch (Exception e) {
			logger.error("TB月充值/消费 人数统计mr调用失败，失败的原因是:",e);
		}
	}

	public void execDayStatistic() throws Exception {
		logger.info("[[RechConsMapReduceWorker]]TB日充值/消费人数统计mr调用开始" + new Date());
		String inputPath = INPUTPATHPREFIX + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		String outputPath = OUTPATHPREFIX +"day"+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1)) + File.separator
				+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		logger.info("TB日充值/消费人数统计远程调用命令:" + MRCOMMAND +"day "+ inputPath + " " + outputPath);
		MrCallUtil.execNewMr(MRCOMMAND +"day "+ inputPath + " " + outputPath);
		logger.info("[[RechConsMapReduceWorker]]TB日充值/消费人数统计mr调用结束" + new Date());

	}

	public void execMonthStatistic() throws Exception {
		logger.info("[[RechConsMapReduceWorker]]TB月充值/消费人数统计mr调用开始" + new Date());
		int nums = DataUtil.getWeeksByChooseDay();
		//如果数据为当月第一天  获取上一个月最后一天是哪一天
		if (nums==1) {
			Date lastMonthEndDay=DateUtil.getLastMonth().getEnd();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			nums=Integer.valueOf(sdf.format(lastMonthEndDay).split("-")[2])+1;
		}
		String inputPath = "";
		for (int i = nums - 1; i >= 1; i--) {
			String currentPath=INPUTPATHPREFIX + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -i));
			if (HadoopFSOperations.isDirExit(currentPath)) {
				inputPath = inputPath + currentPath + " ";
			}
		}

		String outputPath = OUTPATHPREFIX +"month"+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1)) + File.separator
				+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		logger.info("TB月充值/消费人数统计远程调用命令:" + MRCOMMAND +"month "+ inputPath  + outputPath);
		MrCallUtil.execNewMr(MRCOMMAND +"month "+ inputPath  + outputPath);
		logger.info("[[RechConsMapReduceWorker]]TB月充值/消费人数统计mr调用结束" + new Date());

	}

	@Override
	protected String toLog(Object arg0) {
		return null;
	}
   /* public static void main(String[] args) {
    	String inputPath = "";
    	int nums = DataUtil.getWeeksByChooseDay();
		for (int i = 31 - 1; i >= 1; i--) {
			inputPath = inputPath + INPUTPATHPREFIX + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -i)) + " ";
		}
		System.out.println(inputPath);
	}*/
	
	
	
	
}
