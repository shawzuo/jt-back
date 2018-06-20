package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.util.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	//转向到购物车页面   /cart/show.html
	@RequestMapping("/show")
	public String findCartByUserId(Model model){
		
		//Long userId = 7L;	//暂时写死UserId后期维护
		Long userId = UserThreadLocal.getUesr().getId();
		
		//查询购物车列表信息
		List<Cart> cartList = 
				cartService.findCartByUserId(userId);
		
		//将数据写入request域中
		model.addAttribute("cartList", cartList);
		//转向cart.jsp页面
		return "cart";
	}
	
	//修改购物车商品数量
	//url http://www.jt.com/service/cart/update/num/1474391958/101
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public String updateCartNum(@PathVariable Long itemId,
			@PathVariable Integer num){
		//Long userId = 7L;	//获取userId
		Long userId = UserThreadLocal.getUesr().getId();
		Cart cart = new Cart();//为属性赋值
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cart.setNum(num);
		cartService.updateCartNum(cart);
		
		return "";
	}
	
	
	//删除购物车   /cart/delete/7.html
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId){
		
		//Long userId = 7L;
		Long userId = UserThreadLocal.getUesr().getId();
		cartService.deleteCart(userId,itemId);
		//跳转到购物车列表页面  满足springMVC的要求 *.html
		return "redirect:/cart/show.html";
	}
	
	
	//购物车新增  http://www.jt.com/cart/add/1474391962.html
	//添加Cart对象 获取页面中提交的参数
	@RequestMapping("/add/{itemId}")
	//@ResponseBody //如果添加该注解,将不走视图解析器,转向页面不生效
	public String saveCart(@PathVariable Long itemId,Cart cart){
		
		//Long userId = 7L;
		Long userId = UserThreadLocal.getUesr().getId();
		//将数据进行封装
		cart.setUserId(userId);
		cart.setItemId(itemId);
		
		cartService.saveCart(cart);
		//转向购物车展现页面
		return "redirect:/cart/show.html";	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
