package com.ttmv.datacenter.usercenter.dao.implement.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.datacenter.usercenter.dao.implement.mapper.terminalforbid.RedisTerminalForbidMapper;
import com.ttmv.datacenter.usercenter.dao.implement.util.TableIdGenerate;
import com.ttmv.datacenter.usercenter.dao.interfaces.TerminalForbidDao;
import com.ttmv.datacenter.usercenter.domain.data.TerminalForbid;

public class TerminalForbidDaoImpl implements TerminalForbidDao{

	private static final String FORBID = "FORBID_";
	/* 日志输出类 */
	private final Logger log = LogManager.getLogger(TerminalForbidDaoImpl.class);
	
	private RedisTerminalForbidMapper redisTerminalForbidMapper;
	
	/**
	 * 添加TerminalForbid
	 */
	public Integer addTerminalForbid(TerminalForbid terminalForbid)throws Exception {
		
		if(terminalForbid == null){
			log.debug("添加的TerminalForbid对象不能为空！");
			throw new Exception("添加的TerminalForbid对象不能为空！");
		}		
		
		/* 添加redis */
		try{
			log.debug("[" + terminalForbid.getReqId() + "]@@" + "[redis准备添加key]");
			this.addTerminalForbidKey(terminalForbid);
			log.debug("[" + terminalForbid.getReqId() + "]@@" + "[redis准备添加key完毕]");
		}catch(Exception e){
			log.error("[" + terminalForbid.getReqId() + "]@@" ,e);
			throw new Exception(e);
		}
		return null;
	}
	
	/**
	 * 根据key删除终端禁用的列表
	 * @return 
	 */
	public void deleteTerminalForbidKey(TerminalForbid terminalForbid)throws Exception {
		
		log.debug("[" + terminalForbid.getReqId() + "]@@" + "[redis准备删除key]");
		/* 删除ip的key*/
		if(terminalForbid.getIp() != null && !"".equals(terminalForbid.getIp())){
			redisTerminalForbidMapper.deleteRedisTerminalForbidKey(FORBID + terminalForbid.getIp(),terminalForbid.getReqId());
		}
		/* 删除mac的key*/
		if(terminalForbid.getMac() != null && !"".equals(terminalForbid.getMac())){
			redisTerminalForbidMapper.deleteRedisTerminalForbidKey(FORBID + terminalForbid.getMac(),terminalForbid.getReqId());
		}
		/* 删除disksn的key*/
		if(terminalForbid.getDisksn() != null && !"".equals(terminalForbid.getDisksn())){
			redisTerminalForbidMapper.deleteRedisTerminalForbidKey(FORBID + terminalForbid.getDisksn(),terminalForbid.getReqId());
		}
		log.debug("[" + terminalForbid.getReqId() + "]@@" + "[redis准备删除key完毕]");
	}
	
	/**
	 * 判断Key是否存在
	 * @param key
	 * @return
	 */
	public Boolean isExistKey(TerminalForbid terminalForbid)throws Exception{
		boolean flag = false;
		/* 查询ip的key*/
		if(terminalForbid.getIp() != null && !"".equals(terminalForbid.getIp())){
			flag = redisTerminalForbidMapper.isExistKey(FORBID + terminalForbid.getIp());
			if(flag){
				log.debug("存在Ip key ～！");
			  	return flag;
			}
		}
		/* 存在mac的key*/
		if(terminalForbid.getMac() != null && !"".equals(terminalForbid.getMac())){
			flag = redisTerminalForbidMapper.isExistKey(FORBID + terminalForbid.getMac());
			if(flag){
				log.debug("存在mac key ～！");
			  	return flag;
			}
		}
		/* 存在disksn的key*/
		if(terminalForbid.getDisksn() != null && !"".equals(terminalForbid.getDisksn())){
			flag =  redisTerminalForbidMapper.isExistKey(FORBID + terminalForbid.getDisksn());
			if(flag){
				log.debug("存在disksn key ～！");
			  	return flag;
			}
		}
		return flag;
	}
	
	/**
	 *  添加终端禁用的key，添加至少一条最多三条key
	 *  前缀为：FORBID_
	 * @param terminalForbid
	 */
	private void addTerminalForbidKey(TerminalForbid terminalForbid)throws Exception{
		try{
			/* 添加ip的key*/
			if(terminalForbid.getIp() != null && !"".equals(terminalForbid.getIp())){
				redisTerminalForbidMapper.addRedisTerminalForbidKey(FORBID + terminalForbid.getIp(),terminalForbid.getReqId());
				log.debug("添加Ip key 成功～！");
			}
			/* 添加mac的key*/
			if(terminalForbid.getMac() != null && !"".equals(terminalForbid.getMac())){
				redisTerminalForbidMapper.addRedisTerminalForbidKey(FORBID + terminalForbid.getMac(),terminalForbid.getReqId());
				log.debug("添加mac Key 成功～！");
			}
			/* 添加disksn的key*/
			if(terminalForbid.getDisksn() != null && !"".equals(terminalForbid.getDisksn())){
				redisTerminalForbidMapper.addRedisTerminalForbidKey(FORBID + terminalForbid.getDisksn(),terminalForbid.getReqId());
				log.debug("添加disksn key成功～！");
			}
		}catch(Exception e){
			log.error("添加TerminalForbid 的key失败！失败的原因是：",e);
			throw new Exception("添加TerminalForbid 的key失败！失败的原因是：",e);
		}
	}
	
	public void setRedisTerminalForbidMapper(
			RedisTerminalForbidMapper redisTerminalForbidMapper) {
		this.redisTerminalForbidMapper = redisTerminalForbidMapper;
	}
}
