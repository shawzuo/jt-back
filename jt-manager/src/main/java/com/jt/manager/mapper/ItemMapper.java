package com.jt.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.manager.pojo.Item;

public interface ItemMapper extends SysMapper<Item>{
	
	List<Item> findAll();
	
	int findItemCount();
	
	//分页查询数据 begin代表起始位置 rows加载数据量
	//Mybatis中只允许传递单个数据.如果需要传递多个数据时
	//需要进行封装一般采用Map结构  添加@Param("begin")
	List<Item> findItemByPage
	(@Param("begin")int begin,@Param("rows")int rows);
	
	//查询商品分类名称
	String findItemCatNameById(Long itemCatId);
	
	//批量修改状态
	void updateStatus(@Param("ids")Long[] ids,@Param("status")int status);
	
	
	
	
	
	
	
	
	
	
}
