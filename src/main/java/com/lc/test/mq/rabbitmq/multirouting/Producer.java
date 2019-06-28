package com.lc.test.mq.rabbitmq.multirouting;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.lc.test.mq.common.Constant.*;

/**
 * 多路由绑定测试
 * @author wlc
 * 注意：绑定交换机、队列的操作在消费者上。需要首先启动消费者
 */
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
		String EXCHANGE_NAME = "exchange.direct.routing";
		String[] routingKeys = {"debug", "info", "warning", "error"};
		for (int i = 0; i < 20; i++){
			int random = (int)(Math.random() * 4);
			String routingKey = routingKeys[random];
			String message = "Hello RabbitMQ - " + routingKey + " - " + i;

			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
		}

		// 关闭资源
		channel.close();
		connection.close();
	}
}
