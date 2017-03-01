package cn.netkiller.service.impl;

import org.springframework.stereotype.Component;

import cn.netkiller.service.HelloWorldService;
@Component
public class HelloWorldServiceImpl implements HelloWorldService {
	@Override
	public String sayHello(String name) {
		return "Hello World! " + name;
	}
}
