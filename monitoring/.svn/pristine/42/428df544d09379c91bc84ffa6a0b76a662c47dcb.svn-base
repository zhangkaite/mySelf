package com.ttmv.monitoring.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import com.ttmv.monitoring.entity.LiveRoomInfo;

public class LiveRoomInfoDaoMapper {

	private SqlSessionFactory sqlSessionFactory;
	private final String LIVEROOMINFO_MAPPER ="com.ttmv.monitoring.inter.LiveRoomInfoMapper";
	
	public Integer addLiveRoomInfo(LiveRoomInfo liveRoomInfo) {
		Integer result = sqlSessionFactory.openSession().insert(this.LIVEROOMINFO_MAPPER + ".insertSelective", liveRoomInfo);
		return result;
	}

	public Integer updateLiveRoomInfo(LiveRoomInfo liveRoomInfo) {
		Integer result = sqlSessionFactory.openSession().update(this.LIVEROOMINFO_MAPPER + ".updateByPrimaryKeySelective", liveRoomInfo);
		return result;
	}

	public Integer deleteLiveRoomInfo(BigInteger id) {
		Integer result = sqlSessionFactory.openSession().delete(this.LIVEROOMINFO_MAPPER + ".deleteByPrimaryKey", id);
		return result;
	}

	public LiveRoomInfo queryLiveRoomInfo(BigInteger id) {
		LiveRoomInfo data = sqlSessionFactory.openSession().selectOne(this.LIVEROOMINFO_MAPPER + ".selectByPrimaryKey", id);
		return data;
	}

	public List<LiveRoomInfo> queryPageLiveRoomInfo(LiveRoomInfo liveRoomInfo) {
		List<LiveRoomInfo> datas =  sqlSessionFactory.openSession().selectList(this.LIVEROOMINFO_MAPPER + ".selectByConditions", liveRoomInfo);
		return datas;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
}
