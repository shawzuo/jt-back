package com.jt.manager.service;

import java.util.List;

import com.jt.common.vo.ItemCatResult;
import com.jt.manager.pojo.ItemCat;

public interface ItemCatService {
	// 定义业务接口方法
	List<ItemCat> findItemCat();
	
	// 
	List<ItemCat> findItemCatByParentId(Long parentId);
	
	ItemCatResult findItemCatAll();
	
	ItemCatResult findItemCatAllByCache();
}
