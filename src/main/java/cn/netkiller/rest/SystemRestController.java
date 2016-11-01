package cn.netkiller.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.netkiller.pojo.Protocol;

@RestController
@RequestMapping("/v1/system")
public class SystemRestController  extends CommonRestController{

	@Autowired
	private SimpMessagingTemplate template;
	
	private static final Logger log = LoggerFactory.getLogger(SystemRestController.class);
	
	List<String> exclude = new ArrayList<String>();
	
	public SystemRestController() {
		exclude.add("localhost");
		exclude.add("127.0.0.1");
	}
	
	@RequestMapping(value = "/shell/{host}", method = RequestMethod.POST, produces = { "application/xml", MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Protocol> host(@PathVariable String host, @RequestBody Protocol proto) throws IOException {
		String rhost = "localhost";
		if(host != null){
			if(exclude.contains(host)){
				new Thread(new ScreenOutput(this.template, "/topic/shell", this.exec(proto.getRequest(), "/tmp"))).start();
			}else{
				Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s.properties", "host")));
				rhost = (String) properties.get(host);
				new Thread(new ScreenOutput(this.template, "/topic/shell", this.rexec(rhost, proto.getRequest(), "/tmp"))).start();
			}
			proto.setStatus(true);

		}
		proto.setResponse("Done");
		return new ResponseEntity<Protocol> (proto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/shell/remote/{user}/{host}", method = RequestMethod.POST, produces = { "application/xml", MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Protocol> remote(@PathVariable String user, @PathVariable String host, @RequestBody Protocol proto) throws IOException {
		if(host != null){
			if(user != null){
				host = user.concat("@").concat(host);
			}
			new Thread(new ScreenOutput(this.template, "/topic/shell", this.rexec(host, proto.getRequest(), "/tmp"))).start();
		}
		proto.setResponse("Done");
		return new ResponseEntity<Protocol> (proto, HttpStatus.OK);
	}
	
	protected Process rexec(String host, String command, String path) {
		Process process = null;
		String[] cmd = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				path = "~";
			}

			cmd = new String[] { "ssh", host, command };


			log.info("The shell command is {}", Arrays.toString(cmd));

			Runtime runtime = Runtime.getRuntime();
			process = runtime.exec(cmd, null, new File(path));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return process;
	}
	
	protected Process exec(String command, String path) {

		Process process = null;
		String[] cmd = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				path = "/tmp";
			}
			
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
		private String destination = null;

		public ScreenOutput(SimpMessagingTemplate simpMessagingTemplate, String destination, Process process) {
			this.simpMessagingTemplate = simpMessagingTemplate;
			this.destination = destination;
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
					this.simpMessagingTemplate.convertAndSend(this.destination, new Protocol(null, line));
					log.info("{}", line);
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
//			log.info("The output is {}", stringBuilder.toString());
		}

	}

}
