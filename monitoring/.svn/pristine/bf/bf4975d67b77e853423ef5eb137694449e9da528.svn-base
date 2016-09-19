package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.PHPServerData;

/**
 * php服务器监控
 * Created by zbs on 15/9/25.
 */
public class PHPServerAlerter extends AlerterServerInf {

    private PHPServerData data;

    @Override 
    public void checkDataHandle()  {
        check("SysLoad","sysLoad");
        check("NetConnections","netConnections");
        check("NetLoad","netLoad");
        check("CPU","cpu");
        check("Disk","disk");
        check("MEM","mem");        
    }

    @Override
    protected void setData(Object data) {
        this.data = (PHPServerData)data;
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
