package com.ttmv.datacenter.paycenter.worker.service;
import java.math.BigInteger;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.paycenter.dao.interfaces.InitAccountDao;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年3月4日 下午5:45:37  
 * @explain :资金账户初始化
 */
public class AddUserAccountImpl {
	private static Logger logger = LogManager.getLogger(AddUserAccountImpl.class);
//	private AccountInfoDao accountInfoDao;
//	public AccountInfoDao getAccountInfoDao() {
//		return accountInfoDao;
//	}
//	public void setAccountInfoDao(AccountInfoDao accountInfoDao) {
//		this.accountInfoDao = accountInfoDao;
//	}
	//2016年5月10日10:55:01  Damon 初始化账户改造（增加事务）
	private InitAccountDao initAccountDao;
	public InitAccountDao getInitAccountDao() {
		return initAccountDao;
	}

	public void setInitAccountDao(InitAccountDao initAccountDao) {
		this.initAccountDao = initAccountDao;
	}


	protected void execute(Map msgMap) throws Exception {
		initAccountDao.initAccount(new BigInteger(msgMap.get("userID")+""),(String)msgMap.get("reqId"));
	}

}
