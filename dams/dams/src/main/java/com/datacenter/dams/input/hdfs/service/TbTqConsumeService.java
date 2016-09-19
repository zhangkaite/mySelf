package com.datacenter.dams.input.hdfs.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONObject;

import com.datacenter.dams.util.ConsumeSpendConstant;



/***
 * 统计TB、TQ消费
 * 
 * @author kate
 *
 */
public class TbTqConsumeService extends BaseService {

	@Override
	public Map<String, BigDecimal> analysisHdfsDatas(List<String> dataList) throws Exception {
		Map<String, BigDecimal> resultMap=new HashMap<String, BigDecimal>();
		//解析数据
		for (String data : dataList) {
			JSONObject jsonData=new JSONObject(data);
			String tb= jsonData.getString("tb");
			String tq= jsonData.getString("tq");
			if (tb!=null&&!"null".equals(tb)) {
				BigDecimal currentNum=new BigDecimal(tb);
				BigDecimal  dataNum=resultMap.get(ConsumeSpendConstant.TBCONSUME);
				if (null==dataNum) {
					resultMap.put(ConsumeSpendConstant.TBCONSUME, currentNum);
				}else {
					resultMap.put(ConsumeSpendConstant.TBCONSUME, currentNum.add(dataNum));
				}
			}else{
				BigDecimal currentNum=new BigDecimal(tq);
				BigDecimal  dataNum=resultMap.get(ConsumeSpendConstant.TQCONSUME);
				if (null==dataNum) {
					resultMap.put(ConsumeSpendConstant.TQCONSUME, currentNum);
				}else {
					resultMap.put(ConsumeSpendConstant.TQCONSUME, currentNum.add(dataNum));
				}
			}
		}

		return resultMap;
	}
	
	

}
