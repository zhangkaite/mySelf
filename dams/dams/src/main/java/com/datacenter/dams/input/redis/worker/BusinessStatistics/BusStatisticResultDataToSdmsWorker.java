package com.datacenter.dams.input.redis.worker.BusinessStatistics;

import com.datacenter.dams.input.redis.worker.handlerservice.TbRedisService;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.worker.worker.QuartzWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.quartz.DisallowConcurrentExecution;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 轮询运营统计mr统计结束后的redis消息队列，获取mr计算完成后的输出路径
 */
@SuppressWarnings({"rawtypes", "unchecked"})
// 设置work调度上次执行完成才执行下次work
@DisallowConcurrentExecution
public class BusStatisticResultDataToSdmsWorker extends QuartzWorker<Object> {

    private static Logger logger = LogManager.getLogger(BusStatisticResultDataToSdmsWorker.class);
    // 注入redis队列名称
    private String damsBusStatisticFinishQueue = ConsumeSpendConstant.DAMS_BUS_STATISTICS_FINISH;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    protected List getData(int num) {
        logger.debug("从redis队列dams_bus_statistic_finish获取运营业务统计mr调用的数据");
        List ls = null;
        if (num > 0) {
            try {
                ls = TbRedisService.getRedisQueueImpi().getValueBatch(damsBusStatisticFinishQueue, 1);
            } catch (Exception e) {
                logger.error("从redis队列dams_bus_statistic_finish获取运营业务统计数据失败，失败的原因是:", e);
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
        logger.info("从redis队列dams_bus_statistic_finish获取运营首充/首消业务统计mr调用推送的数据statisticDay：" + statisticDay + "outputPath:" +
                outputPath);
        statisticsSc(outputPath, statisticDay);
        statisticsSx(outputPath, statisticDay);


    }


    public void statisticsSc(String outputPath, String statisticDay) {
        //统计首充

        String rechargeHistoryPath = ConsumeSpendConstant.BUSSTATISTICSSCHISTORYHDFSPATH + sdf.format(DateUtil
                .getQueryFixedTime(DateUtil.getDate(statisticDay), 1, -1)) + File.separator + sdf.format(DateUtil
                .getQueryFixedTime(DateUtil.getDate(statisticDay), 1, -1));
        String rechargeTargetHdfsPath = ConsumeSpendConstant.BUSSTATISTICSSCHISTORYHDFSPATH + sdf.format(DateUtil
                .getDate(statisticDay)) + File.separator + sdf.format(DateUtil
                .getDate(statisticDay));
        CallBusStatisticsService.getFirstRechargeStatistics().setParams(rechargeHistoryPath, outputPath,
                rechargeTargetHdfsPath, ConsumeSpendConstant.SDMS_ACTIVEMQ_FISTER_ALL_QUEUE, statisticDay,
                ConsumeSpendConstant.BUSSTATISTICSHBASESCTABLENAME);
        logger.info("运营首充业务统计参数rechargeHistoryPath:" + rechargeHistoryPath + ";outputPath:" + outputPath + ";" +
                "rechargeTargetHdfsPath:" + rechargeTargetHdfsPath + ";SDMS_FISTERRECHARGE_QUEUE:" +
                ConsumeSpendConstant.SDMS_ACTIVEMQ_FISTER_ALL_QUEUE + ";statisticDay:" + statisticDay);
        CallBusStatisticsService.getFirstRechargeStatistics().doBusStatistics();

    }


    public void statisticsSx(String outputPath, String statisticDay) {

        //统计首消
        String consumeHistoryPath = ConsumeSpendConstant.BUSSTATISTICSSXHISTORYHDFSPATH + sdf.format(DateUtil
                .getQueryFixedTime(DateUtil.getDate(statisticDay), 1, -1)) + File.separator + sdf.format(DateUtil
                .getQueryFixedTime(DateUtil.getDate(statisticDay), 1, -1));
        String consumeTargetHdfsPath = ConsumeSpendConstant.BUSSTATISTICSSXHISTORYHDFSPATH + sdf.format(DateUtil
                .getDate(statisticDay)) + File.separator + sdf.format(DateUtil
                .getDate(statisticDay));
        CallBusStatisticsService.getFirstConsumeStatistics().setParams(consumeHistoryPath, outputPath,
                consumeTargetHdfsPath, ConsumeSpendConstant.SDMS_ACTIVEMQ_FISTER_ALL_QUEUE, statisticDay,
                ConsumeSpendConstant.BUSSTATISTICSHBASESXTABLENAME);
        logger.info("运营首消业务统计参数consumeHistoryPath:" + consumeHistoryPath + ";outputPath:" + outputPath + ";" +
                "consumeTargetHdfsPath:" + consumeTargetHdfsPath + ";SDMS_FISTERCONSUME_QUEUE:" +
                ConsumeSpendConstant.SDMS_ACTIVEMQ_FISTER_ALL_QUEUE + ";statisticDay:" + statisticDay);
        CallBusStatisticsService.getFirstConsumeStatistics().doBusStatistics();
    }


    @Override
    protected String toLog(Object arg0) {
        return null;
    }

}
