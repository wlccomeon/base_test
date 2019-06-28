package com.lc.test.mq.activemq.simple;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description: 事务消息生产者---一定要在session关闭之前记得commit。
 * @author: wlc
 * @date: 2019/6/27 0027 10:29
 **/
public class QueueTransactionProducer {
	private static String BROKER_URL = "tcp://192.168.20.138:61616";
	private static String QUEUE_NAME = "lc_transaction_queue";

	public static void main(String[] args) throws JMSException {

		//创建连接工厂
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
		//创建连接
		Connection connection = connectionFactory.createConnection();
		connection.start();
		//第1个参数为是否支持事务，第2个参数为是否使用消息确认机制
		Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
		//session创建destination，queue和topic都是destination。
		Queue queue = session.createQueue(QUEUE_NAME);
		//创建生产者，为destination赋值
		MessageProducer producer = session.createProducer(queue);

		//使用消息提供者发送多个消息
		for (int i = 1; i <= 6; i++) {
			//创建文本消息
			TextMessage textMessage = session.createTextMessage();

			textMessage.setText("lc_transaction_msg_"+i);
			//生产者发送消息
			producer.send(queue,textMessage);
		}

		//提交事务
		session.commit();

		//关闭资源
		producer.close();
		session.close();
		connection.close();

		System.out.println("消息发送到mq服务器。。。");

	}
}
