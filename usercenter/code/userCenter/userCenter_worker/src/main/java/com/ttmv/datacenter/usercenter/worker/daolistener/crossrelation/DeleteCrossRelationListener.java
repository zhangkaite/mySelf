package com.ttmv.datacenter.usercenter.worker.daolistener.crossrelation;

import java.math.BigInteger;
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
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation.SolrUserCrossRelation;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation.MysqlUserCrossRelationMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation.SolrUserCrossRelationMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;
import com.ttmv.datacenter.usercenter.worker.daolistener.constant.ListenerConstant;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DeleteCrossRelationListener implements MessageListener{
	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(DeleteCrossRelationListener.class);
	
	private Destination deleteCrossDestination;
	private MysqlUserCrossRelationMapper mysqlUserCrossRelationMapper;
	private SolrUserCrossRelationMapper solrUserCrossRelationMapper;
	private Destination ucErrorDestination;
	private JmsTemplate jmsTemplate;
	
	public void onMessage(Message message) {
		ActiveMQTextMessage msg = null;
		String jsonData = null;
		UserCrossRelation userCrossRelation = null;
		Map map = null;
		String reqID = null;
		
		log.debug("获取deleteCrossDestination队列数据！开始。。");
		if (message instanceof ActiveMQTextMessage) {
			msg = (ActiveMQTextMessage) message;
			try {
				jsonData = msg.getText();
				log.info("=========获取[deleteCrossDestination]队列消息======:" + jsonData);
				log.debug("从deleteCrossDestination队列获取数据成功！");
			} catch (Exception e) {
				log.error("从deleteCrossDestination队列中获取删除用户好友信息数据失败！" ,e);
				return;//终止此次worker运行
			}
		}
		
		/* json转换成UserInfo 对象 */
		try{	
			map = (Map) JsonUtil.getObjectFromJson(jsonData,Map.class);
			String id=JsonUtil.getObjectToJson(map.get("data"));
			reqID = map.get("reqID").toString();
			log.debug("[" +reqID+ "]@@" + "[获取reqID]");
			userCrossRelation = new UserCrossRelation();
			userCrossRelation.setId(new BigInteger(id));
			log.debug("[" + reqID+ "]@@" + "[声明UserCrossRelation对象成功！]");
		}catch(Exception e){
			log.error("[" + reqID+ "]@@" + "[json转换UserCrossRelation对象失败！]",e);
			/* 数据重新加入队列 */
			backToMq(jsonData);
			return ;//终止此次worker运行
		}
		
		/* 删除mysql */
		Integer count = (Integer)map.get("count");
		count ++ ;
		if( count <= 3 ){
			map.put("count", count);
			log.debug("[" + reqID+ "]@@" + "[重新添加计数count]");
			try{
				log.debug("[" + reqID+ "]@@" + "[开始执行mysql删除。。]");
				if (!map.containsKey(ListenerConstant.MYSQL)) {
					SolrUserCrossRelation solr = solrUserCrossRelationMapper.getUserCrossRelationByKey(SolrConstant.SOLR_KEY_ID, userCrossRelation.getId().toString(), SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE);
					log.debug("[" + reqID+ "]@@" + "[查询SolrUserCrossRelation成功！]");
					if(solr == null){
						log.debug("[" + reqID+ "]@@" + "[查询SolrUserCrossRelation成功,但是对象为null！id是："+userCrossRelation.getId()+"]");
						return;
					}
					mysqlUserCrossRelationMapper.deleteMysqlUserCrossRelationById(new BigInteger(solr.getId()), solr.getDataSourceKey());
					log.debug("[" + reqID+ "]@@" + "[mysql删除成功！]");
					map.put(ListenerConstant.MYSQL, true);
					log.debug("[" + reqID+ "]@@" + "[设置mysql删除成功标示！]");
				}
			}catch(Exception e){
				log.error("[" + reqID+ "]@@" + "[Mysql删除失败！]",e);
				/* 数据重新加入队列 */
				String newJson = null;
				try {
					newJson = JsonUtil.getObjectToJson(map);
					log.debug("[" + reqID+ "]@@" + "[UserCrossRelation数据对象重新转为Json！]");
					backToMq(newJson);
					log.debug("[" + reqID+ "]@@" + "[数据重新加入队列成功！]");
				} catch (Exception e1) {
					log.error("[" + reqID+ "]@@" + "[注意：删除MysqlUserCrossRelation信息，mysql删除失败，数据重新加入队列失败！]",e1);
					log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
				}
				return;// 终止此次worker的运行
			}
			
			/* 向solr中删除数据 */
			try{
				log.debug("[" + reqID+ "]@@" + "[开始执行solr删除。。]");
				if (!map.containsKey(ListenerConstant.SOLR)) {
					solrUserCrossRelationMapper.deleteSolrUserCrossRelation(userCrossRelation.getId().toString(),reqID);
					log.debug("[" + reqID+ "]@@" + "[Solr删除成功！]");
					map.put(ListenerConstant.SOLR, true);
					log.debug("[" + reqID+ "]@@" + "[设置Solr删除成功标示！]");
				}
			}catch(Exception e){
				log.debug("[" + reqID+ "]@@" + "[Solr删除失败！]",e);
				/* 数据重新加入队列 */
				String newJson = null;
				try {
					newJson = JsonUtil.getObjectToJson(map);
					log.debug("[" + reqID+ "]@@" + "[UserCrossRelation数据对象重新转为Json！]");
					backToMq(newJson);
					log.debug("[" + reqID+ "]@@" + "[数据重新加入队列成功！]");
				} catch (Exception e1) {
					log.error("[" + reqID+ "]@@" + "[注意：删除SolrUserCrossRelation信息，Solr删除失败，数据重新加入队列失败！]",e1);
					log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
				}
				return;// 终止此次worker的运行
			}
		}else if( count > 3){
			/* 错误数据放入错误队列中 */
			backToErrorMq(jsonData);
			log.error("[" + reqID+ "]@@" + "[删除UserCrossRelation数据woker执行超过3次，数据是："+jsonData);
		}
		log.debug("[" + reqID+ "]@@" + "[操作deleteCrossDestination队列数据成功！结束。。]");
	}	

	/**
	 * 如果操作队列中间出现问题，则将队列的数据 重新返回到队列中
	 * 
	 * @param json
	 */
	private void backToMq(final String jsonData) {
		try {
			jmsTemplate.send(deleteCrossDestination, new MessageCreator() {
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
	
	public void setDeleteCrossDestination(Destination deleteCrossDestination) {
		this.deleteCrossDestination = deleteCrossDestination;
	}

	public void setMysqlUserCrossRelationMapper(
			MysqlUserCrossRelationMapper mysqlUserCrossRelationMapper) {
		this.mysqlUserCrossRelationMapper = mysqlUserCrossRelationMapper;
	}

	public void setSolrUserCrossRelationMapper(
			SolrUserCrossRelationMapper solrUserCrossRelationMapper) {
		this.solrUserCrossRelationMapper = solrUserCrossRelationMapper;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setUcErrorDestination(Destination ucErrorDestination) {
		this.ucErrorDestination = ucErrorDestination;
	}
}
