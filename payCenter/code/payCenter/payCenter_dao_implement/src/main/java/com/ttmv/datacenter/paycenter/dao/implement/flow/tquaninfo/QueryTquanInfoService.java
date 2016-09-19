package com.ttmv.datacenter.paycenter.dao.implement.flow.tquaninfo;

import java.math.BigInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedReadDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan.MysqlTquanInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan.RedisTquanInfoMapper;
import com.ttmv.datacenter.paycenter.data.TquanInfo;

/**
 * 查询TquanInfo对象信息
 * @author wulinli
 * 2016-04-21 13:45:40
 */
public class QueryTquanInfoService extends DistributedReadDaoTemplate<TquanInfo,BigInteger>{
	private final Logger logger = LogManager.getLogger(QueryTquanInfoService.class);
	
	private RedisTquanInfoMapper redisTquanInfoMapper;
	private MysqlTquanInfoMapper mysqlTquanInfoMapper;
	
	@Override
	public TquanInfo readPostDB(BigInteger userId) throws Exception {
		if(userId == null){
			logger.error("DAO处理异常 @@ QueryTquanInfoService.readPostDB()参数校验失败,查询条件userID为NULL!!!");
			throw new Exception("OP500");
		}
		TquanInfo TquanInfo = mysqlTquanInfoMapper.getTquanInfoByUserId(userId);
		return TquanInfo;
	}

	@Override
	public TquanInfo readPreDB(BigInteger userId) throws Exception {
		if(userId == null){
			logger.error("DAO处理异常 @@ QueryTquanInfoService.readPreDB()参数校验失败,查询条件userID为NULL!!!");
			throw new Exception("OP500");
		}
		TquanInfo info = redisTquanInfoMapper.getTquanInfoByUserId(userId.toString());
		return info;
	}

	@Override
	public void writePreDBBack(TquanInfo TquanInfo) throws Exception {
		
	}
	
	public void setRedisTquanInfoMapper(RedisTquanInfoMapper redisTquanInfoMapper) {
		this.redisTquanInfoMapper = redisTquanInfoMapper;
	}

	public void setMysqlTquanInfoMapper(MysqlTquanInfoMapper mysqlTquanInfoMapper) {
		this.mysqlTquanInfoMapper = mysqlTquanInfoMapper;
	}
}
