package com.ttmv.datacenter.paycenter.dao.implement.flow.tcoininfo;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedReadDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin.MysqlTcoinInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin.RedisTcoinInfoMapper;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;

/**
 * 查询TcoinInfo对象信息
 * @author wulinli
 * 2016-04-21 13:45:40
 */
public class QueryTcoinInfoService extends DistributedReadDaoTemplate<TcoinInfo,BigInteger>{
	private final Logger logger = LogManager.getLogger(QueryTcoinInfoService.class);
	private RedisTcoinInfoMapper redisTcoinInfoMapper;
	private MysqlTcoinInfoMapper mysqlTcoinInfoMapper;
	
	@Override
	public TcoinInfo readPostDB(BigInteger userId) throws Exception {
		if(userId == null){
			logger.error("DAO处理异常 @@ QueryTcoinInfoService.readPostDB()参数校验失败,查询条件userID为NULL!!!");
			throw new Exception("OP500");
		}
		TcoinInfo tcoinInfo = mysqlTcoinInfoMapper.getTcoinInfoByUserId(userId);
		return tcoinInfo;
	}

	@Override
	public TcoinInfo readPreDB(BigInteger userId) throws Exception {
		if(userId == null){
			logger.error("DAO处理异常 @@ QueryTcoinInfoService.readPreDB()参数校验失败,查询条件userID为NULL!!!");
			throw new Exception("OP500");
		}
		TcoinInfo info = redisTcoinInfoMapper.getTcoinInfoByUserId(userId.toString());
		return info;
	}

	@Override
	public void writePreDBBack(TcoinInfo tcoinInfo) throws Exception {
		
	}
	
	public void setRedisTcoinInfoMapper(RedisTcoinInfoMapper redisTcoinInfoMapper) {
		this.redisTcoinInfoMapper = redisTcoinInfoMapper;
	}

	public void setMysqlTcoinInfoMapper(MysqlTcoinInfoMapper mysqlTcoinInfoMapper) {
		this.mysqlTcoinInfoMapper = mysqlTcoinInfoMapper;
	}
}
