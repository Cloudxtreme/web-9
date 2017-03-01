package cn.netkiller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import cn.netkiller.service.HelloWorldService;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
// @EnableMongoRepositories
// @EnableJpaRepositories
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Autowired
	private HelloWorldService helloWorldService;
	
	@Bean(name = "/HelloWorldService")
	public HessianServiceExporter accountService() {   
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
