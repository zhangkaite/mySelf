package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.MtsServerData;

public class MtsServerAlerter extends AlerterServerInf{
	private  MtsServerData data;
	
	@Override
	protected void checkDataHandle() {
		check("CPU", "cpu");
        check("Disk", "disk");
        check("MEM", "mem"); 
		
        check("WorkThread", "workThread");
        check("ClientConnectionCount", "clientConnectionSum");
        
	}

    @Override
    protected void setData(Object data) {
       this.data =  (MtsServerData)data;
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
