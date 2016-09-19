package com.ttmv.datacenter.paycenter.dao.implement.flow.brokerageinfo;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedWriteDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage.MysqlBrokerageInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage.RedisBrokerageInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.util.MapUtil;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;

public class AddBrokerageInfoService extends DistributedWriteDaoTemplate<BrokerageInfo>{
	
	private final Logger logger = LogManager.getLogger(AddBrokerageInfoService.class);

	private RedisBrokerageInfoMapper redisBrokerageInfoMapper;
	private MysqlBrokerageInfoMapper mysqlBrokerageInfoMapper;
	public AddBrokerageInfoService() {
		super();
	}

	@Override
	public void writePreDB(BrokerageInfo brokerageInfo) throws Exception {
		if(brokerageInfo == null || brokerageInfo.getUserId()==null){
			logger.error("DAO处理异常 @@ AddBrokerageInfoService.writePreDB()参数校验失败!!! parameter is null");
			throw new Exception("OP500");
		}
		Map<String, String> hash = null;
		try {
			hash = MapUtil.beanToMap(brokerageInfo);
		} catch (Exception e) {
			logger.error("MapUtil.beanToMap(brokerageInfo)处理失败!!!",e);
			throw new Exception("OP500");
		}
		redisBrokerageInfoMapper.addRedisBrokerageInfo(brokerageInfo.getUserId().toString(), hash);
	}
	


	@Override
	public void writePostDB(BrokerageInfo brokerageInfo) throws Exception {
		if(brokerageInfo == null){
			logger.error("DAO处理异常 @@ AddBrokerageInfoService.writePostDB()参数校验失败!!!");
			throw new Exception("OP500");
		}
		mysqlBrokerageInfoMapper.addBrokerageInfo(brokerageInfo);
	}
	
	public void setRedisBrokerageInfoMapper(RedisBrokerageInfoMapper redisBrokerageInfoMapper) {
		this.redisBrokerageInfoMapper = redisBrokerageInfoMapper;
	}

	public void setMysqlBrokerageInfoMapper(MysqlBrokerageInfoMapper mysqlBrokerageInfoMapper) {
		this.mysqlBrokerageInfoMapper = mysqlBrokerageInfoMapper;
	}

	@Override
	public Class<BrokerageInfo> dataClass() throws Exception {
		// TODO Auto-generated method stub
		return BrokerageInfo.class;
	}

	
}
