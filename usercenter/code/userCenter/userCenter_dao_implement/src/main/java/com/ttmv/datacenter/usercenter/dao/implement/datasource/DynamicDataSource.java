package com.ttmv.datacenter.usercenter.dao.implement.datasource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDbType();
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}