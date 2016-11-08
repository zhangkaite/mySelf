package com.ttmv.datacenter.agent.redis.tool;

import java.util.Collection;

/**
 * Created by zbs on 15/11/11.
 */
public class CheckParameterUtil {

    /**
     * 批量全能校验
     * @param objs
     * @return
     */
    public static boolean checkIsEmptyBatch(Object ... objs){
        for(Object obj : objs){
            if(checkIsEmpty(obj)){
                return true;
            }
        }
        return false;
    }

    /**
     * 全能校验器，能校验 对象是否为空, string是否为"", int是否为0,Collection长度是否为0
     * @param obj
     * @return
     */
    public static boolean checkIsEmpty(Object obj){
        if(obj == null)
          return true;
        if(obj instanceof String && "".equals(obj))
            return true;
        if(obj instanceof Integer && 0 == (Integer)obj)
            return true;
        if(obj instanceof Collection && ((Collection) obj).size()<1)
            return false;
        return false;
    }

    /**
     * 字符验器，能校验 字符对象是否为空, string是否为""
     * @param str
     * @return
     */
    public static boolean checkStringIsEmpty(String str){
        if(str == null || "".equals(str))
            return true;
        return false;
    }
}
