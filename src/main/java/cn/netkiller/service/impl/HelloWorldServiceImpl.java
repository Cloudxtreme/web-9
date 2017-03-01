package cn.netkiller.service.impl;

import cn.netkiller.service.HelloWorldService;

public class HelloWorldServiceImpl implements HelloWorldService {
	@Override
	public String sayHello(String name) {
		return "Hello World! " + name;
	}
}
