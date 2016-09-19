package com.datacenter.dams.input.redis.worker.BusinessStatistics;

import com.datacenter.dams.input.hbase.util.DataUtil;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.input.redis.worker.util.MrCallUtil;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.worker.worker.QuartzWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * 将mr调度统计运营业务的结果 推送给sdms
 *
 * @author kate
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
// 设置work调度上次执行完成才执行下次work
@DisallowConcurrentExecution
public class BusStatisticsMapReduceWorker extends QuartzWorker<Object> {

	private static Logger logger = LogManager.getLogger(BusStatisticsMapReduceWorker.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private String USERLOGINOUTPUTMON="/datacenter/usercenter/login/monoutput_";
	 
	@Override
	protected List getData(int num) {
		List ls = new ArrayList();
		ls.add(new Date());
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		// mr调度用户登录
		execUserLogin();
		// mr调度首充、首消
		execPayCenter();
		// mr调度用户注册
		execUserRegister();
		execMonthUserLogin();
	}

	public void execUserLogin() throws Exception {
		logger.info("[[BusStatisticsMapReduceWorker]]运营用户日登录业务统计mr调用开始" + new Date());
		String command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.USERLOGINJAR;
		// /datacenter/usercenter/login/input_20160407
		String inputPath = ConsumeSpendConstant.USERLOGININPUTPATH + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		// /datacenter/usercenter/login/output_20160407/20160407
		String outputPath = ConsumeSpendConstant.USERLOGINOUTPUTPATH + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1))
				+ File.separator + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		logger.info("运营用户登录业务统计远程调用命令:" + command +"day "+ inputPath + " " + outputPath);
		MrCallUtil.execNewMr(command +"day "+ inputPath + " " + outputPath);
		logger.info("[[BusStatisticsMapReduceWorker]]运营用户登录业务统计mr调用结束" + new Date());

	}

	public void execMonthUserLogin() throws Exception {

		logger.info("[[BusStatisticsMapReduceWorker]]运营用户月登录业务统计mr调用开始" + new Date());
		String command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.USERLOGINJAR;
		int nums = DataUtil.getWeeksByChooseDay();
		// 如果数据为当月第一天 获取上一个月最后一天是哪一天
		if (nums == 1) {
			Date lastMonthEndDay = DateUtil.getLastMonth().getEnd();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			nums = Integer.valueOf(sdf.format(lastMonthEndDay).split("-")[2]) + 1;
		}
		String inputPath = "";
		for (int i = nums - 1; i >= 1; i--) {
			String currentPath = ConsumeSpendConstant.USERLOGININPUTPATH
					+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -i));
			// 对不存在的路径做一次过滤
			if (HadoopFSOperations.isDirExit(currentPath)) {
				inputPath = inputPath + currentPath + " ";
			}
		}
		// /datacenter/usercenter/login/output_20160407/20160407
		String outputPath = USERLOGINOUTPUTMON + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1))
				+ File.separator + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		logger.info("运营月用户登录业务统计远程调用命令:" + command +"month "+ inputPath + outputPath);
		MrCallUtil.execNewMr(command +"month "+ inputPath + outputPath);
		logger.info("[[BusStatisticsMapReduceWorker]]运营用户月登录业务统计mr调用结束" + new Date());

	}

	public void execPayCenter() throws Exception {
		logger.info("[[BusStatisticsMapReduceWorker]]运营首充/首消业务统计mr调用开始" + new Date());
		String command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.BUSSTATISTICSJAR;
		String inputPath = ConsumeSpendConstant.PAYCENTERALLDATA + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		String outputPath = ConsumeSpendConstant.BUSSTATISTICSRESULTHDFSPATH
				+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1)) + File.separator
				+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		logger.info("运营首充/首消业务统计远程调用命令:" + command + inputPath + " " + outputPath);
		MrCallUtil.execNewMr(command + inputPath + " " + outputPath);
		logger.info("[[BusStatisticsMapReduceWorker]]运营首充/首消业务统计mr调用结束" + new Date());

	}

	public void execUserRegister() throws Exception {
		logger.info("[[BusStatisticsMapReduceWorker]]运营用户注册业务统计mr调用开始" + new Date());
		String command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.USERREGISTERJAR;
		String inputPath = ConsumeSpendConstant.USERREGISTERINPUTPATH + sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		String outputPath = ConsumeSpendConstant.USERREGISTEROUTPUTPATH
				+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1)) + File.separator
				+ sdf.format(DateUtil.getQueryFixedTime(new Date(), 1, -1));
		logger.info("运营用户注册业务统计远程调用命令:" + command + inputPath + " " + outputPath);
		MrCallUtil.execNewMr(command + inputPath + " " + outputPath);
		logger.info("[[BusStatisticsMapReduceWorker]]运营用户注册业务统计mr调用结束" + new Date());

	}

	@Override
	protected String toLog(Object arg0) {
		return null;
	}

}
