package com.ttmv.datacenter.usercenter.dao.implement.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2015年2月3日 下午5:47:29  
 * @explain :TTnum号转换工具
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GoodTTnumUtil {
	
	/**
	 * 把靓号串转成map
	 * @param str
	 * @return
	 */
	public static Map getMap(String str){
		String[] arrs = str.split(";");
		Map map = new HashMap();
		for(int i = 0; i<arrs.length ;i++){
			String[] key = arrs[i].split(",");
			map.put(key[0], arrs[i]);
		}
		return map;
	}
	
	/**
	 * 靓号对象转成靓号串
	 * @param m
	 * @return
	 */
	public static String getStr(Map map){
		StringBuffer buffer = new StringBuffer();
		Set<String> keys = map.keySet();
	    for (Iterator it = keys.iterator(); it.hasNext();) {            
		   String key = (String) it.next();  
		   buffer.append(map.get(key)+";");
		}
		return buffer.toString();
	}
	
	/**
	 * 修改靓号状态
	 * @param args
	 */
	public static Map changeTTnum(String oldTTstr,String TTnum,String state){
		Map goodTT = GoodTTnumUtil.getMap(oldTTstr);
		Set<String> keys = goodTT.keySet();
	    for (Iterator it = keys.iterator(); it.hasNext();) {            
		   String key = (String) it.next();  
		   if(key.equals(TTnum)){
			   goodTT.put(TTnum, TTnum+","+state);
		   }
		}
		return goodTT;
	}
	
	/**
	 * 删除靓号
	 * @param args
	 */
	public static Map deleteTTnum(String oldTTstr,String TTnum){
		Map goodTT = GoodTTnumUtil.getMap(oldTTstr);
		Set<String> keys = goodTT.keySet();
	    for (Iterator it = keys.iterator(); it.hasNext();) {            
		   String key = (String) it.next();  
		   if(key.equals(TTnum)){
			   goodTT.remove(it);
		   }
		}
		return goodTT;
	}
	
	/**
	 * 购买靓号
	 * @param 购买前的
	 * @param newTT
	 * @return
	 */
	public static String addGoodTTnum(String oldTTstr,String newTT)throws Exception{
		Map old = GoodTTnumUtil.getMap(oldTTstr);
		if(old.size() < 5){
			old.put(newTT, newTT+","+"0");
		}else{
			throw new Exception("购买的靓号已经超过5个，不能在购买！");
		}
		return GoodTTnumUtil.getStr(old);
	}
}
