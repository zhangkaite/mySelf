package com.datacenter.dams.business.service.bill;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.BillInter;
import com.datacenter.dams.input.queue.entity.HbaseOperationInfo;
import com.datacenter.dams.input.queue.entity.TcoinConsumeSpendInfo;
import com.datacenter.dams.input.queue.entity.TcoinRechargeInfo;
import com.datacenter.dams.input.redis.worker.util.HbaseUtil;
import com.datacenter.dams.util.JsonUtil;

public class TcoinBillService implements BillInter{

	private static Logger logger=LogManager.getLogger(TcoinBillService.class);
	private static Configuration config = HBaseConfiguration.create();
	
	/* 表名 */
	private static final String HBASETABLE = "HbaseOperationInfo";
	/* 列名 */
	private static final String COLUMN = "operation_data";
	/* 列族 */
	private static final String FAMILIY = "operation_data";
	
	
	@Override
	public void handler(Object object,String type) throws Exception {
		HbaseOperationInfo info = this.getHbaseOpertionInfoFromObject(object,type);
		String _type = "01";
		if(type.equals("-1")){//消费
			_type = "01";
		}else if(type.equals("1")){//充值
			_type = "02";
		}
		/* 组装rowKey time时间+随机数+01/02+用户userId */
		String rowKey = info.getTime()+HbaseUtil.getRandomNumber()+_type+info.getUserId();
		String data = JsonUtil.getObjectToJson(info);
		/* 创建表或是判断表是否已经存在 */
		try {
			HbaseUtil.createTableNoOverwrite(config, HBASETABLE, FAMILIY);
			logger.info("[DAMS#TcoinBillService]判断Hbase表是否已经存在,成功!");
		} catch (Exception e) {
			throw e;
		}
		
		/* 添加数据 */
		try {
			HbaseUtil.addRow(HBASETABLE, rowKey, FAMILIY, COLUMN, data, config);
			logger.info("[DAMS#TcoinBillService]添加TB消费账单成功,数据是:"+data);
		} catch (Exception e) {
			logger.error("[DAMS#TcoinBillService#ERROR]添加TB消费账单失败,数据是:"+data+",",e);
			throw e;
		}
	}
	
	/**
	 * 从消费信息中组装消费账单
	 * @param tcoinConsumeInfo
	 */
	private HbaseOperationInfo getHbaseOpertionInfoFromObject(Object object,String type){
		if(object == null){
			return null;
		}
		if(type.equals("-1")){
			TcoinConsumeSpendInfo bill = (TcoinConsumeSpendInfo)object;
			HbaseOperationInfo info = new HbaseOperationInfo();
			info.setUserId(bill.getUserID());
			info.setTime(bill.getTime());
			info.setNumber(bill.getNumber());
			info.setOrderId(bill.getOrderId());
			info.setClientType(bill.getClientType());
			info.setVersion(bill.getVersion());
			info.setType(new Integer(type));//消费
			info.setAccountType(new Integer(0));//TB账户
			info.setDestinationUserID(bill.getDestinationUserID());
			info.setProductID(bill.getProductID() == null ? null : bill.getProductID().toString());
			info.setProductCount(bill.getProductCount());
			info.setProductPrice(bill.getProductPrice());
			info.setRoomID(bill.getRoomID() == null ? null : bill.getRoomID().toString());
			return info;
		}
		if(type.equals("1")){
			TcoinRechargeInfo bill = (TcoinRechargeInfo)object;
			HbaseOperationInfo info = new HbaseOperationInfo();
			info.setUserId(bill.getUserID());
			info.setTime(bill.getTime());
			info.setNumber(bill.getNumber());
			info.setOrderId(bill.getOrderId());
			info.setClientType(bill.getClientType());
			info.setVersion(bill.getVersion());
			info.setType(new Integer(type));//充值
			info.setAccountType(new Integer(0));//TB账户
			return info;
		}
		return null;
	}
}
