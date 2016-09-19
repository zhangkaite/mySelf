package com.ttmv.dbcp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class DbcpDao extends JdbcDaoSupport {
	/** log4j instance. */
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * 插入数据
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	public int insertData(String sql, Object... args) {
		int num = getJdbcTemplate().update(sql, args);
		return num;
	}

}
