package com.ttmv.datacenter.usercenter.service.processor.tools;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.generator.GUID.GUIDGenerator;

/**
 * 用户ID生成 
 * @author Scarlett.zhou
 * @date 2015年1月21日
 */
public class UserIdGenerate {

	private static final Logger logger = LogManager.getLogger(UserIdGenerate.class);;
	private GUIDGenerator generator;
    public UserIdGenerate(GUIDGenerator generator) {
		this.generator = generator;
	}

	public BigInteger getUserId() {
		BigInteger id ;
		try {
		   id = new BigInteger(String.valueOf(generator.guid("UC_user_id")));
		} catch (Exception e) {
			logger.error("生成 user id 异常!",e);
			return null;
		}
	    return id;
	}
}
