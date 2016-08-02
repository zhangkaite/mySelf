package com.ttmv.datacenter.da.storm.calcLevel.rpc;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.common.util.HttpRequestPost;
import com.ttmv.datacenter.da.storm.common.util.JsonUtil;

public class LevelHttpRequest {

    private static Logger logger = Logger.getLogger(LevelHttpRequest.class);

    // 通过http请求获取点心、送花、tdou、在线时长经验值

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static String getLevelData() {
        HttpRequestPost httpRequestPost = new HttpRequestPost(Constant.OCMSURL);
        String responseData = null;
        Map reqMap = new HashMap();
        Map dataMap = new HashMap();
        reqMap.put("reqData", dataMap);
        String reqJsonData = null;
        try {
            reqJsonData = JsonUtil.getObjectToJson(reqMap);
        } catch (Exception e1) {
            logger.error("[[一级bolt]]向ocms请求对象转换失败，失败的原因是:" + e1);
        }
        String param = "data=" + reqJsonData;
        try {
            responseData = httpRequestPost.sendPost(param);
            logger.info("[[一级bolt]]http请求ocms等级计算规则信息:" + responseData);
        } catch (Exception e) {
            logger.error("[[一级bolt]]http请求ocms等级计算规则失败，失败的原因是：" , e);
        }
        return responseData;
    }

    /**
     * 根据等得该等级的配置情况
     *
     * @return
     */
    public static String getExpByLevel(String gradeType, Long grade) {
        HttpRequestPost httpRequestPost = new HttpRequestPost(Constant.OCMSEXPBYLEVEL);
        String responseData = null;
        Map reqMap = new HashMap();
        Map dataMap = new HashMap();
        dataMap.put("gradeType", gradeType);
        dataMap.put("grade", grade);
        reqMap.put("reqData", dataMap);
        String reqJsonData = null;
        try {
            reqJsonData = JsonUtil.getObjectToJson(reqMap);
            String param = "data=" + reqJsonData;
            responseData = httpRequestPost.sendPost(param);
            logger.info("http请求ocms" + grade + "级的计算规则信息:" + responseData);
        } catch (Exception e) {
            logger.error("http请求ocms" + Constant.OCMSEXPBYLEVEL + "接口失败，失败的原因是：", e);
        }
        return responseData;
    }


    /**
     * 根据等得该等级的配置情况
     *
     * @return
     */
    public static String getLevelByExp(String gradeType, Long sumExp) {
        HttpRequestPost httpRequestPost = new HttpRequestPost(Constant.OCMSLEVELCONFIGLIST);
        String responseData = null;
        Map reqMap = new HashMap();
        Map dataMap = new HashMap();
        dataMap.put("gradeType", gradeType);
        dataMap.put("sumExp", sumExp);
        reqMap.put("reqData", dataMap);
        String reqJsonData = null;
        try {
            reqJsonData = JsonUtil.getObjectToJson(reqMap);
            String param = "data=" + reqJsonData;
            responseData = httpRequestPost.sendPost(param);
            logger.info("http请求ocms总积分为" + sumExp + "的等级信息:" + responseData);
        } catch (Exception e) {
            logger.error("http请求ocms" + Constant.OCMSLEVELCONFIGLIST + "接口失败，失败的原因是：", e);
        }
        return responseData;
    }

}
