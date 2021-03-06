package com.ttmv.financialStatistics;

import java.text.SimpleDateFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/***
 * 执行mr的入口
 * 
 * @author kate
 *
 */
public class Main {
	private static Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) {
		Configuration conf = new Configuration();
		Path outPath = new Path(args[1]);
		Path inptPath = new Path(args[0]);
		DataEntity dataEntity = new DataEntity();
		String dateTime = args[1].split("/")[5];
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			dataEntity.setStatisticDay(DateUtil.getUnixDate(sdf.parse(dateTime)));
			dataEntity.setOutputPath(args[1]);
			FileSystem fs;
			fs = FileSystem.get(conf);
			if (!fs.exists(inptPath)) {
				RedisUtil.pushDataToRedisQueue("SDMS_FinanceClipboard_Queue", JsonUtil.getObjectToJson(dataEntity));
				System.exit(0);
			}
			// 如果输出路径已存在，则先删除
			if (fs.exists(outPath)) {
				fs.delete(outPath, true);
				logger.info(outPath + "输出路径存在，已删除！");
				fs.close();
			}
		} catch (Exception e) {
			logger.error("hdfs 删除文件路径失败，失败的原因是:", e);
		}
		Job job = null;
		try {
			job = Job.getInstance(conf, "AllDataStatistic");
			job.setJarByClass(Main.class);
			job.setMapperClass(DataStatisticsMap.class);
			job.setCombinerClass(DataStatisticReducer.class);
			job.setReducerClass(DataStatisticReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
			FileInputFormat.addInputPath(job, new Path(args[0]));
			FileOutputFormat.setOutputPath(job, new Path(args[1]));
			// MultipleOutputs.addNamedOutput(job, "AllDataStatistic",
			// TextOutputFormat.class, Text.class, Text.class);

			if (job.waitForCompletion(true)) {
				RedisUtil.pushDataToRedisQueue("SDMS_FinanceClipboard_Queue", JsonUtil.getObjectToJson(dataEntity));
				System.exit(0);
			} else {
				System.exit(1);
			}

		} catch (Exception e) {
			logger.error("job 提交失败，失败的原因是:", e);
		}

	}

}
