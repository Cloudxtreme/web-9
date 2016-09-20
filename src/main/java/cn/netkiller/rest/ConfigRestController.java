package cn.netkiller.rest;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/config")
public class ConfigRestController extends CommonRestController{

	public ConfigRestController() {
		// TODO Auto-generated constructor stub
	}
	
//	@Autowired
//	private WithdrawRepository repository;

	@RequestMapping("version")
	@ResponseStatus(HttpStatus.OK)
	public String version() {
		return "[OK] Welcome to withdraw Restful version 1.0";
	}

	@RequestMapping("/envionment/{envionment}")
	public Enumeration<Object> findById(@PathVariable String envionment) throws IOException {
		Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource(String.format("/%s.properties", envionment)));
		return properties.keys();
	}

/*	@RequestMapping(value = "create", method = RequestMethod.POST, produces = { "application/xml", "application/json" })
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Withdraw> create(@RequestBody Withdraw withdraw) throws ParseException {
		// withdraw.setCreatedDate(TimeUtils.getCurrentGmtDate());
		repository.save(withdraw);
		return new ResponseEntity<Withdraw>(withdraw, HttpStatus.OK);
	}
*/

}
