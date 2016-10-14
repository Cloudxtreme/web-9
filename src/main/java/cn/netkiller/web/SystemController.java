package cn.netkiller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/system")
public class SystemController {

	public SystemController() {
		// TODO Auto-generated constructor stub
	}
	@RequestMapping("/")
	public ModelAndView manual() {
		
		return new ModelAndView("system/index");

	}
	@RequestMapping("/shell")
	public ModelAndView shell() {
		
		return new ModelAndView("system/shell");

	}

}
