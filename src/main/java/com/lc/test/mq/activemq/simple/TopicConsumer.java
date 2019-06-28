package com.lc.test.mq.activemq.simple;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 发布订阅模式的消费者
 * @author: wlc
 * @date: 2019/6/27 0027 14:42
 **/
public class TopicConsumer {


	private static String BROKER_URL = "tcp://192.168.20.138:61616";
	private static String TOPIC_NAME = "lc_first_active_topic";

	public static void main(String[] args){
		System.out.println("我是消费者1");
		try {
			//创建连接工厂
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
			//创建连接
			Connection connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			//第1个参数为是否支持事务，第2个参数为是否使用消息确认机制
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//session创建destination，queue和topic都是destination。
			Topic topic = session.createTopic(TOPIC_NAME);
			//创建消费者，为destination赋值
			MessageConsumer consumer = session.createConsumer(topic);


			//第2种消费方式：通过监听的方式来消费消息
			// 使用异步非阻塞方式(监听器onMessage())，
			//订阅者或接收者通过MessageConsummer的SetMessageListener注册一个消息监听器
			//当消息到达后，系统自动调用坚挺着的onMessage方法
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					if (message!=null && message instanceof TextMessage){
						TextMessage txtMsg = (TextMessage)message;
						try {
							try {
							    TimeUnit.SECONDS.sleep(2);
							} catch (InterruptedException e) {
							    e.printStackTrace();
							}
							System.out.println("topic pattern messageListener方式接收到数据："+txtMsg.getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
			});

			//防止消费者还没消费，连接就给关了。。
			System.in.read();

			//关闭资源
			consumer.close();
			session.close();
			connection.close();

		}catch (JMSException jmsE){
			jmsE.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//知识点(生产者生产6条消息)，结果都经过验证：
		//1.如果先启动生产者，然后启动消费者1，然后启动消费者2，消费者2能够消费到消息吗？
		//结果：1和2都不能。因为首先启动生产者的话，topic模式下的消息就是废消息。

		//如果消费的过程中处理的立即比较耗时(比如加个睡眠),那么2是可能消费到的。1消费到1/3/5,2消费到2/4/6。
		//2.如果先启动消费者1和2，然后启动生产者，消费者1和2都能消费到消息吗？分别消费到多少条？
		//结果：消费者1消费到1/3/5条消息，2消费到2/4/6消息。内部有类似负载均衡的机制。

	}


}
