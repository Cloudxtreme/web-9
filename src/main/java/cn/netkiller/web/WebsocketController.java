package cn.netkiller.web;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;

import cn.netkiller.pojo.Greeting;
import cn.netkiller.pojo.HelloMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
//@RequestMapping("/socket")
public class WebsocketController {

	public WebsocketController() {
		// TODO Auto-generated constructor stub
	}
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }
	
	@MessageMapping("/tail")
    @SendTo("/topic/tail")
    public Greeting greeting1(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

}
