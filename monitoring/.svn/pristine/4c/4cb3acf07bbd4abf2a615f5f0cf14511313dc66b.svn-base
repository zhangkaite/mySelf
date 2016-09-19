package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.AlertRecordInfo;
import com.ttmv.monitoring.entity.querybean.AlertRecordInfoQuery;

public class AlertRecordInfoDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String ALERTRECORD_MAPPER = "com.ttmv.monitoring.inter.AlertRecordInfoDaoMapper";

	public Integer addAlertRecordInfo(AlertRecordInfo alertRecordInfo)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.ALERTRECORD_MAPPER
					+ ".insertSelective", alertRecordInfo);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public AlertRecordInfo queryAlertRecordInfo(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			AlertRecordInfo data = session.selectOne(this.ALERTRECORD_MAPPER
					+ ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<AlertRecordInfo> queryListAlertRecordInfo(
			AlertRecordInfo alertRecordInfo) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<AlertRecordInfo> datas = session.selectList(
					this.ALERTRECORD_MAPPER + ".selectByConditions",
					alertRecordInfo);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<AlertRecordInfo> queryPageAlertRecordInfo(
			AlertRecordInfoQuery alertRecordInfoQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<AlertRecordInfo> datas = session.selectList(
					this.ALERTRECORD_MAPPER + ".selectByFuzzyConditions",
					alertRecordInfoQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer queryPageAlertRecordInfoSum(
			AlertRecordInfoQuery alertRecordInfoQuery) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer sum = session.selectOne(this.ALERTRECORD_MAPPER
					+ ".selectByFuzzyConditionsAll", alertRecordInfoQuery);
			return sum;
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
