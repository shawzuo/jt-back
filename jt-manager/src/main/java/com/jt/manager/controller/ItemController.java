package com.jt.manager.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manager.pojo.Item;
import com.jt.manager.pojo.ItemDesc;
import com.jt.manager.service.ItemService;

@RequestMapping("/item/")
@Controller
public class ItemController {
	@Autowired
	private ItemService itemSercice;
	// 定义Logger对象，方便日志输出
	private static final Logger logger = Logger.getLogger(ItemController.class);
	
	@RequestMapping("findAll")
	@ResponseBody
	public List<Item> findAll(){
		return itemSercice.findAll();
	}
	
	/**
	 * 规定：
	 * 		如果使用EasyUI进行分页操作则返回值必须满足EasyUI的格式，否则将不能实现正常的解析
	 * 结构：
	 * 		{"total":2000,"rows":[{},{},{}]}
	 * 		total 是记录总数
	 * 		rows表示查询的数据
	 * 补充：JSON一般有3种格式
	 * 		1.
	 */
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemByPage(int page, int rows) {
		return itemSercice.findItemByPage(page,rows);
	}
	
	/*@RequestMapping("/cat/queryItemCatName")   // 自定义返回的编码格式
	public void queryItemCatName(Long itemCatid, HttpServletResponse response) {
		String name = itemSercice.findItemCatNameById(itemCatid);
		System.out.println(name);
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	// 自定义返回的编码格式
	@RequestMapping(value="/cat/queryItemCatName", produces="text/html;charset=utf-8")
	@ResponseBody
	public String queryItemCatName(Long itemCatId) {
		return itemSercice.findItemCatNameById(itemCatId);
	}
	
	/** 新增商品 
	 *  思路:
	 *    1.编辑请求接收@RequestMapping
	 *    2.接收form表单数据
	 *    3.新增商品信息
	 *      3.1 调用service
	 *      3.2 调用通用Mapper实现入库操作
	 *    4.返回JSON数据并且返回状态码
	 *    
	 *  说明: 由于数据表的定义不能将
	 *  新增思路：
	 *    注意：多张表要注意事务控制。
	 */
	@RequestMapping("save")
	@ResponseBody
	public SysResult saveItem(Item item, String desc) {
		try {
			itemSercice.saveItem(item, desc);
			logger.info("~~~~~~~~~~商品新增成功~~~~~~~~~~"+item.getId());
			return SysResult.oK();
		} catch (Exception e) {
			logger.error("~~~~~~~~~~商品新增失败~~~~~~~~~~" + e.getMessage());
			return SysResult.build(201, "新增商品失败");
		}
		
	}
	
	/** 修改商品信息 
	 *  思路:
	 *    1.编辑请求接收@RequestMapping
	 *    2.接收form表单数据
	 *    3.新增商品信息
	 *      3.1 调用service
	 *      3.2 调用通用Mapper实现入库操作
	 *    4.返回JSON数据并且返回状态码
	 */
	@RequestMapping("update")
	@ResponseBody
	public SysResult updateItem(Item item, String desc) {
		try {
			itemSercice.updateItem(item,desc);
			// 添加成功日志
			logger.info("~~~~~~~~~~商品修改成功~~~~~~~~~~"+item.getId());
			return SysResult.oK();
		} catch (Exception e) {
			logger.error("~~~~~~~~~~商品修改失败~~~~~~~~~~" + e.getMessage());
			return SysResult.build(201, "修改失败!");
		}
		
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids) {
		try {
			itemSercice.deleteItem(ids);
			logger.info("~~~~~~~~~~商品删除成功~~~~~~~~~~"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			logger.error("~~~~~~~~~~商品删除失败~~~~~~~~~~" + e.getMessage());
			return SysResult.build(201, "删除失败!");
		}
	}
	
	@RequestMapping("reshelf")
	@ResponseBody
	public SysResult updateReshelf(Long[] ids){
		int status = 1;
		try {
			itemSercice.updateStatus(ids,status);
			logger.info("~~~~~~~~~修改上架信息成功~~~~~~~~~~"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			logger.error("~~~~~~~~~~商品上架失败~~~~~~~~~~" + e.getMessage());
			return SysResult.build(201, "上架失败!");
		}
	}
	
	@RequestMapping("instock")
	@ResponseBody
	public SysResult updateInstock(Long[] ids) {
		int status = 2;
		try {
			itemSercice.updateStatus(ids,status);
			logger.info("~~~~~~~~~修改上架信息成功~~~~~~~~~~"+Arrays.toString(ids));
			return SysResult.oK();
		} catch (Exception e) {
			logger.error("~~~~~~~~~~商品上架失败~~~~~~~~~~" + e.getMessage());
			return SysResult.build(201, "上架失败!");
		}
	}

	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDesc(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = itemSercice.findItemDesc(itemId);
			logger.info("~~~~~~~~~数据查询成功~~~~~~~~~~"+itemDesc.getItemId());
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			logger.error("~~~~~~~~~~商品详情查找失败~~~~~~~~~~" + e.getMessage());
			return SysResult.build(201, "商品详情加载失败!");
		}
	}
	
	
	
	/*@RequestMapping("param/query/itemcatid/{param}")
	@ResponseBody
	public SysResult xxx(){
		return SysResult.build(200, "sss");
	}*/
}

















