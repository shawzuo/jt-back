package com.jt.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class TestJedis {
	// 测试redis操作
	//@Test
	public void test01() {
		/**
		 * host: 表示redis主机ip
		 * port: 表示redis的端口
		 */
		Jedis jedis = new Jedis("192.168.195.134",6379);
		jedis.set("test01", "tomcat毛"); // 成功后返回值为"OK"
		System.out.println(jedis.get("test01"));
	}

	// 测试分片技术
	//@Test
	public void test02() {
		/**
		 * poolConfig: 设置redis服务的配置工具类(例如：设定最大连接数，最小空间数)
		 * shard: 表示包含redis的节点的配置项List集合
		 */
		// 定义redis的配置
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(1000); // 表示最大的连接数
		poolConfig.setMinIdle(5); // 表示最小的连接数，可以不设置，能默认
		// 定义redis多个节点信息
		List<JedisShardInfo> list = new ArrayList<>();
		list.add(new JedisShardInfo("192.168.195.134",6379));
		list.add(new JedisShardInfo("192.168.195.134",6380));
		list.add(new JedisShardInfo("192.168.195.134",6381));		
		ShardedJedisPool jedisPool = new ShardedJedisPool(poolConfig, list);
		
		// 获取连接操作redis
		ShardedJedis shardedJedis = jedisPool.getResource();
		shardedJedis.set("tom", "tom毛");
		System.out.println(shardedJedis.get("tom"));
		jedisPool.close();
	}
	
	// 哨兵jedis测试
	//@Test
	public void test03() {
		// 2.定义哨兵set集合
		Set<String> sets = new HashSet<>();
		// 3.向集合中加入哨兵节点
		sets.add(new HostAndPort("192.168.195.134", 26379).toString());
		sets.add(new HostAndPort("192.168.195.134", 26380).toString());
		sets.add(new HostAndPort("192.168.195.134", 26381).toString());
		
		// 1. 定义哨兵连接池 参数编辑哨兵名称，
		JedisSentinelPool sentinelPool = new JedisSentinelPool("mymaster", sets);
		// 4.插入数据
		Jedis jedis = sentinelPool.getResource();
		jedis.set("1709","哨兵测试");
		System.out.println(jedis.get("1709"));
		
	}
	
	
	
}













