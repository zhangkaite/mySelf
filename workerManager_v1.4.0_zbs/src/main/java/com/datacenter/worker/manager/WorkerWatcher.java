package com.datacenter.worker.manager;

import com.datacenter.worker.common.Constant;
import com.datacenter.worker.common.JsonTool;
import com.datacenter.worker.common.Validate;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerWatcher implements CuratorWatcher {
    private static final Logger logger = LoggerFactory.getLogger(WorkerWatcher.class);
    private WorkerManager workerManager;
    private ZKManager zk;

    public WorkerWatcher(CuratorFramework zk,WorkerManager workerManager) {
        this.zk = new ZKManager(zk);
        this.workerManager = workerManager;
    }

    @Override
    public void process(WatchedEvent watchedEvent) throws Exception {
        String path = watchedEvent.getPath();
        String node = this.getChangeNode(path);
        try {
            zk.start();
            if (watchedEvent.getType() != Watcher.Event.EventType.NodeDataChanged)
                return;
            logger.info("节点改变 : path=" + path + "  node=" + node);
            //对修改进行操作
            changeCron(node, path);
            changePara(node, path);
            changeSwitch(node, path);
        }catch (Exception e){
            logger.error("worker节点修改有异常",e);
        }finally {
            //重新加入监听
            zk.getConnection().getData().usingWatcher(this).forPath(path);
        }
    }

    /**
     * 修改时间
     * @param node
     * @param path
     * @throws Exception
     */
    private void changeCron(String node,String path) throws Exception {
        if(node!= null && node.equals(Constant.CRON_NODE_NAME)){
            String cron = zk.getNodeString(path);
            if(cron != null && !"".equals(cron))
                workerManager.changeCron(cron).resume();
        }
    }

    /**
     * 修改参数
     * @param node
     * @param path
     * @throws Exception
     */
    private void changePara(String node,String path) throws Exception {
        if(node!= null && node.equals(Constant.PARA_NODE_NAME)){
            String attr = zk.getNodeString(path);
            workerManager.changeAttribute(JsonTool.getMapByString(attr));
        }
    }

    /**
     * 修改开关
     * @param node
     * @param path
     * @throws Exception
     */
    private void changeSwitch(String node,String path) throws Exception {
        if(node.equals(Constant.SWITCH_NODE_NAME)){
            String flag = zk.getNodeString(path);
            if(flag.equals(Constant.SWITCH_START))
                workerManager.start();
            if(flag.equals(Constant.SWITCH_STOP))
                workerManager.stop();
        }
    }

    /**
     * 获取最后一节子节点
     * @param path
     * @return
     */
    private String getChangeNode(String path){
        if(Validate.checkParameter(path))
            return null;
        String[] spl = path.split("/");
        int lg = spl.length;
        return spl[lg-1];
    }

}
