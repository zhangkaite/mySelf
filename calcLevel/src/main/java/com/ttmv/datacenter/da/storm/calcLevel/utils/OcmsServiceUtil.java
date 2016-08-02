package com.ttmv.datacenter.da.storm.calcLevel.utils;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.calcLevel.rpc.LevelHttpRequest;
import com.ttmv.datacenter.da.storm.calcLevel.utils.beans.LeverRules;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zbs on 16/2/25.
 */
public class OcmsServiceUtil {

    /**
     * 从ocms中获取该等级的规则
     *
     * @param userLevel
     * @return
     * @throws org.json.JSONException
     */
    public static LeverRules getLeverRules(String type,Long userLevel) throws Exception {
        if(type == null || "".equals(type) || userLevel == null)
            throw new Exception("传入的调用ocms接口获取等级规则的参数有为空，请检查传入参数是否正确。");
        String expByLevel = LevelHttpRequest.getExpByLevel(type, userLevel);
        if(expByLevel == null)
            return null;
        JSONObject json = new JSONObject(expByLevel);
        JSONObject levelRules = null;
        try {levelRules =json.getJSONObject("resData");}catch (JSONException e){}
        if (levelRules == null) {
            throw new Exception("从ocms接口" + Constant.OCMSEXPBYLEVEL + "中获取的值为空，请验证是否是正确的情况。" + expByLevel);
        }
        LeverRules rules =  getLeverRules(levelRules);
        return rules;
    }

    /**
     * 从ocms中获取该该经验对应的等级
     *
     * @return
     * @throws org.json.JSONException
     */
    public static LeverRules getLeverByExp(String type,Long exp) throws Exception {
        if(type == null || "".equals(type) || exp == null)
            throw new Exception("传入的调用ocms接口获取该该经验对应的等级的参数有为空，请检查传入参数是否正确。");
        String levelString = LevelHttpRequest.getLevelByExp(type, exp);
        if(levelString == null)
            return null;
        JSONObject levelJson = null;
        try{levelJson = new JSONObject(levelString).getJSONObject("resData");}catch(JSONException e){}
        if (levelJson == null || levelJson.get("grade") == null) {
            throw new Exception("从ocms接口" + Constant.OCMSLEVELCONFIGLIST + "中获取的等级为空，请验证是否是正确的情况。");
        }
        LeverRules rules = getLeverRules(levelJson);
        return rules;
    }

    private static LeverRules getLeverRules(JSONObject levelRules) throws JSONException {
        LeverRules rules = new LeverRules();
        try{rules.setGradeType(levelRules.getString("gradeType"));} catch(JSONException e){}
        try{rules.setUpgradeExp(levelRules.getLong("upgradeExp"));} catch(JSONException e){}
        try{rules.setSumExp(levelRules.getLong("sumExp"));} catch(JSONException e){}
        try{rules.setGrade(levelRules.getLong("grade"));} catch(JSONException e){}
        try{rules.setDemotionRate(levelRules.getLong("demotionRate"));} catch(JSONException e){}
        try{rules.setLowestexp(levelRules.getLong("lowestexp"));} catch(JSONException e){}
        return rules;
    }
}
