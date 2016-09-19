package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class MediaForwardDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String MEDIAFORWARDDATA_MAPPER = "com.ttmv.monitoring.inter.MediaForwardDataMapper";

	public Integer addMediaForwardData(MediaForwardData mediaForwardData)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.MEDIAFORWARDDATA_MAPPER
					+ ".insertSelective", mediaForwardData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer deleteMediaForwardData(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.delete(this.MEDIAFORWARDDATA_MAPPER
					+ ".deleteByPrimaryKey", id);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MediaForwardData queryMediaForwardData(BigInteger id)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MediaForwardData data = session.selectOne(
					this.MEDIAFORWARDDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MediaForwardData queryMediaForwardDataByIpAndServerTypeAndPort(
			DataBeanQuery mediaForwardDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MediaForwardData data = session.selectOne(
					this.MEDIAFORWARDDATA_MAPPER + ".selectByIpAndServerTypeAndPort",
					mediaForwardDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<MediaForwardData> queryMediaForwardDataListByIpAndServerTypeAndPort(
			DataBeanQuery mediaForwardDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MediaForwardData> datas = session.selectList(
					this.MEDIAFORWARDDATA_MAPPER + ".selectListByIpAndServerTypeAndPort",
					mediaForwardDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<MediaForwardData> queryMediaForwardDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery mediaForwardDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MediaForwardData> datas = session.selectList(
					this.MEDIAFORWARDDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					mediaForwardDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<MediaForwardData> querySelectedMediaForwardData(DataBeanQuery mediaForwardDataQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MediaForwardData> datas = session.selectList(
					this.MEDIAFORWARDDATA_MAPPER + ".selectSelected",
					mediaForwardDataQuery);
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
