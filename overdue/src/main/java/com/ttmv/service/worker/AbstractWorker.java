package com.ttmv.service.worker;

public abstract class AbstractWorker {
	
	//遍历MySql库（低频率筛选数据，符合条件数据添加到ReDis）
	public abstract void traversalMysql();
	
	//遍历ReDis库(高频率筛选数据，符合条件数据对外系统进行通知)
	public abstract void traversalRedis();
	
	//到期业务提醒，每天扫表一次
	public abstract void refreshMysql();

}
