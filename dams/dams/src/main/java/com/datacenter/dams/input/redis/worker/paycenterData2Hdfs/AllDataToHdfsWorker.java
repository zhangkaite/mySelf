package com.datacenter.dams.input.redis.worker.paycenterData2Hdfs;

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
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.worker.worker.QuartzWorker;

@SuppressWarnings({ "rawtypes", "unchecked" })
// 设置work调度上次执行完成才执行下次work
@DisallowConcurrentExecution
public class AllDataToHdfsWorker extends QuartzWorker<Object> {

	private static Logger logger = LogManager.getLogger(AllDataToHdfsWorker.class);
	// 注入redis队列名称
	private String payCenterAllDataQueue = ConsumeSpendConstant.PAYCENTERALLDATAQUE;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	protected List getData(int num) {
		logger.debug("从redis批量获取payCenter四种类型数据信息写入hdfs!!!!");
		List ls = null;
		if (num > 0) {
			try {
				ls = TbRedisService.getRedisQueueImpi().getValueBatch(payCenterAllDataQueue, num);
			} catch (Exception e) {
				logger.error("从redis批量获取payCenter四种类型数据信息信息失败，失败的原因是:", e);
			}
		}
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		// 将obj转换成String，然后转换成jsonObj,获取里面的时间和当前时间进行匹配
		String data = (String) obj;
		JSONObject jsonObject = new JSONObject(data);
		// 获取的时间是Unix时间戳
		String time = String.valueOf(jsonObject.get("time"));
		Date das = DateUtil.getDate(time);
		try {
			logger.info("财务统计从redis获取的数据："+data);
			HadoopFSOperations.createNewHDFSFile(ConsumeSpendConstant.PAYCENTERALLDATA + sdf.format(das)
					+ File.separator + sdf.format(das), data);
			logger.debug("hdfs 存储数据路径:"+ConsumeSpendConstant.PAYCENTERALLDATA + sdf.format(das)
					+ File.separator + sdf.format(das));
		} catch (Exception e) {
            //hdfs些失败，将信息从新放入消息队列
			TbRedisService.getRedisQueueImpi().setValue(ConsumeSpendConstant.PAYCENTERALLDATAQUE,data);
			logger.error("获取redis payCenter四种类型数据写入hdfs失败，失败的数据：" + data);
			logger.error("获取redis payCenter四种类型数据写入hdfs失败，失败的原因是:", e);
		}
	}

	@Override
	protected String toLog(Object arg0) {
		return null;
	}

}
