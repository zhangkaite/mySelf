package com.datacenter.worker.manager;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;

public class ZKManager {

    private CuratorFramework zk;

    public ZKManager(CuratorFramework zk){
        this.zk = zk;
    }

    /**
     * 根据节点名获得节点的值，如果获取的是"",返回null
     * @param path
     * @return
     * @throws Exception
     */
    public String getNodeString(String path) throws Exception {
        if(checkExists(path)){
            String node = new String(zk.getData().forPath(path));
            return node == null || node.trim().equals("") ? null:node;
        }
        return null;
    }

    /**
     * 创建节点 或 更新节点的值
     * @param path
     * @param value
     * @throws Exception
     */
    public void createAndUpdateNode(String path,String value) throws Exception {
        if (value == null)
            value = "";
        if(!checkExists(path)){
            zk.create().forPath(path,value.getBytes());
        }
        zk.setData().forPath(path,value.getBytes());
    }

    /**
     * 检查节点是否存在
     * @param path
     * @return
     * @throws Exception
     */
    public boolean checkExists(String path) throws Exception {
        return zk.checkExists().forPath(path) == null ? false : true;
    }

    /**
     * 启动zk
     * @return
     */
    public ZKManager start(){
        if(!zk.getState().equals(CuratorFrameworkState.STARTED))
            zk.start();
        return this;
    }

    /**
     * 获得链接
      * @return
     */
    public CuratorFramework getConnection(){
        return zk;
    }

}
