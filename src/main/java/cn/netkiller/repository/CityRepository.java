package cn.netkiller.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cn.netkiller.domain.City;

public interface CityRepository extends MongoRepository<City, String> {
	public Page<City> findAll(Pageable pageable);

	public City findByNameAndCountry(String name, String country);

	public City findByName(String name);

	public List<City> findByCountry(String country);

}
