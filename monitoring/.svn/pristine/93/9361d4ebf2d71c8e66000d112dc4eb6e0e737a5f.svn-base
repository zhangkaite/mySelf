package com.springapp.mvc.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springapp.util.JsonUtil;
import com.springapp.util.PropertiesUtil;
import com.ttmv.monitoring.redis.inf.QueryRedisInfoServiceInf;

/**
 * 查看redis详情
 * 
 * @author zkt
 *
 */
@Controller
@RequestMapping("/redis")
public class RedisDetailController {
	
	

	@Resource(name = "queryRedisInfoServiceImpl")
	private QueryRedisInfoServiceInf queryRedisInfoServiceImpl;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model) {

		return "/redis/redisList";
	}

	/**
	 * 根据配置文件查询redis的详情，支持多个redis查询
	 * 
	 * @param request
	 * @param response
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryRedisDetail", method = RequestMethod.POST)
	public void queryRedisDetailList(HttpServletRequest request, HttpServletResponse response) {
		// 通过读取配置文件，数据解析成list数组
		String redisConfigValue = PropertiesUtil.get("jdbc.properties", "redis.config");
		String[] redisHosts = redisConfigValue.split(";");
		List<RedisInfoEntity> ls = new ArrayList<RedisInfoEntity>();
		Map dataMap = new HashMap();
		String redisKeyValue = PropertiesUtil.get("jdbc.properties", "redis.keys");
		String[] redisKeys = redisKeyValue.split(";");

		for (String hostInfo : redisHosts) {
			try {
				// 1、获取制定队列名称的key的数据类型
				for (String redisKey : redisKeys) {
					String keyType = queryRedisInfoServiceImpl.getKeyType(hostInfo, (String) redisKey);
					// 2、根据队列名称和数据类型查询对应的队列信息
					List<Object> valueList = queryRedisInfoServiceImpl.getKeyList(hostInfo, (String) redisKey, keyType);
					RedisInfoEntity redisInfoEntity = new RedisInfoEntity();
					Map resultMap = queryRedisInfoServiceImpl.info(hostInfo);
					redisInfoEntity.setHostName(hostInfo);
					redisInfoEntity.setKeyName(redisKey == null ? "" : redisKey.toString());
					redisInfoEntity.setKeyType(keyType == null ? "" : keyType);
					redisInfoEntity.setValueLength(valueList == null ? "0" : String.valueOf(valueList.size()));
					redisInfoEntity.setUsed_memory(resultMap.get("used_memory_human").toString());
					redisInfoEntity.setUsed_memory_peak(resultMap.get("used_memory_peak_human").toString());
					ls.add(redisInfoEntity);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		dataMap.put("result", ls);
		dataMap.put("dataSum", ls.size());
		try {
			JsonUtil.WriteJson("queryRedisDetail", dataMap, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
