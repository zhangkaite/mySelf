package com.ttmv.datacenter.usercenter.dao.implement.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.rdbcluster.RDBCluster;
import com.ttmv.datacenter.usercenter.dao.implement.datasource.DataSourceContextHolder;

public abstract class BaseMysqlMapper {
	
	private final Logger logger = LogManager.getLogger(BaseMysqlMapper.class);
	
	protected SqlSessionFactory sqlSessionFactory;
	protected RDBCluster rdbCluster;

	/* 获取写入的datasource */
	protected String getWriteDataSource() {
		String writeDataSource = rdbCluster.getOneMaster();
		if(writeDataSource != null && !"".equals(writeDataSource)){
			DataSourceContextHolder.setDbType(writeDataSource);
		}
		return writeDataSource;
	}
	
	/* 设置写入的datasource */
	protected void setWriteDataSource(String datasource) {
		if(datasource != null && !"".equals(datasource)){
			DataSourceContextHolder.setDbType(datasource);
		}
	}
	
	/* 获取读的datasource */
	protected void getReadDataSource(String dataSourceKey){
		String slaveDataSourceKey = rdbCluster.getOneSlave(dataSourceKey);
		logger.debug("[获取读的datasource:]" + slaveDataSourceKey);
		if(slaveDataSourceKey != null && !"".equals(slaveDataSourceKey)){			
			DataSourceContextHolder.setDbType(slaveDataSourceKey);
		}else{
			DataSourceContextHolder.setDbType(dataSourceKey);
		}
	}
	
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public void setRdbCluster(RDBCluster rdbCluster) {
		this.rdbCluster = rdbCluster;
	}
}
