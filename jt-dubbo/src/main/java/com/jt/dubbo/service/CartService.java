package com.jt.dubbo.service;

import java.util.List;

import com.jt.dubbo.pojo.Cart;

public interface CartService {
	// 根据userId查询购物车信息
	List<Cart> findCartByUserId(Long userId);
	
	void updateCartNum(Long userId, Long itemId, Integer num);

	void deleteCart(Long userId, Long itemId);
	/** 将商品加入购物车 */
	void saveCart(Cart cart);
}
