package cn.netkiller.web;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.netkiller.pojo.Greeting;
import cn.netkiller.pojo.HelloMessage;

@Controller
@RequestMapping("/logging")
public class LoggingController {

	@RequestMapping(value = "/watch/{group}/{envionment}/{project}", method = RequestMethod.GET)
	public ModelAndView deployEnvionment(@PathVariable String group, @PathVariable String envionment, @PathVariable String project) throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s/%s/%s.properties", group, envionment, project)));
		ModelAndView modelAndView = new ModelAndView("logging/watch");
		modelAndView.addObject("project", project);
		modelAndView.addObject("groups", group);
		modelAndView.addObject("envionment", envionment);
		modelAndView.addObject("logs", properties.keySet());
		// log.info(String.valueOf(properties.get("group")).concat(","));
		return modelAndView;
	}
	
	
	public class FireGreeting implements Runnable {

	    private SimpMessagingTemplate listener;

	    public FireGreeting(SimpMessagingTemplate listener) {
	        this.listener = listener;
	    }

	    @Override
	    public void run() {
	        while (true) {
	            try {
	                Thread.sleep( 2000 );
//	                listener.fireGreeting();
	                this.listener.convertAndSend("/topic/log", new Greeting("Fire"));
	            } catch ( InterruptedException e ) {
	                e.printStackTrace();
	            }
	        }   
	    }
	}
	
	@Autowired
    private SimpMessagingTemplate template;
	
	@MessageMapping("/log")
    @SendTo("/topic/log")
    public Greeting log(HelloMessage message) throws Exception {
        //Thread.sleep(1000); // simulated delay
		FireGreeting r = new FireGreeting( this.template );
        new Thread(r).start();
        
        return new Greeting("Hello, " + message.getName() + "!");
    }
}

