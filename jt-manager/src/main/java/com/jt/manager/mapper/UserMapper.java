package com.jt.manager.mapper;

import java.util.List;

import com.jt.manager.pojo.User;

public interface UserMapper {
	// 查询用户全部信息
	List<User> findAll();
}
