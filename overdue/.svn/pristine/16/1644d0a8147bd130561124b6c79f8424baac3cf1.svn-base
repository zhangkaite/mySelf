package com.ttmv.dao.service.mysql;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ttmv.dao.bean.Cmp;
import com.ttmv.dao.inter.mysql.ICmpExpireInter;
import com.ttmv.dao.mapper.mysql.CmpExpireMapper;

@Service("icmpExpireInterImpl")
public class ICmpExpireInterImpl implements ICmpExpireInter {

	private static Logger logger=LogManager.getLogger(ICmpExpireInterImpl.class);
	
	@Resource(name = "cmpExpireMapper")
	private CmpExpireMapper cmpExpireMapper;

	@Override
	public Integer addOrUpdateCmp(Cmp cmp) throws Exception {
		Integer result = null;
		if(cmp != null){
			BigInteger userId = cmp.getUserId();
			String tag = cmp.getTag();
			Cmp query = new Cmp();
			query.setTag(tag);
			query.setUserId(userId);
			query.setType(cmp.getType());
			List<Cmp> cmps = cmpExpireMapper.queryListCmpByBean(query);
			if(cmps != null && cmps.size() > 0){
				cmp.setId(cmps.get(0).getId());
				result = this.updateCmp(cmp);
				logger.info("[到期系统ICmpExpireInterImpl]修改成功。用户ID是：" + cmp.getUserId());
			}else{					
				result = cmpExpireMapper.addCmp(cmp);
				logger.info("[到期系统ICmpExpireInterImpl]添加成功。用户ID是：" + cmp.getUserId());				
			}
			return result;
		}
		return null;
	}

	@Override
	public Integer updateCmp(Cmp cmp) throws Exception {
		if(cmp != null){
			Integer result = cmpExpireMapper.updateCmp(cmp);
			return result;
		}
		return null;
	}

	@Override
	public Integer deleteCmp(Cmp cmp) throws Exception {
		if(cmp != null){
			Integer result = cmpExpireMapper.deleteCmp(cmp);
			logger.info("[到期系统ICmpExpireInterImpl]删除成功。用户ID是：" + cmp.getUserId());
			return result;
		}
		return null;
	}

	@Override
	public Cmp queryCmp(BigInteger userId) throws Exception {
		if(userId != null){
			Cmp cmp = cmpExpireMapper.queryCmp(userId);
			logger.info("[到期系统ICmpExpireInterImpl]查询成功。用户ID是：" + cmp.getUserId());
			return cmp;
		}
		return null;
	}

	@Override
	public List<Cmp> queryListCmpByBean(Cmp cmp) throws Exception {
		if(cmp != null){
			List<Cmp> list = cmpExpireMapper.queryListCmpByBean(cmp);
			logger.info("[到期系统ICmpExpireInterImpl]查询cmp列表成功。");
			return list;
		}
		return null;
	}
}
