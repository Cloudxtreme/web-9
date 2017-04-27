package cn.netkiller.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.netkiller.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long>{
	List<Customer> findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);
}
