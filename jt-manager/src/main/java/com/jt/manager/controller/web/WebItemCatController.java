package com.jt.manager.controller.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.ItemCatResult;
import com.jt.manager.service.ItemCatService;

@Controller
@RequestMapping("/web")
public class WebItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	
	//http://manage.jt.com/web/itemcat/all?callback=category.getDataService
	/*@RequestMapping("/itemcat/all")
	@ResponseBody
	public ItemCatResult findItemCatAll(){
		ItemCatResult catResult = new ItemCatResult();
		List<ItemCatData> list = new ArrayList<ItemCatData>();
		//定义一级菜单
		ItemCatData itemCatData = new ItemCatData();	
		itemCatData.setUrl("/products/74.html");
		itemCatData.setName("<a href='"+itemCatData.getUrl()+"'>家用电器</a>");
		//保存2级菜单
		List<ItemCatData> list2 = new ArrayList<ItemCatData>();
		ItemCatData itemCatData2 = new ItemCatData();
		itemCatData2.setUrl("products/89");
		itemCatData2.setName("生活电器");
		//itemCatData2.setItems(items);
		ItemCatData itemCatData2_1 = new ItemCatData();
		itemCatData2_1.setUrl("products/90");
		itemCatData2_1.setName("凹凸曼");
		list2.add(itemCatData2);
		list2.add(itemCatData2_1);
		itemCatData.setItems(list2);
		//返回  callback(ITemCatJSON数据)
		list.add(itemCatData);
		catResult.setItemCats(list);
		return catResult;
	}
	*/
	
	// http://manage.jt.com/web/itemcat/all?callback=category.getDataService
	
		// 通过response对象实现回显
		// @RequestMapping("/itemcat/all")
		public void findItemCatAll(String callback,HttpServletResponse response){
			
			//查询商品的全部的分类信息
			ItemCatResult itemCatResult = itemCatService.findItemCatAll();
			
			String JSONData = null;
			try {
				JSONData = objectMapper.writeValueAsString(itemCatResult);
				
				response.setContentType("html/text;charset=utf-8");
				
				String jsonPString = callback+"("+JSONData+")";
				response.getWriter().write(jsonPString);
					
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	
		
		@RequestMapping("/itemcat/all")
		@ResponseBody
		public Object findItemCatAll(String callback){
			ItemCatResult itemCatResult = itemCatService.findItemCatAllByCache();
			
			//用来操作JSONP对象的
			MappingJacksonValue jacksonValue = new MappingJacksonValue(itemCatResult);
			jacksonValue.setJsonpFunction(callback);
			
			return jacksonValue;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
