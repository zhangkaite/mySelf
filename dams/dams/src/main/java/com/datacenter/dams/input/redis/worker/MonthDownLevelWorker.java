package com.datacenter.dams.input.redis.worker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.service.level.ExpHbaseService;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;

/**
 * 用户降级worker
 * 每月的第一天触发
 * worker一次只发送一个用户信息
 * @author wulinli
 */
public class MonthDownLevelWorker extends QuartzWorker<String>{

	private static Logger logger=LogManager.getLogger(MonthDownLevelWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		if(sum != 0){			
			List<String> listStrings;
			try {
				listStrings = ExpHbaseService.getAllUsers();
				if(listStrings != null && listStrings.size() > 0){					
					listStrings = this.removeSameValue(listStrings);
					logger.info("[DAMS降级MonthDownLevelWorker]查询所有用户完成!" + JsonUtil.getObjectToJson(listStrings));
					return listStrings;
				}
			} catch (Exception e) {
				logger.error("[DAMS降级MonthDownLevelWorker]查询所有用户失败!",e);
			}
		}
		return null;
		
	}

	@Override
	protected void process(String message) throws Exception {
		if(message != null && !"".equals(message)){
			MessageHandlerCenter.monthDownLevelCenter.handler(message);
		}
	}

	@Override
	protected String toLog(String arg0) {
		return null;
	}

	/**
	 * 去重操作
	 * @param listStrings
	 * @throws Exception
	 */
	private List<String> removeSameValue(List<String> listStrings)throws Exception{
		if(listStrings != null && listStrings.size() > 0){
			List<String> list = new ArrayList<String>();
			Set<String> sets = new HashSet<String>();
			for(String str : listStrings){
				sets.add(str);
			}
			if(sets.size() > 0){
				list.addAll(sets);
				return list;
			}
		}
		return null;
	}
}
