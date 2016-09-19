package com.ttmv.datacenter.paycenter.service.facade.impl.account;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.paycenter.domain.protocol.TBConsume;
import com.ttmv.datacenter.paycenter.domain.protocol.TQConsume;
import com.ttmv.datacenter.paycenter.domain.protocol.TQorTBConsume;
import com.ttmv.datacenter.paycenter.service.facade.template.AbstractPayCenter;
import com.ttmv.datacenter.paycenter.service.facade.tools.ResponseTool;
import com.ttmv.datacenter.paycenter.service.processor.constant.ErrorCodeConstant;

/**  
 * @author Damon_Zs   
 * @version 创建时间：2016年3月21日14:51:21
 * @explain :T券或T币消费
 * @Ps:两种账户不能组合扣钱，要么扣券或者扣币（二选一），原则优先扣T券
 */
@SuppressWarnings({ "rawtypes"})
public class TQorTBConsumeServiceImpl  extends AbstractPayCenter{
	
	private static Logger logger = LogManager.getLogger(TQorTBConsumeServiceImpl.class);
	private  TQConsumeServiceImpl tqConsumeService;
	private  TBConsumeServiceImpl tbConsumeService;


	@Override
	public Map handler(Object object, String reqID) {
		logger.info("[" + reqID + "]@@" + "[T券或TB消费]_开始逻辑处理...");
		// 数据检查
		TQorTBConsume tQorTBConsume = null;
		try {
			tQorTBConsume = this.checkData(object);
		} catch (Exception e) {
			logger.error("[" + reqID + "]@@" + "数据校验失败_" + e.getMessage());
			return ResponseTool.returnError(ErrorCodeConstant.SYSTEM_PARAMETER_ERROR_CODE,ErrorCodeConstant.ERRORMSG_PARAMETERNULL_CODE);
		}
		//扣TQ
		Map resMap = tqConsumeService.handler(this.createTQConsume(tQorTBConsume), reqID);
		if(ErrorCodeConstant.ERRORMSG_ACCOUNTNSF_CODE.equals(resMap.get("errorMsg").toString())){//T券扣除失败，且错误码为TQ余额不足，继续扣TB
			//扣TB
			resMap = tbConsumeService.handler(this.createTBConsume(tQorTBConsume), reqID);
		}
		logger.info("[" + reqID + "]@@" + "[T券或TB消费]_处理完成...");
		return resMap;
	}
	
	
	/**
	 * T劵消费对象
	 * @param tQorTBConsume
	 * @return
	 */
	private TQConsume createTQConsume(TQorTBConsume tQorTBConsume){
		TQConsume tqConsume = new TQConsume();
		tqConsume.setUserID(tQorTBConsume.getUserID());//用户ID
		tqConsume.setDestinationUserID(tQorTBConsume.getDestinationUserID());//消费对象
		tqConsume.setRoomID(tQorTBConsume.getRoomID());//频道ID
		tqConsume.setTime(tQorTBConsume.getTime());//消费时间
		tqConsume.setProductID(tQorTBConsume.getProductID());//商品编号
		tqConsume.setProductCount(tQorTBConsume.getProductCount());//商品数量
		tqConsume.setProductPrice(tQorTBConsume.getProductPriceTQ());//商品T券价格
		tqConsume.setEquipID(tQorTBConsume.getEquipID());//道具编号
		tqConsume.setUserType(tQorTBConsume.getUserType());//消费类型
		tqConsume.setNumber(tQorTBConsume.getNumberTQ());//TQ总额
		tqConsume.setOrderId(tQorTBConsume.getOrderId());//订单编号
		tqConsume.setClientType(tQorTBConsume.getClientType());//设备类型
		tqConsume.setVersion(tQorTBConsume.getVersion());//版本号
		return tqConsume;
	}
	
	/**
	 * T劵消费对象
	 * @param tQorTBConsume
	 * @return
	 */
	private TBConsume createTBConsume(TQorTBConsume tQorTBConsume){
		TBConsume TbConsume = new TBConsume();
		TbConsume.setUserID(tQorTBConsume.getUserID());//用户ID
		TbConsume.setDestinationUserID(tQorTBConsume.getDestinationUserID());//消费对象
		TbConsume.setRoomID(tQorTBConsume.getRoomID());//频道ID
		TbConsume.setTime(tQorTBConsume.getTime());//消费时间
		TbConsume.setProductID(tQorTBConsume.getProductID());//商品编号
		TbConsume.setProductCount(tQorTBConsume.getProductCount());//商品数量
		TbConsume.setProductPrice(tQorTBConsume.getProductPriceTB());//商品T券价格
		TbConsume.setNumber(tQorTBConsume.getNumberTB());//TB总额
		TbConsume.setOrderId(tQorTBConsume.getOrderId());//订单编号
		TbConsume.setClientType(tQorTBConsume.getClientType());//设备类型
		TbConsume.setVersion(tQorTBConsume.getVersion());//版本号
		return TbConsume;
	}
	
	/**
	 * 
	 * 数据校验
	 * @param object
	 * @return
	 * @throws Exception
	 */
	private TQorTBConsume checkData(Object object) throws Exception {
		TQorTBConsume tQorTBConsume = (TQorTBConsume) object;
		if (tQorTBConsume == null) {
			throw new Exception("[对象转换失败！！！]");
		}
		if(tQorTBConsume.getUserID() == null){
			throw new Exception("[UserID为空！！！]");
		}
		if(tQorTBConsume.getDestinationUserID() == null){
			throw new Exception("[消费对象_DestinationUserID为空！！！]");
		}
		if(tQorTBConsume.getTime() == null){
			throw new Exception("[消费时间_Time为空！！！]");
		}
		if(tQorTBConsume.getProductID() == null){
			throw new Exception("[商品编号_ProductID为空！！！]");
		}
		if(tQorTBConsume.getProductCount() == null){
			throw new Exception("[消费数量_ProductCount为空！！！]");
		}
		if(tQorTBConsume.getProductPriceTB() == null){
			throw new Exception("[TB商品单价_ProductPriceTB为空！！！]");
		}
		if(tQorTBConsume.getProductPriceTQ() == null){
			throw new Exception("[TQ商品单价_ProductPriceTQ为空！！！]");
		}
		if(tQorTBConsume.getUserType() == null){
			throw new Exception("[用户类型_UserType为空！！！]");
		}
		if(tQorTBConsume.getNumberTB() == null){
			throw new Exception("[消费金额_NumberTB为空！！！]");
		}
		if(tQorTBConsume.getNumberTQ() == null){
			throw new Exception("[消费金额_NumberTQ为空！！！]");
		}
		if(tQorTBConsume.getOrderId() == null){
			throw new Exception("[OrderId为空！！！]");
		}
		if(tQorTBConsume.getClientType() == null){
			throw new Exception("[ClientType为空！！！]");
		}
		if(tQorTBConsume.getVersion() == null){
			throw new Exception("[Version为空！！！]");
		}
	
		return tQorTBConsume;
	}

	public TQConsumeServiceImpl getTqConsumeService() {
		return tqConsumeService;
	}

	public void setTqConsumeService(TQConsumeServiceImpl tqConsumeService) {
		this.tqConsumeService = tqConsumeService;
	}

	public TBConsumeServiceImpl getTbConsumeService() {
		return tbConsumeService;
	}

	public void setTbConsumeService(TBConsumeServiceImpl tbConsumeService) {
		this.tbConsumeService = tbConsumeService;
	}


	
	
	
	
}
