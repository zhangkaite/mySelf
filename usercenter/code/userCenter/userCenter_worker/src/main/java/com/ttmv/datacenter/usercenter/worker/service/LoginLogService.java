package com.ttmv.datacenter.usercenter.worker.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.UserLoginRecordDao;
import com.ttmv.datacenter.usercenter.domain.data.record.UserLoginRecord;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月6日 下午3:41:35  
 * @explain :登录日志记录
 */
public class LoginLogService {
	private static Logger logger = LogManager.getLogger(LoginLogService.class);
	
	private UserLoginRecordDao loginRecordDao;

	public UserLoginRecordDao getLoginRecordDao() {
		return loginRecordDao;
	}

	public void setLoginRecordDao(UserLoginRecordDao loginRecordDao) {
		this.loginRecordDao = loginRecordDao;
	}
	@SuppressWarnings("rawtypes")
	public void excete(Map msgMap){
		logger.info("[" + msgMap.get("reqId") + "]@@" +"[worker执行---登录日志记录！！！]");
		UserLoginRecord loginRecord = new UserLoginRecord();
		try {
			Integer clientType = Integer.parseInt(msgMap.get("clientType")+"");
			Integer type = Integer.parseInt(msgMap.get("type")+"");
			loginRecord.setTTnum(new BigInteger(msgMap.get("TTnum")+""));
			loginRecord.setIp(msgMap.get("IP")+"");
			loginRecord.setMac(msgMap.get("MAC")+"");
			loginRecord.setDisksn(msgMap.get("HdNum")+"");
			loginRecord.setTime(new Date(Long.parseLong(msgMap.get("loginTime")+"")));
			loginRecord.setClientType(clientType);
			loginRecord.setType(type);
			loginRecord.setReqId(msgMap.get("reqId")+"");
		} catch (Exception e) {
			logger.error("[" + msgMap.get("reqId") + "]@@" +"添加对象组装异常！！！"+e.getMessage());
			e.printStackTrace();
		}
		
		try {
			loginRecordDao.addUserLoginRecord(loginRecord);
		} catch (Exception e) {
			logger.error("[" + msgMap.get("reqId") + "]@@" +"[队列执行登录日志失败!!!]"+ e);
			e.printStackTrace();
		}
		logger.info("[" + msgMap.get("reqId") + "]@@" +"[***worker执行完成：登录日志记录成功***]");
	}
	

}
