package com.ttmv.dao.service.mysql;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.LuxuryExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.bean.query.QueryLuxuryExpire;
import com.ttmv.dao.inter.mysql.ILuxuryExpireInter;
import com.ttmv.dao.mapper.mysql.LuxuryExpireMapper;

@Service("iLuxuryExpireInterImpl")
public class ILuxuryExpireInterImpl implements ILuxuryExpireInter {

	@Resource(name = "luxuryExpireMapper")
	private LuxuryExpireMapper luxuryExpireMapper;
	
	@Override
	public Integer addLuxuryExpire(LuxuryExpire luxuryExpire) throws Exception {
		Integer result = luxuryExpireMapper.addLuxuryExpire(luxuryExpire);
		return result;
	}

	@Override
	public Integer updateLuxuryExpire(LuxuryExpire luxuryExpire) throws Exception {
		Integer result = luxuryExpireMapper.updateLuxuryExpire(luxuryExpire);
		return result;
	}

	@Override
	public Integer deleteLuxuryExpire(BigInteger id) throws Exception {
		Integer result = luxuryExpireMapper.deleteLuxuryExpire(id);
		return result;
	}

	@Override
	public LuxuryExpire queryLuxuryExpire(BigInteger userId,BigInteger carId) throws Exception {
		QueryBean query = new QueryBean();
		query.setUserId(userId);
		query.setTempId(carId);
		LuxuryExpire luxuryExpire =luxuryExpireMapper.queryLuxuryExpire(query);
		return luxuryExpire;
	}

	@Override
	public List<LuxuryExpire> queryListLuxuryExpireByEndTime(QueryLuxuryExpire queryLuxuryExpire)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryLuxuryExpire.getStartTime()));
		query.setEndTime(sdf.format(queryLuxuryExpire.getEndTime()));
		List<LuxuryExpire> datas = luxuryExpireMapper.queryListLuxuryExpireByEndTime(query);
		return datas;
	}

	@Override
	public List<LuxuryExpire> queryListLuxuryExpireByRemindTime(
			QueryLuxuryExpire queryLuxuryExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setRemindTime(sdf.format(queryLuxuryExpire.getRemindTime()));
		List<LuxuryExpire> datas = luxuryExpireMapper.queryListLuxuryExpireByRemindTime(query);
		return datas;
	}

	@Override
	public List<LuxuryExpire> queryListDelayNotify(QueryLuxuryExpire queryLuxuryExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryLuxuryExpire.getStartTime()));
		query.setEndTime(sdf.format(queryLuxuryExpire.getEndTime()));
		List<LuxuryExpire> datas = luxuryExpireMapper.queryListDelayNotify(query);
		return datas;
	}
}
