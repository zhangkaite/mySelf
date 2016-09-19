package com.ttmv.datacenter.usercenter;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ttmv.datacenter.gromit.gromitService.RMSParser;
import com.ttmv.datacenter.message.tmcp.TmcpMessage;
import com.ttmv.datacenter.utils.check.CheckParameterUtil;

/**
 *  GromitRMSParser
 * @author Scarlett.zhou
 * @date 2015年1月13日
 */
public class GromitRMSParser implements RMSParser {

	private final static Logger logger = LogManager
			.getLogger(GromitRMSParser.class);

	public String getServiceName(TmcpMessage msg) throws Exception {
		if(msg == null){
			return null;
		}
		String data = msg.getData();
		Map map = new ObjectMapper().readValue(data, Map.class);
		if(map == null){
			return null;
		}
		String serviceName = (String) map.get("service");
		if(CheckParameterUtil.checkIsEmpty(serviceName)){
			return null;
		}
		return serviceName + "ServiceImpl";
	}

	public String getServiceName(String serviceName) throws Exception {
		if(serviceName == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		String[] str = serviceName.split("\\.do");
		if (str.length > 0) {
			sb.append(str[0]);
		}else{
			return null;
		}
		return sb.append("ServiceImpl").toString();
	}

}
