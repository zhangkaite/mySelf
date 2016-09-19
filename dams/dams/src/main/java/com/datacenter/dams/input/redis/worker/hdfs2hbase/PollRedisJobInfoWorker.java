package com.datacenter.dams.input.redis.worker.hdfs2hbase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.input.redis.worker.redis2hdfs.JobTypeEntity;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.input.redis.worker.util.MrCallUtil;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;

/**
 * 轮询存储job信息的redis队列
 * 
 * @author zkt
 *
 */
@SuppressWarnings("rawtypes")
public class PollRedisJobInfoWorker extends QuartzWorker {

	private static Logger logger = LogManager.getLogger(PollRedisJobInfoWorker.class);

	// redis工具类

	private String redisTbJobQueue = ConsumeSpendConstant.REDISMRWORKINGQUEUE;

	private String finishedJobQueue = ConsumeSpendConstant.REDISMRFINISHEDQUEUE;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	

	@Override
	protected List getData(int num) {
		logger.debug("轮询获取mr正在运行的job的redis队列消息");
		List<String> ls = null;
		try {
			ls = TbRedisService.getRedisQueueImpi().getValueBatch(redisTbJobQueue, num);
		} catch (Exception e) {
			logger.error("从mr正在运行的job的redis队列获取数据失败，失败的原因是:", e);
		}
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		String data = (String) obj;
		JobTypeEntity jobTypeEntity = (JobTypeEntity) JsonUtil.getObjectFromJson(data, JobTypeEntity.class);
		Date jobRunTime = jobTypeEntity.getJobRunTime();
		Date currentTime = new Date();
		int diffTimes = (int) ((currentTime.getTime() - jobRunTime.getTime()) / (1000 * 60));
		String jobType = jobTypeEntity.getJobType();
		// 如果时差相差大于设定的时间
		if (diffTimes >= ConsumeSpendConstant.MRRUNTIME) {
			// 说明日调度已完成，则调度周的mr
			if (jobType.equals(ConsumeSpendConstant.MRDAYTYPE)) {
				String jobBusType = jobTypeEntity.getJobBusType();
				Date date = new Date();
				// 设置mr运行详情
				JobTypeEntity jEntity = new JobTypeEntity();
				// 设置调度执行的时间
				jEntity.setJobStatisticTime(jobTypeEntity.getJobStatisticTime());
				jEntity.setJobRunTime(date);
				jEntity.setJobBusType(jobBusType);
				// 根据jobBusType判断当前是哪个业务完成mr日榜计算
				if (jobBusType.equals(ConsumeSpendConstant.DAYSTATISTIC)) {
					// 当前执行完成的业务是富豪榜业务，则继续执行富豪榜周榜
					// 调用mr nohup hadoop jar XXXX.jar 业务标识 输出路径 输入路径
					String command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.MAPREDUCEPATH
							+ ConsumeSpendConstant.OTHERSTATISTIC + " "
							+ ConsumeSpendConstant.STATISTICUSERWEEKHBASEPATH
							+ sdf.format(jobTypeEntity.getJobStatisticTime());
					// 执行周榜的mr统计
					String times = "";
					for (int i = 0; i < 6; i++) {
						Date currentDate = DateUtil.getQueryFixedTime(jobTypeEntity.getJobStatisticTime(), 1, -i);
						String currentPath = ConsumeSpendConstant.STATISTICUSERDAYHBASEPATH + sdf.format(currentDate);
						if (HadoopFSOperations.isDirExit(currentPath)) {
							times = times + currentPath + " ";
						}
					}
					command = command + " " + times;
					logger.info("执行富豪榜周的mr统计命令：" + command);
					// 执行富豪榜周的mr统计
					final String user_week_cmd = command.substring(0, command.length() - 1);
					if (!"".equals(times)) {
						MrCallUtil.execMr(user_week_cmd);
						logger.info("将富豪榜周的mr正在统计的信息放入正在运行的redis队列中");
					}

					jEntity.setJobType(ConsumeSpendConstant.MRWEEKTYPE);
					TbRedisService.getRedisQueueImpi().setValue(redisTbJobQueue, JsonUtil.getObjectToJson(jEntity));
					logger.info("======================将富豪榜周正在执行的mr信息写入redis队列=======================");
					// 调用mr nohup hadoop jar XXXX.jar 业务标识 输出路径 输入路径
					String month_command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.MAPREDUCEPATH
							+ ConsumeSpendConstant.OTHERSTATISTIC + " "
							+ ConsumeSpendConstant.STATISTICUSERMONTHHBASEPATH
							+ sdf.format(jobTypeEntity.getJobStatisticTime());
					// 执行月榜的mr统计
					String month_times = "";
					for (int i = 0; i < 29; i++) {
						Date currentDate = DateUtil.getQueryFixedTime(jobTypeEntity.getJobStatisticTime(), 1, -i);
						String currentPath = ConsumeSpendConstant.STATISTICUSERDAYHBASEPATH + sdf.format(currentDate);
						if (HadoopFSOperations.isDirExit(currentPath)) {
							month_times = month_times + currentPath + " ";
						}

					}

					month_command = month_command + " " + month_times;
					logger.info("执行富豪榜月的mr统计命令：" + month_command);
					final String user_month_cmd = month_command.substring(0, month_command.length() - 1);
					if (!"".equals(month_times)) {
						MrCallUtil.execMr(user_month_cmd);
						logger.info("将富豪榜月的mr正在统计的信息放入正在运行的redis队列中");
					}
					jEntity.setJobType(ConsumeSpendConstant.MRMONTH);
					TbRedisService.getRedisQueueImpi().setValue(redisTbJobQueue, JsonUtil.getObjectToJson(jEntity));
					logger.info("======================将富豪榜月正在执行的mr信息写入redis队列=======================");
					// 当前执行完成的业务是富豪榜业务，则继续执行富豪榜周榜
					// 调用mr nohup hadoop jar XXXX.jar 业务标识 输出路径 输入路径
					String all_command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.MAPREDUCEPATH
							+ ConsumeSpendConstant.OTHERSTATISTIC + " " + ConsumeSpendConstant.STATISTICUSERALLHBASEPATH
							+ sdf.format(jobTypeEntity.getJobStatisticTime());
					// 日榜+前一天的总榜
					String all_inputPath = ConsumeSpendConstant.STATISTICUSERDAYHBASEPATH
							+ sdf.format(jobTypeEntity.getJobStatisticTime());
					all_command = all_command + " " + all_inputPath;
					// 递归调用向前查询60天的数据
					String last_all_path = HadoopFSOperations.recursionHdfsPath(
							ConsumeSpendConstant.STATISTICUSERALLHBASEPATH, ConsumeSpendConstant.HADOOPMAXNUMBER,
							DateUtil.getQueryFixedTime(jobTypeEntity.getJobStatisticTime(), 1, -1),
							DateUtil.getQueryFixedTime(jobTypeEntity.getJobStatisticTime(), 1, -1));

					all_command = all_command + " " + last_all_path;
					logger.info("执行富豪榜总的mr统计命令：" + all_command);
					final String user_all_cmd = all_command;
					if (null != last_all_path) {
						MrCallUtil.execMr(user_all_cmd);
					}
					jEntity.setJobType(ConsumeSpendConstant.MRALL);
					logger.info("将富豪榜总的mr正在统计的信息放入正在运行的redis队列中");
					TbRedisService.getRedisQueueImpi().setValue(redisTbJobQueue, JsonUtil.getObjectToJson(jEntity));
					logger.info("======================将富豪榜总正在执行的mr信息写入redis队列=======================");
				} else {
					// 当前执行完成的业务是明星榜业务
					// 调用mr nohup hadoop jar XXXX.jar 业务标识 输出路径 输入路径
					String command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.MAPREDUCEPATH
							+ ConsumeSpendConstant.OTHERSTATISTIC + " "
							+ ConsumeSpendConstant.STATISTICSTARWEEKHBASEPATH
							+ sdf.format(jobTypeEntity.getJobStatisticTime());
					// 执行周榜的mr统计
					String times = "";
					for (int i = 0; i < 6; i++) {
						Date currentDate = DateUtil.getQueryFixedTime(jobTypeEntity.getJobStatisticTime(), 1, -i);
						String currentPath = ConsumeSpendConstant.STATISTICSTARDAYHBASEPATH + sdf.format(currentDate);
						if (HadoopFSOperations.isDirExit(currentPath)) {
							times = times + currentPath + " ";
						}

					}
					// 执行明星榜周的mr统计
					command = command + " " + times;
					logger.info("执行明星榜周的的mr统计命令：" + command);
					final String star_week_cmd = command.substring(0, command.length() - 1);
					if (!"".equals(times)) {
						MrCallUtil.execMr(star_week_cmd);
						logger.info("将明星榜周的mr正在统计的信息放入正在运行的redis队列中");
					}

					jEntity.setJobType(ConsumeSpendConstant.MRWEEKTYPE);
					TbRedisService.getRedisQueueImpi().setValue(redisTbJobQueue, JsonUtil.getObjectToJson(jEntity));
					logger.info("======================将周榜明星榜正在执行的mr信息写入redis队列=======================");
					// 执行明星榜月的mr统计
					String month_command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.MAPREDUCEPATH
							+ ConsumeSpendConstant.OTHERSTATISTIC + " "
							+ ConsumeSpendConstant.STATISTICSTARMONTHHBASEPATH
							+ sdf.format(jobTypeEntity.getJobStatisticTime());
					String month_times = "";
					for (int i = 0; i < 29; i++) {
						Date currentDate = DateUtil.getQueryFixedTime(jobTypeEntity.getJobStatisticTime(), 1, -i);
						String currentPath = ConsumeSpendConstant.STATISTICSTARDAYHBASEPATH + sdf.format(currentDate);
						if (HadoopFSOperations.isDirExit(currentPath)) {
							month_times = month_times + currentPath + " ";
						}
					}
					month_command = month_command + " " + month_times;

					final String star_month_cmd = month_command.substring(0, month_command.length() - 1);
					if (!"".equals(month_times)) {
						logger.info("执行明星榜月的的mr统计命令：" + month_command);
						MrCallUtil.execMr(star_month_cmd);
					}

					jEntity.setJobType(ConsumeSpendConstant.MRMONTH);
					TbRedisService.getRedisQueueImpi().setValue(redisTbJobQueue, JsonUtil.getObjectToJson(jEntity));
					logger.info("======================将明星月榜正在执行的mr信息写入redis队列=======================");
					// 执行明星榜总的mr统计
					String all_command = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.MAPREDUCEPATH
							+ ConsumeSpendConstant.OTHERSTATISTIC + " " + ConsumeSpendConstant.STATISTICSTARALLHBASEPATH
							+ sdf.format(jobTypeEntity.getJobStatisticTime());
					// 日榜+前一天的总榜
					String all_inputPath = ConsumeSpendConstant.STATISTICSTARDAYHBASEPATH
							+ sdf.format(jobTypeEntity.getJobStatisticTime());
					all_command = all_command + " " + all_inputPath;
					// 递归调用向前查询60天的数据
					String last_all_path = HadoopFSOperations.recursionHdfsPath(
							ConsumeSpendConstant.STATISTICSTARALLHBASEPATH, ConsumeSpendConstant.HADOOPMAXNUMBER,
							DateUtil.getQueryFixedTime(jobTypeEntity.getJobStatisticTime(), 1, -1),
							DateUtil.getQueryFixedTime(jobTypeEntity.getJobStatisticTime(), 1, -1));

					all_command = all_command + " " + last_all_path;
					logger.info("执行明星榜总的的mr统计命令：" + all_command);
					final String star_all_cmd = all_command;
					if (null != last_all_path) {
						MrCallUtil.execMr(star_all_cmd);
					}
					jEntity.setJobType(ConsumeSpendConstant.MRALL);
					TbRedisService.getRedisQueueImpi().setValue(redisTbJobQueue, JsonUtil.getObjectToJson(jEntity));
					logger.info("======================将明星总榜正在执行的mr信息写入redis队列=======================");
				}
				// 说明周的调用已完成，
			} else if (jobType.equals(ConsumeSpendConstant.MRWEEKTYPE)) {
				// 已完成的mr存入完成的redis队列
				TbRedisService.getRedisQueueImpi().setValue(finishedJobQueue, JsonUtil.getObjectToJson(jobTypeEntity));
				logger.info("将周统计已完成的mr存入完成的redis队列");

			} else if (jobType.equals(ConsumeSpendConstant.MRMONTH)) {
				// 已完成的mr存入完成的redis队列
				TbRedisService.getRedisQueueImpi().setValue(finishedJobQueue, JsonUtil.getObjectToJson(jobTypeEntity));

			} else {
				// 表示总榜执行完成
				TbRedisService.getRedisQueueImpi().setValue(finishedJobQueue, JsonUtil.getObjectToJson(jobTypeEntity));
				logger.info("======================将总榜执行完成的mr信息写入已完成的redis队列=======================");
			}

		} else {

			TbRedisService.getRedisQueueImpi().setValue(redisTbJobQueue, data);
			logger.debug("mr执行的时间小于设定的时间，将数据接着放入正在运行的mr redis队列 ");
		}

	}

	@Override
	protected String toLog(Object arg0) {
		return null;
	}

}
