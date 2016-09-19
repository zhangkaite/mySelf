package com.ttmv.dao.service.redis;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Tuple;

import com.ttmv.dao.bean.RoomForbiddenExpire;
import com.ttmv.dao.constant.Constant;
import com.ttmv.dao.inter.redis.IRedisRoomForbiddenExpireInter;
import com.ttmv.dao.mapper.redis.RedisRoomForbiddenExpireMapper;
import com.ttmv.datacenter.agent.redis.beans.SetCollectionBean;

@Service("iRedisRoomForbiddenExpireInterImpl")
public class IRedisRoomForbiddenExpireInterImpl implements IRedisRoomForbiddenExpireInter{

	@Resource(name = "redisRoomForbiddenExpireMapper")
	private RedisRoomForbiddenExpireMapper redisRoomForbiddenExpireMapper;
	
	@Override
	public void addRedisRoomForbiddenExpire(RoomForbiddenExpire roomForbiddenExpire) throws Exception {
		if(roomForbiddenExpire == null){
			throw new Exception("roomForbiddenExpire对象不能为空！");
		}
		String userID= roomForbiddenExpire.getUserId().toString();
		String chanelID=roomForbiddenExpire.getChanelID();
		String key=userID+Constant.SEPARATOR +chanelID;
		long timeStemp = roomForbiddenExpire.getEndTime().getTime();
		redisRoomForbiddenExpireMapper.addRedisRoomForbiddenExpire(key, timeStemp);
	}

	@Override
	public void updateRedisRoomForbiddenExpire(RoomForbiddenExpire roomForbiddenExpire)throws Exception {
		if(roomForbiddenExpire == null){
			throw new Exception("roomForbiddenExpire对象不能为空！");
		}
		String key = roomForbiddenExpire.getUserId().toString();
		long timeStemp = roomForbiddenExpire.getEndTime().getTime();
		redisRoomForbiddenExpireMapper.updateRedisRoomForbiddenExpire(key, timeStemp);
	}

	@Override
	public void deleteRedisRoomForbiddenExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		redisRoomForbiddenExpireMapper.deleteRedisRoomForbiddenExpire(userId);
	}

	@Override
	public RoomForbiddenExpire queryRedisRoomForbiddenExpire(String userId) throws Exception {
		if(userId == null && "".equals(userId)){
			throw new Exception("userId参数不能为空！");
		}
		RoomForbiddenExpire roomForbiddenExpire = new RoomForbiddenExpire();
		Long timeStemp = redisRoomForbiddenExpireMapper.getRedisRoomForbiddenExpire(userId);
		if(timeStemp == null ){
			return null;
		}
		roomForbiddenExpire.setUserId(new BigInteger(userId));
		roomForbiddenExpire.setEndTime(new Date(timeStemp));
		return roomForbiddenExpire;
	}
	
	@Override
	public List<RoomForbiddenExpire> queryRedisRoomForbiddenExpire(Date endTime) throws Exception {
		if(endTime == null){
			throw new Exception("startTime或是endTime不能为空！");
		}
		long start = 0;
		long end = endTime.getTime();
		Set<Tuple> sets = redisRoomForbiddenExpireMapper.getRangeRedisRoomForbiddenExpire(start, end);
		List<RoomForbiddenExpire> lists = new ArrayList<RoomForbiddenExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				RoomForbiddenExpire expire = new RoomForbiddenExpire();
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
		Set<Tuple> sets = redisRoomForbiddenExpireMapper.getAllRedisRoomForbiddenExpire();
		List<RoomForbiddenExpire> lists = new ArrayList<RoomForbiddenExpire>();
		if(sets != null && sets.size() > 0){
			for(Tuple t : sets){
				RoomForbiddenExpire expire = new RoomForbiddenExpire();
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
			RoomForbiddenExpire expire = (RoomForbiddenExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.RFE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisRoomForbiddenExpireMapper.addListRedisRoomForbiddenExpire(lists);
	}

	@Override
	public void deletePipList(List list) throws Exception {
		if(list == null){
			throw new Exception("list不能为空！");
		}
		List<SetCollectionBean> lists = new ArrayList<SetCollectionBean>();
		for(int i=0;i<list.size();i++){
			RoomForbiddenExpire expire = (RoomForbiddenExpire)list.get(i);
			SetCollectionBean s = new SetCollectionBean(Constant.RFE, expire.getUserId().toString(),expire.getEndTime().getTime());
			lists.add(s);
		}
		redisRoomForbiddenExpireMapper.deleteListRedisRoomForbiddenExpire(lists);
	}
}
