package com.datacenter.dams.business.center.input;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.util.JsonUtil;

/**
 * 用户注册记录center
 * @author wulinli
 *
 */
public class AddUserCenter {

	private static Logger logger=LogManager.getLogger(AddUserCenter.class);
	private FormInter addUserFormService;
	
	@SuppressWarnings("rawtypes")
	public void handler(Object object)throws Exception{
		if(object == null){
			return;
		}
		Map map = (Map)object;
		if(!checkObject(map)){
			logger.error("[DAMS用户注册记录AddUserCenter#ERROR]用户注册记录协议字段出错！数据是："+JsonUtil.getObjectToJson(map));
			return;
		}
		addUserFormService.handler(map);
		logger.info("[DAMS用户注册记录AddUserCenter]用户注册记录center处理业务。");
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
		if(!map.containsKey("time") || map.get("time") == null){
			 flag = false;
		}
		return flag;
	}

	public FormInter getAddUserFormService() {
		return addUserFormService;
	}

	public void setAddUserFormService(FormInter addUserFormService) {
		this.addUserFormService = addUserFormService;
	}
}