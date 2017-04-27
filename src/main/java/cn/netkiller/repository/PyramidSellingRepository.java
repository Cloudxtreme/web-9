package cn.netkiller.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.netkiller.domain.PyramidSelling;

public interface PyramidSellingRepository extends MongoRepository<PyramidSelling, String> {
	public Page<PyramidSelling> findAll(Pageable pageable);

//	public PyramidSelling findByNameAndCountry(String name, String country);

	public PyramidSelling findByUsername(String username);
	public PyramidSelling findByMobile(String mobile);
}
