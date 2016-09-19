package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.querybean.UserInfoQuery;

public class UserInfoDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String USERINFO_MAPPER = "com.ttmv.monitoring.imp.UserInfoMapper";

	public Integer addUserInfo(UserInfo userInfo) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.insert(this.USERINFO_MAPPER
					+ ".insertSelective", userInfo);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer updateUserInfo(UserInfo userInfo) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.update(this.USERINFO_MAPPER
					+ ".updateByPrimaryKeySelective", userInfo);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public Integer deleteUserInfo(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.delete(this.USERINFO_MAPPER
					+ ".deleteByPrimaryKey", id);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
	}

	public UserInfo queryUserInfo(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			UserInfo data = session.selectOne(this.USERINFO_MAPPER
					+ ".selectByPrimaryKey", id);
			return data;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}

	}

	public List<UserInfo> queryUserInfo(UserInfoQuery userInfoQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<UserInfo> datas = session.selectList(
					this.USERINFO_MAPPER + ".selectByConditions", userInfoQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}
		
	}

	public List<UserInfo> queryPageUserInfo(UserInfoQuery userInfoQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<UserInfo> datas = session.selectList(
					this.USERINFO_MAPPER + ".selectByFuzzyConditions",userInfoQuery);
			return datas;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}	
	}

	public Integer queryPageUserInfoSum(UserInfoQuery userInfoQuery)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer sum = session.selectOne(
					this.USERINFO_MAPPER + ".selectByFuzzyConditionsAll",
					userInfoQuery);
			return sum;
		} catch (Exception e) {
			throw e;
		} finally {
			session.close();
		}		
	}

	public UserInfo login(UserInfo userInfo)throws Exception{
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			UserInfo user = session.selectOne(
					this.USERINFO_MAPPER + ".login", userInfo);
			return user;
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
