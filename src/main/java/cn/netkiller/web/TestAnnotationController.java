package cn.netkiller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.netkiller.annotation.NeoLogger;

@RestController
public class TestAnnotationController {

	@NeoLogger
	@RequestMapping("/neo/log")
	public String getLog() {
		return "<h1>Hello World</h1>";
	}

}
