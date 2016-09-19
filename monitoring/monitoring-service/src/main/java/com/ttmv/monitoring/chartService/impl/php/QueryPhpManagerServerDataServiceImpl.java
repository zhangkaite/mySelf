package com.ttmv.monitoring.chartService.impl.php;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ttmv.monitoring.chartService.impl.QueryData;
import com.ttmv.monitoring.entity.PhpManagerServerData;
import com.ttmv.monitoring.entity.querybean.DataBeanQuery;
import com.ttmv.monitoring.inter.IPHPManagerServerDataInter;

public class QueryPhpManagerServerDataServiceImpl  extends QueryData{

	private static Logger logger = LogManager.getLogger(QueryPhpManagerServerDataServiceImpl.class);

	private IPHPManagerServerDataInter iPHPManagerServerDataInter;

	@Override
	protected List getQuery(Object obj) throws Exception {
		List list = new ArrayList();
		list.add(iPHPManagerServerDataInter.queryPhpManagerServerDataByIpAndServerTypeAndPort((DataBeanQuery)obj));
		return list;
	}

	@Override
	protected Set<String> getCheckDataWhiteSet() {
		return null;
	}

	@Override
	protected Object getDataType() {
		return new PhpManagerServerData();
	}

	@Override
	protected void addAttributesToQuery(Map reqMap, Object data) throws Exception{
		if (reqMap.get("time") == null || "".equals(reqMap.get("time"))) {//time非空验证
            throw new DataFormatException("数据校验失败:[QueryPhpManagerServerDataServiceImpl_[time] is null...]");
        }
		if (reqMap.get("previousId") == null || "".equals(reqMap.get("previousId"))) {//previousId非空验证
            throw new DataFormatException("数据校验失败:[QueryPhpManagerServerDataServiceImpl_[previousId] is null...]");
        }
	}

	public IPHPManagerServerDataInter getiPHPManagerServerDataInter() {
		return iPHPManagerServerDataInter;
	}

	public void setiPHPManagerServerDataInter(IPHPManagerServerDataInter iPHPManagerServerDataInter) {
		this.iPHPManagerServerDataInter = iPHPManagerServerDataInter;
	}
}
