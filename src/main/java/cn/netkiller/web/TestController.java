package cn.netkiller.web;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

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

}
