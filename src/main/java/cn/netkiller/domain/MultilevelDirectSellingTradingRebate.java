package cn.netkiller.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MultilevelDirectSellingTradingRebate {

	public enum Type {
		POINT, CASH, GIFT
	}

	public enum Rebate {
		DIRECT, INDIRECT
	}

	public enum Status {
		New, Rejected, Approved
	}

	@Id
	private String id;
	public String name;
	public Date beginDate;
	public Date endDate;
	public double lowAmount;
	public double highAmount;
	public Type type;
	public Status status = Status.New;
	public List<Map<String, Map<?, ?>>> product;

	@Override
	public String toString() {
		return "MultilevelDirectSellingTradingRebate [id=" + id + ", name=" + name + ", beginDate=" + beginDate
				+ ", endDate=" + endDate + ", lowAmount=" + lowAmount + ", highAmount=" + highAmount + ", type=" + type
				+ ", status=" + status + ", product=" + product + "]";
	}

}
