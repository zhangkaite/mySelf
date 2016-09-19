package com.datacenter.dams.business.center.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.datacenter.dams.business.service.ActivityInter;
import com.datacenter.dams.business.service.BillInter;
import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.business.service.LevelInter;
import com.datacenter.dams.business.service.OffLineInter;
import com.datacenter.dams.business.service.RankInter;
import com.datacenter.dams.input.queue.entity.TcoinConsumeFormSpendInfo;
import com.datacenter.dams.input.queue.entity.TcoinConsumeSpendInfo;
import com.datacenter.dams.util.TriggerConstant;

/**
 * TB 消费中心
 * @author wll
 */
public class TcoinConsumeSpendCenter{

	private static Logger logger=LogManager.getLogger(TcoinConsumeSpendCenter.class);
	
	private RankInter tcoinConsumeRankListService;
	private OffLineInter hadoopOffLineTcoinSpendService;
	private ActivityInter tcoinConsumeActivityService;
	private LevelInter	tcoinConsumeLevelService;
	private FormInter tcoinConsumeFormService;
	private BillInter tcoinBillService;
	
	public void handler(Object object) throws Exception {
		if(object == null){
			return ;
		}
		TcoinConsumeSpendInfo tcoinConsumeInfo = (TcoinConsumeSpendInfo)object;
		/* 检查消费数据是否有效*/
		if(!this.checkObjectByForm(tcoinConsumeInfo)){
			return ;
		}
		/* 将消费数据写入账单 */
		tcoinBillService.handler(tcoinConsumeInfo,"-1");
		/* 将数据放入统计报表的队列 */
		TcoinConsumeFormSpendInfo formInfo = new TcoinConsumeFormSpendInfo();
		BeanUtils.copyProperties(tcoinConsumeInfo, formInfo);
		tcoinConsumeFormService.handler(formInfo);
		/* 检查消费的数据是否是有效消费 */
		if(!this.checkObject(tcoinConsumeInfo)){
			return ;
		}
		/* 原始数据放入离线计算队列 */
		hadoopOffLineTcoinSpendService.handler(tcoinConsumeInfo);
		/* 分离storm计算数据 */
		tcoinConsumeRankListService.handler(tcoinConsumeInfo);
		/*等级数据*/
		tcoinConsumeLevelService.handler(tcoinConsumeInfo);
		/*吊牌活动数据*/
		if(TriggerConstant.ACTIVITY_FLAG){
			tcoinConsumeActivityService.handler(tcoinConsumeInfo);
		}
	}

	/**
	 * 判断消费数据是不是有效的消费
	 * @param tcoinConsumeInfo
	 * @return
	 */
	private boolean checkObject(TcoinConsumeSpendInfo tcoinConsumeInfo){
		boolean flag = true;
		if(tcoinConsumeInfo == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getUserID() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getDestinationUserID() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getProductID() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getProductCount() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getProductPrice() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getNumber() == null){
			return flag = false;
		}
		/* 判断消费者 和 消费的对象是不是同一个人 */
		if(tcoinConsumeInfo.getUserID().toString().equals(tcoinConsumeInfo.getDestinationUserID().toString())){
			return flag = false;
		}
		return flag ;
	}
	
	/**
	 * 判断统计报表消费数据是不是有效的消费
	 * @param tcoinConsumeInfo
	 * @return
	 */
	private boolean checkObjectByForm(TcoinConsumeSpendInfo tcoinConsumeInfo){
		logger.debug("[DAMS#TBConsume]TB消费数据检验.");
		boolean flag = true;
		if(tcoinConsumeInfo == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getUserID() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getDestinationUserID() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getProductID() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getProductCount() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getProductPrice() == null){
			return flag = false;
		}
		if(tcoinConsumeInfo.getNumber() == null){
			return flag = false;
		}
		return flag ;
	}
	
	public RankInter getTcoinConsumeRankListService() {
		return tcoinConsumeRankListService;
	}

	public void setTcoinConsumeRankListService(RankInter tcoinConsumeRankListService) {
		this.tcoinConsumeRankListService = tcoinConsumeRankListService;
	}

	public OffLineInter getHadoopOffLineTcoinSpendService() {
		return hadoopOffLineTcoinSpendService;
	}

	public void setHadoopOffLineTcoinSpendService(
			OffLineInter hadoopOffLineTcoinSpendService) {
		this.hadoopOffLineTcoinSpendService = hadoopOffLineTcoinSpendService;
	}

	public ActivityInter getTcoinConsumeActivityService() {
		return tcoinConsumeActivityService;
	}

	public void setTcoinConsumeActivityService(
			ActivityInter tcoinConsumeActivityService) {
		this.tcoinConsumeActivityService = tcoinConsumeActivityService;
	}

	public LevelInter getTcoinConsumeLevelService() {
		return tcoinConsumeLevelService;
	}

	public void setTcoinConsumeLevelService(LevelInter tcoinConsumeLevelService) {
		this.tcoinConsumeLevelService = tcoinConsumeLevelService;
	}

	public FormInter getTcoinConsumeFormService() {
		return tcoinConsumeFormService;
	}

	public void setTcoinConsumeFormService(FormInter tcoinConsumeFormService) {
		this.tcoinConsumeFormService = tcoinConsumeFormService;
	}

	public void setTcoinBillService(BillInter tcoinBillService) {
		this.tcoinBillService = tcoinBillService;
	}
}
