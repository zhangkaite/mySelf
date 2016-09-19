package com.ttmv.datacenter.paycenter;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.datacenter.gromit.server.GromitServer;
import com.ttmv.datacenter.gromit.server.flow.ServerContainer;
import com.ttmv.datacenter.gromit.server.netty.NettyServer;
import com.ttmv.datacenter.hdsf.server.zookeeper.ZKCenterAgent;


/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月10日
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GromitStart {

	private static final Logger logger = LogManager.getLogger(GromitStart.class);

	public static ApplicationContext context;
//	private static Thread gromitThread;
	private static GromitServer gromitServer ;

	
	public static void main(String[] arge) {
		System.out.println("Gromit starting ......");
		try {
			 getSpringContainer();
		} catch (Exception e) {
			logger.error("Failed to start gromit\n"+e);
			return;
		}
		
		if(arge.length == 1){
			String strValue = arge[0];
			logger.debug("+++++++获取启动参数+++++++:"+strValue);
			if("zkOpen".equals(strValue)){
				logger.debug("++++++++++++[payCenter注册zk...]:"+strValue);
				Thread registered = new RegisteredService((ZKCenterAgent)context.getBean("zkCenterAgent"),"10002");
				registered.start();
			} 
		}
		
		
		System.out.println("Start server ......");
		Map map = new HashMap();
		map.put("tmcp_port", 10002);
		map.put("tmcp_executor_size", "30");
		map.put("http_port", 8081);
		map.put("http_executor_size", "100");
        
		ServerContainer serverContainer = new ServerContainer(context, "RMSParser");
		gromitServer = new NettyServer(map, serverContainer);
		gromitServer.start();
		System.out.println("Listening the http port:" + map.get("http_port"));
		System.out.println("Listening the tmcp port:" + map.get("tmcp_port"));
	}

	public static void getSpringContainer() {
		System.out.println("根据classpath下的spring配置文件，初始化spring容器");
		context = new ClassPathXmlApplicationContext("spring-gromit.xml",
				"spring-middleware.xml","spring-protocol-bean.xml",
				"spring-hbase-implement.xml","spring-activemq.xml",
				"spring-gromit.xml","spring-middleware.xml",
				"spring-mysql-implement.xml","spring-redis-implement.xml",
				"spring-service.xml","spring.xml","spring-worker-mq.xml",
				"spring-timer.xml","spring-flow.xml");
	}
	
}                                     
