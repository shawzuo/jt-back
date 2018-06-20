package com.jt.cart.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.dubbo.pojo.Cart;

public interface CartMapper extends SysMapper<Cart>{
	/** 修改购物车商品数量 */
	void updateCartNum(Cart cart);
	/** 根据UserID和itemId查询购物车信息 */
	Cart findCartByUserIdAndItemId(Cart cart);
}
