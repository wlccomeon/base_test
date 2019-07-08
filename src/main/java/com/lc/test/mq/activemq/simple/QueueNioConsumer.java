package com.lc.test.mq.activemq.simple;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 消息接收者
 * @author: wlc
 * @date: 2019/6/27 0027 10:33
 **/
public class QueueNioConsumer {


	private static String BROKER_URL = "nio://192.168.20.138:61617";
	private static String QUEUE_NAME = "lc_nio_queue";

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
			Queue queue = session.createQueue(QUEUE_NAME);
			//创建消费者，为destination赋值
			MessageConsumer consumer = session.createConsumer(queue);

			/** 第1种消费方式：使用同步阻塞队列方式(receive())
			 * 订阅者或接收者调用MessageConsummer的receive()方法来接收消息，receive在接收到消息之前(或超时之前)将一直阻塞
			 **/
			while (true){
				//这种方式会一直监听消息服务器
//				TextMessage textMessage =(TextMessage) consumer.receive();
				//这种方式是5秒之后就停止接收消息了。。
				TextMessage textMessage =(TextMessage) consumer.receive(5000L);

				if (textMessage!=null){
					System.out.println("消息消费者接收到消息："+textMessage.getText());
				}else{
					break;
				}
			}

			//关闭资源
			consumer.close();
			session.close();
			connection.close();

		}catch (JMSException jmsE){
			jmsE.printStackTrace();
		}

	}

}
