package com.datacenter.dams.business.center.output;

import com.datacenter.dams.business.dao.hdfs.HdfsBaseDao;

/**
 * 将消费全量数据写入hdfs文件系统中
 * @author wll
 */
public class HadoopOffLineSpendCenter {

	private HdfsBaseDao hdfsBaseDao;
	
	public void handler(Object object) throws Exception {
		hdfsBaseDao.create();
	}

	public HdfsBaseDao getHdfsBaseDao() {
		return hdfsBaseDao;
	}

	public void setHdfsBaseDao(HdfsBaseDao hdfsBaseDao) {
		this.hdfsBaseDao = hdfsBaseDao;
	}
}
