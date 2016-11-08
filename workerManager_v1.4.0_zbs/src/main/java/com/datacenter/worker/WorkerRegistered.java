package com.datacenter.worker;

import com.datacenter.worker.common.Constant;
import com.datacenter.worker.common.JsonTool;
import com.datacenter.worker.manager.WorkerManager;
import com.datacenter.worker.manager.WorkerWatcher;
import com.datacenter.worker.manager.ZKManager;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.Set;

/**
 * 把需要监听的worker全部注册到这，用zk进行监听管理
 *
 */
public class WorkerRegistered {

    private CuratorFramework zk;
    //父路径
    private String parent;
    //轮训器列表
    private Scheduler sched;


    public WorkerRegistered(CuratorFramework zk, String parent, Scheduler sched) throws Exception {
        this.zk = zk;
        this.parent = parent;
        this.sched = sched;
    }

    /**
     * 注册并启动
     */
    public void start() throws Exception {
        if (!zk.getState().equals(CuratorFrameworkState.STARTED)) {
            zk.start();
        }
        sched.startDelayed(5); //延迟5秒启动
        sched.pauseAll(); //暂停全部
        for (String groupName : sched.getJobGroupNames()) {
            Set<JobKey> jobKeys = sched.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
            for (JobKey jobKey : jobKeys) {
                createNode(jobKey);
                CuratorWatcher watcher = new WorkerWatcher(zk, new WorkerManager(jobKey, sched));
                zk.getData().usingWatcher(watcher).forPath(parent + "/" + jobKey.getName() + "/" + Constant.CRON_NODE_NAME);
                zk.getData().usingWatcher(watcher).forPath(parent + "/" + jobKey.getName() + "/" + Constant.PARA_NODE_NAME);
                zk.getData().usingWatcher(watcher).forPath(parent + "/" + jobKey.getName() + "/" + Constant.SWITCH_NODE_NAME);
                startScheduler(jobKey);
            }
        }
    }

    private void startScheduler(JobKey jobKey) throws Exception {
        String nodeName = jobKey.getName();
        String str = new String(zk.getData().forPath(parent + "/" + nodeName +"/"+ Constant.SWITCH_NODE_NAME));
        if(str.equals(Constant.SWITCH_START))
            sched.resumeJob(jobKey);
    }

    private void createNode(JobKey jobKey) throws Exception {
        String nodeName = jobKey.getName();
        String node = parent + "/" + nodeName;
        String cron_node= node +"/"+ Constant.CRON_NODE_NAME;
        String para_node= node +"/"+ Constant.PARA_NODE_NAME;
        String switch_node = node +"/"+ Constant.SWITCH_NODE_NAME;
        WorkerManager workerManager = new WorkerManager(jobKey,sched);
        ZKManager zkManager = new ZKManager(zk);

        if (!zkManager.checkExists(parent)) {
            throw new Exception("zk上没有父节点 " + parent + "请先在zk上创建。");
        }
        //如果没有worker name 节点，创建这个节点
        if (!zkManager.checkExists(node))
            zkManager.createAndUpdateNode(node,"");
        //如果zk上有配置调度器时间配置，默认用zk上的配置,如果不存在就新建一个cron配置节点，值为""
        if (!zkManager.checkExists(cron_node)) {
            zkManager.createAndUpdateNode(cron_node,"");
        } else if(zkManager.getNodeString(cron_node) != null){
            workerManager.changeCron(zkManager.getNodeString(cron_node).trim());
        }
        //如果zk上有配置属性的值，默认用zk上的配置,如果不存在就新建一个节点，值为""
        if (!zkManager.checkExists(para_node)) {
            zkManager.createAndUpdateNode(para_node,"");
        } else if(zkManager.getNodeString(para_node) != null){
            workerManager.changeAttribute(JsonTool.getMapByString(zkManager.getNodeString(para_node)));
        }
        //如果在课上没有配置开关，就创建一个开关，并设置值为stop
        if (!zkManager.checkExists(switch_node))
             zkManager.createAndUpdateNode(switch_node,Constant.SWITCH_STOP);
    }



}
