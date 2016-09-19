package com.datacenter.dams.business.center;

import com.datacenter.dams.business.center.input.AddUserCenter;
import com.datacenter.dams.business.center.input.BrokerageConsumeSpendCenter;
import com.datacenter.dams.business.center.input.BrokerageRechargeServiceCenter;
import com.datacenter.dams.business.center.input.FlowerConsumeCenter;
import com.datacenter.dams.business.center.input.MonthDownLevelCenter;
import com.datacenter.dams.business.center.input.StarExpIncrementCenter;
import com.datacenter.dams.business.center.input.StarOnlineSpendCenter;
import com.datacenter.dams.business.center.input.TakeHeartConsumeCenter;
import com.datacenter.dams.business.center.input.TcoinConsumeSpendCenter;
import com.datacenter.dams.business.center.input.TcoinRechargeServiceCenter;
import com.datacenter.dams.business.center.input.TquanConsumeSpendCenter;
import com.datacenter.dams.business.center.input.TquanRechargeServiceCenter;
import com.datacenter.dams.business.center.input.UserExpIncrementCenter;
import com.datacenter.dams.business.center.input.UserExpResetCenter;
import com.datacenter.dams.business.center.input.UserLoginCenter;
import com.datacenter.dams.business.center.input.UserOnlineSpendCenter;
import com.datacenter.dams.business.center.output.ActivityRankListResultCenter;
import com.datacenter.dams.business.center.output.ActivitySwingTagResultCenter;
import com.datacenter.dams.business.center.output.ActivityUserRankListResultCenter;
import com.datacenter.dams.business.center.output.FamouserRankListResultCenter;
import com.datacenter.dams.business.center.output.HadoopOffLineSpendCenter;
import com.datacenter.dams.business.center.output.RicherRankListResultCenter;
import com.datacenter.dams.business.center.output.UserExpOutPutCenter;
import com.datacenter.dams.business.center.output.UserLevelupOutputCenter;

/**
 * 队列信息处理中心
 * @author wll
 */
public  class MessageHandlerCenter{
	
	public static TcoinConsumeSpendCenter tcoinConsumeSpendCenter;
	public static TquanConsumeSpendCenter tquanConsumeSpendCenter ;
	public static FamouserRankListResultCenter famouserRankListResultCenter;
	public static RicherRankListResultCenter richerRankListResultCenter;
	public static HadoopOffLineSpendCenter hadoopOffLineSpendCenter;
	public static ActivityRankListResultCenter activityRankListResultCenter;
	public static ActivityUserRankListResultCenter activityUserRankListResultCenter;
	public static ActivitySwingTagResultCenter activitySwingTagResultCenter;
	public static FlowerConsumeCenter flowerConsumeCenter;
	public static TakeHeartConsumeCenter takeHeartConsumeCenter;
	public static MonthDownLevelCenter monthDownLevelCenter;
	public static StarExpIncrementCenter starExpIncrementCenter;
	public static UserExpIncrementCenter userExpIncrementCenter;
	public static StarOnlineSpendCenter starOnlineSpendCenter;
	public static UserOnlineSpendCenter userOnlineSpendCenter;
	public static UserLevelupOutputCenter userLevelupOutputCenter;
	public static UserExpOutPutCenter userExpOutPutCenter;
	public static UserExpResetCenter userExpResetCenter;
	public static TcoinRechargeServiceCenter tcoinRechargeServiceCenter;
	public static TquanRechargeServiceCenter tquanRechargeServiceCenter;
	public static BrokerageConsumeSpendCenter brokerageConsumeSpendCenter;
	public static BrokerageRechargeServiceCenter brokerageRechargeServiceCenter;
	public static UserLoginCenter userLoginCenter;
	public static AddUserCenter addUserCenter;
	
	public static TcoinConsumeSpendCenter getTcoinConsumeSpendCenter() {
		return tcoinConsumeSpendCenter;
	}
	public static void setTcoinConsumeSpendCenter(
			TcoinConsumeSpendCenter tcoinConsumeSpendCenter) {
		MessageHandlerCenter.tcoinConsumeSpendCenter = tcoinConsumeSpendCenter;
	}
	public static TquanConsumeSpendCenter getTquanConsumeSpendCenter() {
		return tquanConsumeSpendCenter;
	}
	public static void setTquanConsumeSpendCenter(
			TquanConsumeSpendCenter tquanConsumeSpendCenter) {
		MessageHandlerCenter.tquanConsumeSpendCenter = tquanConsumeSpendCenter;
	}
	public static FamouserRankListResultCenter getFamouserRankListResultCenter() {
		return famouserRankListResultCenter;
	}
	public static void setFamouserRankListResultCenter(
			FamouserRankListResultCenter famouserRankListResultCenter) {
		MessageHandlerCenter.famouserRankListResultCenter = famouserRankListResultCenter;
	}
	public static RicherRankListResultCenter getRicherRankListResultCenter() {
		return richerRankListResultCenter;
	}
	public static void setRicherRankListResultCenter(
			RicherRankListResultCenter richerRankListResultCenter) {
		MessageHandlerCenter.richerRankListResultCenter = richerRankListResultCenter;
	}
	public static HadoopOffLineSpendCenter getHadoopOffLineSpendCenter() {
		return hadoopOffLineSpendCenter;
	}
	public static void setHadoopOffLineSpendCenter(
			HadoopOffLineSpendCenter hadoopOffLineSpendCenter) {
		MessageHandlerCenter.hadoopOffLineSpendCenter = hadoopOffLineSpendCenter;
	}
	public static ActivityRankListResultCenter getActivityRankListResultCenter() {
		return activityRankListResultCenter;
	}
	public static void setActivityRankListResultCenter(
			ActivityRankListResultCenter activityRankListResultCenter) {
		MessageHandlerCenter.activityRankListResultCenter = activityRankListResultCenter;
	}
	public static ActivitySwingTagResultCenter getActivitySwingTagResultCenter() {
		return activitySwingTagResultCenter;
	}
	public static void setActivitySwingTagResultCenter(
			ActivitySwingTagResultCenter activitySwingTagResultCenter) {
		MessageHandlerCenter.activitySwingTagResultCenter = activitySwingTagResultCenter;
	}
	public static ActivityUserRankListResultCenter getActivityUserRankListResultCenter() {
		return activityUserRankListResultCenter;
	}
	public static void setActivityUserRankListResultCenter(
			ActivityUserRankListResultCenter activityUserRankListResultCenter) {
		MessageHandlerCenter.activityUserRankListResultCenter = activityUserRankListResultCenter;
	}
	public static FlowerConsumeCenter getFlowerConsumeCenter() {
		return flowerConsumeCenter;
	}
	public static void setFlowerConsumeCenter(FlowerConsumeCenter flowerConsumeCenter) {
		MessageHandlerCenter.flowerConsumeCenter = flowerConsumeCenter;
	}
	public static TakeHeartConsumeCenter getTakeHeartConsumeCenter() {
		return takeHeartConsumeCenter;
	}
	public static void setTakeHeartConsumeCenter(TakeHeartConsumeCenter takeHeartConsumeCenter) {
		MessageHandlerCenter.takeHeartConsumeCenter = takeHeartConsumeCenter;
	}
	public static MonthDownLevelCenter getMonthDownLevelCenter() {
		return monthDownLevelCenter;
	}
	public static void setMonthDownLevelCenter(MonthDownLevelCenter monthDownLevelCenter) {
		MessageHandlerCenter.monthDownLevelCenter = monthDownLevelCenter;
	}
	public static StarExpIncrementCenter getStarExpIncrementCenter() {
		return starExpIncrementCenter;
	}
	public static void setStarExpIncrementCenter(StarExpIncrementCenter starExpIncrementCenter) {
		MessageHandlerCenter.starExpIncrementCenter = starExpIncrementCenter;
	}
	public static UserExpIncrementCenter getUserExpIncrementCenter() {
		return userExpIncrementCenter;
	}
	public static void setUserExpIncrementCenter(UserExpIncrementCenter userExpIncrementCenter) {
		MessageHandlerCenter.userExpIncrementCenter = userExpIncrementCenter;
	}
	public static StarOnlineSpendCenter getStarOnlineSpendCenter() {
		return starOnlineSpendCenter;
	}
	public static void setStarOnlineSpendCenter(StarOnlineSpendCenter starOnlineSpendCenter) {
		MessageHandlerCenter.starOnlineSpendCenter = starOnlineSpendCenter;
	}
	public static UserOnlineSpendCenter getUserOnlineSpendCenter() {
		return userOnlineSpendCenter;
	}
	public static void setUserOnlineSpendCenter(UserOnlineSpendCenter userOnlineSpendCenter) {
		MessageHandlerCenter.userOnlineSpendCenter = userOnlineSpendCenter;
	}
	public static UserLevelupOutputCenter getUserLevelupOutputCenter() {
		return userLevelupOutputCenter;
	}
	public static void setUserLevelupOutputCenter(UserLevelupOutputCenter userLevelupOutputCenter) {
		MessageHandlerCenter.userLevelupOutputCenter = userLevelupOutputCenter;
	}
	public static UserExpOutPutCenter getUserExpOutPutCenter() {
		return userExpOutPutCenter;
	}
	public static void setUserExpOutPutCenter(UserExpOutPutCenter userExpOutPutCenter) {
		MessageHandlerCenter.userExpOutPutCenter = userExpOutPutCenter;
	}
	public static UserExpResetCenter getUserExpResetCenter() {
		return userExpResetCenter;
	}
	public static void setUserExpResetCenter(UserExpResetCenter userExpResetCenter) {
		MessageHandlerCenter.userExpResetCenter = userExpResetCenter;
	}
	public static TcoinRechargeServiceCenter getTcoinRechargeServiceCenter() {
		return tcoinRechargeServiceCenter;
	}
	public static void setTcoinRechargeServiceCenter(TcoinRechargeServiceCenter tcoinRechargeServiceCenter) {
		MessageHandlerCenter.tcoinRechargeServiceCenter = tcoinRechargeServiceCenter;
	}
	public static TquanRechargeServiceCenter getTquanRechargeServiceCenter() {
		return tquanRechargeServiceCenter;
	}
	public static void setTquanRechargeServiceCenter(TquanRechargeServiceCenter tquanRechargeServiceCenter) {
		MessageHandlerCenter.tquanRechargeServiceCenter = tquanRechargeServiceCenter;
	}
	public static BrokerageConsumeSpendCenter getBrokerageConsumeSpendCenter() {
		return brokerageConsumeSpendCenter;
	}
	public static void setBrokerageConsumeSpendCenter(BrokerageConsumeSpendCenter brokerageConsumeSpendCenter) {
		MessageHandlerCenter.brokerageConsumeSpendCenter = brokerageConsumeSpendCenter;
	}
	public static BrokerageRechargeServiceCenter getBrokerageRechargeServiceCenter() {
		return brokerageRechargeServiceCenter;
	}
	public static void setBrokerageRechargeServiceCenter(BrokerageRechargeServiceCenter brokerageRechargeServiceCenter) {
		MessageHandlerCenter.brokerageRechargeServiceCenter = brokerageRechargeServiceCenter;
	}
	public static UserLoginCenter getUserLoginCenter() {
		return userLoginCenter;
	}
	public static void setUserLoginCenter(UserLoginCenter userLoginCenter) {
		MessageHandlerCenter.userLoginCenter = userLoginCenter;
	}
	public static AddUserCenter getAddUserCenter() {
		return addUserCenter;
	}
	public static void setAddUserCenter(AddUserCenter addUserCenter) {
		MessageHandlerCenter.addUserCenter = addUserCenter;
	}
}
