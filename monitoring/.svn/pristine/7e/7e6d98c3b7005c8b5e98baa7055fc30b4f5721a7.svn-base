package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class PrsServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String PRSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.PrsServerDataDaoMapper";

	public Integer addPrsServerData(PrsServerData prsServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.PRSERVERDATA_MAPPER + ".insertSelective", prsServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public PrsServerData queryPrsServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			PrsServerData data = session.selectOne(
					this.PRSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<PrsServerData> querySelectedPrsServerData(DataBeanQuery prsServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PrsServerData> datas = session.selectList(
					this.PRSERVERDATA_MAPPER + ".selectSelected",
					prsServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public PrsServerData queryPrsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery prsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			PrsServerData data = session.selectOne(this.PRSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", prsServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<PrsServerData> queryPrsServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery prsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PrsServerData> datas = session.selectList(
					this.PRSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					prsServerDataQuery);
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
