package com.ttmv.datacenter.paycenter.dao.implement.flow.brokerageinfo;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedWriteDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.constant.AccountConstant;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage.MysqlBrokerageInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.brokerage.RedisBrokerageInfoMapper;
import com.ttmv.datacenter.paycenter.data.BrokerageInfo;
import com.ttmv.datacenter.paycenter.data.OperationInfo;

public class UpdateBalanceBrokerageInfoService extends DistributedWriteDaoTemplate<OperationInfo>{

	public UpdateBalanceBrokerageInfoService() {
		super();
	}
	
	private final Logger logger = LogManager.getLogger(UpdateBalanceBrokerageInfoService.class);
	private RedisBrokerageInfoMapper redisBrokerageInfoMapper;
	private MysqlBrokerageInfoMapper mysqlBrokerageInfoMapper;
	
	
	private boolean checkData(OperationInfo operationInfo){
		boolean tag = true;
		if(operationInfo == null){
			logger.error("参数异常  @@ 对象 operationInfo is null");
			return false;
		}
		if(operationInfo.getType() == null){
			logger.error("参数校验失败!!!operationInfo.getType() is null");
			return false;
		}
		if(operationInfo.getNumber() == null){
			logger.error("参数校验失败!!!operationInfo.getNumber() is null");
			return false;
		}
		if(operationInfo.getUserId() == null){
			logger.error("参数校验失败!!!operationInfo.getUserId() is null");
			return false;
		}
		return tag;
	}

	@Override
	public void writePreDB(OperationInfo operationInfo) throws Exception {
		if(!this.checkData(operationInfo)){
			throw new Exception("OP500");
		}
		if (operationInfo.getType() .equals(AccountConstant.ADD)) {
			Integer result = redisBrokerageInfoMapper.incrBalance(operationInfo.getUserId().toString(), operationInfo.getNumber().toString());
			if(AccountConstant.NOT_USER.equals(result)){
				logger.error("[userID]"+operationInfo.getUserId().toString() +"[佣金提现金额]"+operationInfo.getNumber().toString()+"@@充值处理失败,账户不存在!!!");
				throw new Exception("OP30000");
			}
		}else if(operationInfo.getType() .equals(AccountConstant.MINUS)){
			Integer result = redisBrokerageInfoMapper.decrBalance(operationInfo.getUserId().toString(), operationInfo.getNumber().toString());
			if (AccountConstant.NOT_ENOUGH.equals(result)) {
				logger.error("[userID]"+operationInfo.getUserId().toString() +"[佣金提现金额]"+operationInfo.getNumber().toString()+"@@提现处理失败,账户余额不足!!!");
				throw new Exception("OP30001");
			}else if(AccountConstant.NOT_USER.equals(result)){
				logger.error("[userID]"+operationInfo.getUserId().toString() +"[佣金提现金额]"+operationInfo.getNumber().toString()+"@@提现处理失败,账户不存在!!!");
				throw new Exception("OP30000");
			}
		}
	}




	@Override
	public void writePostDB(OperationInfo operationInfo) throws Exception {
		if(!this.checkData(operationInfo)){
			throw new Exception("OP500");
		}
		if (AccountConstant.ADD.equals(operationInfo.getType())) {/* 佣金兑换 */
			BrokerageInfo tcoin = mysqlBrokerageInfoMapper.getBrokerageInfoByUserId(operationInfo.getUserId());
			BigDecimal balance = tcoin.getBalance().add(operationInfo.getNumber());
			tcoin.setBalance(balance);
			mysqlBrokerageInfoMapper.updateBrokerageInfo(tcoin);
		} else if (AccountConstant.MINUS.equals(operationInfo.getType())) {/* 佣金提现 */
			BrokerageInfo tcoin = mysqlBrokerageInfoMapper.getBrokerageInfoByUserId(operationInfo.getUserId());
			BigDecimal balance = tcoin.getBalance().subtract(operationInfo.getNumber());
			tcoin.setBalance(balance);
			mysqlBrokerageInfoMapper.updateBrokerageInfo(tcoin);
		}
	}
	
	public void setRedisBrokerageInfoMapper(RedisBrokerageInfoMapper redisBrokerageInfoMapper) {
		this.redisBrokerageInfoMapper = redisBrokerageInfoMapper;
	}

	public void setMysqlBrokerageInfoMapper(MysqlBrokerageInfoMapper mysqlBrokerageInfoMapper) {
		this.mysqlBrokerageInfoMapper = mysqlBrokerageInfoMapper;
	}

	@Override
	public Class<OperationInfo> dataClass() throws Exception {
		// TODO Auto-generated method stub
		return OperationInfo.class;
	}
}
