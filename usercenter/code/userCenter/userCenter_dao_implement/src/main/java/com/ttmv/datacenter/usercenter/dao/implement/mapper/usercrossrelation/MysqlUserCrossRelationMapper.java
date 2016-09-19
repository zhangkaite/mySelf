package com.ttmv.datacenter.usercenter.dao.implement.mapper.usercrossrelation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.usercenter.dao.implement.constant.Constant;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.BaseMysqlMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.usercrossrelation.MysqlUserCrossRelation;
import com.ttmv.datacenter.usercenter.dao.implement.util.BeanCopyProperties;
import com.ttmv.datacenter.usercenter.domain.data.UserCrossRelation;

public class MysqlUserCrossRelationMapper extends BaseMysqlMapper{

	private String USERCROSSRELATION_MAPPER ="com.ttmv.datacenter.usercenter.dao.implement.mapper.MysqlUserCrossRelationMapper";
	private SentryAgent quickSentry;
	
	/**
	 * 添加MysqlUserCrossRelation
	 * @param mysql
	 * @return
	 */
	public List<Object> addMysqlUserCrossRelation(MysqlUserCrossRelation mysql,String dataSourceKey)throws Exception{
		List<Object> results = new ArrayList<Object>();
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			/* 设置写入的datasource */
			this.setWriteDataSource(dataSourceKey);
			int num = session.insert(this.USERCROSSRELATION_MAPPER + ".insertSelective", mysql);
			results.add(dataSourceKey);
			results.add(num);
			return results;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("添加MysqlUserCrossRelation失败！",e);
		}finally{
			session.close();
		}
		
	}
	/**
	 * 查询所有的MysqlUserCrossRelation
	 * @param dataSourceKey
	 * @return
	 */
	public List<MysqlUserCrossRelation> getAllMysqlUserCrossRelation(String dataSourceKey)throws Exception{
		/* 获取读的 dataSource*/
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			List<MysqlUserCrossRelation> list =session.selectList(USERCROSSRELATION_MAPPER + ".selectAll");
			if (list != null && list.size() > 0) {
				return list;
			}
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		
		return null;
	}
	/**
	 * 修改MysqlUserCrossRelation
	 * @param mysql
	 * @return
	 */
	public int updateMysqlUserCrossRelation(MysqlUserCrossRelation mysql,String dataSourceKey)throws Exception{
		/* 获取写入的datasource */
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			int num = session.update(this.USERCROSSRELATION_MAPPER + ".updateByPrimaryKeySelective", mysql);
			return num;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("修改MysqlUserCrossRelation失败!",e);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 删除MysqlUserCrossRelation根据id
	 * @param id
	 * @return
	 */
	public int deleteMysqlUserCrossRelationById(BigInteger id,String dataSourceKey)throws Exception{
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			int num = session.delete(this.USERCROSSRELATION_MAPPER + ".deleteByPrimaryKey", id);
			return num;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception("删除MysqlUserCrossRelation失败！",e);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 通过id查询MysqlUserCrossRelation
	 * @param id
	 * @return
	 */
	public MysqlUserCrossRelation getMysqlUserCrossRelationById(BigInteger id,String dataSourceKey)throws Exception{
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			MysqlUserCrossRelation mysql = session.selectOne(this.USERCROSSRELATION_MAPPER + ".selectByPrimaryKey", id);
			if(mysql != null){				
				return mysql;
			}
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	/**
	 * 通过用户id 查询好友的id
	 * @param userIdA
	 * @return
	 */
	public List<MysqlUserCrossRelation> getListMysqlUserCrossRelationByUserIdA(String userIdA,String dataSourceKey)throws Exception{
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
		List<MysqlUserCrossRelation> mysqls = session.selectOne(
				this.USERCROSSRELATION_MAPPER + ".selectListByUserIdA", userIdA);
		return mysqls;
		}catch(Exception e){
			quickSentry.sendMsg(Constant.UC_SERVER_TYPE, Constant.UC_SERVER_ID, Constant.UC_MYSQL_ERROR_MSG + e.getMessage(), Constant.UC_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 生成MysqlUserCrossRelation对象
	 * 从UserCrossRelation对象中复制值
	 * @param userCrossRelation
	 * @return
	 */
	public MysqlUserCrossRelation userCrossRelationConvertToMysqlUserCrossRelation(UserCrossRelation userCrossRelation)throws Exception{
		MysqlUserCrossRelation mysql = new MysqlUserCrossRelation();
		BeanCopyProperties.copyProperties(userCrossRelation, mysql, false, null);
		mysql.setId(userCrossRelation.getId());
		return mysql;
	}
	
	/**
	 * 修改MysqlUserCrossRelation从UserCrossRelation中复制最新的值
	 * 到MysqlUserCrossRelation对象中
	 * @param userCrossRelation
	 * @param mysql
	 * @return
	 */
	public MysqlUserCrossRelation userCrossRelationConvertToMysqlUserCrossRelation(UserCrossRelation userCrossRelation,MysqlUserCrossRelation mysql)throws Exception{
		BeanCopyProperties.copyProperties(userCrossRelation, mysql, false,null);
		return mysql;
	}
	
	/**
	 * 生成UserCrossRelation对象
	 * 从MysqlUserCrossRelation对象中复制值
	 * @param mysql
	 * @return
	 */
	public UserCrossRelation mysqlUserCrossRelationConvertToUserCrossRelation(MysqlUserCrossRelation mysql){
		UserCrossRelation cross = new UserCrossRelation();
		BeanUtils.copyProperties(mysql, cross);
		return cross;
	}
	public String getUSERCROSSRELATION_MAPPER() {
		return USERCROSSRELATION_MAPPER;
	}
	public void setUSERCROSSRELATION_MAPPER(String uSERCROSSRELATION_MAPPER) {
		USERCROSSRELATION_MAPPER = uSERCROSSRELATION_MAPPER;
	}
	public SentryAgent getQuickSentry() {
		return quickSentry;
	}
	public void setQuickSentry(SentryAgent quickSentry) {
		this.quickSentry = quickSentry;
	}
}
