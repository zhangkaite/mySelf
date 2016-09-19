package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.UmsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class UmsServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String UMSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.UmsServerDataDaoMapper";

	public Integer addUmsServerData(UmsServerData umsServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.UMSERVERDATA_MAPPER + ".insertSelective", umsServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public UmsServerData queryUmsServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			UmsServerData data = session.selectOne(
					this.UMSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<UmsServerData> querySelectedUmsServerData(DataBeanQuery umsServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<UmsServerData> datas = session.selectList(
					this.UMSERVERDATA_MAPPER + ".selectSelected",
					umsServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public UmsServerData queryUmsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery umsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			UmsServerData data = session.selectOne(this.UMSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", umsServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<UmsServerData> queryUmsServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery umsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<UmsServerData> datas = session.selectList(
					this.UMSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					umsServerDataQuery);
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
