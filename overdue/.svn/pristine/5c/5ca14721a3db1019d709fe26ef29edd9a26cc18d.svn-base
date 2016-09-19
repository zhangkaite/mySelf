package com.ttmv.activemq.service;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.ttmv.service.input.impl.added.InOverdueVipServiceImpl;
import com.ttmv.service.input.impl.added.ManageGoodTTNumServiceImpl;
import com.ttmv.service.input.impl.control.InOverdueForbiddenUserServiceImpl;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 下午11:48:13  
 * @explain :日志记录队列处理类
 */
@SuppressWarnings("rawtypes")
@Service("ucOverdueServiceBase")
public class  UcOverdueServiceBase {
	private static Logger logger = LogManager.getLogger(UcOverdueServiceBase.class);
	@Resource(name="inOverdueVipServiceImpl")
	private InOverdueVipServiceImpl inOverdueVipServiceImpl;
	
	@Resource(name="inOverdueForbiddenUserServiceImpl")
	private InOverdueForbiddenUserServiceImpl inOverdueForbiddenUserServiceImpl;
	
	@Resource(name="manageGoodTTNumServiceImpl")
	private ManageGoodTTNumServiceImpl manageGoodTTNumServiceImpl;
	
	public void doService(String msg){
		Map msgMap = this.analysis(msg);
		logger.info("[" + msgMap.get("reqId") + "]@@" +"收到userCenter到期消息：========:" + msg); 
		String service = (String)msgMap.get("service");
		if("openingVIP".equals(service)){//开通会员
			inOverdueVipServiceImpl.handler(msgMap);
		}else if("forbiddenUser".equals(service)){//冻结用户
			inOverdueForbiddenUserServiceImpl.handler(msgMap);
		}else if("manageTTnum".equals(service)){//靓号绑定与解绑
			manageGoodTTNumServiceImpl.handler(msgMap);
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


	
	

}
