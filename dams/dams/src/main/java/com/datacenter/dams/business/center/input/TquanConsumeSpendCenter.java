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
import com.datacenter.dams.input.queue.entity.TquanConsumeFormSpendInfo;
import com.datacenter.dams.input.queue.entity.TquanConsumeSpendInfo;
import com.datacenter.dams.input.queue.woker.TcoinConsumeQueueSpendWorker;
import com.datacenter.dams.util.TriggerConstant;

/**
 * T券 消费中心
 * @author wll
 */
public class TquanConsumeSpendCenter{

	private static Logger logger=LogManager.getLogger(TcoinConsumeQueueSpendWorker.class);
	private RankInter tquanConsumeRankListService;
	private OffLineInter hadoopOffLineTquanSpendService;
	private ActivityInter tquanConsumeActivityService;
	private LevelInter	tquanConsumeLevelService;
	private FormInter tquanConsumeFormService;
	private BillInter tquanBillService;
	public void handler(Object object) throws Exception {
		if(object == null){
			return ;
		}
		
		TquanConsumeSpendInfo tquan = (TquanConsumeSpendInfo)object;
		/* 检查消费数据是否有效*/
		if(!this.checkObjectByForm(tquan)){
			return ;
		}
		/* 将数据放入统计报表的队列 */
		TquanConsumeFormSpendInfo formInfo = new TquanConsumeFormSpendInfo();
		BeanUtils.copyProperties(tquan, formInfo);
		tquanConsumeFormService.handler(formInfo);
		/* 检查消费的数据是否是有效消费 */
		if(!this.checkObject(tquan)){
			return ;
		}
		/* 原始数据放入离线计算队列 */
		hadoopOffLineTquanSpendService.handler(tquan);
		/*经过业务分拣，分离计算数据*/
		tquanConsumeRankListService.handler(tquan);
		/*等级数据*/
		tquanConsumeLevelService.handler(tquan);
		/*吊牌活动数据*/
		if(TriggerConstant.ACTIVITY_FLAG){
			tquanConsumeActivityService.handler(tquan);
		}
		/*T券消费数据*/
		tquanBillService.handler(tquan, "-1");
	}
	
	/**
	 * 判断消费数据是不是有效的消费
	 * @param tquanConsumeSpendInfo
	 * @return
	 */
	private boolean checkObject(TquanConsumeSpendInfo tquanConsumeSpendInfo){
		boolean flag = true;
		if(tquanConsumeSpendInfo == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getUserID() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getDestinationUserID() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getProductID() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getProductCount() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getProductPrice() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getNumber() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getUserType() == null){
			return flag = false;
		}
		/* 判断消费者 和 消费的对象是不是同一个人 */
		if(tquanConsumeSpendInfo.getUserID().toString().equals(tquanConsumeSpendInfo.getDestinationUserID().toString())){
			return flag = false;
		}
		return flag ;
	}
	
	/**
	 * 判断消费数据是不是有效的消费
	 * @param tquanConsumeSpendInfo
	 * @return
	 */
	private boolean checkObjectByForm(TquanConsumeSpendInfo tquanConsumeSpendInfo){
		logger.debug("[DAMS#TBConsume]worker读取TB消费队列数据并处理.");
		boolean flag = true;
		if(tquanConsumeSpendInfo == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getUserID() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getDestinationUserID() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getProductID() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getProductCount() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getProductPrice() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getNumber() == null){
			return flag = false;
		}
		if(tquanConsumeSpendInfo.getUserType() == null){
			return flag = false;
		}
		return flag ;
	}
	
	public RankInter getTquanConsumeRankListService() {
		return tquanConsumeRankListService;
	}

	public void setTquanConsumeRankListService(RankInter tquanConsumeRankListService) {
		this.tquanConsumeRankListService = tquanConsumeRankListService;
	}

	public OffLineInter getHadoopOffLineTquanSpendService() {
		return hadoopOffLineTquanSpendService;
	}

	public void setHadoopOffLineTquanSpendService(
			OffLineInter hadoopOffLineTquanSpendService) {
		this.hadoopOffLineTquanSpendService = hadoopOffLineTquanSpendService;
	}

	public ActivityInter getTquanConsumeActivityService() {
		return tquanConsumeActivityService;
	}

	public void setTquanConsumeActivityService(ActivityInter tquanConsumeActivityService) {
		this.tquanConsumeActivityService = tquanConsumeActivityService;
	}

	public LevelInter getTquanConsumeLevelService() {
		return tquanConsumeLevelService;
	}

	public void setTquanConsumeLevelService(LevelInter tquanConsumeLevelService) {
		this.tquanConsumeLevelService = tquanConsumeLevelService;
	}

	public FormInter getTquanConsumeFormService() {
		return tquanConsumeFormService;
	}

	public void setTquanConsumeFormService(FormInter tquanConsumeFormService) {
		this.tquanConsumeFormService = tquanConsumeFormService;
	}

	public BillInter getTquanBillService() {
		return tquanBillService;
	}

	public void setTquanBillService(BillInter tquanBillService) {
		this.tquanBillService = tquanBillService;
	}
	
}
