package com.ttmv.datacenter.usercenter;

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
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GromitStart {

	private static final Logger logger = LogManager.getLogger(GromitStart.class);

	public static ApplicationContext context;
	private static GromitServer gromitServer ;


	public static void main(String[] arge) {
		logger.info("Gromit starting ......");
		try {
			getSpringContainer();
		} catch (Exception e) {
			logger.error("Failed to start gromit\n" + e);
			return;
		}
		if(arge.length == 1){
			String strValue = arge[0];
			logger.debug("+++++++获取启动参数+++++++:"+strValue);
			if("zkOpen".equals(strValue)){
				logger.debug("++++++++++++[注册zk...]:"+strValue);
				Thread registered = new RegisteredService((ZKCenterAgent)context.getBean("zkCenterAgent"),"10001");
				registered.start();
			}
		}
		
		logger.info("Start server ......");
		Map map = new HashMap();
		map.put("tmcp_port", 10001);
		map.put("tmcp_executor_size", "10");
		map.put("http_port", 8080);
		map.put("http_executor_size", "10");
        
		ServerContainer serverContainer = new ServerContainer(context, "RMSParser");
		gromitServer = new NettyServer(map, serverContainer);
		gromitServer.start();
		logger.info("Listening the http port:" + map.get("http_port"));
		logger.info("Listening the tmcp port:" + map.get("tmcp_port"));
	}

	public static void getSpringContainer() {
		logger.info("根据classpath下的spring配置文件，初始化spring容器");
		context = new ClassPathXmlApplicationContext("spring.xml","spring-activemq.xml",
				"spring-gromit.xml","spring-hbase-implement.xml",
				"spring-middleware.xml","spring-mybatis-datasource.xml",
				"spring-mysql-implement.xml","spring-protocol-bean.xml",
				"spring-redis-implement.xml","spring-service-activemq.xml",
				"spring-service.xml","spring-solr-implement.xml","spring-worker-mq.xml","spring-work-service.xml"
				);
	}
}
