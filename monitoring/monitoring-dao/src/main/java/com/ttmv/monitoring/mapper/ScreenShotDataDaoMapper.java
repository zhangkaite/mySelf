package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

public class ScreenShotDataDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String SCREENSHOTDATA_MAPPER ="com.ttmv.monitoring.inter.ScreenShotDataMapper";
	
	public Integer addScreenShotData(ScreenShotData screenShotData)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.SCREENSHOTDATA_MAPPER
					+ ".insertSelective", screenShotData);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public ScreenShotData queryScreenShotData(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			ScreenShotData data = session.selectOne(
					this.SCREENSHOTDATA_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<ScreenShotData> querySelectedScreenShotData(
			DataBeanQuery screenShotDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ScreenShotData> datas = session.selectList(
					this.SCREENSHOTDATA_MAPPER + ".selectSelected",
					screenShotDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public ScreenShotData queryScreenShotDataByIpAndServerTypeAndPort(
			DataBeanQuery screenShotDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			ScreenShotData data = session.selectOne(this.SCREENSHOTDATA_MAPPER
					+ ".selectByIpAndServerTypeAndPort", screenShotDataQuery);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<ScreenShotData> queryScreenShotDataListByIpAndServerTypeAndPort(
			DataBeanQuery screenShotDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ScreenShotData> datas = session.selectList(
					this.SCREENSHOTDATA_MAPPER + ".selectListByIpAndServerTypeAndPort",
					screenShotDataQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<ScreenShotData> queryScreenShotDataListOnDateByIpAndServerTypeAndPort(
			DataBeanQuery ScreenShotDataQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ScreenShotData> datas = session.selectList(
					this.SCREENSHOTDATA_MAPPER + ".selectListOnDateByIpAndServerTypeAndPort",
					ScreenShotDataQuery);
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
