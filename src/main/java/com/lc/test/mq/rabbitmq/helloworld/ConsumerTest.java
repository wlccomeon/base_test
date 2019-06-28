package com.lc.test.mq.rabbitmq.helloworld;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.lc.test.mq.common.Constant.*;

/**
 * 类名不能直接用Consumer、、跟里面用的类会有冲突
 * @author wlc
 */
public class ConsumerTest {
	@Test
	public void testBasicConsumer() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP);
		factory.setPort(AMQP.PROTOCOL.PORT);
		factory.setUsername(USERNAME);
		factory.setPassword(PASSOWRD);

		// 新建一个长连接
		Connection connection = factory.newConnection();

		// 创建一个通道(一个轻量级的连接)
		Channel channel = connection.createChannel();

		// 声明一个队列
		String QUEUE_NAME = "hello";
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("Consumer Wating Receive Message");

		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [C] Received '" + message + "'");
			}
		};

		// 订阅消息
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}
