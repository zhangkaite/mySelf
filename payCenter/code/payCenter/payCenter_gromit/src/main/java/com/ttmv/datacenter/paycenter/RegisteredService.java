package com.ttmv.datacenter.paycenter;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.hdsf.server.domain.RegistableService;
import com.ttmv.datacenter.hdsf.server.zookeeper.ZKCenterAgent;

public class RegisteredService  extends Thread {
	
	private static final Logger logger = LogManager.getLogger(RegisteredService.class);
	private static ZKCenterAgent zkCenterAgent;
	private static String port;
	
	public RegisteredService(ZKCenterAgent zkCenterAgent,String port){
		this.zkCenterAgent = zkCenterAgent;
		this.port = port;
	}

	public void run() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			Map<String, String> serverConfig = new HashMap<String, String>();
			serverConfig.put("ipv4", addr.getHostAddress().toString());
			serverConfig.put("port", port);
			logger.info("[payCenter注册应用服务IP]:" + addr.getHostAddress().toString()  +"\n"  +"[端口]:" + port);
			for (String servicePath : getServicePath()) {
				zkCenterAgent.setService(new RegistableService(servicePath,
						serverConfig));
			}
			zkCenterAgent.regist();
		} catch (Exception e) {
			logger.error("注册服务出现异常 ", e);
		}
	}
	
	private static List<String> getServicePath(){
		List<String> list = new ArrayList<String>();;
		list.add("/com/ttmv/service/data-center/pay-center/tBRecharge");
	    list.add("/com/ttmv/service/data-center/pay-center/tQRecharge");
	    list.add("/com/ttmv/service/data-center/pay-center/brokerageRecharge");
	    list.add("/com/ttmv/service/data-center/pay-center/tBConsume");
	    list.add("/com/ttmv/service/data-center/pay-center/tQConsume");
	    list.add("/com/ttmv/service/data-center/pay-center/brokerageConsume");
	    list.add("/com/ttmv/service/data-center/pay-center/userQueryAccountBalance");
	    list.add("/com/ttmv/service/data-center/pay-center/tQorTBConsume");//新增接口 2016年3月22日15:27:21
		return list;
	}
}
