package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.MediaControlData;

/**
 * 媒体控制服务器监控
 * Created by zbs on 15/10/14.
 */
public class MediaControlAlerter extends AlerterServerInf {

    private MediaControlData data;

    @Override 
    protected PackingDataInf getPackingDataInf() throws Exception {
        return new DefaultPackingData(data);
    }

    @Override
    public void checkDataHandle() {
        check("CreatedRoomCount", "createdRoomCount");
        check("InputMessages", "inputMessages");
        check("OutputMessages", "outputMessages");
        check("CPU", "cpu");
        check("Disk", "disk");
        check("MEM", "mem");
    }

    @Override
    public void setData(Object data){
        this.data = (MediaControlData)data;
    }

    @Override
    public Object getData() {
        return data;
    }

}
