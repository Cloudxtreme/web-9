package cn.netkiller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.netkiller.repository.CityRepository;
import cn.netkiller.repository.MultilevelDirectSellingAccountRewardsRepository;
import cn.netkiller.repository.MultilevelDirectSellingTradingRebateRepository;
import cn.netkiller.domain.City;
import cn.netkiller.domain.MultilevelDirectSellingAccountRewards;
import cn.netkiller.domain.MultilevelDirectSellingTradingRebate;
import cn.netkiller.domain.MultilevelDirectSellingTradingRebate.Rebate;
import cn.netkiller.domain.MultilevelDirectSellingTradingRebate.Type;

@Controller
@RequestMapping("/test/mongodb")
public class TestMongDBController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private CityRepository repository;

	@Autowired
	private MultilevelDirectSellingTradingRebateRepository multilevelDirectSellingTradingRebateRepository;
	
	@Autowired
	private MultilevelDirectSellingAccountRewardsRepository multilevelDirectSellingAccountRewardsRepository;

	@RequestMapping("/index")
	@ResponseBody
	public ModelAndView index() {
		String message = "Hello";
		return new ModelAndView("home/welcome", "variable", message);
	}

	@RequestMapping("/curd")
	@ResponseBody
	public String curd() {

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

	// recommender systems
	@RequestMapping("/rebate")
	@ResponseBody
	public String rebate() {
		MultilevelDirectSellingTradingRebate multilevelDirectSellingTradingRebate = new MultilevelDirectSellingTradingRebate();
		multilevelDirectSellingTradingRebate.name = "TEST";
		multilevelDirectSellingTradingRebate.beginDate = new Date();
		multilevelDirectSellingTradingRebate.endDate = new Date();
		multilevelDirectSellingTradingRebate.lowAmount = 1.5d;
		multilevelDirectSellingTradingRebate.highAmount = 100d;
		multilevelDirectSellingTradingRebate.type = Type.CASH;

		// List<Float> usd = new ArrayList<Float>();
		// usd.add(10.00f);
		// usd.add(6.00f);

		Map<Enum<Rebate>, Double> rebate = new HashMap<Enum<Rebate>, Double>();

		rebate.put(Rebate.DIRECT, 10.05d);
		rebate.put(Rebate.INDIRECT, 6.05d);

		Map<String, Map<?, ?>> prod1 = new HashMap<String, Map<?, ?>>();
		prod1.put("USDRMB", rebate);

		List<Map<String, Map<?, ?>>> products = new ArrayList<Map<String, Map<?, ?>>>();
		products.add(prod1);
		multilevelDirectSellingTradingRebate.product = products;

		multilevelDirectSellingTradingRebateRepository.deleteAll();
		multilevelDirectSellingTradingRebateRepository.save(multilevelDirectSellingTradingRebate);
		MultilevelDirectSellingTradingRebate rev = multilevelDirectSellingTradingRebateRepository.findByName("TEST");
		System.out.println(rev);
		logger.info(rev.toString());
		return rev.toString();
	}
	
	@RequestMapping("/rewards")
	@ResponseBody
	public String Rewards() {
		MultilevelDirectSellingAccountRewards multilevelDirectSellingAccountRewards = new MultilevelDirectSellingAccountRewards();
		multilevelDirectSellingAccountRewards.name = "TEST";
		multilevelDirectSellingAccountRewards.beginDate = new Date();
		multilevelDirectSellingAccountRewards.endDate = new Date();
		multilevelDirectSellingAccountRewards.directAmount = 60d;
		multilevelDirectSellingAccountRewards.indirectAmount = 70d;
		multilevelDirectSellingAccountRewards.minimumAmount = 10d;

		// List<Float> usd = new ArrayList<Float>();
		// usd.add(10.00f);
		// usd.add(6.00f);

		Map<String, Double> products = new HashMap<String, Double>();

		products.put("USD", 10.05d);
		products.put("RMB", 6.05d);

		List<Map<String, Double>> lots = new ArrayList<Map<String, Double>>();
		lots.add(products);
		multilevelDirectSellingAccountRewards.lot = lots;

		multilevelDirectSellingAccountRewardsRepository.deleteAll();
		multilevelDirectSellingAccountRewardsRepository.save(multilevelDirectSellingAccountRewards);
		MultilevelDirectSellingTradingRebate rev = multilevelDirectSellingTradingRebateRepository.findByName("TEST");
		System.out.println(rev);
		logger.info(rev.toString());
		return rev.toString();
	}

}
