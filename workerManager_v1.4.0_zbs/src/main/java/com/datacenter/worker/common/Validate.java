package com.datacenter.worker.common;

import java.util.Collection;

public class Validate {

    /**
     * 判断对象是否为空
     * @param obj
     * @return
     */
    public static boolean checkObject(Object obj) {
        return obj == null ? true : false;
    }

    /**
     * 校验字符串，判断是否 == null 或者 equest("")
     *
     * @param str
     * @return
     */
    public static boolean checkParameter(String str) {
        return str == null || "".equals(str) ? true : false;
    }
    /**
     * 校验Integer，判断是否 == 0
     *
     * @param integer
     * @return
     */
    public static boolean checkParameter(Integer integer) {
        return integer == 0 ? true : false;
    }

    /**
     * 判断集合是否为空，或者长度小于1
     * @param collection
     * @return
     */
    public static boolean checkParameter(Collection collection){
        return collection == null || collection.size()<1 ? true : false;
    }

}
