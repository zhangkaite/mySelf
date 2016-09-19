package com.ttmv.datacenter.usercenter.worker.service;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 下午11:48:13  
 * @explain :日志记录队列处理类
 */
@SuppressWarnings("rawtypes")
public class  LogExcuteMessageServiceImpl {
	private static Logger logger = LogManager.getLogger(LogExcuteMessageServiceImpl.class);
	private LoginLogService workerLoginLogService;
	
	
	public void doService(String msg){
		Map msgMap = this.analysis(msg);
		logger.info("[" + (String)msgMap.get("reqId") + "]@@" +"收到日志类消息：========\n" + msg); 
		String msgType = (String)msgMap.get("msgType");
		if("loginLog".equals(msgType)){//登录日志
			workerLoginLogService.excete(msgMap);
		}else if("".equals(msgType)){//。。。日志
			
		}
	}
	public Map analysis(String msg){
		Map map = null;
		try {
			map = new ObjectMapper().readValue(msg, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	public LoginLogService getWorkerLoginLogService() {
		return workerLoginLogService;
	}
	public void setWorkerLoginLogService(LoginLogService workerLoginLogService) {
		this.workerLoginLogService = workerLoginLogService;
	}

	
	

}
