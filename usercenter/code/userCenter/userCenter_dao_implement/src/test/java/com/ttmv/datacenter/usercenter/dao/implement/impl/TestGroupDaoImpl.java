package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.operation.query.GroupQuery;

public class TestGroupDaoImpl {
	private static ApplicationContext context = null;
	private static GroupDaoImpl imp = null;

	static {
		context = new ClassPathXmlApplicationContext("spring/spring.xml");
		 imp = (GroupDaoImpl)context.getBean("groupDaoImpl");
	}
	
	/**
	 * 添加Group
	 * 用户分组，
	 */
	@Test
	public void addGroup(){
		Group group  = new Group();
		group.setGroupId(new BigInteger("3333337"));
		group.setUserId(new BigInteger("9999999"));
		group.setName("我的好友");
		group.setGorder(1);
		group.setDefaultType(1);
		try {
			imp.addUgroup(group);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改group
	 */
	@Test
	public void updateGroup(){
		Group group = new Group();
		group.setGroupId(new BigInteger("3333335"));
		group.setName("大学同学");
		try {
			imp.updateUgroup(group);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除Group
	 */
	@Test
	public void deleteGroup(){
		BigInteger id = new BigInteger("3333334");
		try{
			imp.deleteUgroup(id,"a123");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过id查询Group
	 */
	@Test
	public void getGroupById(){
		BigInteger id = new BigInteger("3333333");
		try{
			Group group  = imp.selectUgroup(id);
			System.out.println(group.getName());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过GroupQuery对象查询Group的集合
	 */
	@Test
	public void getListGroup(){
		GroupQuery query = new GroupQuery();
		//query.setGroupId(new BigInteger("3333333"));
		query.setUserId(new BigInteger("9999999"));
		try{
			List<Group> groups =imp.selectListBySelective(query);
			System.out.println(groups.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
