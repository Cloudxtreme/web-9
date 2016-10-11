package cn.netkiller.rest;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.netkiller.pojo.Redis;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/v1/redis")
public class RedisRestController extends CommonRestController {

	public RedisRestController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping(value = "/keys", method = RequestMethod.POST, produces = { "application/xml", "application/json" })
	@SuppressWarnings({ "rawtypes" })
	public ResponseEntity<Set> keys(@RequestBody Redis redis) throws IOException {
		Jedis jedis = new Jedis(redis.getHost());
		jedis.select(redis.getDb());
		Set<String> keys = jedis.keys(redis.getKeys());
		jedis.close();
		return new ResponseEntity<Set>(keys, HttpStatus.OK);
	}
	@RequestMapping(value = "/del", method = RequestMethod.POST, produces = { "application/xml", "application/json" })
	public ResponseEntity<Redis> del(@RequestBody Redis redis) throws IOException {
		Jedis jedis = new Jedis(redis.getHost());
		jedis.select(redis.getDb());
		jedis.del(redis.getKeys());
		jedis.close();
		return new ResponseEntity<Redis>(redis, HttpStatus.OK);
	}
}
