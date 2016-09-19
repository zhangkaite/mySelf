package com.ttmv.monitoring.collection.interf;

import com.ttmv.monitoring.collection.entity.CpuInfo;
import com.ttmv.monitoring.entity.page.Page;

public interface ICpuInfo {
	
	public Page queryPageCpuInfo(CpuInfo cpuInfo) throws Exception;
}
