package com.datacenter.dams.input.redis.worker.BusinessStatistics;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;

/**
 * Created by zkt on 2016/4/5.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@DisallowConcurrentExecution
public class UserRegisterResultDataToSdmsWorker extends QuartzWorker<Object> {
	private static Logger logger = LogManager.getLogger(UserRegisterResultDataToSdmsWorker.class);
	// 注入redis队列名称
	private String userRegisterFinishQueue = ConsumeSpendConstant.USERREGISTERMRFINISHQUEUE;

	@Override
	protected List<Object> getData(int num) {
		logger.debug("从redis队列DAMS_USERREGISTER_STATISTICS_FINISH获取运营业务统计mr调用的数据");
		List ls = null;
		if (num > 0) {
			try {
				ls = TbRedisService.getRedisQueueImpi().getValueBatch(userRegisterFinishQueue, 1);
				if (ls != null && ls.size() > 0) {
					logger.info("[DAMS#UserRegisterResultDataToSdmsWorker]获取redis数据：" + JsonUtil.getObjectToJson(ls));
				}
			} catch (Exception e) {
				logger.error("从redis队列DAMS_USERREGISTER_STATISTICS_FINISH获取运营用户注册业务统计数据失败，失败的原因是:", e);
			}
		}
		return ls;
	}

	@Override
	protected void process(Object obj) throws Exception {
		String data = (String) obj;
		JSONObject jsonObject = new JSONObject(data);
		String statisticDay = jsonObject.getString("statisticDay").substring(0, 10);
		String outputPath = jsonObject.getString("outputPath");
		logger.info("从redis队列" + userRegisterFinishQueue + "获取运营用户注册业务统计mr调用推送的数据statisticDay：" + statisticDay + "outputPath:"
				+ outputPath);
		doBusiness(statisticDay, outputPath);
	}

	public void doBusiness(String statisticDay, String outputPath) throws Exception {
		UserLoginStatistics userLoginStatistics = new UserLoginStatistics();
		userLoginStatistics.setParams(outputPath, ConsumeSpendConstant.USERLOGINMQQUEUE, statisticDay, "RE","day");
		userLoginStatistics.doBusStatistics();

	}

	@Override
	protected String toLog(Object o) {
		return null;
	}

}
