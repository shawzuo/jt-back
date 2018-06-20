package com.tedu.jsoup;

import java.io.IOException;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJSOUP {
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void test01() {
		String url = "http://www.it211.com.cn/web/index_new.html?tedu";
		// 通过jsoup进行数据爬取
		try {
			Document document = Jsoup.connect(url).get();
			// 通过jsoup提供的选择器快速定位目标
			Element element = document.select(".b_search h2").get(0);
			// 获取h2具体的文本内容
			String msg = element.text();
			System.out.println("爬取标题数据：" + msg);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test02() {
		String url = "http://www.it211.com.cn/web/index_new.html?tedu";
		// 通过jsoup进行数据爬取
		try {
			Document document = Jsoup.connect(url).get();
			// 通过jsoup提供的选择器快速定位目标
			Element element = document.select("#user_num").get(0);
			// 获取h2具体的文本内容
			String msg = element.text();
			System.out.println("爬取标题数据：" + msg);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void test03() throws IOException {
		String url = "http://www.it211.com.cn/commonData/getCommonNum";
		Response response = Jsoup.connect(url).ignoreContentType(true).execute();
		// 将获取到的结果转化为字符串
		String resultJson = response.body();
		// {"result":"1","obj":{"userNum":397607,"dirNum":17,"bookNum":999}}
		// 获取学生人数
		JsonNode jsonNode = objectMapper.readTree(resultJson);
		String msg = jsonNode.get("obj").get("userNum").asText();
		System.out.println("获取响应数据：" + resultJson);
		System.out.println("获取人数:" + msg);
		
		
	}
	
	@Test
	public void test04() throws IOException {
		String url = "http://www.it211.com.cn/book_test/getHotBook";
		String resultJSON = Jsoup.connect(url).ignoreContentType(true).execute().body();
		System.out.println("获取数据:" + resultJSON);
		String listJSON = objectMapper.readTree(resultJSON).get("list").asText();
		
		
		
	}
}









