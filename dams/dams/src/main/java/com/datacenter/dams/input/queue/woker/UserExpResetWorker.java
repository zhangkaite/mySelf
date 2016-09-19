package com.datacenter.dams.input.queue.woker;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.input.queue.UserExpResetQueueService;
import com.datacenter.dams.input.queue.entity.UserExpResetInfo;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;
import com.google.common.base.Strings;

/**
 * 主播明星等级经验清零 worker，从uc系统取消息
 * @author wulinli
 */
public class UserExpResetWorker extends QuartzWorker<String>{
	
	private static Logger logger = LogManager.getLogger(UserExpResetWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		try {
			List<String> listStrings = UserExpResetQueueService.receiveMessage(sum);
			if(listStrings != null && listStrings.size() > 0){
				logger.info("[DAMS主播经验清零UserExpResetWorker]从PC队列取消息："+JsonUtil.getObjectToJson(listStrings));
				return listStrings;
			}
		} catch (Exception e) {
			logger.error("[DAMS主播经验清零UserExpResetWorker]从PC队列取消息出错。",e);
		}
		return null;
	}

	@Override
	protected void process(String message) throws Exception {
		if(!Strings.isNullOrEmpty(message)){
			UserExpResetInfo info = (UserExpResetInfo) JsonUtil.getObjectFromJson(message, UserExpResetInfo.class);
			MessageHandlerCenter.userExpResetCenter.handler(info);
		}
	}

	@Override
	protected String toLog(String log) {
		return null;
	}
}
