package com.ttmv.dao.service.redis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisLuxuryExpireInter;
import com.ttmv.dao.mapper.redis.RedisLuxuryExpireMapper;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;

@Service("iRedisLuxuryExpireInterImpl")
public class IRedisLuxuryExpireInterImpl implements IRedisLuxuryExpireInter{

	@Resource(name = "redisLuxuryExpireMapper")
	private RedisLuxuryExpireMapper redisLuxuryExpireMapper;
	
	@Override
	public void addRedisLuxuryExpire(LuxuryExpire luxuryExpire) throws Exception {
		if(luxuryExpire == null){
			throw new Exception("luxuryExpire对象不能为空！");
		}
		String userId = luxuryExpire.getUserId().toString();
		String carId = luxuryExpire.getCarId().toString();
		String key = userId +Constant.SEPARATOR + carId;
		long timeStemp = luxuryExpire.getEndTime().getTime();
		redisLuxuryExpireMapper.addRedisLuxuryExpire(key, timeStemp);
	}

	@Override
	public void updateRedisLuxuryExpire(LuxuryExpire luxuryExpire)throws Exception {
		if(luxuryExpire == null){
			throw new Exception("luxuryExpire对象不能为空！");
		}
		String userId = luxuryExpire.getUserId().toString();
		String carId = luxuryExpire.getCarId().toString();
		String key = userId +Constant.SEPARATOR + carId;
		long timeStemp = luxuryExpire.getEndTime().getTime();
		redisLuxuryExpireMapper.updateRedisLuxuryExpire(key, timeStemp);
	}

	@Override
	public void deleteRedisLuxuryExpire(String userId,String carId) throws Exception {
		if(userId == null && "".equals(userId) && carId == null && "".equals(carId)){
			throw new Exception("参数不能为空！");
		}
		String key = userId +Constant.SEPARATOR + carId;
		redisLuxuryExpireMapper.deleteRedisLuxuryExpire(key);
	}

	@Override
	public LuxuryExpire queryRedisLuxuryExpire(String userId,String carId) throws Exception {
		if(userId == null && "".equals(userId) && carId == null && "".equals(carId)){
			throw new Exception("参数不能为空！");
		}
		LuxuryExpire luxuryExpire = new LuxuryExpire();
		String key = userId +Constant.SEPARATOR + carId;
		Long timeStemp = redisLuxuryExpireMapper.getRedisLuxuryExpire(key);
		if(timeStemp == null ){
			return null;
		}
		luxuryExpire.setUserId(new BigInteger(userId));
		luxuryExpire.setCarId(new BigInteger(carId));
		luxuryExpire.setEndTime(new Date(timeStemp));
		return luxuryExpire;
	}
	
	@Override
	public List<LuxuryExpire> queryRedisLuxuryExpire(Date endTime) throws Exception {
		if(endTime == null){
			throw new Exception("startTime或是endTime不能为空！");
		}
		long start = 0;
		long end = endTime.getTime();
		Set<Tuple> sets = redisLuxuryExpireMapper.getRangeRedisLuxuryExpire(start, end);
		List<LuxuryExpire> lists = new ArrayList<LuxuryExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				LuxuryExpire expire = new LuxuryExpire();
				String key[] = t.getElement().split(Constant.SEPARATOR);
				expire.setUserId(new BigInteger(key[0]));
				expire.setCarId(new BigInteger(key[1]));
				long _endTime = (long) t.getScore();
				expire.setEndTime(new Date(_endTime));
				lists.add(expire);
			}
			return lists;
		}
		return null;
	}
	
	@Override
	public List getAll() throws Exception {
		Set<Tuple> sets = redisLuxuryExpireMapper.getAllRedisLuxuryExpire();
		List<LuxuryExpire> lists = new ArrayList<LuxuryExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				LuxuryExpire expire = new LuxuryExpire();
				String key[] = t.getElement().split(Constant.SEPARATOR);
				expire.setUserId(new BigInteger(key[0]));
				expire.setCarId(new BigInteger(key[1]));
				long _endTime = (long) t.getScore();
				expire.setEndTime(new Date(_endTime));
				lists.add(expire);
			}
			return lists;
		}
		return null;
	}

	@Override
	public void addPipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			LuxuryExpire expire = (LuxuryExpire)list.get(i);
			String userId = expire.getUserId().toString();
			String carId = expire.getCarId().toString();
			String key = userId +Constant.SEPARATOR + carId;
			SetCollectionBean s = new SetCollectionBean(Constant.LE, key,expire.getEndTime().getTime());
			lists.add(s);
		}
		redisLuxuryExpireMapper.addListRedisLuxuryExpire(lists);
	}

	@Override
	public void deletePipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			LuxuryExpire expire = (LuxuryExpire)list.get(i);
			String userId = expire.getUserId().toString();
			String carId = expire.getCarId().toString();
			String key = userId +Constant.SEPARATOR + carId;
			SetCollectionBean s = new SetCollectionBean(Constant.LE, key,expire.getEndTime().getTime());
			lists.add(s);
		}
		redisLuxuryExpireMapper.deleteListRedisLuxuryExpire(lists);
	}
}
