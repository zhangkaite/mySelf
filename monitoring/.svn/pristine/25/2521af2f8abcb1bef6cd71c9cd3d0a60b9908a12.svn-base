package com.ttmv.monitoring.chartService.impl.mf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.chartService.impl.QueryDataById;
import com.ttmv.monitoring.entity.MediaForwardData;
import com.ttmv.monitoring.inter.IMediaForwardDataInter;

/**
 * CPU动态折线图
 * @author wll
 *
 */
public class QueryMediaForwardDataByIdServiceImpl  extends QueryDataById {

	private static Logger logger = LogManager.getLogger(QueryMediaForwardDataByIdServiceImpl.class);
	
	private IMediaForwardDataInter iMediaForwardDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		if(obj == null )
			throw new Exception("创建查询对象时候，传入的参数为空");
		List list = new ArrayList();
		list.add(iMediaForwardDataInter.queryMediaForwardData((BigInteger)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new MediaForwardData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) {

	}

	public IMediaForwardDataInter getiMediaForwardDataInter() {
		return iMediaForwardDataInter;
	}

	public void setiMediaForwardDataInter(
			IMediaForwardDataInter iMediaForwardDataInter) {
		this.iMediaForwardDataInter = iMediaForwardDataInter;
	}
}
