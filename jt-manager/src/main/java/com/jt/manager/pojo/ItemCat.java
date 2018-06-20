package com.jt.manager.pojo;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 通用Mapper的配置
 * 1.匹配对应的表
 * 2.匹配Id
 * 3.如果是自增需要设定 
 */
import com.jt.common.po.BasePojo;

@Table(name="tb_item_cat")
@JsonIgnoreProperties(ignoreUnknown=true) // 忽略不匹配的属性，在爬虫时经常使用
public class ItemCat extends BasePojo{
	
	@Id // 自定义主键
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 主键自增
	private Long id;  // 商品分类id
	private Long parentId; // 商品分类父id
	private String name; // 商品分类名称
	private Integer status; // 1.正常，2.删除
	private Integer sortOrder; // 排序号
	private Boolean isParent; // 定义是否为上级
	
	@Transient
	private String Text;
	
	/** 为了满足树形结构，添加getXXX方法 */
	public String getState() {
		return isParent?"closed":"open";
	}
	
	public Long getId() {
		return id;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.setText(name);
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
}
