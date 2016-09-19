package com.ttmv.monitoring.interfaceService.template;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.entity.MonitoringInitBean;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * 服务监控平台接口 （模板）
 * 
 * @author Damon_Zs
 * @time 2015年9月15日18:00:11
 */

@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public abstract class AbstractMonitorBase extends FacadeService {

	private MonitoringInitBean monitoringInitBean;
	private static Logger logger = LogManager
			.getLogger(AbstractMonitorBase.class);

	// 服务处理接口
	public abstract int handler(Object obj) throws Exception;

	public Map execute(Map reqMap) {
		// 一、数据校验null
		try {
			this.checkData(reqMap);
		} catch (Exception e) {
			logger.error("数据非空校验失败!!!" + e.getMessage());
			return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
		}
		// 二、获取添加对象
		Object object = null;
		try {
			object = getDataObject(reqMap);
		} catch (Exception e) {
			logger.error("传入参数缺失!!! && " + e.getMessage());
			return ResponseTool.returnError();
		}
		if (object == null) {
			logger.error("入库对象组装为NULL");
			return ResponseTool.returnError();
		}
		// 三、 数据入库
		try {
			this.handler(object);
		} catch (Exception e) {
			logger.error("入库失败！！！", e);
			return ResponseTool.returnError();
		}
		// 四、 更新最新数据（报警器引擎依赖对象）
		Map newDataMap = new HashMap();
		newDataMap.put(
				reqMap.get("ServerType").toString() + "_"
						+ reqMap.get("serverId").toString() + "_"
						+ reqMap.get("IP").toString() + "_"
						+ reqMap.get("Port").toString(), object);
		monitoringInitBean.setNewDataMap(newDataMap);
		// 五、返回
		return ResponseTool.returnSuccess();
	}

	/**
	 * 创建请求对象
	 * 
	 * @return
	 */
	protected abstract Object getDataObject(Map reqMap) throws Exception;

	/**
	 * 检查参数值是否为空（Map数据全部校验）
	 */
	public void checkData(Map<String, Object> reqMap) throws Exception {
		for (Map.Entry<String, Object> set : reqMap.entrySet()) {
			String key = set.getKey();
			Object object = set.getValue();
			if (object == null || "".equals(object)) {
				throw new DataFormatException("数据校验失败:["
						+ this.getClass().getSimpleName() 
							+ "_[" + key + "] is null...]");
			}
		}
	}

	/*
	 * 判断bean的正确性
	 */
	public void isEmptyByBean(Object bean) throws Exception {
		Method[] methods = bean.getClass().getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("get") && !methodName.equals("getClass")) {// 如果方法名以get开头
				Object value = method.invoke(bean);// 调用方法,并打印返回值
				if (value == null
						|| (value instanceof String && "".equals(value))
						|| (value instanceof Integer && (Integer) value == 0)) {
					throw new Exception(bean.getClass().getSimpleName() + "对象中"
							+ methodName + "方法返回值为空。");
				}
			}
		}

	}

	public MonitoringInitBean getMonitoringInitBean() {
		return monitoringInitBean;
	}

	public void setMonitoringInitBean(MonitoringInitBean monitoringInitBean) {
		this.monitoringInitBean = monitoringInitBean;
	}

}
