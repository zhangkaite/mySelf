package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class MtsServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String MTSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.MtsServerDataDaoMapper";

	public Integer addMtsServerData(MtsServerData mtsServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.MTSERVERDATA_MAPPER + ".insertSelective", mtsServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MtsServerData queryMtsServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MtsServerData data = session.selectOne(
					this.MTSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<MtsServerData> querySelectedMtsServerData(DataBeanQuery mtsServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MtsServerData> datas = session.selectList(
					this.MTSERVERDATA_MAPPER + ".selectSelected",
					mtsServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MtsServerData queryMtsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mtsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MtsServerData data = session.selectOne(this.MTSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", mtsServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<MtsServerData> queryMtsServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery mtsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MtsServerData> datas = session.selectList(
					this.MTSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					mtsServerDataQuery);
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
