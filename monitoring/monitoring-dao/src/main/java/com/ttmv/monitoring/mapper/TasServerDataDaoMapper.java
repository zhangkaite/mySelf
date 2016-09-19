package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class TasServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String TASERVERDATA_MAPPER = "com.ttmv.monitoring.inter.TasServerDataDaoMapper";

	public Integer addTasServerData(TasServerData tasServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.TASERVERDATA_MAPPER + ".insertSelective", tasServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public TasServerData queryTasServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			TasServerData data = session.selectOne(
					this.TASERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<TasServerData> querySelectedTasServerData(DataBeanQuery tasServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<TasServerData> datas = session.selectList(
					this.TASERVERDATA_MAPPER + ".selectSelected",
					tasServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public TasServerData queryTasServerDataByIpAndServerTypeAndPort(
			DataBeanQuery tasServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			TasServerData data = session.selectOne(this.TASERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", tasServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<TasServerData> queryTasServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery tasServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<TasServerData> datas = session.selectList(
					this.TASERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					tasServerDataQuery);
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
