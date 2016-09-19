package com.ttmv.datacenter.paycenter.dao.implement.flow;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedWriteDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.InitAccountMapper;
import com.ttmv.datacenter.paycenter.dao.implement.util.MapUtil;
import com.ttmv.datacenter.paycenter.data.InitAccount;

/**
 * 资金账户初始化 SV
 * @author Damon
 *
 */
public class InitAccountService extends DistributedWriteDaoTemplate<InitAccount>{
	
	public InitAccountService() {
		super();
	}

	private final Logger logger = LogManager.getLogger(InitAccountService.class);

	private InitAccountMapper initAccountMapper;
	

	@Override
	public void writePreDB(InitAccount data) throws Exception {
		if(data == null || data.getUserId() == null){
			logger.error("DAO处理异常 @@ InitAccountService.writePreDB()参数校验失败!!! parameter is null");
			throw new Exception("OP500");
		}
		Map<String, String> map = null;
		try {
			map = MapUtil.beanToMap(data);
		} catch (Exception e) {
			logger.error("MapUtil.beanToMap(tquanInfo)处理失败!!!",e);
			throw new Exception("OP500");
		}
		initAccountMapper.initAccountRedis(data.getUserId().toString(), map);
		
	}

	@Override
	public void writePostDB(InitAccount data) throws Exception {
		if(data == null){
			logger.error("DAO处理异常 @@ InitAccountMapper.writePreDB()参数校验失败!!! parameter is null");
			throw new Exception("OP500");
		}
		initAccountMapper.initAccountMysql(data);
		
	}

	public InitAccountMapper getInitAccountMapper() {
		return initAccountMapper;
	}

	public void setInitAccountMapper(InitAccountMapper initAccountMapper) {
		this.initAccountMapper = initAccountMapper;
	}

	@Override
	public Class<InitAccount> dataClass() throws Exception {
		// TODO Auto-generated method stub
		return InitAccount.class;
	}



}
