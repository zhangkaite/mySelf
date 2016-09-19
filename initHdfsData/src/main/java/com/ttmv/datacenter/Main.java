package com.ttmv.datacenter;

/**
 * Created by zkt on 2016/4/1.
 */
public class Main {

    public static  void  main(String[] args){

        String filePath="D://xf.txt";
        String targetHdfsPath="/datacenter/paycenter/statistics/sx_history_20160502/20160502";
        try {
            Service.inserDataToHdfs(filePath,targetHdfsPath);
        } catch (Exception e) {
            System.out.println("数据写入hdfs失败，失败的原因是:");
            e.printStackTrace();
        }


    }


}
