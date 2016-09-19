package com.ttmv.dao.mapper.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.sentry.SentryAgent;

/**
 * Mysql VIP到期记录
 * @author wll
 *
 */
@Component("vipExpireMapper")
public class VipExpireMapper {

	private String VIPEXPIRE_MAPPER = "com.ttmv.dao.inter.VipExpireMapper";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
	@Resource(name = "sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	
	public Integer addVipExpire(VipExpire vipExpire)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.selectOne(VIPEXPIRE_MAPPER
					+ ".insertSelective", vipExpire);
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
	
	public Integer updateVipExpire(VipExpire vipExpire)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.update(VIPEXPIRE_MAPPER
					+ ".updateByPrimaryKeySelective", vipExpire);
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
	
	public VipExpire queryVipExpire(BigInteger userId)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			VipExpire vipExpire =session.selectOne(VIPEXPIRE_MAPPER
					+ ".selectByPrimaryKey", userId);
			if (vipExpire != null) {
				return vipExpire;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public Integer deleteVipExpire(BigInteger id)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.delete(VIPEXPIRE_MAPPER
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
	
	public List<VipExpire> queryListVipExpireByEndTime(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<VipExpire> datas =session.selectList(VIPEXPIRE_MAPPER
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
	
	public List<VipExpire> queryListVipExpireByRemindTime(QueryBean queryBean)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<VipExpire> datas =session.selectList(VIPEXPIRE_MAPPER
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
	
	public List<VipExpire> queryListDelayNotify(QueryBean queryBean) throws Exception{
		
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<VipExpire> datas =session.selectList(VIPEXPIRE_MAPPER
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
