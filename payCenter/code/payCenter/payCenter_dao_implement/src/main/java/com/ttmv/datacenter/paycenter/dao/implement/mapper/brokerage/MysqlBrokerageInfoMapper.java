package com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.error.FailAccessError;
import org.springframework.beans.BeanUtils;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.brokerage.MysqlBrokerageInfo;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;

public class MysqlBrokerageInfoMapper {

	private static final String BROKERAGE_MAPPER = "com.ttmv.datacenter.paycenter.dao.implement.mapper.MysqlBrokerageInfoMapper";
	private SqlSessionFactory sqlSessionFactory;
	private final Logger log = LogManager.getLogger(MysqlBrokerageInfoMapper.class);

	/**
	 * 通过id获取BrokerageInfo
	 * 
	 * @param id
	 * @return BrokerageInfo
	 * @throws Exception
	 */
	public BrokerageInfo getBrokerageInfoByUserId(BigInteger userid) throws Exception {
		SqlSession sqlSession = null;
		MysqlBrokerageInfo mysqlBrokerageInfo = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接失败!!!",e);
			throw new FailAccessError(e);
		}
		try {
			mysqlBrokerageInfo = sqlSession.selectOne(BROKERAGE_MAPPER + ".selectByPrimaryKey", userid);
		} catch (Exception e) {
			log.error("mysql查询异常!!!",e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
		BrokerageInfo brokerageInfo = new BrokerageInfo();
		try{
			if(mysqlBrokerageInfo != null){				
				BeanUtils.copyProperties(mysqlBrokerageInfo, brokerageInfo);
				return brokerageInfo;
			}
		}catch(Exception e){
			log.error("对象转换异常!!!" , e);
			throw new Exception("OP500");
		}
		return null;
	}

	/**
	 * 查询所有的佣金账户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MysqlBrokerageInfo> getAllMysqlBrokerageInfo() throws Exception {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接失败!!!",e);
			throw new FailAccessError(e);
		}
		try {
			List<MysqlBrokerageInfo> listBrokerage = sqlSession.selectList(BROKERAGE_MAPPER + ".selectAll");
			if (listBrokerage != null) {
				return listBrokerage;
			}
		} catch (Exception e) {
			log.error("mysql查询异常!!!" , e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
		return null;
	}

	/**
	 * 修改MysqlBrokerageInfo
	 * 
	 * @param MysqlBrokerageInfo
	 * @param mapper
	 * @throws Exception
	 * @return int
	 */
	public Integer updateBrokerageInfo(BrokerageInfo brokerageInfo) throws Exception {
		MysqlBrokerageInfo mysql = new MysqlBrokerageInfo();
		try{
			BeanUtils.copyProperties(brokerageInfo, mysql);
		}catch(Exception e){
			log.error("对象转换异常!!!" , e);
			throw new Exception("OP500");
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接异常",e);
			throw new FailAccessError(e);
		}
		
		try {
			Integer num = sqlSession.update(BROKERAGE_MAPPER + ".updateByPrimaryKeySelective", mysql);
			return num;
		} catch (Exception e) {
			log.error("mysql数据更新异常",e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
	}

	/**
	 * 新增MysqlBrokerageInfo
	 * 
	 * @param MysqlBrokerageInfo
	 * @param mapper
	 * @return int
	 */
	public Integer addBrokerageInfo(BrokerageInfo brokerageInfo) throws Exception {
		MysqlBrokerageInfo mysql = new MysqlBrokerageInfo();
		try{
			BeanUtils.copyProperties(brokerageInfo, mysql);
		}catch(Exception e){
			log.error("对象转换失败",e);
			throw new Exception("OP500");
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接异常", e);
			throw new FailAccessError(e);
		}
		try {
			Integer num = sqlSession.insert(BROKERAGE_MAPPER + ".insertSelective", mysql);
			return num;
		} catch (Exception e) {
			log.error("mysql数据添加异常", e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
}
