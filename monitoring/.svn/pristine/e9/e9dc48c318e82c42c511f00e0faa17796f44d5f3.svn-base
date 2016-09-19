package com.ttmv.monitoring.imp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.ttmv.monitoring.entity.AlerterInfo;
import com.ttmv.monitoring.entity.page.Page;
import com.ttmv.monitoring.entity.querybean.AlerterInfoQuery;
import com.ttmv.monitoring.entity.tmp.AlerterInfoTmp;
import com.ttmv.monitoring.inter.IAlerterInfoInter;
import com.ttmv.monitoring.mapper.AlerterInfoDaoMapper;

public class IAlerterInfoInterImpl implements IAlerterInfoInter {

	private AlerterInfoDaoMapper alerterInfoDaoMapper;
	
	public Integer addAlerterInfo(AlerterInfoTmp alerterInfo) throws Exception {
		if(alerterInfo == null ){
			throw new Exception("新增报警器对象不能为空！");
		}
		AlerterInfo info = new AlerterInfo();
		BeanUtils.copyProperties(alerterInfo, info);
		if(alerterInfo.getAlerterUser().size() != 0){
			String usersStr = this.getListToString(alerterInfo.getAlerterUser());
			info.setAlerterUsers(usersStr); 
		}
		Integer result = alerterInfoDaoMapper.addAlerterInfo(info);
		return result;
	}

	public Integer updateAlerterInfo(AlerterInfoTmp alerterInfo) throws Exception {
		if(alerterInfo == null ){
			throw new Exception("新增报警器对象不能为空！");
		}
		AlerterInfo info = new AlerterInfo();
		BeanUtils.copyProperties(alerterInfo, info);
		if(alerterInfo.getAlerterUser().size() != 0){
			String usersStr = this.getListToString(alerterInfo.getAlerterUser());
			info.setAlerterUsers(usersStr);
		}else if(alerterInfo.getAlerterUser().size() == 0){
			info.setAlerterUsers("");
		}
		Integer result = alerterInfoDaoMapper.updateAlerterInfo(info);
		return result;
	}

	public AlerterInfoTmp queryAlerterInfo(BigInteger id) throws Exception {
		AlerterInfo alerterInfo = alerterInfoDaoMapper.queryAlerterInfo(id);
		if(alerterInfo != null){
			AlerterInfoTmp tmp = this.getAlerterInfoTmp(alerterInfo);
			return tmp;
		}
		return null;
	}

	public Page queryPageAlerterInfo(AlerterInfoQuery alerterInfoQuery)throws Exception {
		Integer page = alerterInfoQuery.getPage();
		Integer pageSize = alerterInfoQuery.getPageSize();
		if(page == null || pageSize == null){
			throw new Exception("page或是pageSize不是为空！");
		}
		if(page < 1 || pageSize < 1 ){
			throw new Exception("page不能小于1或是pageSize不能小于1！");
		}
		/* 结果对象 */
		Page p = new Page();
		/* 总数 */
		Integer sum = alerterInfoDaoMapper.queryPageAlerterInfoSum(alerterInfoQuery);
		if(sum == null || sum == 0){
			return null;
		}
		/* 计算起始位置 */
		Integer start = getPagingStart(sum,page,pageSize);
		alerterInfoQuery.setStart(start);
		List<AlerterInfo> datas = alerterInfoDaoMapper.queryPageAlerterInfo(alerterInfoQuery);
		List<AlerterInfoTmp> list = new ArrayList<AlerterInfoTmp>();
		if(datas != null && datas.size() >0){
			for(int i=0;i<datas.size();i++){
				AlerterInfo info = datas.get(i);
				AlerterInfoTmp temp = getAlerterInfoTmp(info);
				list.add(temp);
			}
		}
		p.setSum(sum);
		p.setData(list);
		return p;
	}

	public List<AlerterInfoTmp> queryAlerterInfo(AlerterInfoQuery alerterInfoQuery)throws Exception {
		List<AlerterInfo> datas = alerterInfoDaoMapper.queryAlerterInfo(alerterInfoQuery);
		List<AlerterInfoTmp> list = new ArrayList<AlerterInfoTmp>();
		if(datas != null && datas.size() >0){
			for(int i=0;i<datas.size();i++){
				AlerterInfo info = datas.get(i);
				AlerterInfoTmp temp = getAlerterInfoTmp(info);
				list.add(temp);
			}
		}
		return list;
	}

	public Integer deleteAlerterInfo(BigInteger id) throws Exception {
		Integer result = alerterInfoDaoMapper.deleteAlerterInfo(id);
		return result;
	}
	
	/* 将list列表转换成字符串*/
	private String getListToString(List<String> list)throws Exception{
		if(list == null ||list.size() ==0){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.size();i++){
			if(i == list.size()-1){
				sb.append(String.valueOf(list.get(i)));
			}else{
				sb.append(String.valueOf(list.get(i)) + "|");
			}
		}
		return sb.toString();
	}
	/* 将String字符串转换成List列表 */
	private List<String> getStringToList(String str){
		List<String> list = new ArrayList<String>();
		if(str != null && !"".equals(str)){
			String temp[] = str.split("\\|");
			for(String s:temp){
				list.add(s);
			}
		}
		return list;
	}
	
	/* 将字符串转换成List列表 */
	private AlerterInfoTmp getAlerterInfoTmp(AlerterInfo alerterInfo)throws Exception{
		if(alerterInfo == null){
			throw new Exception("alerterInfo对象为空！");
		}
		AlerterInfoTmp tmp = new AlerterInfoTmp();
		BeanUtils.copyProperties(alerterInfo, tmp);
		tmp.setAlerterUser(getStringToList(alerterInfo.getAlerterUsers()));
		return tmp;
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
	
	public AlerterInfoDaoMapper getAlerterInfoDaoMapper() {
		return alerterInfoDaoMapper;
	}

	public void setAlerterInfoDaoMapper(AlerterInfoDaoMapper alerterInfoDaoMapper) {
		this.alerterInfoDaoMapper = alerterInfoDaoMapper;
	}
}
