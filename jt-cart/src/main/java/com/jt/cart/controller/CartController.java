package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 任务：根据用户id查询购物车信息
	 * url:http://cart.jt.com/cart/query/{userId}
	 * 返回SysResult对象，并且data数据中保存itemList的json数据
	 */
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartByUserId(@PathVariable Long userId) {
		try {
			// 获取cartList数据
			List<Cart> cartList = cartService.findCartByUserId(userId);
			String cartListJSON = objectMapper.writeValueAsString(cartList);
			return SysResult.oK(cartListJSON);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "查询失败");
		}
	}
	
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Integer num) {
		try {
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "修改商品数量失败");
		}
	}
	
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable Long userId, @PathVariable Long itemId) {
		
		
		try {
			cartService.deleteCart(userId, itemId);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "删除商品失败");
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(Cart cart) {
		
		try {
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201, "新增失败");
		}
	}
	
	
	
}












