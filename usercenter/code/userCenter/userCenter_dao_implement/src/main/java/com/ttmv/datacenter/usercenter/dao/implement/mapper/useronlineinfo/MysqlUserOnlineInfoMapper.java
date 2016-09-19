package com.ttmv.datacenter.usercenter.dao.implement.mapper.useronlineinfo;

import java.math.BigInteger;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.BaseMysqlMapper;
import com.ttmv.datacenter.usercenter.dao.implement.mapper.bean.useronlineinfo.MysqlUserOnlineInfo;

public class MysqlUserOnlineInfoMapper extends BaseMysqlMapper{

	private String USERONLINEINFO_MAPPER ="com.ttmv.datacenter.usercenter.dao.implement.mapper.MysqlUserOnlineInfoMapper";
	/**
	 * 添加UserOnlineInfo
	 * @param userOnlineInfo
	 * @param dataSourceKey
	 * @return
	 */
	public int addMysqlUserOnlineInfo(MysqlUserOnlineInfo mysql)throws Exception{
		/* 获取写入的datasource */
		this.getWriteDataSource();
		
		int num = sqlSessionFactory.openSession().insert(
				this.USERONLINEINFO_MAPPER + ".insertSelective", mysql);
		return num;
	}
	
	/**
	 * 修改MysqlUserOnlineInfo
	 * @param userOnlineInfo
	 * @param dataSourceKey
	 * @return
	 */
	public int updateMysqlUserOnlineInfo(MysqlUserOnlineInfo mysql ,String dataSourceKey)throws Exception{
		/* 获取写入的datasource */
		this.getReadDataSource(dataSourceKey);

		int num = sqlSessionFactory.openSession().update(
				this.USERONLINEINFO_MAPPER + ".updateByPrimaryKeySelective", mysql);
		return num;
		
		
	}
	
	/**
	 * 根据Id删除MysqlUserOnlineInfo
	 * @param id
	 * @param dataSourceKey
	 */
	public int deleteMysqlUserOnlineInfoById(BigInteger id,String dataSourceKey)throws Exception{
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);

		int num = sqlSessionFactory.openSession().delete(
				this.USERONLINEINFO_MAPPER + ".deleteByPrimaryKey", id);
		return num;
	}
	
	/**
	 * 根据userid删除MysqlUserOnlineInfo
	 * @param id
	 * @param dataSourceKey
	 */
	public int deleteMysqlUserOnlineInfoByUserid(BigInteger userid,String dataSourceKey)throws Exception{
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);

		int num = sqlSessionFactory.openSession().delete(
				this.USERONLINEINFO_MAPPER + ".deleteByUserId", userid);
		return num;
	}
	
	/**
	 * 根据userid查询MysqlUserOnlineInfo
	 * @return
	 */
	public MysqlUserOnlineInfo getMysqlUserOnlineInfoByUserid(BigInteger userid,String dataSourceKey)throws Exception{
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);

		MysqlUserOnlineInfo mysql = sqlSessionFactory.openSession().selectOne(
				this.USERONLINEINFO_MAPPER + ".selectByUserId", userid);
		return mysql;
	}
	
	/**
	 * 根据id查询MysqlUserOnlineInfo
	 * @param id
	 * @return
	 */
	public MysqlUserOnlineInfo getMysqlUserOnlineInfoById(BigInteger id,String dataSourceKey)throws Exception{
		/* 获取指定datasource 连接 */
		this.getReadDataSource(dataSourceKey);

		MysqlUserOnlineInfo mysql = sqlSessionFactory.openSession().selectOne(
				this.USERONLINEINFO_MAPPER + ".selectByPrimaryKey", id);
		return mysql;
	}
}
