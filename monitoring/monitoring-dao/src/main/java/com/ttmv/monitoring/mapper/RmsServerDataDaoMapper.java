package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class RmsServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String RMSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.RmsServerDataDaoMapper";

	public Integer addRmsServerData(RmsServerData rmsServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.RMSERVERDATA_MAPPER + ".insertSelective", rmsServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public RmsServerData queryRmsServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			RmsServerData data = session.selectOne(
					this.RMSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<RmsServerData> querySelectedRmsServerData(DataBeanQuery rmsServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<RmsServerData> datas = session.selectList(
					this.RMSERVERDATA_MAPPER + ".selectSelected",
					rmsServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public RmsServerData queryRmsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery rmsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			RmsServerData data = session.selectOne(this.RMSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", rmsServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<RmsServerData> queryRmsServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery rmsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<RmsServerData> datas = session.selectList(
					this.RMSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					rmsServerDataQuery);
			return datas;
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
