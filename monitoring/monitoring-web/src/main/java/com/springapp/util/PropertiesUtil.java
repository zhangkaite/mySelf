package com.springapp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 * 
 * @author zkt
 *
 */
public class PropertiesUtil {

	/** 属性文件列表 */
	private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

	/**
	 * 根据属性文件名称和属性名获取属性值
	 * 
	 * @param propertiesFileName
	 *            属性文件名称
	 * @param key
	 *            属性名称
	 * @return 属性值
	 */
	public static String get(String propertiesFileName, String key) {
		if (null != propertiesFileName && 0 < propertiesFileName.trim().length()) {
			if (propertiesMap.containsKey(propertiesFileName)) {
				return propertiesMap.get(propertiesFileName).getProperty(key);
			} else {
				Properties properties = new Properties();
				try {
					properties.load(PropertiesUtil.class.getClassLoader()
							.getResourceAsStream(propertiesFileName.trim()));
					propertiesMap.put(propertiesFileName, properties);
					return PropertiesUtil.get(propertiesFileName, key);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static Boolean updatePro(String path, String key, String value) {
		Properties prop = new Properties();// 属性集合对象
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);
			prop.load(fis);// 将属性文件流装载到Properties对象中
			fis.close();// 关闭流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		prop.setProperty(key, value);
		// 文件输出流
		try {
			FileOutputStream fos = new FileOutputStream(path);
			// 将Properties集合保存到流中
			fos.close();// 关闭流
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		PropertiesUtil.updatePro("src/main/resources/application.properties","openInfService","false");
	}

}
