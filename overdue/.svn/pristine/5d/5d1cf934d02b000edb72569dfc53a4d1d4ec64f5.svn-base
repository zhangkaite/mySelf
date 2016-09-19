package com.ttmv.dao.service.redis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisGoodNumberExpireInter;
import com.ttmv.dao.mapper.redis.RedisGoodNumberExpireMapper;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;

@Service("iRedisGoodNumberExpireInterImpl")
public class IRedisGoodNumberExpireInterImpl implements IRedisGoodNumberExpireInter{

	@Resource(name = "redisGoodNumberExpireMapper")
	private RedisGoodNumberExpireMapper redisGoodNumberExpireMapper;
	
	@Override
	public void addRedisGoodNumberExpire(GoodNumberExpire goodNumberExpire) throws Exception {
		if(goodNumberExpire == null){
			throw new Exception("goodNumberExpire对象不能为空！");
		}
		String userId = goodNumberExpire.getUserId().toString();
		String goodNumberId = goodNumberExpire.getGoodNumberId().toString();
		String key = userId + Constant.SEPARATOR + goodNumberId;
		long timeStemp = goodNumberExpire.getEndTime().getTime();
		redisGoodNumberExpireMapper.addRedisGoodNumberExpire(key, timeStemp);
	}

	@Override
	public void updateRedisGoodNumberExpire(GoodNumberExpire goodNumberExpire)throws Exception {
		if(goodNumberExpire == null){
			throw new Exception("goodNumberExpire对象不能为空！");
		}
		String userId = goodNumberExpire.getUserId().toString();
		String goodNumberId = goodNumberExpire.getGoodNumberId().toString();
		String key = userId + Constant.SEPARATOR + goodNumberId;
		long timeStemp = goodNumberExpire.getEndTime().getTime();
		redisGoodNumberExpireMapper.updateRedisGoodNumberExpire(key, timeStemp);
	}

	@Override
	public void deleteRedisGoodNumberExpire(String userId,String goodNumberId) throws Exception {
		if(userId == null && "".equals(userId) && goodNumberId == null && "".equals(goodNumberId)){
			throw new Exception("参数不能为空！");
		}
		String key = userId + Constant.SEPARATOR + goodNumberId;
		redisGoodNumberExpireMapper.deleteRedisGoodNumberExpire(key);
	}

	@Override
	public GoodNumberExpire queryRedisGoodNumberExpire(String userId,String goodNumberId) throws Exception {
		if(userId == null && "".equals(userId) && goodNumberId == null && "".equals(goodNumberId)){
			throw new Exception("参数不能为空！");
		}
		String key = userId + Constant.SEPARATOR + goodNumberId;
		GoodNumberExpire goodNumberExpire = new GoodNumberExpire();
		Long timeStemp = redisGoodNumberExpireMapper.getRedisGoodNumberExpire(key);
		if(timeStemp == null ){
			return null;
		}
		goodNumberExpire.setUserId(new BigInteger(userId));
		goodNumberExpire.setGoodNumberId(new BigInteger(goodNumberId));
		goodNumberExpire.setEndTime(new Date(timeStemp));
		return goodNumberExpire;
	}

	@Override
	public List<GoodNumberExpire> queryRedisGoodNumberExpire(Date endTime) throws Exception {
		if(endTime == null){
			throw new Exception("startTime或是endTime不能为空！");
		}
		long start = 0;
		long end = endTime.getTime();
		Set<Tuple> sets = redisGoodNumberExpireMapper.getRangeRedisGoodNumberExpire(start, end);
		List<GoodNumberExpire> lists = new ArrayList<GoodNumberExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				GoodNumberExpire expire = new GoodNumberExpire();
				String key[] = t.getElement().split(Constant.SEPARATOR);
				expire.setUserId(new BigInteger(key[0]));
				expire.setGoodNumberId(new BigInteger(key[1]));
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
		Set<Tuple> sets = redisGoodNumberExpireMapper.getAllRedisGoodNumberExpire();
		List<GoodNumberExpire> lists = new ArrayList<GoodNumberExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				GoodNumberExpire expire = new GoodNumberExpire();
				String key[] = t.getElement().split(Constant.SEPARATOR);
				expire.setUserId(new BigInteger(key[0]));
				expire.setGoodNumberId(new BigInteger(key[1]));
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
			GoodNumberExpire expire = (GoodNumberExpire)list.get(i);
			String userId = expire.getUserId().toString();
			String goodNumberId = expire.getGoodNumberId().toString();
			String key = userId + Constant.SEPARATOR + goodNumberId;
			SetCollectionBean s = new SetCollectionBean(Constant.GNE, key,expire.getEndTime().getTime());
			lists.add(s);
		}
		redisGoodNumberExpireMapper.addListRedisGoodNumberExpire(lists);
	}

	@Override
	public void deletePipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			GoodNumberExpire expire = (GoodNumberExpire)list.get(i);
			String userId = expire.getUserId().toString();
			String goodNumberId = expire.getGoodNumberId().toString();
			String key = userId + Constant.SEPARATOR + goodNumberId;
			SetCollectionBean s = new SetCollectionBean(Constant.GNE, key,expire.getEndTime().getTime());
			lists.add(s);
		}
		redisGoodNumberExpireMapper.deleteListRedisGoodNumberExpire(lists);
	}
}
