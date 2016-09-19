package com.ttmv.datacenter.usercenter.worker.service;

import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.interfaces.GroupDao;
import com.ttmv.datacenter.usercenter.domain.data.Group;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年2月4日 上午12:51:38
 * @explain :注册用户 异步队列
 */
public class AddUserServiceImpl {
	private static Logger logger = LogManager.getLogger(AddUserServiceImpl.class);

	private GroupDao groupDao;

	protected void execute(Map msgMap) {
		logger.info("[" + msgMap.get("reqId") + "]@@" +"[worker执行---好友分组初始化！！！]");
		Group ugroup = new Group();
		ugroup.setUserId(new BigInteger(msgMap.get("userID").toString()));
		ugroup.setDefaultType(new Integer(1));
		ugroup.setGorder(new Integer(1));
		ugroup.setName("我的好友");
		ugroup.setReqId(msgMap.get("reqId")+"");
		Group ugroup2 = new Group();
		ugroup2.setUserId(new BigInteger(msgMap.get("userID").toString()));
		ugroup2.setDefaultType(new Integer(0));
		ugroup2.setGorder(new Integer(10000));
		ugroup2.setName("黑名单");
		ugroup2.setReqId(msgMap.get("reqId")+"");
		try {
			 groupDao.addUgroup(ugroup);
			 groupDao.addUgroup(ugroup2);
		} catch (Exception e) {
			logger.error("[" + msgMap.get("reqId") + "]@@" +"[好友默认分组初始化失败！！！]"+e.getMessage());
			e.printStackTrace();
		}
		logger.info("[" + msgMap.get("reqId") + "]@@" +"[***worker执行完成：[我的好友]、[黑名单]初始化完成***]");
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
