package com.ttmv.dao.service.redis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.LiveRoomBanExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisLiveRoomBanExpireInter;
import com.ttmv.dao.mapper.redis.RedisLiveRoomBanExpireMapper;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;

@Service("iRedisLiveRoomBanExpireInterImpl")
public class IRedisLiveRoomBanExpireInterImpl implements IRedisLiveRoomBanExpireInter{

	@Resource(name = "redisLiveRoomBanExpireMapper")
	private RedisLiveRoomBanExpireMapper redisLiveRoomBanExpireMapper;
	
	@Override
	public void addRedisLiveRoomBanExpire(LiveRoomBanExpire liveRoomBanExpire) throws Exception {
		if(liveRoomBanExpire == null){
			throw new Exception("liveRoomBanExpire对象不能为空！");
		}
		String userID = liveRoomBanExpire.getUserId().toString();
		String chanelID=liveRoomBanExpire.getChanelID();
		String key=userID+Constant.SEPARATOR+chanelID;
		long timeStemp = liveRoomBanExpire.getEndTime().getTime();
		redisLiveRoomBanExpireMapper.addRedisLiveRoomBanExpire(key, timeStemp);
	}

	@Override
	public void updateRedisLiveRoomBanExpire(LiveRoomBanExpire liveRoomBanExpire)throws Exception {
		if(liveRoomBanExpire == null){
			throw new Exception("liveRoomBanExpire对象不能为空！");
		}
		String key = liveRoomBanExpire.getUserId().toString();
		long timeStemp = liveRoomBanExpire.getEndTime().getTime();
		redisLiveRoomBanExpireMapper.updateRedisLiveRoomBanExpire(key, timeStemp);
	}

	@Override
	public void deleteRedisLiveRoomBanExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		redisLiveRoomBanExpireMapper.deleteRedisLiveRoomBanExpire(userId);
	}

	@Override
	public LiveRoomBanExpire queryRedisLiveRoomBanExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		LiveRoomBanExpire liveRoomBanExpire = new LiveRoomBanExpire();
		Long timeStemp = redisLiveRoomBanExpireMapper.getRedisLiveRoomBanExpire(userId);
		if(timeStemp == null ){
			return null;
		}
		liveRoomBanExpire.setUserId(new BigInteger(userId));
		liveRoomBanExpire.setEndTime(new Date(timeStemp));
		return liveRoomBanExpire;
	}
	
	@Override
	public List<LiveRoomBanExpire> queryRedisLiveRoomBanExpire(Date endTime) throws Exception {
		if(endTime == null){
			throw new Exception("startTime或是endTime不能为空！");
		}
		long start = 0;
		long end = endTime.getTime();
		Set<Tuple> sets = redisLiveRoomBanExpireMapper.getRangeRedisLiveRoomBanExpire(start, end);
		List<LiveRoomBanExpire> lists = new ArrayList<LiveRoomBanExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				LiveRoomBanExpire expire = new LiveRoomBanExpire();
				String key[] = t.getElement().split(Constant.SEPARATOR);
				expire.setUserId(new BigInteger(key[0]));
				expire.setChanelID(key[1]);
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
		Set<Tuple> sets = redisLiveRoomBanExpireMapper.getAllRedisLiveRoomBanExpire();
		List<LiveRoomBanExpire> lists = new ArrayList<LiveRoomBanExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				LiveRoomBanExpire expire = new LiveRoomBanExpire();
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
			LiveRoomBanExpire expire = (LiveRoomBanExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.LRBE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisLiveRoomBanExpireMapper.addListRedisLiveRoomBanExpire(lists);
	}

	@Override
	public void deletePipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			LiveRoomBanExpire expire = (LiveRoomBanExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.LRBE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisLiveRoomBanExpireMapper.deleteListRedisLiveRoomBanExpire(lists);
	}
}
