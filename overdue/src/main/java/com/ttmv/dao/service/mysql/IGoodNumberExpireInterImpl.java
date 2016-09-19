package com.ttmv.dao.service.mysql;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.GoodNumberExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.bean.query.QueryGoodNumberExpire;
import com.ttmv.dao.inter.mysql.IGoodNumberExpireInter;
import com.ttmv.dao.mapper.mysql.GoodNumberExpireMapper;

@Service("iGoodNumberExpireInterImpl")
public class IGoodNumberExpireInterImpl implements IGoodNumberExpireInter {

	@Resource(name = "goodNumberExpireMapper")
	private GoodNumberExpireMapper goodNumberExpireMapper;
	
	@Override
	public Integer addGoodNumberExpire(GoodNumberExpire goodNumberExpire) throws Exception {
		Integer result = goodNumberExpireMapper.addGoodNumberExpire(goodNumberExpire);
		return result;
	}

	@Override
	public Integer updateGoodNumberExpire(GoodNumberExpire goodNumberExpire) throws Exception {
		Integer result = goodNumberExpireMapper.updateGoodNumberExpire(goodNumberExpire);
		return result;
	}

	@Override
	public Integer deleteGoodNumberExpire(BigInteger id) throws Exception {
		Integer result = goodNumberExpireMapper.deleteGoodNumberExpire(id);
		return result;
	}

	@Override
	public GoodNumberExpire queryGoodNumberExpire(BigInteger userId,BigInteger goodNumber) throws Exception {
		QueryBean query = new QueryBean();
		query.setUserId(userId);
		query.setTempId(goodNumber);
		GoodNumberExpire GoodNumberExpire = goodNumberExpireMapper.queryGoodNumberExpire(query);
		return GoodNumberExpire;
	}

	@Override
	public List<GoodNumberExpire> queryListGoodNumberExpireByEndTime(QueryGoodNumberExpire queryGoodNumberExpire)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryGoodNumberExpire.getStartTime()));
		query.setEndTime(sdf.format(queryGoodNumberExpire.getEndTime()));
		List<GoodNumberExpire> datas = goodNumberExpireMapper.queryListGoodNumberExpireByEndTime(query);
		return datas;
	}

	@Override
	public List<GoodNumberExpire> queryListGoodNumberExpireByRemindTime(
			QueryGoodNumberExpire queryGoodNumberExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setRemindTime(sdf.format(queryGoodNumberExpire.getRemindTime()));
		List<GoodNumberExpire> datas = goodNumberExpireMapper.queryListGoodNumberExpireByRemindTime(query);
		return datas;
	}

	@Override
	public Integer updateFlag(GoodNumberExpire goodNumberExpire) throws Exception {
		Integer result = goodNumberExpireMapper.updateFlag(goodNumberExpire);
		return result;
	}

	@Override
	public GoodNumberExpire queryBindedNum(GoodNumberExpire goodNumberExpire) throws Exception {
		GoodNumberExpire gExpire=goodNumberExpireMapper.queryBindedNum(goodNumberExpire);
		return gExpire;
	}

	@Override
	public List<GoodNumberExpire> queryListDelayNotify(QueryGoodNumberExpire queryGoodNumberExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryGoodNumberExpire.getStartTime()));
		query.setEndTime(sdf.format(queryGoodNumberExpire.getEndTime()));
		List<GoodNumberExpire> datas = goodNumberExpireMapper.queryListDelayNotify(query);
		return datas;
	}

	@Override
	public GoodNumberExpire queryGoodNumberExpire(BigInteger userId, BigInteger goodNumber, int numType)
			throws Exception {
		QueryBean query = new QueryBean();
		query.setUserId(userId);
		query.setTempId(goodNumber);
		query.setNumType(numType);
		GoodNumberExpire GoodNumberExpire = goodNumberExpireMapper.queryGoodNumberExpire(query);
		return GoodNumberExpire;
	}
}
