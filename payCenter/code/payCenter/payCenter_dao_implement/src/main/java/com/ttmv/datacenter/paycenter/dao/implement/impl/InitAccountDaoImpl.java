package com.ttmv.datacenter.paycenter.dao.implement.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ttmv.datacenter.paycenter.dao.implement.constant.AccountConstant;
import com.ttmv.datacenter.paycenter.dao.implement.flow.InitAccountService;
import com.ttmv.datacenter.paycenter.dao.implement.util.TableIdGenerate;
import com.ttmv.datacenter.paycenter.dao.interfaces.InitAccountDao;
import com.ttmv.datacenter.paycenter.data.InitAccount;

public class InitAccountDaoImpl implements InitAccountDao{
	
	private InitAccountService initAccountService;
	private TableIdGenerate tableIdGenerate; 
	
	public void initAccount(BigInteger userid,String reqID) throws Exception {
		/* 唯一标识id */
		BigInteger id = tableIdGenerate.getTableId("AccountTableID");
		InitAccount initAccount = new InitAccount();
		initAccount.setReqId(reqID);
		initAccount.setId(id);
		initAccount.setUserId(userid);
		initAccount.setBalance(new BigDecimal(0.0));
		initAccount.setFreezeBalance(new BigDecimal(0.0));
		initAccount.setAccountStatus(AccountConstant.NORMAL);
		initAccountService.write(initAccount);
	}

	public InitAccountService getInitAccountService() {
		return initAccountService;
	}

	public void setInitAccountService(InitAccountService initAccountService) {
		this.initAccountService = initAccountService;
	}

	public TableIdGenerate getTableIdGenerate() {
		return tableIdGenerate;
	}

	public void setTableIdGenerate(TableIdGenerate tableIdGenerate) {
		this.tableIdGenerate = tableIdGenerate;
	}
	
	
	
}
