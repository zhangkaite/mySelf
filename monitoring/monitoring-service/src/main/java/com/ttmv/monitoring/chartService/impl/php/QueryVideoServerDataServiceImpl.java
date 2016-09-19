package com.ttmv.monitoring.chartService.impl.php;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.PhpVideoServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPVideoServerDataInter;

public class QueryVideoServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryVideoServerDataServiceImpl.class);

	private IPHPVideoServerDataInter iPHPVideoServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iPHPVideoServerDataInter.queryPhpVideoServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new PhpVideoServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) throws Exception{
		if (reqMap.get("time") == null || "".equals(reqMap.get("time"))) {//time非空验证
            throw new DataFormatException("数据校验失败:[QueryVideoServerDataServiceImpl_[time] is null...]");
        }
		if (reqMap.get("previousId") == null || "".equals(reqMap.get("previousId"))) {//previousId非空验证
            throw new DataFormatException("数据校验失败:[QueryVideoServerDataServiceImpl_[previousId] is null...]");
        }
	}

	public IPHPVideoServerDataInter getiPHPVideoServerDataInter() {
		return iPHPVideoServerDataInter;
	}

	public void setiPHPVideoServerDataInter(IPHPVideoServerDataInter iPHPVideoServerDataInter) {
		this.iPHPVideoServerDataInter = iPHPVideoServerDataInter;
	}
}
