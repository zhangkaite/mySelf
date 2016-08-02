package com.ttmv.calLevel;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import com.ttmv.datacenter.da.storm.common.util.JsonUtil;

public class Test {
	
	public static void main(String[] args) throws Exception {
		//System.out.println("a"+System.currentTimeMillis());
		/*String object="[{star_8888_1456282557=220000},{user_9999_1456282557=110000}]";
		
		try {
			JSONArray jsonArray = new JSONArray(object);
			for (int i = 0; i < jsonArray.length(); i++) {
				Map dataMap = (Map) JsonUtil.getObjectFromJson(jsonArray.get(i).toString(), Map.class);
				Set keys = dataMap.keySet();
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					System.out.println("HbaeBolt 获取一级bolt的key值:" + key);
					BigDecimal value =new BigDecimal(dataMap.get(key).toString()) ;
					System.out.println("HbaeBolt 获取一级bolt的value值:" + value);
				}
			}
			
			//System.out.println(ServiceUtils.queryUserLevel("1000"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		BigDecimal s=new BigDecimal("7.4824E+7");
		System.out.println(s.toString());
		
	}

}
