package com.ttmv.datacenter.paycenter.dao.implement.flow.tcoininfo;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedWriteDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin.MysqlTcoinInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin.RedisTcoinInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.util.MapUtil;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;

public class AddTcoinInfoService extends DistributedWriteDaoTemplate<TcoinInfo>{
	
	public AddTcoinInfoService() {
		super();
	}
	
	private final Logger logger = LogManager.getLogger(AddTcoinInfoService.class);
	private RedisTcoinInfoMapper redisTcoinInfoMapper;
	private MysqlTcoinInfoMapper mysqlTcoinInfoMapper;

	@Override
	public void writePostDB(TcoinInfo tcoinInfo) throws Exception {
		if(tcoinInfo == null){
			logger.error("DAO处理异常 @@ AddTcoinInfoService.writePreDB()参数校验失败!!! parameter is null");
			throw new Exception("OP500");
		}
		mysqlTcoinInfoMapper.addTcoinInfo(tcoinInfo);
	}
	
	@Override
	public void writePreDB(TcoinInfo tcoinInfo) throws Exception {
		if(tcoinInfo == null || tcoinInfo.getUserId() == null){
			logger.error("DAO处理异常 @@ AddTcoinInfoService.writePreDB()参数校验失败!!!");
			throw new Exception("OP500");
		}
		Map<String, String> hash = null;
		try {
			hash = MapUtil.beanToMap(tcoinInfo);
		} catch (Exception e) {
			logger.error("MapUtil.beanToMap(tcoinInfo)处理失败!!!",e);
			throw new Exception("OP500");
		}
		redisTcoinInfoMapper.addRedisTcoinInfo(tcoinInfo.getUserId().toString(), hash);
	}
	
	public void setRedisTcoinInfoMapper(RedisTcoinInfoMapper redisTcoinInfoMapper) {
		this.redisTcoinInfoMapper = redisTcoinInfoMapper;
	}

	public void setMysqlTcoinInfoMapper(MysqlTcoinInfoMapper mysqlTcoinInfoMapper) {
		this.mysqlTcoinInfoMapper = mysqlTcoinInfoMapper;
	}

	@Override
	public Class<TcoinInfo> dataClass() throws Exception {
		// TODO Auto-generated method stub
		return TcoinInfo.class;
	}
}
