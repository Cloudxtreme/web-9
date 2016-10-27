package cn.netkiller.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/config")
public class ConfigRestController extends CommonRestController {

	public ConfigRestController() {
		// TODO Auto-generated constructor stub
	}

	// @Autowired
	// private WithdrawRepository repository;

	@RequestMapping("version")
	@ResponseStatus(HttpStatus.OK)
	public String version() {
		return "[OK] Welcome to withdraw Restful version 1.0";
	}

	@RequestMapping("/project/{group}/{envionment}")
	public Enumeration<Object> project(@PathVariable String group, @PathVariable String envionment) throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s/%s.properties", group, envionment)));
		return properties.keys();
	}

	@RequestMapping("/group")
	public List<String> group() throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s.properties", "config")));
		return Arrays.asList(String.valueOf(properties.get("group")).concat(",").split(","));
	}
	@RequestMapping("/project/{group}")
	public List<String> project(@PathVariable String group) throws IOException {
		List<String> dir= new ArrayList<String>();
		String path = String.format("%s/%s/", this.workspace, group);
 		try(Stream<Path> paths = Files.walk(Paths.get(path),1)) {
		    paths.forEach(filePath -> {
		        if (Files.isDirectory(filePath)) {
		        	String project = filePath.toString().replace(path, "").replace(path.substring(0, path.length()-1), "");
		        	if(! project.equals("")){
		        		dir.add(project);
		        	}
		            
		        }
		    });
		}
		return dir;
	}
	@RequestMapping("/envionment")
	public List<String> envionment() throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s.properties", "config")));
		return Arrays.asList(String.valueOf(properties.get("envionment")).concat(",").split(","));
	}
	
	@RequestMapping("/envionment/{group}/{project}")
	public List<String> envionment(@PathVariable String group, @PathVariable String project) throws IOException {
		List<String> dir= new ArrayList<String>();
		String path = String.format("%s/%s/%s/", this.workspace, group, project);
 		try(Stream<Path> paths = Files.walk(Paths.get(path),1)) {
		    paths.forEach(filePath -> {
		        if (Files.isDirectory(filePath)) {
		        	String envionment = filePath.toString().replace(path, "").replace(path, "").replace(path.substring(0, path.length()-1), "");
		        	if(! envionment.equals("")){
		        		dir.add(envionment);
		        	}
		            
		        }
		    });
		}
		return dir;
	}
	@RequestMapping("/build/{group}/{envionment}/{project}/")
	public ResponseEntity<Properties> build(@PathVariable String group, @PathVariable String envionment, @PathVariable String project) throws IOException {
		Properties properties = null;
		String workspace = String.format("%s/%s/%s/%s/build.properties", this.workspace, group, envionment, project);
		File file = new File(workspace);
		if (file.exists()) {
			properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s/%s.properties", group, envionment)));
		}

		return new ResponseEntity<Properties>(properties, HttpStatus.OK);
	}
	@RequestMapping("/host")
	public Enumeration<Object> host() throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s.properties", "host")));
		return properties.keys();
	}

	@RequestMapping("/mail")
	public Collection<Object> mail() throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s.properties", "mail")));
		return properties.values();
	}
	@RequestMapping("/dns")
	public Properties dns() throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s.properties", "dns")));
		return properties;
	}
}
