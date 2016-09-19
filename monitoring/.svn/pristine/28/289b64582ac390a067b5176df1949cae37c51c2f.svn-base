package com.ttmv.monitoring.alerterService.packingData;

import com.ttmv.monitoring.alerterService.bean.AlerterLogMessage;
import com.ttmv.monitoring.entity.AlertRecordInfo;
import com.ttmv.monitoring.msgNotification.entity.Threshold;

import java.util.List;
import java.util.Map;

/**
 * Created by zbs on 15/10/22.
 */
public interface PackingDataInf {


    /**
     * 创建报警和警告邮件信息中的提示信息
     * @param setThresholdName
     * @param thresholdValue
     * @param actualValue
     * @return
     */
    public AlerterLogMessage createMsg(String setThresholdName,String thresholdValue, String actualValue);

    /**
     * 用于构造保存报警信息的对象
     * @param type
     * @param msg
     * @return
     */
    public AlertRecordInfo getAlertRecordInfo(String type,String msg);

    /**
     * 传递给邮件发送系统，用于发送邮件
     * @param msg
     * @param mark
     * @param alertID
     * @return
     */
    public Map getParamsMsg(List<Threshold> msg,int mark,String alertID);

    /**
     * 报警日志的名称 = “serverType” + “serverId” + “ip” + “port”
     * @return
     */
    public String getMessageName();

    public String getServerType();
}
