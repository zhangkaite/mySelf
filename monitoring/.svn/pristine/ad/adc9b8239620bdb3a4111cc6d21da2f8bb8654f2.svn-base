package com.ttmv.monitoring.alerterService.impl;

import com.ttmv.monitoring.alerterService.AlerterServerInf;
import com.ttmv.monitoring.alerterService.packingData.DefaultPackingData;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.RmsServerData;

public class RmsServerAlerter extends AlerterServerInf{
	
	private RmsServerData data;
	
	@Override
	protected void checkDataHandle() {
		check("CPU", "cpu");
        check("Disk", "disk");
        check("MEM", "mem");
        
        check("WorkThread", "workThread");
//        check("InputCmds", "inputCmds");
//        check("OutputCmds", "outputCmds");
        
	}

    @Override
    protected void setData(Object data) {
       this.data =  (RmsServerData)data;
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
