package com.ttmv.datacenter.paycenter.triger;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ttmv.datacenter.paycenter.triger.triggerimpl.triggerconstant.Trigger;

public class CheckTriggerTimer {

	/**
	 * 检查TriggerConstant 变量的变化
	 * 根据值调启动线程调用相应的系统任务
	 */
	public void check(){
		System.out.println("扫描的collection开始");
		/* 获取collection */
		Map<String,String> collection = Trigger.getCollection();
		Map<String,Object> collectionObject = Trigger.getCollectionObject();
		/* 没有值 */
		if(collection == null || collection.size() == 0){
			return ;
		}
		/* 不为空 */
		if(collection != null && collection.size() > 0){
			/* 遍历所有的key */
			Set<String> set = collection.keySet();
			Iterator<String> iterator = set.iterator();
			if(iterator.hasNext()){
				String key = iterator.next();
				String value = collection.get(key);
				/* 启动线程执行task */
				String objKey = key+"_"+value;
				if(collectionObject.containsKey(objKey)){					
					BaseTask object = (BaseTask)collectionObject.get(key+"_"+value);
					System.out.println("启动线程执行操作的任务！");
					Thread thread = new Thread(object);
					thread.start();
				}
			}
		}
		System.out.println("扫描的collection结束");
	}
}
