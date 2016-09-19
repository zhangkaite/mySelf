package com.datacenter.dams.business.service.level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.input.redis.worker.util.HbaseUtil;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 提供操作hbase服务
 * 主播经验清零和用户降级查询用户
 * @author wulinli
 *
 */
public class ExpHbaseService{

	private static Logger logger=LogManager.getLogger(ExpHbaseService.class);
	private static Configuration config = HBaseConfiguration.create();
	
	/* 表名 */
	private static final String HBASETABLE = "da_level";
	/* 主播等级 */
	private static final String STARLEVEL = "starlevel";
	/* 主播经验 */
	private static final String STARALL = "starall";
	/* 列族 */
	private static final String FAMILIY = "level";
	
	/**
	 * 根据用户ID清零主播经验值
	 * @param userId
	 * @throws Exception
	 */
	public static void reset(Object object)throws Exception{
		if(object == null){
			return;
		}
		String userId = object.toString();
		/* 清空经验值和等级 */
		if(!Strings.isNullOrEmpty(userId)){
			Map<String,String> keyValues = new HashMap<String,String>();
			keyValues.put(STARALL, "0");
			keyValues.put(STARLEVEL, "0");
			HbaseUtil.addRowColumns(HBASETABLE, userId, FAMILIY, config, keyValues);
			logger.info("[DAMS主播经验清零ExpHbaseService]清空主播ID为：【"+userId+"】的经验值成功！");
		}
	}
	
	/**
	 * 获取所有的用户
	 * @return
	 */
	public static List<String> getAllUsers()throws Exception{
		List<String> listStrings = HbaseUtil.getTableAllRows(config, HBASETABLE);
		if(listStrings != null && listStrings.size() > 0){			
			logger.info("[DAMS用户降级ExpHbaseService]用户查询hbase数据成功！数据是：" + JsonUtil.getObjectToJson(listStrings));
			return listStrings;
		}
		return null;
	}
}
