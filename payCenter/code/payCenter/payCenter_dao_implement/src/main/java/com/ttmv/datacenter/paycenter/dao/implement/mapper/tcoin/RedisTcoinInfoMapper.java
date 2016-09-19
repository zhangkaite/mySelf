package com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.error.FailAccessError;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.paycenter.dao.implement.constant.AccountConstant;
import com.ttmv.datacenter.paycenter.dao.implement.constant.LuaScript;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;

public class RedisTcoinInfoMapper {

	private final Logger log = LogManager.getLogger(RedisTcoinInfoMapper.class);
	private static final String TCOIN = "TCOIN_";

	private RedisAgent jedisAgent;
	
	/**
	 * 添加reids的TcoinInfo
	 * 
	 * @param userid
	 * @param jsonData
	 * @throws Exception
	 */
	public void addRedisTcoinInfo(String userid, Map<String, String> hash)throws Exception{
		try {
			jedisAgent.setMulipleHashKeyFields(TCOIN + userid, hash);
		} catch (Exception e) {
			throw new FailAccessError(e);
		}
	}

	/**
	 * TB充值
	 * 
	 * @param number
	 * @throws Exception
	 */
	public Integer incrBalance(String userId, String number) throws Exception {
		/* 设置Lua调用redis的key */
		List<String> keys = new ArrayList<String>();
		keys.add(TCOIN + userId);
		keys.add(AccountConstant.BALANCE);
		/* 设置调用Lua脚本调用的值 */
		List<String> argvs = new ArrayList<String>();
		argvs.add(number);
		Long result = null;
		try {
			result = (Long) jedisAgent.evalLua(LuaScript.INCR, keys, argvs);
		} catch (Exception e) {
			log.error("支付中心redis异常，异常信息：",e);
			throw new FailAccessError(e);
		}
		return  new Integer(result.toString());
	}

	/**
	 * TB消费
	 * 
	 * @param number
	 * @throws Exception
	 */
	public Integer decrBalance(String userId, String number) throws Exception {
		/* 设置Lua调用redis的key */
		List<String> keys = new ArrayList<String>();
		keys.add(TCOIN + userId);
		keys.add(AccountConstant.BALANCE);
		/* 设置调用Lua脚本调用的值 */
		List<String> argvs = new ArrayList<String>();
		argvs.add(number);
		Long result = null;
		try {
			result = (Long) jedisAgent.evalLua(LuaScript.DECR, keys, argvs);
		} catch (Exception e) {
			log.error("支付中心redis异常，异常信息：",e);
			throw new FailAccessError(e);
		}
		return new Integer(result.toString());
	}

	/**
	 * 冻结余额增加
	 * 
	 * @param number
	 * @throws Exception
	 */
	public Integer incrFreezeBalance(String userId, String number) throws Exception {
		/* 设置Lua调用redis的key */
		List<String> keys = new ArrayList<String>();
		keys.add(TCOIN + userId);
		keys.add(AccountConstant.BALANCE);
		keys.add(AccountConstant.FREEZEBALANCE);
		/* 设置调用Lua脚本调用的值 */
		List<String> argvs = new ArrayList<String>();
		argvs.add(number);
		Long result = null;
		try {
			result = (Long) jedisAgent.evalLua(LuaScript.INCR_FREEZE, keys, argvs);
		} catch (Exception e) {
			log.error("支付中心redis异常，异常信息：",e);
			throw new FailAccessError(e);
		}
		return new Integer(result.toString());
	}

	/**
	 * 解冻冻结余额
	 * 
	 * @param number
	 * @throws Exception
	 */
	public Integer decrFreezeBalance(String userId, String number) throws Exception {
		/* 设置Lua调用redis的key */
		List<String> keys = new ArrayList<String>();
		keys.add(TCOIN + userId);
		keys.add(AccountConstant.BALANCE);
		keys.add(AccountConstant.FREEZEBALANCE);
		/* 设置调用Lua脚本调用的值 */
		List<String> argvs = new ArrayList<String>();
		argvs.add(number);
		Long result = null;
		try {
			result = (Long) jedisAgent.evalLua(LuaScript.DECR_FREEZE, keys, argvs);
		} catch (Exception e) {
			log.error("支付中心redis异常，异常信息：",e);
			throw new FailAccessError(e);
		}
		return new Integer(result.toString());
	}

	/**
	 * 根据userid 查询TcoinInfo对象
	 * 
	 * @param userid
	 * @return
	 */
	public TcoinInfo getTcoinInfoByUserId(String userid) throws Exception { 
		Map<String, String> tcoinInfoMap = null;
		try{			
			tcoinInfoMap = jedisAgent.getHashKeyAllField(TCOIN + userid);
		}catch(Exception e){
			throw new FailAccessError(e);
		}
		if (tcoinInfoMap == null) {
			return null;
		}
		return getMapToTcoinInfo(tcoinInfoMap);
	}

	/**
	 * 获取hash的数据，转换为对象
	 * 
	 * @return
	 */
	private TcoinInfo getMapToTcoinInfo(Map<String, String> hash) {
		if (hash == null) {
			return null;
		}
		TcoinInfo info = new TcoinInfo();
		if (hash.containsKey(AccountConstant.ID)) {
			info.setId(new BigInteger(hash.get(AccountConstant.ID)));
		}
		if (hash.containsKey(AccountConstant.USERID)) {
			info.setUserId(new BigInteger(hash.get(AccountConstant.USERID)));
		}
		if (hash.containsKey(AccountConstant.BALANCE)) {
			info.setBalance(new BigDecimal(hash.get(AccountConstant.BALANCE)));
		}
		if (hash.containsKey(AccountConstant.FREEZEBALANCE)) {
			info.setFreezeBalance(new BigDecimal(hash.get(AccountConstant.FREEZEBALANCE)));
		}
		if (hash.containsKey(AccountConstant.ACCOUNTSTATUS)) {
			info.setAccountStatus(new Integer(hash.get(AccountConstant.ACCOUNTSTATUS)));
		}
		return info;
	}

	public RedisAgent getJedisAgent() {
		return jedisAgent;
	}

	public void setJedisAgent(RedisAgent jedisAgent) {
		this.jedisAgent = jedisAgent;
	}
}
