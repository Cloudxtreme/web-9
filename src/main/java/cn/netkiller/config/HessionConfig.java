package cn.netkiller.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
//import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;

import cn.netkiller.service.HelloWorldService;

@Configuration
public class HessionConfig {
	@Autowired
	private HelloWorldService helloWorldService;

	@Bean(name = "/HelloWorldService")
	public HessianServiceExporter hessianServiceExporter() {
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(helloWorldService);
		exporter.setServiceInterface(HelloWorldService.class);
		return exporter;
	}

	@Bean
	public HessianProxyFactoryBean helloClient() {
		HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
		factory.setServiceUrl("http://localhost:7000/HelloWorldService");
		factory.setServiceInterface(HelloWorldService.class);
		return factory;
	}
}
