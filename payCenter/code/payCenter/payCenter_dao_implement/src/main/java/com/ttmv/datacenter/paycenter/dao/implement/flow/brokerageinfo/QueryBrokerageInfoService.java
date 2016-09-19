package com.ttmv.datacenter.paycenter.dao.implement.flow.brokerageinfo;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedReadDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage.MysqlBrokerageInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage.RedisBrokerageInfoMapper;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;

/**
 * 查询BrokerageInfo对象信息
 * @author wulinli
 * 2016-04-21 13:45:40
 */
public class QueryBrokerageInfoService extends DistributedReadDaoTemplate<BrokerageInfo,BigInteger>{
	

	private final Logger logger = LogManager.getLogger(QueryBrokerageInfoService.class);
	
	private RedisBrokerageInfoMapper redisBrokerageInfoMapper;
	private MysqlBrokerageInfoMapper mysqlBrokerageInfoMapper;
	
	@Override
	public BrokerageInfo readPostDB(BigInteger userId) throws Exception {
		if(userId == null){
			logger.error("DAO处理异常 @@ QueryBrokerageInfoService.readPostDB()参数校验失败!!!");
			throw new Exception("OP500");
		}
		BrokerageInfo BrokerageInfo = mysqlBrokerageInfoMapper.getBrokerageInfoByUserId(userId);
		return BrokerageInfo;
	}

	@Override
	public BrokerageInfo readPreDB(BigInteger userId) throws Exception {
		if(userId == null){
			logger.error("DAO处理异常 @@ QueryBrokerageInfoService.readPreDB()参数校验失败!!!");
			throw new Exception("OP500");
		}
		BrokerageInfo info = redisBrokerageInfoMapper.getBrokerageInfoByUserId(userId.toString());
		return info;
	}

	@Override
	public void writePreDBBack(BrokerageInfo BrokerageInfo) throws Exception {
		
	}
	
	public void setRedisBrokerageInfoMapper(RedisBrokerageInfoMapper redisBrokerageInfoMapper) {
		this.redisBrokerageInfoMapper = redisBrokerageInfoMapper;
	}

	public void setMysqlBrokerageInfoMapper(MysqlBrokerageInfoMapper mysqlBrokerageInfoMapper) {
		this.mysqlBrokerageInfoMapper = mysqlBrokerageInfoMapper;
	}
}
