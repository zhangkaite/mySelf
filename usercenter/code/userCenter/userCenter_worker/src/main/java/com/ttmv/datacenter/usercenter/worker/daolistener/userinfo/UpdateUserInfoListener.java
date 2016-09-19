package com.ttmv.datacenter.usercenter.worker.daolistener.userinfo;

import java.math.BigInteger;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.ttmv.datacenter.usercenter.dao.implement.constant.SolrConstant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.HbaseUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.MysqlUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.userinfo.SolrUserInfo;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.HbaseUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.MysqlUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.userinfo.SolrUserInfoMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.worker.daolistener.constant.ListenerConstant;

public class UpdateUserInfoListener implements MessageListener{
	
	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(UpdateUserInfoListener.class);
	
	private SolrUserInfoMapper solrUserInfoMapper;
	private HbaseUserInfoMapper hbaseUserInfoMapper;
	private MysqlUserInfoMapper mysqlUserInfoMapper;
	private Destination updateUserInfoDestination;
	private Destination ucErrorDestination;
	private JmsTemplate jmsTemplate;

	public void onMessage(Message message) {
		
		ActiveMQTextMessage msg = null;
		String jsonData = null;
		UserInfo userInfo = null;
		Map map = null;
		String dataSourceKey = null;
		String reqID = null;
		
		log.debug("[获取updateUserInfoDestination队列数据！开始。。]");
		/* 获取队列信息 */
		if (message instanceof ActiveMQTextMessage) {
			msg = (ActiveMQTextMessage) message;
			try {
				jsonData = msg.getText();
				log.info("=========获取[updateUserInfoDestination]队列消息======:" + jsonData);
				log.debug("从updateUserInfoDestination队列获取数据成功！");
			} catch (Exception e) {
				log.error("从updateUserInfoDestination队列中获取数据失败，失败的原因是：",e);
				return;//终止此次worker运行
			}
		}

		/* json转换成UserInfo 对象 */
		try{				
			map = (Map) JsonUtil.getObjectFromJson(jsonData,Map.class);
			/* 获取UserInfo对象*/
			String str  = JsonUtil.getObjectToJson(map.get("data"));
			reqID = map.get("reqID").toString();
			log.debug("[" +reqID+ "]@@" + "[获取reqID]");
			userInfo = (UserInfo) JsonUtil.getObjectFromJson(str, UserInfo.class);
			log.debug("[" + reqID+ "]@@" + "[修改用户信息，json转换成UserInfo对象成功！]");
		}catch(Exception e){
			log.error("[" + reqID+ "]@@" + "[修改用户信息，json转换成UserInfo对象失败]",e);
			return ;//终止此次worker运行
		}	
		
		/* 循环执行计数 */
		Integer count = (Integer)map.get("count");
		count ++ ;
		if(count <= 3){
				/* 统计循环的次数 */
				map.put("count", count);
				log.debug("[" + reqID+ "]@@" + "[重新添加计数count]");
				/* 修改mysql */
				SolrUserInfo solr = null;
				try{
					log.debug("[" + reqID+ "]@@" + "[开始执行mysql修改。。]");
					if(!map.containsKey(ListenerConstant.MYSQL)){				
						solr = solrUserInfoMapper.getSolrUserInfoByKey(SolrConstant.SOLR_KEY_ID, userInfo.getUserid().toString(), SolrConstant.SOLR_START, SolrConstant.SOLR_UNIQUE);
						log.debug("[" + reqID+ "]@@" + "[查询SolrUserInfo成功！]");
						if(solr  == null ){
							log.debug("[" + reqID+ "]@@" + "[查询SolrUserInfo成功,但是对象为null！]");
							return;
						}
						dataSourceKey = solr.getDataSourceKey();
						log.debug("[" + reqID+ "]@@" + "[获取Mysql数据源dataSourceKey]");
						MysqlUserInfo mysql = mysqlUserInfoMapper.getMysqlUserInfo(new BigInteger(solr.getId().toString()), dataSourceKey);
						log.debug("[" + reqID+ "]@@" + "[查询MysqlUserInfo成功！]");
						if(mysql == null){
							log.debug("[" + reqID+ "]@@" + "[查询MysqlUserInfo成功,但是对象为null！]");
							return;
						}
						/* 将要修改的值复制MysqlUserInfo对象中 */
						mysqlUserInfoMapper.getUpdateMysqlUserInfo(userInfo,mysql);
						log.debug("[" + reqID+ "]@@" + "[MysqlUserInfo新值覆盖旧值]");
						mysqlUserInfoMapper.updateMysqlUserInfo(mysql, dataSourceKey);
						log.debug("[" + reqID+ "]@@" + "[修改MysqlUserInfo成功！]");
						map.put(ListenerConstant.MYSQL, true);
						log.debug("[" + reqID+ "]@@" + "[设置MysqlUserInfo修改成功标示！]");
					}
				}catch(Exception e){
					log.error("[" + reqID+ "]@@" + "[Mysql修改失败！]",e);
					/* 数据重新加入队列 */
					String newJson = null;
					try {
						newJson = JsonUtil.getObjectToJson(map);
						log.debug("[" + reqID+ "]@@" + "[UserInfo数据对象重新转为Json！]");
						backToMq(newJson);
						log.debug("[" + reqID+ "]@@" + "[数据重新加入队列成功！]");
					} catch (Exception e1) {
						log.error("[" + reqID+ "]@@" + "[注意：修改用户信息，mysql修改失败，数据重新加入队列失败！]",e1);
						log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
					}
					return;// 终止此次worker运行
				}
				
				/*  修改hbase */
				try{
					log.debug("[" + reqID+ "]@@" + "[开始执行Hbase修改。。]");
					if (!map.containsKey(ListenerConstant.HBASE)) {
						HbaseUserInfo newHbase = hbaseUserInfoMapper.getConvertUserInfoToHbaseUserInfo(userInfo);
						log.debug("[" + reqID+ "]@@" + "[userInfo对象转HbaseUserInfo对象成功！]");
						HbaseUserInfo oldHbase = hbaseUserInfoMapper.getHbaseUserInfoById(userInfo.getUserid().toString());
						log.debug("[" + reqID+ "]@@" + "[查询HbaseUserInfo对象成功！]");
						if(oldHbase == null){
							log.debug("[" + reqID+ "]@@" + "[查询HbaseUserInfo对象成功，但对象为null！]");
							return;
						}
						BeanCopyProperties.copyProperties(newHbase, oldHbase,false,null);
						log.debug("[" + reqID+ "]@@" + "[HbaseUserInfo新值覆盖旧值]");
						/* 更新 */
						hbaseUserInfoMapper.addHbaseUserInfo(oldHbase, userInfo.getUserid().toString(),reqID);
						log.debug("[" + reqID+ "]@@" + "[HbaseUserInfo修改成功！]");
						map.put(ListenerConstant.HBASE, true);
						log.debug("[" + reqID+ "]@@" + "[设置HbaseUserInfo修改成功的标示！]");
					}
				}catch(Exception e){
					log.error("[" + reqID+ "]@@" + "[Hbase修改失败！]",e);
					/* 数据重新加入队列 */
					String newJson = null;
					try {
						newJson = JsonUtil.getObjectToJson(map);
						log.debug("[" + reqID+ "]@@" + "[UserInfo数据对象重新转为Json！]");
						backToMq(newJson);
						log.debug("[" + reqID+ "]@@" + "[数据重新加入队列成功！]");
					} catch (Exception e1) {
						log.error("[" + reqID+ "]@@" + "[注意：修改用户信息，hbase修改失败，数据重新加入队列失败！]",e1);
						log.error("[" + reqID+ "]@@" + "[失败的数据是："+ newJson);
					}
					return;// 终止此次worker运行
				}
			}else if(count > 3){
				/* 错误数据放入错误队列中 */
				backToErrorMq(jsonData);
				log.error("[" + reqID+ "]@@" + "[修改用户信息数据woker执行超过3次，数据是："+jsonData);
			}
		}		
	/**
	 * 如果操作队列中间出现问题，则将队列的数据 重新返回到队列中
	 * 
	 * @param json
	 */
	private void backToMq(final String jsonData) {
		try {
			jmsTemplate.send(updateUserInfoDestination, new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(jsonData);
				}
			});
			log.debug("操作修改用户队列的数据失败后，数据重新加入队列成功！");
		} catch (Exception e) {
			log.error("注意，操作修改用户队列的数据失败后，数据重新加入队列失败，请手工把数据库中数据清理掉！失败原因：",e);
			/** 如果数据在入库的时候出现了错误的时候，再次加入队列的时候出现错误改怎么办？**/
			
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

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setUpdateUserInfoDestination(Destination updateUserInfoDestination) {
		this.updateUserInfoDestination = updateUserInfoDestination;
	}
	
	public void setUcErrorDestination(Destination ucErrorDestination) {
		this.ucErrorDestination = ucErrorDestination;
	}
}
