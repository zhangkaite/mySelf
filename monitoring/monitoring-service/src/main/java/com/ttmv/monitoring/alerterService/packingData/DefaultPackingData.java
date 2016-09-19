package com.ttmv.monitoring.alerterService.packingData;

import com.ttmv.monitoring.alerterService.bean.AlerterLogMessage;
import com.ttmv.monitoring.alerterService.tool.GeneralUtility;
import com.ttmv.monitoring.entity.AlertRecordInfo;
import com.ttmv.monitoring.msgNotification.entity.Threshold;
import net.sf.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zbs on 15/10/22.
 */
public class DefaultPackingData implements PackingDataInf {

    private static Logger logger = LogManager.getLogger(DefaultPackingData.class);

    // 服务器类型，如tas、mts等等,用于获取对应的阀值
    private String serverType;
    private String serverId;
    private String ip;
    private String port;
    private Date timestamp;
    private Object data;

    public DefaultPackingData(Object data) throws Exception {
        if(data == null)
            throw new Exception(this.getClass().getSimpleName() + "类中createMsg方法传入的data为空。");
        this.serverType = (String) GeneralUtility.getValueByObject(data, "getServerType");
        this.serverId = (String)GeneralUtility.getValueByObject(data,"getServerId");
        this.ip = (String)GeneralUtility.getValueByObject(data,"getIp");
        this.port = GeneralUtility.getValueByObject(data,"getPort")+"";
        this.timestamp = (Date)GeneralUtility.getValueByObject(data,"getTimestamp");
        this.data = data;
    }

    /**
     * 创建报警和警告邮件信息中的提示信息
     * @param setThresholdName
     * @param thresholdValue
     * @param actualValue
     * @return
     */
    public AlerterLogMessage createMsg(String setThresholdName,String thresholdValue, String actualValue) {
        AlerterLogMessage msg = new AlerterLogMessage();
        msg.setServerType(serverType);
        msg.setServerId(serverId);
        msg.setIp(ip);
        msg.setPort(port);
        msg.setThresholdName(setThresholdName);
        msg.setThresholdValue(thresholdValue);
        msg.setActualValue(actualValue);
        return msg;
    }

    /**
     * 用于构造保存报警信息的对象
     * @param type
     * @param msg
     * @return
     */
    public AlertRecordInfo getAlertRecordInfo(String type,String msg) {
        AlertRecordInfo alertRecordInfo = new AlertRecordInfo();
        alertRecordInfo.setIp(ip);
        alertRecordInfo.setAlertType(type);
        alertRecordInfo.setServerType(serverType);
        alertRecordInfo.setAlertTime(timestamp);
        alertRecordInfo.setServerId(serverId);
        alertRecordInfo.setAlertMsg(msg);
        return alertRecordInfo;
    }

    /**
     * 传递给邮件发送系统，用于发送邮件
     * @param msg
     * @param mark
     * @param alertID
     * @return
     */
    public Map getParamsMsg(List<Threshold> msg,int mark,String alertID) {
        String msgType = (mark == 0) ? "OK" : "PROBLEM";
        Map map = new HashMap();
        map.put("alerterID", alertID);
        map.put("msgType", msgType);
        map.put("ip", ip);
        map.put("serverName", serverType);
        map.put("serverID", serverId);
        map.put("serverTime", timestamp);
        map.put("thresholds", msg);
        return map;
    }

    /**
     * 报警日志的名称 = “serverType” + “serverId” + “ip” + “port”
     * @return
     */
    public String getMessageName(){
        return serverType + "_" + serverId + "_" + ip + "_" + port;
    }

    public String getServerType(){
        return serverType;
    }
}
