package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ttmv.datacenter.rdbcluster.RDBCluster;
import com.ttmv.datacenter.usercenter.dao.implement.constant.SolrConstant;
import com.ttmv.datacenter.usercenter.dao.implement.constant.UserInfoConstant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.temp.Item;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.HbaseUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.MysqlUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.SolrUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.HbaseUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.MysqlUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.RedisUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.SolrUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.implement.util.SolrUtil;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserInfoQuery;

public class UserInfoDaoImpl implements UserInfoDao {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(UserInfoDaoImpl.class);
	/* 日期转换solr */
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	/* 用户修改信息 队列 */
	private Destination updateUserInfoDestination;
	/* 用户注册信息 队列 */
	private Destination addUserInfoDestination;
	private SolrUserInfoMapper solrUserInfoMapper;
	private HbaseUserInfoMapper hbaseUserInfoMapper;
	private RedisUserInfoMapper redisUserInfoMapper;
	private MysqlUserInfoMapper mysqlUserInfoMapper;
	private JmsTemplate jmsTemplate;
	private RDBCluster rDBCluster;
	
	/**
	 * 注册用户 业务：添加mq队列，添加redis，添加solr
	 */
	public Integer addUserInfo(UserInfo userInfo) throws Exception {

		long start = System.currentTimeMillis();
		if (userInfo == null) {
			log.debug("userInfo不能为Null！");
			throw new Exception("UserInfo对象不能为Null！");
		}
		String dataSourceKey = rDBCluster.getOneMaster();
		log.debug("[" + userInfo.getReqId() + "]@@" + "获取dataSourceKey：" + dataSourceKey);
		log.debug("[" + userInfo.getReqId() + "]@@" + "[准备添加UserInfo！开始。。]");
		/* 向队列中添加数据 */
		Map addUserMap = new HashMap<String,Object>();
		addUserMap.put("service", UserInfoConstant.ADD_USERINFO);
		addUserMap.put("data",userInfo);
		addUserMap.put("count", new Integer(0));
		addUserMap.put("reqID", userInfo.getReqId());
		addUserMap.put("dataSourceKey", dataSourceKey);
		
		final String userInfoJson = JsonUtil.getObjectToJson(addUserMap);
		log.debug("[" + userInfo.getReqId() + "]@@" + "[生成添加addUserInfoDestination队列的json数据成功！]");
		try {
			jmsTemplate.send(addUserInfoDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(userInfoJson);
				}
			});
			log.debug("[" + userInfo.getReqId() + "]@@" + "[向addUserInfoDestination队列中添加json成功！]");
		} catch (Exception e) {
			/* 如果出现添加队列失败，则抛出添加队列失败的错误的信息，并且添加数据失败 */
			log.error("[" + userInfo.getReqId() + "]@@" + "[向addUserInfoDestination队列中添加json失败！]" ,e);
			throw new Exception("[" + userInfo.getReqId() + "]@@" + "[向addUserInfoDestination队列中添加json失败！]",e);
		}
		long mq = System.currentTimeMillis();
		log.debug("dao层用户注册MQ耗时："+ (mq - start));
		/* 向redis 中添加数据 */
		try {
			String redisJson = JsonUtil.getObjectToJson(userInfo);
			log.debug("[" + userInfo.getReqId() + "]@@" + "[生成UserInfo对象的redis的json数据成功！]");
			redisUserInfoMapper.addUserInfoInRedis(userInfo.getUserid().toString(), redisJson,userInfo.getReqId());
		} catch (Exception e) {
			/* 如果添加队列成功，而添加redis失败，则提示用户注册成功！数据不断的从队列中同步数据到redis中 */
			log.error("[" + userInfo.getReqId() + "]@@" + "[注册用户成功！但是添加数据到redis时失败！]",e);
			return UserInfoConstant.ACCEPT;
		}
		long redis = System.currentTimeMillis();
		log.debug("dao层用户注册Redis耗时："+ (redis - mq));
		/* 向solr中添加数据 */
		try {
				SolrUserInfo solr = solrUserInfoMapper.getConvertToSolrUserInfo(userInfo);
				solr.setDataSourceKey(dataSourceKey);
				log.debug("[" + userInfo.getReqId() + "]@@" + "[生成UserInfo对象的Solr的json数据成功！]");
				solrUserInfoMapper.addSolrUserInfoDelay(solr,userInfo.getReqId());
		} catch (Exception e) {
				log.error("[" + userInfo.getReqId() + "]@@" + "[添加Solr的SolrUserInfo失败！]",e);
				throw new Exception("[" + userInfo.getReqId() + "]@@" + "[添加Solr的SolrUserInfo失败！]",e);
		}
		log.debug("[" + userInfo.getReqId() + "]@@" + "[添加UserInfo成功！结束。。]");
		long end = System.currentTimeMillis();
		log.debug("dao层用户注册Solr耗时："+ (end - redis));
		log.debug("dao层注册用户耗时：" + (end - start));
		return UserInfoConstant.SUCCESS;
	}

	/**
	 * 修改用户信息
	 */
	public Integer updateUserInfo(UserInfo userInfo) throws Exception {

		long start = System.currentTimeMillis();
		if (userInfo == null) {
			log.debug("userInfo不能为Null！");
			throw new Exception("UserInfo对象不能为空！");
		}

		int result = -1;
		log.debug("[" + userInfo.getReqId() + "]@@" + "[准备修改UserInfo！开始。。]");
		/* 向队列中添加数据 */
		Map updateUserMap = new HashMap<String,Object>();
		updateUserMap.put("service", UserInfoConstant.UPD_USERINFO);
		updateUserMap.put("data",userInfo );
		updateUserMap.put("count", new Integer(0));
		updateUserMap.put("reqID", userInfo.getReqId());
		
		final String userInfoJson = JsonUtil.getObjectToJson(updateUserMap);
		log.debug("[" + userInfo.getReqId() + "]@@" + "[生成添加updateUserInfoDestination队列的json数据成功！]");
		try {
			jmsTemplate.send(updateUserInfoDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(userInfoJson);
				}
			});
			log.debug("[" + userInfo.getReqId() + "]@@" + "[向updateUserInfoDestination队列中添加json成功！]");
		} catch (Exception e) {
			log.error("[" + userInfo.getReqId() + "]@@" + "[向updateUserInfoDestination队列中添加json失败！]" ,e);
			throw new Exception("[" + userInfo.getReqId() + "]@@" + "[向updateUserInfoDestination队列中添加json失败！]" ,e);
		}
		long mq = System.currentTimeMillis();
		log.debug("dao层修改用户MQ耗时："+ (mq - start));
		/* 修改redis中的数据 */
		try {
			result = this.updateRedisUserInfo(userInfo);
			result = UserInfoConstant.SUCCESS;
		} catch (Exception e) {
			log.error("[" + userInfo.getReqId() + "]@@" + "[修改redis用户信息失败！]",e);
			result = UserInfoConstant.ACCEPT;
		}
		long redis = System.currentTimeMillis();
		log.debug("dao层修改用户redis耗时："+ (redis -mq));
		
		/* 修改solr */
		try {
				SolrUserInfo solr = solrUserInfoMapper.getSolrUserInfoByKey(SolrConstant.SOLR_KEY_ID, userInfo.getUserid().toString(), SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE);
				log.debug("[" + userInfo.getReqId() + "]@@" + "[修改用户信息，查询solr成功！]");
				if(solr == null){
					log.debug("[" + userInfo.getReqId() + "]@@" + "[修改用户信息，查询solr成功，但对象为Null！]");
					return UserInfoConstant.OBJECT_NULL;
				}
				solr = solrUserInfoMapper.getConvertToSolrUserInfo(userInfo,solr);
				log.debug("[" + userInfo.getReqId() + "]@@" + "[userInfo对象的新值覆盖solr对象的旧值！]");
				solrUserInfoMapper.addSolrUserInfoDelay(solr,userInfo.getReqId());
				log.debug("[" + userInfo.getReqId() + "]@@" + "[修改Solr的SolrUserInfo成功！]");
				result = UserInfoConstant.SUCCESS;
		} catch (Exception e) {
			log.error("[" + userInfo.getReqId() + "]@@" + "[修改Solr的SolrUserInfo失败！]",e);
			throw new Exception("[" + userInfo.getReqId() + "]@@" + "[修改Solr的SolrUserInfo失败！]",e);
		}
		long solr = System.currentTimeMillis();
		log.debug("dao层修改用户solr耗时："+ (solr - redis));
		log.debug("dao层修改用户耗时："+ (solr - start));
		log.debug("[" + userInfo.getReqId() + "]@@" + "[修改UserInfo成功！结束。。]");
		return result ;
	}

	
	/**
	 * 根据Userid查询UserInfo的数据 数据的组成部分分为两个部分：redis 内存数据 和 数据库数据
	 */
	public UserInfo selectUserInfoByUserId(BigInteger userId) throws Exception {
		long start  = System.currentTimeMillis();
		if (userId == null) {
			log.debug("userId为null！");
			return null;
		}

		/* 查询Redis */
		UserInfo userInfo = redisUserInfoMapper.getUserInfoInRedis(userId.toString());
		if (userInfo != null) {
			log.debug("根据UserId查询redisUserInfo成功，userId是：" + userId);
			long redis = System.currentTimeMillis();
			log.debug("根据UserId查询UserInfo的数据耗时：" + (redis - start));
			return userInfo;
		}

		/* 查询solr数据 */
		Item item = solrUserInfoMapper.getItem(SolrConstant.SOLR_KEY_ID,userId.toString(), SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE);
		log.debug("根据UserId查询SolrUserInfo成功，userId是：" + userId);
		if (item == null) {
			log.debug("根据UserId查询SolrUserInfo成功，但是对象Null！");
			return null;
		}
		long solr = System.currentTimeMillis();
		log.debug("根据UserId查询UserInfo的数据查询solr耗时：" + (solr - start));
		log.debug("根据UserId查询SolrUserInfo成功，userId是：" + userId);
		 /* 根据Item 查询UserInfo */
		UserInfo result = this.getUserInfoByItem(item);
		long redis = System.currentTimeMillis();
		log.debug("根据UserId查询UserInfo的数据查询redis或mysql耗时：" + (redis - solr));
		if (result != null) {
			log.debug("根据UserId查询UserInfo成功，userId是：" + userId);
			try{
				/* 数据存放redis中 */
				String jsonData = JsonUtil.getObjectToJson(result);
				redisUserInfoMapper.addUserInfoInRedis(userId.toString(), jsonData,"return Data");
				long back = System.currentTimeMillis();
				log.debug("根据UserId查询UserInfo的数据放入redis耗时" + (back - redis ));
				log.debug("查询用户数据成功，数据存入redis成功！");
			}catch(Exception e){
				log.error("查询用户数据成功，数据存入redis失败！",e);
			}
			long end = System.currentTimeMillis();
			log.debug("根据UserId查询UserInfo的数据耗时" + (end - start ));
			return result;
		}
		return null;
	}

	
	/**
	 * 根据TTnum查询UserInfo 数据的组成部分分为两个部分：redis 内存数据 和 数据库数据
	 */
	public UserInfo selectUserInfoByTTNum(String ttnum) throws Exception {

		long start  = System.currentTimeMillis();
		if (ttnum == null) { 
			log.debug("ttnum为null！");
			return null;
		 }
		
		/* 查询solr数据 */
		Item item = solrUserInfoMapper.getItem(SolrConstant.SOLRUSERINFO_KEY_TTNUM, ttnum, SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE);
		long solr = System.currentTimeMillis();
		log.debug("根据TTnum查询UserInfo的数据查询solr耗时：" + (solr - start));
		log.debug("根据ttnum查询SolrUserInfo成功，ttnum是：" + ttnum);
		if (item == null) {
			long end = System.currentTimeMillis();
			log.debug("根据TTnum查询UserInfo的数据耗时：" + (end - start));
			log.debug("根据ttnum查询SolrUserInfo成功，但是对象Null！");
			return null;
		}
		log.debug("根据ttnum查询SolrUserInfo成功，ttnum是：" + ttnum);
		/* 查询Redis */
		UserInfo userInfo = redisUserInfoMapper.getUserInfoInRedis(item.getId().toString());
		if (userInfo != null) {
			long end = System.currentTimeMillis();
			log.debug("根据TTnum查询UserInfo的数据耗时：" + (end - start));
			log.debug("[根据TTnum查询redisUserInfo成功，TTnum是：" + ttnum);
			return userInfo;
		}
		
		/* 根据 Item 查询 UserInfo */
		UserInfo result = this.getUserInfoByItem(item);
		long redis = System.currentTimeMillis();
		log.debug("根据TTnum查询UserInfo的数据redis或是mysql耗时：" + (redis - start));
		if (result != null) {
			log.debug("根据TTnum查询UserInfo成功，TTnum是：" + ttnum);
			try{
				/* 数据存放redis中 */
				String jsonData = JsonUtil.getObjectToJson(result);
				redisUserInfoMapper.addUserInfoInRedis(result.getUserid().toString(), jsonData,"return Data");
				log.debug("查询用户数据成功，数据返存redis成功！");
			}catch(Exception e){
				log.error("查询用户数据成功，数据返存redis失败！",e);
			}
			long end = System.currentTimeMillis();
			log.debug("根据TTnum查询UserInfo的数据耗时：" + (end - start));
			return result;
		}
		return null;
	}

	
	/**
	 * 用户登陆
	 * 
	 * @param userInfoQuery
	 * @return List<UserInfo>
	 */
	public List<UserInfo> userLogin(UserInfoQuery userInfoQuery)throws Exception {
		long start = System.currentTimeMillis();
		List<UserInfo> userInfos = this.selectListByLogin(userInfoQuery, true);
		long login = System.currentTimeMillis();
		log.debug("用户登陆查询数据耗时：" + ( login - start ));
		UserInfo userInfo = null;
		if (userInfos != null && userInfos.size() > 0) {
			try{
				userInfo = userInfos.get(0);
				/* 数据存放redis中 */
				String jsonData = JsonUtil.getObjectToJson(userInfo);
				redisUserInfoMapper.addUserInfoInRedis(userInfo.getUserid().toString(), jsonData,"return Data");
				long back = System.currentTimeMillis();
				log.debug("用户登陆,数据返存redis耗时：" + ( back - login ));
				log.debug("查询用户数据成功，数据返存redis成功！");
			}catch(Exception e){
				log.error("查询用户数据成功，数据返存redis失败！",e);
			}
			log.debug("查询用户数据成功！");
			long end = System.currentTimeMillis();
			log.debug("用户登陆耗时：" + ( end - start ));
			return userInfos;
		}
		if(userInfos == null){
			log.debug("没有查到对象！");
		}
		return null;
	}

	/**
	 * 模糊分页查询
	 * 
	 * @param userInfoQuery
	 * @return List<UserInfo>
	 */
	public List<UserInfo> selectListBySelectivePaging(UserInfoQuery userInfoQuery) throws Exception {
		long start = System.currentTimeMillis();
		List<UserInfo> userInfos = this.selectListBySelective(userInfoQuery);
		if (userInfos != null) {
			long end = System.currentTimeMillis();
			log.debug("用户分页查询耗时：" + ( end -start ));
			return userInfos;
		}
		return null;
	}

	/**
	 * 通过用户id的集合查询用户的集合
	 * 
	 * @param ids
	 * @return List<UserInfo>
	 */
	public List<UserInfo> selectUserInfosByIds(List<BigInteger> ids) throws Exception {
		
		long start = System.currentTimeMillis();
		/* 查询solr得到item的集合 */
		List<Item> items = null;
		List<UserInfo> listUserInfo = null;
		try {
			items = solrUserInfoMapper.getItemsByIds(ids);
			log.debug("查询solrUserInfo的ids集合成功！");
		} catch (Exception e) {
			log.error("查询solrUserInfo的ids集合失败！原因是：" ,e);
			throw new Exception("查询solrUserInfo的ids集合失败！原因是：",e);
		}
		long solr = System.currentTimeMillis();
		log.debug("用户id集合查询用户solr耗时：" + ( solr - start ));

		/* 查询userInfo的数据 */
		try {
			if (items == null) {
				log.debug("查询SolrUserInfo的ids集合没有数据！");
				return null;
			}
			listUserInfo = this.getUserInfosByItems(items);
			long redis = System.currentTimeMillis();
			log.debug("用户id集合查询用户redis或是mysql耗时：" + ( redis - solr ));
			log.debug("用户id集合查询用户耗时：" + ( redis - start ));
			log.debug("items 查询UserInfo成功！");
			return listUserInfo;
		} catch (Exception e) {
			log.error("items 查询UserInfo失败 ! 出错的原因是：",e);
			throw new Exception("items 查询UserInfo失败！出错的原因是：",e);
		}
	}

	/**
	 *  用户添加靓号
	 */
	public Integer addGoodTTnum(BigInteger userid, String ttNum)throws Exception {
		UserInfo userInfo = this.selectUserInfoByUserId(userid);
		String strTTnum = ttNum + ",1";
		userInfo.setLoginGoodTTnum(strTTnum);
		this.updateUserInfo(userInfo);
		log.debug("添加靓号成功！");
		return null;
	}

	/**
	 * 修改靓号的状态
	 * 0：删除，1：正常
	 */
	public Integer updateGoodTTnum(BigInteger userid, Integer type)throws Exception {
		UserInfo userInfo = this.selectUserInfoByUserId(userid);
		if(userInfo != null){
			userInfo.setLoginGoodTTnumType(type);
			this.updateUserInfo(userInfo);			
			log.debug("修改靓号状态成功！");
		}else{
			throw new Exception("用户不存在！");
		}
		return null;
	}

	/**
	 * 查询靓号
	 */
	public String selectGoodTTnum(BigInteger userid) throws Exception {
		UserInfo userInfo = this.selectUserInfoByUserId(userid);
		if(userInfo != null){
			return userInfo.getLoginGoodTTnum();
		}
		return null;
	}
	
	/**
	 * 查询用户列表 分页,模糊查询
	 * 
	 * @param userInfoQuery
	 * @param accurateSearch
	 * @return
	 * @throws Exception
	 */
	private List<UserInfo> selectListBySelective(UserInfoQuery userInfoQuery) throws Exception {

		if (userInfoQuery == null) {
			return null;
		}

		/* 查询条件的组装 */
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		Map<String, List<String>> timeMap = new HashMap<String, List<String>>();
		Map<String, Boolean> sortFields = userInfoQuery.getSorfFields();
		Integer page = new Integer(userInfoQuery.getPage());
		Integer pageSize = new Integer(userInfoQuery.getPageSize());
		assemblyCollectionsMap(userInfoQuery, fieldValues, timeMap);
		/* 查询分页的起始位置和偏移量 */
		List<Item> items = solrUserInfoMapper.getItems(fieldValues, timeMap,SolrConstant.SOLR_START, SolrConstant.SOLR_COUNT, sortFields,null);
		int start = getPagingStart(items, page, pageSize);
		int count = pageSize;
		/* 查询的结果 */
		List<Item> resultItems = solrUserInfoMapper.getItems(fieldValues,timeMap, start, count, sortFields, null);
		if (resultItems != null && resultItems.size() > 0) {
			List<UserInfo> userInfos = this.getUserInfosByItems(resultItems);
			return userInfos;
		}
		return null;
	}

	/**
	 *  用户登陆，精确查询的实现
	 *
	 * @param userInfoQuery
	 * @param accurateSearch
	 * @return
	 * @throws Exception
	 */
	private List<UserInfo> selectListByLogin(UserInfoQuery userInfoQuery,
			boolean accurateSearch) throws Exception {
		
		String ttnum = null;
		String loginGoodTtnum = null;
		if(userInfoQuery.getTTnum() != null){
			ttnum = userInfoQuery.getTTnum().toString();
		}
		if(userInfoQuery.getLoginGoodTTnum() != null && !"".equals(userInfoQuery.getLoginGoodTTnum())){
			loginGoodTtnum = userInfoQuery.getTTnum().toString();
		}
		
		/* ttnum 和 LoginGoodTTNum的字段值相同 */
		if(loginGoodTtnum != null && ttnum != null && loginGoodTtnum.equals(ttnum)){			
			/* 根据loginGoodTtnum查询 */
			Map<String, Object> fieldValues = new HashMap<String, Object>();
			Map<String, List<String>> timeMap = new HashMap<String, List<String>>();
			Map<String, Boolean> sortFields = userInfoQuery.getSorfFields();
			assemblyCollectionsMap(userInfoQuery, fieldValues, timeMap);
			if(fieldValues.containsKey(SolrConstant.SOLRUSERINFO_KEY_TTNUM)){
				fieldValues.remove(SolrConstant.SOLRUSERINFO_KEY_TTNUM);
			}
			/* 查询Solr分页的起始位置和偏移量 */
			List<Item> items = solrUserInfoMapper.getItems(fieldValues, timeMap,SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE, sortFields,null);
			 /* 查询redis中的数据 */
			if (items != null && items.size() > 0) {
				List<UserInfo> userInfos = this.getUserInfosByItems(items);
				return userInfos;
			}
			/* 如果根据loginGoodTtnum查询失败，则根据TTnum查询*/
			fieldValues.put(SolrConstant.SOLRUSERINFO_KEY_TTNUM, fieldValues.get(SolrConstant.SOLRUSERINFO_LOGINGOODTTNUM).toString());
			fieldValues.remove(SolrConstant.SOLRUSERINFO_LOGINGOODTTNUM);
			fieldValues.remove(SolrConstant.SOLRUSERINFO_LOGINGOODTTNUMTYPE);
			/* 查询Solr分页的起始位置和偏移量 */
			List<Item> _items = solrUserInfoMapper.getItems(fieldValues, timeMap,SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE, sortFields,null);
			 /* 查询redis中的数据 */
			if (_items != null && _items.size() > 0) {
				List<UserInfo> userInfos = this.getUserInfosByItems(_items);
				return userInfos;
			}	
		}else{
			/* 查询条件的组装 */
			Map<String, Object> fieldValues = new HashMap<String, Object>();
			Map<String, List<String>> timeMap = new HashMap<String, List<String>>();
			Map<String, Boolean> sortFields = userInfoQuery.getSorfFields();
			assemblyCollectionsMap(userInfoQuery, fieldValues, timeMap);		
			/* 查询Solr分页的起始位置和偏移量 */
			List<Item> items = solrUserInfoMapper.getItems(fieldValues, timeMap,SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE, sortFields,null);
			 /* 查询redis中的数据 */
			if (items != null && items.size() > 0) {
				List<UserInfo> userInfos = this.getUserInfosByItems(items);
				return userInfos;
			}
		}
		return null;
	}

	/**
	 * 根据Item查询UserInfo对象,查询包含Redis
	 * 
	 * @param solr
	 * @return
	 * @throws Exception
	 */
	private UserInfo getUserInfoByItemHasRedis(Item item) throws Exception {

		/* 查询Redis内存中的 */
		UserInfo userInfo = redisUserInfoMapper.getUserInfoInRedis(item.getId());
		if (userInfo != null) {
			return userInfo;
		}
		/* 查询mysql数据 */
		MysqlUserInfo mysql = mysqlUserInfoMapper.getMysqlUserInfo(
				new BigInteger(item.getId()), item.getDataSourceKey());
		if (mysql == null) {
			return null;
		}
		log.debug("根据key查询MysqlUserInfo成功！");

		/* 查询hbase数据 */
		HbaseUserInfo hbase = hbaseUserInfoMapper.getHbaseUserInfoById(item
				.getId());
		if (hbase == null) {
			return null;
		}
		log.debug("根据key查询HbaseUserInfo成功");

		/* 组装UserInfo */
		UserInfo userInfoTemp = this
				.getUserInfoFromMysqlUserInfoAndHbaseUserInfo(mysql, hbase);
		if (userInfoTemp != null) {
			log.debug("根据key查询UserInfo成功！");
			return userInfoTemp;
		}
		return null;
	}
	
	/**
	 * 根据Item 查询UserInfo对象
	 * 
	 * @param solr
	 * @return
	 * @throws Exception
	 */
	private UserInfo getUserInfoByItem(Item item) throws Exception {

		/* 查询mysql数据 */
		MysqlUserInfo mysql = mysqlUserInfoMapper.getMysqlUserInfo(
				new BigInteger(item.getId()), item.getDataSourceKey());
		if (mysql == null) {
			return null;
		}
		log.debug("根据key查询MysqlUserInfo成功！");

		/* 查询hbase数据 */
		HbaseUserInfo hbase = hbaseUserInfoMapper.getHbaseUserInfoById(item
				.getId());
		if (hbase == null) {
			return null;
		}
		log.debug("根据key查询HbaseUserInfo成功");

		/* 组装UserInfo */
		UserInfo userInfoTemp = this
				.getUserInfoFromMysqlUserInfoAndHbaseUserInfo(mysql, hbase);
		if (userInfoTemp != null) {
			log.debug("根据key查询UserInfo成功！");
			return userInfoTemp;
		}
		return null;
	}

	/**
	 * 通过Items 查询UserInfo的数据
	 * @param items
	 * @return
	 */
	private List<UserInfo> getUserInfosByItems(List<Item> items)throws Exception{
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		if(items != null && items.size() > 0){
			for(Item item:items){
				UserInfo userInfo = this.getUserInfoByItemHasRedis(item);
				if(userInfo != null){
					userInfos.add(userInfo);
				}
			}
			return userInfos;
		}
		return null;
	}
	
	/**
	 * 通过MysqlUserInfo和HbaseUserInfo合成UserInfo
	 * 
	 * @param mysql
	 * @param hbase
	 * @return
	 * @throws Exception 
	 */
	private UserInfo getUserInfoFromMysqlUserInfoAndHbaseUserInfo(MysqlUserInfo mysql, HbaseUserInfo hbase) throws Exception {
		UserInfo userInfoTemp = new UserInfo();
		userInfoTemp = mysqlUserInfoMapper.getConvertMysqlUserInfoToUserInfo(userInfoTemp, mysql);
		userInfoTemp = hbaseUserInfoMapper.getConvertHbaseUserInfoToUserInfo(userInfoTemp, hbase);
		return userInfoTemp;
	}

	/**
	 * 修改redis中的信息
	 * 
	 * @param userInfo
	 * @throws Exception
	 */
	private int updateRedisUserInfo(UserInfo userInfo) throws Exception {

		/* 查询redis数据 */
		UserInfo temp = redisUserInfoMapper.getUserInfoInRedis(userInfo.getUserid().toString());
		log.debug("[" + userInfo.getReqId() + "]@@" + "[修改Redis的UserCrossRelation对象，查询redis的数据成功！]");
		log.debug("");
		if (temp == null) {
			log.debug("[" + userInfo.getReqId() + "]@@" + "[修改Redis的UserCrossRelation对象，查询redis的数据是Null！id是："+userInfo.getUserid().toString()+"]");
			log.info("[" + userInfo.getReqId() + "]@@" + "[修改Redis的UserCrossRelation对象，查询redis的数据是Null！id是："+userInfo.getUserid().toString()+"]");
			return UserInfoConstant.OBJECT_NULL;
		}
		BeanCopyProperties.copyProperties(userInfo, temp,false,new String[] { "userid" });
		log.debug("[" + userInfo.getReqId() + "]@@" + "[UserInfo的新值覆盖旧值成功！]");
		/* 修改redis中的值 */
		String tempJson = JsonUtil.getObjectToJson(temp);
		log.debug("[" + userInfo.getReqId() + "]@@" + "[UserInfo的新对象转换成Json成功！]");
		redisUserInfoMapper.updateUserInfoInRedis(userInfo.getUserid().toString(),tempJson,userInfo.getReqId());
		return UserInfoConstant.SUCCESS;
	}

	/**
	 * 组装查询条件的Map对象
	 * 
	 * @throws Exception
	 */
	private void assemblyCollectionsMap(UserInfoQuery userInfoQuery,
			Map<String, Object> fieldValues, Map<String, List<String>> timeMap)
			throws Exception {

		/* 转换UserInfo对象变为SolrUserInfo对象 */
		SolrUserInfo solrUserInfo = new SolrUserInfo();
		BeanCopyProperties.copyProperties(userInfoQuery, solrUserInfo, true,null);
		/* 手工复制需要 手工复制的字段 */
		if(userInfoQuery.getUserid() != null){
			solrUserInfo.setId(userInfoQuery.getUserid().toString());
		}
		/* 时间段查询 */
		List<String> list = new ArrayList<String>();
		if (userInfoQuery.getStartTime() != null&& userInfoQuery.getEndTime() != null) {
			list.add(sdf.format(userInfoQuery.getStartTime()));
			list.add(sdf.format(userInfoQuery.getEndTime()));
			timeMap.put(SolrConstant.SOLRUSERINFO_KEY_TIME, list);
		}else if(userInfoQuery.getStartTime() != null && userInfoQuery.getEndTime() == null){
			list.add(sdf.format(userInfoQuery.getStartTime()));
			list.add("*");
			timeMap.put(SolrConstant.SOLRUSERINFO_KEY_TIME, list);
		}else if(userInfoQuery.getStartTime() == null && userInfoQuery.getEndTime() != null){
			list.add("*");
			list.add(sdf.format(userInfoQuery.getEndTime()));
			timeMap.put(SolrConstant.SOLRUSERINFO_KEY_TIME, list);
		}
		/* 查询字段的组装 */
		this.getMapFieldValues(solrUserInfo, fieldValues);
	}

	/**
	 * 将SolrUserInfo对象转换成Map<String ,Object>字段查询Map
	 * 
	 * @param solrUserInfo
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void getMapFieldValues(SolrUserInfo solrUserInfo,
			Map<String, Object> fieldValues) throws Exception {
		/* 通过反射组装查询字段 */
		Method[] Methods = solrUserInfo.getClass().getDeclaredMethods();
		for (int i = 0; i < Methods.length; i++) {
			Method method = Methods[i];
			String methodName = method.getName();
			if (!methodName.contains("get"))
				continue;		
			String fieldName = SolrUtil.getLowerName(methodName.substring(3));
			if(fieldName.equals("tTnum"))
				fieldName = "TTnum";
			Object value = method.invoke(solrUserInfo, new Object[0]);
			if (value == null)
				continue;

			/* 集合类判空处理 */
			if (value instanceof Collection) {
				Collection newValue = (Collection) value;
				if (newValue.size() <= 0)
					continue;
			}
			if (value instanceof BigInteger) {
				value = value.toString();
			}
			if (value instanceof Integer) {
				value = value.toString();
			}
			if (value instanceof Date) {
				value = sdf.format(value).toString();
			}
			fieldValues.put(fieldName, value);
		}
	}

	/**
	 * 获取userInfo分页的起始位置
	 * 
	 * @param solrs
	 * @param page
	 * @param pageSize
	 */
	private int getPagingStart(List<Item> items, int page, int pageSize) {
		if (items == null) {
			return 0;
		}
		int start = 0;
		/* 总数 */
		int sum = items.size();
		/* 分页的总数 */
		int sumPage = sum / pageSize + 1;
		/* 取得起始位置 */
		if (page <= sumPage) {
			start = (page - 1) * pageSize;
		}
		//2016年1月5日19:55:34  如果到了最后一页，再点下一页，返回NULL
		if(page > sumPage){
			start = (sumPage - 1) * pageSize;
			return sum;
		}
		
		return start;
	}
	
	public HbaseUserInfoMapper getHbaseUserInfoMapper() {
		return hbaseUserInfoMapper;
	}

	public void setSolrUserInfoMapper(SolrUserInfoMapper solrUserInfoMapper) {
		this.solrUserInfoMapper = solrUserInfoMapper;
	}

	public void setHbaseUserInfoMapper(HbaseUserInfoMapper hbaseUserInfoMapper) {
		this.hbaseUserInfoMapper = hbaseUserInfoMapper;
	}

	public void setMysqlUserInfoMapper(MysqlUserInfoMapper mysqlUserInfoMapper) {
		this.mysqlUserInfoMapper = mysqlUserInfoMapper;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setAddUserInfoDestination(Destination addUserInfoDestination) {
		this.addUserInfoDestination = addUserInfoDestination;
	}

	public void setUpdateUserInfoDestination(Destination updateUserInfoDestination) {
		this.updateUserInfoDestination = updateUserInfoDestination;
	}

	public void setRedisUserInfoMapper(RedisUserInfoMapper redisUserInfoMapper) {
		this.redisUserInfoMapper = redisUserInfoMapper;
	}

	public void setrDBCluster(RDBCluster rDBCluster) {
		this.rDBCluster = rDBCluster;
	}
}
