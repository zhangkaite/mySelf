//package com.ttmv.datacenter.paycenter.test;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.Date;
//
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.ttmv.datacenter.paycenter.dao.implement.impl.AccountInfoDaoImpl;
//import com.ttmv.datacenter.paycenter.dao.implement.impl.BrokerageInfoDaoImpl;
//import com.ttmv.datacenter.paycenter.dao.implement.impl.TcoinInfoDaoImpl;
//import com.ttmv.datacenter.paycenter.dao.implement.impl.TquanInfoDaoImpl;
//import com.ttmv.datacenter.paycenter.data.BrokerageInfo;
//import com.ttmv.datacenter.paycenter.data.OperationInfo;
//import com.ttmv.datacenter.paycenter.data.TcoinInfo;
//import com.ttmv.datacenter.paycenter.data.TquanInfo;
//
//
//
//public class TestAccountInfo {
//
//	private static ApplicationContext context = null;
//    private static AccountInfoDaoImpl accountInfoDaoImpl=null;
//    private static BrokerageInfoDaoImpl brokerageInfoDaoImpl=null;
//    private static TcoinInfoDaoImpl tcoinInfoDaoImpl=null;
//    private static TquanInfoDaoImpl tquanInfoDaoImpl=null;
//	static{
//		context = new ClassPathXmlApplicationContext("spring/spring.xml");
//		brokerageInfoDaoImpl = (BrokerageInfoDaoImpl)context.getBean("brokerageInfoDaoImpl");
//	}
//	
//	/***
//	 * 测试生成用户账户初始化
//	 * 包括 T币、T券、充值
//	 */
//	@Test
//	public void TestaddAccount() throws Exception{
//		BigInteger bInteger=new BigInteger("1");
//		try {
//			accountInfoDaoImpl  = (AccountInfoDaoImpl)context.getBean("accountInfoDaoImpl");
//			accountInfoDaoImpl.addAccount(bInteger, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/***
//	 * 测试修改佣金账户信息
//	 * 包括修改账户状态、账户总额、剩余额度
//	 */
//	@Test
//	public void TestBrokerage()throws Exception {
//		
//		BrokerageInfo brokerageInfo=new BrokerageInfo();
//		brokerageInfo.setUserId(new BigInteger("1"));
//		brokerageInfo.setBalance(new BigDecimal("20000"));
//		brokerageInfo.setFreezeBalance(new BigDecimal("10000"));
//		try {
//			brokerageInfoDaoImpl = (BrokerageInfoDaoImpl)context.getBean("brokerageInfoDaoImpl");
//			brokerageInfoDaoImpl.updateBrokerageInfo(brokerageInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//	/**
//	 * 查询佣金账户信息
//	 */
//	
//	@Test
//	public void TestselectBrokerageInfo () throws Exception{
//		BigInteger bInteger=new BigInteger("111111");
//		try {
//			BrokerageInfo bInfo=	brokerageInfoDaoImpl.selectBrokerageInfo(bInteger);
//			System.out.println("账户金额："+bInfo.getBalance()+"账户状态："+bInfo.getAccountStatus()+"账户可用资金:"+bInfo.getFreezeBalance());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
//	/***
//	 * 修改佣金账户额度 hbase 添加数据
//	 */
//	
//	@Test
//	public void TestChangeBalance() throws Exception{
//		OperationInfo op=new OperationInfo();
//		//交易类型（-1：消费，1：充值）
//		op.setType(1);
//		//账户类型（0：TB账户，1：T券账户，2：佣金账户）
//		op.setAccountType(2);
//		op.setNumber(new BigDecimal("100"));
//		op.setUserId(new BigInteger("111111"));
//		op.setOrderId("TT_ORDER_001");
//		op.setDestinationUserID(new BigInteger("222222"));
//		op.setProductID("zkt_001");
//		op.setProductCount(10);
//		op.setTime(new Date());
//		op.setProductPrice(new BigDecimal(100));
//		try {
//				brokerageInfoDaoImpl.changeBalance(op);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	@Test
//	public void moddify()throws Exception{
//		TcoinInfoDaoImpl tcoinInfoDaoImpl  = (TcoinInfoDaoImpl)context.getBean("tcoinInfoDaoImpl");
//		TcoinInfo info = tcoinInfoDaoImpl.selectTcoinInfo(new BigInteger("190"));
//		info.setBalance(new BigDecimal(0));
//		tcoinInfoDaoImpl.updateTcoinInfo(info);
//		
//		
//	}
//	
//	
//	/***
//	 * 加/减 冻结余额
//	 */
//	@Test
//	public void changeFreezeBalance() throws Exception{
//		
//		
//		try {
//			brokerageInfoDaoImpl = (BrokerageInfoDaoImpl)context.getBean("brokerageInfoDaoImpl");
//			brokerageInfoDaoImpl.changeFreezeBalance(new BigInteger("1"), new BigDecimal(2000), 1);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 删除佣金用户
//	 */
//	@Test
//	public void deleletBrokerageInfo() throws Exception{
//		
//		try {
//			brokerageInfoDaoImpl.deleletBrokerageInfo(new BigInteger("1"));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//	/**
//	 * 修改TcoinInfo对象
//	 */
//	
//	@Test
//	public void modifyTcoinInfo() throws Exception{
//		tcoinInfoDaoImpl = (TcoinInfoDaoImpl)context.getBean("tcoinInfoDaoImpl");
//		TcoinInfo tcoinInfo=new TcoinInfo();
//		tcoinInfo.setUserId(new BigInteger("1"));
//		tcoinInfo.setBalance(new BigDecimal("2000"));
//		tcoinInfo.setFreezeBalance(new BigDecimal("4000"));
//		tcoinInfoDaoImpl.updateTcoinInfo(tcoinInfo);
//		
//	}
//	
//	
//	/***
//	 * TcoinInfo 加/减 冻结余额
//	 */
//	@Test
//	public void tcoinInfoChangeFreezeBalance() throws Exception{
//		
//		
//		try {
//			tcoinInfoDaoImpl = (TcoinInfoDaoImpl)context.getBean("tcoinInfoDaoImpl");
//			tcoinInfoDaoImpl.changeFreezeBalance(new BigInteger("111111"), new BigDecimal(2000), -1);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	
//	
//	/***
//	 * TcoinInfo 修改佣金账户额度 hbase 添加数据
//	 */
//	
//	@Test
//	public void TestTcoinInfoChangeBalance() throws Exception{
//		tcoinInfoDaoImpl = (TcoinInfoDaoImpl)context.getBean("tcoinInfoDaoImpl");
//		OperationInfo op=new OperationInfo();
//		//交易类型（-1：消费，1：充值）
//		op.setType(1);
//		//账户类型（0：TB账户，1：T券账户，2：佣金账户）
//		op.setAccountType(0);
//		op.setNumber(new BigDecimal("1000"));
//		op.setUserId(new BigInteger("111111"));
//		op.setOrderId("Tcoin_ORDER_001");
//		op.setDestinationUserID(new BigInteger("2"));
//		op.setProductID("zkt_001");
//		op.setProductCount(10);
//		op.setTime(new Date());
//		op.setProductPrice(new BigDecimal(100));
//		try {
//		Integer a=	tcoinInfoDaoImpl.changeBalance(op);
//		System.out.println("TcoinInfo 充值消费结果:"+a);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	/***
//	 *  TcoinInfo 加/减 冻结余额
//	 */
//	@Test
//	public void changeTcoinInfoFreezeBalance() throws Exception{
//		
//		
//		try {
//			tcoinInfoDaoImpl = (TcoinInfoDaoImpl)context.getBean("tcoinInfoDaoImpl");
//			tcoinInfoDaoImpl.changeFreezeBalance(new BigInteger("1"), new BigDecimal(5000), -1);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 删除TcoinInfo用户
//	 */
//	@Test
//	public void deleletTcoinInfoBrokerageInfo() throws Exception{
//		
//		try {
//			tcoinInfoDaoImpl = (TcoinInfoDaoImpl)context.getBean("tcoinInfoDaoImpl");
//			tcoinInfoDaoImpl.deleletTcoinInfo(new BigInteger("1"));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//	}
//	
//	
//	
//	/**
//	 * 修改Tquan对象
//	 */
//	
//	@Test
//	public void modifyTquanInfo() throws Exception{
//		tquanInfoDaoImpl = (TquanInfoDaoImpl)context.getBean("tquanInfoDaoImpl");
//		TquanInfo tcoinInfo=new TquanInfo();
//		tcoinInfo.setUserId(new BigInteger("1"));
//		tcoinInfo.setBalance(new BigDecimal("2000"));
//		tcoinInfo.setFreezeBalance(new BigDecimal("1000"));
//		tquanInfoDaoImpl.updateTquanInfo(tcoinInfo);
//		
//	}
//	
//	
//	/***
//	 * Tquan 加/减 冻结余额
//	 */
//	@Test
//	public void TquanInfoChangeFreezeBalance() throws Exception{
//		
//		
//		try {
//			tquanInfoDaoImpl = (TquanInfoDaoImpl)context.getBean("tquanInfoDaoImpl");
//			tquanInfoDaoImpl.changeFreezeBalance(new BigInteger("1"), new BigDecimal(200), 1);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	
//	
//	/***
//	 * Tquan 修改佣金账户额度 hbase 添加数据
//	 */
//	
//	@Test
//	public void TestTquanInfoChangeBalance() throws Exception{
//		tquanInfoDaoImpl = (TquanInfoDaoImpl)context.getBean("tquanInfoDaoImpl");
//		OperationInfo op=new OperationInfo();
//		//交易类型（-1：消费，1：充值）
//		op.setType(1);
//		//账户类型（0：TB账户，1：T券账户，2：佣金账户）
//		op.setAccountType(1);
//		op.setNumber(new BigDecimal("1000"));
//		op.setUserId(new BigInteger("1"));
//		op.setOrderId("Tquan_ORDER_001");
//		op.setDestinationUserID(new BigInteger("2"));
//		op.setProductID("zkt_001");
//		op.setProductCount(10);
//		op.setTime(new Date());
//		op.setProductPrice(new BigDecimal(100));
//		try {
//			tquanInfoDaoImpl.changeBalance(op);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	/***
//	 *  TcoinInfo 加/减 冻结余额
//	 */
//	@Test
//	public void changeTquanInfoFreezeBalance() throws Exception{
//		
//		
//		try {
//			tquanInfoDaoImpl = (TquanInfoDaoImpl)context.getBean("tquanInfoDaoImpl");
//			tquanInfoDaoImpl.changeFreezeBalance(new BigInteger("1"), new BigDecimal(500), -1);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 删除TcoinInfo用户
//	 */
//	@Test
//	public void deleletTquanInfoBrokerageInfo() throws Exception{
//		
//		try {
//			tquanInfoDaoImpl = (TquanInfoDaoImpl)context.getBean("tquanInfoDaoImpl");
//			tquanInfoDaoImpl.deleletTquanInfo(new BigInteger("1"));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//	}
//	
//}
