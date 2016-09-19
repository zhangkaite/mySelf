package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ttmv.monitoring.entity.ThresholdInfo;
import com.ttmv.monitoring.inter.IThresholdInfoInter;
import com.ttmv.monitoring.mapper.ThresholdInfoDaoMapper;

public class IThresholdInfoInterImpl implements IThresholdInfoInter {

	private ThresholdInfoDaoMapper thresholdInfoDaoMapper;
	
	public List<ThresholdInfo> queryThresholdInfoByType(String type)throws Exception {
		if(type == null || "".equals(type)){
			throw new Exception("查询类型值不能为空！");
		}
		List<ThresholdInfo> resultList = thresholdInfoDaoMapper.queryThresholdInfo(type);
		return resultList;
	}
	
	public List<ThresholdInfo> queryAllThresholdInfo() throws Exception {
		List<ThresholdInfo> resultList = thresholdInfoDaoMapper.queryAllThresholdInfo();
		return resultList;
	}
	
	public Integer updateListThresholdInfo(Map map)throws Exception {
		if(map == null ||map.size() == 0){
			throw new Exception("修改阀值对象不能为空！");
		}
		/* 循环外层值 */
		List<ThresholdInfo> thresholdInfos = new ArrayList<ThresholdInfo>();
		String alerterId = null;
		String type = null;
		List<Map> listMap = null; 
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(key.equals("params")){
				listMap = (List<Map>)map.get(key);
			}
			if(key.equals("alerterID")){
				alerterId = map.get(key).toString();
			}
			if(key.equals("threshold_type")){
				type = map.get(key).toString();
			}
		}
		/* 获取更新的List */
		thresholdInfos = getListThresholdInfo(alerterId,type,listMap);
		/* 更新对象 */
		Integer result = thresholdInfoDaoMapper.updateThresholdInfos(thresholdInfos);
		return result;
	}

	public Integer updateListThresholdInfo(ThresholdInfo thresholdInfo)throws Exception {
		if(thresholdInfo == null ){
			throw new Exception("修改阀值不能为空！");
		}
		Integer result = thresholdInfoDaoMapper.updateThresholdInfo(thresholdInfo);
		return result;
	}
	
	public ThresholdInfo queryThresholdInfo(BigInteger id) throws Exception {
		if(id == null ){
			throw new Exception("查询的Id不能为空！");
		}
		ThresholdInfo result = thresholdInfoDaoMapper.queryThresholdInfo(id);
		return result;
	}

	/**
	 * 转换Map对象为ThresholdInfo对象
	 * @param alerterId
	 * @param type
	 * @param listMap
	 * @return
	 * @throws Exception
	 */
	private List<ThresholdInfo> getListThresholdInfo(String alerterId,String type,List<Map> listMap)throws Exception{
		List<ThresholdInfo> list = new ArrayList<ThresholdInfo>();
		if(listMap != null && listMap.size() != 0){
			for(int i=0;i<listMap.size();i++){
				Map map = listMap.get(i);
				Set<String> set = map.keySet();
				Iterator<String> iterator = set.iterator();
				String key = iterator.next();
				String value = map.get(key).toString();
				ThresholdInfo q = new ThresholdInfo();
				q.setThresholdType(type);
				q.setThresholdName(key);
				ThresholdInfo info = thresholdInfoDaoMapper.queryThresholdInfoByTypeAndName(q);
				
				/* 组装修改对象 */
				ThresholdInfo u= new ThresholdInfo();
				u.setId(info.getId());
				u.setThresholdAlerterId(alerterId);
				u.setThresholdName(key);
				u.setThresholdValue(value);
				u.setThresholdType(type);
				list.add(u);
			}
		}
		return list;
	}
	public ThresholdInfoDaoMapper getThresholdInfoDaoMapper() {
		return thresholdInfoDaoMapper;
	}

	public void setThresholdInfoDaoMapper(ThresholdInfoDaoMapper thresholdInfoDaoMapper) {
		this.thresholdInfoDaoMapper = thresholdInfoDaoMapper;
	}
}
