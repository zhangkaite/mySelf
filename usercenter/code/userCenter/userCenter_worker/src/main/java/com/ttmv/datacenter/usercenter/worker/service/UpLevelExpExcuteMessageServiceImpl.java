package com.ttmv.datacenter.usercenter.worker.service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ttmv.datacenter.usercenter.domain.protocol.LevelCallBack;
import com.ttmv.datacenter.usercenter.service.facade.impl.overCallBack.LevelCallBackServiceImpl;

@SuppressWarnings("rawtypes")
public class UpLevelExpExcuteMessageServiceImpl {
	private static Logger logger = LogManager.getLogger(UpLevelExpExcuteMessageServiceImpl.class);
	private LevelCallBackServiceImpl levelCallBackServiceImpl;
	
	public void doService(String msg){
		Map msgMap = this.analysis(msg);
		String reqID = "DAMS_"+System.currentTimeMillis();
 		logger.info("[" + reqID + "]@@" +"收到等级经验变更消息：----->>>>" + msg); 
		//组装 LevelCallBack
		LevelCallBack callBack = new LevelCallBack();
		callBack.setUserID(new BigInteger(msgMap.get("userID").toString()));
		if(msgMap.get("announcerExp")!=null){
			callBack.setAnnouncerExp(new BigInteger(msgMap.get("announcerExp").toString()));
		}
		if(msgMap.get("exp")!=null){
			callBack.setExp(new BigInteger(msgMap.get("exp").toString()));
		}
		if(msgMap.get("announcerLevel")!=null){
			callBack.setAnnouncerLevel(Integer.parseInt(msgMap.get("announcerLevel").toString()));
		}
		if(msgMap.get("userLevel")!=null){
			callBack.setUserLevel(Integer.parseInt(msgMap.get("userLevel").toString()));
		}
		levelCallBackServiceImpl.handler(callBack, reqID);
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
	public LevelCallBackServiceImpl getLevelCallBackServiceImpl() {
		return levelCallBackServiceImpl;
	}
	public void setLevelCallBackServiceImpl(
			LevelCallBackServiceImpl levelCallBackServiceImpl) {
		this.levelCallBackServiceImpl = levelCallBackServiceImpl;
	}
	
	

}
