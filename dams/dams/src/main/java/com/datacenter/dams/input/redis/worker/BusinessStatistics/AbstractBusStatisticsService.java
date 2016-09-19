package com.datacenter.dams.input.redis.worker.BusinessStatistics;

import com.datacenter.dams.input.redis.worker.handlerservice.CallActiveMQQueueService;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.input.redis.worker.util.HbaseUtil;
import com.datacenter.dams.util.JsonUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zkt on 2016/3/28.
 * 1、从hdfs上读取历史数据
 * 2、从hdfs上读取统计后的数据
 * 3、从统计后的数据剔除hdfs上读取的数据
 * 4、将最新的数据写入hdfs类似20160329格式的文件夹下面
 * 5、将最终的结果通过MQ推送给sdms
 * 6、将首充/首消详情信息写入hbase
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractBusStatisticsService {

    private static Logger logger = LogManager.getLogger(AbstractBusStatisticsService.class);
    private static Configuration config = HBaseConfiguration.create();
    public abstract void doBusStatistics() ;

    /**
     * 读取历史数据
     *
     * @param historyHdfsPath  读取hdfs文件路径
     * @return
     * @throws Exception
     */
    public List readHistoryHdfsData(String historyHdfsPath) throws Exception {
        List<String> ls = HadoopFSOperations.readHdfsDate(historyHdfsPath);
        return ls;
    }

    /***
     * 读取统计结果后的数据
     *
     * @param statisticHdfsPath  读取hdfs文件路径
     * @return
     * @throws Exception
     */
    public abstract Map readStatisticsHdfsData(String statisticHdfsPath) throws Exception;


    /***
     *
     * @param targetHdfsPath   写hdfs的目标路径
     * @param resultMap       要写的内容Map
     * @throws Exception
     */
    public void writeDataToHdfs(String targetHdfsPath, Map resultMap) throws Exception {
        //在写入之前如果hdfs已存在，则删除
        boolean isExit=HadoopFSOperations.isDirExit(targetHdfsPath);
        if (isExit){
            HadoopFSOperations.deleteHDFSFile(targetHdfsPath);
        }
        for (Object key : resultMap.keySet()) {
            String userID = (String) key;
            HadoopFSOperations.createNewHDFSFile(targetHdfsPath, userID);
            logger.debug("运营业务统计向hsdf" + targetHdfsPath + "添加内容userid:" + userID);
        }
    }


    /**
     *
     * @param queueName   activeMQ队列名称
     * @param resultData   统计结果Map数据
     * @param date         统计时间
     * @param num          可选参数
     * @throws Exception
     */
    public abstract  void sendDataToMQ(String queueName, Map resultData, String date, Integer num) throws Exception;

    /***
     *
     * @param queueName  队列名称
     * @param resultJson  要推送的json串
     * @throws Exception
     */
    public void SendActiveMqMessage(String queueName,String resultJson) throws Exception{
        CallActiveMQQueueService.getActiveMqQueueService().setActiveMqQueueName(queueName);
        CallActiveMQQueueService.getActiveMqQueueService().send(resultJson);
        //logger.info("运营业务统计向ActiveMq队列" + queueName + "推送的数据:" + JsonUtil.getObjectToJson(resultJson));
    }

    /**
     * key   userid+dataType
     * value  number+destinationUserID+time
     *
     * @param ls
     * @param dataType
     * @return
     * @throws Exception
     */
    public Map getHbaseStoreData(List<String> ls, String dataType) throws Exception {
        Map resultMap = new HashMap();
        for (String data : ls) {
            String[] datas = data.split("\\s+");
            String key = datas[0];
            String value = datas[1];
            String[] keys = key.split("_");
            if (dataType.equals(keys[1])) {
                resultMap.put(keys[0], value);
            }
        }
        return resultMap;

    }


    /**
     * 首充/首消数据写入hbase
     *
     * @param resultData  统计结果数据
     * @param date         统计时间
     * @param hbaseTableName  hbase表名称
     * @throws Exception
     */
    public void insertDataToHbase(Map resultData, String date, String hbaseTableName) throws Exception {
        boolean flag = HbaseUtil.isExitHbaseTable(config, hbaseTableName);
        if (!flag) {
            HbaseUtil.createTableNoOverwrite(config, hbaseTableName, "payCenter");
        }

        for (Object key : resultData.keySet()) {
            String rowkey = date + key;
            String value = String.valueOf(resultData.get(key));
            Map res = new HashMap();
            res.put("userID", key);
            res.put("number", value.split("_")[0]);
            res.put("destinationUserID", value.split("_")[1]);
            res.put("time", value.split("_")[2]);
            HbaseUtil.addRow(hbaseTableName, rowkey, "payCenter", "data", JsonUtil.getObjectToJson(res), config);
            logger.info("hbase添加表" + hbaseTableName + "数据成功，添加的数据：" + JsonUtil.getObjectToJson(res));
        }

    }


}
