package com.lc.test.mq.activemq.simple;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 消息接收者，如果使用签收机制，则在接收到消息之后，message必须显示的调用acknowledge，
 * 				才能确保消息服务器上的消息被消费并删除掉。
 * @author: wlc
 * @date: 2019/6/27 0027 10:33
 **/
public class QueueAcknowledgeConsumer {


	private static String BROKER_URL = "tcp://192.168.20.138:61616";
	private static String QUEUE_NAME = "lc_acknowledge_queue";

	public static void main(String[] args){
		System.out.println("我是消费者1");
		try {
			//创建连接工厂
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
			//创建连接
			Connection connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			//第1个参数为是否支持事务，第2个参数为是否使用消息确认机制(手动签收)
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
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
					//手动签收消息，mq服务器才会删除，否则会一直存在，导致重复消费消息。
					textMessage.acknowledge();
				}else{
					break;
				}
			}



			/**第2种消费方式：通过监听的方式来消费消息
			// 使用异步非阻塞方式(监听器onMessage())，
			//订阅者或接收者通过MessageConsummer的SetMessageListener注册一个消息监听器
			//当消息到达后，系统自动调用坚挺着的onMessage方法
			consumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					if (message!=null && message instanceof TextMessage){
						TextMessage txtMsg = (TextMessage)message;
						try {
							//睡眠1秒，测试两个消费者。
							try {
							    TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
							    e.printStackTrace();
							}
							System.out.println("messageListener方式接收到数据："+txtMsg.getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
			});

			//防止消费者还没消费，连接就给关了。。
			System.in.read();
			 */

			//关闭资源
			consumer.close();
			session.close();
			connection.close();

		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
