//package com.datacenter.worker.worker;
//
//import java.util.List;
//import java.util.ArrayList;
//
//public class QuartzWorkerExample extends QuartzWorker<String> {
//
//    public QuartzWorkerExample(Logger logger, int processDataBatch){
//        super(logger, processDataBatch);
//    }
//
//    /*
//     * 获取worker每次处理的一组数据
//     */
//    private abstract List<String> getData(int processDataBatch){
//        List<String> result = new ArrayList<String>();
//        for(int i = 0; i < processDataBatch; i++){
//            result.add("data" + i);
//        }
//        return result;
//    }
//
//    /*
//     * worker处理每条数据逻辑
//     */
//    private abstract void process(String d) throws Exception{
//        System.out.println("QuartzWorkerExample process data: " + data);
//    }
//
//    /*
//     * 每条数据记录日志的形式
//     */
//    private abstract String toLog(String d){
//        return d + " toLog";
//    }
//}
