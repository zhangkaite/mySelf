package com.ttmv.datacenter.da.storm.calcLevel.utils;

import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;

/**
 * Created by zbs on 16/2/25.
 */
public class HbaseServiceUtilTest {

    /**
     * 从HBASE中查询 N年N月 的积分
     * @return
     * @throws Exception
     */
    @Test
    public void queryMonthIntegralTest() throws Exception {
        System.out.println(HbaseServiceUtil.queryMonthIntegral("9", Constant.USERFLAG, "201512"));
        System.out.println(HbaseServiceUtil.queryMonthIntegral("84", Constant.ANCHORFLAG, "201601"));
    }

    /**
     * 从HBASE中查询 （主播，用户）的等级
     * @return
     * @throws Exception
     */
    @Test
    public void queryLevelTest() throws Exception{
        System.out.println(HbaseServiceUtil.queryLevel("9",Constant.USERFLAG));
        System.out.println(HbaseServiceUtil.queryLevel("84",Constant.ANCHORFLAG));
    }

    /**
     * 从HBASE中查询（主播，用户）的总积分
     * @return
     */
    @Test
    public void queryAllScoreTest() throws Exception {
        System.out.println(HbaseServiceUtil.queryAllScore("9",Constant.USERFLAG));
        System.out.println(HbaseServiceUtil.queryAllScore("84",Constant.ANCHORFLAG));
    }

    /**
     * 从HBASE中更新（主播，用户）的等级
     * @return
     */
    @Test
    public void modifyLeverTest() throws Exception {
        HbaseServiceUtil.modifyLever("9", Constant.USERFLAG, 10L);
        HbaseServiceUtil.modifyLever("84", Constant.ANCHORFLAG, 20L);
        Assert.assertEquals("10", HbaseServiceUtil.queryLevel("9", Constant.USERFLAG));
        Assert.assertEquals("20", HbaseServiceUtil.queryLevel("84", Constant.ANCHORFLAG));
    }

    /**
     * 对HBASE中（主播，用户）的总积分进行扣减操作
     * @return
     * @throws Exception
     */
    @Test
    public  void decreaseScoreTest() throws Exception {
        String userAll =HbaseServiceUtil.queryAllScore("9",Constant.USERFLAG);
        String starAll =HbaseServiceUtil.queryAllScore("84", Constant.ANCHORFLAG);
        HbaseServiceUtil.decreaseScore("9", Constant.USERFLAG, -1L);
        HbaseServiceUtil.decreaseScore("84", Constant.ANCHORFLAG, -1L);
        String new_userAll =HbaseServiceUtil.queryAllScore("9", Constant.USERFLAG);
        String new_starAll =HbaseServiceUtil.queryAllScore("84", Constant.ANCHORFLAG);
        Assert.assertEquals(new_userAll, String.valueOf(Long.valueOf(userAll)-1));
        Assert.assertEquals(new_starAll, String.valueOf(Long.valueOf(starAll)-1));
    }

    /**
     * 从HBASE中更新（主播，用户）等级，通通过isPush参数控制是否对外推送等级变化
     * @throws Exception
     */
    @Test
    public  void updateLevelTest() throws Exception {
        //TODO 还没有测试，没数据
        System.out.println(HbaseServiceUtil.queryAllScore("5", Constant.USERFLAG));
        System.out.println(HbaseServiceUtil.queryAllScore("7", Constant.ANCHORFLAG));
        //扣减分值
        HbaseServiceUtil.decreaseScore("5", Constant.USERFLAG, -30000L);
        HbaseServiceUtil.decreaseScore("7", Constant.ANCHORFLAG, -1L);
        //测试重算等级
        HbaseServiceUtil.updateLevel("5",Constant.USERFLAG,true);
        HbaseServiceUtil.updateLevel("7",Constant.ANCHORFLAG,true);
        System.out.println(HbaseServiceUtil.queryLevel("5", Constant.USERFLAG));
        System.out.println(HbaseServiceUtil.queryLevel("7",Constant.ANCHORFLAG));
    }
}