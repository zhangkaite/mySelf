package com.ttmv.dao.mapper.mysql;

import java.math.BigInteger;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import com.ttmv.dao.bean.ScanRecord;
import com.ttmv.dao.constant.Constant;
import com.ttmv.datacenter.sentry.SentryAgent;

/**
 * Mysql扫描记录
 * @author wll
 *
 */
@Component("scanRecordMapper")
public class ScanRecordMapper {

	private String SCANRECORD_MAPPER = "com.ttmv.dao.inter.ScanRecordMapper";
	
	@Resource(name = "quickSentry")
	private SentryAgent quickSentry;
	@Resource(name = "sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;
	
	public Integer addScanRecord(ScanRecord scanRecord)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.selectOne(SCANRECORD_MAPPER
					+ ".insertSelective", scanRecord);
			if (result != null) {
				return result;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public Integer updateScanRecord(ScanRecord scanRecord)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.update(SCANRECORD_MAPPER
					+ ".updateByPrimaryKeySelective", scanRecord);
			if (result != null) {
				return result;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public ScanRecord queryScanRecord(Integer expireType)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			ScanRecord ScanRecord =session.selectOne(SCANRECORD_MAPPER
					+ ".selectByExpireType", expireType);
			if (ScanRecord != null) {
				return ScanRecord;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}
	
	public Integer deleteScanRecord(BigInteger id)throws Exception{
		SqlSession session =  null;
		try{
			session = sqlSessionFactory.openSession();
			Integer result =session.delete(SCANRECORD_MAPPER
					+ ".deleteByPrimaryKey", id);
			if (result != null) {
				return result;
			}
		}catch(Exception e){
			//quickSentry.sendMsg(Constant.OD_SERODR_TYPE, Constant.OD_SERODR_ID, Constant.OD_MYSQL_ERROR_MSG + e.getMessage(), Constant.OD_ERROR);
			throw new Exception(e);
		}finally{
			session.close();
		}
		return null;
	}

	public SentryAgent getQuickSentry() {
		return quickSentry;
	}
}
