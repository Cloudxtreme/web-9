package cn.netkiller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testRedis() throws Exception {
		stringRedisTemplate.opsForValue().set("hello", "world");
		Assert.assertEquals("world", stringRedisTemplate.opsForValue().get("hello"));
    }

}
