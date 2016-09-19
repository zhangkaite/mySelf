package com.ttmv.dao.service.redis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisNobilityExpireInter;
import com.ttmv.dao.mapper.redis.RedisNobilityExpireMapper;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;

@Service("iRedisNobilityExpireInterImpl")
public class IRedisNobilityExpireInterImpl implements IRedisNobilityExpireInter{

	@Resource(name = "redisNobilityExpireMapper")
	private RedisNobilityExpireMapper redisNobilityExpireMapper;
	
	@Override
	public void addRedisNobilityExpire(NobilityExpire nobilityExpire) throws Exception {
		if(nobilityExpire == null){
			throw new Exception("nobilityExpire对象不能为空！");
		}
		String key = nobilityExpire.getUserId().toString();
		long timeStemp = nobilityExpire.getEndTime().getTime();
		redisNobilityExpireMapper.addRedisNobilityExpire(key, timeStemp);
	}

	@Override
	public void updateRedisNobilityExpire(NobilityExpire nobilityExpire)throws Exception {
		if(nobilityExpire == null){
			throw new Exception("nobilityExpire对象不能为空！");
		}
		String key = nobilityExpire.getUserId().toString();
		long timeStemp = nobilityExpire.getEndTime().getTime();
		redisNobilityExpireMapper.updateRedisNobilityExpire(key, timeStemp);
	}

	@Override
	public void deleteRedisNobilityExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		redisNobilityExpireMapper.deleteRedisNobilityExpire(userId);
	}

	@Override
	public NobilityExpire queryRedisNobilityExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		NobilityExpire nobilityExpire = new NobilityExpire();
		Long timeStemp = redisNobilityExpireMapper.getRedisNobilityExpire(userId);
		if(timeStemp == null ){
			return null;
		}
		nobilityExpire.setUserId(new BigInteger(userId));
		nobilityExpire.setEndTime(new Date(timeStemp));
		return nobilityExpire;
	}
	
	@Override
	public List<NobilityExpire> queryRedisNobilityExpire(Date endTime) throws Exception {
		if(endTime == null){
			throw new Exception("endTime不能为空！");
		}
		long start = 0;
		long end = endTime.getTime();
		Set<Tuple> sets = redisNobilityExpireMapper.getRangeRedisNobilityExpire(start, end);
		List<NobilityExpire> lists = new ArrayList<NobilityExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				NobilityExpire expire = new NobilityExpire();
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
		Set<Tuple> sets = redisNobilityExpireMapper.getAllRedisNobilityExpire();
		List<NobilityExpire> lists = new ArrayList<NobilityExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				NobilityExpire expire = new NobilityExpire();
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
			NobilityExpire expire = (NobilityExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.NE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisNobilityExpireMapper.addListRedisNobilityExpire(lists);
	}

	@Override
	public void deletePipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			NobilityExpire expire = (NobilityExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.NE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisNobilityExpireMapper.deleteListRedisNobilityExpire(lists);
	}
}
