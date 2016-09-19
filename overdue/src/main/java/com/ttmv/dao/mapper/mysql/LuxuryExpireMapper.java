package com.ttmv.dao.mapper.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.sentry.SentryAgent;

/**
 * Mysql 豪车到期记录
 * @author wll
 *
 */
@Component("luxuryExpireMapper")
public class LuxuryExpireMapper {

	private String LUXURYEXPIRE_MAPPER = "com.ttmv.dao.inter.LuxuryExpireMapper";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
	@Resource(name = "sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	
	public Integer addLuxuryExpire(LuxuryExpire luxuryExpire)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.selectOne(LUXURYEXPIRE_MAPPER
					+ ".insertSelective", luxuryExpire);
			if (result != null) {
				return result;
			}
		}catch(Exception e){
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public Integer updateLuxuryExpire(LuxuryExpire luxuryExpire)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.update(LUXURYEXPIRE_MAPPER
					+ ".updateByPrimaryKeySelective", luxuryExpire);
			if (result != null) {
				return result;
			}
		}catch(Exception e){
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public LuxuryExpire queryLuxuryExpire(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			LuxuryExpire luxuryExpire =session.selectOne(LUXURYEXPIRE_MAPPER
					+ ".selectByUserIdAndCarId", queryBean);
			if (luxuryExpire != null) {
				return luxuryExpire;
			}
		}catch(Exception e){
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public Integer deleteLuxuryExpire(BigInteger id)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.delete(LUXURYEXPIRE_MAPPER
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
	
	public List<LuxuryExpire> queryListLuxuryExpireByEndTime(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<LuxuryExpire> datas =session.selectList(LUXURYEXPIRE_MAPPER
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
	
	public List<LuxuryExpire> queryListLuxuryExpireByRemindTime(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<LuxuryExpire> datas =session.selectList(LUXURYEXPIRE_MAPPER
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

	public List<LuxuryExpire> queryListDelayNotify(QueryBean queryBean)throws Exception {
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<LuxuryExpire> datas =session.selectList(LUXURYEXPIRE_MAPPER
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
