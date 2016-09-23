package cn.netkiller.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

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

@RestController
@RequestMapping("/v1/deploy")
public class DeployRestController {

	@Autowired
	private SimpMessagingTemplate template;
	
	public DeployRestController() {
		// TODO Auto-generated constructor stub
	}

	private Process exec(String command, String path) {
		//
		Process process = null;
		String[] cmd = null;
		try {
			// String command = String.format("deployment %s %s", envionment,
			// project);
			if(System.getProperty("os.name").equals("Windows 10")){
				cmd = new String[] { "cmd", "/C", command };
			}else{
				cmd = new String[] { "/bin/bash", "-c", command };
			} 
			// log.info("The deployment command is {}", Arrays.toString(cmd));

			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(cmd);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// log.info("The output is {}", stringBuilder.toString());
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
			// StringBuilder stringBuilder = new StringBuilder();
			String line = null;
			try {

				InputStream inputStream = process.getInputStream();

				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				while ((line = bufferedReader.readLine()) != null) {
					// stringBuilder.append(line + "\n");
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

		}

	}

	@RequestMapping("/test")
	public String test() throws IOException {
		ScreenOutput r = new ScreenOutput(this.template, this.exec("dir", "."));
		new Thread(r).start();
		System.out.println("===========os.name:"+System.getProperties().getProperty("os.name"));  
		return "OK";
	}

	@RequestMapping("/config/{group}/{envionment}/{project}/")
	public String config(@PathVariable String group, @PathVariable String envionment, @PathVariable String project) throws IOException {
		String status = "OK";
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
			
		}else{
			status = "False";
		}

		return status;
	}
	
	@RequestMapping(value = "/manual", method = RequestMethod.POST, produces = { "application/xml", "application/json" })
	public String test(@RequestBody Deploy deploy) {
		String status = "OK";
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
		} else {
			status = "false";
		}
		
		return status;
	}

}
