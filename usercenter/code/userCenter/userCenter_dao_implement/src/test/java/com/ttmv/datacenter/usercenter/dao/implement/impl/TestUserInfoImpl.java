package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.GroupQuery;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;

public class TestUserInfoImpl {

	private static ApplicationContext context = null;
	private static UserInfoDaoImpl imp = null;
	private static GroupDaoImpl groupDaoImpl = null;
	static {
		context = new ClassPathXmlApplicationContext("spring/spring.xml");
		 imp = (UserInfoDaoImpl)context.getBean("userInfoDaoImpl");
		 groupDaoImpl = (GroupDaoImpl)context.getBean("groupDaoImpl");
	}
	
	@Test
	public void addUserInfo(){
		
		UserInfo userInfo = new UserInfo();
		userInfo.setNickName("ffffffff");
		userInfo.setMobile("18610416271");
		userInfo.setEmail("ffffff@qq.com");
		userInfo.setAdminId(new BigInteger("147258"));
		userInfo.setUserName("wulinli");
		userInfo.setUserid(new BigInteger("654321"));
		userInfo.setUserPhoto("http://345687");
		userInfo.setReason("reason");
		userInfo.setTTnum(new BigInteger("963852"));
		userInfo.setPassword("111111");
		userInfo.setNickName("74 85 96 12");
		userInfo.setSex(1);
		userInfo.setTime(new Date());
		userInfo.setState(0);
		userInfo.setUtype(0);
		userInfo.setVipType(2);
		userInfo.setAddress("北京市朝阳区");
		userInfo.setCity("北京市");
		try {
			long start = System.currentTimeMillis();
			imp.addUserInfo(userInfo);
			System.out.println("==============================="+(System.currentTimeMillis() - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void update()throws Exception{
		UserInfo userInfo = new UserInfo();
		userInfo.setUtype(1);
		userInfo.setUserid(new BigInteger("963875"));
		userInfo.setPassword("111111");
		userInfo.setVipType(1);
		try {
			imp.updateUserInfo(userInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void userInfoLogin(){
		UserInfoQuery query = new UserInfoQuery();
		query.setUserName("wulinli1");
		query.setPassword("111111");
		try {
				long start = System.currentTimeMillis();
				List<UserInfo> userInfos = imp.userLogin(query);
				System.out.println(userInfos.get(0).getVipType());
				long end = System.currentTimeMillis();
				System.out.println("查询结果毫秒数："+(end - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void selectUserInfoList(){
//		UserInfoQuery query = new UserInfoQuery();
//		query.setPage(1);
//		query.setPageSize(50);
//		query.setUserName("ttmvsdk");
//		try {
//				long start = System.currentTimeMillis();
//				List<UserInfo> userInfos = imp.selectListBySelectivePaging(query);
//				for (int i = 0; i < userInfos.size(); i++) {
//					UserInfo user = userInfos.get(i);
//					GroupQuery qu = new GroupQuery();
//					qu.setUserId(user.getUserid());
//					List<Group> list = groupDaoImpl.selectListBySelective(qu);
//					if(list != null ){
//						Group group = list.get(0);
//						if(group.getName().equals("我的好友")){
//							group.setDefaultType(1);
//							group.setGorder(1);
//						}
//						if(group.getName().equals("黑名单")){
//							group.setDefaultType(0);
//							group.setGorder(10000);
//						}
//						groupDaoImpl.updateUgroup(group);
//					}
//				}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void selectOne(){
		BigInteger userid = new BigInteger("963875");
		try {
			long start = System.currentTimeMillis();
			UserInfo userInfo = imp.selectUserInfoByUserId(userid);
			System.out.println(userInfo.getUtype());
			System.out.println(userInfo.getUserName());
			long end = System.currentTimeMillis();
			System.out.println("查询结果毫秒数："+(end - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectOneByTTnum(){
		String ttnum = "963852";
		try {
			long start = System.currentTimeMillis();
			UserInfo userInfo = imp.selectUserInfoByTTNum(ttnum);
			System.out.println(userInfo.getUserName());
			long end = System.currentTimeMillis();
			System.out.println("查询结果毫秒数："+(end - start));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectListByIds(){
		List list = new ArrayList();
		BigInteger id = new BigInteger("654321");
		list.add(id);
		id = new BigInteger("123456");
		list.add(id);
		try{			
			List<UserInfo> userInfos = imp.selectUserInfosByIds(list);
			System.out.println(userInfos.size());
			System.out.println(userInfos.get(1).getNickName());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void changeutype() throws Exception{
//		UserInfoQuery query = new UserInfoQuery();
//		query.setPageSize(999);
//		List<UserInfo> userInfos =imp.selectListBySelectivePaging(query);
//		for(int i=0;i<userInfos.size();i++){
//			UserInfo userInfo = userInfos.get(i);
//			imp.updateUserInfo(userInfo);
//		}
//		System.out.println(userInfos.size());
//	}
	
}
