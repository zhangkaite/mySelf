package com.ttmv.monitoring.collection.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.collection.entity.CpuInfo;

public class CpuInfoDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String CPUINFO_MAPPER = "com.ttmv.monitoring.collection.mapper.CpuInfoDaoMapper";

	public List<CpuInfo> queryPageUserInfo(CpuInfo cpuInfo) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<CpuInfo> datas = session.selectList(this.CPUINFO_MAPPER + ".selectCpuInfoList", cpuInfo);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer queryPageUserInfoSum(CpuInfo cpuInfo) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer sum = session.selectOne(this.CPUINFO_MAPPER + ".selectCpuInfoSumNum");
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
