package com.ttmv.monitoring.chartService.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.tools.constant.ChartConstant;
import com.ttmv.monitoring.chartService.tools.util.ResultUtil;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * Created by zbs on 15/10/17.
 */
public abstract class QueryListDate extends ChartServiceInf {

    private static Logger logger = LogManager.getLogger(QueryListDate.class);

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
        query.setIp(reqMap.get("ip") == null ? null : reqMap.get("ip").toString());
        query.setServerType(reqMap.get("server_type") == null ? null : reqMap.get("server_type").toString());
        query.setPort((reqMap.get("port") == null ||"".equals(reqMap.get("port")) ? 0 : Integer.parseInt(reqMap.get("port").toString())));
        query.setSum(ChartConstant.SUM_120);
        addAttributesToQuery(reqMap, query);
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
        List<Map> listMap = new ArrayList<Map>();
        for(int i=0;i<result.size();i++) {
            Map map = dataTOResultObject(result.get(i));
            listMap.add(map);
        }
        return ResultUtil.getListRevers(listMap);
    }

}
