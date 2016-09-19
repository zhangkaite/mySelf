package com.ttmv.monitoring.chartService.tools.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ttmv.monitoring.chartService.tools.constant.ChartConstant;
import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.PhpManagerServerData;
import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.UmsServerData;

public class ResultUtil {

	
	/**
	 * 将MediaForwardData对象转换成结果对象
	 * @param mediaForwardData
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getMediaForwardDataTOResultObject(MediaForwardData mediaForwardData){
		Map map = new HashMap();
		if(mediaForwardData != null){
			map.put(ChartConstant.GEN_ID, mediaForwardData.getId());
			map.put(ChartConstant.GEN_CPU, mediaForwardData.getCpu());
			map.put(ChartConstant.GEN_DISK, mediaForwardData.getDisk());
			map.put(ChartConstant.GEN_MEM, mediaForwardData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, mediaForwardData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, mediaForwardData.getServerId());
			map.put(ChartConstant.GEN_IP, mediaForwardData.getIp());
			map.put(ChartConstant.GEN_PORT, mediaForwardData.getPort());
			map.put(ChartConstant.GEN_TIMESTAMP, mediaForwardData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, mediaForwardData.getCreateTime());
			map.put(ChartConstant.MF_UDXCONNECTIONLENGTH, mediaForwardData.getUdxConnectionLength());
			map.put(ChartConstant.MF_ROOMCOUNT, mediaForwardData.getRoomCount());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据MediaForwardData
	 * @param date
	 * @return
	 */
	public static MediaForwardData initMediaForwardData(Date date){
		MediaForwardData data = new MediaForwardData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRoomCount(0);
		data.setTimestamp(date);
		data.setUdxConnectionLength(0);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将PHPServerData对象转换成结果对象
	 * @param mediaForwardData
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getPHPServerDataTOResultObject(PHPServerData pHPServerData){
		Map map = new HashMap();
		if(pHPServerData != null){
			map.put(ChartConstant.GEN_ID, pHPServerData.getId());
			map.put(ChartConstant.GEN_CPU, pHPServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, pHPServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, pHPServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, pHPServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, pHPServerData.getServerId());
			map.put(ChartConstant.GEN_IP, pHPServerData.getIp());
			map.put(ChartConstant.GEN_PORT, pHPServerData.getPort());
			map.put(ChartConstant.GEN_TIMESTAMP, pHPServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, pHPServerData.getCreateTime());
			map.put(ChartConstant.PHP_NETCONNECTIONS, pHPServerData.getNetConnections());
			map.put(ChartConstant.PHP_NETLOAD, pHPServerData.getNetLoad());
			map.put(ChartConstant.PHP_SYSLOAD, pHPServerData.getSysLoad());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据MediaForwardData
	 * @param date
	 * @return
	 */
	public static PHPServerData initPHPServerData(Date date){
		PHPServerData data = new PHPServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setNetConnections(0);
		data.setSysLoad(0);
		data.setNetLoad(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}

	/**
	 * 将PHPServerData对象转换成结果对象
	 * @param mediaForwardData
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getPhpVideoServerDataTOResultObject(PhpVideoServerData phpVideoServerData){
		Map map = new HashMap();
		if(phpVideoServerData != null){
			map.put(ChartConstant.GEN_ID, phpVideoServerData.getId());
			map.put(ChartConstant.GEN_CPU, phpVideoServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, phpVideoServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, phpVideoServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, phpVideoServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, phpVideoServerData.getServerId());
			map.put(ChartConstant.GEN_IP, phpVideoServerData.getIp());
			map.put(ChartConstant.GEN_PORT, phpVideoServerData.getPort());
			map.put(ChartConstant.GEN_TIMESTAMP, phpVideoServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, phpVideoServerData.getCreateTime());
			map.put(ChartConstant.PHP_NETCONNECTIONS, phpVideoServerData.getNetConnections());
			map.put(ChartConstant.PHP_NETLOAD, phpVideoServerData.getNetLoad());
			map.put(ChartConstant.PHP_SYSLOAD, phpVideoServerData.getSysLoad());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据MediaForwardData
	 * @param date
	 * @return
	 */
	public static PhpVideoServerData initPhpVideoServerData(Date date){
		PhpVideoServerData data = new PhpVideoServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setNetConnections(0);
		data.setSysLoad(0);
		data.setNetLoad(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将PHPServerData对象转换成结果对象
	 * @param mediaForwardData
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getPhpManagerServerDataTOResultObject(PhpManagerServerData phpManagerServerData){
		Map map = new HashMap();
		if(phpManagerServerData != null){
			map.put(ChartConstant.GEN_ID, phpManagerServerData.getId());
			map.put(ChartConstant.GEN_CPU, phpManagerServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, phpManagerServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, phpManagerServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, phpManagerServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, phpManagerServerData.getServerId());
			map.put(ChartConstant.GEN_IP, phpManagerServerData.getIp());
			map.put(ChartConstant.GEN_PORT, phpManagerServerData.getPort());
			map.put(ChartConstant.GEN_TIMESTAMP, phpManagerServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, phpManagerServerData.getCreateTime());
			map.put(ChartConstant.PHP_NETCONNECTIONS, phpManagerServerData.getNetConnections());
			map.put(ChartConstant.PHP_NETLOAD, phpManagerServerData.getNetLoad());
			map.put(ChartConstant.PHP_SYSLOAD, phpManagerServerData.getSysLoad());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据MediaForwardData
	 * @param date
	 * @return
	 */
	public static PhpManagerServerData initPhpManagerServerData(Date date){
		PhpManagerServerData data = new PhpManagerServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setNetConnections(0);
		data.setSysLoad(0);
		data.setNetLoad(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将MediaControlData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getMediaControlDataTOResultObject(MediaControlData mediaControlData) {
		Map map = new HashMap();
		if(mediaControlData != null){
			map.put(ChartConstant.GEN_ID, mediaControlData.getId());
			map.put(ChartConstant.GEN_CPU, mediaControlData.getCpu());
			map.put(ChartConstant.GEN_DISK, mediaControlData.getDisk());
			map.put(ChartConstant.GEN_MEM, mediaControlData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, mediaControlData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, mediaControlData.getServerId());
			map.put(ChartConstant.GEN_IP, mediaControlData.getIp());
			map.put(ChartConstant.GEN_PORT, mediaControlData.getPort());
			map.put(ChartConstant.GEN_TIMESTAMP, mediaControlData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, mediaControlData.getCreateTime());
			map.put(ChartConstant.MN_CREATEDROOMCOUNT, mediaControlData.getCreatedRoomCount());
			map.put(ChartConstant.MN_MEDIATRANSMISSIONSERVERS, mediaControlData.getMediaTransmissionServers());
			map.put(ChartConstant.MN_INPUTMESSAGES, mediaControlData.getInputMessages());
			map.put(ChartConstant.MN_OUTPUTMESSAGES, mediaControlData.getOutputMessages());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据MediaControlData
	 * @param date
	 * @return
	 */
	public static MediaControlData initMediaControlData(Date date){
		MediaControlData data = new MediaControlData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setInputMessages(0);
		data.setOutputMessages(0);
		//data.setMediaTransmissionServers("");
		data.setCreatedRoomCount(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将ScreenShotData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getScreenShotDataTOResultObject(ScreenShotData screenShotData) {
		Map map = new HashMap();
		if(screenShotData != null){
			map.put(ChartConstant.GEN_ID, screenShotData.getId());
			map.put(ChartConstant.GEN_CPU, screenShotData.getCpu());
			map.put(ChartConstant.GEN_DISK, screenShotData.getDisk());
			map.put(ChartConstant.GEN_MEM, screenShotData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, screenShotData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, screenShotData.getServerId());
			map.put(ChartConstant.GEN_IP, screenShotData.getIp());
			map.put(ChartConstant.GEN_PORT, screenShotData.getPort());
			map.put(ChartConstant.GEN_CREATETIME, screenShotData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据ScreenShotData
	 * @param date
	 * @return
	 */
	public static ScreenShotData initScreenShotData(Date date) {
		ScreenShotData data = new ScreenShotData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将MediaControlData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getTranscodingDataTOResultObject(TranscodingData transcodingData) {
		Map map = new HashMap();
		if(transcodingData != null){
			map.put(ChartConstant.GEN_ID, transcodingData.getId());
			map.put(ChartConstant.GEN_CPU, transcodingData.getCpu());
			map.put(ChartConstant.GEN_DISK, transcodingData.getDisk());
			map.put(ChartConstant.GEN_MEM, transcodingData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, transcodingData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, transcodingData.getServerId());
			map.put(ChartConstant.GEN_IP, transcodingData.getIp());
			map.put(ChartConstant.GEN_PORT, transcodingData.getPort());
			map.put(ChartConstant.GEN_CREATETIME, transcodingData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据ScreenShotData
	 * @param date
	 * @return
	 */
	public static TranscodingData initTranscodingData(Date date) {
		TranscodingData data = new TranscodingData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将HpServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getHpServerDataTOResultObject(HpServerData hpServerData) {
		Map map = new HashMap();
		if(hpServerData != null){
			map.put(ChartConstant.GEN_ID, hpServerData.getId());
			map.put(ChartConstant.GEN_CPU, hpServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, hpServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, hpServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, hpServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, hpServerData.getServerId());
			map.put(ChartConstant.GEN_IP, hpServerData.getIp());
			map.put(ChartConstant.GEN_PORT, hpServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, hpServerData.getRunTime());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, hpServerData.getWorkThread());
			map.put(ChartConstant.GEN_TIMESTAMP, hpServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, hpServerData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据HpServerData
	 * @param date
	 * @return
	 */
	public static HpServerData initHpServerData(Date date){
		HpServerData data = new HpServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将LbsServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getLbsServerDataTOResultObject(LbsServerData lbsServerData) {
		Map map = new HashMap();
		if(lbsServerData != null){
			map.put(ChartConstant.GEN_ID, lbsServerData.getId());
			map.put(ChartConstant.GEN_CPU, lbsServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, lbsServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, lbsServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, lbsServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, lbsServerData.getServerId());
			map.put(ChartConstant.GEN_IP, lbsServerData.getIp());
			map.put(ChartConstant.GEN_PORT, lbsServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, lbsServerData.getRunTime());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, lbsServerData.getWorkThread());
			map.put(ChartConstant.GEN_TIMESTAMP, lbsServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, lbsServerData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据LbsServerData
	 * @param date
	 * @return
	 */
	public static LbsServerData initLbsServerData(Date date){
		LbsServerData data = new LbsServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将MdsServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getMdsServerDataTOResultObject(MdsServerData mdsServerData) {
		Map map = new HashMap();
		if(mdsServerData != null){
			map.put(ChartConstant.GEN_ID, mdsServerData.getId());
			map.put(ChartConstant.GEN_CPU, mdsServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, mdsServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, mdsServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, mdsServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, mdsServerData.getServerId());
			map.put(ChartConstant.GEN_IP, mdsServerData.getIp());
			map.put(ChartConstant.GEN_PORT, mdsServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, mdsServerData.getRunTime());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, mdsServerData.getWorkThread());
			map.put(ChartConstant.GEN_TIMESTAMP, mdsServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, mdsServerData.getCreateTime());
			map.put(ChartConstant.IM_GROUP_COUNT,mdsServerData.getGroupCount());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据MdsServerData
	 * @param date
	 * @return
	 */
	public static MdsServerData initMdsServerData(Date date){
		MdsServerData data = new MdsServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	
	/**
	 * 将MtsServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getMtsServerDataTOResultObject(MtsServerData mtsServerData) {
		Map map = new HashMap();
		if(mtsServerData != null){
			map.put(ChartConstant.GEN_ID, mtsServerData.getId());
			map.put(ChartConstant.GEN_CPU, mtsServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, mtsServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, mtsServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, mtsServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, mtsServerData.getServerId());
			map.put(ChartConstant.GEN_IP, mtsServerData.getIp());
			map.put(ChartConstant.GEN_PORT, mtsServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, mtsServerData.getRunTime());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, mtsServerData.getWorkThread());
			map.put(ChartConstant.GEN_TIMESTAMP, mtsServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, mtsServerData.getCreateTime());
			map.put(ChartConstant.GEN_CLIENTCONNECTIONSUM, mtsServerData.getClientConnectionSum());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据MtsServerData
	 * @param date
	 * @return
	 */
	public static MtsServerData initMtsServerData(Date date){
		MtsServerData data = new MtsServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将MssServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getMssServerDataTOResultObject(MssServerData mssServerData) {
		Map map = new HashMap();
		if(mssServerData != null){
			map.put(ChartConstant.GEN_ID, mssServerData.getId());
			map.put(ChartConstant.GEN_CPU, mssServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, mssServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, mssServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, mssServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, mssServerData.getServerId());
			map.put(ChartConstant.GEN_IP, mssServerData.getIp());
			map.put(ChartConstant.GEN_PORT, mssServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, mssServerData.getRunTime());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, mssServerData.getWorkThread());
			map.put(ChartConstant.GEN_TIMESTAMP, mssServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, mssServerData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据MssServerData
	 * @param date
	 * @return
	 */
	public static MssServerData initMssServerData(Date date){
		MssServerData data = new MssServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将PrsServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getPrsServerDataTOResultObject(PrsServerData prsServerData) {
		Map map = new HashMap();
		if(prsServerData != null){
			map.put(ChartConstant.GEN_ID, prsServerData.getId());
			map.put(ChartConstant.GEN_CPU, prsServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, prsServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, prsServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, prsServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, prsServerData.getServerId());
			map.put(ChartConstant.GEN_IP, prsServerData.getIp());
			map.put(ChartConstant.GEN_PORT, prsServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, prsServerData.getRunTime());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, prsServerData.getWorkThread());
			map.put(ChartConstant.GEN_TIMESTAMP, prsServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, prsServerData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据PrsServerData
	 * @param date
	 * @return
	 */
	public static PrsServerData initPrsServerData(Date date){
		PrsServerData data = new PrsServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将UmsServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getUmsServerDataTOResultObject(UmsServerData umsServerData) {
		Map map = new HashMap();
		if(umsServerData != null){
			map.put(ChartConstant.GEN_ID, umsServerData.getId());
			map.put(ChartConstant.GEN_CPU, umsServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, umsServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, umsServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, umsServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, umsServerData.getServerId());
			map.put(ChartConstant.GEN_IP, umsServerData.getIp());
			map.put(ChartConstant.GEN_PORT, umsServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, umsServerData.getRunTime());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, umsServerData.getWorkThread());
			map.put(ChartConstant.GEN_TIMESTAMP, umsServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, umsServerData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据umsServerData
	 * @param date
	 * @return
	 */
	public static UmsServerData initUmsServerData(Date date){
		UmsServerData data = new UmsServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	/**
	 * 将RmsServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getRmsServerDataTOResultObject(RmsServerData rmsServerData) {
		Map map = new HashMap();
		if(rmsServerData != null){
			map.put(ChartConstant.GEN_ID, rmsServerData.getId());
			map.put(ChartConstant.GEN_CPU, rmsServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, rmsServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, rmsServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, rmsServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, rmsServerData.getServerId());
			map.put(ChartConstant.GEN_IP, rmsServerData.getIp());
			map.put(ChartConstant.GEN_PORT, rmsServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, rmsServerData.getRunTime());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, rmsServerData.getWorkThread());
			map.put(ChartConstant.IM_INPUT_CMNDS, rmsServerData.getInputCmds());
			map.put(ChartConstant.IM_OUTPUT_CMDS, rmsServerData.getOutputCmds());
			map.put(ChartConstant.GEN_TIMESTAMP, rmsServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, rmsServerData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据RmsServerData
	 * @param date
	 * @return
	 */
	public static RmsServerData initRmsServerData(Date date){
		RmsServerData data = new RmsServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setInputCmds(0);
		data.setOutputCmds(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	
	
	/**
	 * 将TasServerData对象转换成结果对象
	 * @param result
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getTasServerDataTOResultObject(TasServerData tasServerData) {
		Map map = new HashMap();
		if(tasServerData != null){
			map.put(ChartConstant.GEN_ID, tasServerData.getId());
			map.put(ChartConstant.GEN_CPU, tasServerData.getCpu());
			map.put(ChartConstant.GEN_DISK, tasServerData.getDisk());
			map.put(ChartConstant.GEN_MEM, tasServerData.getMem());
			map.put(ChartConstant.GEN_SERVER_TYPE, tasServerData.getServerType());
			map.put(ChartConstant.GEN_SERVERID, tasServerData.getServerId());
			map.put(ChartConstant.GEN_IP, tasServerData.getIp());
			map.put(ChartConstant.GEN_PORT, tasServerData.getPort());
			map.put(ChartConstant.IM_RUM_TIME, tasServerData.getRunTime());
			map.put(ChartConstant.GEN_CLIENTCONNECTIONSUM, tasServerData.getClientConnectionSum());
			map.put(ChartConstant.IM_WORD_THRESH_SUM, tasServerData.getWorkThread());
			map.put(ChartConstant.GEN_TIMESTAMP, tasServerData.getTimestamp());
			map.put(ChartConstant.GEN_CREATETIME, tasServerData.getCreateTime());
			return map;
		}
		return null;
	}
	
	/**
	 * 构造数据TasServerData
	 * @param date
	 * @return
	 */
	public static TasServerData initTasServerData(Date date){
		TasServerData data = new TasServerData();
		data.setId(new BigInteger("-1"));
		data.setCpu(0);
		data.setDisk(0);
		data.setMem(0);
		data.setRunTime(0);
		data.setWorkThread(0);
		data.setClientConnectionSum(0);
		data.setTimestamp(date);
		data.setCreateTime(date);
		return data;
	}
	/**
	 * 实现List数组的反转
	 * @param result
	 * @return
	 */
	public static List getListRevers(List result){
		if(result != null && result.size() > 0){
			List objs = new ArrayList();
			for(int i=result.size() - 1;i>=0;i--){
				objs.add(result.get(i));
			}
			return objs;
		}
		return null;
	}

	
}
