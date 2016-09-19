package com.ttmv.monitoring.alerterService;

import com.ttmv.monitoring.entity.*;
import com.ttmv.monitoring.webService.entity.MonitoringInitBean;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.annotation.Resource;

/**
 * Created by zbs on 15/9/25.
 */
public class AlerterWork {
	private static Logger logger = LogManager.getLogger(AlerterWork.class);

	@Resource(name = "monitoringInitBean")
	private MonitoringInitBean monitoringInitBean;
	//----未做-----
	@Resource(name = "hpServerAlerter")
	private AlerterServerInf hpServerAlerter;
	@Resource(name = "lbsServerAlerter")
	private AlerterServerInf lbsServerAlerter;
	@Resource(name = "mdsServerAlerter")
	private AlerterServerInf mdsServerAlerter;
	@Resource(name = "mssServerAlerter")
	private AlerterServerInf mssServerAlerter;
	@Resource(name = "mtsServerAlerter")
	private AlerterServerInf mtsServerAlerter;
	@Resource(name = "phpManagerServerAlerter")
	private AlerterServerInf phpManagerServerAlerter;
	@Resource(name = "phpVideoServerAlerter")
	private AlerterServerInf phpVideoServerAlerter;
    @Resource(name = "prsServerAlerter")
    private AlerterServerInf prsServerAlerter;
	@Resource(name = "rmsServerAlerter")
	private AlerterServerInf rmsServerAlerter;
	@Resource(name = "tasServerAlerter")
	private AlerterServerInf tasServerAlerter;
	@Resource(name = "umsServerAlerter")
	private AlerterServerInf umsServerAlerter;
	//----已做-----
	@Resource(name = "mediaForwardAlerter")
	private AlerterServerInf mediaForwardAlerter;
	@Resource(name = "phpServerAlerter")
	private AlerterServerInf phpServerAlerter;
	@Resource(name = "mediaControlAlerter")
	private AlerterServerInf mediaControlAlerter;
	@Resource(name = "screenShotAlerter")
	private AlerterServerInf screenShotAlerter;
	@Resource(name = "transcodingAlerter")
	private AlerterServerInf transcodingAlerter;

	// 运行主方法，默认30秒一轮询，在spring中配置
	public void start() {
		if (monitoringInitBean == null || monitoringInitBean.getNewDataMap() == null || monitoringInitBean.getNewDataMap().size() < 1) {
			logger.debug("没有收到新的监控数据...");
		} else {
			Map<String, Object> newDatas = monitoringInitBean.getNewDataMap();
			logger.debug("==============开始对传入的数据进行扫描==============");
			for (Map.Entry<String, Object> entry : newDatas.entrySet()) {
				Object object = entry.getValue();
				run(object);
			}
		}
	}

	/**
	 * 对传入的数据进行检查
	 * @param obj
	 */
	private void run(Object obj) {
		try {
			if (obj == null)
				throw new Exception("传入的需要检查的对象为空，请检查消息Map");
			AlerterServerInf server = getAlerterServerImpl(obj);
			if(server == null) {
				logger.warn("如果没有查找到对应的检查器,没找到的检查器名字为：" + obj.getClass().getName());
				return;
			}
			server.checkAndSendEmail(obj);
		} catch (Exception e) {
			logger.error("检查器出现异常！", e);
		}
	}

	/**
	 * 根据放入的数据的类型,选择不同的检查器去检查
	 * @param obj
	 * @return
	 */
	private AlerterServerInf getAlerterServerImpl(Object obj){
		AlerterServerInf server = null;
		if(obj instanceof HpServerData){
			server = hpServerAlerter;
		}
		if(obj instanceof LbsServerData){
			server = lbsServerAlerter;
		}
		if(obj instanceof MdsServerData){
			server = mdsServerAlerter;
		}
		if(obj instanceof MediaControlData){
			server = mediaControlAlerter;
		}
		if (obj instanceof MediaForwardData) {
			server = mediaForwardAlerter;
		}
		if (obj instanceof MssServerData){
			server = mssServerAlerter;
		}
		if (obj instanceof MtsServerData){
			server = mtsServerAlerter;
		}
		if (obj instanceof PhpManagerServerData){
			server = phpManagerServerAlerter;
		}
		if (obj instanceof PHPServerData){
			server = phpServerAlerter;
		}
		if (obj instanceof PhpVideoServerData){
			server = phpVideoServerAlerter;
		}
		if (obj instanceof PrsServerData){
			server = prsServerAlerter;
		}
		if (obj instanceof RmsServerData){
			server = rmsServerAlerter;
		}
		if (obj instanceof ScreenShotData){
			server = screenShotAlerter;
		}
		if (obj instanceof TasServerData){
			server = tasServerAlerter;
		}
		if (obj instanceof TranscodingData){
			server = transcodingAlerter;
		}
		if (obj instanceof UmsServerData){
			server = umsServerAlerter;
		}
		logger.debug("调用"+server.getClass().getSimpleName()+"检查器进行检查");
        return server;
	}

}
