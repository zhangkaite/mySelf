package com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.error.FailAccessError;
import org.springframework.beans.BeanUtils;

import com.ttmv.datacenter.paycenter.dao.implement.mapper.bean.tquan.MysqlTquanInfo;
import com.ttmv.datacenter.paycenter.data.TquanInfo;

public class MysqlTquanInfoMapper {

	private static final String TQUAN_MAPPER = "com.ttmv.datacenter.paycenter.dao.implement.mapper.MysqlTquanInfoMapper";
	private SqlSessionFactory sqlSessionFactory;
	private final Logger log = LogManager.getLogger(MysqlTquanInfoMapper.class);

	/**
	 * 通过id获取MysqlTquanInfo
	 * 
	 * @param id
	 * @return TquanInfo
	 * @throws Exception
	 */
	public TquanInfo getTquanInfoByUserId(BigInteger id) throws Exception {

		MysqlTquanInfo mysqlTquanInfo = null;
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接异常",e);
			throw new FailAccessError(e);
		}
		try {
			mysqlTquanInfo = sqlSession.selectOne(TQUAN_MAPPER + ".selectByPrimaryKey", id);
		} catch (Exception e) {
			log.error("mysql查询异常",e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
		TquanInfo tquanInfo = new TquanInfo();
		try{
			if(mysqlTquanInfo != null){				
				BeanUtils.copyProperties(mysqlTquanInfo, tquanInfo);
				return tquanInfo;
			}
		}catch(Exception e){
			log.error("对象转换异常",e);
			throw new Exception("OP500");
		}
		return null;
	}
	
	/**
	 * 查询所有的TQ账户信息
	 * 
	 * @param id
	 * @return MysqlTquanInfo
	 * @throws Exception
	 */
	public List<MysqlTquanInfo> getAllMysqlTquanInfo() throws Exception {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接异常",e);
			throw new FailAccessError(e);
		}
		try {
			List<MysqlTquanInfo> listTquan = sqlSession.selectList(TQUAN_MAPPER + ".selectAll");
			if (listTquan != null) {
				return listTquan;
			}
		} catch (Exception e) {
			log.error("mysql查询异常",e);
			throw new Exception("OP500");
		} finally {
			if(sqlSession != null){				
				sqlSession.close();
			}
		}
		return null;
	}

	/**
	 * 修改MysqlTquanInfo
	 * 
	 * @param MysqlTquanInfo
	 * @param mapper
	 * @throws Exception
	 * @return int
	 */
	public Integer updateTquanInfo(TquanInfo tquanInfo) throws Exception {
		MysqlTquanInfo mysql = new MysqlTquanInfo();
		try{
			BeanUtils.copyProperties(tquanInfo, mysql);
		}catch(Exception e){
			log.error("对象转换异常",e);
			throw new Exception("OP500");
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			log.error("mysql连接异常");
			throw new FailAccessError(e);
		} 
		try {
			Integer num = sqlSession.update(TQUAN_MAPPER + ".updateByPrimaryKeySelective", mysql);
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
	 * 新增MysqlTquanInfo
	 * 
	 * @param MysqlTquanInfo
	 * @param mapper
	 * @return int
	 */
	public Integer addMysqlTquanInfo(TquanInfo tquanInfo) throws Exception {

		MysqlTquanInfo mysql = new MysqlTquanInfo();
		try{
			BeanUtils.copyProperties(tquanInfo, mysql);
		}catch(Exception e){
			log.error("对象转换异常",e);
			throw new Exception("OP500");
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
		} catch (Exception e) {
			throw new FailAccessError(e);
		} 
		try {
			Integer num = sqlSession.insert(TQUAN_MAPPER + ".insertSelective", mysql);
			return num;
		} catch (Exception e) {
			log.error("mysql数据添加异常",e);
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
