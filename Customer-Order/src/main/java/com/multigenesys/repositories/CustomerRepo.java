package com.multigenesys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multigenesys.entities.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
	
	public Customer findByEmail(String name);

}
