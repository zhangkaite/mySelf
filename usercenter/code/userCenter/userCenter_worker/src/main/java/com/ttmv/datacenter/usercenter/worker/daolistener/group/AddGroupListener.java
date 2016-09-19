package com.ttmv.datacenter.usercenter.worker.daolistener.group;

import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ttmv.datacenter.usercenter.dao.implement.constant.SolrConstant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.group.MysqlGroup;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.group.MysqlGroupMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.group.SolrGroupMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.Group;
import com.ttmv.datacenter.usercenter.worker.daolistener.constant.ListenerConstant;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AddGroupListener implements MessageListener {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(AddGroupListener.class);

	/* Group添加信息 队列 */
	private Destination addGroupDestination;
	private MysqlGroupMapper mysqlGroupMapper;
	private SolrGroupMapper solrGroupMapper;
	private Destination ucErrorDestination;
	private JmsTemplate jmsTemplate;

	/* 监听地址，处理信息 */
	public void onMessage(Message message) {

		ActiveMQTextMessage msg = null;
		String jsonData = null;
		String dataSourceKey = null;
		Map map = null;
		Group group = null;
		String reqID = null;
		
		log.debug("[获取addGroupDestination队列数据！开始。。]");
		if (message instanceof ActiveMQTextMessage) {
			msg = (ActiveMQTextMessage) message;
			try {
				jsonData = msg.getText();
				log.info("=========获取[addGroupDestination]队列消息======:" + jsonData);
				log.debug("从addGroupDestination队列获取数据成功！");
			} catch (Exception e) {
				log.error("从addGroupDestination队列获取数据失败！",e);
				return;// 终止此次worker运行
			}

			/* json转换成Group 对象 */
			try {
				map = (Map) JsonUtil.getObjectFromJson(jsonData, Map.class);
				String str = JsonUtil.getObjectToJson(map.get("data"));
				reqID = map.get("reqID")+"";
				log.debug("[" +reqID+ "]@@" + "[获取reqID]");
				dataSourceKey = map.get("dataSourceKey")+"";
				log.debug("[" +reqID+ "]@@" + "[获取dataSourceKey]");
				group = (Group) JsonUtil.getObjectFromJson(str, Group.class);
				log.debug("[" + reqID+ "]@@" + "[json转换Group对象成功！]");
			} catch (Exception e) {
				log.error("[" + reqID+ "]@@" + "[json转换Group对象失败！]",e);
				return;// 终止此次worker运行
			}

			/* 向mysql中添加数据 */
			Integer count = (Integer) map.get("count");
			count++;
			List<Object> results = null;
			if (count <= 3) {
				map.put("count", count);
				log.debug("[" + reqID+ "]@@" + "[重新添加计数count]");
				try {
					if (!map.containsKey(ListenerConstant.MYSQL)) {
						MysqlGroup mysql = mysqlGroupMapper.getConvertGroupToMsqlGroup(group);
						log.debug("[" + reqID+ "]@@" + "[Group对象转MysqlGroup对象成功！]");
						results = mysqlGroupMapper.addMysqlGroup(mysql,dataSourceKey);
						map.put(ListenerConstant.MYSQL, true);
						log.debug("[" + reqID+ "]@@" + "[设置Group加成功的标示]");
						log.debug("[" + reqID+ "]@@" + "[添加Group对象成功！]");
					}
				} catch (Exception e) {
					log.error("[" + reqID+ "]@@" + "[添加Mysql失败！]",e);
					/* 数据重新加入队列 */
					String newJson = null;
					try {
						newJson = JsonUtil.getObjectToJson(map);
						log.debug("[" + reqID+ "]@@" + "[Group数据对象重新转为Json！]");
						backToMq(newJson);
						log.debug("[" + reqID+ "]@@" + "[数据重新加入队列成功！]");
					} catch (Exception e1) {
						log.error("[" + reqID+ "]@@" + "[注意：添加MysqlGroup信息，存储mysql失败，数据重新加入队列失败！]",e1);
						log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
					}
					return;// 终止此次worker的运行
				}

				/* 向solr中添加数据 */
				/*try {
					log.debug("[" + reqID+ "]@@" + "[开始执行Solr添加。。]");
					if (!map.containsKey(ListenerConstant.SOLR)) {
						log.debug("[" + reqID+ "]@@" +"[groupID :]"+ group.getGroupId() +"---[获取mysql存放的key.....]" +results.get(0));
						solrGroupMapper.updateSolrGroupField(group.getGroupId().toString(), SolrConstant.SOLR_KEY_DATASOURCEKEY, results.get(0).toString(), reqID);
						log.debug("[" + reqID+ "]@@" + "[SolrGroup对象添加dataSourceKey！]");
						map.put(ListenerConstant.SOLR, true);
						log.debug("[" + reqID+ "]@@" + "[设置SolrGroup添加成功的标示]");
					}
				} catch (Exception e) {
					log.error("[" + reqID+ "]@@" + "[Solr添加失败！]",e);
					 数据重新加入队列 
					String newJson = null;
					try {
						newJson = JsonUtil.getObjectToJson(map);
						log.debug("[" + reqID+ "]@@" + "[Group数据对象重新转为Json！]");
						backToMq(newJson);
						log.debug("[" + reqID+ "]@@" + "[数据重新加入队列成功！]");
					} catch (Exception e1) {
						log.error("[" + reqID+ "]@@" + "[注意：添加SolrGroup信息，solr存储失败，数据重新加入队列失败！]",e1);
						log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
					}
					return;// 终止此次worker的运行
				}*/
			} else if (count > 3) {
				/* 错误数据放入错误队列中 */
				backToErrorMq(jsonData);
				log.error("[" + reqID+ "]@@" + "[添加SolrGroup信息数据woker执行超过3次，数据是："+jsonData);
			}
		}
		log.debug("[" + reqID+ "]@@" + "[操作addGroupDestination队列数据成功！结束。。]");
	}

	/**
	 * 如果操作队列中间出现问题，则将队列的数据 重新返回到队列中
	 * 
	 * @param json
	 */
	private void backToMq(final String jsonData) {
		try {
			jmsTemplate.send(addGroupDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(jsonData);
				}
			});
			log.info("操作添加用户组队列的数据失败，数据重新加入队列成功！");
		} catch (Exception e) {
			log.error("注意，操作添加用户组队列的数据失败，数据重新加入队列失败，请手工把数据库中数据清理掉！失败原因：",e);
		}
	}

	/**
	 * 如果数据经过3次重试以后没有正确入库，则直接将数据放入错误的队列中
	 * 
	 * @param json
	 */
	private void backToErrorMq(final String jsonData) {
		try {
			jmsTemplate.send(ucErrorDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(jsonData);
				}
			});
			log.debug("数据放入错误数据队列成功！");
		} catch (Exception e) {
			log.error("数据放入错误数据队列失败，错误的原因是：",e);
			log.error("数据放入错误数据队列失败，错误的数据是：" + jsonData);
		}
	}
	
	public void setAddGroupDestination(Destination addGroupDestination) {
		this.addGroupDestination = addGroupDestination;
	}

	public void setMysqlGroupMapper(MysqlGroupMapper mysqlGroupMapper) {
		this.mysqlGroupMapper = mysqlGroupMapper;
	}

	public void setSolrGroupMapper(SolrGroupMapper solrGroupMapper) {
		this.solrGroupMapper = solrGroupMapper;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setUcErrorDestination(Destination ucErrorDestination) {
		this.ucErrorDestination = ucErrorDestination;
	}
}
