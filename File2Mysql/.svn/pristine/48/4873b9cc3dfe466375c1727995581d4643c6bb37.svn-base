package com.ttmv.dbcp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.ttmv.util.ApplicationResource;

/**
 *
 * dbcp 实用类，提供了dbcp连接，不允许继承；
 *
 * 该类需要有个地方来初始化 DS ，通过调用initDS 方法来完成，可以在通过调用带参数的构造函数完成调用，可以在其它类中调用， 也可以在本类中加一个static{}来完成；
 */
public final class DbcpBean {
	/** 数据源，static */
	private static DataSource DS;

	public DbcpDao dbcpDao = null;

	/** 从数据源获得一个连接 */
	public Connection getConn() {
		try {
			return DS.getConnection();
		} catch (SQLException e) {
			System.out.println("获得连接出错！");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取DbcpDao
	 */
	public DbcpDao getDbcpDao() {
		return dbcpDao;

	}

	/** 默认的构造函数 */
	public DbcpBean() {
		initDS();
		getConn();
		initDao();
	}

	/** 构造函数，初始化了 DS ，指定 所有参数 */
	public DbcpBean(String connectURI, String username, String pswd, String driverClass, int initialSize,
			int maxActive, int maxIdle, int maxWait) {
		initDS(connectURI, username, pswd, driverClass, initialSize, maxActive, maxIdle, maxWait);
	}

	/**
	 * 创建数据源，除了数据库外，都使用硬编码默认参数；
	 *
	 * @param
	 * @return
	 */
	public void initDS() {
		String connectURI = ApplicationResource.getValue("dbcp.default.connectURI");
		String username = ApplicationResource.getValue("dbcp.default.username");
		String pswd = ApplicationResource.getValue("dbcp.default.password");
		String driverClass = ApplicationResource.getValue("dbcp.default.driverClassName");
		int initialSize = Integer.parseInt(ApplicationResource.getValue("dbcp.default.initialSize"));
		int maxActive = Integer.parseInt(ApplicationResource.getValue("dbcp.default.maxActive"));
		int maxIdle = Integer.parseInt(ApplicationResource.getValue("dbcp.default.maxIdle"));
		int maxWait = Integer.parseInt(ApplicationResource.getValue("dbcp.default.maxWait"));
		initDS(connectURI, username, pswd, driverClass, initialSize, maxActive, maxIdle, maxWait);
	}

	/**
	 * 创建DAO
	 *
	 * @param
	 * @return
	 */
	public void initDao() {
		dbcpDao = new DbcpDao();
		dbcpDao.setDataSource(DS);
	}

	/**
	 * 指定所有参数连接数据源
	 *
	 * @param connectURI
	 *            数据库
	 * @param username
	 *            用户名
	 * @param pswd
	 *            密码
	 * @param driverClass
	 *            数据库连接驱动名
	 * @param initialSize
	 *            初始连接池连接个数
	 * @param maxActive
	 *            最大激活连接数
	 * @param maxIdle
	 *            最大闲置连接数
	 * @param maxWait
	 *            获得连接的最大等待毫秒数
	 * @return
	 */
	public void initDS(String connectURI, String username, String pswd, String driverClass, int initialSize,
			int maxActive, int maxIdle, int maxWait) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverClass);
		ds.setUsername(username);
		ds.setPassword(pswd);
		ds.setUrl(connectURI);
		ds.setInitialSize(initialSize); // 初始的连接数；
		ds.setMaxActive(maxActive);
		ds.setMaxIdle(maxIdle);
		ds.setMaxWait(maxWait);
		DS = ds;
	}

	/** 获得数据源连接状态 */
	public Map<String, Integer> getDataSourceStats() throws SQLException {
		BasicDataSource bds = (BasicDataSource) DS;
		Map<String, Integer> map = new HashMap<String, Integer>(2);
		map.put("active_number", bds.getNumActive());
		map.put("idle_number", bds.getNumIdle());
		return map;
	}

	/** 关闭数据源 */
	public void shutdownDataSource() throws SQLException {
		BasicDataSource bds = (BasicDataSource) DS;
		bds.close();
	}
}
