package cn.netkiller.rest.hession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.netkiller.service.HelloWorldService;

@RestController
@RequestMapping("/public/hession/test")
public class TestRestController {
	@Autowired
	HelloWorldService helloWorldService;

	@RequestMapping("/hello")
	public String test() {
		return helloWorldService.sayHello("Spring boot with Hessian.");
	}
}
