package com.ttmv.monitoring.alerterService.tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

/**
 * Created by zbs on 15/10/22.
 */
public class GeneralUtility {

    private static Logger logger = LogManager.getLogger(GeneralUtility.class);

    /**
     * 通过反射调用对象
     * @param obj
     * @param methodName
     * @return
     */
    public static Object getValueByObject(Object obj,String methodName) throws Exception {
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName);
            return method.invoke(obj);
        }catch (Exception e){
            throw new Exception("通过反射调用对方方法时出现异常,通过反射调用类:"+obj.getClass().getSimpleName()+"中的"+methodName+"方法:");
        }
    }

    /**
     * 通过反射调用对象,返回空，不返回异常
     * @param obj
     * @param methodName
     * @return
     */
    public static Object getValueByObjectSimple(Object obj,String methodName) {
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName);
            if(method == null)
                return null;
            return method.invoke(obj);
        }catch (Exception e){
            logger.error("通过反射调用对方方法时出现异常,通过反射调用类:"+obj.getClass().getSimpleName()+"中的"+methodName+"方法:");
            return null;
        }
    }
}
