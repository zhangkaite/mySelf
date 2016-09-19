package com.ttmv.monitoring.chartService.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.tools.constant.ChartConstant;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * Created by zbs on 15/10/17.
 */
public abstract class QuerySelectedPort extends ChartServiceInf {

    private static Logger logger = LogManager.getLogger(QuerySelectedPort.class);

    /**
     * 创建请求对象
     *
     * @return
     */
    @Override
    protected Object getDataObject(Map reqMap) throws Exception {
        if(reqMap == null || reqMap.size() < 1)
            throw new Exception("创建请求对象时传入的参数为空");
        DataBeanQuery query = new DataBeanQuery();
        query.setServerType(reqMap.get("server_type").toString());
        query.setIp(reqMap.get("ip").toString());
        query.setGroupField(ChartConstant.GROUP_BY_PORT);
        addAttributesToQuery(reqMap,query);
        return query;
    }


    protected abstract void addAttributesToQuery(Map reqMap,DataBeanQuery query);


    /**
     * 执行查询，并返回结果
     * @param result
     * @return
     */
    @Override
    protected Object getResData(List result) throws Exception {
        List<Map> list = new ArrayList<Map>();
        for(int i=0;i<result.size();i++){
            Object data = result.get(i);
            Map map = new HashMap();
            Method getPort = data.getClass().getDeclaredMethod("getPort");
            map.put("value",getPort.invoke(data));
            map.put("text", getPort.invoke(data));
            list.add(map);
        }
        return list;
    }


}
