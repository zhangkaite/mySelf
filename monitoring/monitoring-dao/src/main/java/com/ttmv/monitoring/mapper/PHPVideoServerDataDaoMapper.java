package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class PHPVideoServerDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String PHPVIDEOSERVERDATA_MAPPER = "com.ttmv.monitoring.inter.PhpVideoServerDataDaoMapper";

	public Integer addPhpVideoServerData(PhpVideoServerData phpVideoServerData)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(
					this.PHPVIDEOSERVERDATA_MAPPER + ".insertSelective", phpVideoServerData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public PhpVideoServerData queryPhpVideoServerData(BigInteger id)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			PhpVideoServerData data = session.selectOne(
					this.PHPVIDEOSERVERDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<PhpVideoServerData> querySelectedPhpVideoServerData(DataBeanQuery phpVideoServerDataQuery) throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PhpVideoServerData> datas = session.selectList(
					this.PHPVIDEOSERVERDATA_MAPPER + ".selectSelected",
					phpVideoServerDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public PhpVideoServerData queryPhpVideoServerDataByIpAndServerTypeAndPort(
			DataBeanQuery phpVideoServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			PhpVideoServerData data = session.selectOne(this.PHPVIDEOSERVERDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", phpVideoServerDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<PhpVideoServerData> queryPhpVideoServerDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery phpVideoServerDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<PhpVideoServerData> datas = session.selectList(
					this.PHPVIDEOSERVERDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					phpVideoServerDataQuery);
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
