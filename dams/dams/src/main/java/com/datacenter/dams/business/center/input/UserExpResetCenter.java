package com.datacenter.dams.business.center.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.level.ExpHbaseService;
import com.datacenter.dams.input.queue.entity.UserExpResetInfo;
import com.google.common.base.Strings;

/**
 * 主播经验清零，center
 * @author wulinli
 *
 */
public class UserExpResetCenter {

	private static Logger logger=LogManager.getLogger(UserExpResetCenter.class);
	
	public void handler(Object object)throws Exception{
		if(object == null){
			return;
		}
		UserExpResetInfo info = (UserExpResetInfo)object;
		if(!checkObject(info)){
			return;
		}
		String userId = info.getUserID().toString();
		ExpHbaseService.reset(userId);
		logger.info("[DAMS主播经验清零UserExpResetCenter]清零中心处理。");
	}
	
	/**
	 * 检查对象
	 * @param info
	 * @return
	 */
	private boolean checkObject(UserExpResetInfo info){
		boolean flag = true;
		if(info == null){
			flag = false;
		}
		if(info.getUserID() == null){
			 flag = false;
		}
		if(Strings.isNullOrEmpty(info.getTime())){
			flag = false;
		}
		if(Strings.isNullOrEmpty(info.getToken())){
			 flag = false;
		}
		if(Strings.isNullOrEmpty(info.getType())){
			 flag = false;
		}
		return flag;
	}
}