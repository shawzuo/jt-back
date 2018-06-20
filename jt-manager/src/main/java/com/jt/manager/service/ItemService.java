package com.jt.manager.service;

import java.util.List;

import com.jt.common.vo.EasyUIResult;
import com.jt.manager.pojo.Item;
import com.jt.manager.pojo.ItemDesc;

public interface ItemService {
	
	List<Item> findAll();
	
	/** 分页查询 */
	EasyUIResult findItemByPage(int page, int rows);

	String findItemCatNameById(Long itemCatid);
	/** 保存商品信息 */
	void saveItem(Item item, String desc);
	/** 修改商品信息 */
	void updateItem(Item item, String desc);
	/** 修改商品信息 */
	void deleteItem(Long[] ids);
	/** 修改商品上架信息 */
	void updateStatus(Long[] ids, int status);
	/** 查询商品详情信息 */
	ItemDesc findItemDesc(Long itemId);
	
	Item findItemById(Long itemId);
}
