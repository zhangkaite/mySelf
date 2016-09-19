package com.ttmv.datacenter.usercenter.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.implement.util.JsonUtil;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserContributeDao;
import com.ttmv.datacenter.usercenter.dao.interfaces.UserInfoDao;
import com.ttmv.datacenter.usercenter.domain.data.UserContribution;
import com.ttmv.datacenter.usercenter.domain.data.UserInfo;
import com.ttmv.datacenter.usercenter.util.DataUtil;

/**
 * TT房间七日排行榜调度统计
 *
 * @author zkt
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class SevenRanking {
    private final Logger log = LogManager.getLogger(SevenRanking.class);
    // 注入用户消费dao
    private UserContributeDao userContributeDao;
    // 注入用户信息查询dao
    private UserInfoDao userInfoDao;

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public UserContributeDao getUserContributeDao() {
        return userContributeDao;
    }

    public void setUserContributeDao(UserContributeDao userContributeDao) {
        this.userContributeDao = userContributeDao;
    }

    private Configuration conf = null;
    // hbase表名
    private static final String HBASEOPERATIONINFO_TABLENAME = "HbaseOperationInfo";
    // 查询排行榜天数
    private final int NUM = 7;

    // 初始化获取hbase配置信息
    SevenRanking() {
        conf = HBaseConfiguration.create();
    }

    /***
     * 定时调度要执行的方法 1、将七天的数据拆分成七天查询，每天查询
     */
    public void doStatistics() {
        long start_time = System.currentTimeMillis();
        List listMap = new ArrayList();
        for (int i = 0; i < NUM; i++) {
            try {
                String[] startEndTime =DataUtil.getStartAndEndTime(DataUtil.getIntervalData(new Date(), i));
                
                try {
                    Map resMap = scanByStartAndStopRow(HBASEOPERATIONINFO_TABLENAME, startEndTime[0], startEndTime[1]);
                    if (null != resMap && resMap.size() > 0) {
                        listMap.add(resMap);
                    }
                } catch (Exception e) {
                    log.error("hbase数据检索失败，失败的原因是:", e);
                }
            } catch (Exception e) {
                log.error("获取指定时间的起始、结束时间戳失败，失败的原因是:", e);
            }

        }
        Map map = mergeMap(listMap);
        Set rom_keys = map.keySet();
        for (Iterator iterator = rom_keys.iterator(); iterator.hasNext(); ) {
            String rom_key = (String) iterator.next();
            Map user_maps = (Map) map.get(rom_key);
            Set user_keys = user_maps.keySet();
            for (Iterator iterator2 = user_keys.iterator(); iterator2.hasNext(); ) {
                String user_key = String.valueOf(iterator2.next());
                String user_num = String.valueOf(user_maps.get(user_key));
                UserContribution uContribution = new UserContribution();
                uContribution.setUserId(new BigInteger(user_key));
                UserInfo userInfo = null;
                try {
                    userInfo = userInfoDao.selectUserInfoByUserId(new BigInteger(user_key));

                } catch (Exception e) {
                    log.error("用户资料查询失败，失败的原因是：", e);
                }

                uContribution.setNickName(userInfo.getNickName());
                uContribution.setRoomId(new BigInteger(rom_key));
                uContribution.setContributionSum(new BigDecimal(user_num));
                uContribution.setUserPhoto(userInfo.getUserPhoto());
                uContribution.setDataType("0");
                try {
                	Integer tag = userContributeDao.updateUserContribution(uContribution);
                    log.debug("Mysql调用返回" + tag);
                } catch (Exception e) {
                    log.error("排行榜mysql更新失败，失败的原因是：", e);
                }
            }

        }
        long end_time = System.currentTimeMillis();
        log.info("总共花费的时间:" + (end_time - start_time));
    }

    /**
     * 根据起始时间戳、结束时间戳、hbase表名称获取所有的数据
     *
     * @param tableName
     * @param startRow
     * @param stopRow
     * @throws Exception
     */
    public Map scanByStartAndStopRow(String tableName, String startRow, String stopRow) throws Exception {

        HTable table = new HTable(conf, tableName);
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (admin.tableExists(tableName)) {
            // table.setScannerCaching(10000);
            Scan scan = new Scan();
            // 通过配置一次拉去的较大的数据量可以减少客户端获取数据的时间，但是它会占用客户端内存
            scan.setCaching(10000);
            scan.setStartRow(Bytes.toBytes(startRow));
            scan.setStopRow(Bytes.toBytes(stopRow));
            ResultScanner rs = table.getScanner(scan);
            List<BillInfoEntity> ls = new ArrayList<BillInfoEntity>();
            for (Result r : rs) {
                BillInfoEntity bEntity = printRecoder(r);
                // 只填加消费类型和房间号不为空的数据
                if ("-1".equals(bEntity.getType()) && null != bEntity.getRoomID()) {
                    ls.add(bEntity);
                }
            }
            // 释放资源
            table.close();
            // 对获取的数据进行统计
            Map resultMap = getRomConSumerInfo(ls);

            return resultMap;
        }
        return null;
    }

    /**
     * 将查询出的结果json串转换成对象
     *
     * @param result
     * @return
     * @throws Exception
     */
    public BillInfoEntity printRecoder(Result result) throws Exception {
        BillInfoEntity billInfoEntity = null;
        for (Cell cell : result.rawCells()) {
            String info = new String(CellUtil.cloneValue(cell));
            billInfoEntity = (BillInfoEntity) JsonUtil.getObjectFromJson(info, BillInfoEntity.class);
        }
        return billInfoEntity;
    }

    /**
     * 根据list数组获取当天所有房间每个人消费总数 1、删选符合条件的数据 2、将所有的数据按照房间号分类 3、将同一房间号 同一用户的消费数据求和
     * 消费数据区分t券和t币
     *
     * @param ls
     * @return
     */

    public Map getRomConSumerInfo(List<BillInfoEntity> ls) {
        Map resultMap = new HashedMap();
        for (Iterator iterator = ls.iterator(); iterator.hasNext(); ) {
            BillInfoEntity billInfoEntity = (BillInfoEntity) iterator.next();
            String rom_id = billInfoEntity.getRoomID();
            // 如果房间号为空，则说明该数据不是房间T币消费数据
            if (null == rom_id || "".equals(rom_id)) {
                continue;
            }
            String user_id = String.valueOf(billInfoEntity.getUserId());
            BigDecimal trade_num = billInfoEntity.getNumber();
            // 判断当前消费类型是t币还是t券，t币乘以1000
            if ("0".equals(billInfoEntity.getAccountType())) {
                trade_num = trade_num.multiply(new BigDecimal(1000));
            }
            // 如果map中包含当前的房间号
            if (resultMap.containsKey(rom_id)) {
                // 获取当前房间号的所有人员消费数据
                HashedMap consumMap = (HashedMap) resultMap.get(rom_id);
                if (consumMap.containsKey(user_id)) {
                    BigDecimal current_trade_num = (BigDecimal) consumMap.get(user_id);
                    BigDecimal new_trade_num = current_trade_num.add(trade_num);
                    Map currUserTradeMap = new HashedMap();
                    currUserTradeMap.put(user_id, new_trade_num);
                    resultMap.put(rom_id, currUserTradeMap);
                } else {
                    Map currUserTradeMap = new HashedMap();
                    currUserTradeMap.put(user_id, trade_num);
                    resultMap.put(rom_id, currUserTradeMap);
                }

            } else {
                Map currUserTradeMap = new HashedMap();
                currUserTradeMap.put(user_id, trade_num);
                resultMap.put(rom_id, currUserTradeMap);
            }

        }
        return resultMap;
    }

    /**
     * 对查询的七天的map进行合并，相同房间号、用户id相同的数据进行叠加求和
     *
     * @param listMap
     * @return
     */
    public Map mergeMap(List<Map> listMap) {
        Map resultMap = new HashedMap();
        for (Map map : listMap) {
            for (Object key : map.keySet()) {
                // 获取
                if (resultMap.containsKey(key)) {
                    // 获取当前房间刷礼物的数据
                    Map currentMap = (Map) map.get(key);
                    // 获取map结果中相同房间号的所有刷礼物的人和金额
                    Map resultMapValue = (Map) resultMap.get(key);
                    for (Object currkey : currentMap.keySet()) {
                        if (resultMapValue.containsKey(currkey)) {
                            BigDecimal resultValueConsumNum = (BigDecimal) resultMapValue.get(currkey);
                            BigDecimal currentMapConsumNum = (BigDecimal) currentMap.get(currkey);
                            Map nowMap = new HashedMap();
                            nowMap.put(currkey, resultValueConsumNum.add(currentMapConsumNum));
                            resultMap.put(key, nowMap);
                        } else {
                            resultMapValue.put(currkey, currentMap.get(currkey));
                        }
                    }
                } else {
                    resultMap.put(key, map.get(key));
                }
            }
        }
        return resultMap;
    }
}
