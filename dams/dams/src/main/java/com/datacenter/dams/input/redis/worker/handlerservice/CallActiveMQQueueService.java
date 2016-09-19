package com.datacenter.dams.input.redis.worker.handlerservice;

public class CallActiveMQQueueService {

	private static ActiveMqQueueService activeMqQueueService;

	public static ActiveMqQueueService getActiveMqQueueService() {
		return activeMqQueueService;
	}

	public static void setActiveMqQueueService(ActiveMqQueueService activeMqQueueService) {
		CallActiveMQQueueService.activeMqQueueService = activeMqQueueService;
	}

}
