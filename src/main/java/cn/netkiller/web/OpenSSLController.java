package cn.netkiller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/openssl")
public class OpenSSLController {

	public OpenSSLController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/index")
	public ModelAndView config() {
		
		return new ModelAndView("openssl/index");

	}
}
