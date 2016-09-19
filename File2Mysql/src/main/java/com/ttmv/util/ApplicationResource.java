package com.ttmv.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ApplicationResource {
	/**
	 * 获取properties文件的工具类
	 */
	/** 日志. */
	protected static final Log log = LogFactory.getLog(ApplicationResource.class);
	/** 属性文件. */
	private final static Properties _applicationProperties = new Properties();
	static {
		load();
	}

	public final static void load() {
		InputStream is = null;
		try {
			is = ApplicationResource.class.getResourceAsStream("/application.properties");
			_applicationProperties.load(is);
			log.info("加载application.properties文件.");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 返回key对应的value
	 *
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		if (key == null || key.trim().length() == 0) {
			return null;
		}
		return _applicationProperties.getProperty(key);
	}

}
