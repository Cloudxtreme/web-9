package cn.netkiller.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.netkiller.domain.MultilevelDirectSellingAccountRewards;

public interface MultilevelDirectSellingAccountRewardsRepository extends MongoRepository<MultilevelDirectSellingAccountRewards, String> {
	public Page<MultilevelDirectSellingAccountRewards> findAll(Pageable pageable);

	public MultilevelDirectSellingAccountRewards findByName(String name);
}
