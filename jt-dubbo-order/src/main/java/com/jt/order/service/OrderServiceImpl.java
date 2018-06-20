package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import com.jt.dubbo.service.OrderService;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * 
	 */
	/** 新增订单 */
	@Override
	public String saveOrder(Order order) {
		// 准备orderId
		String orderId = "" + order.getUserId() + System.currentTimeMillis();
		// 位rabbitMQ封装数据
		order.setOrderId(orderId);
		// 将order信息发往消息队列rabbitMQ中,定义路由key
		rabbitTemplate.convertAndSend("save.Order", order);
		
		
		
		
		// 未使用rabbitMQ，直接连接数据库
		/*
		// 封装order对象
		order.setOrderId(orderId);
		order.setStatus(1); // 状态为1  表示未支付
		order.setCreated(new Date());
		order.setUpdated(order.getCreated());
		orderMapper.insert(order);
		// 封装orderItem对象
		List<OrderItem> orderItemList = order.getOrderItems();
		for (OrderItem orderItem : orderItemList) {
			orderItem.setOrderId(orderId);
			orderItemMapper.insert(orderItem);
		}
		// 封装orderShipping
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(orderShipping.getCreated());
		orderShippingMapper.insert(orderShipping);
		*/
		return orderId;
	}

	@Override
	public Order findOrderById(String orderId) {
		
		// 通过sql实现多表查询
		return orderMapper.findOrderById(orderId);
	}

}
