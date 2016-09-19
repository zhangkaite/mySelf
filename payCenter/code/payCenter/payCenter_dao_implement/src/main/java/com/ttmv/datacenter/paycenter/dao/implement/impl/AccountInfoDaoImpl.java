package com.ttmv.datacenter.paycenter.dao.implement.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.paycenter.dao.implement.constant.AccountConstant;
import com.ttmv.datacenter.paycenter.dao.implement.util.TableIdGenerate;
import com.ttmv.datacenter.paycenter.dao.interfaces.AccountInfoDao;
import com.ttmv.datacenter.paycenter.dao.interfaces.BrokerageInfoDao;
import com.ttmv.datacenter.paycenter.dao.interfaces.TcoinInfoDao;
import com.ttmv.datacenter.paycenter.dao.interfaces.TquanInfoDao;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;
import com.ttmv.datacenter.paycenter.data.TquanInfo;

public class AccountInfoDaoImpl implements AccountInfoDao {

	private final Logger log = LogManager.getLogger(AccountInfoDaoImpl.class);

	private BrokerageInfoDao brokerageInfoDao;
	private TcoinInfoDao tcoinInfoDao;
	private TquanInfoDao tquanInfoDao;
	private TableIdGenerate tableIdGenerate;


	/**
	 * 根据用户的userid查询用户三个账户的信息
	 * 
	 * @param userid
	 */
	public Map<String, Object> selectAccount(BigInteger userid,
			List<Integer> types) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (types != null && types.size() > 0) {
				for (Integer value : types) {
					if (value.equals(AccountConstant.TCOIN)) {// TCOIN
						TcoinInfo tcoin = tcoinInfoDao.selectTcoinInfo(userid);
						map.put(AccountConstant.TCOIN_KEY, tcoin);
					} else if (value.equals(AccountConstant.TQUAN)) {// TQUAN
						TquanInfo tquan = tquanInfoDao.selectTquanInfo(userid);
						map.put(AccountConstant.TQUAN_KEY, tquan);
					} else if (value.equals(AccountConstant.BROKERAGE)) {// 佣金
						BrokerageInfo brokerage = brokerageInfoDao
								.selectBrokerageInfo(userid);
						map.put(AccountConstant.BROKERAGE_KEY, brokerage);
					}
				}
				return map;
			}
		} catch (Exception e) {
			log.error("查询用户的账户余额失败！失败的原因："  ,e);
			throw new Exception("查询用户的账户余额失败！失败的原因："  ,e);
		}
		return null;
	}

	/**
	 * 将mysql中所有的账户写入redis
	 * @throws Exception
	 */
	public void getAllMysqlAccountToRedis()throws Exception{
		/* 获取mysql中三个账户的所有信息，存放入redis中 */
	}
	public BrokerageInfoDao getBrokerageInfoDao() {
		return brokerageInfoDao;
	}

	public void setBrokerageInfoDao(BrokerageInfoDao brokerageInfoDao) {
		this.brokerageInfoDao = brokerageInfoDao;
	}

	public TcoinInfoDao getTcoinInfoDao() {
		return tcoinInfoDao;
	}

	public void setTcoinInfoDao(TcoinInfoDao tcoinInfoDao) {
		this.tcoinInfoDao = tcoinInfoDao;
	}

	public TquanInfoDao getTquanInfoDao() {
		return tquanInfoDao;
	}

	public void setTquanInfoDao(TquanInfoDao tquanInfoDao) {
		this.tquanInfoDao = tquanInfoDao;
	}

	public TableIdGenerate getTableIdGenerate() {
		return tableIdGenerate;
	}

	public void setTableIdGenerate(TableIdGenerate tableIdGenerate) {
		this.tableIdGenerate = tableIdGenerate;
	}
}
