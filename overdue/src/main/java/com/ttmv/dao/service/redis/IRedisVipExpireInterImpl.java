package com.ttmv.dao.service.redis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisVipExpireInter;
import com.ttmv.dao.mapper.redis.RedisVipExpireMapper;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;

@Service("iRedisVipExpireInterImpl")
public class IRedisVipExpireInterImpl implements IRedisVipExpireInter{

	@Resource(name = "redisVipExpireMapper")
	private RedisVipExpireMapper redisVipExpireMapper;
	
	@Override
	public void addRedisVipExpire(VipExpire vipExpire) throws Exception {
		if(vipExpire == null){
			throw new Exception("vipExpire对象不能为空！");
		}
		String key = vipExpire.getUserId().toString();
		long timeStemp = vipExpire.getEndTime().getTime();
		redisVipExpireMapper.addRedisVipExpire(key, timeStemp);
	}

	@Override
	public void updateRedisVipExpire(VipExpire vipExpire)throws Exception {
		if(vipExpire == null){
			throw new Exception("vipExpire对象不能为空！");
		}
		String key = vipExpire.getUserId().toString();
		long timeStemp = vipExpire.getEndTime().getTime();
		redisVipExpireMapper.updateRedisVipExpire(key, timeStemp);
	}

	@Override
	public void deleteRedisVipExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		redisVipExpireMapper.deleteRedisVipExpire(userId);
	}

	@Override
	public VipExpire queryRedisVipExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		VipExpire vipExpire = new VipExpire();
		Long timeStemp = redisVipExpireMapper.getRedisVipExpire(userId);
		if(timeStemp == null ){
			return null;
		}
		vipExpire.setUserId(new BigInteger(userId));
		vipExpire.setEndTime(new Date(timeStemp));
		return vipExpire;
	}
	
	@Override
	public List<VipExpire> queryRedisVipExpire(Date endTime) throws Exception {
		if(endTime == null){
			throw new Exception("startTime或是endTime不能为空！");
		}
		long start = 0L;
		long end = endTime.getTime();
		Set<Tuple> sets = redisVipExpireMapper.getRangeRedisVipExpire(start, end);
		List<VipExpire> lists = new ArrayList<VipExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				VipExpire expire = new VipExpire();
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
		Set<Tuple> sets = redisVipExpireMapper.getAllRedisVipExpire();
		List<VipExpire> lists = new ArrayList<VipExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				VipExpire expire = new VipExpire();
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
			VipExpire expire = (VipExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.VE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisVipExpireMapper.addListRedisVipExpire(lists);
	}

	@Override
	public void deletePipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			VipExpire expire = (VipExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.VE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisVipExpireMapper.deleteListRedisVipExpire(lists);
	}
}
