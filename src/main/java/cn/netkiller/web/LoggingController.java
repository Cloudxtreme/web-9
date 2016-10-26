package cn.netkiller.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.messaging.MessagingException;
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
public class LoggingController extends CommonController{
	Properties properties = null;

	public LoggingController() {

	}
	@RequestMapping("/")
	public ModelAndView ant() {
		
		return new ModelAndView("logging/index");

	}
	@RequestMapping(value = "/watch/{group}/{envionment}/{project}", method = RequestMethod.GET)
	public ModelAndView deployEnvionment(@PathVariable String group, @PathVariable String envionment, @PathVariable String project) throws IOException {
		properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s/%s/%s.properties", group, envionment, project)));
		ModelAndView modelAndView = new ModelAndView("logging/watch");
		modelAndView.addObject("project", project);
		modelAndView.addObject("groups", group);
		modelAndView.addObject("envionment", envionment);
		modelAndView.addObject("logs", properties.keySet());
		// log.info(String.valueOf(properties.get("group")).concat(","));
		return modelAndView;
	}

	public class FireGreeting implements Runnable {

		private SimpMessagingTemplate simpMessagingTemplate;
		private String command ="";

		public FireGreeting(SimpMessagingTemplate simpMessagingTemplate, String command) {
			this.simpMessagingTemplate = simpMessagingTemplate;
			this.command = command;
		}

		@Override
		public void run() {
			//StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			try {
				
				InputStream inputStream = this.cmd(this.command).getInputStream();

				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				while ((line = bufferedReader.readLine()) != null) {
					//stringBuilder.append(line + "\n");
					this.simpMessagingTemplate.convertAndSend("/topic/log", new Greeting(line));
				}
				bufferedReader.close();
				inputStream.close();
				inputStreamReader.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			while (true) {
				try {
					Thread.sleep(2000);
					// simpMessagingTemplate.fireGreeting();
					this.simpMessagingTemplate.convertAndSend("/topic/log", new Greeting("Fire"));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
		}
		
		private Process cmd(String command) {
			//
			Process process = null;
			try {
				//String command = String.format("deployment %s %s", envionment, project);

				//String[] cmd = new String[] { "/bin/bash", "-c", command };
				String[] cmd = new String[] { "cmd","/C", command };

				//log.info("The deployment command is {}", Arrays.toString(cmd));

				Runtime runtime = Runtime.getRuntime();
				 process = runtime.exec(cmd);				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//log.info("The output is {}", stringBuilder.toString());
			return process;
		}
		
	}

	@Autowired
	private SimpMessagingTemplate template;

	@MessageMapping("/log")
	@SendTo("/topic/log")
	public Greeting log(HelloMessage message) throws Exception {
		// Thread.sleep(1000); // simulated delay
		FireGreeting r = new FireGreeting(this.template, message.getName());
		new Thread(r).start();
		
		//return new Greeting("Hello, " + properties.getProperty(message.getName()) + "!");
		return new Greeting("Hello, " + message.getName() + "!");
	}
}
