package com.ttmv.dao.mapper.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.sentry.SentryAgent;

/**
 * Mysql 用户冻结账户到期记录
 * @author wll
 *
 */
@Component("userForbiddenExpireMapper")
public class UserForbiddenExpireMapper {

	private String USERFORBIDDEN_MAPPER = "com.ttmv.dao.inter.UserForbiddenExpireMapper";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
	@Resource(name = "sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	
	public Integer addUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.selectOne(USERFORBIDDEN_MAPPER
					+ ".insertSelective", userForbiddenExpire);
			if (result != null) {
				return result;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public Integer updateUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.update(USERFORBIDDEN_MAPPER
					+ ".updateByPrimaryKeySelective", userForbiddenExpire);
			if (result != null) {
				return result;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public UserForbiddenExpire queryUserForbiddenExpire(BigInteger userId)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			UserForbiddenExpire userForbiddenExpire =session.selectOne(USERFORBIDDEN_MAPPER
					+ ".selectByPrimaryKey", userId);
			if (userForbiddenExpire != null) {
				return userForbiddenExpire;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public Integer deleteUserForbiddenExpire(BigInteger id)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.delete(USERFORBIDDEN_MAPPER
					+ ".deleteByPrimaryKey", id);
			if (result != null) {
				return result;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public List<UserForbiddenExpire> queryListUserForbiddenExpireByEndTime(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<UserForbiddenExpire> datas =session.selectList(USERFORBIDDEN_MAPPER
					+ ".selectListByEndTime", queryBean);
			if (datas != null) {
				return datas;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public List<UserForbiddenExpire> queryListUserForbiddenExpireByRemindTime(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<UserForbiddenExpire> datas =session.selectList(USERFORBIDDEN_MAPPER
					+ ".selectListRemindTime", queryBean);
			if (datas != null) {
				return datas;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
}
