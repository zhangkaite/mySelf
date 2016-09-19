package com.datacenter.dams.input.redis.worker.paycenterData2Hdfs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.DisallowConcurrentExecution;

import com.datacenter.dams.input.redis.worker.handlerservice.CallActiveMQQueueService;
import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.worker.worker.QuartzWorker;

/***
 * 将mr调度统计的结果 推送给sdms
 *
 * @author kate
 */
@SuppressWarnings({"rawtypes", "unchecked"})
// 设置work调度上次执行完成才执行下次work
@DisallowConcurrentExecution
public class StatisticResultDataToSdmsWorker extends QuartzWorker<Object> {

    private static Logger logger = LogManager.getLogger(StatisticResultDataToSdmsWorker.class);
    // 注入redis队列名称
    private String damsStatisticFinishQueue = ConsumeSpendConstant.DAMSSTATISTICFINISH;

    @Override
    protected List getData(int num) {
        logger.debug("从redis队列dams_statistic_finish获取财务统计mr调用的数据");
        List ls = null;
        if (num > 0) {
            try {
                ls = TbRedisService.getRedisQueueImpi().getValueBatch(damsStatisticFinishQueue, 1);
            } catch (Exception e) {
                logger.error("从redis批量获取payCenter四种类型数据信息信息失败，失败的原因是:", e);
            }
        }
        return ls;
    }

    @Override
    protected void process(Object obj) throws Exception {
        String data = (String) obj;
        JSONObject jsonObject = new JSONObject(data);
        String statisticDay = jsonObject.getString("statisticDay").substring(0, 10);
        String outputPath = jsonObject.getString("outputPath");
        logger.info("从redis队列dams_statistic_finish获取财务统计mr调用推送的数据statisticDay：" + statisticDay + "outputPath:" +
                outputPath);
        // Date dataTime = DateUtil.getDate(statisticDay);
        Map resultMap = readStatisticResultHdfs(outputPath, statisticDay);
        if (null != resultMap && resultMap.size() > 0) {
            resultMap.put("time", statisticDay);
            resultMap = getResultMap(resultMap);
        } else {
            resultMap.put("time", statisticDay);
            resultMap = getResultMap(resultMap);
            logger.error(statisticDay + "hdfs上没有数据，请查看hdfs是否有数据，如果有数据请查看yarn集群运行是否正常！");
        }
        String result = JsonUtil.getObjectToJson(resultMap);
        logger.info("财务统计向SDMS MQ传递的数据是：" + result);
        CallActiveMQQueueService.getActiveMqQueueService().setActiveMqQueueName(ConsumeSpendConstant
                .DAMSSTATISTICFINISH);
        CallActiveMQQueueService.getActiveMqQueueService().send(result);
    }

    public Map readStatisticResultHdfs(String hdfsPath, String statisticDay) {
        Map resultMap = new HashMap();
        try {
            List<String> dataList = HadoopFSOperations.readHdfsDate(hdfsPath);
            for (String data : dataList) {
                String[] datas = data.split("\\s+");
                resultMap.put(datas[0], datas[1]);
            }
            // resultMap.put("time", statisticDay);
        } catch (Exception e) {
            logger.error("财务统计读取路径下" + hdfsPath + "文件失败，失败的原因是:", e);
            return null;
        }
        return resultMap;

    }


    public Map getResultMap(Map resultMap) {
        if (!resultMap.containsKey("tq_recharge")) {
            resultMap.put("tq_recharge", "0");
        }
        if (!resultMap.containsKey("tb_recharge")) {
            resultMap.put("tb_recharge", "0");
        }
        if (!resultMap.containsKey("tb_consume")) {
            resultMap.put("tb_consume", "0");
        }

        if (!resultMap.containsKey("tq_consume")) {
            resultMap.put("tq_consume", "0");
        }

        if (!resultMap.containsKey("tb_consume_self")) {
            resultMap.put("tb_consume_self", "0");
        }
        if (!resultMap.containsKey("tq_consume_self")) {
            resultMap.put("tq_consume_self", "0");
        }

        if (!resultMap.containsKey("yj_consume")) {
            resultMap.put("yj_consume", "0");
        }
        if (!resultMap.containsKey("yj_recharge")) {
            resultMap.put("yj_recharge", "0");
        }
        return resultMap;
    }


    @Override
    protected String toLog(Object arg0) {
        return null;
    }

}
