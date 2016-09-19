package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class MediaControlDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String MEDIACONTROLDATA_MAPPER = "com.ttmv.monitoring.inter.MediaControlDataMapper";

	public Integer addMediaControlData(MediaControlData mediaControlData)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.MEDIACONTROLDATA_MAPPER
					+ ".insertSelective", mediaControlData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public MediaControlData queryMediaControlData(BigInteger id)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MediaControlData data = session.selectOne(
					this.MEDIACONTROLDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public MediaControlData queryMediaControlDataByIpAndServerTypeAndPort(
			DataBeanQuery mediaControlDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			MediaControlData data = session.selectOne(
					this.MEDIACONTROLDATA_MAPPER + ".selectByIpAndServerTypeAndPort",
					mediaControlDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	
	
	public List<MediaControlData> querySelectedMediaControlData(DataBeanQuery mediaControlDataQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MediaControlData> datas = session.selectList(
					this.MEDIACONTROLDATA_MAPPER + ".selectSelected",
					mediaControlDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<MediaControlData> queryMediaControlDataListByIpAndServerTypeAndPort(
			DataBeanQuery mediaControlDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MediaControlData> datas = session.selectList(
					this.MEDIACONTROLDATA_MAPPER + ".selectListByIpAndServerTypeAndPort",
					mediaControlDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<MediaControlData> queryMediaControlDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery mediaControlDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<MediaControlData> datas = session.selectList(
					this.MEDIACONTROLDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					mediaControlDataQuery);
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