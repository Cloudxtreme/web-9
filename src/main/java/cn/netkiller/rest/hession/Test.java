package cn.netkiller.rest.hession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.netkiller.service.HelloWorldService;

@RestController
@RequestMapping("/v1/hession/test")
public class Test {
	@Autowired
	HelloWorldService helloWorldService;
	@RequestMapping("/test")
	public String test(){
	    return helloWorldService.sayHello("Spring boot with Hessian.");
	}
}
