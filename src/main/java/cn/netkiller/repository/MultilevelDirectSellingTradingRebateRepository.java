package cn.netkiller.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.netkiller.domain.MultilevelDirectSellingTradingRebate;

public interface MultilevelDirectSellingTradingRebateRepository extends MongoRepository<MultilevelDirectSellingTradingRebate, String> {
	public Page<MultilevelDirectSellingTradingRebate> findAll(Pageable pageable);

	public MultilevelDirectSellingTradingRebate findByName(String name);
}
