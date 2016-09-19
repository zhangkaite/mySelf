package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class LbsServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String LBSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.LbsServerDataDaoMapper";

	public Integer addLbsServerData(LbsServerData lbsServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.LBSERVERDATA_MAPPER + ".insertSelective", lbsServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public LbsServerData queryLbsServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			LbsServerData data = session.selectOne(
					this.LBSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<LbsServerData> querySelectedLbsServerData(DataBeanQuery lbsServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<LbsServerData> datas = session.selectList(
					this.LBSERVERDATA_MAPPER + ".selectSelected",
					lbsServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public LbsServerData queryLbsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery lbsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			LbsServerData data = session.selectOne(this.LBSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", lbsServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<LbsServerData> queryLbsServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery LbsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<LbsServerData> datas = session.selectList(
					this.LBSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					LbsServerDataQuery);
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
