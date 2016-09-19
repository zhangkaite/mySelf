package com.ttmv.monitoring.imp;

import java.math.BigInteger;

import com.ttmv.monitoring.entity.LiveRoomInfo;
import com.ttmv.monitoring.inter.ILiveRoomInfoInter;
import com.ttmv.monitoring.mapper.LiveRoomInfoDaoMapper;

public class ILiveRoomInfoInterImpl implements ILiveRoomInfoInter {

	private LiveRoomInfoDaoMapper liveRoomInfoDaoMapper;
	
	public Integer addLiveRoomInfo(LiveRoomInfo liveRoomInfo) throws Exception {
		Integer result = liveRoomInfoDaoMapper.addLiveRoomInfo(liveRoomInfo);
		return result;
	}

	public Integer updateLiveRoomInfo(LiveRoomInfo liveRoomInfo)throws Exception {
		Integer result = liveRoomInfoDaoMapper.updateLiveRoomInfo(liveRoomInfo);
		return result;
	}

	public Integer deleteLiveRoomInfo(BigInteger id) throws Exception {
		Integer result = liveRoomInfoDaoMapper.deleteLiveRoomInfo(id);
		return result;
	}

	public LiveRoomInfo queryLiveRoomInfo(BigInteger id) throws Exception {
		LiveRoomInfo data = liveRoomInfoDaoMapper.queryLiveRoomInfo(id);
		return data;
	}

	public void setLiveRoomInfoDaoMapper(LiveRoomInfoDaoMapper liveRoomInfoDaoMapper) {
		this.liveRoomInfoDaoMapper = liveRoomInfoDaoMapper;
	}
}
