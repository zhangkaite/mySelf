package com.ttmv.financialStatistics;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 对map的数据根据key进行累加计算
 */
public class DataStatisticReducer extends Reducer<Text, Text, Text, Text> {
	private static Logger logger = LogManager.getLogger(DataStatisticReducer.class);


	
	public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		BigDecimal f = new BigDecimal(0);
		for (Text val : values) {
			// 将Text转换成BigDecimal
			String num = val.toString();
			f=f.add(new BigDecimal(num));
		}
		// context.write(key, result);
		logger.info("reduce 阶段统计的数据key:"+key+";value:"+f.toString());
        context.write(key, new Text(f.toString()));
	}
	
	
	
	
	
	

}
