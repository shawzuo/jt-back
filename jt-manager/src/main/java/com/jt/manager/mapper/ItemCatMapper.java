package com.jt.manager.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.manager.pojo.ItemCat;

public interface ItemCatMapper extends SysMapper<ItemCat>{
	// 传统需要在接口中编辑很多的接口方法方便调用
	
	/**
	 * 除非有特定业务，否则不需要写方法。
	 * 规则：使用通用Mapper通常适用于单表操作。
	 * 如果是多表操作需要自己写sql
	 */
	
	
	
}
