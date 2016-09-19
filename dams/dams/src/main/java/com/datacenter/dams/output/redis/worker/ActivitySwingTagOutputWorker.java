package com.datacenter.dams.output.redis.worker;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.datacenter.dams.business.center.MessageHandlerCenter;
import com.datacenter.dams.business.dao.redis.inner.output.ActivitySwingTagRedisQueueOutputDao;
import com.datacenter.dams.util.JsonUtil;
import com.datacenter.domain.protocol.da.Present;
import com.datacenter.domain.protocol.da.StarActivityInfo;
import com.datacenter.worker.worker.QuartzWorkerBatch;

/**
 * 吊牌活动 推送到外部系统的队列
 * @author wll
 */

public class ActivitySwingTagOutputWorker extends QuartzWorkerBatch<String>{

	private static Logger logger=LogManager.getLogger(ActivitySwingTagOutputWorker.class);
	
	@Override
	protected List<String> getData(int sum) {
		logger.debug("[DAMS#ActivitySwingTagOutput]worker读取吊牌活动信息队列数据并处理.");
		List<String> listStrings = new ArrayList<String>();
		if(sum != 0){
			try {
				listStrings = ActivitySwingTagRedisQueueOutputDao.getRedisQueueMessage(sum);
				if(listStrings!=null){
					logger.info("[吊牌活动信息数据读取入口数据记录]>>总数是 : "+sum+" ,数据是 : data="+listStrings);
				}
			} catch (Exception e) {
				logger.error("[DAMS#ActivitySwingTagOutput#ERROR]worker读取吊牌活动信息队列数据出错.",e);
			}
			logger.debug("[DAMS#ActivitySwingTagOutput]worker读取吊牌活动信息队列数据并处理.");
		}
		return listStrings;
	}

	@SuppressWarnings("unchecked")
	protected void process(List<String> listStrings) throws Exception {
			if(listStrings != null && listStrings.size() > 0){
				handlerMessages(listStrings);				
			}
	}

	/**
	 * 处理信息
	 * @param message
	 */
	private void handlerMessages(List<String> messages)throws Exception{
		logger.debug("[DAMS#ActivitySwingTagOutput]处理吊牌活动队列数据.");
		if(messages != null && messages.size() > 0){
	
			/* 最终结果 */
			Map<String,Object> result = new HashMap<String,Object>();
			/* 临时存放 */
			Map<String,Object> temp = new HashMap<String,Object>();
			for(int i=0;i<messages.size();i++){
				String json = messages.get(i);
				/* 转换对象,并检查数据的正确性 */
				Map resultMap = (Map) JsonUtil.getObjectFromJson(json, Map.class);
				if(!checkObject(resultMap)){
					return ;
				}
				/* 对外推送吊牌活动信息 */
				StarActivityInfo swingTag = getStarActivityInfoFromMap(resultMap);
				String key = swingTag.getUserID().toString();
				long score = Long.parseLong(swingTag.getScore().toString());
				if(temp.containsKey(key)){
					StarActivityInfo tempStar = (StarActivityInfo) temp.get(key);
					long tempScore = Long.parseLong(tempStar.getScore().toString());
					if(score > tempScore){								
						temp.put(swingTag.getUserID().toString(), swingTag);
					}
				}else if(!temp.containsKey(key)){					
					temp.put(swingTag.getUserID().toString(), swingTag);	
				}
			}
			/* 组合最后结果 */
			if(temp.size()>0){
				Collection collection = temp.values();
				result.put("stars", collection);
				ObjectMapper mapper = new ObjectMapper();
				String resultJson = JsonUtil.getObjectToJson(result);
				MessageHandlerCenter.activitySwingTagResultCenter.handler(resultJson);
			}
		}
	}
	
	/**
	 * 校验数据正确性
	 * @return
	 */
	private boolean checkObject(Map map){
		boolean flag = false;
		if(map.get("userID") == null){
			return flag = false;
		}
		if(map.get("ranking") == null){
			return flag = false;
		}
		if(map.get("activityID") == null){
			return flag = false;
		}
		if(map.get("levelLogoPC") == null){
			return flag = false;
		}
		if(map.get("levelLogoMC") == null){
			return flag = false;
		}
		if(map.get("score") == null){
			return flag = false;
		}
		if(map.get("levelName") == null){
			return flag = false;
		}
		if(map.get("Gifts") == null){
			return flag = false;
		}
		if(map.get("Gifts") != null){
			List<Map> list = (List<Map>)map.get("Gifts");
			if(list.size() > 0){
				for(int i=0;i<list.size();i++){
					Map m = list.get(i);
					if(!checkPresent(m)){
						return  flag = false;
					}
				}
			}else{
				return flag = false;
			}
		}
		return flag = true;
	}
	
	/**
	 * 检查礼物数据正确性
	 * @param map
	 * @return
	 */
	private boolean checkPresent(Map map){
		boolean flag = false;
		if(map.get("productID") == null){
			return flag = false;
		}
		if(map.get("sum") == null){
			return flag = false;
		}
		if(map.get("limitSum") == null){
			return flag = false;
		}
		return flag = true;
	}
	
	/**
	 * Map转为StarActivityInfo
	 * @param map
	 * @return
	 */
	private StarActivityInfo getStarActivityInfoFromMap(Map map){
		StarActivityInfo star = new StarActivityInfo();
		List<Map> gifts = (List<Map>) map.get("Gifts");
		List<Present> presents = new ArrayList<Present>();
		star.setActivityID(new BigInteger(map.get("activityID").toString()));
		star.setLevelLogoPC(map.get("levelLogoPC").toString());
		star.setLevelLogoMC(map.get("levelLogoPC").toString());
		star.setLevelName(map.get("levelName").toString());
		star.setRanking(new Integer(map.get("ranking").toString()));
		star.setScore(new BigInteger(map.get("score").toString()));
		star.setUserID(new BigInteger(map.get("userID").toString()));
		/*组装礼物*/
		for(int i=0;i<gifts.size();i++){
			Present p = new Present();
			Map m = gifts.get(i);
			p.setLimitSum(new Integer(m.get("limitSum").toString()));
			p.setProductID(new BigInteger(m.get("productID").toString()));
			p.setSum(new Integer(m.get("sum").toString()));
			presents.add(p);
		}
		star.setGifts(presents);
		return star;
	}

	@Override
	protected String toLog(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
