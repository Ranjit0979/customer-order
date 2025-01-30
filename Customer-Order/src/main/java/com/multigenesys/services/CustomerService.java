package com.multigenesys.services;

import java.util.List;

import com.multigenesys.entities.Customer;
import com.multigenesys.entities.dto.CustomerDTO;

public interface CustomerService {
	
	public Customer addNewCustomer(Customer customer);
	
	public Customer getCustomerById(String email,Long id);
	
	public List<Customer> getAllCustomers();
    
	public Customer updateCustomer(CustomerDTO customer);
	
	public Customer deleteCustomerById(Long id);

	public Customer addNewAdmin();
	
}
