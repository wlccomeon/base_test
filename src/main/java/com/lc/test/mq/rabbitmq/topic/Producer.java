package com.lc.test.mq.rabbitmq.topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.lc.test.mq.common.Constant.*;

public class Producer {
	@Test
	public void testBasicPublish() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(IP);
		factory.setPort(AMQP.PROTOCOL.PORT);
		factory.setUsername(USERNAME);
		factory.setPassword(PASSOWRD);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// Routing 的路由规则使用直连接
		String EXCHANGE_NAME = "exchange.topic.x";
		String[] routingKeys = {"quick.orange.rabbit", "lazy.orange.elephant", "mq.erlang.rabbit", "lazy.brown.fox", "lazy."};
		for (String routingKey : routingKeys){
			String message = "Hello RabbitMQ - " + routingKey;

			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
		}

		// 关闭资源
		channel.close();
		connection.close();
	}
}
