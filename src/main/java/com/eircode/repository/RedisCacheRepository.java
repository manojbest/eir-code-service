package com.eircode.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisCacheRepository implements CacheRepository {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void putData(final String key, final String jsonString) {
		redisTemplate.opsForHash().put("eri-code-service", key, jsonString);
	}

	@Override
	public String getData(final String key) {
		return (String) redisTemplate.opsForHash().get("eri-code-service", key);
	}
}
