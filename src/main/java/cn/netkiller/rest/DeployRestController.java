package cn.netkiller.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.netkiller.pojo.Deploy;
import cn.netkiller.pojo.Protocol;

@RestController
@RequestMapping("/v1/deploy")
public class DeployRestController extends ShellRestController {

	@Autowired
	private SimpMessagingTemplate template;
	private static final Logger log = LoggerFactory.getLogger(DeployRestController.class);

	public DeployRestController() {
		// TODO Auto-generated constructor stub
	}

	private Properties config(String path) {
		log.info("The config is {}", path);
		Properties properties = new java.util.Properties();

		try {
			File file = new File(path);
			if (file.exists()) {
				properties.load(new FileInputStream(file));
			}else{
				properties = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}

	@RequestMapping("/test")
	public String test() throws IOException {
		ScreenOutput r = new ScreenOutput(this.template, "/topic/log", super.exec("dir", "."));
		new Thread(r).start();
		System.out.println("===========os.name:" + System.getProperties().getProperty("os.name"));
		return "OK";
	}

	
	@RequestMapping(value = "/ant", method = RequestMethod.POST, produces = { "application/xml", "application/json" })
	public ResponseEntity<Protocol> ant(@RequestBody Deploy deploy) throws IOException {
		Protocol protocol = new Protocol();
		protocol.setStatus(true);
		String group =deploy.getGroup(), envionment = deploy.getEnvionment(), project = deploy.getProject();
		String arguments = String.join(" ", deploy.getArguments());
		String buildfile = "";
		ClassPathResource classPathResource = new ClassPathResource("build.xml");
		if (classPathResource.exists()) {
			// properties =
			// PropertiesLoaderUtils.loadProperties(classPathResource);
			buildfile = classPathResource.getFile().getAbsolutePath();
			// System.out.println(buildfile);
		}

		String propertyfile = String.format("%s/%s/%s/%s/build.properties", this.workspace, group, project, envionment);
		String command = String.format("ant -propertyfile %s -buildfile %s %s", propertyfile, buildfile, arguments);
		Properties properties = this.config(propertyfile);
		if (properties != null) {
			for (Entry<Object, Object> entry : properties.entrySet()) {
				System.out.println(entry.getKey() + " => " + entry.getValue());
			}

			ScreenOutput r = new ScreenOutput(this.template, "/topic/log", this.exec(command, "~"));
			new Thread(r).start();
			protocol.setRequest(command);
		}else {
			protocol.setRequest(command);
			protocol.setResponse(String.format("Cannot open file (%s)", propertyfile));
			protocol.setStatus(false);
		}

		return new ResponseEntity<Protocol> (protocol, HttpStatus.OK);
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
			ScreenOutput r = new ScreenOutput(this.template, "/topic/log", this.exec(command, workspace));
			new Thread(r).start();
			protocol.setRequest(command);
		} else {
			protocol.setStatus(false);
		}

		return protocol;
	}

	/*
	 * curl -i -H "Accept: application/json" -H "Content-Type: application/json"
	 * -X POST -d '{"group":"netkiller.cn", "envionment":"development",
	 * "project":"www.netkiller.cn", "arguments":["ant", "pull"]}'
	 * http://user:password@172.30.9.11:7000/v1/deploy/manual.json
	 */
	@RequestMapping(value = "/manual", method = RequestMethod.POST, produces = { "application/xml", "application/json" })
	public Protocol manual(@RequestBody Deploy deploy) {
		System.out.println(deploy.toString());
		Protocol protocol = new Protocol();
		protocol.setStatus(true);
		String command = "";
		ScreenOutput screenOutput = null;

		if (deploy.getArguments() != null) {

			if (deploy.getArguments().contains("deployment")) {
				command = String.format("deployment %s %s", deploy.getEnvionment(), deploy.getProject());
				screenOutput = new ScreenOutput(this.template, "/topic/log", this.exec(command, "/www"));
			} else {
				command = String.join(" ", deploy.getArguments());
				String workspace = String.format("/www/%s/%s/%s", deploy.getGroup(), deploy.getEnvionment(), deploy.getProject());
				screenOutput = new ScreenOutput(this.template, "/topic/log", this.exec(command, workspace));
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
