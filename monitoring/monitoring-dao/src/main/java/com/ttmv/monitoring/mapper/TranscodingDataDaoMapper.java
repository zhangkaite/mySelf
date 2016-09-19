package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class TranscodingDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String TRANSCODING_MAPPER = "com.ttmv.monitoring.inter.TranscodingDataMapper";

	public Integer addTranscodingData(TranscodingData transcodingData)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.TRANSCODING_MAPPER
					+ ".insertSelective", transcodingData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public TranscodingData queryTranscodingData(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			TranscodingData data = session.selectOne(
					this.TRANSCODING_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<TranscodingData> querySelectedTranscodingData(
			DataBeanQuery transcodingDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<TranscodingData> datas = session.selectList(
					this.TRANSCODING_MAPPER + ".selectSelected",
					transcodingDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public TranscodingData queryTranscodingDataByIpAndServerTypeAndPort(
			DataBeanQuery transcodingDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			TranscodingData data = session.selectOne(this.TRANSCODING_MAPPER
					+ ".selectByIpAndServerTypeAndPort", transcodingDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<TranscodingData> queryTranscodingDataListByIpAndServerTypeAndPort(
			DataBeanQuery transcodingDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<TranscodingData> datas = session.selectList(
					this.TRANSCODING_MAPPER + ".selectListByIpAndServerTypeAndPort",
					transcodingDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<TranscodingData> queryTranscodingDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery transcodingDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<TranscodingData> datas = session.selectList(
					this.TRANSCODING_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					transcodingDataQuery);
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
