package com.ttmv.datacenter.sentry.handle;

import com.ttmv.datacenter.sentry.SentryAgent;
import com.ttmv.datacenter.sentry.tool.CheckData;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetAddress;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zbs on 15/10/20.
 */
public class QuickSentry extends SentryAgent {

    private static Logger logger = LogManager.getLogger(QuickSentry.class);

    /**
     * 把传入的数据进行检查，并放到队列中。
     * @param serverType
     * @param serverId
     * @param alertMsg
     * @param type
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public boolean sendMsg(String serverType,String serverId, String alertMsg,String type) {
        try {
            Map map = new HashMap();
            map.put("ServerType", serverType);
            map.put("serverId",serverId);
            map.put("IP", InetAddress.getLocalHost().getHostAddress());
            DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("alertTime", dateFormat.format(new Date(System.currentTimeMillis())));
            map.put("type", type);
            map.put("errorMsg", alertMsg);
            CheckData.isEmptyByMap(map);
            getQueue().add("data=" + JSONObject.fromObject(map).toString());
            return true;
        }catch (Exception e){
            logger.error("数据格式有错误，请检查。",e);
        }
        return false;
    }

    @Override
    public String expressSendHttp(String json) {
        try {
            logger.debug("发送http请求：" + json);
            return getHttpRequestPost().sendPost(json);
        } catch (Exception e) {
            logger.error("发送数据失败，请看错误日志。", e);
        }
        return null;
    }

    /**
     *  新建立一个线程进行发送，主要为避免在等待监控服务器返回时候对整个业务系统产出阻塞。
     *  而使用Spring时间进行控制的原因是为在避免在大并发，很多线程同时在报告异常时，每一个报告都创建一个新线程，
     *  而使用比如10秒调用一次观察者，如果队列中有数据，就对队列（Queue）中的数据进行发送。
     *  这样能保证在10秒中不管有多少异常报告都只新建一个线程去处理。
     */
    @Override
    public void start(){
        Thread thread = new Thread(new Runnable() {
            public void run() {
                String param = null;
                while((param=(String)getQueue().poll()) != null){
                    try{
                        logger.debug("发送http请求："+param);
                        getHttpRequestPost().sendPost(param);
                    }catch (Exception e){
                        logger.error("发送数据失败，请看错误日志。",e);
                    }
                }
            }
        });
        thread.start();
    }

}
