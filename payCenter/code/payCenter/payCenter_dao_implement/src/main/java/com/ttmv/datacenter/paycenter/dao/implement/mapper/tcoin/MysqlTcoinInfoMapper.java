package com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.error.FailAccessError;
import org.springframework.beans.BeanUtils;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.tcoin.MysqlTcoinInfo;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;

public class MysqlTcoinInfoMapper {

	private static final String TCOIN_MAPPER = "com.ttmv.datacenter.paycenter.dao.implement.mapper.MysqlTcoinInfoMapper";
	private SqlSessionFactory sqlSessionFactory;
	private final Logger log = LogManager.getLogger(MysqlTcoinInfoMapper.class);
	
	/**
	 * 通过userid获取TcoinInfo
	 * 
	 * @param id
	 * @return MysqlTcoinInfo
	 * @throws Exception
	 * 
	 */
	public TcoinInfo getTcoinInfoByUserId(BigInteger userid) throws Exception  {
		SqlSession sqlSession = null;
		MysqlTcoinInfo mysqlTcoinInfo = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接异常",e);
			throw new FailAccessError(e);
		} 
		
		try {
			mysqlTcoinInfo = sqlSession.selectOne(TCOIN_MAPPER + ".selectByPrimaryKey", userid);
		} catch (Exception e) {
			log.error("数据库查询失败" , e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
		TcoinInfo tcoinInfo = new TcoinInfo();
		try{
			if(mysqlTcoinInfo != null){				
				BeanUtils.copyProperties(mysqlTcoinInfo, tcoinInfo);
				return tcoinInfo;
			}
		}catch(Exception e){
			log.error("对象转换异常");
			throw new Exception("OP500");
		}
		return null;
	}
	
	/**
	 * 查询所有TB账户的信息
	 * @return
	 * @throws Exception
	 */
	public List<MysqlTcoinInfo> getAllMysqlTcoinInfo() throws Exception {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接异常",e);
			/* 获取连接出错，抛出库连接指定异常 */
			throw new FailAccessError(e);
		}
		try {
			List<MysqlTcoinInfo> listTcoinInfo = sqlSession.selectList(TCOIN_MAPPER + ".selectAll");
			if (listTcoinInfo != null) {
				return listTcoinInfo;
			}
		} catch (Exception e) {
			log.error("mysql查询失败",e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
		return null;
	}

	/**
	 * 修改TcoinInfo
	 * 
	 * @param TcoinInfo
	 * @param mapper
	 * @throws Exception
	 * @return int
	 */
	public Integer updateTcoinInfo(TcoinInfo tcoinInfo) throws Exception {
		MysqlTcoinInfo mysql = new MysqlTcoinInfo();
		try{
			BeanUtils.copyProperties(tcoinInfo, mysql);
		}catch(Exception e){
			log.error("对象转换异常",e);
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
			Integer num = sqlSession.update(TCOIN_MAPPER + ".updateByPrimaryKeySelective", mysql);
			return num;
		} catch (Exception e) {
			log.error("mysql数据更新失败",e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
	}

	/**
	 * 新增TcoinInfo
	 * 
	 * @param MysqlTcoinInfo
	 * @param mapper
	 * @return int
	 */
	public Integer addTcoinInfo(TcoinInfo tcoinInfo) throws Exception {
		MysqlTcoinInfo mysql = new MysqlTcoinInfo();
		try{
			BeanUtils.copyProperties(tcoinInfo, mysql);
		}catch(Exception e){
			log.error("对象转换失败",e);
			throw new Exception("OP500");
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			throw new FailAccessError(e);
		}
		try {
			Integer num = sqlSession.insert(TCOIN_MAPPER + ".insertSelective", mysql);
			return num;
		} catch (Exception e) {
			log.error("mysql数据添加失败",e);
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
