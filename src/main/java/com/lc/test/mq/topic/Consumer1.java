package com.lc.test.mq.topic;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.lc.test.mq.common.Constant.*;

/**
 * topic模式消费者1
 * @author wlc
 */
public class Consumer1 {
	@Test
	public void testBasicConsumer1() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP);
		factory.setPort(AMQP.PROTOCOL.PORT);
		factory.setUsername(USERNAME);
		factory.setPassword(PASSOWRD);

		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		String EXCHANGE_NAME = "exchange.topic.x";
		String QUEUE_NAME = "queue.topic.q1";
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
		
		String[] routingKeys = {"*.orange.*"};
		for (int i = 0; i < routingKeys.length; i++) {
			channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKeys[i]);
		}

		System.out.println("Consumer Wating Receive Message");
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [C] Received '" + message + "', 处理业务中...");
			}
		};

		channel.basicConsume(QUEUE_NAME, true, consumer);

		Thread.sleep(1000000);
	}

}

