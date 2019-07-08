package com.lc.test.mq.activemq.simple;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 发布订阅模式下的持久性测试，这个例子中增加发布者(publisher)和订阅者(subscriber)的角色
 * 				与普通topic下的不同：
 * 				1.connection.start()需要在produccer下才启动。
 * 				2.需要为connection为发布者设置订阅者的id
 * 				3.需要使用session创建一个持久化的订阅者,并使用该订阅者接收消息。而不是使用consummer。
 * 			解释：1.一定要先运行一次消费者，等于向MQ注册，类似我订阅了这个主题
 * 					 然后再运行生产者发送消息，此时，无论消费者是否在线，都会接收到，
 * 					 不在线的话，下次连接的时候，会把没有接收到的消息都接收下来。
 *
 * @author: wlc
 * @date: 2019/6/27 0027 14:42
 **/
public class TopicPersistentConsumer {

	private static String BROKER_URL = "tcp://192.168.20.138:61616";
	private static String TOPIC_NAME = "lc_durable_jdbc_topic";
	/**订阅者zhangsan，启动两个main实例即可测试两个消费者*/
	private static String subscriber1 = "zhangsan";
	/**订阅者lisi*/
	private static String subscriber2 = "lisi";

	public static void main(String[] args){

		System.out.println("我是订阅者："+subscriber1);

		try {
			//创建连接工厂
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
			//创建连接
			Connection connection = connectionFactory.createConnection();
			//设置订阅者的id，需要在创建session之前赋值，否则会报错。
			connection.setClientID(subscriber1);
			//第1个参数为是否支持事务，第2个参数为是否使用消息确认机制
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//session创建destination，queue和topic都是destination。
			Topic topic = session.createTopic(TOPIC_NAME);
			//设置订阅者的id
			TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, "remark...");
			//启动连接
			connection.start();

			//使用订阅者接收消息
			Message message = durableSubscriber.receive();
			while (null!=message){
				TextMessage txtMsg = (TextMessage)message;
				System.out.println(subscriber1+"接收到持久化发布者推送的消息："+txtMsg.getText());
				//如果5秒钟之内，接收不到新的消息，将中断循环。
				message = durableSubscriber.receive(5000L);
			}

			//关闭资源
			durableSubscriber.close();
			session.close();
			connection.close();

		}catch (JMSException jmsE){
			jmsE.printStackTrace();
		}
	}
}
