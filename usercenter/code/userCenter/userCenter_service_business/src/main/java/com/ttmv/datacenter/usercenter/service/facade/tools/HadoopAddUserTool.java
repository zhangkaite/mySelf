package com.ttmv.datacenter.usercenter.service.facade.tools;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * @author Damon_Zs
 * @version 创建时间：2015年7月29日09:27:27
 * @explain :异步队列消息发送器（hadoop统计分析）
 */
public class HadoopAddUserTool {

	private JmsTemplate hadoopAddUserjmsTemplate;

	public JmsTemplate getHadoopAddUserjmsTemplate() {
		return hadoopAddUserjmsTemplate;
	}
	public void setHadoopAddUserjmsTemplate(JmsTemplate hadoopAddUserjmsTemplate) {
		this.hadoopAddUserjmsTemplate = hadoopAddUserjmsTemplate;
	}

	/**
	 * 发送消息
	 * @param msg
	 */
	public void sendMessage(final String msg) {
		hadoopAddUserjmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message = session.createTextMessage(msg);
				return message;
			}
		});
	}
}
