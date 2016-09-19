package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserCrossRelationQuery;

public class TestUserCrossRelationDaoImp {

	private static ApplicationContext context = null;
	private static UserCrossRelationDaoImpl imp = null;

	static {
		context = new ClassPathXmlApplicationContext("spring/spring.xml");
		 imp = (UserCrossRelationDaoImpl)context.getBean("userCrossRelationDaoImpl");
	}
	
	/**
	 * 添加用户关系
	 * 
	 */
	@Test
	public void addUserCrossRelation(){
		UserCrossRelation cross = new UserCrossRelation();
		cross.setId(new BigInteger("2"));
		cross.setGroupId(new BigInteger("1"));
		cross.setUserIdA(new BigInteger("1"));
		cross.setUserIdB(new BigInteger("3"));
		cross.setType(1);
		cross.setRemarkName("相同组第二个好友");
		try{
			imp.addUserCrossRelation(cross);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 *修改用户关系
	 *只能修改好友的备注和好友的分组
	 */
	@Test
	public void updateUserCrossRelation(){
		UserCrossRelation cross = new UserCrossRelation();
		cross.setId(new BigInteger("3333333"));
		cross.setRemarkName("我的好友");
		try{
			imp.updateUserCrossRelation(cross);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除好友
	 */
	@Test
	public void deleteUserCrossRelation(){
		BigInteger id = new BigInteger("444444");
		try{
			imp.deleteUserCrossRelation(id,"aa");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据Id查询UserCrossRelation
	 */
	@Test
	public void selectUserCrossRelation(){
		BigInteger id = new BigInteger("444444");
		try{
			UserCrossRelation cross = imp.selectUserCrossRelation(id);
			System.out.println(cross.getRemarkName());
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
	
	/**
	 * 根据userIdA查询用户关系
	 */
	@Test
	public void selectByUserIdA(){
		UserCrossRelationQuery query  = new UserCrossRelationQuery();
		query.setUserIdA(new BigInteger("1"));
		try{
			List<UserCrossRelation> list = imp.selectListBySelective(query);
			if(list != null && list.size() > 0 ){
				for(int i = 0; i< list.size();i++){					
					UserCrossRelation user = list.get(i);
					System.out.println(user.getUserIdB());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据groupId查询用户关系
	 */
	@Test
	public void selectByGroupId(){
		UserCrossRelationQuery query  = new UserCrossRelationQuery();
		query.setGroupId(new BigInteger("1"));
		try{
			List<UserCrossRelation> list = imp.selectListBySelective(query);
			if(list != null && list.size() > 0 ){
				for(int i = 0; i< list.size();i++){					
					UserCrossRelation user = list.get(i);
					System.out.println(user.getUserIdB());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
