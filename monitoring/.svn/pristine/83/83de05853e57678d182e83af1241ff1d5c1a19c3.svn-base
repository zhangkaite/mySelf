package com.ttmv.monitoring.chartService.impl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.tools.constant.ChartConstant;
import com.ttmv.monitoring.chartService.tools.util.DateUtil;
import com.ttmv.monitoring.chartService.tools.util.ResultUtil;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;

/**
 * Created by zbs on 15/10/16.
 )*/
public abstract class QueryListByDate extends ChartServiceInf {

    private static Logger logger = LogManager.getLogger(QueryListByDate.class);
    private static final Integer INTERAL_10 = 10;
    private static final Integer INTERAL_30 = 30;
    private static final Integer PRESET_TIME_10 = -20;
    private static final Integer PRESET_TIME_30 = -1;
    private static final Integer SUM = 120;

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
        Integer interval = Integer.parseInt(reqMap.get("interval").toString());
        query.setPort((reqMap.get("port") == null ||"".equals(reqMap.get("port")) ? 0 : Integer.parseInt(reqMap.get("port").toString())));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currDate = new Date();
		query.setCurentTime(sdf.format(currDate));
		/* 查询前20分钟 */
		if(interval.equals(INTERAL_10)){			
			query.setPreviousTime(sdf.format(DateUtil.getQueryFixedTime(currDate,ChartConstant.DATE_MINUE, PRESET_TIME_10)));
		}
		/* 查询前一个小时 */
		if(interval.equals(INTERAL_30)){			
			query.setPreviousTime(sdf.format(DateUtil.getQueryFixedTime(currDate,ChartConstant.DATE_HOUR, PRESET_TIME_30)));
		}
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
    protected Object getResData(List result) throws Exception{
        /*当前时间*/
        Date date = new Date();
        List list = dealData(result,date);
        List<Map> listMap = new ArrayList<Map>();
        for(int i=0;i<list.size();i++){
            Map map = dataTOResultObject(list.get(i));
            listMap.add(map);
        }
        return ResultUtil.getListRevers(listMap);
    }

    /**
     * 处理预置节点
     * @param result
     * @return
     */
    protected List dealData(List result,Date currDate) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    	Integer interval = Integer.parseInt(reqMap.get("interval").toString());
        List list = new ArrayList();
        if(result != null && result.size() > 0 &&result.get(0) != null){
            //一共120点
            int tmp = result.size();
            for(int i=0;i<this.SUM;i++){
                Date date =	DateUtil.getQueryFixedTime(currDate, ChartConstant.DATE_SECOND, -(i*interval));
                if(i < tmp){
                    Object obj = result.get(i);
                    Class c = obj.getClass();
                    Method setCreateTime = c.getDeclaredMethod("setCreateTime",Date.class);
                    Method setTimestamp = c.getDeclaredMethod("setTimestamp",Date.class);
                    setCreateTime.invoke(obj,date);
                    setTimestamp.invoke(obj,date);
                    list.add(obj);
                }else if(i >= tmp){
                    Object obj = initData(date);
                    list.add(obj);
                }
            }
        }else{
            for(int i=0;i<this.SUM;i++){
                Date date =	DateUtil.getQueryFixedTime(currDate, ChartConstant.DATE_SECOND, -(i*interval));
                Object media =initData(date);
                list.add(media);
            }
        }
        return list;
    }
}
