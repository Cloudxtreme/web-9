package cn.netkiller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/redis")
public class RedisController {

	public RedisController() {
		// TODO Auto-generated constructor stub
	}
	@RequestMapping("/")
	public ModelAndView manual() {
		
		return new ModelAndView("redis/index");

	}
	@RequestMapping(value = "/get/{key}/", method = RequestMethod.GET)
	public ModelAndView deployEnvionment(@PathVariable String key) {

		return new ModelAndView("redis/get");
	}
}
