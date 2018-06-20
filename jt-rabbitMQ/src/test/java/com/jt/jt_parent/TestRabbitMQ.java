package com.jt.jt_parent;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.Channel;

public class TestRabbitMQ {
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


	private String queue_name = "simple-1";
	//生产者的任务: 定义队列名称 发送消息
	//@Test
	public void proverder() throws IOException{
		//定义通道  作用声明队列 交换机  和发送消息
		Channel channel = connection.createChannel();

		/**
		 * 创建队列  
		 * queue :消息队列名称
		 * durable : 数据是否需要持久化
		 * exclusive: 服务器独有 如果设置为true则消费者 不能使用
		 * autoDelete: 表示如果队列中没有消费者 是否删除队列
		 * arguments: 表示一些额外的参数  一般都为null
		 */
		channel.queueDeclare(queue_name, false, false, false, null);

		//定义需要发送的数据
		String msg = "Hello RabbitMQ";

		/**
		 * exchange 表示交换机的名称  路由模式使用   
		 * routingKey 一般为队列名称 路由key  发送消息的key  路由模式中使用
		 * props   扩展参数 一般为null 
		 */
		for(int i=0; i<1000000; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			channel.basicPublish("", queue_name, null, msg.getBytes());
			System.out.println("消息发送成功!!!");
		}
		

		System.out.println("消息发送成功!!!");
		channel.close();
		connection.close();
	}
	
	
	//@Test
	public void consumers() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		//获取通道
		Channel channel = connection.createChannel();
		
		//获取队列
		channel.queueDeclare(queue_name, false, false, false, null);
		
		//定义消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		
		//声明消费者队列信息
		/**
		 * 1.queue 队列名称
		 * 2.autoACK  自动回复表示 true 表示自动回复 表示成功接收数据 false表示 手动回复
		 * 3.callback 消费者对象  获取信息后返回给消费者
		 */
		channel.basicConsume(queue_name, true, consumer);
		
		//从队列中获取数据
		while(true){
			//将信息交付给客户端
			Thread.sleep(5000);
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.out.println("从队列中获取数据:"+msg);
			
		}
		// 习惯：关闭流 
		//channel.close();
		//connection.close();
		
	}

}
