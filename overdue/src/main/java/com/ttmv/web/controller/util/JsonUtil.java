package com.ttmv.web.controller.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JsonUtil {
	private static Logger logger = LogManager.getLogger(JsonUtil.class);
	public static String getObjectToJson(Object obj) throws Exception {
		String resultJson = null;
		ObjectMapper jsonMapper = new ObjectMapper();
		resultJson = jsonMapper.writeValueAsString(obj);
		if (resultJson != null) {
			return resultJson;
		}
		return null;
	}

	public static Object getObjectFromJson(String json, Class objClass)  {
		ObjectMapper jsonMapper = new ObjectMapper();
		Object obj;
		try {
			obj = jsonMapper.readValue(json, objClass);
			if (obj != null) {
				return obj;
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public static void WriteJson(String methodName, Object resultMap, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String json = "";
		try {
			json = JsonUtil.getObjectToJson(resultMap);
			logger.info("调用" + methodName + "方法返回的json数据:" + json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().print(json);
	}
	
}
