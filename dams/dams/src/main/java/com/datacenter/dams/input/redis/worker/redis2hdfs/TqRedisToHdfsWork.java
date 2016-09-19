package com.datacenter.dams.input.redis.worker.redis2hdfs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.datacenter.dams.input.redis.worker.util.HadoopFSOperations;
import com.datacenter.dams.util.ConsumeSpendConstant;
import com.datacenter.dams.util.DateUtil;
import com.datacenter.worker.worker.QuartzWorker;
import com.ttmv.datacenter.agent.redis.queue.RedisQueueImpi;
@SuppressWarnings("rawtypes")
public class TqRedisToHdfsWork extends QuartzWorker{
	private static Logger logger = LogManager.getLogger(TqRedisToHdfsWork.class);
	// 注入redis操作的工具类
	private RedisQueueImpi redisQueueImpi;
	// 注入redis队列名称
	private String redisQueueName;

	public RedisQueueImpi getRedisQueueImpi() {
		return redisQueueImpi;
	}

	public void setRedisQueueImpi(RedisQueueImpi redisQueueImpi) {
		this.redisQueueImpi = redisQueueImpi;
	}

	public String getRedisQueueName() {
		return redisQueueName;
	}

	public void setRedisQueueName(String redisQueueName) {
		this.redisQueueName = redisQueueName;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	private int num = 0;
	@Override
	protected List getData(int arg0) {
		List ls = null;
		if (num > 0) {
			try {
				ls = redisQueueImpi.getValueBatch(redisQueueName, num);
			} catch (Exception e) {
				logger.error("从redis批量获取TQ消费信息失败，失败的原因是:", e);
			}
		}
		return ls;
	}


	@Override
	protected void process(Object obj) throws Exception {
		// 将obj转换成String，然后转换成jsonObj,获取里面的时间和当前时间进行匹配
		String data = (String) obj;
		JSONObject jsonObject = new JSONObject(data);
		// 获取的时间是Unix时间戳
		String time = String.valueOf(jsonObject.get("time"));
		Date dataTime = DateUtil.getDate(time);
		String formatDataTime = sdf.format(dataTime);
		//获取系统当前时间
		Date currentTime = new Date();
		String formatCurrentDataTime = sdf.format(currentTime);
        //每次处理的数据为一批数据，这批数据存在两种情况：1、消费时间小于当前系统时间2、消费时间和系统时间相同。如果消费时间在系统时间之前，则将数据写入消费时间路径的hdfs
		//否则将数据写入当前系统时间路径的hdfs
		if (dataTime.before(currentTime)) {
				//	HadoopFSOperations.createNewHDFSFile(ConsumeSpendConstant.HDFSCONSUMEPATH + formatDataTime, data);
					num++;
				} else {
					// 不一致存放新的hdfs路径
				//	HadoopFSOperations.createNewHDFSFile(ConsumeSpendConstant.HDFSCONSUMEPATH + formatCurrentDataTime, data);
					//如果存在num>0说明昨天的数据已处理完成,调用mr统计jar包
					if (num > 0) {
					//	 Runtime.getRuntime().exec(ConsumeSpendConstant.TQSTATISTICJAR+formatDataTime);
						//process.destroy();
					}
				}
	}

	@Override
	protected String toLog(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
