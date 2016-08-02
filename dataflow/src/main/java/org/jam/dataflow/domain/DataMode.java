package org.jam.dataflow.domain;

/**
 * Created by James on 16/4/27.
 *
 * 1:正常模式  1.5:缓存故障自动切换过渡模式  2:低速模式  2.5:数据恢复中模式  2.9:本实例的数据已经恢复完毕
 * 带小数的模式下系统无法提供写入服务
 *
 */
public enum DataMode {

    HIGH("high mode with cache", 1),
    TOLOW("switching to low mode", 1.5),
    LOW("low mode without cache", 2),
    RECOVERING("recovering data in cache", 2.5),
    RECOVERED("complete recovery from this instance", 2.9);

    private String description;

    private double mode;

    DataMode(String s, double i) {
        this.description = s;
        this.mode = i;
    }

    public static String getDesciption(double mode){
        for(DataMode m : DataMode.values()){
            if(m.getMode() == mode){
                return m.getDescription();
            }
        }
        return null;
    }

    public static DataMode toDataMode(double mode){
        for(DataMode m : DataMode.values()){
            if(m.getMode() == mode){
                return m;
            }
        }
        return null;
    }

    public String getDescription(){
        return this.description;
    }

    public double getMode(){
        return this.mode;
    }
}
