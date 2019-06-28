package com.lc.test.mq.rabbitmq.multirouting;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.lc.test.mq.common.Constant.*;

/**
 * 多路由绑定的消费者
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

		String EXCHANGE_NAME = "exchange.direct.routing";
		// 生成一个随机的名称，queueDeclare()方法没有任何参数，当最后一个消费者断开时就会删除掉该队列，当消费者结束后可以看到队列就删除了
		String QUEUE_NAME = channel.queueDeclare().getQueue();
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
		// 在消费者端队列绑定

		// 将一个对列绑定多个路由键
		String[] routingKeys = {"debug", "info"};
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

