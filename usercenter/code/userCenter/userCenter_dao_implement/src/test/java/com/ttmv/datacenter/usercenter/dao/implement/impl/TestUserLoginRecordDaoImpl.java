package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.datacenter.usercenter.domain.data.record.UserLoginRecord;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserLoginRecordQuery;

public class TestUserLoginRecordDaoImpl {
	
	private static ApplicationContext context = null;
	private static UserLoginRecordDaoImpl userLoginRecordDaoImpl = null;
	
	static {
		context = new ClassPathXmlApplicationContext("spring/spring.xml");
		userLoginRecordDaoImpl = (UserLoginRecordDaoImpl)context.getBean("userLoginRecordDaoImpl");
	}
	
	@Test
	public void addUserLoingRecord(){
		UserLoginRecord record = new UserLoginRecord();
		record.setAddress("北京市");
		record.setTTnum(new BigInteger("10000000"));
		record.setTime(new Date());
		record.setType(1);
		try {
			userLoginRecordDaoImpl.addUserLoginRecord(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectiveLoginRecord()throws Exception{
		UserLoginRecordQuery query  = new UserLoginRecordQuery();
		query.setTTnum(new BigInteger("10000000"));
		query.setType(0);
		List<UserLoginRecord> list = userLoginRecordDaoImpl.selectListBySelective(query);
		System.out.println(list.get(0).getTime());
	}
}
