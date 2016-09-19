package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.TasServerData;

public class TasServerAlerter extends AlerterServerInf{
	
	private TasServerData data;
	
	@Override
	protected void checkDataHandle() {
		check("CPU", "cpu");
        check("Disk", "disk");
        check("MEM", "mem");
		
        check("WorkThread", "workThread");
        check("ClientConntionCount", "clientConnectionSum");
        
        
	}

    @Override
    protected void setData(Object data) {
       this.data =  (TasServerData)data;
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
