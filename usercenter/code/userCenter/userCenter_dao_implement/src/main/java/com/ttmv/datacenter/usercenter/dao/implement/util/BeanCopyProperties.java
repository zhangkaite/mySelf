package com.ttmv.datacenter.usercenter.dao.implement.util;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BeanCopyProperties {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	/**
	 * 复制相同属性的值从from对象到to对象 如果属性的值为Null则忽略
	 * 
	 * @param from
	 * @param to
	 * @throws Exception
	 */
	public static void copyProperties(Object from, Object to,Boolean IsConvertBigIntegerToString ,String excluds[]) throws Exception {
		copyPropertiesExclude(from, to, IsConvertBigIntegerToString,excluds);
	}

	/**
	 * 复制对象属性
	 * 
	 * @param from
	 * @param to
	 * @param excludsArray
	 *            排除属性列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static void copyPropertiesExclude(Object from, Object to, Boolean IsConvertBigIntegerToString ,
			String[] excludsArray) throws Exception {
		List<String> excludesList = null;
		if (excludsArray != null && excludsArray.length > 0) {
			excludesList = Arrays.asList(excludsArray); // 构造列表对象
		}
		Method[] fromMethods = from.getClass().getMethods();
		Method[] toMethods = to.getClass().getDeclaredMethods();
		Method fromMethod = null, toMethod = null;
		String fromMethodName = null, toMethodName = null;
		
		for (int i = 0; i < fromMethods.length; i++) {
			fromMethod = fromMethods[i];
			fromMethodName = fromMethod.getName();
			if (!fromMethodName.contains("get"))
				continue;
			
			/*排除列表检测*/
			if (excludesList != null&& excludesList.contains(fromMethodName.substring(3).toLowerCase())) {
				continue;
			}
			toMethodName = "set" + fromMethodName.substring(3);
			toMethod = findMethodByName(toMethods, toMethodName);
			if (toMethod == null)
				continue;
			Object value = fromMethod.invoke(from, new Object[0]);
			if (value == null)
				continue;
			
			/*集合类判空处理*/
			if (value instanceof Collection) {
				Collection newValue = (Collection) value;
				if (newValue.size() <= 0)
					continue;
			}
			if(IsConvertBigIntegerToString){
				if(value instanceof BigInteger){
					value = value.toString();
				}				
			}
//			if(value instanceof Date){
//				value = sdf.format(value).toString();
//			}
			toMethod.invoke(to, new Object[] { value });
		}
	}

	/**
	 * 从方法数组中获取指定名称的方法
	 * 
	 * @param methods
	 * @param name
	 * @return
	 */
	private static Method findMethodByName(Method[] methods, String name) {
		for (int j = 0; j < methods.length; j++) {
			if (methods[j].getName().equals(name))
				return methods[j];
		}
		return null;
	}
}
