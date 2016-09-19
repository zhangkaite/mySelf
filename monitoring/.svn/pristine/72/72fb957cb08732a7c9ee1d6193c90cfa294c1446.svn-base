package com.ttmv.monitoring.chartService.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by zbs on 15/10/17.
 */
public abstract class QueryDataById extends ChartServiceInf {

    private static Logger logger = LogManager.getLogger(QueryDataById.class);
    private static final Integer INTERAL = 30;
    /**
     * 创建请求对象
     *
     * @return
     */
    @Override
    protected Object getDataObject(Map reqMap) throws Exception {
        if(reqMap == null || reqMap.size() < 1)
            throw new Exception("创建请求对象时传入的参数为空");
        BigInteger id = new BigInteger(reqMap.get("id").toString());
        return id;
    }

    protected abstract void addAttributesToQuery(Map reqMap,Object data)throws Exception;

    /**
     * 执行查询，并返回结果
     * @param result
     * @return
     */
    @Override
    protected Object getResData(List result) throws Exception {
    	if(result != null && result.size() > 0){    		
    		return dataTOResultObject(result.get(0));
    	}
    	return null;
    }

}
