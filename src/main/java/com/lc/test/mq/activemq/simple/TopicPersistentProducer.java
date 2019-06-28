package com.lc.test.mq.activemq.simple;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description: 发布订阅模式下的持久性测试，这个例子中增加订阅者(subscriber)的角色
 * 					1.connection.start()需要在produccer下才启动。
 * 					2.可以显示的将setDeliveryMode设置为	PERSISTENT
 * 				明白控制台中：Active Durable Topic Subscribers/Offline Durable Topic Subscribers/Active Non-Durable Topic Subscribers的含义
 * @author: wlc
 * @date: 2019/6/27 0027 14:42
 **/
public class TopicPersistentProducer {


	private static String BROKER_URL = "tcp://192.168.20.138:61616";
	private static String TOPIC_NAME = "lc_durable_topic";


	public static void main(String[] args) throws JMSException {

		System.out.println();

		//创建连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
		//创建连接
		Connection connection = connectionFactory.createConnection();
		//第1个参数为是否支持事务，第2个参数为是否使用消息确认机制
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//session创建destination，queue和topic都是destination。
		Topic topic = session.createTopic(TOPIC_NAME);
		//创建生产者，为destination赋值
		MessageProducer producer = session.createProducer(topic);
		//设置消息采用持久化方式(默认，可以不设置)
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		connection.start();

		//使用消息提供者发送多个消息
		for (int i = 1; i <= 6; i++) {
			//创建文本消息
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("lc_topic_msg_"+i);
			//生产者发送消息
			producer.send(topic,textMessage);
		}

		//关闭资源
		producer.close();
		session.close();
		connection.close();

		System.out.println("消息发送到mq服务器。。。");

	}

}
