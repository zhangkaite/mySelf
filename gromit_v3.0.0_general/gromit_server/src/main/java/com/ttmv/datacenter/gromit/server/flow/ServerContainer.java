package com.ttmv.datacenter.gromit.server.flow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

import com.ttmv.datacenter.gromit.gromitService.GromitService;
import com.ttmv.datacenter.gromit.gromitService.RMSParser;


/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月9日
 */
public class ServerContainer {

	private static final Logger logger = LogManager.getLogger(ServerContainer.class);

	public ApplicationContext context;
	public String rmsParserName;

	public ServerContainer(ApplicationContext context, String rmsParserName) {
		this.context = context;
		this.rmsParserName = rmsParserName;
	}

	/**
	 * 通过传入的service name 获得 service 的实现类
	 * 
	 * @throws Exception
	 * */
	public GromitService getService(String serviceName) {
		if (serviceName == null || serviceName.equals("")) {
			return null;
		}
		GromitService gromitService = null;
		try{
		// 获取serivce
		gromitService = (GromitService) context.getBean(serviceName);
		}catch(Exception e){
			logger.error("invalid bean named '"+serviceName+"' is defined !");
		}
		return gromitService;
	}

	/**
	 * 从容器中获取 RMSParser
	 * 
	 * @throws Exception
	 * */
	public RMSParser getRmsParser() {
		if (context == null || rmsParserName == null || rmsParserName.equals("")) {
			return null;
		}
		RMSParser rms = null;
		try{
		rms = (RMSParser) context.getBean(rmsParserName);
		}catch(Exception e){
			logger.error("invalid bean named '"+rmsParserName+"' is defined !");
		}
		return rms;
	}

	public ApplicationContext getContext() {
		return context;
	}
}
