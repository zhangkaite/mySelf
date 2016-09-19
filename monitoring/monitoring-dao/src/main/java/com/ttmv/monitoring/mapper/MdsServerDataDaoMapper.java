package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class MdsServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String MDSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.MdsServerDataDaoMapper";

	public Integer addMdsServerData(MdsServerData mdsServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.MDSERVERDATA_MAPPER + ".insertSelective", mdsServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MdsServerData queryMdsServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MdsServerData data = session.selectOne(
					this.MDSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<MdsServerData> querySelectedMdsServerData(DataBeanQuery mdsServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MdsServerData> datas = session.selectList(
					this.MDSERVERDATA_MAPPER + ".selectSelected",
					mdsServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MdsServerData queryMdsServerDataByIpAndServerTypeAndPort(
			DataBeanQuery mdsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MdsServerData data = session.selectOne(this.MDSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", mdsServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<MdsServerData> queryMdsServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery MdsServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MdsServerData> datas = session.selectList(
					this.MDSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					MdsServerDataQuery);
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
