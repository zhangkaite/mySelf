package com.ttmv.datacenter.sentry.tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

/**
 * Created by zbs on 15/10/21.
 */
public class CheckData {

    private static Logger logger = LogManager.getLogger(CheckData.class);

    public static void isEmptyByBean(Object bean) throws Exception {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("getClass")) {// 如果方法名以get开头
                Object value = method.invoke(bean);// 调用方法,并打印返回值
                if (value == null || (value instanceof String && "".equals(value)) || (value instanceof Integer && (Integer)value == 0)) {
                    throw new Exception(bean.getClass().getSimpleName() + "对象中" + methodName + "方法返回值为空。");
                }
            }
        }
    }

    public static void isEmptyByMap(Map<String,Object> reqMap) throws Exception {
        for(Map.Entry<String, Object> set :reqMap.entrySet()) {
            String key = set.getKey();
            Object object = set.getValue();
            //如果校验白名单中没有说明，表示需要验证
            if (object == null || (object instanceof String && "".equals(object)) || (object instanceof Integer && (Integer)object == 0) ) {//IP非空验证
                throw new DataFormatException("数据校验失败:["+ key + "] is null or zero...]");
            }
        }
    }
}
