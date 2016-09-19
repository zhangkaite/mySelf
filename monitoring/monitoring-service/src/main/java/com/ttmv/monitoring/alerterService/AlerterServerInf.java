package com.ttmv.monitoring.alerterService;

import com.ttmv.monitoring.alerterService.bean.AlerterLogMessage;
import com.ttmv.monitoring.alerterService.packingData.PackingDataInf;
import com.ttmv.monitoring.alerterService.tool.GeneralUtility;
import com.ttmv.monitoring.entity.AlertRecordInfo;
import com.ttmv.monitoring.entity.ThresholdInfo;
import com.ttmv.monitoring.inter.IAlertRecordInfoInter;
import com.ttmv.monitoring.msgNotification.MessageServiceInf;
import com.ttmv.monitoring.msgNotification.entity.Threshold;
import com.ttmv.monitoring.tools.util.AlerterUtil;
import com.ttmv.monitoring.webService.entity.MonitoringInitBean;
import net.sf.json.JSONArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zbs on 15/9/25.
 */
public abstract class AlerterServerInf {

	@Resource(name = "monitoringInitBean")
	private MonitoringInitBean monitoringInitBean;

	@Resource(name = "messageServiceImpl")
	private MessageServiceInf messageServiceInf;

	@Resource(name = "iAlertRecordInfoInterImpl" )
	private IAlertRecordInfoInter iAlertRecordInfoInterImpl;

	private static Logger logger = LogManager.getLogger(AlerterServerInf.class);

	private Map<String, ThresholdInfo> threshold;
	private Map<String, AlerterLogMessage> message;

	private String alertID = null;
	
	// 恢复邮件内容
	private List<Threshold> recoveryMsg;
	// 报警邮件内容
	private List<Threshold> alarmMsg;

	//恢复邮件类型名称，用于在保存报警时候的类型的值
	private final static String recoveryTypeName = "OK";
    //告警邮件类型名称，用于在保存报警时候的类型的值
	private final static String alarmTypeName = "PROBLEM";

	/**
	 * 初始化
	 * @throws Exception
	 */
	private void verifyAddInit() throws Exception {
		if (monitoringInitBean == null)
			throw new Exception("传入信息为空，请查询monitoringInitBean类是否加载成功");
		this.threshold = monitoringInitBean.getThresholdMap();
		if (threshold == null)
			throw new Exception("传入阀值配置为空，请查数据库阀值信息是否为空");
		this.message = monitoringInitBean.getMonitoringRecord();
		this.recoveryMsg = new ArrayList<Threshold>();
		this.alarmMsg = new ArrayList<Threshold>();
	}

	/**
	 * 检查数据，发送邮件，并且保存日志信息到数据库（主方法）
	 * @throws Exception
	 */
	public void checkAndSendEmail(Object data) throws Exception {
		//把值传入实现子类中初始化
		setData(data);
		//校验数据
		verifyAddInit();
        //设置需要验证的参数
		checkDataHandle();
		if(recoveryMsg !=null && recoveryMsg.size()>0){
			logger.info("【发恢复邮件】");
			messageServiceInf.sendMessage(getPackingDataInf().getParamsMsg(recoveryMsg, 0, alertID));
			AlertRecordInfo alertRecordInfo = getPackingDataInf().getAlertRecordInfo(recoveryTypeName, JSONArray.fromObject(recoveryMsg).toString());
			if(alertRecordInfo != null) {
				iAlertRecordInfoInterImpl.addAlertRecordInfo(alertRecordInfo);
			}else{
				logger.error("保存报警日志信息失败，构造的报警信息alertRecordInfo为空.详细原因请查看上面的报错信息");
			}
		}
		if(alarmMsg != null && alarmMsg.size()>0){
			logger.info("【发报警邮件】");
			messageServiceInf.sendMessage(getPackingDataInf().getParamsMsg(alarmMsg, 1, alertID));
			AlertRecordInfo alertRecordInfo = getPackingDataInf().getAlertRecordInfo(alarmTypeName, JSONArray.fromObject(alarmMsg).toString());
			if(alertRecordInfo != null) {
			    iAlertRecordInfoInterImpl.addAlertRecordInfo(alertRecordInfo);
			}else{
				logger.error("保存报警日志信息失败，构造的报警信息alertRecordInfo为空.详细原因请查看上面的报错信息");
			}
		}
	}
	/**
	 * 设定需要检查的数据
	 * @throws Exception
	 */
	protected abstract void checkDataHandle();

	/**
	 * 把data从实现类传回到这个类中，做一些处理
	 * @return
	 */
	protected abstract Object getData();
	protected abstract void setData(Object data);

	/**
	 * 获取包装数据类，有一个默认实现，如果符合的，可以直接使用默认的实现类
	 * @return
	 * @throws Exception
	 */
	protected abstract PackingDataInf getPackingDataInf() throws Exception;

	/**
	 * 检查是否超过阀值，超过阀值把信息放到邮件记录中，等检查完毕，一并发送邮件。
	 * @param type
	 * @param name
	 * @throws Exception
	 */
	protected void check(String type,String name) {
		//*******检查数据
		Object data = getData();
		if (data == null) {
			logger.error("在" + this.getClass().getSimpleName() + "类的Check方法中传入的data为空。");
			return;
		}
		try {
			logger.debug("开始检查" + name + ".");
			//*******是否有get***方法检查
			Object valueObject = GeneralUtility.getValueByObjectSimple(data, "get" + AlerterUtil.toUpperCaseFirstOne(name));
			if (valueObject == null)
				throw new Exception("请检查" + data.getClass().getSimpleName() + "类中是否有get" + AlerterUtil.toUpperCaseFirstOne(name) + "类或者该方法返回值是否为null");
			//*******值是否是一个Int类型检查
			if (!(valueObject instanceof Integer))
				throw new Exception(data.getClass().getSimpleName() + "类中get" + AlerterUtil.toUpperCaseFirstOne(name) + "方法返回的类型不是Integer类型(只支持对Integer类型的判断)");
			Integer value = (Integer) valueObject;
			//*******值是否大于0检查
			if (value < 0)
				throw new Exception(data.getClass().getSimpleName() + "类中get" + AlerterUtil.toUpperCaseFirstOne(name) + "方法返回的值为负数(不支持负数的判断)");
			//*******是否有这个阀值检查
			String thresholdTemp = getThreshold(type);
			if (thresholdTemp == null || "".equals(thresholdTemp)) {
				logger.warn("没有找到" + data.getClass().getSimpleName() + "对象，对应的类型为" + type + "的阀值");
				return;
			}
			//*******阀值的值的合法性检查
			int[] thresholds = AlerterUtil.getIntArrayByStr(thresholdTemp, "\\|");
			if (thresholds == null || thresholds.length < 1) {
				logger.warn(data.getClass().getSimpleName() + "对象，对应的类型为" + type + "的阀值数据有异常，请检查data=【" + thresholds + "】");
				return;
			}
			//*******正式开始比对值和阀值
			perform(type, value, getState(value, thresholds), thresholds, getPackingDataInf().getMessageName() + "_" + type);
		}catch (Exception e){
			logger.error("检查"+data.getClass().getSimpleName()+"对象"+name+"属性出现异常.",e);
		}
	}

	/**
	 * 根据状态机状态值进行事件触发
	 * @param thresholdName
	 * @param alerterValue
	 * @param state
	 * @param thresholds
	 * @param msgName
	 */
	protected void perform(String thresholdName, int alerterValue, State state, int[] thresholds, String msgName) throws Exception {
		int stateValue = getStateValue(alerterValue, state, thresholds, msgName);
		logger.debug("【状态机值为】" + stateValue);
		// 【没超过阀值，日志中没有，null 】什么都不处理
		if (AlerterUtil.isExtent("000", "002", stateValue)) {
			logger.debug("【没超过阀值，日志中没有，null 】什么都不处理");
			return;
		}
		// 【没有超过阀值，日志中有，null 】发恢复，并删除日志中的消息
		if (AlerterUtil.isExtent("010", "012", stateValue)) {
			logger.debug("【没有超过阀值，日志中有，null 】发恢复，并删除日志中的消息");
			addEmailMsg(thresholdName, String.valueOf(state.getMinThreshold()),
					String.valueOf(alerterValue), 0);
			message.remove(msgName);
			return;
		}
		// 【超过阀值，日志没有，null】 发告警，并在日志中增加一条
		if (AlerterUtil.isExtent("100", "102", stateValue)) {
			logger.debug("【超过阀值，日志没有，null】 发告警，并在日志中增加一条");
			addEmailMsg(thresholdName, String.valueOf(state.getNowThreshold()),
					String.valueOf(alerterValue), 1);
			message.put(msgName, getPackingDataInf().createMsg(thresholdName, String.valueOf(state.getNowThreshold()), String.valueOf(alerterValue)));
			return;
		}
		// 【超过阀值，日志中有，默认或等于上一次阀值】什么都不做
		if (AlerterUtil.isExtent("110", "110", stateValue)) {
			logger.debug("【超过阀值，日志中有，默认或等于上一次阀值】什么都不做");
			return;
		}
		// 【超过阀值，日志中有，小于上一次阀值】发恢复，更新日志中阀值
		if (AlerterUtil.isExtent("111", "111", stateValue)) {
			logger.debug("【超过阀值，日志中有，小于上一次阀值】发恢复，更新日志中阀值");
			addEmailMsg(thresholdName,
					String.valueOf(state.getLargerThreshold()),
					String.valueOf(alerterValue), 0);
			AlerterLogMessage msg = message.get(msgName);
			msg.setThresholdValue(String.valueOf(state.getNowThreshold()));
			msg.setActualValue(String.valueOf(alerterValue));
			return;
		}
		// 【超过阀值，日志中有，大于上一次阀值】发告警，更新日志中阀值
		if (AlerterUtil.isExtent("112", "112", stateValue)) {
			logger.debug("【超过阀值，日志中有，大于上一次阀值】发告警，更新日志中阀值");
			addEmailMsg(thresholdName, String.valueOf(state.getNowThreshold()),
					String.valueOf(alerterValue), 1);
			AlerterLogMessage msg = message.get(msgName);
			msg.setThresholdValue(String.valueOf(state.getNowThreshold()));
			msg.setActualValue(String.valueOf(alerterValue));
			return;
		}
		logger.warn(stateValue + "没有对应的执行内容，可能为一个异常，请检查代码。");
	}

	/**
	 * 获得状态机状态值
	 * 
	 * @param alerterValue
	 * @param state
	 * @param thresholds
	 * @param msgName
	 * @return
	 */
	private int getStateValue(int alerterValue, State state, int[] thresholds,
			String msgName) {
		StringBuffer sb = new StringBuffer();
		// 判断是否达到了最低阀值，如果超过最低阀值返回1,没有超过返回0
		sb.append(moreThanThreshold(alerterValue, thresholds));
		// 判断是否在日志中,如果存在返回1, 没有返回0
		sb.append(isIncludedInMsg(msgName));
		// 判断这次传入的值触动的阀值，是否小于上一触动的阀值，默认，小于，等于 返回0, 大于返回1
		sb.append(compareThreshold(state.getNowThreshold(), msgName));
		return Integer.valueOf(sb.toString());
	}

	/**
	 * 判断是否达到了阀值，默认和没超过返回0， 超过阀值返回 1
	 * 
	 * @param value
	 *            需要做判断的真实的值
	 * @param thresholds
	 *            阀值集合
	 */
	private int moreThanThreshold(int value, int[] thresholds) {
		if (thresholds == null) {
			logger.debug("【判断是否达到了阀值】没有对应的阀值集合，所以默认" + value + " 没触动阀值");
			return 0;
		}
		Arrays.sort(thresholds);
		for (int threshold : thresholds) {
			if (value >= threshold) {
				logger.debug("【判断是否达到了阀值】" + value + " 触动了阀值");
				return 1;
			}
		}
		logger.debug("【判断是否达到了阀值】" + value + " 没触动阀值");
		return 0;
	}

	/**
	 * 判断是否在日志中,默认和没有返回0，存在返回1
	 * 
	 * @param msgName
	 *            报警日志的名字
	 * @return
	 */
	private int isIncludedInMsg(String msgName) {
		if (msgName == null || "".equals(msgName)) {
			logger.debug("【判断是否在日志中】传入的日志名称为null.默认返回不在日志中.");
			return 0;
		}
		AlerterLogMessage msg = message.get(msgName);
		if (msg != null) {
			logger.debug("【判断是否在日志中】在日志中.");
			return 1;
		}
		logger.debug("【判断是否在日志中】不在日志中.");
		return 0;
	}

	/**
	 * 判断这次的阀值是否大于上一次的阀值, 默认和等于返回0，小于返回1， 大于返回2
	 * @param thresholdValue
	 * @param msgName
	 * @return
	 */
	private int compareThreshold(int thresholdValue, String msgName) {
		if (thresholdValue < 0 || msgName == null || "".equals(msgName)) {
			logger.debug("【判断这次的阀值是否大于上一次的阀值】阀值小于0 或则 日志名称为null");
			return 0;
		}
		AlerterLogMessage msg = message.get(msgName);
		if (msg == null) {
			logger.debug("【判断这次的阀值是否大于上一次的阀值】日志中没有该报警记录");
			return 0;
		}
		// 如果在日志中没有消息，代表没有报警过，所以默认返回1；
		int oldthresholdValue = Integer.valueOf(msg.getThresholdValue());
		if (thresholdValue < oldthresholdValue) {
			logger.debug("【判断这次的阀值是否大于上一次的阀值】最新值【小于】日志中阀值 new="
					+ thresholdValue + " old=" + oldthresholdValue);
			return 1;
		}
		if (thresholdValue > oldthresholdValue) {
			logger.debug("【判断这次的阀值是否大于上一次的阀值】最新值【大于】于日志中阀值 new="
					+ thresholdValue + " old=" + oldthresholdValue);
			return 2;
		}
		logger.debug("【判断这次的阀值是否大于上一次的阀值】最新值【等于】日志中阀值 new=" + thresholdValue
				+ " old=" + oldthresholdValue);
		return 0;
	}

	/**
	 * 判断触动了哪个阀值，如果超过阀值，返回[真实的值,触动的阀门值,最小阀值],否则返回null
	 * 
	 * @param value
	 *            需要做判断的值
	 * @param thresholds
	 *            阀值集合
	 */
	protected State getState(int value, int[] thresholds) {
		// 排序从小到大
		Arrays.sort(thresholds);
		State state = new State();
		state.setAlerterValus(value);
		for (int i = 0; i < thresholds.length; i++) {
			int threshold = thresholds[i];
			if (value >= threshold) {
				state.setNowThreshold(threshold);
				state.setLargerThreshold((i + 1) > thresholds.length - 1 ? threshold
						: thresholds[i + 1]);
			}
		}
		state.setMinThreshold(thresholds[0]);
		return state;
	}

	/**
	 * 构建报警或恢复邮件中的超过阀值的信息值
	 * 
	 * @param thresholdName
	 *            阀值的名称
	 * @param thresholdValue
	 *            触动的阀值
	 * @param actualValue
	 *            真实的值
	 * @param mark
	 *            如果为0为恢复邮件，如果为1为报警邮件
	 */
	private void addEmailMsg(String thresholdName, String thresholdValue,
			String actualValue, int mark) {
		Threshold threshold = new Threshold();
		threshold.setActualValue(actualValue);
		threshold.setThresholdName(thresholdName);
		threshold.setThresholdValue(thresholdValue);
		if (mark == 0)
			recoveryMsg.add(threshold);
		if (mark == 1)
			alarmMsg.add(threshold);
	}
	
	/**
	 * 通过传入的type比如cpu，mem等，获得阀值的值
	 * 
	 * @param mark
	 * @return
	 */
	protected String getThreshold(String mark) throws Exception {
		if (mark == null || "".equals(mark)) {
			logger.error("传入的阀值名称为空。在getThreshold()方法中。");
			return null;
		}
		ThresholdInfo thresholdInfo = threshold.get(getPackingDataInf().getServerType() + "_" + mark);
		if(thresholdInfo!=null && this.alertID == null){
			this.alertID = thresholdInfo.getThresholdAlerterIds();
		}
		if (thresholdInfo == null) {
			logger.error("通过阀值名称没有找到对应的阀值。阀值名称:" + getPackingDataInf().getServerType() + "_" + mark);
			return null;
		}
		logger.debug("【" + mark + "的报警阀值为】" + thresholdInfo.getThresholdValue());
		return thresholdInfo.getThresholdValue();
	}

	class State {
		private int alerterValus = -1;
		private int nowThreshold = -1;
		private int largerThreshold = -1;
		private int minThreshold = -1;

		public State() {
		}

		public int getAlerterValus() {return alerterValus;}

		public void setAlerterValus(int alerterValus) {
			this.alerterValus = alerterValus;
		}

		public int getNowThreshold() {
			return nowThreshold;
		}

		public void setNowThreshold(int nowThreshold) {
			this.nowThreshold = nowThreshold;
		}

		public int getLargerThreshold() {
			return largerThreshold;
		}

		public void setLargerThreshold(int largerThreshold) {
			this.largerThreshold = largerThreshold;
		}

		public int getMinThreshold() {
			return minThreshold;
		}

		public void setMinThreshold(int minThreshold) {
			this.minThreshold = minThreshold;
		}
		
		
	}
}
