package com.ttmv.dao.service.redis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.LiveAnchorBanExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisLiveAnchorBanExpireInter;
import com.ttmv.dao.mapper.redis.RedisLiveAnchorBanExpireMapper;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;

@Service("iRedisLiveAnchorBanExpireInterImpl")
public class IRedisLiveAnchorBanExpireInterImpl implements IRedisLiveAnchorBanExpireInter{

	@Resource(name = "redisLiveAnchorBanExpireMapper")
	private RedisLiveAnchorBanExpireMapper redisLiveAnchorBanExpireMapper;
	
	@Override
	public void addRedisLiveAnchorBanExpire(LiveAnchorBanExpire liveAnchorBanExpire) throws Exception {
		if(liveAnchorBanExpire == null){
			throw new Exception("liveAnchorBanExpire对象不能为空！");
		}
		String key = liveAnchorBanExpire.getUserId().toString();
		long timeStemp = liveAnchorBanExpire.getEndTime().getTime();
		redisLiveAnchorBanExpireMapper.addRedisLiveAnchorBanExpire(key, timeStemp);
	}

	@Override
	public void updateRedisLiveAnchorBanExpire(LiveAnchorBanExpire liveAnchorBanExpire)throws Exception {
		if(liveAnchorBanExpire == null){
			throw new Exception("liveAnchorBanExpire对象不能为空！");
		}
		String key = liveAnchorBanExpire.getUserId().toString();
		long timeStemp = liveAnchorBanExpire.getEndTime().getTime();
		redisLiveAnchorBanExpireMapper.updateRedisLiveAnchorBanExpire(key, timeStemp);
	}

	@Override
	public void deleteRedisLiveAnchorBanExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		redisLiveAnchorBanExpireMapper.deleteRedisLiveAnchorBanExpire(userId);
	}

	@Override
	public LiveAnchorBanExpire queryRedisLiveAnchorBanExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		LiveAnchorBanExpire liveAnchorBanExpire = new LiveAnchorBanExpire();
		Long timeStemp = redisLiveAnchorBanExpireMapper.getRedisLiveAnchorBanExpire(userId);
		if(timeStemp == null ){
			return null;
		}
		liveAnchorBanExpire.setUserId(new BigInteger(userId));
		liveAnchorBanExpire.setEndTime(new Date(timeStemp));
		return liveAnchorBanExpire;
	}
	
	@Override
	public List<LiveAnchorBanExpire> queryRedisLiveAnchorBanExpire(Date endTime) throws Exception {
		if(endTime == null){
			throw new Exception("startTime或是endTime不能为空！");
		}
		long start = 0;
		long end = endTime.getTime();
		Set<Tuple> sets = redisLiveAnchorBanExpireMapper.getRangeRedisLiveAnchorBanExpire(start, end);
		List<LiveAnchorBanExpire> lists = new ArrayList<LiveAnchorBanExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				LiveAnchorBanExpire expire = new LiveAnchorBanExpire();
				expire.setUserId(new BigInteger(t.getElement()));
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
		Set<Tuple> sets = redisLiveAnchorBanExpireMapper.getAllRedisLiveAnchorBanExpire();
		List<LiveAnchorBanExpire> lists = new ArrayList<LiveAnchorBanExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				LiveAnchorBanExpire expire = new LiveAnchorBanExpire();
				expire.setUserId(new BigInteger(t.getElement()));
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
			LiveAnchorBanExpire expire = (LiveAnchorBanExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.LABE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisLiveAnchorBanExpireMapper.addListRedisLiveAnchorBanExpire(lists);
	}

	@Override
	public void deletePipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			LiveAnchorBanExpire expire = (LiveAnchorBanExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.LABE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisLiveAnchorBanExpireMapper.deleteListRedisLiveAnchorBanExpire(lists);
	}
}
