//用于定时任务，发送 机器cpu，mem，disk 等信息，暂时没这个需求，使用注释掉这个类
//package com.ttmv.datacenter.sentry.handle;
//
//import com.ttmv.datacenter.sentry.SentryAgent;
//import com.ttmv.datacenter.sentry.tool.CheckData;
//import net.sf.json.JSONObject;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hyperic.sigar.*;
//import java.net.InetAddress;
//import java.text.DateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by zbs on 15/10/20.
// */
//public class PatrolSentry extends SentryAgent{
//
//    private static Logger logger = LogManager.getLogger(PatrolSentry.class);
//
//    private Sigar sigar = null;
//    private String serverType;
//    private String serverId;
//    private String port;
//
//    public PatrolSentry(String serverType,String serverId,String port){
//        this.serverType = serverType;
//        this.serverId = serverId;
//        this.port = port;
//    }
//
//    @Override
//    public void start(){
//        sigar = new Sigar();
//        Thread thread = new Thread(new Runnable() {
//            public void run() {
//                try {
//                    String ip = InetAddress.getLocalHost().getHostAddress();
//                    Map<String,Object> map = new HashMap<String,Object>();
//                    map.put("serverType",serverType);
//                    map.put("serverId", serverId);
//                    map.put("ip",ip );
//                    map.put("port", port);
//                    DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                    map.put("alertTime", dateFormat.format(new Date()));
//                    map.put("Cpu",getCpu() );
//                    map.put("Dist",getDisk() );
//                    map.put("MEM", getMEM());
//                    //对bean对象进行空值判断
//                    CheckData.isEmptyByMap(map);
//                    getHttpRequestPost().sendPost("data=" + JSONObject.fromObject(map).toString());
//                }catch (Exception e){
//                    logger.error("运行异常，请看错误报告。",e);
//                }
//            }
//        });
//        thread.start();
//    }
//
//    /**
//     * 内存占用，单位MB
//     * @return
//     */
//    public String getMEM() throws Exception {
//        try {
//            Mem mem = sigar.getMem();
//            return String.valueOf(mem.getActualUsed()/(1024L * 1024L));
//        } catch (SigarException e) {
//            throw new Exception("获取内存占用率失败",e);
//        }
//    }
//
//    /**
//     * cpu占用率，单位%
//     * @return
//     * @throws Exception
//     */
//    public String getCpu() throws Exception {
//        try {
//            CpuPerc[] cpuList = sigar.getCpuPercList();
//            double cpuValue = -1;
//            int i = 0;
//            for (CpuPerc cpu : cpuList) {
//                if(cpuValue != -1) {
//                    cpuValue = (cpu.getCombined() + cpuValue) / 2;
//                }else{
//                    cpuValue = cpu.getCombined();
//                }
//                logger.debug("第"+ ++i +"块CPU占用率:"+cpu.getCombined());
//            }
//            return String.valueOf(Math.round(cpuValue * 100.0D));
//        } catch (SigarException e) {
//            throw new Exception("获取CPU占用率失败",e);
//        }
//    }
//
//    /**
//     * 获取硬盘信息单位GB
//     * @return
//     * @throws Exception
//     */
//    public String getDisk() throws Exception {
//        try {
//            FileSystem[] fileList = sigar.getFileSystemList();
//            double total = 0;
//            for(FileSystem fileSystem :fileList){
//                if (fileSystem.getType() == 2) {
//                    FileSystemUsage fsu = sigar.getFileSystemUsage(fileSystem.getDirName());
//                    total = total + fsu.getUsed();
//                }
//            }
//            return String.valueOf(Math.round(total/(1000L * 1000L)));
//        }catch (Exception e){
//            throw new Exception("获取硬盘信息失败。",e);
//        }
//    }
//
//    @Override
//    public boolean sendMsg(String serverType, String serverId,String alertMsg,String type) { logger.info("别调用我，我没有这个方法的实现");return false;}
//
//    @Override
//    public boolean sendMsg(String json) {
//        return false;
//    }
//
//}
