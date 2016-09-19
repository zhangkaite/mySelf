package com.ttmv.datacenter.paycenter.dao.interfaces;

import java.math.BigInteger;

public interface InitAccountDao {
	/**
	 * 初始化用户的账户信息
	 * @param userid
	 * @throws Exception
	 */
	public void initAccount(BigInteger userid ,String reqID) throws Exception; 
}
