package com.datacenter.dams.input.hdfs.service;

public class HdfsConsumeService {

	private static BaseService tbTqConsumeService;
	private static BaseService tbRechargeService;
	private static BaseService tqRechargeService;

	public static BaseService getTbTqConsumeService() {
		return tbTqConsumeService;
	}

	public static void setTbTqConsumeService(BaseService tbTqConsumeService) {
		HdfsConsumeService.tbTqConsumeService = tbTqConsumeService;
	}

	public static BaseService getTbRechargeService() {
		return tbRechargeService;
	}

	public static void setTbRechargeService(BaseService tbRechargeService) {
		HdfsConsumeService.tbRechargeService = tbRechargeService;
	}

	public static BaseService getTqRechargeService() {
		return tqRechargeService;
	}

	public static void setTqRechargeService(BaseService tqRechargeService) {
		HdfsConsumeService.tqRechargeService = tqRechargeService;
	}

}
