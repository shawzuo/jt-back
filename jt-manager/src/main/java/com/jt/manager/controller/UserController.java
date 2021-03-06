package com.jt.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.manager.pojo.User;
import com.jt.manager.service.UserService;

@RequestMapping("/")
@Controller   // webApplicationContex,SpringMVC特有的容器
public class UserController {
	@Autowired
	private UserService userService;
	
	/*
	 * 定义浏览器的访问路径接收
	 * 1.通过前端控制器将用户的url转发到具体Controller
	 * 2.通过转发查找最匹配的RequestMapping
	 * 3.进行业务操作controller-service-mybatis依次调用
	 * 4.准备返回数据(JSON/页面名称)
	 */
	
	@RequestMapping("findAll")
	public String doFindAll(Model model){
		List<User> userList = userService.findAll();
		// 通过model对象将数据存入request域中
		model.addAttribute("userList",userList);
		return "userList";
		/*
		 * SpringMVC将返回的字符串经过视图解析器进行数据拼接，prefix + userList +suffix
		 */
	}
}












