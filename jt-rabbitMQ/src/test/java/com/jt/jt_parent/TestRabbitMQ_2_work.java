package com.jt.jt_parent;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class TestRabbitMQ_2_work {
	
private Connection connection = null;
	
	//定义rabbit连接池
	//@Before
	public void initConnection() throws IOException{
		//定义工厂对象
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//设定参数
		connectionFactory.setHost("192.168.195.138");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/jt");
		connectionFactory.setUsername("jtadmin");
		connectionFactory.setPassword("jtadmin");
		
		//创建连接
		connection = connectionFactory.newConnection();	
	}
	
	
	private String queue_name = "rabbit_work";
	//定义生产者
	//@Test
	public void proverder() throws Exception{
		//定义通道
		Channel channel = connection.createChannel();
		
		//声明队列名称
		channel.queueDeclare(queue_name, false, false, false, null);
		
		String msg = "工作模式";
		channel.basicPublish("", queue_name, null, msg.getBytes());
		
		channel.close();
		connection.close();
	}
	
	//@Test
	public void consumer1() throws Exception{
		Channel channel = connection.createChannel();
		
		//获取队列
		channel.queueDeclare(queue_name, false, false, false, null);
		
		//定义消费个数  表示队列每次同一时间只能获取一条数据  消费者允许有三次没有响应给rabbitMQq,
		//如果长时间没有响应则不允许再次消费消息
		channel.basicQos(1);
		//定义消费者对象
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//定义监听回复模式  表示手动回复
		channel.basicConsume(queue_name, false, consumer);
		
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = "消费者1成功获取队列信息:"+ new String(delivery.getBody());
			
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			System.out.println(msg);
		}
	}
	
	//@Test
	public void consumer2() throws Exception{
		Channel channel = connection.createChannel();
		
		//获取队列
		channel.queueDeclare(queue_name, false, false, false, null);
		
		//定义消费个数  表示队列每次同一时间只能获取一条数据 
		channel.basicQos(1);
		//定义消费者对象
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//定义监听回复模式  表示手动回复
		channel.basicConsume(queue_name, false, consumer);
		
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = "消费者2成功获取队列信息:"+ new String(delivery.getBody());	
			//表示手动传回
			channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			System.out.println(msg);
		}
	}
	
	
	
	
}
