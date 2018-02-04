package com.lbs.nettyserver.utils.sysutils;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
	
	@Autowired
	private RedisTemplate<Serializable, Object> redisTemplate;

	public  void remove(String... keys) {
		String[] arg1 = keys;
		int arg2 = keys.length;

		for (int arg3 = 0; arg3 < arg2; ++arg3) {
			String key = arg1[arg3];
			this.remove(key);
		}

	}

	public void removePattern(String pattern) {
		Set keys = this.redisTemplate.keys(pattern);
		if (keys.size() > 0) {
			this.redisTemplate.delete(keys);
		}

	}

	public void remove(String key) {
		if (this.exists(key)) {
			this.redisTemplate.delete(key);
		}

	}

	public boolean exists(String key) {
		return this.redisTemplate.hasKey(key).booleanValue();
	}

	public Object get(String key) {
		Object result = null;
		ValueOperations operations = this.redisTemplate.opsForValue();
		result = operations.get(key);
		return result;
	}

	public boolean set(String key, Object value) {
		boolean result = false;

		try {
			ValueOperations e = this.redisTemplate.opsForValue();
			e.set(key, value);
			result = true;
		} catch (Exception arg4) {
			arg4.printStackTrace();
		}

		return result;
	}

	public boolean set(String key, Object value, Long expireTime) {
		boolean result = false;

		try {
			ValueOperations e = this.redisTemplate.opsForValue();
			e.set(key, value);
			this.redisTemplate.expire(key, expireTime.longValue(), TimeUnit.SECONDS);
			result = true;
		} catch (Exception arg5) {
			arg5.printStackTrace();
		}

		return result;
	}

	public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}