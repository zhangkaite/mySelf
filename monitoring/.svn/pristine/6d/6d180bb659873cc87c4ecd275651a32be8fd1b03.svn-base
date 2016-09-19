package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.WhiteList;
import com.ttmv.monitoring.entity.querybean.WhiteListQuery;

public class WhiteListDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String WHITELIST_MAPPER = "com.ttmv.monitoring.inter.WhiteListMapper";

	public Integer addWhiteList(WhiteList whiteList) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.WHITELIST_MAPPER
					+ ".insertSelective", whiteList);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer updateWhiteList(WhiteList whiteList) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.update(this.WHITELIST_MAPPER
					+ ".updateByPrimaryKeySelective", whiteList);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer deleteWhiteList(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.delete(this.WHITELIST_MAPPER
					+ ".deleteByPrimaryKey", id);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public WhiteList queryWhiteList(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			WhiteList data = session.selectOne(this.WHITELIST_MAPPER
					+ ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}

	}

	public List<WhiteList> queryListByConditions(WhiteList whitelist)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			 List<WhiteList> datas = session.selectList(
					this.WHITELIST_MAPPER + ".selectByConditions",whitelist);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}		
	}

	public List<WhiteList> queryPageWhiteList(WhiteListQuery WhiteListQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<WhiteList> datas = session.selectList(
					this.WHITELIST_MAPPER + ".selectByFuzzyConditions",WhiteListQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}	
	}

	public Integer queryPageWhiteListSum(WhiteListQuery whiteListQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer sum = session.selectOne(
					this.WHITELIST_MAPPER + ".selectByFuzzyConditionsAll",
					whiteListQuery);
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
