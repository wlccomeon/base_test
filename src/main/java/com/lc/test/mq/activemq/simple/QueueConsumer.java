package com.lc.test.mq.activemq.simple;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.soap.Text;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 消息接收者
 * @author: wlc
 * @date: 2019/6/27 0027 10:33
 **/
public class QueueConsumer {


	private static String BROKER_URL = "tcp://192.168.20.138:61616";
	private static String QUEUE_NAME = "lc_first_active_queue";

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
			 *
			//不停的循环消费消息
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
			 */


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
			//结果：有可能，消费者1已经将消息全部消费掉了。那么2是不能消费了。
					//如果消费的过程中处理的立即比较耗时(比如加个睡眠),那么2是可能消费到的。1消费到1/3/5,2消费到2/4/6。
		//2.如果先启动消费者1和2，然后启动生产者，消费者1和2都能消费到消息吗？分别消费到多少条？
			//结果：消费者1消费到1/3/5条消息，2消费到2/4/6消息。内部有类似负载均衡的机制。

	}

}
