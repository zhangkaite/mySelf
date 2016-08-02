package com.ttmv.datacenter.da.storm.calcLevel.utils;
import com.ttmv.datacenter.da.storm.calcLevel.context.Constant;
import com.ttmv.datacenter.da.storm.calcLevel.service.LevelHbaseDataService;
import com.ttmv.datacenter.da.storm.calcLevel.utils.beans.LeverRules;
import com.ttmv.datacenter.da.storm.common.util.HbaseUtil;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.math.BigInteger;

/**
 * Created by zbs on 16/2/24.
 */
public class HbaseServiceUtil {

    private static Logger logger = LogManager.getLogger(HbaseServiceUtil.class);

    /**
     * 从HBASE中查询 N年N月 的积分
     * @param userId
     * @return
     * @throws Exception
     */
    public static String queryMonthIntegral(String userId,String type,String YYYYMM) throws Exception {
        Result starMonthIntegral = null;
        if(Constant.ANCHORFLAG.equals(type))
            starMonthIntegral = HbaseUtil.getOneDataByRowKey(Constant.HBASETABLE, userId, Constant.COLUMNFAMILY, Constant.COLUMNSTARTMONTH + YYYYMM);
        if(Constant.USERFLAG.equals(type))
            starMonthIntegral = HbaseUtil.getOneDataByRowKey(Constant.HBASETABLE, userId, Constant.COLUMNFAMILY, Constant.COLUMNUSERMONTH + YYYYMM);
        if(starMonthIntegral == null || starMonthIntegral.rawCells().length <= 0)
            return null;
        BigInteger bigInteger =  new BigInteger(CellUtil.cloneValue(starMonthIntegral.rawCells()[0]));
        if(bigInteger != null)
            return bigInteger.toString();
        return null;
    }

    /**
     * 从HBASE中查询 （主播，用户）的等级
     * @param userId
     * @return
     * @throws Exception
     */
    public static String queryLevel(String userId,String type) throws Exception{
        Result userLevelResult = null;
        if(Constant.ANCHORFLAG.equals(type))
            userLevelResult = HbaseUtil.getOneDataByRowKey(Constant.HBASETABLE, userId, Constant.COLUMNFAMILY, Constant.COLUMNSTARLEVEL);
        if(Constant.USERFLAG.equals(type))
            userLevelResult = HbaseUtil.getOneDataByRowKey(Constant.HBASETABLE, userId, Constant.COLUMNFAMILY, Constant.COLUMNUSERLEVEL);
        if(userLevelResult == null || userLevelResult.rawCells().length <= 0)
            return null;
        BigInteger bigInteger = new BigInteger(CellUtil.cloneValue(userLevelResult.rawCells()[0]));
        if (bigInteger == null)
            return null;
        return bigInteger.toString();
    }

    /**
     * 从HBASE中查询（主播，用户）的总积分
     * @param userId
     * @param type
     * @return
     */
    public static String queryAllScore(String userId,String type) throws Exception {
        Result scoreResult = null;
        //主播
        if(Constant.ANCHORFLAG.equals(type))
            scoreResult = HbaseUtil.getOneDataByRowKey(Constant.HBASETABLE, userId, Constant.COLUMNFAMILY, Constant.COLUMSTARTNALL);
        //用户
        if(Constant.USERFLAG.equals(type))
            scoreResult = HbaseUtil.getOneDataByRowKey(Constant.HBASETABLE, userId, Constant.COLUMNFAMILY, Constant.COLUMNUSERALL);
        if(scoreResult == null || scoreResult.rawCells().length <= 0)
            return null;
        BigInteger bigInteger = new BigInteger(CellUtil.cloneValue(scoreResult.rawCells()[0]));
        if(bigInteger == null)
            return null;
        return bigInteger.toString();
    }

    /**
     * 从HBASE中更新（主播，用户）的等级
     * @param userId
     * @param type
     * @return
     */
    public static void modifyLever(String userId,String type,Long lever) throws Exception {
        Result scoreResult = null;
        //主播
        if(Constant.ANCHORFLAG.equals(type))
            HbaseUtil.addRow(Constant.HBASETABLE, userId, Constant.COLUMNFAMILY, Constant.COLUMNSTARLEVEL, lever);
        //用户
        if(Constant.USERFLAG.equals(type))
            HbaseUtil.addRow(Constant.HBASETABLE, userId, Constant.COLUMNFAMILY, Constant.COLUMNUSERLEVEL, lever);
    }

    /**
     * 对HBASE中（主播，用户）的总积分进行扣减操作
     * @param userId
     * @param decrease
     * @return 扣减后的值
     * @throws Exception
     */
    public static long decreaseScore(String userId,String type,long decrease) throws Exception {
        long score = 0L;
        HTable hTable = HbaseUtil.getTable(Constant.HBASETABLE);
        try {
            logger.info("对HBASE中类型="+type+" 用户ID为 = "+userId+"的总积分，进行扣减"+decrease+"分操作。");
            if(Constant.ANCHORFLAG.equals(type))
                score = hTable.incrementColumnValue(Bytes.toBytes(userId), Bytes.toBytes(Constant.COLUMNFAMILY), Bytes.toBytes(Constant.COLUMSTARTNALL), decrease > 0 ? -decrease : decrease);
            if(Constant.USERFLAG.equals(type))
                score = hTable.incrementColumnValue(Bytes.toBytes(userId), Bytes.toBytes(Constant.COLUMNFAMILY), Bytes.toBytes(Constant.COLUMNUSERALL), decrease > 0 ? -decrease : decrease);
        }catch (Exception e){
            logger.error("对HBASE中userid = "+userId+"的总积分，进行扣减"+decrease+"分操作时出现异常：",e);
        }finally {
            if(hTable!=null)
                hTable.close();
        }
        return score;
    }

    /**
     * 从HBASE中更新（主播，用户）等级，通通过isPush参数控制是否对外推送等级变化
     * @param userID
     * @param userType
     * @param isPush
     * @throws Exception
     */
    public static void updateLevel(String userID,String userType,boolean isPush) throws Exception {
        //查询当前总经验值
        String allScore = queryAllScore(userID, userType);
        //查询该积分对应的等级应该是多少
        LeverRules leverRules = OcmsServiceUtil.getLeverByExp(userType, Long.valueOf(allScore));
        Long new_level = leverRules.getGrade();
        //查询Hbase中保存的等级
        String old_level = queryLevel(userID, userType);
        //查出的新积分为空，或者 新积分等于旧积分，不做处理
        if (new_level == null || Long.valueOf(old_level) == new_level)
            return;
        if (old_level == null || Long.valueOf(old_level) != new_level) {
            modifyLever(userID, userType, new_level);
            if (isPush) {
                LevelHbaseDataService.pushLevelDataToIm(new BigInteger(allScore),
                        BigInteger.valueOf(new_level),
                        new BigInteger(userID),
                        BigInteger.valueOf(leverRules.getSumExp()+leverRules.getUpgradeExp()),
                        userType);
            }
        }
    }
}
