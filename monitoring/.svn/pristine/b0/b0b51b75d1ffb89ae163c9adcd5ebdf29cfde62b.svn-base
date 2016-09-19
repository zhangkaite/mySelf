package com.ttmv.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ttmv.monitoring.entity.ThresholdInfo;
import com.ttmv.monitoring.imp.IThresholdInfoInterImpl;

public class TestThresholdInfo {

	/**
	 * 根据类型查询 列表集合
	 * @throws Exception
	 */
	@Test
	public void searchThresholdInfo()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IThresholdInfoInterImpl impl = (IThresholdInfoInterImpl)context.getBean("iThresholdInfoInterImpl");
		List<ThresholdInfo> list = impl.queryThresholdInfoByType("1");
		for(int i=0;i<list.size();i++){
			ThresholdInfo info = list.get(i);
			System.out.println(info.getThresholdAlerterIds());
			System.out.println(info.getThresholdShowName());
		}
	}
	
	/**
	 *修改阀值的列表
	 */
	@Test
	public void updateThresholdInfo()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IThresholdInfoInterImpl impl = (IThresholdInfoInterImpl)context.getBean("iThresholdInfoInterImpl");
		Map map = new HashMap();
		map.put("threshold_type", "MF");
		map.put("alerterID", "4");
		List<Map> list = new ArrayList<Map>();
		Map q = new HashMap();
		q.put("cpu", 30);
		list.add(q);
		q = new HashMap();
		q.put("mem", 30);
		list.add(q);
		q = new HashMap();
		q.put("disk", 30);
		list.add(q);
		map.put("params", list);
		impl.updateListThresholdInfo(map);
	}
	
	/**
	 * 查询全部列表集合
	 * @throws Exception
	 */
	@Test
	public void searchAllThresholdInfo()throws Exception{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-dao.xml");
		IThresholdInfoInterImpl impl = (IThresholdInfoInterImpl)context.getBean("iThresholdInfoInterImpl");
		List<ThresholdInfo> list = impl.queryAllThresholdInfo();
		for(int i=0;i<list.size();i++){
			ThresholdInfo info = list.get(i);
			System.out.println(info.getThresholdAlerterIds());
		}
	}
}
