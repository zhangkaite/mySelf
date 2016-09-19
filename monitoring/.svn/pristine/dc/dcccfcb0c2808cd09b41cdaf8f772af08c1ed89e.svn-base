package com.ttmv.monitoring.msgNotification.tools;

import java.util.List;
import java.util.Map;

import com.ttmv.monitoring.msgNotification.entity.Threshold;

@SuppressWarnings({"unchecked","rawtypes"})
public class MsgFormat {
	
	/**
	 * [Mail]阀值报警消息内容
	 * @param params
	 * @return
	 */
	public static String emailFormatFZyes(Map params){
		StringBuffer str = new StringBuffer();
		str.append("\r\n" + "[线上服务阀值报警 ]" + "\r\n\r\n");
		str.append("[服务器IP]:  " + params.get("ip").toString() + "\r\n");
		str.append("[服务名称]:  " + params.get("serverName").toString() + "\r\n");
		str.append("[服务器ID]:  " + params.get("serverID").toString() + "\r\n");
		str.append("[上报时间]:  " + params.get("serverTime").toString() + "\r\n");
		str.append("\r\n=================\r\n");
		List<Threshold> list = (List)params.get("thresholds");
		for(int i=0;i<list.size();i++){
			str.append("[监控指标名称]：" + list.get(i).getThresholdName()+" ; ") ;
			str.append("[指标实际值]：" + list.get(i).getActualValue()+" ; ");
			str.append("[超过预设阀值]：" + list.get(i).getThresholdValue()+" ; ");
			str.append("\r\n");
		}
		str.append("=================");
		return str.toString();
	}
	
	/**
	 * [Mail]阀值报警恢复消息内容
	 * @param params
	 * @return
	 */
	public static String emailFormatFZno(Map params){
		StringBuffer str = new StringBuffer();
		str.append("\r\n" + "[线上服务阀值报警恢复]" + "\r\n\r\n");
		str.append("[服务器IP]:  " + params.get("ip").toString() + "\r\n");
		str.append("[服务名称]:  " + params.get("serverName").toString() + "\r\n");
		str.append("[服务器ID]:  " + params.get("serverID").toString() + "\r\n");
		str.append("[上报时间]:  " + params.get("serverTime").toString() + "\r\n");
		str.append("\r\n=================\r\n");
		List<Threshold> list = (List)params.get("thresholds");
		for(int i=0;i<list.size();i++){
			str.append("[监控指标名称]：" + list.get(i).getThresholdName()+" ; ") ;
			str.append("[指标实际值]：" + list.get(i).getActualValue()+" ; ");
			str.append("[低于预设阀值]：" + list.get(i).getThresholdValue()+" ; ");
			str.append("\r\n");
		}
		str.append("=================");
		return str.toString();
	}
	
	/**
	 * [Mail]直接报警
	 * @param params
	 * @return
	 */
	public static String emailFormatAlert(Map params){
		StringBuffer str = new StringBuffer();
		str.append("\r\n" + "[线上服务出现严重问题]" + "\r\n\r\n");
		str.append("[服务器IP]:  " + params.get("ip").toString() + "\r\n");
		str.append("[服务名称]:  " + params.get("serverName").toString() + "\r\n");
		str.append("[服务器ID]:  " + params.get("serverID").toString() + "\r\n");
		str.append("[上报时间]:  " + params.get("serverTime").toString() + "\r\n");
		str.append("\r\n=================\r\n");
			str.append("[ERROR_Msg]:  " + params.get("errorMsg").toString() + "\r\n");
		str.append("=================");
		return str.toString();
	}
	
	
	
	

}
