package com.springapp.mvc.receives;



import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springapp.util.JsonUtil;
import com.ttmv.monitoring.filter.FiltratorAgent;
import com.ttmv.monitoring.interfaceService.InterServerInf;
import com.ttmv.monitoring.interfaceService.impl.alert.WarningServiceImpl;

/**
 * Created by zbs on 15/9/28.
 */

@Controller
public class ReceivesController {
	
	@Resource(name = "warningServiceImpl")
	WarningServiceImpl warningServiceImpl;

	@Resource(name = "avServerTransmitServiceDataReportImpl")
    InterServerInf avServerTransmitServiceDataReportImpl;
	
	@Resource(name = "avServerControlServiceDataReportImpl")
    InterServerInf avServerControlServiceDataReportImpl;

    @Resource(name = "screenshotServiceDataReportImpl")
    InterServerInf screenshotServiceDataReportImpl;

    @Resource(name = "transcodingServiceDataReportImpl")
    InterServerInf transcodingServiceDataReportImpl;

    @Resource(name = "phpLiveServiceDataReportImpl")
    InterServerInf phpLiveServiceDataReportImpl;

    @Resource(name = "phpVideoServiceDataReportImpl")
    InterServerInf phpVideoServiceDataReportImpl;

    @Resource(name = "phpManageServiceDataReportImpl")
    InterServerInf phpManageServiceDataReportImpl;

    @Resource(name = "imShowLbsServiceDataReportImpl")
    InterServerInf imShowLbsServiceDataReportImpl;

    @Resource(name = "imShowMdsServiceDataReportImpl")
    InterServerInf imShowMdsServiceDataReportImpl;

    @Resource(name = "imShowMtsServiceDataReportImpl")
    InterServerInf imShowMtsServiceDataReportImpl;

    @Resource(name = "imShowTasServiceDataReportImpl")
    InterServerInf imShowTasServiceDataReportImpl;

    @Resource(name = "imShowUmsServiceDataReportImpl")
    InterServerInf imShowUmsServiceDataReportImpl;

    @Resource(name = "imShowPrsServiceDataReportImpl")
    InterServerInf imShowPrsServiceDataReportImpl;

    @Resource(name = "imShowRmsServiceDataReportImpl")
    InterServerInf imShowRmsServiceDataReportImpl;

    @Resource(name = "imShowMssServiceDataReportImpl")
    InterServerInf imShowMssServiceDataReportImpl;

    @Resource(name = "imShowHttpProxyServiceDataReportImpl")
    InterServerInf imShowHttpProxyServiceDataReportImpl;

    @Resource(name = "dataSourceIPFilter")
    private FiltratorAgent dataSourceIPFilter;

    private static Logger logger = LogManager.getLogger(ReceivesController.class);


    private static String msg = "ERR_408";

    /**
     * 白名单验证，如果是非白名单IP传入的数据报出异常，拒绝数据。
     * @param ip
     * @throws IOException
     */
    private boolean filterByIp(String ip) throws Exception {
        if(!dataSourceIPFilter.checkWhite(ip)) {
            logger.warn("【拒绝来至"+ip+"的数据，该IP未在请求白名单中】");
            return false;
        }
        return true;
    }

    private String handler(Object serviceObject,String data,String ip) {
    	String res = new String();
        logger.info("【接收来自" + ip + "的数据】data=" + data);
        if (data == null || "".equals(data)) {
            logger.warn("没有收到任何数据，请查询是否有发送数据");       
            res = "ERR_400";
        } else {
        	Map map = null;
            if(serviceObject instanceof WarningServiceImpl){
            	map = ((WarningServiceImpl)serviceObject).handler((Map) JsonUtil.getObjectFromJson(data, Map.class));
            }else {
            	map = ((InterServerInf)serviceObject).execute((Map) JsonUtil.getObjectFromJson(data, Map.class));
            }
            res = map.get("resultCode").toString();   		        
        }
        return res;
    }

    /**
     * 09000_服务器直接报警
     * @param request
     * @param response
     */
    @RequestMapping(value = "alertingDataReport", method = RequestMethod.POST)
    public void alertingDataReport(HttpServletRequest request, HttpServletResponse response){
    	
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip)){
            	String str = handler(warningServiceImpl,data,ip);
                response.getWriter().print(str);
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09001_流媒体控制服务器监控数据上报  avServerControlServiceDataReport
     * @param request
     * @param response
     */
    @RequestMapping(value = "avServerControlServiceDataReport", method = RequestMethod.POST)
    public void avServerControlDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip)){
                response.getWriter().print(handler(avServerControlServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09002_流媒体转发服务器监控数据上报  avServerTransmitServiceDataReport
     * @param request
     * @param response
     */
    @RequestMapping(value = "avServerTransmitServiceDataReport", method = RequestMethod.POST)
    public void avServerTransmitDataReport(HttpServletRequest request, HttpServletResponse response) {
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip)){
                
                response.getWriter().print( handler(avServerTransmitServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09003_截图服务器监控数据上报  screenshotServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "screenshotServiceDataReport", method = RequestMethod.POST)
    public void screenshotDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(screenshotServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09004_转码服务器监控数据上报  transcodingServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "transcodingServiceDataReport", method = RequestMethod.POST)
    public void transcodingDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(transcodingServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09005_PHP直播服务器监控数据上报  phpLiveServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "phpLiveServiceDataReport", method = RequestMethod.POST)
    public void phpLiveDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip)){
                
                response.getWriter().print( handler(phpLiveServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09006_PHP点播服务器监控数据上报 phpVideoServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "phpVideoServiceDataReport", method = RequestMethod.POST)
    public void phpVideoDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(phpVideoServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09007_PHP管控中心服务器监控数据上报 phpManageServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "phpManageServiceDataReport", method = RequestMethod.POST)
    public void phpManageDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(phpManageServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09008_IM&秀场LBS数据上报  imShowLbsServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowLbsServiceDataReport", method = RequestMethod.POST)
    public void imShowLbsDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(imShowLbsServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09009_IM&秀场MDS数据上报  imShowMdsServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowMdsServiceDataReport", method = RequestMethod.POST)
    public void imShowMdsDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(imShowMdsServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09010_IM&秀场MTS数据上报  imShowMtsServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowMtsServiceDataReport", method = RequestMethod.POST)
    public void imShowMtsDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip)){
                
                response.getWriter().print( handler(imShowMtsServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09011_IM&秀场TAS数据上报  imShowTasServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowTasServiceDataReport", method = RequestMethod.POST)
    public void imShowTasDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(imShowTasServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09012_IM&秀场UMS数据上报  imShowUmsServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowUmsServiceDataReport", method = RequestMethod.POST)
    public void imShowUmsDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(imShowUmsServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     * 09013_IM&秀场PRS数据上报  imShowPrsServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowPrsServiceDataReport", method = RequestMethod.POST)
    public void imShowPrsDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip)){
                
                response.getWriter().print( handler(imShowPrsServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     *09014_IM&秀场RMS数据上报  imShowRmsServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowRmsServiceDataReport", method = RequestMethod.POST)
    public void imShowRmsDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip)){
                
                response.getWriter().print( handler(imShowRmsServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     *09015_IM&秀场MSS数据上报  imShowMssServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowMssServiceDataReport", method = RequestMethod.POST)
    public void imShowMssDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
                
                response.getWriter().print(handler(imShowMssServiceDataReportImpl,data,ip));
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

    /**
     *09016_IM&秀场HTTPPROXY数据上报  imShowHttpProxyServiceDataReportImpl
     * @param request
     * @param response
     */
    @RequestMapping(value = "imShowHttpProxyServiceDataReport", method = RequestMethod.POST)
    public void imShowHttpProxyDataReport(HttpServletRequest request, HttpServletResponse response){
        String data = request.getParameter("data");
        String ip = request.getRemoteAddr();
        try {
            if(filterByIp(ip) ){
            	String res = handler(imShowHttpProxyServiceDataReportImpl,data,ip);
                response.getWriter().print(res);
            }else{
                response.setStatus(403);
                response.getWriter().print(msg);
            }
        } catch (Exception e) {
            response.setStatus(405);
            logger.error("接收信息程序出现错误。",e);
        }
    }

//    /**
//     *09017_数据中心userCenter数据上报
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public void userCenterDataReport(HttpServletRequest request, HttpServletResponse response){
//        String data = request.getParameter("data");
//        String ip = request.getRemoteAddr();
//        try {
//            filterByIp(response,ip);
//            handler(,data,ip,response);
//        } catch (Exception e) {
//            response.setStatus(405);
//            logger.error("接收信息程序出现错误。",e);
//        }
//    }
//
//    /**
//     *09018_数据中心payCenter数据上报
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "", method = RequestMethod.POST)
//    public void userCenterDataReport(HttpServletRequest request, HttpServletResponse response){
//        String data = request.getParameter("data");
//        String ip = request.getRemoteAddr();
//        try {
//            filterByIp(response,ip);
//            handler(,data,ip,response);
//        } catch (Exception e) {
//            response.setStatus(405);
//            logger.error("接收信息程序出现错误。",e);
//        }
//    }

}
