package com.ttmv.datacenter.paycenter.triger.triggerimpl.triggerdb;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.paycenter.triger.triggerimpl.triggerconstant.Trigger;

public class TriggerDBConstantMapperTimer {
	
	private static Logger log = LogManager.getLogger(TriggerDBConstantMapperTimer.class);
	private TriggerDBMapper triggerDBMapper;
	/**
	 * 将db的key的值覆盖TriggerConstant的Collection的值
	 * @throws Exception 
	 */
	public void mapper() throws Exception{
		String pythonFilePath = "/home/wll/phyData/berkeleydb_forApp.py";
		List<String> listData = triggerDBMapper.getAll(pythonFilePath);
		//List<String> listData = new ArrayList<String>();
		listData.add("test,1");
		System.out.println(listData.size());
		if(listData != null && listData.size() > 0){
			for(int i = 0;i<listData.size();i++){
				String key_value = listData.get(i);
				System.out.println("调用python脚本读取的数据："+key_value);
				Map<String,String> collection = Trigger.getCollection();
				String key = key_value.split(",")[0];
				if(key == null ||"".equals(key) ){
					log.debug("key不能为空！");
					continue;
				}
				String value = key_value.split(",")[1];
				if(collection.containsKey(key)){
					collection.put(key, value);
				}else if(!collection.containsKey(key)){
					log.debug("TriggerConstant的collection中不包含key："+key);
				}
			} 
	   }
	}
	
	public TriggerDBMapper getTriggerDBMapper() {
		return triggerDBMapper;
	}
	
	public void setTriggerDBMapper(TriggerDBMapper triggerDBMapper) {
		this.triggerDBMapper = triggerDBMapper;
	}
}