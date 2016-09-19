package com.ttmv.dao.service.redis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.UserForbiddenExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisUserForbiddenExpireInter;
import com.ttmv.dao.mapper.redis.RedisUserForbiddenExpireMapper;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;

@Service("iRedisUserForbiddenExpireInterImpl")
public class IRedisUserForbiddenExpireInterImpl implements IRedisUserForbiddenExpireInter{

	@Resource(name = "redisUserForbiddenExpireMapper")
	private RedisUserForbiddenExpireMapper redisUserForbiddenExpireMapper;
	
	@Override
	public void addRedisUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire) throws Exception {
		if(userForbiddenExpire == null){
			throw new Exception("userForbiddenExpire对象不能为空！");
		}
		String key = userForbiddenExpire.getUserId().toString();
		long timeStemp = userForbiddenExpire.getEndTime().getTime();
		redisUserForbiddenExpireMapper.addRedisUserForbiddenExpire(key, timeStemp);
	}

	@Override
	public void updateRedisUserForbiddenExpire(UserForbiddenExpire userForbiddenExpire)throws Exception {
		if(userForbiddenExpire == null){
			throw new Exception("userForbiddenExpire对象不能为空！");
		}
		String key = userForbiddenExpire.getUserId().toString();
		long timeStemp = userForbiddenExpire.getEndTime().getTime();
		redisUserForbiddenExpireMapper.updateRedisUserForbiddenExpire(key, timeStemp);
	}

	@Override
	public void deleteRedisUserForbiddenExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		redisUserForbiddenExpireMapper.deleteRedisUserForbiddenExpire(userId);
	}

	@Override
	public UserForbiddenExpire queryRedisUserForbiddenExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		UserForbiddenExpire userForbiddenExpire = new UserForbiddenExpire();
		Long timeStemp = redisUserForbiddenExpireMapper.getRedisUserForbiddenExpire(userId);
		if(timeStemp == null ){
			return null;
		}
		userForbiddenExpire.setUserId(new BigInteger(userId));
		userForbiddenExpire.setEndTime(new Date(timeStemp));
		return userForbiddenExpire;
	}
	
	@Override
	public List<UserForbiddenExpire> queryRedisUserForbiddenExpire(Date endTime) throws Exception {
		if(endTime == null){
			throw new Exception("startTime或是endTime不能为空！");
		}
		long start = 0;
		long end = endTime.getTime();
		Set<Tuple> sets = redisUserForbiddenExpireMapper.getRangeRedisUserForbiddenExpire(start, end);
		List<UserForbiddenExpire> lists = new ArrayList<UserForbiddenExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				UserForbiddenExpire expire = new UserForbiddenExpire();
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
		Set<Tuple> sets = redisUserForbiddenExpireMapper.getAllRedisUserForbiddenExpire();
		List<UserForbiddenExpire> lists = new ArrayList<UserForbiddenExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				UserForbiddenExpire expire = new UserForbiddenExpire();
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
			UserForbiddenExpire expire = (UserForbiddenExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.UFE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisUserForbiddenExpireMapper.addListRedisUserForbiddenExpire(lists);
	}

	@Override
	public void deletePipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			UserForbiddenExpire expire = (UserForbiddenExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.UFE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisUserForbiddenExpireMapper.deleteListRedisUserForbiddenExpire(lists);
	}
}
