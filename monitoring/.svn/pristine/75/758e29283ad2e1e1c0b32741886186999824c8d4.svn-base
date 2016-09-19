package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class MssServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String MSSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.MssServerDataDaoMapper";

	public Integer addMssServerData(MssServerData mssServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.MSSERVERDATA_MAPPER + ".insertSelective", mssServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MssServerData queryMssServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MssServerData data = session.selectOne(
					this.MSSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<MssServerData> querySelectedMssServerData(DataBeanQuery mssServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MssServerData> datas = session.selectList(
					this.MSSERVERDATA_MAPPER + ".selectSelected",
					mssServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MssServerData queryMssServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mssServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MssServerData data = session.selectOne(this.MSSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", mssServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<MssServerData> queryMssServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery mssServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MssServerData> datas = session.selectList(
					this.MSSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					mssServerDataQuery);
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
