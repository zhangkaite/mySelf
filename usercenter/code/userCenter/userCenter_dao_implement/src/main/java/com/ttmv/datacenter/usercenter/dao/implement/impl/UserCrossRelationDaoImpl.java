package com.ttmv.datacenter.usercenter.dao.implement.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ttmv.datacenter.rdbcluster.RDBCluster;
import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.constant.SolrConstant;
import com.ttmv.datacenter.usercenter.dao.implement.constant.UserInfoConstant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.temp.Item;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation.MysqlUserCrossRelation;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation.SolrUserCrossRelation;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation.MysqlUserCrossRelationMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation.RedisUserCrossRelationMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation.SolrUserCrossRelationMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.implement.util.SolrUtil;
import com.ttmv.datacenter.usercenter.dao.implement.util.TableIdGenerate;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserCrossRelationDao;
import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.operation.query.UserCrossRelationQuery;
@SuppressWarnings({ "rawtypes", "unchecked" ,"unused"})
public class UserCrossRelationDaoImpl implements UserCrossRelationDao {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(UserCrossRelationDaoImpl.class);
	
	/* 用户注册信息 队列 */
	private Destination addUserCrossRelationDestination;
	/* 用户修改信息 队列 */
	private Destination updateUserCrossRelationDestination;
	/* 用户删除信息 队列*/
	private Destination deleteUserCrossRelationDestination;
	
	private TableIdGenerate tableIdGenerate;
	private SolrUserCrossRelationMapper solrUserCrossRelationMapper;
	private RedisUserCrossRelationMapper redisUserCrossRelationMapper;
	private MysqlUserCrossRelationMapper mysqlUserCrossRelationMapper;
	private JmsTemplate jmsTemplate;
	private RDBCluster rDBCluster;
	
	/**
	 * 添加UserCrossRelation
	 */
	public Integer addUserCrossRelation(UserCrossRelation userCrossRelation) throws Exception {
		
		if(userCrossRelation == null){
			log.debug("userCrossRelation不能为null");
			throw new Exception("userCrossRelation不能为null");
		}
		String dataSourceKey = rDBCluster.getOneMaster();
		log.debug("[" + userCrossRelation.getReqId() + "]@@" + "获取dataSourceKey：" + dataSourceKey);
		log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[准备添加UserCrossRelation！开始。。]");
		try{
			BigInteger uuid = tableIdGenerate.getTableId("crossRelation");
			log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[生成UserCrossRelation的id成功！]");
			userCrossRelation.setId(uuid);
			log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[UserCrossRelation添加id成功！]");
		}catch(Exception e){
			log.error("[" + userCrossRelation.getReqId() + "]@@" + "[UserCrossRelation添加id失败！]",e);
			throw new Exception("[" + userCrossRelation.getReqId() + "]@@" + "[UserCrossRelation添加id失败！]",e);
		}
		
		/* 向队列中添加数据 */
		Map addCrossMap = new HashMap<String,Object>();
		addCrossMap.put("service", UserInfoConstant.ADD_CROSS);
		addCrossMap.put("data", userCrossRelation );
		addCrossMap.put("count", new Integer(0));
		addCrossMap.put("reqID", userCrossRelation.getReqId());
		addCrossMap.put("dataSourceKey", dataSourceKey);
		
		final String crossRelationJson = JsonUtil.getObjectToJson(addCrossMap);
		log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[生成添加UserCrossRelation对象的队列的json数据成功！]");
		try {
			jmsTemplate.send(addUserCrossRelationDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(crossRelationJson);
				}
			});
			log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[向addUserCrossRelationDestination队列中添加json成功！]");
		} catch (Exception e) {
			/* 如果出现添加队列失败，则抛出添加队列失败的错误的信息，并且添加数据失败 */
			log.error("[" + userCrossRelation.getReqId() + "]@@" + "[向addUserCrossRelationDestination队列中添加json失败！]",e);
			throw new Exception("[" + userCrossRelation.getReqId() + "]@@" + "[向addUserCrossRelationDestination队列中添加json失败！]",e);
		}
		
		/* 添加redis */
		try{
			String jsonData = JsonUtil.getObjectToJson(userCrossRelation);
			log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[生成UserCrossRelation对象的redis的json数据成功！]");
			redisUserCrossRelationMapper.addRedisUserCrossRelation(userCrossRelation.getId().toString(), jsonData,userCrossRelation.getReqId());
		}catch(Exception e){
			throw new Exception(e);
		}
		
		/* 添加solr */
		try{			
			SolrUserCrossRelation solr = this.UserCrossRelationConvertToSolrUserCrossRelation(userCrossRelation);
			//lily 2货!!!又尼玛掉了下面这句代码. 2015年7月10日10:44:52
			solr.setDataSourceKey(dataSourceKey);
			log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[UserCrossRelation复制生成SolrUserCrossRelation成功！]");
			solrUserCrossRelationMapper.addSolrUserCrossRelationDelay(solr,userCrossRelation.getReqId());
			log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[solr添加数据SolrUserCrossRelation成功！]");
		}catch(Exception e){
			log.error("[" + userCrossRelation.getReqId() + "]@@" + "[solr添加数据SolrUserCrossRelation失败！]",e);
			throw new Exception("[" + userCrossRelation.getReqId() + "]@@" + "[solr添加数据SolrUserCrossRelation失败！]",e);
		}
		log.debug("[" + userCrossRelation.getReqId() + "]@@" + "[添加UserCrossRelation成功！结束。。]");
		return 0;
	}

	/**
	 * 修改UserCrossRelation
	 */
	public Integer updateUserCrossRelation(UserCrossRelation crossRelation) throws Exception {
	
		if(crossRelation == null){
			log.debug("crossRelation对象不能为null");
			throw new Exception("userCrossRelation不能为null");
		}
		log.debug("[" + crossRelation.getReqId() + "]@@" + "[准备修改UserCrossRelation！开始。。]");
		/* 添加到队列中 */
		Map updateCrossMap = new HashMap<String,Object>();
		updateCrossMap.put("service", UserInfoConstant.UPD_CROSS);
		updateCrossMap.put("data", crossRelation );
		updateCrossMap.put("count", new Integer(0));		
		updateCrossMap.put("reqID", crossRelation.getReqId());
		
		final String crossRelationJson = JsonUtil.getObjectToJson(updateCrossMap);
		log.debug("[" + crossRelation.getReqId() + "]@@" + "[生成修改UserCrossRelation对象的队列的json数据成功！]");
		try {
			jmsTemplate.send(updateUserCrossRelationDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(crossRelationJson);
				}
			});
			log.debug("[" + crossRelation.getReqId() + "]@@" + "[向updateUserCrossRelationDestination队列中添加json数据队列成功！]");
		} catch (Exception e) {
			log.error("[" + crossRelation.getReqId() + "]@@" + "[向updateUserCrossRelationDestination队列中添加json数据队列失败！]" , e);
			throw new Exception("[" + crossRelation.getReqId() + "]@@" + "向updateUserCrossRelationDestination队列中添加json数据队列失败！]",e);
		}
		
		/* 修改solr */
		String dataSourceKey = null;
		try{			
			SolrUserCrossRelation solr = solrUserCrossRelationMapper.getUserCrossRelationByKey(SolrConstant.SOLR_KEY_ID, crossRelation.getId().toString(),SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE);
			log.debug("[" + crossRelation.getReqId() + "]@@" + "[修改solr的Group对象，查询solr的数据成功！]");
			if(solr == null){
				log.debug("[" + crossRelation.getReqId() + "]@@" + "[修改solr的UserCrossRelation对象，查询solr的数据是Null！id是："+crossRelation.getId().toString()+"]");
				throw new Exception("[" + crossRelation.getReqId() + "]@@" + "[修改solr的UserCrossRelation对象，查询solr的数据是Null！id是："+crossRelation.getId().toString()+"]");
			}
			solr = this.UserCrossRelationConvertToSolrUserCrossRelation(crossRelation, solr);
			log.debug("[" + crossRelation.getReqId() + "]@@" + "[复制crossRelation的新值成功！]");
			solrUserCrossRelationMapper.addSolrUserCrossRelation(solr, crossRelation.getReqId() );
			log.debug("[" + crossRelation.getReqId() + "]@@" + "[修改SolrUserCrossRelation对象成功！]");
		}catch(Exception e){
			log.error("[" + crossRelation.getReqId() + "]@@" + "[修改SolrUserCrossRelation对象失败！]",e);
			throw new Exception("[" + crossRelation.getReqId() + "]@@" + "[修改SolrUserCrossRelation对象失败！]",e);
		}
		
		/* 修改redis的数据 */
		try{
			redisUserCrossRelationMapper.updateRedisUserCrossRelation(crossRelation);
		}catch(Exception e ){
			throw new Exception(e);
		}
		log.debug("[" + crossRelation.getReqId() + "]@@" + "[修改UserCrossRelation成功！结束。。]");
		return null;
	}

	/**
	 * 删除UserCrossRelation
	 */
	public Integer deleteUserCrossRelation(BigInteger id,String reqID) throws Exception {
		if(id == null){
			log.debug("id不能为null");
			throw new Exception("id不能为null");
		}
		log.debug("[" + reqID + "]@@" + "[删除UserCrossRelation！开始。。]");
		/* 添加到队列中 */
		Map deleteCrossMap = new HashMap<String,Object>();
		deleteCrossMap.put("service", UserInfoConstant.DEL_CROSS);
		deleteCrossMap.put("data", id );
		deleteCrossMap.put("count", new Integer(0));
		deleteCrossMap.put("reqID", reqID);
		log.debug("[" + reqID + "]@@" + "[新建Map对象,并将id放入Map对象]");
		/* 添加到队列中 */
		final String crossJson = JsonUtil.getObjectToJson(deleteCrossMap);
		log.debug("[" + reqID + "]@@" + "[生成添加deleteUserCrossRelationDestination队列的json数据成功！]");
		try {
			jmsTemplate.send(deleteUserCrossRelationDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(crossJson);
				}
			});
			log.debug("[" + reqID + "]@@" + "[向deleteUserCrossRelationDestination队列中添加json成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@" + "[向deleteUserCrossRelationDestination队列中添加json失败！]",e);
			throw new Exception("[" + reqID + "]@@" + "[向deleteUserCrossRelationDestination队列中添加json失败！]",e);
		}
		/* 删除redis中的数据 */
		try{
			redisUserCrossRelationMapper.deleteRedisUserCrossRelation(id.toString(),reqID);
		}catch(Exception e){
			log.error("[" + reqID + "]@@" + "[redis删除UserCrossRelation失败！]",e);
			throw new Exception("[" + reqID + "]@@" + "[redis删除UserCrossRelation失败！]",e);
		}
		log.debug("[" + reqID + "]@@" + "[删除UserCrossRelation成功！结束。。]");
		return Constant.SUCCESS;	
	}

	/**
	 * 查询UserCrossRelation根据Id
	 */
	public UserCrossRelation selectUserCrossRelation(BigInteger id)throws Exception {
		
		if(id == null){
			log.debug("id不能为null");
			throw new Exception("id不能为null");
		}
		
		/* 查询redis中的数据 */
		try{
			UserCrossRelation cross = redisUserCrossRelationMapper.getRedisUserCrossRelation(id.toString());
			if(cross != null){				
				return cross;
			}
		}catch(Exception e){
			throw new Exception(e);
		}
		
		/* 查询solr*/
		Item item = null;
		try{			
			item = solrUserCrossRelationMapper.getItem(SolrConstant.SOLR_KEY_ID,id.toString(),SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE);
			if(item == null){
				log.debug("查询solrUserCrossRelation对象，查询数据为null，id是："+id.toString());
				return null;
			}
			log.debug("查询solrUserCrossRelation对象成功！");
		}catch(Exception e){
			log.error("查询solrUserCrossRelation对象失败,失败原因：",e);
			throw new Exception("查询solrUserCrossRelation对象失败,失败原因：",e);
		}
		
		/* 查询mysql数据库数据 */
		try{
			UserCrossRelation cross = this.getUserCrossRelationByItem(item);
			if(cross != null){
				log.debug("根据id查询UserCrossRelation成功！");
				try{
					/* 数据存放redis中 */
					String jsonData = JsonUtil.getObjectToJson(cross);
					redisUserCrossRelationMapper.addRedisUserCrossRelation(cross.getId().toString(), jsonData,cross.getId().toString());
					log.debug("查询用户关系数据成功，数据存入redis成功！");
				}catch(Exception e){
					log.error("查询用户关系数据成功，数据存入redis失败！",e);
				}
				return cross;
			}
		}catch(Exception e){
			throw new Exception(e);
		}
		return null;
	}

	public List<UserCrossRelation> selectListBySelective(UserCrossRelationQuery crossRelationQuery) throws Exception {
		if (crossRelationQuery == null) {
			return null;
		}

		/* 查询条件的组装 */
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		Map<String, Boolean> sortFields = crossRelationQuery.getSorfFields();
		Integer page = new Integer(crossRelationQuery.getPage());
		Integer pageSize = new Integer(crossRelationQuery.getPageSize());
		assemblyCollectionsMap(crossRelationQuery, fieldValues);
		/* 查询Solr分页的起始位置和偏移量*/
		List<Item> items =  solrUserCrossRelationMapper.getItems(fieldValues, null, SolrConstant.SOLR_START, SolrConstant.SOLR_COUNT, sortFields, null);
		log.debug("查询solrUserCrossRelation的items集合成功！");
		if(items == null ){
			log.debug("查询solrUserCrossRelation的items集合为null！");
			return null;
		}
		/* 计算起始位置和偏移量*/
		int start = getPagingStart(items, page, pageSize);
		int count = pageSize;
		/*查询最终返回结果*/
		List<Item> resultItems =  solrUserCrossRelationMapper.getItems(fieldValues, null, start, count, sortFields, null);
		log.debug("查询需要返回的items的集合成功！");
		if (resultItems != null && resultItems.size() > 0) {
			List<UserCrossRelation> crosses = this.getUserCrossRelationsByItems(resultItems);
			log.debug("查询需要返回的solrUserCrossRelation的集合成功！");
			return crosses;
		}
		return null;
	}
	
	/**
	 * 根据Item查询UserCrossRelation
	 * @param item
	 * @return
	 * @throws Exception
	 */
	private UserCrossRelation getUserCrossRelationByItem(Item item)throws Exception{
		MysqlUserCrossRelation mysql = mysqlUserCrossRelationMapper.getMysqlUserCrossRelationById(new BigInteger(item.getId()),item.getDataSourceKey());
		if (mysql == null){
			return null;
		}
		log.debug("根据key查询MysqlUserCrossRelation成功！");
		UserCrossRelation cross = mysqlUserCrossRelationMapper.mysqlUserCrossRelationConvertToUserCrossRelation(mysql);
		if(cross != null){
			log.debug("根据key查询UserCrossRelation成功！"); 
			return cross;
		}
		return null;
	}
	
	/**
	 * 根据Item查询UserCrossRelation对象，查询redis
	 * @param item
	 * @return
	 * @throws Exception
	 */
	private UserCrossRelation getUserCrossRelationByItemHasRedis(Item item)throws Exception{
		
		/* 查询redis */
		UserCrossRelation cross = redisUserCrossRelationMapper.getRedisUserCrossRelation(item.getId().toString());
		if(cross != null){
			return cross;
		}
		
		/* 查询mysql */
		MysqlUserCrossRelation mysql = mysqlUserCrossRelationMapper.getMysqlUserCrossRelationById(new BigInteger(item.getId()),item.getDataSourceKey());
		if (mysql == null){
			return null;
		}
		log.debug("根据key查询MysqlUserCrossRelation成功！");
		UserCrossRelation temp = mysqlUserCrossRelationMapper.mysqlUserCrossRelationConvertToUserCrossRelation(mysql);
		if(temp != null){
			log.debug("根据key查询UserCrossRelation成功！"); 
			return temp;
		}
		return null;
	}
	
	/**
	 * 通过items查询UserCrossRelation的数据
	 * @param items
	 * @return
	 * @throws Exception
	 */
	private List<UserCrossRelation> getUserCrossRelationsByItems(List<Item> items) throws Exception{
		List<UserCrossRelation> crosses = new ArrayList<UserCrossRelation>();
		if(items != null && items.size() > 0){
			for(Item item:items){
				UserCrossRelation cross = this.getUserCrossRelationByItemHasRedis(item);
				log.debug("根据item查询UserCrossRelation成功！"); 
				if(cross != null){
					crosses.add(cross);
				}
			}
			return crosses;
		}
		return null;
	}
	
	/**
	 * 组装查询条件的Map对象
	 * 
	 * @throws Exception
	 */
	private void assemblyCollectionsMap(UserCrossRelationQuery crossRelationQuery,
			Map<String, Object> fieldValues)
			throws Exception {

		/* 转换UserInfo对象变为SolrUserInfo对象 */
		SolrUserCrossRelation solrCrossRelation = new SolrUserCrossRelation();
		BeanCopyProperties.copyProperties(crossRelationQuery, solrCrossRelation, true,null);
		/* 查询字段的组装 */
		this.getMapFieldValues(solrCrossRelation, fieldValues);
	}
	
	/**
	 * 将UserCrossRelationQuery对象转换成Map<String ,Object>字段查询Map
	 * 
	 * @param UserCrossRelationQuery
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void getMapFieldValues(SolrUserCrossRelation solrCrossRelation,
			Map<String, Object> fieldValues) throws Exception {
		/* 通过反射组装查询字段 */
		Method[] Methods = solrCrossRelation.getClass().getDeclaredMethods();
		for (int i = 0; i < Methods.length; i++) {
			Method method = Methods[i];
			String methodName = method.getName();
			if (!methodName.contains("get"))
				continue;		
			String fieldName = SolrUtil.getLowerName(methodName.substring(3));
			if(fieldName.equals("tTnum"))
				fieldName = "TTnum";
			Object value = method.invoke(solrCrossRelation, new Object[0]);
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
			fieldValues.put(fieldName, value);
		}
	}
	
	/**
	 * 获取分页的起始位置
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
		/* 添加其他的判断操作 */
		return start;
	}
	
	/**
	 * 通过usercrossrelation的id 的集合获取UserCrossRelation的集合
	 * 
	 * @return
	 */
	private List<UserCrossRelation> getUserCrossRelationsFromIds(List<Item> items)throws Exception{
		if (items == null || items.size() == 0) {
			return null;
		}
		List<UserCrossRelation> crosses = new ArrayList<UserCrossRelation>();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			UserCrossRelation cross = redisUserCrossRelationMapper.getRedisUserCrossRelation(item.getId());
			crosses.add(cross);
		}
		return crosses;
	}
	
	/**
	 * 通过userIdA查询好友id的集合
	 */
	public List<BigInteger> selectUserInfoIdsByUserId(BigInteger userid,Integer type)throws Exception {
		
		if(userid == null){
			log.info("userid不能为null");
			throw new Exception("userid不能为null");
		}
		/* 查询条件的组装 */
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		fieldValues.put(SolrConstant.SOLRUSERCROSSRELATION_USERIDA, userid);
		fieldValues.put(SolrConstant.SOLRUSERCROSSRELATION_TYPE, type);
		List<BigInteger> list = solrUserCrossRelationMapper.getUserIdBs(fieldValues, null, SolrConstant.SOLR_START, SolrConstant.SOLR_COUNT, null, null);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}

	/**
	 * UserCrossRelation对象转换成SolrUserCrossRelation对象
	 * @param userCrossRelation
	 * @return
	 * @throws Exception
	 */
	private SolrUserCrossRelation UserCrossRelationConvertToSolrUserCrossRelation(UserCrossRelation userCrossRelation)throws Exception{
		SolrUserCrossRelation solr = new SolrUserCrossRelation();
		BeanCopyProperties.copyProperties(userCrossRelation, solr,true, null);
		return solr;
	}
	
	/**
	 * UserCrossRelation对象转换成SolrUserCrossRelation对象
	 * @param userCrossRelation
	 * @return
	 * @throws Exception
	 */
	private SolrUserCrossRelation UserCrossRelationConvertToSolrUserCrossRelation(UserCrossRelation userCrossRelation,SolrUserCrossRelation solr)throws Exception{
		BeanCopyProperties.copyProperties(userCrossRelation, solr,true, null);
		return solr;
	}
	
	public void setAddUserCrossRelationDestination(
			Destination addUserCrossRelationDestination) {
		this.addUserCrossRelationDestination = addUserCrossRelationDestination;
	}

	public void setDeleteUserCrossRelationDestination(
			Destination deleteUserCrossRelationDestination) {
		this.deleteUserCrossRelationDestination = deleteUserCrossRelationDestination;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setSolrUserCrossRelationMapper(
			SolrUserCrossRelationMapper solrUserCrossRelationMapper) {
		this.solrUserCrossRelationMapper = solrUserCrossRelationMapper;
	}

	public void setUpdateUserCrossRelationDestination(
			Destination updateUserCrossRelationDestination) {
		this.updateUserCrossRelationDestination = updateUserCrossRelationDestination;
	}

	public void setRedisUserCrossRelationMapper(
			RedisUserCrossRelationMapper redisUserCrossRelationMapper) {
		this.redisUserCrossRelationMapper = redisUserCrossRelationMapper;
	}

	public void setTableIdGenerate(TableIdGenerate tableIdGenerate) {
		this.tableIdGenerate = tableIdGenerate;
	}

	public void setMysqlUserCrossRelationMapper(
			MysqlUserCrossRelationMapper mysqlUserCrossRelationMapper) {
		this.mysqlUserCrossRelationMapper = mysqlUserCrossRelationMapper;
	}

	public void setrDBCluster(RDBCluster rDBCluster) {
		this.rDBCluster = rDBCluster;
	}
}
