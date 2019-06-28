package com.lc.test.mq.rabbitmq.fanout;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.lc.test.mq.common.Constant.*;

/**
 * fanout模式下的消息发送
 * 因为交换机是在消费者中声明的，这里没有。所以需要先启动消费者。
 * @author wlc
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

		String EXCHANGE_NAME = "exchange.fanout";
		// 生产者不需要队列声明，也不需要声明交换机，只需要指定交换机的名称即可，队列和交换机的声明可以在消费者中声明
		// 循环发布多条消息, 注意广播模式不需要routingKey, 可以写成""， 也可以随意写个名字，在消费者也随便写一个，生产者和消费者的routingKey的不一样看看可以不
		for (int i = 0; i < 10; i++){
			String message = "Hello RabbitMQ " + i;
			// 广播类型不需要routingKey，但是不能写成null，可以写成空字符串""
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
		}

		// 关闭资源
		channel.close();
		connection.close();
	}
}
