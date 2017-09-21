//package cn.netkiller.web;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//
//import cn.netkiller.domain.Article;
//import cn.netkiller.repository.ArticleElasticsearchRepository;
//
//@Controller
//@RequestMapping("/test/elasticsearch")
//public class ElasticsearchController {
//
//	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchController.class);
//
////	@Autowired
////	private ElasticsearchTemplate elasticsearchTemplate;
//
//	@Autowired
//	private ArticleElasticsearchRepository articleElasticsearchRepository;
//
//	@RequestMapping("/curd")
//	@ResponseBody
//	public ModelAndView curd() {
//		String message = "Hello";
//		Article article = new Article();
//		article.setId("1");
//		article.setTitle("Helloworld!!!");
//		article.setAuthor("Neo");
//		article.setContent("I am netkiller");
//		articleElasticsearchRepository.save(article);
//		logger.info("insert {}", article.toString());
//		Article result = articleElasticsearchRepository.findOne("1");
//		logger.info("find {}", result.toString());
//		return new ModelAndView("home/welcome", "variable", message);
//	}
//
//	@RequestMapping("/index")
//	@ResponseBody
//	public ModelAndView index() {
//		String message = "Hello";
////
////		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryString("1").field("id")).build();
////		Page<SampleEntity> sampleEntities = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
//
//		return new ModelAndView("home/welcome", "variable", message);
//	}
//}
