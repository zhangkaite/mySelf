package com.ttmv.datacenter.paycenter.dao.implement.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.error.FailAccessError;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.ttmv.datacenter.agent.redis.RedisAgent;
import com.ttmv.datacenter.paycenter.dao.implement.constant.LuaScript;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.brokerage.MysqlBrokerageInfo;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.tcoin.MysqlTcoinInfo;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.tquan.MysqlTquanInfo;
import com.ttmv.datacenter.paycenter.data.InitAccount;

/**
 * 资金账户初始化MP
 * @author Damon
 * 需要增加数据库事务，要么整体成功，要么整体失败 
 */
public class InitAccountMapper {
	
	private final Logger logger = LogManager.getLogger(InitAccountMapper.class);
	
	private final String TCOIN = "TCOIN_";
	private final String TQUAN = "TQUAN_";
	private final String BROKERAGE = "BROKERAGE_";
	
	private SqlSessionFactory sqlSessionFactory;
	private RedisAgent jedisAgent;

	/**
	 * 保证三个账户同时成功
	 * @param userid
	 * @param map
	 * @throws Exception 
	 */
	public void initAccountRedis(String userid, Map<String, String> map) throws Exception{
		logger.info("开始初始化reids资金账户");
		try {
			List<String> keys = this.getKeys(userid);
			List<String> values = this.getMuliFields(map);
			jedisAgent.evalLua(LuaScript.INIT_ACCOUNT, keys, values);
			logger.debug("redis账户初始化成功");
		} catch (Exception e) {
			logger.error("redis连接获取失败");
			throw new FailAccessError(e);
		}
	}
	
	/**
	 * 
	 * @param initAccount
	 */
	@Transactional
	public void initAccountMysql(InitAccount initAccount) throws Exception{
		logger.info("开始初始化mysql资金账户");
		MysqlTcoinInfo mysqlbeanTB = new MysqlTcoinInfo();
		MysqlTquanInfo mysqlbeanTQ = new MysqlTquanInfo();
		MysqlBrokerageInfo mysqlbeanYJ = new MysqlBrokerageInfo();
		try{
			BeanUtils.copyProperties(initAccount, mysqlbeanTB);
			BeanUtils.copyProperties(initAccount, mysqlbeanTQ);
			BeanUtils.copyProperties(initAccount, mysqlbeanYJ);
		}catch(Exception e){
			logger.error("对象转换失败",e);
			throw new Exception("OP500");
		}
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession(false);// 打开会话，开启手动事务
		} catch (Exception e) {
			logger.error("mysql连接异常",e);
			throw new FailAccessError(e);
		} 
		try {
			session.insert("com.ttmv.datacenter.paycenter.dao.implement.mapper.MysqlTcoinInfoMapper" + ".insertSelective", mysqlbeanTB);
			session.insert("com.ttmv.datacenter.paycenter.dao.implement.mapper.MysqlTquanInfoMapper" + ".insertSelective", mysqlbeanTQ);
			session.insert("com.ttmv.datacenter.paycenter.dao.implement.mapper.MysqlBrokerageInfoMapper" + ".insertSelective", mysqlbeanYJ);
			session.commit();
		} catch (Exception e) {
			logger.error("mysql操作失败，事务回滚,抛出错误!!!",e);
			session.rollback();
			throw new Exception("OP500");
		}finally {  
			session.close(); // 关闭会话，释放资源  
        }  
		
	}

	/**
	 * 将map数据类型转换成指定的List数据结构类型
	 * @param map
	 * @return
	 */
	private List<String> getMuliFields(Map<String,String> map){
		List<String> list = new ArrayList<String>();
		Set<String> sets = map.keySet();
		for(String key :sets){
			String value = map.get(key).toString();
			list.add(key);
			list.add(value);
		}
		return list;
	}
	
	/**
	 * 组装keys
	 * @param userid
	 * @return
	 */
	private List<String> getKeys(String userid){
		List<String> keys = new ArrayList<String>();
		keys.add(TCOIN+userid);
		keys.add(TQUAN+userid);
		keys.add(BROKERAGE+userid);
		return keys ;
	}
	
	public SqlSessionFactory getSqlSessionFactory()  throws Exception{
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setJedisAgent(RedisAgent jedisAgent) {
		this.jedisAgent = jedisAgent;
	}
	
}
