package com.jt.manager.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

import com.jt.common.po.BasePojo;
@Table(name="tb_item_desc")
public class ItemDesc extends BasePojo{
	@Id	//主键
	//不需要设定主键自增,通过代码自动赋值itemId
	private Long itemId;	//商品Id
	private String itemDesc;	//商品的描述详情
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	@Override
	public String toString() {
		return "ItemDesc [itemId=" + itemId + ", itemDesc=" + itemDesc + super.getCreated()+","+super.getUpdated()+"]";
	}
	
	
}
