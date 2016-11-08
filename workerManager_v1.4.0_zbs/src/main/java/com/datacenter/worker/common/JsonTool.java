package com.datacenter.worker.common;

import net.sf.json.JSONObject;

import java.util.Map;

public class JsonTool {

    /**
     * 字符串转换为map
     * @param str
     * @return
     */
    public static Map<String,String> getMapByString(String str){
        if(Validate.checkParameter(str))
            return null;
        return getMapByObject(str);
    }

    /**
     * 对象转换为Map
     * @param obj
     * @return
     */
    public static Map<String,String> getMapByObject(Object obj){
        if(obj == null)
            return null;
        JSONObject jsonObject = JSONObject.fromObject(obj);
        Map<String,String> map = (Map<String,String>)JSONObject.toBean(jsonObject, Map.class);
        return map;
    }
}
