package com.jt.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manager.mapper.ItemCatMapper;
import com.jt.manager.pojo.ItemCat;
import com.jt.manager.service.ItemCatService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	// 定义格式转换工具
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private ItemCatMapper itemCatMapper;
	// 上午通过单节点实现缓存操作，但是现在不适用
	// @Autowired
	// private Jedis jedis;
	//@Autowired // 通过Spring方式实现依赖注入
	//private RedisService redisService;   //  分片--->>哨兵
	
	@Autowired
	private JedisCluster jedisCluster;
	
	// 通过spring动态注入的形式引入key  ,注意：属性不能static否则spring不能注入
	@Value("${ITEM_CAT_KEY}") // 在message.property中的配置给Spring管理
	private String ITEM_CAT_KEY;
	
	@Override
	public List<ItemCat> findItemCat() {
		// 直接调用Mapper的方法
		// 如果查询全部数据则不需要设定参数
		return itemCatMapper.select(null);
	}

	@Override
	public List<ItemCat> findItemCatByParentId(Long parentId) {
		// 1.定义查询的key值
		String key = "ITEM_CAT_"+parentId;
		
		// 2.根据key查询缓存数据
		String dataJSON = jedisCluster.get(key);
		// 3.定义公用的List集合
		List<ItemCat> itemCatList = new ArrayList<>();
		// 4.判断返回值是否含有数据
		try {
			if(StringUtils.isEmpty(dataJSON)) {
				//证明缓存中没有数据，则通过数据库查询数据
				ItemCat itemCat = new ItemCat();
				itemCat.setParentId(parentId);
				itemCat.setStatus(1);
				itemCatList = itemCatMapper.select(itemCat);
				// 将返回数据转换为JSON
				String jsonResult = objectMapper.writeValueAsString(itemCatList);
				jedisCluster.set(key, jsonResult);	
			} else {
				// 表示数据不为空，需要将JSON串转化为List<ItemCat>集合对象
				ItemCat[] itemCats = objectMapper.readValue(dataJSON, ItemCat[].class);
				for(ItemCat itemCat : itemCats){
					itemCatList.add(itemCat);
				}
				System.out.println("save");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatList;	
		
		/** 通用mapper中带参数的查询，如果需要带参数查询，只需要将参数set到具体对象中国即可 
		 *  实现思路：
		 *  	 通用Mapper会将对象中不为null的数据当做where条件查询(所以构建类的时候基本类型要用包装类，默认初始化为null)
		 *  	lg:select id,parent_id... from tb_item_cat where parent_id = 0 and status = 1
		 */
		/*
		System.out.println(jedis.get("test01"));
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId); // 设定父级id
		itemCat.setStatus(1);  // 设定正常的数据 1
		return itemCatMapper.select(itemCat);
		*/
	}
	
	/**
	 * 1.首先查询全部商品分类信息
	 * 2.通过Map将数据进行分类保存
	 * 3.准备返回值数据ItemCatResult对象
	 * 4.准备一级菜单列表集合  data
	 * 5.dataList赋值添加一级菜单
	 * 6.准备二级菜单  List
	 * 7.将二级菜单追加到一级菜单中
	 * 8.准备三级菜单 list
	 * 9.将三级菜单追加大二级菜单中
	 */
	@Override
	public ItemCatResult findItemCatAll() {
		//查询全部可用的商品分类信息
		ItemCat itemCatTemp = new ItemCat();
		itemCatTemp.setStatus(1); //表示可用
		List<ItemCat> itemCatList = itemCatMapper.select(itemCatTemp);
		
		//准备Map数据结构将商品信息进行分类 Long代表parentId
		Map<Long,List<ItemCat>> map = new HashMap<Long,List<ItemCat>>();
		
		for (ItemCat itemCat : itemCatList) {
			
			if(map.containsKey(itemCat.getParentId())){
				//如果存入parentId证明之前有数据,只需要做数据的追加即可
				map.get(itemCat.getParentId()).add(itemCat);
			}else{
				//表示第一次插入数据
				List<ItemCat> tempList = new ArrayList<ItemCat>();
				tempList.add(itemCat);
				map.put(itemCat.getParentId(), tempList);
				
			}
		}
		//通过上述的转化已经将数据完成了清洗
		
		//定义一级菜单信息
		List<ItemCatData> itemCatDataList1 = new ArrayList<ItemCatData>();
		
		//开始查询全部的一级菜单
		for (ItemCat itemCat1 : map.get(0L)) {
			//定义一级菜单对象
			ItemCatData itemCatData1 = new ItemCatData();
			itemCatData1.setUrl("/products/"+itemCat1.getId()+".html");
			itemCatData1.setName("<a href='"+itemCatData1.getUrl()+"'>"+itemCat1.getName()+"</a>");
			
			//准备二级菜单数据
			List<ItemCatData> itemCatDataList2 = new ArrayList<ItemCatData>();
			
			//循环遍历全部的二级菜单
			for (ItemCat itemCat2 : map.get(itemCat1.getId())) {
				ItemCatData itemCatData2 = new ItemCatData();
				itemCatData2.setUrl("/products/"+itemCat2.getId());
				itemCatData2.setName(itemCat2.getName());
				
				//定义三级商品分类列表
				List<String> itemCatDataList3 = new ArrayList<String>();
				for (ItemCat itemCat3 : map.get(itemCat2.getId())) {
					itemCatDataList3.add("/products/"+itemCat3.getId()+"|"+itemCat3.getName());
					
				}
				
				itemCatData2.setItems(itemCatDataList3);
				itemCatDataList2.add(itemCatData2);
			}
			//新增二级菜单
			itemCatData1.setItems(itemCatDataList2);
			
			//将一级菜单信息保存到一级菜单列表中
			itemCatDataList1.add(itemCatData1);
			
			//控制菜单的个数 数量达标后跳出循环
			if(itemCatDataList1.size() >13)
			break;	
		}
		
		ItemCatResult catResult = new ItemCatResult();
		//保存一级商品菜单信息
		catResult.setItemCats(itemCatDataList1);
		return catResult;
	}	
	
	/**
	 * 编程思想：
	 *  1.当原有的业务逻辑已经很复杂时不建议进行二次开发
	 *  2.当实现自己业务逻辑方法时不要修改别人的方法
	 *  3.如果业务逻辑相对复杂，尽可能的将方法拆分
	 *  需求：引入缓存技术
	 *  说明: 如果key的关键字是自己单独使用，可以直接写死，如果key的值需要多人一起使用，
	 *        则最好使用依赖注入的形式进行赋值。
	 */
	public ItemCatResult findItemCatAllByCache() {
		ItemCatResult itemCatResult = null;
		// 从缓存中获取数据
		String jsonData = jedisCluster.get(ITEM_CAT_KEY);
		try {
			if(StringUtils.isEmpty(jsonData)) {
				itemCatResult = findItemCatAll();
				// 将对象转换为JSON数据
				String jsonItemCat = objectMapper.writeValueAsString(itemCatResult);
				// 将数据存入redis中
				jedisCluster.set(ITEM_CAT_KEY, jsonItemCat);
			} else { // 表示json中有数据，则直接转化为对象
				itemCatResult = objectMapper.readValue(jsonData, ItemCatResult.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCatResult;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
