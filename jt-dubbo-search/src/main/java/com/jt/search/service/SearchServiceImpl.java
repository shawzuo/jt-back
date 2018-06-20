package com.jt.search.service;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import com.jt.dubbo.pojo.Item;
import com.jt.dubbo.service.SearchService;

public class SearchServiceImpl implements SearchService {
	// 需要引入solr配置文件
	@Autowired
	private HttpSolrServer httpSolrServer;
	
	
	@Override
	public List<Item> findItemByKey(String keyword) {
		// 定义查询
		SolrQuery query = new SolrQuery();
		// 设定查询的关键字
		query.setQuery(keyword);
		// 设置分页的参数
		query.setStart(0);
		query.setRows(20);
		try {
			// solr实现条件查询
			QueryResponse queryResponse = httpSolrServer.query(query);
			// 获取查询条件
			List<Item> itemList = queryResponse.getBeans(Item.class);
			return itemList;
		} catch (Exception e) {
			System.out.println("----------"+e.getMessage());
			e.printStackTrace();
			
		}
		return null;
	}

}
