package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.PhpManagerServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class PHPManagerServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String PHPMANAGERSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.PhpManagerServerDataDaoMapper";

	public Integer addPhpManagerServerData(PhpManagerServerData phpManagerServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.PHPMANAGERSERVERDATA_MAPPER + ".insertSelective", phpManagerServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public PhpManagerServerData queryPhpManagerServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			PhpManagerServerData data = session.selectOne(
					this.PHPMANAGERSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<PhpManagerServerData> querySelectedPhpManagerServerData(DataBeanQuery phpManagerServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PhpManagerServerData> datas = session.selectList(
					this.PHPMANAGERSERVERDATA_MAPPER + ".selectSelected",
					phpManagerServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public PhpManagerServerData queryPhpManagerServerDataByIpAndServerTypeAndPort(
			DataBeanQuery phpManagerServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			PhpManagerServerData data = session.selectOne(this.PHPMANAGERSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", phpManagerServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<PhpManagerServerData> queryPhpManagerServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery phpManagerServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PhpManagerServerData> datas = session.selectList(
					this.PHPMANAGERSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					phpManagerServerDataQuery);
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
