package com.ttmv.datacenter.paycenter.dao.implement.flow.tcoininfo;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedWriteDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.constant.AccountConstant;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin.MysqlTcoinInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tcoin.RedisTcoinInfoMapper;
import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.data.TcoinInfo;

public class UpdateBalanceTcoinInfoService extends DistributedWriteDaoTemplate<OperationInfo>{
	
	public UpdateBalanceTcoinInfoService() {
		super();
	}

	private final Logger logger = LogManager.getLogger(UpdateBalanceTcoinInfoService.class);
	private RedisTcoinInfoMapper redisTcoinInfoMapper;
	private MysqlTcoinInfoMapper mysqlTcoinInfoMapper;
	
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
		if (operationInfo.getType().equals(AccountConstant.ADD)) {
			Integer result = redisTcoinInfoMapper.incrBalance(operationInfo.getUserId().toString(), operationInfo.getNumber().toString());
			if(AccountConstant.NOT_USER.equals(result)){
				logger.error("[userID]"+operationInfo.getUserId().toString() +"[T币充值金额]"+operationInfo.getNumber().toString()+"@@充值处理失败,账户不存在!!!");
				throw new Exception("OP30000");
			}
		}else if(operationInfo.getType().equals(AccountConstant.MINUS)){
			Integer result = redisTcoinInfoMapper.decrBalance(operationInfo.getUserId().toString(), operationInfo.getNumber().toString());
			if (AccountConstant.NOT_ENOUGH.equals(result)) {
				logger.error("[userID]"+operationInfo.getUserId().toString() +"[T币消费金额]"+operationInfo.getNumber().toString()+"@@消费处理失败,账户余额不足!!!");
				throw new Exception("OP30001");
			}else if(AccountConstant.NOT_USER.equals(result)){
				logger.error("[userID]"+operationInfo.getUserId().toString() +"[T币消费金额]"+operationInfo.getNumber().toString()+"@@消费处理失败,账户不存在!!!");
				throw new Exception("OP30000");
			}
		}
	}
	

	@Override
	public void writePostDB(OperationInfo operationInfo) throws Exception {
		if(!this.checkData(operationInfo)){
			throw new Exception("OP500");
		}
		if (AccountConstant.ADD.equals(operationInfo.getType())) {/* 充值 */
			TcoinInfo tcoin = mysqlTcoinInfoMapper.getTcoinInfoByUserId(operationInfo.getUserId());
			BigDecimal balance = tcoin.getBalance().add(operationInfo.getNumber());
			tcoin.setBalance(balance);
			mysqlTcoinInfoMapper.updateTcoinInfo(tcoin);
		} else if (AccountConstant.MINUS.equals(operationInfo.getType())) {/* 消费 */
			TcoinInfo tcoin = mysqlTcoinInfoMapper.getTcoinInfoByUserId(operationInfo.getUserId());
			BigDecimal balance = tcoin.getBalance().subtract(operationInfo.getNumber());
			tcoin.setBalance(balance);
			mysqlTcoinInfoMapper.updateTcoinInfo(tcoin);
		}
	}
	
	public void setRedisTcoinInfoMapper(RedisTcoinInfoMapper redisTcoinInfoMapper) {
		this.redisTcoinInfoMapper = redisTcoinInfoMapper;
	}

	public void setMysqlTcoinInfoMapper(MysqlTcoinInfoMapper mysqlTcoinInfoMapper) {
		this.mysqlTcoinInfoMapper = mysqlTcoinInfoMapper;
	}



	@Override
	public Class<OperationInfo> dataClass() throws Exception {
		// TODO Auto-generated method stub
		return OperationInfo.class;
	}
	
}
