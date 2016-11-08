package com.ttmv.datacenter.gromit.gromitService;

import com.ttmv.datacenter.message.tmcp.TmcpMessage;

public interface  RMSParser {

	public  String getServiceName(TmcpMessage msg)  throws Exception;
    
	public String getServiceName(String string) throws Exception;
}