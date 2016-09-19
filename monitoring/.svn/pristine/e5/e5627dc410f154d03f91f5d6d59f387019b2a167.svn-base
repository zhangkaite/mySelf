package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.PhpManagerServerData;

public class PhpManagerServerAlerter extends AlerterServerInf{
	
	private PhpManagerServerData data;
	
	@Override
	protected void checkDataHandle() {
		check("CPU", "cpu");
        check("Disk", "disk");
        check("MEM", "mem");
		
        check("SysLoad", "sysLoad");
        check("NetConnections", "netConnections");
        check("NetLoad", "netLoad");
	}

    @Override
    protected void setData(Object data) {
       this.data =  (PhpManagerServerData)data;
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
