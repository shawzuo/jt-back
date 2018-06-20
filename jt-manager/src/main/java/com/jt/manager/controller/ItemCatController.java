package com.jt.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.SysResult;
import com.jt.manager.pojo.ItemCat;
import com.jt.manager.service.ItemCatService;

@RequestMapping("/item/cat/")
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	/*@RequestMapping("findItemCat")
	@ResponseBody
	public List<ItemCat> findItemCat() {
		return itemCatService.findItemCat();
	}*/
	
	@RequestMapping("list")
	@ResponseBody
	public List<ItemCat> findItemCat(@RequestParam(value="id", defaultValue="0") Long parentId) {
		//Long id =0L; // 定义一级菜单
		
		List<ItemCat> itemCatList = itemCatService.findItemCatByParentId(parentId);
		// 通过工具类展现JSON  JSON工具类
		ObjectMapper objectMapper = new ObjectMapper();
		/*try {
			String jsonDate = objectMapper.writeValueAsString(itemCatList);
			System.out.println(jsonDate);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		*/
		return itemCatList;
	}
	
	
	
	
}

























