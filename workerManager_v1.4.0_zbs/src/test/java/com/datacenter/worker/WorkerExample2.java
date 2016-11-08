package com.datacenter.worker;

import com.datacenter.worker.worker.QuartzWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zbs on 15/12/31.
 */
public class WorkerExample2  extends QuartzWorker {

    private int count = 0;

    @Override
    protected List getData(int processDataBatch) {
        List<String> data = new ArrayList<String>();
        Random rand = new Random();
        for(int i =0;i<100;i++){
            data.add(String.valueOf(rand.nextInt(10000)));
        }
        List resp = data.subList(count,count+processDataBatch);
        count = count+processDataBatch;
        return resp;
    }

    @Override
    protected void process(Object d) throws Exception {
        System.out.println("Example2 : "+(String)d);
    }

    @Override
    protected String toLog(Object d) {
        return (String)d;
    }

//    @Override
//    protected String getWorkerName() {
//        return "example2";
//    }

}
