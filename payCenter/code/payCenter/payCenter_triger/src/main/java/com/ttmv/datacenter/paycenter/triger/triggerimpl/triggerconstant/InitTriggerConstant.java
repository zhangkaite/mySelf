package com.ttmv.datacenter.paycenter.triger.triggerimpl.triggerconstant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InitTriggerConstant {

	private static Logger log = LogManager.getLogger(InitTriggerConstant.class);
	
	/**
	 * 初始化TriggerConstant
	 */
	public void init(){
		InputStream in = InitTriggerConstant.class.getClassLoader().getResourceAsStream("trigger.properties");
		Properties prop = new Properties();
		try {
			/* 读取properties文件 */
			prop.load(in);
			Set<Object> set = prop.keySet();
			Iterator<Object> iterator = set.iterator();
			while(iterator.hasNext()){
				String key = iterator.next().toString();
				String value = prop.getProperty(key).trim();
				/* 将key-value值存入TriggerConstant的collection变量中 */
				Trigger.setCollection(key, value);
			}
		} catch (IOException e) {
			log.debug("初始化TriggerConstant的collection参数！");
		}
	}
	
	/**
	 * 初始化DB库中不存在的key
	 */
	public void initNotExsistKeyOnDB(){
		
	}
}
