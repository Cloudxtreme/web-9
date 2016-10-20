package cn.netkiller.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import cn.netkiller.pojo.Protocol;


@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

	public RedisConfiguration() {
		// TODO Auto-generated constructor stub
	}

	// @Bean
	// JedisConnectionFactory jedisConnectionFactory() {
	// return new JedisConnectionFactory();
	// }
//	@Bean
//	public JedisConnectionFactory redisConnectionFactory() {
//		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
//
//		// Defaults
//		redisConnectionFactory.setHostName("192.168.4.1");
//		redisConnectionFactory.setPort(6379);
//		return redisConnectionFactory;
//	}

//	@Bean
//	public KeyGenerator listKeyGenerator() {
//		return new KeyGenerator() {
//			@Override
//			public Object generate(Object target, Method method, Object... params) {
//				StringBuilder sb = new StringBuilder();
//				sb.append(target.getClass().getName());
//				sb.append(method.getName());
//				for (Object obj : params) {
//					sb.append(obj.toString());
//				}
//				return sb.toString();
//			}
//		};
//
//	}

	// @Bean
	// public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory
	// factory) {
	// RedisTemplate<String, String> redisTemplate = new RedisTemplate<String,
	// String>();
	// redisTemplate.setConnectionFactory(factory);
	// //redisTemplate.setDefaultSerializer(redisSerializer);
	// return redisTemplate;
	// }

	// @Bean
	// @SuppressWarnings("rawtypes")
	// public RedisTemplate<String, List> redisTemplate(RedisConnectionFactory
	// factory) {
	// RedisTemplate<String, List> template = new RedisTemplate<String, List>();
	// template.setConnectionFactory(redisConnectionFactory());
	// template.setKeySerializer(new StringRedisSerializer());
	// //template.setValueSerializer(new RedisSerializer<?>());
	// return template;
	// }

	@Bean
	public RedisTemplate<String, Protocol> redisTemplate1(RedisConnectionFactory factory) {
		RedisTemplate<String, Protocol> redisTemplate = new RedisTemplate<String, Protocol>();
		redisTemplate.setConnectionFactory(factory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		//redisTemplate.setValueSerializer();
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
//
//	@Bean
//	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
//		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
//		redisCacheManager.setDefaultExpiration(5000);
//		return redisCacheManager;
//		// return new RedisCacheManager(redisTemplate);
//	}
}
