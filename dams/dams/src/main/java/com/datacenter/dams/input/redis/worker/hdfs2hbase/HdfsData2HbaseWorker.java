package com.datacenter.dams.input.redis.worker.hdfs2hbase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.input.redis.worker.redis2hdfs.JobTypeEntity;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.input.redis.worker.util.HbaseUtil;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;

@SuppressWarnings("rawtypes")
public class HdfsData2HbaseWorker extends QuartzWorker {
	private static Logger logger = LogManager.getLogger(HdfsData2HbaseWorker.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static Configuration config = HBaseConfiguration.create();
	private String finishedJobQueue = ConsumeSpendConstant.REDISMRFINISHEDQUEUE;

	@Override
	protected List getData(int num) {
		logger.debug("通过work获取mr统计结果已完成的业务");
		List<String> ls = null;
		try {
			ls = TbRedisService.getRedisQueueImpi().getValueBatch(finishedJobQueue, num);
		} catch (Exception e) {
			logger.error("从mr完成的redis队列获取数据失败，失败的原因是:", e);
		}
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		String data = (String) obj;
		JobTypeEntity jobTypeEntity = (JobTypeEntity) JsonUtil.getObjectFromJson(data, JobTypeEntity.class);
		// 获取已完成的jobType和jobBusType
		String jobType = jobTypeEntity.getJobType();
		String jobBusType = jobTypeEntity.getJobBusType();
		Date jobStatisticTime = jobTypeEntity.getJobStatisticTime();
		String date = sdf.format(jobStatisticTime);
		// 判断当前是周数据统计完成
		if (jobType.equals(ConsumeSpendConstant.MRWEEKTYPE)) {
			if (jobBusType.equals(ConsumeSpendConstant.DAYSTATISTIC)) {
				logger.info("============富豪榜周统计完成开始从hdfs路径读取数据写入hbase表中==================");
				String tableName = ConsumeSpendConstant.HBASEUSERWEEKTABLETEMP + date;
				String dir = ConsumeSpendConstant.STATISTICUSERWEEKHBASEPATH + date;
				String finalHbaseTableName = ConsumeSpendConstant.HBASEUSERWEEKTABLE + date;
				readHdfs2Hbase(tableName, finalHbaseTableName, dir, data);
			} else {
				logger.info("============明星榜周统计完成开始从hdfs路径读取数据写入hbase表中==================");
				String tableName = ConsumeSpendConstant.HBASESTARWEEKTABLETEMP + date;
				String dir = ConsumeSpendConstant.STATISTICSTARWEEKHBASEPATH + date;
				String finalHbaseTableName = ConsumeSpendConstant.HBASESTARWEEKTABLE + date;
				readHdfs2Hbase(tableName, finalHbaseTableName, dir, data);
			}

		} else if (jobType.equals(ConsumeSpendConstant.MRMONTH)) {
			if (jobBusType.equals(ConsumeSpendConstant.DAYSTATISTIC)) {
				logger.info("============富豪榜月统计完成开始从hdfs路径读取数据写入hbase表中==================");
				String tableName = ConsumeSpendConstant.HBASEUSERMONTHTABLETEMP + date;
				String dir = ConsumeSpendConstant.STATISTICUSERMONTHHBASEPATH + date;
				String finalHbaseTableName = ConsumeSpendConstant.HBASEUSERMONTHTABLE + date;
				readHdfs2Hbase(tableName, finalHbaseTableName, dir, data);
			} else {
				logger.info("============明星榜月统计完成开始从hdfs路径读取数据写入hbase表中==================");
				String tableName = ConsumeSpendConstant.HBASESTARMONTHTABLETEMP + date;
				String dir = ConsumeSpendConstant.STATISTICSTARMONTHHBASEPATH + date;
				String finalHbaseTableName = ConsumeSpendConstant.HBASESTARMONTHTABLE + date;
				readHdfs2Hbase(tableName, finalHbaseTableName, dir, data);
			}

		} else if (jobType.equals(ConsumeSpendConstant.MRALL)) {

			if (jobBusType.equals(ConsumeSpendConstant.DAYSTATISTIC)) {
				logger.info("============富豪榜总统计完成开始从hdfs路径读取数据写入hbase表中==================");
				String tableName = ConsumeSpendConstant.HBASEUSERALLTABLETEMP + date;
				String dir = ConsumeSpendConstant.STATISTICUSERALLHBASEPATH + date;
				String finalHbaseTableName = ConsumeSpendConstant.HBASEUSERALLTABLE + date;
				readHdfs2Hbase(tableName, finalHbaseTableName, dir, data);
			} else {
				logger.info("============明星榜总统计完成开始从hdfs路径读取数据写入hbase表中==================");
				String tableName = ConsumeSpendConstant.HBASESTARALLTABLETEMP + date;
				String dir = ConsumeSpendConstant.STATISTICSTARALLHBASEPATH + date;
				String finalHbaseTableName = ConsumeSpendConstant.HBASESTARALLTABLE + date;
				readHdfs2Hbase(tableName, finalHbaseTableName, dir, data);
			}
		}
	}

	/***
	 * 
	 * @param tableName
	 * @param finalHbaseTableName
	 * @param date
	 * @param dir
	 * @param data
	 * @throws Exception
	 */
	public void readHdfs2Hbase(String tableName, String finalHbaseTableName, String dir, String data) throws Exception {
		// 先创建hbase表
		try {
			HbaseUtil.createTable(config, tableName, ConsumeSpendConstant.HBASECOLUMNFAMILY);
			logger.info("============hbase表"+tableName+"创建成功==================");
			// 读取指定路径下的hdfs文件，将数据写入hbase表中
			try {
				HadoopFSOperations.readHDFSListAll(dir, tableName, config);
				logger.info("============完成hdfs文件读取，并将读取的数据写入hbase表"+tableName+"==================");
				try {
					HbaseUtil.rename(config, tableName, finalHbaseTableName);
					logger.info("============hbase表"+tableName+"重命名成功==================");
				} catch (Exception e) {
					logger.error("榜重命名失败，失败的原因是：", e);
					// TbRedisService.getRedisQueueImpi().setValue(finishedJobQueue,
					// data);
				}
			} catch (Exception e) {
				logger.error("统计完成hdfs文件读取，并将读取的数据写入hbase失败，失败的原因是:", e);
				// TbRedisService.getRedisQueueImpi().setValue(finishedJobQueue,
				// data);
			}
		} catch (Exception e) {
			// TbRedisService.getRedisQueueImpi().setValue(finishedJobQueue,
			// data);
			logger.error("统计hbase表创建失败，失败的原因是:", e);
		}
	}

	@Override
	protected String toLog(Object paramT) {
		// TODO Auto-generated method stub
		return null;
	}

}
