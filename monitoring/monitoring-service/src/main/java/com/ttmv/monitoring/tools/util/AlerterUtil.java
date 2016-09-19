package com.ttmv.monitoring.tools.util;

/**
 * Created by zbs on 15/9/27.
 */
public class AlerterUtil {

    /**
     * 判断一个值是否在一个区间
     *
     * @param mixValue
     * @param maxValue
     * @param value
     * @return
     */
    public static boolean isExtent(String mixValue, String maxValue, int value) {
        int max = Integer.valueOf(maxValue);
        int min = Integer.valueOf(mixValue);
        if (min <= value && value <= max) {
            return true;
        }
        return false;
    }

    /**
     * 把数据库中的string 数组转为int[] 数组
     *
     * @param str
     * @param sep
     * @return
     * @throws Exception
     */
    public static int[] getIntArrayByStr(String str, String sep) throws Exception {
        if (str == null || sep == null || "".equals(str) || "".equals(sep))
            return null;
        String[] strings = str.split(sep);
        if (strings == null || strings.length < 1)
            throw new Exception("需要转义的String数组为空或长度小于1");
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.valueOf(strings[i]);
        }
        return ints;
    }

    /**
     * 首字母转大写
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
