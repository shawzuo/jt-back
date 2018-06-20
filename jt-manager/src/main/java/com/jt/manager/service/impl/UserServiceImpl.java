package com.jt.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.vo.EasyUIResult;
import com.jt.manager.mapper.UserMapper;
import com.jt.manager.pojo.Item;
import com.jt.manager.pojo.User;
import com.jt.manager.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired // byName 通过bean的id注入，byType通过Class类型注入
	// @Qualifier(value="...")
	// Mapper接口不能实例化，其实spring通过Mybatis为其创建代理对象进行注入
	private UserMapper userMapper; 
	
	public List<User> findAll() {	
		return userMapper.findAll();
	}
	
	
}
