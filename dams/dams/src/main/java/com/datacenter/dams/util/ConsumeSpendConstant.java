package com.datacenter.dams.util;

public interface ConsumeSpendConstant {

    /* TB消费,支付中心一次获取消息的数量 */
    Integer TCOIN_CONSUME_SPEND_GET_MESSAGE = 2;
    Integer TCOIN_CONSUME_SPEND_GET_MESSAGE_TIMEOUT = 1000;
    /* 吊牌活动开关 */
    boolean ACTIVITY_FLAG = true;

    /* 用户和主播登陆类型 */
    String USER_IN = "in";
    String USER_OUT = "out";
    String STAR_UP = "up";
    String STAR_DOWN = "down";
    /* 增长经验的时间单位 */
    Integer USER_STEP = 1;
    Integer STAR_STEP = 1;

    String REDISHDFSQUEUE = "dams_spending_hdfs_que";

    String REDISMRWORKINGQUEUE = "dams_mr_working_que";

    String REDISMRFINISHEDQUEUE = "dams_mr_finished_que";
    String REDISCONSUMEQUEUE="dams_consume_daychange_que";
    String REDISTBRECHARGEQUEUE = "da_tb_recharge_que";
    String REDISTQRECHARGEQUEUE = "da_tq_recharge_que";
    String ACTIVEMQTBTQQUEUE = "sdms_mq_tbtq_info";
    //SDMS_RechConsBus_Queue
    String RECHCONSREDISQUEUE="SDMS_RechConsBus_Queue";

   /**
    * 用户登录记录原始记录数据存储
    */
    String HDFSUSERLOGIN = "/datacenter/usercenter/login/input_";
    
    /**
     * 用户注册记录原始记录数据存储
     */
     String HDFSADDUSER = "/datacenter/usercenter/register/input_";
     
    /**
     * 消费原始数据存储hdfs路径
     */
    String HDFSSPENDINGPATH = "/datacenter/payment/spending/spend_";

    /**
     * 日榜富豪榜统计结果数据存储路径
     */
    String STATISTICUSERDAYHBASEPATH = "/datacenter/payment/score/end_day_score_";

    /**
     * 日榜明星榜统计结果数据存储路径
     */
    String STATISTICSTARDAYHBASEPATH = "/datacenter/payment/score/end_day_star_score_";

    /**
     * 周榜统计结果数据存储路径
     */

    String STATISTICUSERWEEKHBASEPATH = "/datacenter/payment/score/preweek_score_";
    String STATISTICSTARWEEKHBASEPATH = "/datacenter/payment/score/preweek_start_score_";

    /**
     * 月榜统计结果数据存储路径
     */
    String STATISTICUSERMONTHHBASEPATH = "/datacenter/payment/score/premonth_score_";
    String STATISTICSTARMONTHHBASEPATH = "/datacenter/payment/score/premonth_start_score_";

    /***
     * 总榜统计结果数据存储路径
     */

    String STATISTICUSERALLHBASEPATH = "/datacenter/payment/score/preall_score_";
    String STATISTICSTARALLHBASEPATH = "/datacenter/payment/score/preall_start_score_";

    /**
     * 调用的mr commond
     */

    String MAPREDUCECOMMOND = "hadoop jar ";

    /**
     * 调用mr的jar 路径
     */
    String MAPREDUCEPATH = "/opt/countSpending.jar ";

    /***
     * 调用财务统计jar
     */
    String STATISTICSJAR="/opt/financialStatistics.jar ";


    /***
     * 调用运营首充、首消统计jar
     */
    String BUSSTATISTICSJAR="/opt/BusStatistics.jar ";

    
    /***
     * 调用用户注册统计jar
     */
    String USERREGISTERJAR="/opt/UserRegisterStatistics.jar ";

    /***
     * 调用运营用户登录jar
     */

    String USERLOGINJAR="/opt/UserLoginStatistics.jar ";





    /***
     * mr计算阶段标识
     */

    String MRDAYTYPE = "day";
    String MRWEEKTYPE = "week";
    String MRMONTH = "month";
    String MRALL = "all";

    /**
     * mr业务标识日榜计算 富豪榜
     */
    String DAYSTATISTIC = "countSpending";
    /*
     * ** 明星榜
     */
    String DAYSTARSTATISTIC = "countStarSpending";

    /***
     * mr业务周、月、总标识
     */
    String OTHERSTATISTIC = "countScore";

    /**
     * TQ消费调用的jar包
     */

    String TQSTATISTIC = "";

    /**
     * 佣金消费统计调用的jar包
     */

    String BROSTATISTIC = "";

    /**
     * mr执行的时常
     */

    int MRRUNTIME = 20;

    // columnFamily
    String HBASECOLUMNFAMILY = "a";

    // cloumn

    String HBASECLOUMN = "score";

    // hbase富豪榜临时表
    String HBASEUSERWEEKTABLETEMP = "preweek_score_current_";
    // hbase明星榜临时榜

    String HBASESTARWEEKTABLETEMP = "preweek_star_score_current_";

    String HBASEUSERMONTHTABLETEMP = "premonth_score_current_";
    // hbase明星榜临时榜

    String HBASESTARMONTHTABLETEMP = "premonth_star_score_current_";

    String HBASEUSERALLTABLETEMP = "preall_score_current_";
    // hbase明星榜临时榜

    String HBASESTARALLTABLETEMP = "preall_star_score_current_";

    // hbase富豪榜临时表
    String HBASEUSERWEEKTABLE = "da_rankspending_preweek_";
    // hbase明星榜临时榜

    String HBASESTARWEEKTABLE = "da_rankspending_star_preweek_";

    String HBASEUSERMONTHTABLE = "da_rankspending_premonth_";
    // hbase明星榜临时榜

    String HBASESTARMONTHTABLE = "da_rankspending_star_premonth_";

    String HBASEUSERALLTABLE = "da_rankspending_preall_";
    // hbase明星榜临时榜

    String HBASESTARALLTABLE = "da_rankspending_star_preall_";

    String HADOOPMASTERHOST = "hadoopMasterHost";
    //上线需要修改
    String HADOOPMASTERUSERNAME = "root";
    String HADOOPMASTERPASSWORD = "dc2016";

    // 递归查询总榜统计已存在的hdfs路径，最大寻址
    int HADOOPMAXNUMBER = 60;

    // 用户充值数据写入hdfs路径地址
    String HDFSTBRECHARGEPATH = "/datacenter/payment/recharge/tb/recharge_";
    String HDFSTQRECHARGEPATH = "/datacenter/payment/recharge/tq/recharge_";

    String TBCONSUME = "TBCONSUME";
    String TBRECHARGE = "TBRECHARGE";
    String TQCONSUME = "TQCONSUME";
    String TQRECHARGE = "TQRECHARGE";



    //运营统计首充、首消
    String PAYCENTERALLDATAQUE="da_paycenter_alldata_que";
    String PAYCENTERALLDATA="/datacenter/paycenter/all/allinput_";
    String PAYCENTERALLOUTPUTDATA="/datacenter/paycenter/all/allout_";
    String DAMSSTATISTICFINISH="SDMS_FinanceClipboard_Queue";
    String DAMS_BUS_STATISTICS_FINISH="DAMS_BUS_STATISTICS_FINISH";

    String SDMS_COUNTCONSUME_QUEUE="SDMS_CountConsume_Queue";
    String BUSSTATISTICSSCHISTORYHDFSPATH="/datacenter/paycenter/statistics/sc_history_";
    String BUSSTATISTICSSXHISTORYHDFSPATH="/datacenter/paycenter/statistics/sx_history_";
    String BUSSTATISTICSRESULTHDFSPATH="/datacenter/paycenter/statistics/result_";
    String SDMS_ACTIVEMQ_FISTER_ALL_QUEUE="SDMS_FisterRechargeConsume_Queue";
    //tb消费人数统计
    String SDMS_ACTIVEMQ_TBCONSUME_SIZE_QUEUE="SDMS_TBConsume_Size_Queue";

    String FISTERRECHARGE="recharge";
    String FISTERCONSUME="consume";
    String BUSSTATISTICSHBASESXTABLENAME="dams_statistics_sx";
    String BUSSTATISTICSHBASESCTABLENAME="dams_statistics_sc";

    // 运用统计用户登录信息
    String USERLOGININPUTPATH="/datacenter/usercenter/login/input_";
    String USERLOGINOUTPUTPATH="/datacenter/usercenter/login/output_";
    String USERREGISTERINPUTPATH="/datacenter/usercenter/register/input_";
    String USERREGISTEROUTPUTPATH="/datacenter/usercenter/register/output_";
    String USERLOGINMRFINISHQUEUE="DAMS_USERLOGIN_STATISTICS_FINISH";
    String USERREGISTERMRFINISHQUEUE="DAMS_USERREGISTER_STATISTICS_FINISH";
    String USERLOGINMQQUEUE="SDMS_UserLogin_Queue";
    




}
