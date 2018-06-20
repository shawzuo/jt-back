package com.jt.manager.service;

import java.util.List;

import com.jt.manager.pojo.User;

public interface UserService {
	// 定义serviece 接口文件
	List<User> findAll();
	
	/** 实现分页查询 */
	//List<>
}
