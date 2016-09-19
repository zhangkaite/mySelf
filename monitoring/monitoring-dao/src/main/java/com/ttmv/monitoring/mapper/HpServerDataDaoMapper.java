package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class HpServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String HPSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.HpServerDataDaoMapper";

	public Integer addHpServerData(HpServerData hpServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.HPSERVERDATA_MAPPER + ".insertSelective", hpServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public HpServerData queryHpServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			HpServerData data = session.selectOne(
					this.HPSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<HpServerData> querySelectedHpServerData(DataBeanQuery hpServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<HpServerData> datas = session.selectList(
					this.HPSERVERDATA_MAPPER + ".selectSelected",
					hpServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public HpServerData queryHpServerDataByIpAndServerTypeAndPort(
			DataBeanQuery hpServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			HpServerData data = session.selectOne(this.HPSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", hpServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<HpServerData> queryHpServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery hpServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<HpServerData> datas = session.selectList(
					this.HPSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					hpServerDataQuery);
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
