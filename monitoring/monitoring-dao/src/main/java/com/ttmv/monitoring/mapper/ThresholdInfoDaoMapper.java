package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.ThresholdInfo;

public class ThresholdInfoDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String THRESHOLDINFO_MAPPER = "com.ttmv.monitoring.imp.ThresholdInfoMapper";

	public ThresholdInfo queryThresholdInfo(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			ThresholdInfo result = session.selectOne(this.THRESHOLDINFO_MAPPER
					+ ".selectByPrimaryKey", id);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public ThresholdInfo queryThresholdInfoByTypeAndName(ThresholdInfo thresholdInfo) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			ThresholdInfo result = session.selectOne(this.THRESHOLDINFO_MAPPER
					+ ".selectByTypeAndName",thresholdInfo);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	
	public List<ThresholdInfo> queryThresholdInfo(String thresholdType)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ThresholdInfo> result = session.selectList(
					this.THRESHOLDINFO_MAPPER + ".selectByType", thresholdType);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public List<ThresholdInfo> queryAllThresholdInfo()
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ThresholdInfo> result = session.selectList(this.THRESHOLDINFO_MAPPER + ".selectAll");
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public Integer updateThresholdInfo(ThresholdInfo thresholdInfo)
			throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.update(this.THRESHOLDINFO_MAPPER
					+ ".updateByPrimaryKeySelective", thresholdInfo);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer updateThresholdInfos(List<ThresholdInfo> thresholdInfos)throws Exception{
		Integer result = new Integer(0);
		SqlSession session = null;
		try {
			if (thresholdInfos != null && thresholdInfos.size() > 0) {
				/* 打开会话 */
				session = sqlSessionFactory.openSession();
				for (ThresholdInfo info : thresholdInfos) {
					result += session.update(this.THRESHOLDINFO_MAPPER
							+ ".updateByPrimaryKeySelective", info);
				}
			}
			return result;
		} catch (Exception e) {
			throw e;
		}finally{
			session.close();			
		}		
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
}
