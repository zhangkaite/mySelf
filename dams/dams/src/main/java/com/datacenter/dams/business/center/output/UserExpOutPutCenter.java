package com.datacenter.dams.business.center.output;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datacenter.dams.business.dao.queue.UserExpQueueDao;
import com.datacenter.dams.output.redis.entity.UserExpOutputBean;
import com.datacenter.dams.util.JsonUtil;
import com.google.common.base.Strings;

/**
 * 用户娱乐等级或是主播明星等级经验增加，推送外部系统中心。
 * @author wulinli
 */
public class UserExpOutPutCenter {

	private static Logger logger=LogManager.getLogger(UserExpOutPutCenter.class);
	private UserExpQueueDao userExpQueueDao;
	
	@SuppressWarnings("unchecked")
	public void handler(Object object)throws Exception{
		if(object == null){
			return;
		}
		List<String> listStrings = (List<String>)object;
		logger.info("[DAMS用户经验变动UserExpOutPutCenter]用户经验变动中心处理。");
		this.dealData(listStrings);
	}
	
	/**
	 * 处理数据
	 * @param listStrings
	 * @throws Exception
	 */
	public void dealData(List<String> listStrings)throws Exception{
		if(listStrings != null && listStrings.size() > 0){
			/* 整合用户经验数据，将相同的去重，并取经验值大的数据 */
			Map<String,UserExpOutputBean> userMap = new HashMap<String,UserExpOutputBean>();
			/* 整合主播经验数据，将相同的去重，并取经验值大的数据 */
			Map<String,UserExpOutputBean> starMap = new HashMap<String,UserExpOutputBean>();
			for(String message:listStrings){				
				UserExpOutputBean bean = (UserExpOutputBean) JsonUtil.getObjectFromJson(message, UserExpOutputBean.class);
				/* 执行去重 */
				this.dealMap(bean, userMap, starMap);
			}
			/* 发送数据到UC系统队列 */
			sendMessage(userMap, starMap);
		}
	}
	
	/**
	 * 执行去重操作
	 * @param bean
	 * @param userMap
	 * @param starMap
	 * @throws Exception
	 */
	private void dealMap(UserExpOutputBean bean,Map<String,UserExpOutputBean> userMap,Map<String,UserExpOutputBean> starMap)throws Exception{
		if(bean != null && userMap != null && starMap != null){
			String userId = bean.getUserID().toString();
			if(userMap.containsKey(userId+"_user")){
				UserExpOutputBean temp = userMap.get(userId+"_user");
				this.dealOne(bean, temp, userMap, "user");
				return ;
			}
			if(starMap.containsKey(userId+"_star")){
				UserExpOutputBean temp = userMap.get(userId+"_star");
				this.dealOne(bean, temp, userMap, "star");
				return ;
			}
			BigInteger userExp = bean.getExp();
			BigInteger starExp = bean.getAnnouncerExp();
			if(userExp == null && starExp != null){
				starMap.put(userId+"_star", bean);
			}
			if(userExp != null && starExp == null){
				userMap.put(userId+"_user", bean);
			}
		}
	}
	
	/**
	 * 执行一个对象的取大值
	 * @param bean
	 * @param source
	 * @param userMap
	 * @param type
	 */
	private void dealOne(UserExpOutputBean bean,UserExpOutputBean source,Map<String,UserExpOutputBean> map,String type){
		if(bean != null && source != null && map != null && !Strings.isNullOrEmpty(type)){
			String userId = bean.getUserID().toString();
			if(type.equals("user")){
				BigInteger beanExp = bean.getExp();
				BigInteger sourceExp = bean.getExp();
				if(beanExp.compareTo(sourceExp) == 1){
					map.put(userId+"_user", bean);
				}
			}else if(type.equals("star")){
				BigInteger beanExp = bean.getAnnouncerExp();
				BigInteger sourceExp = bean.getAnnouncerExp();
				if(beanExp.compareTo(sourceExp) == 1){
					map.put(userId+"_star", bean);
				}
			}
		}
	}
	
	/**
	 * 集合合并
	 * @param userSet
	 * @param starSet
	 */
	@SuppressWarnings({ "rawtypes" })
	private void sendMessage(Map<String,UserExpOutputBean> userMap,Map<String,UserExpOutputBean> starMap)throws Exception{
		if(userMap != null && starMap != null){
			Collection collection = null;
			if(userMap.size() == 0 && starMap.size() > 0){
				collection = starMap.values();
			}
			if(userMap.size() > 0 && starMap.size() == 0){
				collection = userMap.values();
			}
			if(userMap.size() > 0 && starMap.size() > 0){
				userMap.putAll(starMap);
				collection = userMap.values();
			}
			Iterator iterator = collection.iterator();
			while(iterator.hasNext()){
				UserExpOutputBean map = (UserExpOutputBean)iterator.next();
				String json = JsonUtil.getObjectToJson(map);
				userExpQueueDao.send(json);
				logger.info("[DAMS用户经验变动UserExpOutPutCenter]用户经验变动数据发送PC队列成功：" +json);
			}
		}
	}

	public UserExpQueueDao getUserExpQueueDao() {
		return userExpQueueDao;
	}

	public void setUserExpQueueDao(UserExpQueueDao userExpQueueDao) {
		this.userExpQueueDao = userExpQueueDao;
	}
}
