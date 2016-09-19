package com.datacenter.test.dao.queue.output;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.datacenter.dams.business.dao.redis.inner.input.TcoinConsumeRedisQueueInputDao;

public class RicherRankListQueueDaoTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		TcoinConsumeRedisQueueInputDao dao = (TcoinConsumeRedisQueueInputDao) context.getBean("tcoinConsumeRedisQueueDao");
		try {
			dao.sendToRedisQueue("吴林立");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
