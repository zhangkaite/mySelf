package com.ttmv.datacenter.paycenter.dao.implement.flow.tquaninfo;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedWriteDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan.MysqlTquanInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan.RedisTquanInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.util.MapUtil;
import com.ttmv.datacenter.paycenter.data.TquanInfo;

public class AddTquanInfoService extends DistributedWriteDaoTemplate<TquanInfo>{
	
	public AddTquanInfoService() {
		super();
	}

	private final Logger logger = LogManager.getLogger(AddTquanInfoService.class);
	private RedisTquanInfoMapper redisTquanInfoMapper;
	private MysqlTquanInfoMapper mysqlTquanInfoMapper;
	
	@Override
	public void writePreDB(TquanInfo tquanInfo) throws Exception {
		if(tquanInfo == null || tquanInfo.getUserId() == null){
			logger.error("DAO处理异常 @@ AddTquanInfoService.writePreDB()参数校验失败!!! parameter is null");
			throw new Exception("OP500");
		}
		Map<String, String> hash = null;
		try {
			hash = MapUtil.beanToMap(tquanInfo);
		} catch (Exception e) {
			logger.error("MapUtil.beanToMap(tquanInfo)处理失败!!!",e);
			throw new Exception("OP500");
		}
		redisTquanInfoMapper.addRedisTquanInfo(tquanInfo.getUserId().toString(), hash);
	}
	

	@Override
	public void writePostDB(TquanInfo tquanInfo) throws Exception {
		if(tquanInfo == null){
			logger.error("DAO处理异常 @@ AddTquanInfoService.writePreDB()参数校验失败!!! tquanInfo is null ... ");
			throw new Exception("OP500");
		}
		mysqlTquanInfoMapper.addMysqlTquanInfo(tquanInfo);
	}
	
	public void setRedisTquanInfoMapper(RedisTquanInfoMapper redisTquanInfoMapper) {
		this.redisTquanInfoMapper = redisTquanInfoMapper;
	}

	public void setMysqlTquanInfoMapper(MysqlTquanInfoMapper mysqlTquanInfoMapper) {
		this.mysqlTquanInfoMapper = mysqlTquanInfoMapper;
	}


	@Override
	public Class<TquanInfo> dataClass() throws Exception {
		// TODO Auto-generated method stub
		return TquanInfo.class;
	}
	
}
