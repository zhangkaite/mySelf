package com.ttmv.monitoring.imp;

import java.util.List;

import com.ttmv.monitoring.entity.AlertRecordInfo;
import com.ttmv.monitoring.entity.UserInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.AlertRecordInfoQuery;
import com.ttmv.monitoring.inter.IAlertRecordInfoInter;
import com.ttmv.monitoring.mapper.AlertRecordInfoDaoMapper;

public class IAlertRecordInfoInterImpl implements IAlertRecordInfoInter{

	private AlertRecordInfoDaoMapper alertRecordInfoDaoMapper;
	
	public Integer addAlertRecordInfo(AlertRecordInfo alertRecordInfo)
			throws Exception {
		Integer result = alertRecordInfoDaoMapper.addAlertRecordInfo(alertRecordInfo);
		return result;
	}

	public Page queryPageAlertRecordInfo(AlertRecordInfoQuery alertRecordInfoQuery) throws Exception {
		Integer page = alertRecordInfoQuery.getPage();
		Integer pageSize = alertRecordInfoQuery.getPageSize();
		if(page == null || pageSize == null){
			throw new Exception("page或是pageSize不是为空！");
		}
		if(page < 1 || pageSize < 1 ){
			throw new Exception("page不能小于1或是pageSize不能小于1！");
		}
		/* 结果对象 */
		Page p = new Page();
		/* 总数 */
		Integer sum = alertRecordInfoDaoMapper.queryPageAlertRecordInfoSum(alertRecordInfoQuery);
		if(sum == null || sum == 0){
			return null;
		}
		/* 计算起始位置 */
		Integer start = getPagingStart(sum,page,pageSize);
		alertRecordInfoQuery.setStart(start);
		List<AlertRecordInfo> datas = alertRecordInfoDaoMapper.queryPageAlertRecordInfo(alertRecordInfoQuery);
		p.setSum(sum);
		p.setData(datas);
		return p;
	}

	public List<AlertRecordInfo> queryListAlertRecordInfo(
			AlertRecordInfo alertRecordInfo) throws Exception {
		List<AlertRecordInfo> result = alertRecordInfoDaoMapper.queryListAlertRecordInfo(alertRecordInfo);
		return result;
	}

	/**
	 * 获取分页的起始位置
	 * 
	 * @param solrs
	 * @param page
	 * @param pageSize
	 */
	private Integer getPagingStart(Integer sum, Integer page, Integer pageSize)throws Exception{
		Integer start = 0;
		/* 分页的总数 */
		Integer sumPage = sum / pageSize + 1;
		/* 取得起始位置 */
		if (page <= sumPage) {
			start = (page - 1) * pageSize;
		}else{
			throw new Exception("page不正确,不能大于总页数或是小于1！");
		}
		return start;
	}
	
	
	public AlertRecordInfoDaoMapper getAlertRecordInfoDaoMapper() {
		return alertRecordInfoDaoMapper;
	}

	public void setAlertRecordInfoDaoMapper(
			AlertRecordInfoDaoMapper alertRecordInfoDaoMapper) {
		this.alertRecordInfoDaoMapper = alertRecordInfoDaoMapper;
	}
}
