package com.ttmv.datacenter.usercenter.worker.service;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ttmv.datacenter.usercenter.worker.listener.LogJmsReceiverListener;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 下午11:48:13  
 * @explain :用户注册推队列处理类
 */
@SuppressWarnings("rawtypes")
public class  AddUserExcuteMessageServiceImpl {
	private static Logger logger = LogManager.getLogger(AddUserExcuteMessageServiceImpl.class);
	
	private AddUserServiceImpl workerAddUserServiceImpl;
	
	public void doService(String msg){
		Map msgMap = this.analysis(msg);
		logger.info("[" + msgMap.get("reqId") + "]@@" +"收到队列消息：==========="+msg);
		String msgType = (String)msgMap.get("msgType");
		if("addUser".equals(msgType)){//用户注册信息初始化（异步队列）
			workerAddUserServiceImpl.execute(msgMap);
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
	public AddUserServiceImpl getWorkerAddUserServiceImpl() {
		return workerAddUserServiceImpl;
	}
	public void setWorkerAddUserServiceImpl(
			AddUserServiceImpl workerAddUserServiceImpl) {
		this.workerAddUserServiceImpl = workerAddUserServiceImpl;
	}
	
}
