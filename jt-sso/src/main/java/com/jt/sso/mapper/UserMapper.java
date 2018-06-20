package com.jt.sso.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.sso.pojo.User;

public interface UserMapper extends SysMapper<User>{
	int findCheckUser(@Param("param") String param,@Param("cloumn") String cloumn);
	
	User selectUserByUP(@Param("username") String username, @Param("password") String password);
	
}
