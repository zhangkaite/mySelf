package com.ttmv.monitoring.alerterService.impl;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.Before;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.bean.AlerterLogMessage;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.ThresholdInfo;

public class AlerterServerInfTest {

	Map<String, ThresholdInfo> threshold;
	MediaForwardData data;
	Map<String, AlerterLogMessage> message;

	@Before
	public void befor() {
		threshold = getThreshold();
		data = getData();
		message = getMessage();
	}

	@Test
	public void testCheckAndSendEmail() throws Exception {
//		AlerterServerInf alerterServerInf = new MediaForwardAlerter(threshold,
//				data, message);
//		alerterServerInf.checkAndSendEmail();
//		System.out.print(getEmailString(alerterServerInf.getRecoveryMsg(),
//				alerterServerInf.getAlarmMsg()));
	}

	private String getEmailString(List<Map<String, String>> recoveryMsg,
			List<Map<String, String>> alarmMsg) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		if (recoveryMsg != null && recoveryMsg.size() > 0) {
			for (Map<String, String> map : recoveryMsg) {
				sb.append("【恢复】");
				for (Map.Entry<String, String> entry : map.entrySet()) {
					sb.append(entry.getKey() + " : " + entry.getValue() + "  ");
				}
				sb.append("\n");
			}
		}
		if (alarmMsg != null || alarmMsg.size() > 0) {
			for (Map<String, String> map : alarmMsg) {
				sb.append("【报警】");
				for (Map.Entry<String, String> entry : map.entrySet()) {
					sb.append(entry.getKey() + " : " + entry.getValue() + "  ");
				}
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static Map<String, ThresholdInfo> getThreshold() {
		Map<String, ThresholdInfo> map = new HashMap<String, ThresholdInfo>();
		map.put("testService_WriteBufferLength",
				getThresholdInfo("WriteBufferLength", "60|40|20"));
		map.put("testService_ReadBufferLength",
				getThresholdInfo("ReadBufferLength", "40|20|60"));
		map.put("testService_UdxConnectionLength",
				getThresholdInfo("UdxConnectionLength", "20|40|60"));
		map.put("testService_ReadPacketCount",
				getThresholdInfo("ReadPacketCount", "20|40|60"));
		map.put("testService_WritePacketCount",
				getThresholdInfo("WritePacketCount", "20|40|60"));
		map.put("testService_RoomCount",
				getThresholdInfo("RoomCount", "20|40|60"));
		map.put("testService_CPU", getThresholdInfo("CPU", "20|40|60"));
		map.put("testService_Disk", getThresholdInfo("Disk", "20|40|60"));
		map.put("testService_MEM", getThresholdInfo("MEM", "20|40|60"));
		return map;
	}

	public static ThresholdInfo getThresholdInfo(String name, String value) {
		ThresholdInfo t = new ThresholdInfo();
		t.setThresholdName(name);
		t.setThresholdValue(value);
		t.setThresholdType("testService");
		t.setThresholdAlerterId("1");
		return t;
	}

	public static MediaForwardData getData() {
		MediaForwardData data = new MediaForwardData();
		data.setServerType("testService");
		data.setServerId("test");
		data.setIp("192.168.12.11");
		data.setPort(8080);
		data.setTimestamp(new Date());
		data.setUdxConnectionLength(21);
		data.setCpu(41);
		data.setMem(61);
		data.setDisk(31);
		return data;
	}

	public static Map<String, AlerterLogMessage> getMessage() {
		Map<String, AlerterLogMessage> msg = new HashMap<String, AlerterLogMessage>();
		String name = "testService_test_192.168.12.11_8080";
		msg.put(name + "_ReadBufferLength",
				getAlerterLogMessage("40", "45", "ReadBufferLength"));
		msg.put(name + "_UdxConnectionLength",
				getAlerterLogMessage("40", "45", "UdxConnectionLength"));
		msg.put(name + "_ReadPacketCount",
				getAlerterLogMessage("40", "45", "ReadPacketCount"));
		msg.put(name + "_WritePacketCount",
				getAlerterLogMessage("60", "145", "WritePacketCount"));
		return msg;
	}

	public static AlerterLogMessage getAlerterLogMessage(String thresholdValue,
			String actualValue, String thresholdName) {
		AlerterLogMessage msg = new AlerterLogMessage();
		msg.setActualValue(actualValue);
		msg.setIp("192.168.12.11");
		msg.setPort("8080");
		msg.setServerId("test");
		msg.setServerType("testService");
		msg.setThresholdName(thresholdName);
		msg.setThresholdValue(thresholdValue);
		return msg;
	}
}
