package com.ttmv.datacenter.paycenter.triger;

public interface BaseTask extends Runnable{

	/**
	 * 执行任务
	 * @throws Exception
	 */
	public void doWork()throws Exception;
}
