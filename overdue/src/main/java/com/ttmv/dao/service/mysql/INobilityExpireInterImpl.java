package com.ttmv.dao.service.mysql;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.NobilityExpire;
import com.ttmv.dao.bean.query.QueryBean;
import com.ttmv.dao.bean.query.QueryNobilityExpire;
import com.ttmv.dao.inter.mysql.INobilityExpireInter;
import com.ttmv.dao.mapper.mysql.NobilityExpireMapper;

@Service("iNobilityExpireInterImpl")
public class INobilityExpireInterImpl implements INobilityExpireInter {

	@Resource(name = "nobilityExpireMapper")
	private NobilityExpireMapper nobilityExpireMapper;
	
	@Override
	public Integer addNobilityExpire(NobilityExpire nobilityExpire) throws Exception {
		Integer result = nobilityExpireMapper.addNobilityExpire(nobilityExpire);
		return result;
	}

	@Override
	public Integer updateNobilityExpire(NobilityExpire nobilityExpire) throws Exception {
		Integer result = nobilityExpireMapper.updateNobilityExpire(nobilityExpire);
		return result;
	}

	@Override
	public Integer deleteNobilityExpire(BigInteger id) throws Exception {
		Integer result = nobilityExpireMapper.deleteNobilityExpire(id);
		return result;
	}

	@Override
	public NobilityExpire queryNobilityExpire(BigInteger userId) throws Exception {
		NobilityExpire NobilityExpire = nobilityExpireMapper.queryNobilityExpire(userId);
		return NobilityExpire;
	}

	@Override
	public List<NobilityExpire> queryListNobilityExpireByEndTime(QueryNobilityExpire queryNobilityExpire)throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryNobilityExpire.getStartTime()));
		query.setEndTime(sdf.format(queryNobilityExpire.getEndTime()));
		List<NobilityExpire> datas = nobilityExpireMapper.queryListNobilityExpireByEndTime(query);
		return datas;
	}

	@Override
	public List<NobilityExpire> queryListNobilityExpireByRemindTime(
			QueryNobilityExpire queryNobilityExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		QueryBean query = new QueryBean();
		query.setRemindTime(sdf.format(queryNobilityExpire.getRemindTime()));
		List<NobilityExpire> datas = nobilityExpireMapper.queryListNobilityExpireByRemindTime(query);
		return datas;
	}

	@Override
	public List<NobilityExpire> queryListDelayNotify(QueryNobilityExpire queryNobilityExpire) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		QueryBean query = new QueryBean();
		query.setStartTime(sdf.format(queryNobilityExpire.getStartTime()));
		query.setEndTime(sdf.format(queryNobilityExpire.getEndTime()));
		List<NobilityExpire> datas = nobilityExpireMapper.queryListDelayNotify(query);
		return datas;
	}
}
