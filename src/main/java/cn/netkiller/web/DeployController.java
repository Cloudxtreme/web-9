package cn.netkiller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/deploy")
public class DeployController {

	public DeployController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/ant")
	public ModelAndView ant() {
		
		return new ModelAndView("deploy/ant");

	}
	
	@RequestMapping("/manual")
	public ModelAndView manual() {
		
		return new ModelAndView("deploy/manual");

	}
}
