package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.dubbo.pojo.Cart;
import com.jt.dubbo.pojo.Order;
import com.jt.dubbo.service.CartService;
import com.jt.dubbo.service.OrderService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/create")
	public String create(Model model) {
		Long userId = UserThreadLocal.getUesr().getId();
		// 由于页面中需要进行购物车展现，所以需要查询数据
		List<Cart> cartList = cartService.findCartByUserId(userId);
		// 将数据展现到页面
		model.addAttribute("carts", cartList);
		return "order-cart";
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		Long userId = UserThreadLocal.getUesr().getId();
		order.setUserId(userId);
		try {
			String orderId = orderService.saveOrder(order);
			return SysResult.oK(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "新增订单失败");
		}
	}
	
	
	@RequestMapping("/success")
	public String findOrderById(@RequestParam("id") String orderId, Model model) {
		Order order = orderService.findOrderById(orderId);
		model.addAttribute("order", order);
		return "success";
	}
	
	
	
}















