package cn.netkiller.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PyramidSelling {
	@Id
	private String id;
	@Indexed
	public String username;
	public String name;
	public String idcard;
	@Indexed(unique = true)
	public String mobile;
	public String email;
	public String accountLevel;
	public Date createDate;
	@Indexed
	public String directRecommender;
	public String indirectRecommender;

	public Date activeDate;
	public Boolean status;
	public TradeBonus tradeBonus;
	public RecommendedBonus recommendedBonus;

	public static class TradeBonus {
		public Map<String, Integer> threshold; // 满足条件
		public Boolean status; // 是否达标
		public TradeBonus(Map<String, Integer> threshold){
			this.threshold =threshold;
		}
	}

	public static class RecommendedBonus {
		public List<Map<String, Integer>> threshold; // 满足条件
		public Boolean status; // 是否达标
	}

	// public class EventBonus{
	// public Boolean status;
	// }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDirectRecommender() {
		return directRecommender;
	}

	public void setDirectRecommender(String directRecommender) {
		this.directRecommender = directRecommender;
	}

	public String getIndirectRecommender() {
		return indirectRecommender;
	}

	public void setIndirectRecommender(String indirectRecommender) {
		this.indirectRecommender = indirectRecommender;
	}

	public String getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(String accountLevel) {
		this.accountLevel = accountLevel;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PyramidSelling [id=" + id + ", username=" + username + ", name=" + name + ", idcard=" + idcard
				+ ", mobile=" + mobile + ", email=" + email + ", createDate=" + createDate + ", directRecommender="
				+ directRecommender + ", indirectRecommender=" + indirectRecommender + ", accountLevel=" + accountLevel
				+ ", status=" + status + "]";
	}
}
