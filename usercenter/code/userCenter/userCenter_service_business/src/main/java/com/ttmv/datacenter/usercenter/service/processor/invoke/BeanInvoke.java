package com.ttmv.datacenter.usercenter.service.processor.invoke;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.ttmv.datacenter.utils.check.CheckParameterUtil;
import com.ttmv.datacenter.utils.letter.UpperCase;

/**
 * 
 * @author Scarlett.zhou
 * @date 2015年1月19日
 */
public class BeanInvoke {
	
	
	private final static Logger logger = LogManager.getLogger(BeanInvoke.class);
		
	/**
	 *  把协议解析成对象
	 *  Object o ：  需要包装的对象，可以是String，Map，List（List只支持list中包含serviceClass对象，不支持list嵌套List等其他类型）
	 *  serviceClass ：包装成的对象
	 *  support ：    对于对象中的属性值
	 *               false 只支持String，Integer，Object 三种
	 *               true  支持String,Integer,Object，list和Map的属性
	 * */
	public static Object getServiceObject(Object o,Class<?> serviceClass,boolean support) throws Exception{
		if(o == null){
			logger.error("传入的需要解析的对象为空. [object]");
			throw new Exception("传入的需要解析的对象为空. [object] ="+o);
		}else if(o instanceof String){
			return getServiceObject((String)o,serviceClass,support);
		}else if(o instanceof List){
			return getServiceObject((List)o,serviceClass,support);
		}else if(o instanceof Map){
			return getServiceObject((Map)o,serviceClass,support);
		}
		return null;
	}
	
	private static Object getServiceObject(String data,Class<?> serviceClass,boolean support) throws Exception{
		if(CheckParameterUtil.checkIsEmpty(data) || serviceClass == null){
			logger.error("传入的需要解析的对象为空. [data]="+data+" [serviceClass]="+support);
			throw new Exception("传入的需要解析的对象为空. [data]="+data+" [serviceClass]="+support);
		}
		try{
		Map<String,Object> map = new ObjectMapper().readValue(data, Map.class);	
	    return getServiceObject(map,serviceClass,support);
		}catch(Exception e){
			logger.error(data+"串解析Map失败.\n" + e.getMessage());
			throw new Exception(data+"串解析Map失败.\n",e);
		}
	}
	
	private static Object getServiceObject(List list,Class<?> serviceClass,boolean support) throws Exception {
		if(list ==null || serviceClass == null){
			logger.error("传入的需要解析的对象为空. [list]="+list+" [serviceClass]="+serviceClass);
			throw new Exception("传入的需要解析的对象为空. [list]="+list+" [serviceClass]="+serviceClass);
		}
		List<Object> req = new ArrayList<Object>();
		for(Object o : list){
		    if(o instanceof Map){
		    	req.add(getServiceObject((Map)o,serviceClass,support));
		    }else{
		    	logger.error("转换json对象失败，无该类型的json自动注入方法!");
		    	throw new Exception("转换json对象失败，无该类型的json自动注入方法!");
		    }
		}
		return req;
	}

	private static Object getServiceObject(Map<String,Object> map,Class<?> serviceClass,boolean support) throws Exception{
		if(map == null || serviceClass == null){
			logger.error("传入的需要解析的对象为空. [map]="+map+" [serviceClass]="+serviceClass);
			throw new Exception("传入的需要解析的对象为空. [map]="+map+" [serviceClass]="+serviceClass);
		}
		try{
	    Object serviceObject = serviceClass.newInstance();
	    for(Map.Entry<String,Object> entry : map.entrySet()){
	    	String name = (String) entry.getKey();
	    	Object key = entry.getValue();
	    	Method m;
			try {
				Class<?> returnType = serviceClass.getMethod("get"+UpperCase.firstUpperCase(name)).getReturnType();
				Type type = serviceClass.getMethod("get"+UpperCase.firstUpperCase(name)).getGenericReturnType();
				m = serviceClass.getMethod("set"+UpperCase.firstUpperCase(name),returnType);
				m.invoke(serviceObject, getKey(key,returnType,type));
				//m.invoke(serviceObject, key);
			} catch (Exception e) {
				logger.error(serviceClass+"的属性 ["+name+"] 赋值失败.\n" + e.getMessage());
				throw new Exception(serviceClass+"的属性 ["+name+"] 赋值失败.\n",e);
			} 
	     }
	    return serviceObject;
		}catch(Exception e){
			logger.error(serviceClass+"对象映射失败.\n" + e.getMessage());
			throw new Exception(serviceClass+"对象映射失败.\n",e);
		}
	}
	/**
	 *  类型按照protocol的类型的来定义
	 * **/
	private static Object getKey(Object key ,Class<?> returnType,Type type){
	
		if(returnType  == String.class){
			return String.valueOf(key);
		}
		if(returnType == int.class || returnType == Integer.class) {
			return  new Integer(String.valueOf(key));
		}
		if(returnType == BigInteger.class){ 
			return new BigInteger(String.valueOf(key));
		}
		if(returnType == BigDecimal.class){ 
			return new BigDecimal(String.valueOf(key));
		}
		if(returnType == long.class || returnType == Long.class){
		    return  new Long(String.valueOf(key));
	    }		
		//对List做泛型类型的判断
		if(returnType == List.class){
			if(type instanceof ParameterizedType){
			  Type[] types =((ParameterizedType)type).getActualTypeArguments();
			  if(types != null){
			   	 Class clazz = (Class)types[0];
			   	 //如果bean中的类型为List<BigIngeter>,那就把json串自动转为的List<Ingeter>转换为List<BigIngeter>
			   	 if(clazz == BigInteger.class){
			   	  	return convertList((List)key);
			   	 }
			  }
			}
			return key;
		}	
		if(returnType == Object.class || returnType == Map.class){
			return key;
		}
		return null;
	}
	
	private static List<BigInteger> convertList(List list){
		List<BigInteger> newKey = new ArrayList<BigInteger>();
   	  	for(Object o : list){
   	  	   if(o instanceof Integer){
   	  	      BigInteger newKeyValue = new BigInteger(String.valueOf((Integer)o));
   	  	      newKey.add(newKeyValue);
   	  	   }else{
   	  		  logger.warn("解析List对象可能会有问题，请查看list的泛型是否为Integer.");
   	  		  return list;
   	  	   }
   	  	}
   	  	return newKey;
	}	
}
