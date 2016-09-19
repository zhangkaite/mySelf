package com.ttmv.datacenter.paycenter.dao.interfaces;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface AccountInfoDao {
	
	/**
	 * 查询账户的余额
	 * @param userid
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> selectAccount(BigInteger userid,List<Integer> types)throws Exception;
	
	/**
	 * 将mysql中所有的账户写入redis
	 * @throws Exception
	 */
	public void getAllMysqlAccountToRedis()throws Exception;
}
