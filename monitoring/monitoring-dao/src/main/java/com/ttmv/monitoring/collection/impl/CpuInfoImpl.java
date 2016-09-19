package com.ttmv.monitoring.collection.impl;

import java.util.List;

import com.ttmv.monitoring.collection.entity.CpuInfo;
import com.ttmv.monitoring.collection.interf.ICpuInfo;
import com.ttmv.monitoring.collection.mapper.CpuInfoDaoMapper;
import com.ttmv.monitoring.entity.page.Page;

public class CpuInfoImpl implements ICpuInfo {

	private CpuInfoDaoMapper cpuInfoDaoMapper;

	
	public Page queryPageCpuInfo(CpuInfo cpuInfo) throws Exception {
		Integer page = cpuInfo.getPage();
		Integer pageSize = cpuInfo.getPageSize();
		if(page == null || pageSize == null){
			throw new Exception("page或是pageSize不是为空！");
		}
		if(page < 1 || pageSize < 1 ){
			throw new Exception("page不能小于1或是pageSize不能小于1！");
		}
		/* 结果对象 */
		Page p = new Page();
		/* 总数 */
		Integer sum = cpuInfoDaoMapper.queryPageUserInfoSum(cpuInfo);
		if(sum == null || sum == 0){
			return null;
		}
		/* 计算起始位置 */
		Integer start = getPagingStart(sum,page,pageSize);
		cpuInfo.setStart(start);
		List<CpuInfo> datas = cpuInfoDaoMapper.queryPageUserInfo(cpuInfo);
		p.setSum(sum);
		p.setData(datas);
		return p;
	}
	
	/**
	 * 获取userInfo分页的起始位置
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

	public CpuInfoDaoMapper getCpuInfoDaoMapper() {
		return cpuInfoDaoMapper;
	}

	public void setCpuInfoDaoMapper(CpuInfoDaoMapper cpuInfoDaoMapper) {
		this.cpuInfoDaoMapper = cpuInfoDaoMapper;
	}

}
