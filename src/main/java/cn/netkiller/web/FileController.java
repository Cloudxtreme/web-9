package cn.netkiller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/file")
public class FileController {

	public FileController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@RequestMapping("/test")
	@ResponseBody
	public String ping() {
		String message = "PONG";
		stringRedisTemplate.opsForValue().set("hello", "world");
		message = stringRedisTemplate.opsForValue().get("hello");
		return message;
	}

}
