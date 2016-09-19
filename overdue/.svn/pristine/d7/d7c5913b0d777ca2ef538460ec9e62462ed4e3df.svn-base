package com.ttmv.dao.mapper.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.sentry.SentryAgent;

/**
 * Mysql 爵位到期记录
 * @author wll
 *
 */
@Component("nobilityExpireMapper")
public class NobilityExpireMapper {

	private String NOBOILITYEXPIRE_MAPPER = "com.ttmv.dao.inter.NobilityExpireMapper";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
	@Resource(name = "sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	
	public Integer addNobilityExpire(NobilityExpire nobilityExpire)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.selectOne(NOBOILITYEXPIRE_MAPPER
					+ ".insertSelective", nobilityExpire);
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
	
	public Integer updateNobilityExpire(NobilityExpire nobilityExpire)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.update(NOBOILITYEXPIRE_MAPPER
					+ ".updateByPrimaryKeySelective", nobilityExpire);
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
	
	public NobilityExpire queryNobilityExpire(BigInteger userId)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			NobilityExpire nobilityExpire =session.selectOne(NOBOILITYEXPIRE_MAPPER
					+ ".selectByPrimaryKey", userId);
			if (nobilityExpire != null) {
				return nobilityExpire;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public Integer deleteNobilityExpire(BigInteger id)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.delete(NOBOILITYEXPIRE_MAPPER
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
	
	public List<NobilityExpire> queryListNobilityExpireByEndTime(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<NobilityExpire> datas =session.selectList(NOBOILITYEXPIRE_MAPPER
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
	
	public List<NobilityExpire> queryListNobilityExpireByRemindTime(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<NobilityExpire> datas =session.selectList(NOBOILITYEXPIRE_MAPPER
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

	public List<NobilityExpire> queryListDelayNotify(QueryBean queryBean)throws Exception {
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<NobilityExpire> datas =session.selectList(NOBOILITYEXPIRE_MAPPER
					+ ".queryListDelayNotify", queryBean);
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
