package com.ttmv.dao.mapper.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import com.ttmv.dao.bean.Cmp;
import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.sentry.SentryAgent;

/**
 * Mysql cmp到期记录
 * 
 * @author wll
 *
 */
@Component("cmpExpireMapper")
public class CmpExpireMapper {

	private String CMP_MAPPER = "com.ttmv.dao.inter.CmpExpireMapper";

	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
	@Resource(name = "sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;

	public Integer addCmp(Cmp cmp) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.selectOne(CMP_MAPPER + ".insertSelective", cmp);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(),
					Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unused")
	public Integer updateCmp(Cmp cmp) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.update(CMP_MAPPER + ".updateByPrimaryKeySelective", cmp);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(),
					Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public Cmp queryCmp(BigInteger userId) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Cmp cmp = session.selectOne(CMP_MAPPER + ".selectByUserId", userId);
			if (cmp != null) {
				return cmp;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(),
					Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unused")
	public Integer deleteCmp(Cmp cmp) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.delete(CMP_MAPPER + ".deleteByCmp", cmp);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(),
					Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public List<Cmp> queryListCmpByBean(Cmp cmp) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<Cmp> datas = session.selectList(CMP_MAPPER + ".selectListByCmp", cmp);
			if (datas != null) {
				return datas;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(),
					Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}
}
