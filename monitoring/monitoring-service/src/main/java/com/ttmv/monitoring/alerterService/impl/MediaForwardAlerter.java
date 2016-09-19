package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.MediaForwardData;

/**
 * 媒体转发服务器监控
 * Created by zbs on 15/9/25.
 */
class MediaForwardAlerter extends AlerterServerInf {

	private MediaForwardData data;

	@Override 
	public void checkDataHandle() {
		check("UdxConnectionLength", "udxConnectionLength");
		check("RoomCount", "roomCount");
		check("CPU", "cpu");
		check("Disk", "disk");
		check("MEM", "mem");

	}

	@Override
	protected void setData(Object data) {
        this.data = (MediaForwardData)data;
	}

	@Override
	protected PackingDataInf getPackingDataInf() throws Exception {
		return new DefaultPackingData(data);
	}

	@Override
	public Object getData() {
		return data;
	}

}
