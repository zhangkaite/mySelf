package com.datacenter.dams.business.service.form;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.service.FormInter;
import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 用户注册记录业务处理，存入hdfs文件
 * @author wulinli
 */
public class AddUserFormService implements FormInter{

	private static Logger logger=LogManager.getLogger(AddUserFormService.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	@Override
	@SuppressWarnings("rawtypes")
	public void handler(Object object)throws Exception{
		if(object == null){
			return ;
		}
		Map map = (Map)object;
		Long time = Long.parseLong(map.get("time").toString());
		Date date = new Date(time*1000);
		String strDate = sdf.format(date);
		String json = JsonUtil.getObjectToJson(map);
		String path = ConsumeSpendConstant.HDFSADDUSER + strDate + "/" + strDate;
		if(!Strings.isNullOrEmpty(json)){
			try {
				HadoopFSOperations.createNewHDFSFile(path,json);
				logger.info("[DAMS#UserLoginFormService用户注册记录]数据存入hdsf文件，路径是："+path+",数据是："+json);
			} catch (IOException e) {
				logger.error("[DAMS#UserLoginFormService用户注册记录#ERROR]数据存入hdsf文件出错，路径是："+path+",数据是："+json,e);
				throw e;
			}
		}
	}
}
