package com.jt.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.EasyUIResult;
import com.jt.manager.mapper.ItemDescMapper;
import com.jt.manager.mapper.ItemMapper;
import com.jt.manager.pojo.Item;
import com.jt.manager.pojo.ItemDesc;
import com.jt.manager.service.ItemService;

import redis.clients.jedis.JedisCluster;
import sun.org.mozilla.javascript.internal.annotations.JSConstructor;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	@Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public List<Item> findAll() {
		return itemMapper.findAll();
	}
	
	public EasyUIResult findItemByPage(int page, int rows) {
		int total = itemMapper.findItemCount();
		List<Item> itemList = itemMapper.findItemByPage((page-1)*rows,rows);
		return new EasyUIResult(total,itemList);
	}

	@Override
	public String findItemCatNameById(Long itemCatid) {
		return itemMapper.findItemCatNameById(itemCatid);
	}
	
	/**
	 * 1. item表的主键是自增的，所有只有当item保存到数据库中时才有id
	 * 2. 如果获取不到item的Id只能通过传入的数据进行where条件的拼接,
	 *     但是这样的做法 不能一定满足需求  可能出现重复数据 
	 * 
	 * 注意: 由于配置了事务切面  通知+切入=切面，该方法的两个表都插入成功后才执行事务
	 */
	@Override
	public void saveItem(Item item, String desc) {
		// 商品新增
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		// 商品描述表新增
		ItemDesc itemDesc = new ItemDesc();
		//说明:由于通用Mapper会在插入数据和会进行Id的最大值查询,
		//将结果自动映射到对象中,所有item.getId能够获取数据
		//Executing: SELECT LAST_INSERT_ID() 
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
		
		// 将商品加入redis缓存	
		try {
			String jsonData = objectMapper.writeValueAsString(item);
			String key = "ITEM_" + item.getId();
			jedisCluster.set(key, jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 修改商品信息 */
	@Override
	public void updateItem(Item item, String desc) {
		// 设置修改时间
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(item.getUpdated());
		itemDesc.setItemDesc(desc);
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		
		// 修改操作时应该将缓存删除
		jedisCluster.del("ITEM_" + item.getId());
	}

	@Override
	public void deleteItem(Long[] ids) {
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIDS(ids);	
		
		// 删除操作时同时删除缓存
		for(Long id : ids) {
			jedisCluster.del("ITEM_" + id);
		}
	}

	@Override
	public void updateStatus(Long[] ids, int status) {
		itemMapper.updateStatus(ids,status);
	}

	@Override
	public ItemDesc findItemDesc(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public Item findItemById(Long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}
	
	
}













