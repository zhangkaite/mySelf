package com.datacenter.dams.input.hdfs.worker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.datacenter.dams.input.hdfs.service.RechConsService;
import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.worker.worker.QuartzWorker;

/***
 * 统计TB充值消费日/月 总人数 mr调用完成后获取
 * 
 * @author kate
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RechConsStatisticsWork extends QuartzWorker<Object> {
	private static Logger logger = LogManager.getLogger(RechConsStatisticsWork.class);
	private String redisQueue = ConsumeSpendConstant.RECHCONSREDISQUEUE;
	// private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	protected List getData(int num) {
		List<String> ls = null;
		try {
			ls = TbRedisService.getRedisQueueImpi().getValueBatch(redisQueue, 1);
		} catch (Exception e) {
			logger.error("从统计TB充值消费日/月 总人数mr完成的redis队列获取数据失败，失败的原因是:", e);
		}
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		String data = (String) obj;
		JSONObject jsonObject = new JSONObject(data);
		String statisticDay = jsonObject.getString("statisticDay").substring(0, 10);
		String outputPath = jsonObject.getString("outputPath");
		String dateType = jsonObject.getString("dateType");
		logger.info("从redis队列" + redisQueue + "获取TB充值消费日/月 总人数统计mr调用推送的数据statisticDay：" + statisticDay + "outputPath:"
				+ outputPath + "dateType:" + dateType);
	   doBusiness(statisticDay, outputPath,dateType);

	}

	/**
	 * 读取hdfs上统计的结果，将结果推送给Sdms ActiveMQ
	 * @param statisticDay
	 * @param outputPath
	 * @param dateType
	 * @throws Exception
	 */
	public void doBusiness(String statisticDay, String outputPath, String dateType) throws Exception {
        RechConsService rechConsService=new RechConsService();
        rechConsService.setParams(outputPath, redisQueue, statisticDay, dateType);
        rechConsService.doBusStatistics();
		
	}

	@Override
	protected String toLog(Object arg0) {
		return null;
	}

}
