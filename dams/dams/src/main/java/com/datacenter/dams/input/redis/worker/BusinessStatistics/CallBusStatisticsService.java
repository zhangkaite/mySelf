package com.datacenter.dams.input.redis.worker.BusinessStatistics;

/**
 * Created by zkt on 2016/3/29.
 */
public class CallBusStatisticsService {

    private static FirstRechargeStatistics firstRechargeStatistics;

    private static  FisterConsumeStatistics firstConsumeStatistics;


    public static FirstRechargeStatistics getFirstRechargeStatistics() {
        return firstRechargeStatistics;
    }

    public static void setFirstRechargeStatistics(FirstRechargeStatistics firstRechargeStatistics) {
        CallBusStatisticsService.firstRechargeStatistics = firstRechargeStatistics;
    }

    public static FisterConsumeStatistics getFirstConsumeStatistics() {
        return firstConsumeStatistics;
    }

    public static void setFirstConsumeStatistics(FisterConsumeStatistics firstConsumeStatistics) {
        CallBusStatisticsService.firstConsumeStatistics = firstConsumeStatistics;
    }
}
