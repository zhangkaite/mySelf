package com.ttmv.monitoring.chartService.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.tools.constant.ChartConstant;
import com.ttmv.monitoring.chartService.tools.util.DateUtil;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * Created by zbs on 15/10/17.
 */
public abstract class QueryData extends ChartServiceInf {

    private static Logger logger = LogManager.getLogger(QueryData.class);
    private static final Integer INTERAL = 300;
    /**
     * 创建请求对象
     *
     * @return
     */
    @Override
    protected Object getDataObject(Map reqMap) throws Exception {
        if(reqMap == null || reqMap.size() < 1)
            throw new Exception("创建请求对象时传入的参数为空");
        DataBeanQuery query =new  DataBeanQuery();
        Date date = new Date();
        query.setIp(reqMap.get("ip").toString());
        query.setServerType(reqMap.get("server_type").toString());
        query.setPort((reqMap.get("port") == null ||"".equals(reqMap.get("port")) ? 0 : Integer.parseInt(reqMap.get("port").toString())));
        String strDate = DateUtil.getStringTime(DateUtil.getQueryFixedTime(date, ChartConstant.DATE_SECOND, -INTERAL));
        query.setCurentTime(strDate);
        return query;
    }

    protected abstract void addAttributesToQuery(Map reqMap,Object data)throws Exception;

    /**
     * 执行查询，并返回结果
     * @param result
     * @return
     */
    @Override
    protected Object getResData(List result) throws Exception {
        Date date = new Date();
       if(result != null && result.size() > 0 &&result.get(0) != null){
    	   Object obj = result.get(0);
           Map map = dataTOResultObject(obj);
           map.put(ChartConstant.GEN_CREATETIME, date);
           return map;
       }else{
    	   Object data = initData(date);
           Map map = dataTOResultObject(data);
           return map;
       }       
    }
}
