package com.ttmv.monitoring.chartService.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.tools.util.ResultUtil;
import com.ttmv.monitoring.entity.HpServerData;
import com.ttmv.monitoring.entity.LbsServerData;
import com.ttmv.monitoring.entity.MdsServerData;
import com.ttmv.monitoring.entity.MediaControlData;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.entity.MssServerData;
import com.ttmv.monitoring.entity.MtsServerData;
import com.ttmv.monitoring.entity.PHPServerData;
import com.ttmv.monitoring.entity.PhpManagerServerData;
import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.entity.PrsServerData;
import com.ttmv.monitoring.entity.RmsServerData;
import com.ttmv.monitoring.entity.ScreenShotData;
import com.ttmv.monitoring.entity.TasServerData;
import com.ttmv.monitoring.entity.TranscodingData;
import com.ttmv.monitoring.entity.UmsServerData;
import com.ttmv.monitoring.tools.constant.ResultCodeConstant;
import com.ttmv.monitoring.webService.tools.ResponseTool;

/**
 * Created by zbs on 15/10/16.
 */
public abstract class ChartServiceInf {

    private static Logger logger = LogManager.getLogger(ChartServiceInf.class);

    protected  Map reqMap;

    public Map handler(Map reqMap) {
        try {
            //通过这个把值传递给子类
            setReqMap(reqMap);
            logger.info("【" + this.getClass().getSimpleName() + "】 @ start...");
            logger.debug("【" + this.getClass().getSimpleName() + " 开始校验数据】");
            checkData(reqMap);
            logger.debug("【" + this.getClass().getSimpleName() +" 创建请求对象】");
            Object object = getDataObject(reqMap);
            logger.debug("【" + this.getClass().getSimpleName() +" 创建查询对象】");
            List result = getQuery(object);
            logger.debug("【" + this.getClass().getSimpleName() +" 执行查询，并返回结果】");
            Object res = getResData(result);
            return ResponseTool.returnSuccess(res);
        }catch (DataFormatException de){
            logger.error("数据有误",de);
            return ResponseTool.returnError(ResultCodeConstant.RESULTCODE_MISSING_PARAMETERS);
        } catch (Exception e) {
            logger.error("执行失败出现异常!!!",e);
            return ResponseTool.returnError();
        }
    }

    /**
     * 创建请求对象
     *
     * @return
     */
    protected abstract Object getDataObject(Map reqMap) throws Exception;

    /**
     * 创建查询对象
     * @param obj
     * @return
     * @throws Exception
     */
    protected abstract List getQuery(Object obj) throws Exception;

    /**
     * 执行查询，并返回结果
     * @param result
     * @return
     */
    protected abstract Object  getResData(List result) throws Exception;

    /**
     * 数据检查白名单
     * @return
     */
    protected abstract Set<String> getCheckDataWhiteSet();

    /**
     *检查参数是否为空
     */
    public void checkData(Map<String, Object> reqMap) throws Exception {
        Set<String> whiteSet = getCheckDataWhiteSet();
        for(Map.Entry<String, Object> set :reqMap.entrySet()) {
            String key = set.getKey();
            Object object = set.getValue();
            //如果校验白名单中没有说明，表示需要验证
            if (whiteSet != null && !whiteSet.contains(key)) {
                if (object == null || "".equals(object)) {//IP非空验证
                    throw new DataFormatException("数据校验失败:[" + this.getClass().getSimpleName() + "_[" + key + "] is null...]");
                }
            }
        }
    }

    /**
     * 根据不同的类型，返回不同的get...TOResultObject
     * @param obj
     * @return
     */
    protected Map dataTOResultObject(Object obj){
        if(obj instanceof MediaForwardData){
            return ResultUtil.getMediaForwardDataTOResultObject((MediaForwardData) obj);
        }else if(obj instanceof MediaControlData){
            return ResultUtil.getMediaControlDataTOResultObject((MediaControlData)obj);
        }else if(obj instanceof PHPServerData){
            return ResultUtil.getPHPServerDataTOResultObject((PHPServerData)obj);
        }else if(obj instanceof ScreenShotData){
            return ResultUtil.getScreenShotDataTOResultObject((ScreenShotData)obj);
        }else if(obj instanceof TranscodingData){
            return ResultUtil.getTranscodingDataTOResultObject((TranscodingData)obj);
        }else if(obj instanceof PhpVideoServerData){
        	return ResultUtil.getPhpVideoServerDataTOResultObject((PhpVideoServerData)obj);
        }else if(obj instanceof PhpManagerServerData){
        	return ResultUtil.getPhpManagerServerDataTOResultObject((PhpManagerServerData)obj);
        }else if(obj instanceof HpServerData){
        	return ResultUtil.getHpServerDataTOResultObject((HpServerData)obj);
        }else if(obj instanceof LbsServerData){
        	return ResultUtil.getLbsServerDataTOResultObject((LbsServerData)obj);
        }else if(obj instanceof MdsServerData){
        	return ResultUtil.getMdsServerDataTOResultObject((MdsServerData)obj);
        }else if(obj instanceof MssServerData){
        	return ResultUtil.getMssServerDataTOResultObject((MssServerData)obj);
        }else if(obj instanceof MtsServerData){
        	return ResultUtil.getMtsServerDataTOResultObject((MtsServerData)obj);
        }else if(obj instanceof PrsServerData){
        	return ResultUtil.getPrsServerDataTOResultObject((PrsServerData)obj);
        }else if(obj instanceof UmsServerData){
        	return ResultUtil.getUmsServerDataTOResultObject((UmsServerData)obj);
        }else if(obj instanceof RmsServerData){
        	return ResultUtil.getRmsServerDataTOResultObject((RmsServerData)obj);
        }else if(obj instanceof TasServerData){
        	return ResultUtil.getTasServerDataTOResultObject((TasServerData)obj);
        }
        logger.error("没有找到对应的对象，请查看代码");
        return null;
    }

    /**
     * 根据不同的类型，返回不同的init...Data
     * @return
     */
    protected Object initData(Date date){
        Object obj = getDataType();
        if(obj instanceof MediaForwardData){
            return ResultUtil.initMediaForwardData(date);
        }else if(obj instanceof MediaControlData){
            return ResultUtil.initMediaControlData(date);
        }else if(obj instanceof PHPServerData){
            return ResultUtil.initPHPServerData(date);
        }else if(obj instanceof ScreenShotData){
            return ResultUtil.initScreenShotData(date);
        }else if(obj instanceof TranscodingData){
            return ResultUtil.initTranscodingData(date);
        }else if(obj instanceof PhpVideoServerData){
        	return ResultUtil.initPhpVideoServerData(date);
        }else if(obj instanceof PhpManagerServerData){
        	return ResultUtil.initPhpManagerServerData(date);
        }else if(obj instanceof HpServerData){
        	return ResultUtil.initHpServerData(date);
        }else if(obj instanceof LbsServerData){
        	return ResultUtil.initLbsServerData(date);
        }else if(obj instanceof MdsServerData){
        	return ResultUtil.initMdsServerData(date);
        }else if(obj instanceof MssServerData){
        	return ResultUtil.initMssServerData(date);
        }else if(obj instanceof MtsServerData){
        	return ResultUtil.initMtsServerData(date);
        }else if(obj instanceof PrsServerData){
        	return ResultUtil.initPrsServerData(date);
        }else if(obj instanceof UmsServerData){
        	return ResultUtil.initUmsServerData(date);
        }else if(obj instanceof RmsServerData){
        	return ResultUtil.initRmsServerData(date);
        }else if(obj instanceof TasServerData){
        	return ResultUtil.initTasServerData(date);
        }
        logger.error("没有找到对应的对象，请查看代码");
        return null;
    }

    /**
     * 返回数据类型用于进行init...Data中类型判断
     * @return
     */
    protected abstract Object getDataType();


    protected Map getReqMap(){
        return this.reqMap;
    }
    protected void setReqMap(Map reqMap){
        this.reqMap = reqMap;
    }
}
