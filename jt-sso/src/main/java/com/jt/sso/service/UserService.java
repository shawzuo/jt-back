package com.jt.sso.service;

import com.jt.sso.pojo.User;

public interface UserService {
	// 查询是否存在
	Boolean findCheckUser(String param, Integer type);
	
	String saveUser(User user);
	
	String findUserByUP(String username, String password);
}
