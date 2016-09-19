package com.ttmv.datacenter.paycenter.worker.service;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ttmv.datacenter.paycenter.worker.tool.InitUserAccountTool;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月4日 下午5:48:13  
 * @explain :用户注册推队列处理类
 */
@SuppressWarnings("rawtypes")
public class  AddUserAccountExcuteMessageServiceImpl {
	private static Logger logger = LogManager.getLogger(AddUserAccountExcuteMessageServiceImpl.class);
	
	private AddUserAccountImpl addUserAccountImpl;
	private InitUserAccountTool initUserAccountTool;
	
	public void doService(String msg){
		Map msgMap = this.analysis(msg);
		logger.info("[" + (String)msgMap.get("reqId") + "]@@" +"[收到账户初始化消息！！！]"+msg);
		try {
			addUserAccountImpl.execute(msgMap);
		} catch (Exception e) {
			logger.error("资金账户初始化异常,消息重新放入队列",e);
			try {
				initUserAccountTool.sendMessage(msg);
			} catch (Exception e1) {
				logger.error("初始化资金账户重试队列加入失败，需要手工处理！！！@@" + msg, e1);
			}
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
	public AddUserAccountImpl getAddUserAccountImpl() {
		return addUserAccountImpl;
	}
	public void setAddUserAccountImpl(AddUserAccountImpl addUserAccountImpl) {
		this.addUserAccountImpl = addUserAccountImpl;
	}
	public InitUserAccountTool getInitUserAccountTool() {
		return initUserAccountTool;
	}
	public void setInitUserAccountTool(InitUserAccountTool initUserAccountTool) {
		this.initUserAccountTool = initUserAccountTool;
	}
	
	
	
 
}
