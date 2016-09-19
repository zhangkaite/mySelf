package com.datacenter.dams.business.center.input;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.util.JsonUtil;

/**
 * 用户登录记录center
 * @author wulinli
 *
 */
public class UserLoginCenter {

	private static Logger logger=LogManager.getLogger(UserLoginCenter.class);
	private FormInter userLoginFormService;
	
	@SuppressWarnings("rawtypes")
	public void handler(Object object)throws Exception{
		if(object == null){
			return;
		}
		Map map = (Map)object;
		if(!checkObject(map)){
			logger.error("[DAMS用户登录记录UserLoginCenter#ERROR]用户登录记录协议字段出错！数据是："+JsonUtil.getObjectToJson(map));
			return;
		}
		userLoginFormService.handler(map);
	}
	
	/**
	 * 检查对象
	 * @param info
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private boolean checkObject(Map map){
		boolean flag = true;
		if(map == null){
			flag = false;
		}
		if(!map.containsKey("type") || map.get("type") == null){
			flag = false;
		}
		if(!map.containsKey("loginName") || map.get("loginName") == null){
			flag = false;
		}
		if(!map.containsKey("clientType") || map.get("clientType") == null){
			flag = false;
		}
		if(!map.containsKey("time") || map.get("time") == null){
			flag = false;
		}
		return flag;
	}

	public FormInter getUserLoginFormService() {
		return userLoginFormService;
	}

	public void setUserLoginFormService(FormInter userLoginFormService) {
		this.userLoginFormService = userLoginFormService;
	}
}