package com.ttmv.monitoring;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.UserInfoQuery;
import com.ttmv.monitoring.imp.IUserInfoInterImpl;

public class TestUserInfo {
	
	@Test
	public void addUserInfo(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUserInfoInterImpl userInfoDaoImpl = (IUserInfoInterImpl)context.getBean("iUserInfoInterImpl");
		
		UserInfo userInfo = new UserInfo();
		userInfo.setCreateTime(new Date());
		userInfo.setRemark("这是一个什么啊");
		userInfo.setUserMail("wshnbe@sina.com");
		userInfo.setUserMobile("18610416270");
		userInfo.setUserName("wll");
		userInfo.setUserPasswd("123456");
		userInfo.setUserRealName("吴林立");
		userInfo.setUserStatus(new Integer(1));
		try {			
			Integer result = userInfoDaoImpl.addUserInfo(userInfo);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryUserInfo(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUserInfoInterImpl userInfoDaoImpl = (IUserInfoInterImpl)context.getBean("iUserInfoInterImpl");
		try {
			UserInfo userInfo = userInfoDaoImpl.queryUserInfo(new BigInteger("1"));
			System.out.println(userInfo.getUserRealName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void queryUserInfoList(){	
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUserInfoInterImpl userInfoDaoImpl = (IUserInfoInterImpl)context.getBean("iUserInfoInterImpl");
		
		UserInfoQuery userInfo = new UserInfoQuery();
		userInfo.setUserRealName("吴林立");
		try {
			List<UserInfo> list = userInfoDaoImpl.queryUserInfo(userInfo);
			System.out.println(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateUserInfo(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUserInfoInterImpl userInfoDaoImpl = (IUserInfoInterImpl)context.getBean("iUserInfoInterImpl");
		UserInfo userInfo = new UserInfo();
		userInfo.setId(new BigInteger("1"));
		userInfo.setRemark("这是吴林立的一个账号！");
		try {
			Integer result = userInfoDaoImpl.updateUserInfo(userInfo);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试分页查询功能
	 */
	@Test
	public void selectPageUserInfo(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUserInfoInterImpl userInfoDaoImpl = (IUserInfoInterImpl)context.getBean("iUserInfoInterImpl");
		UserInfoQuery query = new UserInfoQuery();
		query.setPage(1);
		query.setPageSize(20);
		query.setUserName("l");
		query.setUserStatus(new Integer("1"));
		try {
			Page p = userInfoDaoImpl.queryPageUserInfo(query);
			List list = p.getData();
			for(int i=0;i<list.size();i++){
				UserInfo user = (UserInfo)list.get(i);
				System.out.println(user.getId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试精确查询功能
	 * @throws Exception 
	 */
	@Test
	public void selectUserInfo(){
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IUserInfoInterImpl userInfoDaoImpl = (IUserInfoInterImpl)context.getBean("iUserInfoInterImpl");
		UserInfoQuery query = new UserInfoQuery();
		query.setUserRealName("吴林立");
		query.setUserStatus(new Integer("9"));
		List<UserInfo> list;
		try {
			list = userInfoDaoImpl.queryUserInfo(query);
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
