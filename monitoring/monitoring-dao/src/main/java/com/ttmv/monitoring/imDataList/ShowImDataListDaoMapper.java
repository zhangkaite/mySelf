package com.ttmv.monitoring.imDataList;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


public class ShowImDataListDaoMapper {
	private SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	private final String IMDATALIST_MAPPER = "com.ttmv.monitoring.imDataList.ShowImDataListDaoMapper";
	
	public List<ImServiceDataEntity> showImMtsDataList()throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ImServiceDataEntity> datas = session.selectList(
					this.IMDATALIST_MAPPER + ".selectMts", null);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<ImServiceDataEntity> showImMdsDataList()throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ImServiceDataEntity> datas = session.selectList(
					this.IMDATALIST_MAPPER + ".selectMds", null);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	
	public List<ImServiceDataEntity> showImMssDataList()throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ImServiceDataEntity> datas = session.selectList(
					this.IMDATALIST_MAPPER + ".selectMss", null);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	
	public List<ImServiceDataEntity> showImRmsDataList()throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ImServiceDataEntity> datas = session.selectList(
					this.IMDATALIST_MAPPER + ".selectRms", null);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	
	
	public List<ImServiceDataEntity> showImPrsDataList()throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ImServiceDataEntity> datas = session.selectList(
					this.IMDATALIST_MAPPER + ".selectPrs", null);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	
	public List<ImServiceDataEntity> showImLbsDataList()throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ImServiceDataEntity> datas = session.selectList(
					this.IMDATALIST_MAPPER + ".selectLbs", null);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<ImServiceDataEntity> showImUmsDataList()throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ImServiceDataEntity> datas = session.selectList(
					this.IMDATALIST_MAPPER + ".selectUms", null);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<ImServiceDataEntity> showImTasDataList()throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ImServiceDataEntity> datas = session.selectList(
					this.IMDATALIST_MAPPER + ".selectTas", null);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	
}
