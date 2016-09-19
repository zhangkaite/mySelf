package com.ttmv.dao.mapper.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.sentry.SentryAgent;

/**
 * Mysql VIP到期记录
 * 
 * @author wll
 *
 */
@Component("goodNumberExpireMapper")
public class GoodNumberExpireMapper {

	private String GOODNUMBEREXPIRE_MAPPER = "com.ttmv.dao.inter.GoodNumberExpireMapper";

	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
	@Resource(name = "sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;

	public Integer addGoodNumberExpire(GoodNumberExpire goodNumberExpire) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.selectOne(GOODNUMBEREXPIRE_MAPPER + ".insertSelective", goodNumberExpire);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public Integer updateGoodNumberExpire(GoodNumberExpire goodNumberExpire) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.update(GOODNUMBEREXPIRE_MAPPER + ".updateByPrimaryKeySelective", goodNumberExpire);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public GoodNumberExpire queryGoodNumberExpire(QueryBean queryBean) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			GoodNumberExpire goodNumberExpire = session.selectOne(GOODNUMBEREXPIRE_MAPPER
					+ ".selectByUserIdAndGoodNumber", queryBean);
			if (goodNumberExpire != null) {
				return goodNumberExpire;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}
	
	
	public GoodNumberExpire queryGoodNumberExpireByNumType(QueryBean queryBean) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			GoodNumberExpire goodNumberExpire = session.selectOne(GOODNUMBEREXPIRE_MAPPER
					+ ".selectByNumType", queryBean);
			if (goodNumberExpire != null) {
				return goodNumberExpire;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}
	
	
	
	

	public Integer deleteGoodNumberExpire(BigInteger id) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.delete(GOODNUMBEREXPIRE_MAPPER + ".deleteByPrimaryKey", id);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public List<GoodNumberExpire> queryListGoodNumberExpireByEndTime(QueryBean queryBean) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<GoodNumberExpire> datas = session.selectList(GOODNUMBEREXPIRE_MAPPER + ".selectListByEndTime",
					queryBean);
			if (datas != null) {
				return datas;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public List<GoodNumberExpire> queryListGoodNumberExpireByRemindTime(QueryBean queryBean) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<GoodNumberExpire> datas = session.selectList(GOODNUMBEREXPIRE_MAPPER + ".selectListRemindTime",
					queryBean);
			if (datas != null) {
				return datas;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public Integer updateFlag(GoodNumberExpire goodNumberExpire) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			Integer result = session.update(GOODNUMBEREXPIRE_MAPPER + ".updateByUserIDAndGoodNum", goodNumberExpire);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public GoodNumberExpire queryBindedNum(GoodNumberExpire goodNumberExpire) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			GoodNumberExpire result = session.selectOne(GOODNUMBEREXPIRE_MAPPER + ".selectByUserId", goodNumberExpire);
			if (result != null) {
				return result;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}

	public List<GoodNumberExpire> queryListDelayNotify(QueryBean queryBean) throws Exception {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			List<GoodNumberExpire> datas = session.selectList(GOODNUMBEREXPIRE_MAPPER + ".queryListDelayNotify",
					queryBean);
			if (datas != null) {
				return datas;
			}
		} catch (Exception e) {
			/*quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID,
					Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);*/
			throw new Exception(e);
		} finally {
			session.close();
		}
		return null;
	}
}
