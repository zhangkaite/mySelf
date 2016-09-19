package com.ttmv.dao.util;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	private static Logger logger = LogManager.getLogger(JsonUtil.class);
	/**
	 * 将Object对象转换成Json格式的数据
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String getObjectToJson(Object obj) {
		String resultJson  = null;
		ObjectMapper jsonMapper = new ObjectMapper();
		try {
			resultJson = jsonMapper.writeValueAsString(obj);
		} catch (Exception e) {
			logger.error("对象转换json失败，失败的原因是:",e);
			return null;
		}
		if(resultJson != null){
			return resultJson;
		}
		return null;
	}
	
	/**
	 * 将Json字符串转换成相应的
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static Object getObjectFromJson(String json,Class objClass) {
		ObjectMapper jsonMapper = new ObjectMapper();
		Object obj = null;
		try {
			obj = jsonMapper.readValue(json, objClass);
		}catch (Exception e) {
			logger.error("json转换对象失败，失败的原因是:",e);
			return null;
		}
		if(obj != null){
			return obj;
		}
		return null;
	}
}
