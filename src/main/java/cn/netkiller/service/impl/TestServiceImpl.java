package cn.netkiller.service.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.netkiller.pojo.Protocol;
import cn.netkiller.service.TestService;

@Component
public class TestServiceImpl implements TestService {
	public String name = "Test";
	public TestServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	public String helloUser(String user) {
		return "hello " + user;
	}

	public String getName() {
		return this.name;
	}
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public List<Protocol> getProtocol() {
		List<Protocol> protocols = new ArrayList<Protocol>();
		Gson gson = new Gson();
		Type type = new TypeToken<List<Protocol>>(){}.getType();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		
		String key = "key1";
		long expireTime = 5;
		
		if(redisTemplate.hasKey(key)){
			System.out.println(redisTemplate.opsForValue().get(key));
			protocols = gson.fromJson(redisTemplate.opsForValue().get(key), type);
		}else{
			Protocol protocol = new Protocol();
			protocol.setRequest(new Date().toString());
			protocols.add(protocol);
			
			String jsonString = gson.toJson(protocols, type);
	        System.out.println( jsonString );
			
			redisTemplate.opsForValue().set(key, jsonString);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
		}
		
		
		
		return protocols;
	}
	@Override
	public String toString() {
		return "TestServiceImpl [config=" + this.name + "]";
	}

}
