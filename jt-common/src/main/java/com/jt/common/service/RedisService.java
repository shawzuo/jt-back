package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
public class RedisService {
	/*@Autowired
	private ShardedJedisPool jedisPool;
	
	public void set(String key, String value) {
		ShardedJedis shardedJedis = jedisPool.getResource();
		shardedJedis.set(key, value);
		// 将连接还回池中
		jedisPool.returnResource(shardedJedis);
	}
	
	public String get(String key) {
		ShardedJedis shardedJedis = jedisPool.getResource();
		String value = shardedJedis.get(key);
		jedisPool.returnResource(shardedJedis);
		return value;
	}*/
	
	// 如果spring容器中有该对象则注入，如果没有该对象则不管
	@Autowired(required=false)
	private JedisSentinelPool sentinelPool;
	
	public void set(String key, String value) {
		Jedis jedis = sentinelPool.getResource();
		jedis.set(key, value);
		sentinelPool.returnResource(jedis);
	}
	
	public String get(String key) {
		Jedis jedis = sentinelPool.getResource();
		String value = jedis.get(key);
		sentinelPool.returnResource(jedis);
		return value;
	}
}
