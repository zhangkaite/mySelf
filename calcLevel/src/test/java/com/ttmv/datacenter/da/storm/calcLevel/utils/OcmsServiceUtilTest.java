package com.ttmv.datacenter.da.storm.calcLevel.utils;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.calcLevel.rpc.LevelHttpRequest;
import com.ttmv.datacenter.da.storm.calcLevel.utils.beans.LeverRules;
import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by zbs on 16/2/25.
 */
public class OcmsServiceUtilTest {
    /**
     * 从ocms中获取该等级的规则
     * @return
     * @throws org.json.JSONException
     */
    @Test
    public void getLeverRules() throws Exception {
        LeverRules user_rules = OcmsServiceUtil.getLeverRules(Constant.USERFLAG, 10L);
        System.out.println(user_rules.toString());
        LeverRules star_rules = OcmsServiceUtil.getLeverRules(Constant.ANCHORFLAG, 10L);
        System.out.println(star_rules.toString());
    }

    /**
     * 从ocms中获取该该经验对应的等级
     *
     * @return
     * @throws org.json.JSONException
     */
    @Test
    public void getLeverByExp() throws Exception {
        LeverRules user_rules = OcmsServiceUtil.getLeverByExp(Constant.USERFLAG, 435454353454543253L);
        System.out.print(user_rules.toString());
        LeverRules star_rules = OcmsServiceUtil.getLeverByExp(Constant.ANCHORFLAG, 2222545453454522L);
        System.out.print(star_rules.toString());
    }
}
