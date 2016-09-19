package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class PHPServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String PHPSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.PHPServerDataMapper";

	public Integer addPHPServerData(PHPServerData pHPServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.PHPSERVERDATA_MAPPER + ".insertSelective", pHPServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public PHPServerData queryPHPServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			PHPServerData data = session.selectOne(
					this.PHPSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<PHPServerData> querySelectedPHPServerData(DataBeanQuery pHPServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PHPServerData> datas = session.selectList(
					this.PHPSERVERDATA_MAPPER + ".selectSelected",
					pHPServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public PHPServerData queryPhpServerDataByIpAndServerTypeAndPort(
			DataBeanQuery pHPServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			PHPServerData data = session.selectOne(this.PHPSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", pHPServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<PHPServerData> queryPHPServerDataListByIpAndServerTypeAndPort(
			DataBeanQuery pHPServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PHPServerData> datas = session.selectList(
					this.PHPSERVERDATA_MAPPER + ".selectListByIpAndServerTypeAndPort",
					pHPServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<PHPServerData> queryPHPServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery pHPServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PHPServerData> datas = session.selectList(
					this.PHPSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					pHPServerDataQuery);
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
