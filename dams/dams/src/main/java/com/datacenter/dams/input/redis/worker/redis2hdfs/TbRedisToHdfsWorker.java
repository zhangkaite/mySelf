package com.datacenter.dams.input.redis.worker.redis2hdfs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.input.redis.worker.util.MrCallUtil;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;

@SuppressWarnings({ "rawtypes", "unchecked" })
// 设置work调度上次执行完成才执行下次work
@DisallowConcurrentExecution
public class TbRedisToHdfsWorker extends QuartzWorker<Object> {

	private static Logger logger = LogManager.getLogger(TbRedisToHdfsWorker.class);
	// 注入redis队列名称
	private String redisTbQueueName = ConsumeSpendConstant.REDISHDFSQUEUE;
	// 注入将job运行的信息写入redis队列 队列名称
	private String redisTbJobQueue = ConsumeSpendConstant.REDISMRWORKINGQUEUE;


	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	static Date flagDate = null;

	@Override
	protected List getData(int num) {
		logger.debug("从redis批量获取TB消费信息写入hdfs!!!!");
		List ls = null;
		if (num > 0) {
			try {
				ls = TbRedisService.getRedisQueueImpi().getValueBatch(redisTbQueueName, num);
			} catch (Exception e) {
				logger.error("从redis批量获取TB消费信息失败，失败的原因是:", e);
			}
		}
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		String data = (String) obj;
		JSONObject jsonObject = new JSONObject(data);
		String time = String.valueOf(jsonObject.get("time"));
		Date dataTime = DateUtil.getDate(time);
		if (null == flagDate) {
			flagDate = dataTime;
		}
		if (sdf.parse(sdf.format(dataTime)).after(sdf.parse(sdf.format(flagDate)))) {
			logger.debug("spending消费获取redis数据写入hdfs,日期发生变更的数据写入的hdfs路径:" + ConsumeSpendConstant.HDFSSPENDINGPATH
					+ sdf.format(dataTime) + ";内容：" + data);
			try {
				HadoopFSOperations.createNewHDFSFile(
						ConsumeSpendConstant.HDFSSPENDINGPATH + sdf.format(dataTime) + File.separator + sdf.format(dataTime),
						data);
			} catch (Exception e) {
				logger.error("spending消费获取redis数据写入hdfs失败，失败的原因是:", e);
				logger.error("spending消费获取redis数据写入hdfs失败，失败的数据：" + data);
			}

			JobTypeEntity jobTypeEntity = new JobTypeEntity();
			jobTypeEntity.setJobType(ConsumeSpendConstant.MRDAYTYPE);
			if (sdf.parse(sdf.format(DateUtil.getQueryFixedTime(dataTime, 1, -1))).equals(sdf.parse(sdf.format(flagDate)))) {
				jobTypeEntity.setJobStatisticTime(flagDate);
			} else {
				jobTypeEntity.setJobStatisticTime(DateUtil.getQueryFixedTime(dataTime, 1, -1));
			}
			jobTypeEntity.setJobRunTime(new Date());
			jobTypeEntity.setJobBusType(ConsumeSpendConstant.DAYSTATISTIC);
			String command = getCommand(ConsumeSpendConstant.DAYSTATISTIC, ConsumeSpendConstant.STATISTICUSERDAYHBASEPATH,
					flagDate);
			logger.debug("富豪榜日mr远程调用命令：" + command);
			exeComand(jobTypeEntity, command);
			jobTypeEntity.setJobBusType(ConsumeSpendConstant.DAYSTARSTATISTIC);
			String cmd = getCommand(ConsumeSpendConstant.DAYSTARSTATISTIC, ConsumeSpendConstant.STATISTICSTARDAYHBASEPATH,
					flagDate);
			logger.debug("明星榜日mr远程调用命令：" + cmd);
			exeComand(jobTypeEntity, cmd);
			flagDate = dataTime;
		} else {
			logger.debug("spending消费获取redis数据写入hdfs,数据写入的hdfs路径:" + ConsumeSpendConstant.HDFSSPENDINGPATH + sdf.format(dataTime)
					+ ";内容：" + data);
			try {
				HadoopFSOperations.createNewHDFSFile(
						ConsumeSpendConstant.HDFSSPENDINGPATH + sdf.format(dataTime) + File.separator + sdf.format(dataTime),
						data);
			} catch (Exception e) {
				logger.error("spending消费获取redis数据写入hdfs失败，失败的原因是:", e);
				logger.error("spending消费获取redis数据写入hdfs失败，失败的数据：" + data);
			}
		}
	}

	@Override
	protected String toLog(Object arg0) {
		return null;
	}

	public void exeComand(JobTypeEntity jobTypeEntity, String command) {
		try {
			MrCallUtil.execMr(command);
		} catch (Exception e) {
			logger.error("远程调用mr命令失败，失败的原因是:", e);
			return;
		}
		try {
			TbRedisService.getRedisQueueImpi().setValue(redisTbJobQueue, JsonUtil.getObjectToJson(jobTypeEntity));
		} catch (Exception e) {
			logger.error("日榜mr开始计算数据写入redis失败，失败的原因是：", e);

		}
	}

	public String getCommand(String mrType, String hdfsPath, Date flagDate) {
		String cmd = ConsumeSpendConstant.MAPREDUCECOMMOND + ConsumeSpendConstant.MAPREDUCEPATH + mrType + " " + hdfsPath
				+ sdf.format(flagDate);
		String inputPath = ConsumeSpendConstant.HDFSSPENDINGPATH + sdf.format(flagDate);
		return cmd + " " + inputPath;

	}

}
