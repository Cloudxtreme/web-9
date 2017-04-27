package cn.netkiller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.netkiller.model.Customer;
import cn.netkiller.repository.CustomerRepository;

@Controller
@RequestMapping("/test/pgsql")
public class TestPostgreSQLController {

	@Autowired
	private CustomerRepository customerRepository;

	@RequestMapping("/save")
	public @ResponseBody String process() {
		customerRepository.save(new Customer("Jack", "Smith"));
		customerRepository.save(new Customer("Adam", "Johnson"));
		customerRepository.save(new Customer("Kim", "Smith"));
		customerRepository.save(new Customer("David", "Williams"));
		customerRepository.save(new Customer("Peter", "Davis"));
		return "Done";
	}

	@RequestMapping("/findall")
	public @ResponseBody String findAll() {
		String result = "<html>";

		for (Customer cust : customerRepository.findAll()) {
			result += "<div>" + cust.toString() + "</div>";
		}

		return result + "</html>";
	}

	@RequestMapping("/findbyid")
	public @ResponseBody String findById(@RequestParam("id") long id) {
		String result = "";
		result = customerRepository.findOne(id).toString();
		return result;
	}

	@RequestMapping("/findbylastname")
	public @ResponseBody String fetchDataByLastName(@RequestParam("lastname") String lastName) {
		String result = "<html>";

		for (Customer cust : customerRepository.findByLastName(lastName)) {
			result += "<div>" + cust.toString() + "</div>";
		}

		return result + "</html>";
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/jdbc")
	public @ResponseBody String dailyStats(@RequestParam Integer id) {
		String query = "SELECT id, firstname, lastname from customer where id = " + id;

		return jdbcTemplate.queryForObject(query, (resultSet, i) -> {
			System.out.println(resultSet.getLong(1)+","+ resultSet.getString(2)+","+ resultSet.getString(3));
			return (resultSet.getLong(1)+","+ resultSet.getString(2)+","+ resultSet.getString(3));
		});
	}
}
