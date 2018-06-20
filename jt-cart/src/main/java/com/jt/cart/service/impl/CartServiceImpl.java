package com.jt.cart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.service.BaseService;
@Service
public class CartServiceImpl extends BaseService<Cart> implements CartService{
	@Autowired
	private CartMapper cartMapper;
	
	/** 根据UserId查询购物车信息 */
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		// 通过通用Mapper查询购物车信息
		return cartMapper.select(cart);
	}
	
	/** 修改商品数量 */
	@Override
	public void updateCartNum(Cart cart) {
		// 添加修改时间
		cart.setUpdated(new Date());
		cartMapper.updateNum(cart);
	}
	
	/** 删除购物车商品 */
	@Override
	public void deleteCart(Long userId, Long itemId) {
		
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		// 调用父类的方法
		super.deleteByWhere(cart);
	}
	
	/** 购物车新增商品 */
	@Override
	public void saveCart(Cart cart) {
		// 通过itemId和userId判断购物车是否已有该商品
		Cart cartTemp = new Cart();
		cartTemp.setUserId(cart.getUserId());
		cartTemp.setItemId(cart.getItemId());
		// 通过通用mapper查询
		Cart cartDB = cartMapper.findCartByUserIdAndItemId(cartTemp);
		// 判断数据是否存在
		if(cartDB != null) {
			// 证明原来有该数据
			int count = cartDB.getNum() + cart.getNum();
			cartDB.setNum(count);
			cartDB.setUpdated(new Date());
			// 数据更新 动态更新操作
			cartMapper.updateByPrimaryKeySelective(cartDB);
		} else {
			// 数据库中没有该信息， 应该新增数据
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}
	}
	
	
	
	
	
}











