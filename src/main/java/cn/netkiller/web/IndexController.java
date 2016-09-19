package cn.netkiller.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*import api.domain.City;
import api.config.ApplicationConfiguration;
import api.domain.Article;
import api.repository.CityRepository;
import api.repository.ArticleRepository;
import api.service.TestService;*/

@Controller
public class IndexController {

	/*@Autowired
	private CityRepository repository;

	@Autowired
	private TestService testService;

	@Autowired
	private ApplicationConfiguration propertie;
*/
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping("/ping")
	@ResponseBody
	public String ping() {
		String message = "PONG";
		return message;
	}
	
	@RequestMapping("/")
	public ModelAndView index() {
		String message = "Hello";
		return new ModelAndView("index").addObject("message", message);
	}

	
	@RequestMapping("/deploy/{group}/{envionment}/{project}")
	public ModelAndView restfulGetId(@PathVariable String group, @PathVariable String envionment, @PathVariable String project) {
		String output = "";
		String command = "";
		if(envionment.equals("testing")){
			command = String.format("ant %s", "push deploy restart");
		}else if(envionment.equals("production")){
			command = String.format("ant %s", "push deploy restart");
		}else{
			command = String.format("ant %s", "pull deploy restart");
		}
		
        try {  
        	String [] cmd={"/bin/sh","-c", command};
        	log.info("The deploy command is {}", cmd.toString());
            // 使用Runtime来执行command，生成Process对象  
            Runtime runtime = Runtime.getRuntime();  
            Process process = runtime.exec(cmd, null, new File(String.format("/www/%s/%s/%s", group, envionment, project)));  
            // 取得命令结果的输出流  
            InputStream is = process.getInputStream();  
            // 用一个读输出流类去读  
            InputStreamReader isr = new InputStreamReader(is);  
            // 用缓冲器读行  
            BufferedReader br = new BufferedReader(isr); 
            
            StringBuilder sb = new StringBuilder();
            String line = null;  
            while ((line = br.readLine()) != null) {  
                sb.append(line);  
            }  
            output = sb.toString();
            log.info("The output is {}", output);
            is.close();  
            isr.close();  
            br.close();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }
        
		return new ModelAndView("output").addObject("output", output);
	}
	
	/*@RequestMapping("/repository")
	@ResponseBody
	public String repository() {

		repository.deleteAll();

		// save a couple of city
		repository.save(new City("Shenzhen", "China"));
		repository.save(new City("Beijing", "China"));

		System.out.println("--------------------------------");
		// fetch all city
		for (City city : repository.findAll()) {
			System.out.println(city);
		}
		// fetch an individual city
		System.out.println("--------------------------------");
		System.out.println(repository.findByName("Shenzhen"));
		System.out.println("--------------------------------");
		for (City city : repository.findByCountry("China")) {
			System.out.println(city);
		}

		String message = "Hello";
		return message;
	}

	@RequestMapping("/service")
	@ResponseBody
	public String service() {
		return testService.helloUser("Neo");
	}

	@Autowired
	private ArticleRepository articleRepository;

	@RequestMapping("/mysql")
	@ResponseBody
	public String mysql() {
		articleRepository.save(new Article("Neo", "Chen"));
		for (Article article : articleRepository.findAll()) {
			System.out.println(article);
		}
		Article tmp = articleRepository.findByTitle("Neo");
		return tmp.getTitle();
	}

	@RequestMapping("/search")
	@ResponseBody
	public String search() {

		
		 * for (Article article : articleRepository.findBySearch(1)) {
		 * System.out.println(article); }
		 
		List<Article> tmp = articleRepository.findBySearch(1L);

		tmp.forEach((temp) -> {
			System.out.println(temp.toString());
		});

		return tmp.get(0).getTitle();
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/article")
	public @ResponseBody String dailyStats(@RequestParam Integer id) {
		String query = "SELECT id, title, content from article where id = " + id;

		return jdbcTemplate.queryForObject(query, (resultSet, i) -> {
			System.out.println(resultSet.getLong(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
			return (resultSet.getLong(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
		});
		
		 * jdbcTemplate.
		 * query("SELECT id, title, content from article where id = ?", new
		 * Object[] { "1" }, (rs, rowNum) -> new Customer(rs.getLong("id"),
		 * rs.getString("first_name"), rs.getString("last_name"))
		 * ).forEach(customer -> log.info(customer.toString()));
		 
		
		 * return jdbcTemplate.queryForObject(query, (resultSet, i) -> { return
		 * new Person(resultSet.getString(1), resultSet.getString(2),
		 * resultSet.getInt(3)); });
		 

	}

	// Spring RESTFul Client
	@RequestMapping("/restful/get")
	@ResponseBody
	public String restfulGet() {
		RestTemplate restTemplate = new RestTemplate();
		String text = restTemplate.getForObject("http://inf.netkiller.com/detail/html/2/2/42564.html", String.class);
		return text;
	}

	@RequestMapping("/restful/get/{id}")
	@ResponseBody
	private static String restfulGetId(@PathVariable String id) {
		final String uri = "http://inf.netkiller.com/detail/html/{tid}/{cid}/{id}.html";

		Map<String, String> params = new HashMap<String, String>();
		params.put("tid", "2");
		params.put("cid", "2");
		params.put("id", id);
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class, params);

		return (result);
	}

	@RequestMapping("/restful/post/{id}")
	@ResponseBody
	private static String restfullPost(@PathVariable String id) {

		final String uri = "http://inf.netkiller.com/detail/html/{tid}/{cid}/{id}.html";

		Map<String, String> params = new HashMap<String, String>();
		params.put("tid", "2");
		params.put("cid", "2");
		params.put("id", id);

		City city = new City("Shenzhen", "Guangdong");

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.postForObject(uri, city, String.class, params);
		return result;
	}

	@RequestMapping("/restful/put/{id}")
	private static void restfulPut(@PathVariable String id) {
		final String uri = "http://inf.netkiller.com/detail/html/{tid}/{cid}/{id}.html";

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);

		City city = new City("Shenzhen", "Guangdong");

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put(uri, city, params);
	}

	@RequestMapping("/restful/delete/{id}")
	private static void restfulDelete(@PathVariable String id) {
		final String uri = "http://inf.netkiller.com/detail/html/{tid}/{cid}/{id}.html";

		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri, params);
	}*/
}
