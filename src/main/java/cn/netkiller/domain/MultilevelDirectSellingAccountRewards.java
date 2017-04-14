package cn.netkiller.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MultilevelDirectSellingAccountRewards {
	@Id
	private String id;
	public String name;
	public Date beginDate;
	public Date endDate;
	public double directAmount;
	public double indirectAmount;
	public double minimumAmount;
	
	public List<Map<String, Double>> lot;

	@Override
	public String toString() {
		return "MultilevelDirectSellingAccountRewards [id=" + id + ", name=" + name + ", beginDate=" + beginDate
				+ ", endDate=" + endDate + ", directAmount=" + directAmount + ", indirectAmount=" + indirectAmount
				+ ", minimumAmount=" + minimumAmount + ", lot=" + lot + "]";
	}
	
	
}
