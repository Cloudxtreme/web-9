package cn.netkiller.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.netkiller.pojo.Deploy;
import cn.netkiller.pojo.Greeting;
import cn.netkiller.pojo.Protocol;
import cn.netkiller.web.IndexController;

@RestController
@RequestMapping("/v1/deploy")
public class DeployRestController {

	@Autowired
	private SimpMessagingTemplate template;
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	public DeployRestController() {
		// TODO Auto-generated constructor stub
	}

	private Process exec(String command, String path) {

		Process process = null;
		String[] cmd = null;
		try {
			if (System.getProperty("os.name").equals("Windows 10")) {
				cmd = new String[] { "cmd", "/C", command };
			} else {
				cmd = new String[] { "/bin/bash", "-c", command };
			}

			log.info("The command is {}", Arrays.toString(cmd));

			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(cmd, null, new File(path));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return process;
	}

	public class ScreenOutput implements Runnable {

		private SimpMessagingTemplate simpMessagingTemplate;
		private Process process = null;

		public ScreenOutput(SimpMessagingTemplate simpMessagingTemplate, Process process) {
			this.simpMessagingTemplate = simpMessagingTemplate;
			this.process = process;
		}

		@Override
		public void run() {
			StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			try {

				InputStream inputStream = process.getInputStream();

				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line + "\n");
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
			log.info("The output is {}", stringBuilder.toString());
		}

	}
	private Properties config(String path){
		Properties properties = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(path));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}
	@RequestMapping("/test")
	public String test() throws IOException {
		ScreenOutput r = new ScreenOutput(this.template, this.exec("dir", "."));
		new Thread(r).start();
		System.out.println("===========os.name:" + System.getProperties().getProperty("os.name"));
		return "OK";
	}
	@RequestMapping("/ant")
	public String ant() throws IOException {
		String group= "cf88.com",envionment="development",project="admin.cf88.com";
		
		String path = String.format("/www/%s/%s/%s", group, envionment, project);
		String command = String.format("ant -propertyfile %s/%s", "/tmp/test", "build.properties");
		Properties properties = this.config(path);
		
		for (Entry<Object, Object> entry : properties.entrySet()) {
	        System.out.println(entry.getKey() + " => " + entry.getValue());
	    }
		
		ScreenOutput r = new ScreenOutput(this.template, this.exec(command, "/tmp/test"));
		new Thread(r).start();
		

		
		return "OK";
	}

	@RequestMapping("/config/{group}/{envionment}/{project}/")
	public Protocol config(@PathVariable String group, @PathVariable String envionment, @PathVariable String project) throws IOException {
		Protocol protocol = new Protocol();	
		protocol.setStatus(true);
		String workspace = String.format("/www/%s/%s/%s", group, envionment, project);
		File file = new File(workspace);
		if (!file.exists()) {
			workspace = "/www";
		}

		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s/%s.properties", group, envionment)));
		if (properties.containsKey(project)) {
			String command = properties.getProperty(project);
			ScreenOutput r = new ScreenOutput(this.template, this.exec(command, workspace));
			new Thread(r).start();
			protocol.setRequest(command);
		} else {
			protocol.setStatus(false);
		}

		return protocol;
	}

	
	/*	 
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X POST -d '{"group":"netkiller.cn", "envionment":"development", "project":"www.netkiller.cn", "arguments":["ant", "pull"]}'  http://user:password@172.30.9.11:7000/v1/deploy/manual.json
	 */
	@RequestMapping(value = "/manual", method = RequestMethod.POST, produces = { "application/xml", "application/json" })
	public Protocol test(@RequestBody Deploy deploy) {
		System.out.println(deploy.toString());
		Protocol protocol = new Protocol();	
		protocol.setStatus(true);
		String command = "";
		ScreenOutput screenOutput = null;

		if (deploy.getArguments() != null) {

			if (deploy.getArguments().contains("deployment")) {
				command = String.format("deployment %s %s", deploy.getEnvionment(), deploy.getProject());
				screenOutput = new ScreenOutput(this.template, this.exec(command, "/www"));
			} else {
				command = String.join(" ", deploy.getArguments());
				String workspace = String.format("/www/%s/%s/%s", deploy.getGroup(), deploy.getEnvionment(), deploy.getProject());
				screenOutput = new ScreenOutput(this.template, this.exec(command, workspace));
			}
			new Thread(screenOutput).start();
			protocol.setRequest(command);
			protocol.setResponse(deploy.toString());
		} else {
			protocol.setStatus(false);
		}

		return protocol;
	}

}
