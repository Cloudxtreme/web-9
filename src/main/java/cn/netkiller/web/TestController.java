package cn.netkiller.web;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PropertySource("classpath:test.properties")
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(TestController.class);

	@RequestMapping("/test/log")
	@ResponseBody
	public String log() {
		String message = "Test";
		log.debug(message);
		log.info(message);
		log.warn(message);
		log.error(message);
		log.trace(message);
		return message;
	}
	
	@Autowired
	Environment environment;

	@Value("${age}")
	private String age;

	public TestController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/session/set")
	@ResponseBody
	public String set(HttpSession session) {
		String key = "test";
		session.setAttribute(key, new Date());
		return key;
	}

	@RequestMapping("/session/get")
	@ResponseBody
	public String get(HttpSession session) {
		String value = (String) session.getAttribute("test").toString();
		return value;
	}

	@RequestMapping("/test/env")
	@ResponseBody
	public String env() {
		String message = environment.getProperty("name");
		return message;
	}

	@RequestMapping("/test/value/age")
	@ResponseBody
	public String age() {
		String message = age;
		return message;
	}

}
