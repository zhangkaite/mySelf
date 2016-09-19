package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.TranscodingData;

/**
 * Created by zbs on 15/10/14.
 */
public class TranscodingAlerter extends AlerterServerInf {

    private TranscodingData data;

    @Override 
    public void checkDataHandle() {
        check("CPU", "cpu");
        check("Disk", "disk");
        check("MEM", "mem");
    }

    @Override
    protected void setData(Object data) {
       this.data = (TranscodingData)data;
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
