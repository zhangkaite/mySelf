package com.ttmv.datacenter.usercenter.dao.implement.hbase;

import java.math.BigInteger;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.HbaseUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.HbaseUserInfoMapper;

public class TestUserInfoHbaseInfo {

	private static HbaseUserInfoMapper hbaseUserInfoMapper = null;
	private static ApplicationContext context = null;

	static{
		context = new ClassPathXmlApplicationContext("spring/spring.xml");
		hbaseUserInfoMapper = (HbaseUserInfoMapper)context.getBean("hbaseUserInfoMapper");
	}
	
	/**
	 * 添加hbase
	 */
	@Test
	public void addHbaseUserInfo(){
		HbaseUserInfo hbase = new HbaseUserInfo();
		hbase.setCity("北京市");
		hbase.setUserid(new BigInteger("456789"));
		try {
			hbaseUserInfoMapper.addHbaseUserInfo(hbase, hbase.getUserid().toString(),"");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询HbaseUserInfo对象
	 */
	@Test
	public void selectHbaseUserInfo(){
		HbaseUserInfo hbase;
		try {
			hbase = hbaseUserInfoMapper.getHbaseUserInfoById("456789");
			System.out.println(hbase.getCity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
