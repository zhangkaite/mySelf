package com.ttmv.datacenter.paycenter.triger.triggerimpl.triggerdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TriggerDBMapper {

	private static Logger log = LogManager.getLogger(TriggerDBMapper.class);
	
	/**
	 * 获取全部key和value
	 * @return
	 * @throws IOException 
	 */
	public List<String> getAll(String pythonFilePath) throws Exception{
		List<String> listResult = new ArrayList<String>();
		try{			
			Process pr = Runtime.getRuntime().exec("python " + pythonFilePath);               
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));  
			String line = null;  
			while ((line = in.readLine()) != null) {  
				listResult.add(line);
			}  
			if(listResult.size() > 0){				
				return listResult;
			}
		}catch(Exception e){
			log.error("执行python脚本：" + pythonFilePath +"读取数据出错！",e );
			throw new Exception("执行python脚本：" + pythonFilePath +"读取数据出错！",e );
		}
		return null;
	}
}
