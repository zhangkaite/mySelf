package com.ttmv.datacenter.usercenter;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.ttmv.datacenter.message.tmcp.TmcpMessage;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月19日
 */
public class GromitRMSParserTest {
	private TmcpMessage message;
	private String data = "{\"service\":\"userAdd\",\"reqData\":{\"loginName\":\"damon\",\"password\":\"1\"},\"time\":\"2014-12-06\",\"reqID\":\"111\",\"form\":\"gromit_test\",\"timeStamp\":\"2014-12-06\",\"tradeType\":\"1\"}\n";;

	@Before
	public void before() throws Exception {
		message = new TmcpMessage();
		message.setVersion((byte) 1);
		message.setCrypt((byte) 1);
		message.setLength(data.getBytes("UTF-8").length);
		message.setMode(2);
		message.setContentType(1);
		message.setCommand(1);
		message.setUserAgentId(1);
		message.setSequence(10086);
		message.setError(1);
		message.setRes(4);
		message.setData(data);
	}

	@Test
	public void testGetServiceNameA() {

	}

	@Test
	public void testGetServiceNameB() {

	}
}
