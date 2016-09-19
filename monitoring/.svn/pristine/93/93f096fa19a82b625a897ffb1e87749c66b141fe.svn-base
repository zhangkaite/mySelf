package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.HpServerData;


/**
 * Created by zbs on 15/10/21.
 */
public class HpServerAlerter extends AlerterServerInf {

    private HpServerData data;

    @Override 
    public void checkDataHandle() {
        check("WorkThread", "workThread");
        check("CPU", "cpu");
        check("Disk", "disk");
        check("MEM", "mem");
    }

    @Override
    protected void setData(Object data) {
       this.data =  (HpServerData)data;
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
