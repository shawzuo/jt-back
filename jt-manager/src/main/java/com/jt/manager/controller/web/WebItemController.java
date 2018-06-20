package com.jt.manager.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manager.pojo.Item;
import com.jt.manager.service.ItemService;

@Controller
@RequestMapping("/web/item")
public class WebItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/{itemId}")
	@ResponseBody
	public Item findItemById(@PathVariable Long itemId) {
		Item item = itemService.findItemById(itemId);
		return item;
	}
	
	
}
