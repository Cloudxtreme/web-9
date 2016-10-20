package cn.netkiller.web;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.netkiller.service.TestService;

@Controller
@PropertySource("classpath:test.properties")
public class TestController implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@RequestMapping("/test/nocacheable")
	@ResponseBody
	public String nocacheable() {
		Date date = new Date();
		String message = date.toString();
		return message;
	}
	
	@Cacheable("cacheable")
	@RequestMapping("/test/cacheable")
	@ResponseBody
	public String cacheable() {
		Date date = new Date();
		String message = date.toString();
		return message;
	}
	
	//@Scheduled(fixedDelay = 5000)
	@CacheEvict(allEntries = true, value = "cacheable")	
	public void expire() {
		Date date = new Date();
		String message = date.toString();
		System.out.println(message);
	}
	
	@Autowired
	private TestService testService;

	@RequestMapping("/test/protocol")
	@ResponseBody
	public String proto(){
		return testService.getProtocol().get(0).getRequest();
	}
	
	
	
	@RequestMapping("/test/service")
	@ResponseBody
	public String service() {
		return testService.helloUser("Neo");
	}
	
}
