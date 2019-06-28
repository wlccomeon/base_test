package com.lc.test.mq.activemq.simple;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description: 发布订阅模式的消息生产者，一定要先启动消费者。
 * 				否则，发布的消息，后启动的消费者无法消费，成为了废消息。
 * @author: wlc
 * @date: 2019/6/27 0027 14:42
 **/
public class TopicProducer {


	private static String BROKER_URL = "tcp://192.168.20.138:61616";
	private static String TOPIC_NAME = "lc_first_active_topic";

	public static void main(String[] args) throws JMSException {

		//创建连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
		//创建连接
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//第1个参数为是否支持事务，第2个参数为是否使用消息确认机制
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//session创建destination，queue和topic都是destination。
		Topic topic = session.createTopic(TOPIC_NAME);
		//创建生产者，为destination赋值
		MessageProducer producer = session.createProducer(topic);

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
