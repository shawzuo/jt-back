package com.jt.sso.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public Boolean findCheckUser(String param, Integer type) {
		String cloumn = null;
		switch (type) {
		case 1: cloumn = "username"; break;
		case 2: cloumn = "phone"; break;
		case 3: cloumn = "email"; break;
		}
		int count = userMapper.findCheckUser(param, cloumn);
		return count == 1 ? true : false;
	}

	@Override
	public String saveUser(User user) {
		// 将数据准备完整
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		
		// 将密码进行加密处理
		String md5Password = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5Password);
		
		// 修改bug 填补邮箱为null现象
		user.setEmail(user.getPhone());
		
		// 执行insert操作
		userMapper.insert(user);
		return user.getUsername();
	}
	
	/**
	 * 1.验证用户名和密码是否正确
	 * 2.需要将用户的明文加密为密文
	 * 3.如果用户信息校验通过，准备redis缓存数据
	 * 4.生成ticket = md5("JT_TICKET_" + System.currentTime + username)
	 * 5.将User对象转化为JSON数据
	 * 6.将ticket和UserJSON数据存入redis
	 * 7.return返回ticket
	 */
	@Override
	public String findUserByUP(String username, String password) {
		// 根据用户名和加密后的密码查询用户信息
		String md5Password = DigestUtils.md5Hex(password);
		User user = userMapper.selectUserByUP(username,md5Password);
		// 判断user是否为null
		if (user != null) {
			// 准备ticket信息
			String ticket = "JT_TICKET_" + System.currentTimeMillis() +user.getUsername();
			String md5Ticket = DigestUtils.md5Hex(ticket);
			
			try {
				String userJson = objectMapper.writeValueAsString(user);
				// 将数据存入redis中
				jedisCluster.set(md5Ticket, userJson);
				return md5Ticket;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}















