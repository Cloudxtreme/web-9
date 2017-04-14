package cn.netkiller.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PyramidSelling {
	@Id
    private String id;
	public String user;
	public String recommender;
	public String mobile;
}
