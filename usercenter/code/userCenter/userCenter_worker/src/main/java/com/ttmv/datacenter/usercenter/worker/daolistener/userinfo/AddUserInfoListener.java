package com.ttmv.datacenter.usercenter.worker.daolistener.userinfo;

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
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.HbaseUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.MysqlUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.HbaseUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.MysqlUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.SolrUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.worker.daolistener.constant.ListenerConstant;

public class AddUserInfoListener implements MessageListener {

	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(AddUserInfoListener.class);
	private SolrUserInfoMapper solrUserInfoMapper;
	private HbaseUserInfoMapper hbaseUserInfoMapper;
	private MysqlUserInfoMapper mysqlUserInfoMapper;
	private Destination addUserInfoDestination;
	private Destination ucErrorDestination;
	private JmsTemplate jmsTemplate;

	public void onMessage(Message message) {

		ActiveMQTextMessage msg = null;
		String jsonData = null;
		Map map = null;
		UserInfo userInfo = null;
		String dataSourceKey = null;
		String reqID = null;
		
		log.debug("[获取addUserInfoDestination队列数据！开始。。]");
		if (message instanceof ActiveMQTextMessage) {
			msg = (ActiveMQTextMessage) message;
			try {
				jsonData = msg.getText();
				log.info("=========获取[addUserInfoDestination]队列消息======:" + jsonData);
				log.debug("从addUserInfoDestination队列获取数据成功！");
			} catch (Exception e) {
				log.error("从addUserInfoDestination队列获取数据成功！",e);
				/* 数据重新加入队列 */
				backToMq(jsonData);
				return;//终止此次worker运行
			}
			
			/* json转换成UserInfo 对象 */
			try{	
				/* 转换map对象 */
				map = (Map) JsonUtil.getObjectFromJson(jsonData,Map.class);			
				/* 获取UserInfo对象*/
				String str  = JsonUtil.getObjectToJson(map.get("data"));
				reqID = map.get("reqID").toString();
				log.debug("[" +reqID+ "]@@" + "[获取reqID]");
				dataSourceKey = map.get("dataSourceKey").toString();
				log.debug("[" +reqID+ "]@@" + "[获取dataSourceKey]");
				userInfo = (UserInfo) JsonUtil.getObjectFromJson(str, UserInfo.class);
				log.debug("[" + reqID+ "]@@" + "[json转换userInfo对象]");
			}catch(Exception e){
				log.error("[" + reqID+ "]@@" + "[json转换UserInfo对象失败！]",e);
				backToMq(jsonData);
				return ;//终止此次worker运行
			}
			
			/* 循环执行计数 */
			Integer count = (Integer)map.get("count");
			count ++ ;
			if(count <= 3){
					map.put("count", count);
					log.debug("[" + reqID+ "]@@" + "[重新添加计数count]");
					/* 向mysql中添加数据 */
					List<Object> results = null;
					try {
						log.debug("[" + reqID+ "]@@" + "[开始执行mysql添加。。]");
						if (!map.containsKey(ListenerConstant.MYSQL)) {
							MysqlUserInfo mysql = mysqlUserInfoMapper.getConvertUserInfoToMysqlUserInfo(userInfo);
							log.debug("[" + reqID+ "]@@" + "[userInfo对象转MysqlUserInfo对象成功！]");
							results = mysqlUserInfoMapper.addMysqlUserInfo(mysql,dataSourceKey);
							map.put(ListenerConstant.MYSQL, true);
							log.debug("[" + reqID+ "]@@" + "[设置MysqlUserInfo添加成功的标示]");
							log.debug("[" + reqID+ "]@@" + "[添加MysqlUserInfo对象成功！]");
						}
					} catch (Exception e) {
						log.error("[" + reqID+ "]@@" + "[Mysql添加失败！]",e);
						/* 数据重新加入队列 */
						String newJson = null;
						try {
							newJson = JsonUtil.getObjectToJson(map);
							log.debug("[" + reqID+ "]@@" + "[UserInfo数据对象重新转为Json！]");
							backToMq(newJson);
							log.debug("[" + reqID+ "]@@" + "[数据重新加入队列成功！]");
						} catch (Exception e1) {
							log.error("[" + reqID+ "]@@" + "[注意：添加MysqlUserInfo信息，mysql存储失败，数据重新加入队列失败！]",e1);
							log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
						}
						return;// 终止此次worker的运行
					}
					
					/* 向solr添加数据 */
					/*try {
						log.debug("[" + reqID+ "]@@" + "[开始执行Solr添加。。]");
						if (!map.containsKey(ListenerConstant.SOLR)) {
							solrUserInfoMapper.updateSolrUserInfoField(userInfo.getUserid().toString(), SolrConstant.SOLR_KEY_DATASOURCEKEY, dataSourceKey, reqID);
							log.debug("[" + reqID+ "]@@" + "[SolrUserInfo对象添加dataSourceKey！]");
							map.put(ListenerConstant.SOLR, true);
							log.debug("[" + reqID+ "]@@" + "[设置SolrUserInfo添加成功的标示]");
						}
					} catch (Exception e) {
						log.error("[" + reqID+ "]@@" + "[Solr添加失败！]",e);
						 数据重新加入队列 
						String newJson = null;
						try {
							newJson = JsonUtil.getObjectToJson(map);
							log.debug("[" + reqID+ "]@@" + "[UserInfo数据对象重新转为Json！]");
							backToMq(newJson);
						} catch (Exception e1) {
							log.error("[" + reqID+ "]@@" + "[注意：添加SolrUserInfo信息，solr存储失败，数据重新加入队列失败！]",e1);
							log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
						}
						return;// 终止此次worker运行
					}*/
					
					/* 向hbase中添加数据 */
					try{
						log.debug("[" + reqID+ "]@@" + "[开始执行Hbase添加。。]");
						if (!map.containsKey(ListenerConstant.HBASE)) {
							HbaseUserInfo hbase = hbaseUserInfoMapper.getConvertUserInfoToHbaseUserInfo(userInfo);
							log.debug("[" + reqID+ "]@@" + "[userInfo对象转HbaseUserInfo对象成功！]");
							hbaseUserInfoMapper.addHbaseUserInfo(hbase, userInfo.getUserid().toString(),reqID);
							map.put(ListenerConstant.HBASE, true);
							log.debug("[" + reqID+ "]@@" + "[设置HbaseUserInfo添加成功的标示]");
						}
					}catch(Exception e){
						log.error("[" + reqID+ "]@@" + "[Hbase添加失败！]",e);
						/* 数据重新加入队列 */
						String newJson = null;
						try {
							newJson = JsonUtil.getObjectToJson(map);
							log.debug("[" + reqID+ "]@@" + "[UserInfo数据对象重新转为Json！]");
							backToMq(newJson);
							log.debug("[" + reqID+ "]@@" + "[数据重新加入队列成功！]");
						} catch (Exception e1) {
							log.error("[" + reqID+ "]@@" + "[注意：添加HbaseUserInfo信息，hbase存储失败，数据重新加入队列失败！]",e1);
							log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
						}
						return;// 终止此次worker运行
					}
				}else if(count > 3){//如果此次数据的执行次数超过了3次，则将数据放入到日志中，手工执行
					/* 错误数据放入错误队列中 */
					backToErrorMq(jsonData);
					log.error("[" + reqID+ "]@@" + "[添加UserInfo信息数据woker执行超过3次，数据是："+jsonData);
				}
			}
		log.debug("[操作addUserInfoDestination队列数据成功！结束。。]");
	}

	/**
	 * 如果操作队列中间出现问题，则将队列的数据 重新返回到队列中
	 * 
	 * @param json
	 */
	private void backToMq(final String jsonData) {
		try {
			jmsTemplate.send(addUserInfoDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(jsonData);
				}
			});
			log.debug("操作添加用户队列的数据失败，数据重新加入队列成功！");
		} catch (Exception e) {
			log.error("注意，操作添加用户队列的数据失败，数据重新加入队列失败，请手工把数据库中数据清理掉！失败原因：",e);
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

	public void setSolrUserInfoMapper(SolrUserInfoMapper solrUserInfoMapper) {
		this.solrUserInfoMapper = solrUserInfoMapper;
	}

	public void setHbaseUserInfoMapper(HbaseUserInfoMapper hbaseUserInfoMapper) {
		this.hbaseUserInfoMapper = hbaseUserInfoMapper;
	}

	public void setMysqlUserInfoMapper(MysqlUserInfoMapper mysqlUserInfoMapper) {
		this.mysqlUserInfoMapper = mysqlUserInfoMapper;
	}

	public void setAddUserInfoDestination(Destination addUserInfoDestination) {
		this.addUserInfoDestination = addUserInfoDestination;
	}

	public void setUcErrorDestination(Destination ucErrorDestination) {
		this.ucErrorDestination = ucErrorDestination;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
}
