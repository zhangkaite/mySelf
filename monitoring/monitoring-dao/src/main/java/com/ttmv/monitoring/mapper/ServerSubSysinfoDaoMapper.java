package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.ServerSubSysinfo;

public class ServerSubSysinfoDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String SERVERSUBSYSINFO_MAPPER = "com.ttmv.monitoring.inter.ServerSubSysinfoMapper";

	public Integer addServerSubSysinfo(ServerSubSysinfo serverSubSysinfo)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.SERVERSUBSYSINFO_MAPPER
					+ ".insertSelective", serverSubSysinfo);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public ServerSubSysinfo queryServerSubSysinfo(BigInteger id)
			throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			ServerSubSysinfo data = session.selectOne(
					this.SERVERSUBSYSINFO_MAPPER + ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}
	
	public List<ServerSubSysinfo> queryServerSubSysinfoBySysType(String  sysType)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<ServerSubSysinfo> datas = session.selectList(
					this.SERVERSUBSYSINFO_MAPPER + ".selectBySystype",	sysType);
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
