package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.AlerterInfo;
import com.ttmv.monitoring.entity.querybean.AlerterInfoQuery;

public class AlerterInfoDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String ALERTERINFO_MAPPER = "com.ttmv.monitoring.imp.AlerterInfoMapper";

	public Integer addAlerterInfo(AlerterInfo alerterInfo) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.ALERTERINFO_MAPPER
					+ ".insertSelective", alerterInfo);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer updateAlerterInfo(AlerterInfo alerterInfo)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.update(
					this.ALERTERINFO_MAPPER + ".updateByPrimaryKeySelective",
					alerterInfo);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer deleteAlerterInfo(BigInteger id)throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.delete(
					this.ALERTERINFO_MAPPER + ".deleteByPrimaryKey", id);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public AlerterInfo queryAlerterInfo(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			AlerterInfo data = session.selectOne(
					this.ALERTERINFO_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<AlerterInfo> queryAlerterInfo(AlerterInfoQuery alerterInfoQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<AlerterInfo> datas = session.selectList(
					this.ALERTERINFO_MAPPER + ".selectByConditions",
					alerterInfoQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}		
	}

	public List<AlerterInfo> queryPageAlerterInfo(AlerterInfoQuery alerterInfoQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<AlerterInfo> datas = session.selectList(
					this.ALERTERINFO_MAPPER + ".selectByFuzzyConditions",
					alerterInfoQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}	
	}

	public Integer queryPageAlerterInfoSum(AlerterInfoQuery alerterInfoQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer sum = session.selectOne(
					this.ALERTERINFO_MAPPER + ".selectByFuzzyConditionsAll",
					alerterInfoQuery);
			return sum;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}		
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
}
