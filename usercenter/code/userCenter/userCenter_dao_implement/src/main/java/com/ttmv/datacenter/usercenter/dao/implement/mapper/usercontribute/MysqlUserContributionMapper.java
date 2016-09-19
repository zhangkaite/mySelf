package com.ttmv.datacenter.usercenter.dao.implement.mapper.usercontribute;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.impl.ParameterDaoImpl;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.BaseMysqlMapper;
import com.ttmv.datacenter.usercenter.domain.data.UserContribution;

public class MysqlUserContributionMapper extends BaseMysqlMapper{

	private static final String USERCONTRIBUTION_MAPPER = "com.ttmv.datacenter.usercenter.dao.implement.mapper.MysqlUserContributionMapper";
	private static final String DATASOURCE_KEY = "uc_mysql_m1";
	private SentryAgent quickSentry;
	private final Logger log = LogManager.getLogger(MysqlUserContributionMapper.class);
	/**
	 * 更新用户贡献礼物数
	 * 
	 * @param UserContribution
	 */
	public Integer updateMysqlUserContribution(UserContribution userContribution)
			throws Exception {
		/* 获取读的 dataSource */
		this.getWriteDataSource();
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.update(USERCONTRIBUTION_MAPPER + ".updateByUserIdAndRoomId", userContribution);
		} catch (Exception e) {
			log.error("Mysql操作失败！！！",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID,Constant.UC_MYSQL_ERROR_MSG + e.getMessage(),Constant.UC_ERROR);
			throw new Exception(e);
		} finally {
			session.close();
		}
	}
	
		/**
	 * 更新用户贡献礼物数
	 * 
	 * @param UserContribution
	 */
	public Integer insertMysqlUserContribution(UserContribution userContribution)
			throws Exception {
		/* 获取读的 dataSource */
		this.getWriteDataSource();
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.insert(USERCONTRIBUTION_MAPPER + ".insertSelective",userContribution);
		} catch (Exception e) {
			log.error("Mysql操作失败！！！",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID,Constant.UC_MYSQL_ERROR_MSG + e.getMessage(),Constant.UC_ERROR);
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	/**
	 * 根据用户ID和房间号ID查询用户
	 * @param UserContribution
	 */
	public UserContribution queryMysqlUserContribution(UserContribution userContribution)throws Exception{
		/* 获取读的 dataSource*/
		this.getReadDataSource(DATASOURCE_KEY);
//		this.getWriteDataSource();
		SqlSession session =  null;
		UserContribution data = null;
		try{
			session = sqlSessionFactory.openSession();
			data =session.selectOne(USERCONTRIBUTION_MAPPER + ".selectByUserIdAndRoomId", userContribution);
			if(data != null){
				return data;
			}
			return null;
		}catch(Exception e){
			log.error("Mysql操作失败！！！",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 根据用户ID和DateType删除用户
	 * @param userContribution
	 */
	public Integer deleteByRoomIdAndDataType(UserContribution userContribution)throws Exception{
		/* 获取读的 dataSource*/
		this.getWriteDataSource();
		SqlSession session =  null;
		Integer result = null;
		try{
			session = sqlSessionFactory.openSession();
			result = session.delete(USERCONTRIBUTION_MAPPER + ".deleteByRoomIdAndDataType", userContribution);
			return result;
		}catch(Exception e){
			log.error("Mysql操作失败！！！",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 获取房间所有用户
	 * @param roomId
	 * @return
	 * @throws Exception
	 */
	public List<UserContribution> getAllRoomUser(UserContribution userContribution)throws Exception{
		/* 获取读的 dataSource*/
		this.getReadDataSource(DATASOURCE_KEY);
//		this.getWriteDataSource();
		SqlSession session =  null;
		List<UserContribution> result = new ArrayList<UserContribution>();
		try{
			session = sqlSessionFactory.openSession();
			result =session.selectList(USERCONTRIBUTION_MAPPER + ".selectByRoomId", userContribution);
			if(result != null && result.size() > 0){
				return result;
			}
			return null;
		}catch(Exception e){
			log.error("Mysql操作失败！！！",e);
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
	}
	
	public SentryAgent getQuickSentry() {
		return quickSentry;
	}

	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}
