package com.lc.test.mq.fanout;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.lc.test.mq.common.Constant.*;

/**
 * fanout模式下的消费者1
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
		channel.basicQos(1);

		String QUEUE_NAME = "queue.fanout.q1";
		String EXCHANGE_NAME = "exchange.fanout";
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		// 声明交换机：指定交换机的名称和类型(广播：fanout)
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		// 在消费者端队列绑定
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
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
