package com.ttmv.datacenter.paycenter.triger.triggerimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.brokerage.MysqlBrokerageInfo;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.tcoin.MysqlTcoinInfo;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.tquan.MysqlTquanInfo;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage.MysqlBrokerageInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage.RedisBrokerageInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin.MysqlTcoinInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin.RedisTcoinInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan.MysqlTquanInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan.RedisTquanInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.util.MapUtil;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;
import com.ttmv.datacenter.paycenter.data.TquanInfo;
import com.ttmv.datacenter.paycenter.triger.BaseTask;
import com.ttmv.datacenter.paycenter.triger.constant.TriggerConstant;
import com.ttmv.datacenter.paycenter.triger.triggerimpl.triggerconstant.Trigger;

/**
 * 完成用户账户mysql数据flush到redis数据库
 * @author wll
 *
 */
public class ReflushMysqlToRedisTrigger implements BaseTask{

	private static Logger log = LogManager.getLogger(ReflushMysqlToRedisTrigger.class);
	private static String KEY_NAME = "trigger_reflush_redis";
	private MysqlBrokerageInfoMapper mysqlBrokerageInfoMapper;
	private MysqlTcoinInfoMapper mysqlTcoinInfoMapper;
	private MysqlTquanInfoMapper mysqlTquanInfoMapper;
	
	private RedisBrokerageInfoMapper redisBrokerageInfoMapper;
	private RedisTcoinInfoMapper redisTcoinInfoMapper;
	private RedisTquanInfoMapper redisTquanInfoMapper;

	public void doWork() throws Exception {
		/* 获取所有佣金账户 */
		try{			
			List<MysqlBrokerageInfo> list = mysqlBrokerageInfoMapper.getAllMysqlBrokerageInfo();
			log.debug("[回刷]获取T券账户的size :" + list.size());
			if(list != null && list.size() > 0){
				for(MysqlBrokerageInfo mysql : list){
					BrokerageInfo info = new BrokerageInfo();
					BeanUtils.copyProperties(mysql, info);
					log.debug("[回刷]BrokerageInfo的id："+info.getUserId());
					String userId = info.getUserId().toString();
					Map<String,String> hash = new HashMap<String, String>();
					hash = MapUtil.beanToMap(info);
					redisBrokerageInfoMapper.addRedisBrokerageInfo(userId, hash);
				}
			}
		}catch(Exception e){
			log.error("[回刷]mysql佣金账户回刷redis出错！",e );
			throw new Exception("[回刷]mysql佣金账户回刷redis出错！",e);
		}
		
		/* 获取TB账户 */
		try{			
			List<MysqlTcoinInfo> list = mysqlTcoinInfoMapper.getAllMysqlTcoinInfo();
			log.debug("[回刷]获取TB账户的size :" + list.size());
			if(list != null && list.size() > 0){
				for(MysqlTcoinInfo mysql : list){
					TcoinInfo info = new TcoinInfo();
					BeanUtils.copyProperties(mysql, info);
					log.debug("[回刷]tcoin的id："+info.getUserId());
					String userId = info.getUserId().toString();
					Map<String,String> hash = new HashMap<String, String>();
					hash = MapUtil.beanToMap(info);
					redisTcoinInfoMapper.addRedisTcoinInfo(userId, hash);
				}
			}
		}catch(Exception e){
			log.error("[回刷]mysqlTB账户回刷redis出错！",e );
			throw new Exception("[回刷]mysqlTB账户回刷redis出错！",e );
		}
		
		/* 获取T券账户 */
		try{			
			List<MysqlTquanInfo> list = mysqlTquanInfoMapper.getAllMysqlTquanInfo();
			log.debug("[回刷]获取T券账户的size :" + list.size());
			if(list != null && list.size() > 0){
				for(MysqlTquanInfo mysql : list){
					TquanInfo info = new TquanInfo();
					BeanUtils.copyProperties(mysql, info);
					log.debug("[回刷]tquan的id："+info.getUserId());
					String userId = info.getUserId().toString();
					Map<String,String> hash = new HashMap<String, String>();
					hash = MapUtil.beanToMap(info);
					redisTquanInfoMapper.addRedisTquanInfo(userId, hash);
				}
			}
		}catch(Exception e){
			log.error("[回刷]mysqlT券账户回刷redis出错！",e );
			throw new Exception("[回刷]mysqlT券账户回刷redis出错！",e );
		}
		
		/* 修改TriggerConstant的值 修改为holdon */
		Trigger.setCollection(KEY_NAME, TriggerConstant.FLUSH_REDIS_HOLDON);
		
	}
	
	/**
	 * 线程执行体
	 */
	public void run() {
		try {
			log.debug("线程执行ReflushMysqlToRedisTrigger开始！");
			this.doWork();
			log.debug("线程执行ReflushMysqlToRedisTrigger结束！");
		} catch (Exception e) {
			log.error("线程执行ReflushMysqlToRedisTrigger出错！",e);
		}
	}
	
	public void setMysqlBrokerageInfoMapper(
			MysqlBrokerageInfoMapper mysqlBrokerageInfoMapper) {
		this.mysqlBrokerageInfoMapper = mysqlBrokerageInfoMapper;
	}

	public void setMysqlTcoinInfoMapper(MysqlTcoinInfoMapper mysqlTcoinInfoMapper) {
		this.mysqlTcoinInfoMapper = mysqlTcoinInfoMapper;
	}

	public void setMysqlTquanInfoMapper(MysqlTquanInfoMapper mysqlTquanInfoMapper) {
		this.mysqlTquanInfoMapper = mysqlTquanInfoMapper;
	}

	public void setRedisBrokerageInfoMapper(
			RedisBrokerageInfoMapper redisBrokerageInfoMapper) {
		this.redisBrokerageInfoMapper = redisBrokerageInfoMapper;
	}

	public void setRedisTcoinInfoMapper(RedisTcoinInfoMapper redisTcoinInfoMapper) {
		this.redisTcoinInfoMapper = redisTcoinInfoMapper;
	}

	public void setRedisTquanInfoMapper(RedisTquanInfoMapper redisTquanInfoMapper) {
		this.redisTquanInfoMapper = redisTquanInfoMapper;
	}

}
