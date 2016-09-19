package com.ttmv.datacenter.paycenter.dao.implement.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.ttmv.datacenter.paycenter.dao.implement.constant.AccountConstant;

public class MapUtil {

	/**
	 * 将bean的属性和值转换成key - value形式，使用Map结构
	 * @param object
	 * @return
	 */
	public static Map<String,String> beanToMap(Object object)throws Exception{
		if(object == null){
			throw new Exception("传入的对象为null！");
		}
		Method[] methods = object.getClass().getDeclaredMethods();
		if(methods.length == 0){
			return null;
		}
		Map<String,String> result = new HashMap<String, String>();
		for(Method method:methods){
			String methodName = method.getName();
			if(methodName.contains("get")){
				String field = getLowerName(methodName.substring(3));
				if(field.equals(AccountConstant.CREATED) || field.equals(AccountConstant.UPDATED)){
					continue;
				}
				Object obj = method.invoke(object, new Object[0]);
				if(obj == null){
					continue;
				}
				String value = obj.toString();
				/* 添加字段值 */
				result.put(field, value);
			}
		}
		return result;
	}
	
	/**
	 * 把Map结构的data转换为 Object类型
	 * @param data
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static Object mapTobean(Map<String,String> data,Object object)throws Exception{
		if(object == null ||data == null ){
			throw new Exception("传入的对象为null！");
		}
		Method[] methods = object.getClass().getDeclaredMethods();
		if(methods.length == 0){
			return null;
		}
		for(Method method:methods){
			String methodName = method.getName();
			if(methodName.contains("set")){
				String field = getLowerName(methodName.substring(3));
				if(data.containsKey(field)){
					method.invoke(object, new Object[]{data.get(field)});
					continue;
				}
			}
		}
		return object;
	}
	
	/**
	 * 设置字符串的第一个字母小写
	 * @param fildeName
	 * @return
	 */
	private static String getLowerName(String fildeName){
		byte[] items = fildeName.getBytes(); 
		   items[0] = (byte)((char)items[0]-'A'+'a');
		   return new String(items);
	}
}
