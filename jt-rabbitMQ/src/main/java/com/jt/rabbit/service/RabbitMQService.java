package com.jt.rabbit.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.pojo.OrderItem;
import com.jt.dubbo.pojo.OrderShipping;
import com.jt.rabbit.mapper.OrderItemMapper;
import com.jt.rabbit.mapper.OrderMapper;
import com.jt.rabbit.mapper.OrderShippingMapper;

public class RabbitMQService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;


	/**
	 * 
	 */
	/** 新增订单 */
	public void saveOrder(Order order) {
		String orderId = order.getOrderId();

		// 封装order对象
		//order.setOrderId(orderId);
		order.setStatus(1); // 状态为1 表示未支付
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
		System.out.println("***********消息队列执行成功************");
	}

	public Order findOrderById(String orderId) {
		// 通过sql实现多表查询
		return orderMapper.findOrderById(orderId);
	}

}
