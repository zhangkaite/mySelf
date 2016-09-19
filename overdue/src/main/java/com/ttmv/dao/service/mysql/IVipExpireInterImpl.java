package com.ttmv.dao.service.mysql;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.VipExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.bean.query.QueryVipExpire;
import com.ttmv.dao.inter.mysql.IVipExpireInter;
import com.ttmv.dao.mapper.mysql.VipExpireMapper;

@Service("iVipExpireInterImpl")
public class IVipExpireInterImpl implements IVipExpireInter {

	@Resource(name = "vipExpireMapper")
	private VipExpireMapper vipExpireMapper;
	
	@Override
	public Integer addVipExpire(VipExpire vipExpire) throws Exception {
		Integer result = vipExpireMapper.addVipExpire(vipExpire);
		return result;
	}

	@Override
	public Integer updateVipExpire(VipExpire vipExpire) throws Exception {
		Integer result = vipExpireMapper.updateVipExpire(vipExpire);
		return result;
	}

	@Override
	public Integer deleteVipExpire(BigInteger id) throws Exception {
		Integer result = vipExpireMapper.deleteVipExpire(id);
		return result;
	}

	@Override
	public VipExpire queryVipExpire(BigInteger userId) throws Exception {
		VipExpire vipExpire = vipExpireMapper.queryVipExpire(userId);
		return vipExpire;
	}

	@Override
	public List<VipExpire> queryListVipExpireByEndTime(QueryVipExpire queryVipExpire)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryVipExpire.getStartTime()));
		query.setEndTime(sdf.format(queryVipExpire.getEndTime()));
		List<VipExpire> datas = vipExpireMapper.queryListVipExpireByEndTime(query);
		return datas;
	}

	@Override
	public List<VipExpire> queryListVipExpireByRemindTime(
			QueryVipExpire queryVipExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setRemindTime(sdf.format(queryVipExpire.getRemindTime()));
		List<VipExpire> datas = vipExpireMapper.queryListVipExpireByRemindTime(query);
		return datas;
	}

	@Override
	public List<VipExpire> queryListDelayNotify(QueryVipExpire queryVipExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryVipExpire.getStartTime()));
		query.setEndTime(sdf.format(queryVipExpire.getEndTime()));
		List<VipExpire> datas = vipExpireMapper.queryListDelayNotify(query);
		return datas;
	}
}
