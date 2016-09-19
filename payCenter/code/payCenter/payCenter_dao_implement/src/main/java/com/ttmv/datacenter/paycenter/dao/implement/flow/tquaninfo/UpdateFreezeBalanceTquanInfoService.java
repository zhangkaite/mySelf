package com.ttmv.datacenter.paycenter.dao.implement.flow.tquaninfo;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jam.dataflow.DistributedWriteDaoTemplate;

import com.ttmv.datacenter.paycenter.dao.implement.constant.AccountConstant;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan.MysqlTquanInfoMapper;
import com.ttmv.datacenter.paycenter.dao.implement.mapper.tquan.RedisTquanInfoMapper;
import com.ttmv.datacenter.paycenter.data.OperationInfo;
import com.ttmv.datacenter.paycenter.data.TquanInfo;

public class UpdateFreezeBalanceTquanInfoService extends DistributedWriteDaoTemplate<OperationInfo>{

	public UpdateFreezeBalanceTquanInfoService() {
		super();
	}

	private final Logger logger = LogManager.getLogger(UpdateFreezeBalanceTquanInfoService.class);
	private RedisTquanInfoMapper redisTquanInfoMapper;
	private MysqlTquanInfoMapper mysqlTquanInfoMapper;
	
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
			redisTquanInfoMapper.incrBalance(operationInfo.getUserId().toString(), operationInfo.getNumber().toString());
		}else if(operationInfo.getType().equals(AccountConstant.MINUS)){
			Integer result = redisTquanInfoMapper.decrBalance(operationInfo.getUserId().toString(), operationInfo.getNumber().toString());
			if (AccountConstant.NOT_ENOUGH.equals(result)) {
				logger.error("[userID]"+operationInfo.getUserId().toString() +"[T券冻结金额]"+operationInfo.getNumber().toString()+"@@冻结处理失败,账户余额不足!!!");
				throw new Exception("OP30001");
			}else if(AccountConstant.NOT_USER.equals(result)){
				logger.error("[userID]"+operationInfo.getUserId().toString() +"[T券冻结金额]"+operationInfo.getNumber().toString()+"@@冻结处理失败,账户不存在!!!");
				throw new Exception("OP30000");
			}
		}
	}



	@Override
	public void writePostDB(OperationInfo operationInfo) throws Exception {
		if(!this.checkData(operationInfo)){
			throw new Exception("OP500");
		}
		if (AccountConstant.ADD.equals(operationInfo.getType())) {/* 冻结 */
			TquanInfo tcoin = mysqlTquanInfoMapper.getTquanInfoByUserId(operationInfo.getUserId());
			BigDecimal balance = tcoin.getFreezeBalance().add(operationInfo.getNumber());
			tcoin.setFreezeBalance(balance);
			mysqlTquanInfoMapper.updateTquanInfo(tcoin);
		} else if (AccountConstant.MINUS.equals(operationInfo.getType())) {/* 解冻 */
			TquanInfo tcoin = mysqlTquanInfoMapper.getTquanInfoByUserId(operationInfo.getUserId());
			BigDecimal balance = tcoin.getFreezeBalance().subtract(operationInfo.getNumber());
			tcoin.setFreezeBalance(balance);
			mysqlTquanInfoMapper.updateTquanInfo(tcoin);
		}
	}

	public void setRedisTquanInfoMapper(RedisTquanInfoMapper redisTquanInfoMapper) {
		this.redisTquanInfoMapper = redisTquanInfoMapper;
	}

	public void setMysqlTquanInfoMapper(MysqlTquanInfoMapper mysqlTquanInfoMapper) {
		this.mysqlTquanInfoMapper = mysqlTquanInfoMapper;
	}

	@Override
	public Class<OperationInfo> dataClass() throws Exception {
		// TODO Auto-generated method stub
		return OperationInfo.class;
	}
}
