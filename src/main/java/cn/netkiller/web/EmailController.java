package cn.netkiller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mail")
public class EmailController {

	public EmailController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/")
	public ModelAndView ant() {
		
		return new ModelAndView("mail/index");

	}

}
