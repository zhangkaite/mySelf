package com.ttmv.datacenter.usercenter.dao.implement.impl;

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
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group.MysqlGroup;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group.SolrGroup;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.temp.Item;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.group.MysqlGroupMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.group.RedisGroupMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.group.SolrGroupMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.implement.util.SolrUtil;
import com.ttmv.datacenter.usercenter.dao.implement.util.TableIdGenerate;
import com.ttmv.datacenter.usercenter.dao.interfaces.GroupDao;
import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.domain.operation.query.GroupQuery;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class GroupDaoImpl implements GroupDao {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(GroupDaoImpl.class);

	/* Group添加信息 队列 */
	private Destination addGroupDestination;
	/* Group修改信息 队列 */
	private Destination updateGroupDestination;
	/* Group删除信息 队列 */
	private Destination deleteGroupDestination;

	private TableIdGenerate tableIdGenerate;
	private MysqlGroupMapper mysqlGroupMapper;
	private SolrGroupMapper solrGroupMapper;
	private RedisGroupMapper redisGroupMapper;
	private JmsTemplate jmsTemplate;
	private RDBCluster rDBCluster;

	/**
	 * 添加Group
	 */
	public void addUgroup(Group group) throws Exception {

		if (group == null) {
			log.error("Group对象不能为空");
			throw new Exception("Group对象不能为空");
		}
		long start = System.currentTimeMillis();
		String dataSourceKey = rDBCluster.getOneMaster();
		log.debug("[" + group.getReqId() + "]@@" + "[准备添加Group！开始。。]");
		/* 添加uuid */
		try {
			BigInteger uuid = tableIdGenerate.getTableId("group");
			log.debug("[" + group.getReqId() + "]@@" + "[生成Group对象的id成功！]");
			group.setGroupId(uuid);
			log.debug("[" + group.getReqId() + "]@@" + "[Group对象添加id成功！]");
		} catch (Exception e) {
			log.error("[" + group.getReqId() + "]@@" + "[生成Group对象的id失败！]", e);
			throw new Exception("[" + group.getReqId() + "]@@"
					+ "[生成Group对象的id失败！]", e);
		}
		long endUid = System.currentTimeMillis();
		log.debug("[" + group.getReqId() + "]@@" + "生成UID的耗时："
				+ (endUid - start));

		/* 添加到队列中 */
		Map addGroupMap = new HashMap<String, Object>();
		addGroupMap.put("service",UserInfoConstant.ADD_GROUP);
		addGroupMap.put("data", group);
		addGroupMap.put("count", new Integer(0));
		addGroupMap.put("reqID", group.getReqId());
		addGroupMap.put("dataSourceKey", dataSourceKey);
		
		final String groupJson = JsonUtil.getObjectToJson(addGroupMap);
		log.debug("[" + group.getReqId() + "]@@" + "[生成添加Group对象的队列的json数据成功！]");
		try {
			jmsTemplate.send(addGroupDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(groupJson);
				}
			});
			log.debug("[" + group.getReqId() + "]@@"
					+ "[向addGroupDestination队列中添加json成功！]");
		} catch (Exception e) {
			log.error("[" + group.getReqId() + "]@@"
					+ "[向addGroupDestination队列中添加json失败！]", e);
			throw new Exception("[" + group.getReqId() + "]@@"
					+ "[向addGroupDestination队列中添加json失败！]", e);
		}
		long endMq = System.currentTimeMillis();
		log.debug("[" + group.getReqId() + "]@@" + "Mq的耗时：" + (endMq - endUid));

		/* 添加solr */
		try {
			SolrGroup solr = this.groupConvertToSolrGroup(group);
			solr.setDataSourceKey(dataSourceKey);
			log.debug("[" + group.getReqId() + "]@@"
					+ "[Group对象copy为SolrGroup成功！]");
			solrGroupMapper.addSolrGroupDelay(solr, group.getReqId());
		} catch (Exception e) {
			log.error("[" + group.getReqId() + "]@@" + "[Solr添加SolrGroup失败！]",
					e);
			throw new Exception("[" + group.getReqId() + "]@@"
					+ "[Solr添加SolrGroup失败！]", e);
		}
		long endSolr = System.currentTimeMillis();
		log.debug("[" + group.getReqId() + "]@@" + "Solr的耗时："
				+ (endSolr - endMq));

		/* 添加redis */
		try {
			String jsonData = JsonUtil.getObjectToJson(group);
			log.debug("[" + group.getReqId() + "]@@"
					+ "[生成Group对象的redis的json数据成功！]");
			redisGroupMapper.addRedisGroup(group.getGroupId().toString(),
					jsonData, group.getReqId());
		} catch (Exception e) {
			throw new Exception(e);
		}
		log.debug("[" + group.getReqId() + "]@@" + "[添加Group成功！结束。。]");
		long endrRedis = System.currentTimeMillis();
		log.debug("[" + group.getReqId() + "]@@" + "Redis的耗时："
				+ (endrRedis - endSolr));
	}

	/**
	 * 修改Group
	 */
	public void updateUgroup(Group group) throws Exception {
		if (group == null) {
			log.debug("group对象不能为null");
			throw new Exception("group不能为null");
		}
		log.debug("[" + group.getReqId() + "]@@" + "[准备修改Group！开始。。]");
		/* 添加到队列中 */
		Map updateGroupMap = new HashMap<String, Object>();
		updateGroupMap.put("service", UserInfoConstant.UPD_GROUP);
		updateGroupMap.put("data", group);
		updateGroupMap.put("count", new Integer(0));
		updateGroupMap.put("reqID", group.getReqId());

		final String groupJson = JsonUtil.getObjectToJson(updateGroupMap);
		log.debug("[" + group.getReqId() + "]@@" + "[生成修改Group对象的队列的json数据成功！]");
		try {
			jmsTemplate.send(updateGroupDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(groupJson);
				}
			});
			log.debug("[" + group.getReqId() + "]@@"
					+ "[向updateGroupDestination队列中添加json成功！]");
		} catch (Exception e) {
			log.error("[" + group.getReqId() + "]@@"
					+ "[向updateGroupDestination队列中添加json失败！]", e);
			throw new Exception("[" + group.getReqId() + "]@@"
					+ "[向updateGroupDestination队列中添加json失败！]", e);
		}

		/* 修改redis */
		try {
			Group temp = redisGroupMapper.getRedisGroup(group.getGroupId()
					.toString());
			log.debug("[" + group.getReqId() + "]@@"
					+ "[修改Redis的Group对象，查询redis的数据成功！]");
			if (temp == null) {
				log.debug("[" + group.getReqId() + "]@@"
						+ "[修改Redis的Group对象，查询redis的数据是Null！groupId是："
						+ group.getGroupId().toString() + "]");
				throw new Exception("[" + group.getReqId() + "]@@"
						+ "[修改Redis的Group对象，查询redis的数据是Null！groupId是："
						+ group.getGroupId().toString() + "]");
			}
			log.debug("[" + group.getReqId() + "]@@"
					+ "[修改Redis的Group对象，查询redis成功！]");
			BeanCopyProperties.copyProperties(group, temp, false, null);
			log.debug("[" + group.getReqId() + "]@@" + "[Group的新值覆盖旧值成功！]");
			String newJson = JsonUtil.getObjectToJson(temp);
			log.debug("[" + group.getReqId() + "]@@" + "[Group的新对象转换成Json成功！]");
			redisGroupMapper.updateRedisGroup(temp.getGroupId().toString(),
					newJson, group.getReqId());
		} catch (Exception e) {
			log.error("[" + group.getReqId() + "]@@" + "[修改Redis的Group对象失败！]",
					e);
			throw new Exception(e);
		}
		log.debug("[" + group.getReqId() + "]@@" + "[修改Group成功！结束。。]");
	}

	/**
	 * 删除Group 1、删除redis里面数据，mysql和solr里面的数据通过消息队列删除
	 */
	public void deleteUgroup(BigInteger id, String reqID) throws Exception {
		if (id == null) {
			log.debug("id不能未null");
			throw new Exception("id不能为null");
		}
		log.debug("[" + reqID + "]@@" + "[准备删除Group！开始。。]");
		/* 添加到队列中 */
		Group group = new Group();
		group.setGroupId(id);
		log.debug("[" + reqID + "]@@" + "[新建group对象]");
		Map deleteGroupMap = new HashMap<String, Object>();
		
		deleteGroupMap.put("service",UserInfoConstant.DEL_GROUP);
		deleteGroupMap.put("data", group);
		deleteGroupMap.put("count", new Integer(0));
		deleteGroupMap.put("reqID", reqID);

		log.debug("[" + reqID + "]@@" + "[新建Map对象,并将group放入Map对象]");
		final String jsonData = JsonUtil.getObjectToJson(deleteGroupMap);
		log.debug("[" + reqID + "]@@" + "[Map对象转换成json成功！]");
		try {
			jmsTemplate.send(deleteGroupDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(jsonData);
				}
			});
			log.debug("[" + reqID + "]@@"
					+ "[向deleteGroupDestination队列中添加json成功！]");
		} catch (Exception e) {
			log.error("[" + reqID + "]@@"
					+ "[向deleteGroupDestination队列中添加json失败！]", e);
			throw new Exception("[" + reqID + "]@@"
					+ "[向deleteGroupDestination队列中添加json失败！]", e);
		}

		/* 删除redis */
		try {
			redisGroupMapper.deleteRedisGroup(id.toString(), reqID);
		} catch (Exception e) {
			throw new Exception(e);
		}
		log.debug("[" + reqID + "]@@" + "[删除Group成功！结束。。]");
	}

	/**
	 * 查询Group
	 */
	public Group selectUgroup(BigInteger id) throws Exception {

		if (id == null) {
			log.debug("id不能为null");
			throw new Exception("id不能为null");
		}

		/* 查询redis数据 */
		try {
			Group group = redisGroupMapper.getRedisGroup(id.toString());
			if (group != null) {
				return group;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}

		/* 查询solr数据 */
		Item item = null;
		try {
			item = solrGroupMapper.getItemByKey(SolrConstant.SOLR_KEY_ID,
					id.toString(), SolrConstant.SOLR_START,
					SolrConstant.SOLR_UNIQUE);
			if (item == null) {
				return null;
			}
			log.debug("查询SolrGroup对象成功！");
		} catch (Exception e) {
			throw new Exception(e);
		}

		/* 查询mysql */
		try {
			Group group = this.getGroupByItem(item);
			if (group != null) {
				log.info("根据GroupId查询Group成功！");
				try {
					/* 数据存放redis中 */
					String jsonData = JsonUtil.getObjectToJson(group);
					redisGroupMapper.addRedisGroup(id.toString(), jsonData,
							group.getReqId());
					log.debug("查询用户组数据成功，数据存入redis成功！");
				} catch (Exception e) {
					log.error("查询用户组数据成功，数据存入redis失败！", e);
				}
				return group;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		return null;
	}

	/**
	 * 查询多个Group
	 */
	public List<Group> selectListBySelective(GroupQuery groupQuery)
			throws Exception {

		if (groupQuery == null) {
			log.debug("groupQuery不能为null");
			throw new Exception("groupQuery不能为null");
		}
		/* 查询solr和redis 拿到groups */
		try {
			List<Group> groups = this.selectListByGroupQuery(groupQuery);
			log.debug("查询Groups成功！");
			return groups;
		} catch (Exception e) {
			log.error("查询Groups失败，失败的原因：", e);
			throw new Exception("查询Groups失败，失败的原因：", e);
		}
	}

	/**
	 * 通过ids 查询group的列表的集合
	 */
	public List<Group> selectGroupsByIds(List<BigInteger> ids) throws Exception {
		/* 查询solr得到item的集合 */
		List<Item> items = null;
		List<Group> listGroup = null;
		try {
			items = solrGroupMapper.getItemsByIds(ids);
			log.debug("查询SolrGroup的ids集合成功！");
		} catch (Exception e) {
			log.error("查询SolrGroup的ids集合失败！原因是：", e);
			throw new Exception("查询SolrGroup的ids集合失败！原因是：", e);
		}

		/* 查询mysql 和 redis */
		if (items == null) {
			log.debug("查询SolrGroup的id集合没有数据！");
			return null;
		}
		try {
			listGroup = this.getGroupsByItems(items);
			log.debug("items 查询SolrGroup成功！");
			return listGroup;
		} catch (Exception e) {
			log.error("items 查询SolrGroup失败 ! 出错的原因是：", e);
			throw new Exception("items 查询SolrGroup失败！出错的原因是：", e);
		}
	}

	/**
	 * Group对象转换成SolrGroup对象
	 * 
	 * @param group
	 * @return
	 * @throws Exception
	 */
	private SolrGroup groupConvertToSolrGroup(Group group) throws Exception {
		SolrGroup solr = new SolrGroup();
		solr.setId(group.getGroupId().toString());
		solr.setUserId(group.getUserId().toString());
		solr.setDefaultType(group.getDefaultType());
		return solr;
	}

	/**
	 * 通过Items查询Group
	 * 
	 * @param items
	 * @return
	 * @throws Exception
	 */
	private List<Group> convertListGroupByItems(List<Item> items)
			throws Exception {
		List<Group> listGroup = new ArrayList<Group>();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			Group group = new Group();
			try {
				MysqlGroup mysql = mysqlGroupMapper.getMysqlGroupById(
						new BigInteger(item.getId()), item.getDataSourceKey());
				BeanUtils.copyProperties(mysql, group);
			} catch (Exception e) {
				log.error("item查询MysqlGroup出错，id是：" + item.getId());
				throw new Exception("item查询MysqlGroup出错，id是：" + item.getId());
			}
			listGroup.add(group);
		}
		return listGroup;
	}

	/**
	 * 查询Group列表,精确查询
	 */
	private List<Group> selectListByGroupQuery(GroupQuery query)
			throws Exception {

		if (query == null) {
			return null;
		}
		/* 查询条件的组装 */
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		Map<String, List<String>> timeMap = new HashMap<String, List<String>>();
		assemblyCollectionsMap(query, fieldValues, timeMap);
		/* 查询分页的起始位置和偏移量 */
		long start = System.currentTimeMillis();
		List<Item> items = solrGroupMapper.getItems(fieldValues,
				SolrConstant.SOLR_START, SolrConstant.SOLR_COUNT, null, null);
		long end = System.currentTimeMillis();
		System.out.println("solr查询时间:" + (end - start));
		if (items != null && items.size() > 0) {
			List<Group> groups = this.getGroupsByItems(items);
			long redisEnd = System.currentTimeMillis();
			System.out.println("redis查询时间:" + (redisEnd - end));
			return groups;
		}
		return null;
	}

	/**
	 * 组装查询条件的Map对象
	 * 
	 * @throws Exception
	 */
	private void assemblyCollectionsMap(GroupQuery query,
			Map<String, Object> fieldValues, Map<String, List<String>> timeMap)
			throws Exception {

		/* 转换UserInfo对象变为SolrUserInfo对象 */
		SolrGroup solrGroup = new SolrGroup();
		BeanCopyProperties.copyProperties(query, solrGroup, true, null);
		/* 查询字段的组装 */
		this.getMapFieldValues(solrGroup, fieldValues);
	}

	/**
	 * 根据Item查询Group对象
	 * 
	 * @param item
	 * @return
	 * @throws Exception
	 */
	private Group getGroupByItem(Item item) throws Exception {
		MysqlGroup mysql = mysqlGroupMapper.getMysqlGroupById(new BigInteger(
				item.getId()), item.getDataSourceKey());
		if (mysql == null) {
			return null;
		}
		log.debug("根据key查询MysqlGroup成功！");

		Group group = mysqlGroupMapper.getConvertMysqlGroupToGroup(mysql);
		if (group != null) {
			log.debug("根据key查询Group成功！");
			return group;
		}
		return null;
	}

	/**
	 * 根据Item查询Group对象，查询redis
	 * 
	 * @param item
	 * @return
	 * @throws Exception
	 */
	private Group getGroupByItemHasRedis(Item item) throws Exception {

		/* 查询redis */
		Group group = redisGroupMapper.getRedisGroup(item.getId().toString());
		if (group != null) {
			return group;
		}

		/* 查询mysql */
		MysqlGroup mysql = mysqlGroupMapper.getMysqlGroupById(new BigInteger(
				item.getId()), item.getDataSourceKey());
		if (mysql == null) {
			return null;
		}
		log.debug("根据key查询MysqlGroup成功！");

		Group temp = mysqlGroupMapper.getConvertMysqlGroupToGroup(mysql);
		if (temp != null) {
			log.debug("根据key查询Group成功！");
			return temp;
		}
		return null;
	}

	/**
	 * 通过items查询Group的数据
	 * 
	 * @param items
	 * @return
	 * @throws Exception
	 */
	private List<Group> getGroupsByItems(List<Item> items) throws Exception {
		List<Group> groups = new ArrayList<Group>();
		if (items != null && items.size() > 0) {
			for (Item item : items) {
				Group group = this.getGroupByItemHasRedis(item);
				if (group != null) {
					groups.add(group);
				}
			}
			return groups;
		}
		return null;
	}

	/**
	 * 将SolrGroup对象转换成Map<String ,Object>字段查询Map
	 * 
	 * @param solrUserInfo
	 * @return
	 */
	private void getMapFieldValues(SolrGroup solrGroup,
			Map<String, Object> fieldValues) throws Exception {
		/* 通过反射组装查询字段 */
		Method[] Methods = solrGroup.getClass().getDeclaredMethods();
		for (int i = 0; i < Methods.length; i++) {
			Method method = Methods[i];
			String methodName = method.getName();
			if (!methodName.contains("get"))
				continue;
			String fieldName = SolrUtil.getLowerName(methodName.substring(3));
			Object value = method.invoke(solrGroup, new Object[0]);
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

	public void setMysqlGroupMapper(MysqlGroupMapper mysqlGroupMapper) {
		this.mysqlGroupMapper = mysqlGroupMapper;
	}

	public void setSolrGroupMapper(SolrGroupMapper solrGroupMapper) {
		this.solrGroupMapper = solrGroupMapper;
	}

	public void setAddGroupDestination(Destination addGroupDestination) {
		this.addGroupDestination = addGroupDestination;
	}

	public void setUpdateGroupDestination(Destination updateGroupDestination) {
		this.updateGroupDestination = updateGroupDestination;
	}

	public void setDeleteGroupDestination(Destination deleteGroupDestination) {
		this.deleteGroupDestination = deleteGroupDestination;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setRedisGroupMapper(RedisGroupMapper redisGroupMapper) {
		this.redisGroupMapper = redisGroupMapper;
	}

	public void setTableIdGenerate(TableIdGenerate tableIdGenerate) {
		this.tableIdGenerate = tableIdGenerate;
	}

	public void setrDBCluster(RDBCluster rDBCluster) {
		this.rDBCluster = rDBCluster;
	}
}
