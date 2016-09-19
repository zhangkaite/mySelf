package com.ttmv.datacenter.paycenter.triger.triggerimpl.triggerconstant;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 静态变量的值是随时改变的，线程通过判断
 * 标示的值来处理某些系统功能。
 * @author wll
 *
 */
public class Trigger {
	
	/**
	 * collection
	 */
	private static Hashtable<String,String> collection = new Hashtable<String,String>();
	private static Map<String,Object> collectionObject = new HashMap<String, Object>();
	private Map<String,Object> collectionTmpObject = null;
	
	/**
	 * 获取collection
	 * @return
	 */
	public static Hashtable<String,String> getCollection(){
		return collection;
	}
	
	public static Map<String, Object> getCollectionObject() {
		return collectionObject;
	}
	
	public void setCollectionTmpObject(Map<String, Object> collectionTmpObject) {
		Trigger.collectionObject = collectionTmpObject;
	}

	/**
	 * 添加值
	 */
	public static void setCollection(String name,String value){
		if(name != null && !"".equals(name) && value != null && !"".equals(value)){
			Trigger.collection.put(name, value);
		}
	}
}
