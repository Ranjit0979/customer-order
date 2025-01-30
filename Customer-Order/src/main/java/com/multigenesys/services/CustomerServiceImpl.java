package com.multigenesys.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.multigenesys.entities.Customer;
import com.multigenesys.entities.Role;
import com.multigenesys.entities.dto.CustomerDTO;
import com.multigenesys.exceptions.CustomException;
import com.multigenesys.repositories.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepo customerRepo;
	
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Customer addNewCustomer(Customer customer) {
		if(customerRepo.findByEmail(customer.getEmail())!=null) {
			throw new RuntimeException("Email is already registered.");
		}
		customer.setCreatedAt(LocalDateTime.now());
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		return customerRepo.save(customer);	
	}
	
	@Override
	public Customer getCustomerById(String email, Long id) {
		Customer customer = customerRepo.findByEmail(email);
		if (customer != null) {
			if (customer.getRole() == Role.ADMIN) {
				return customerRepo.findById(id)
						.orElseThrow(() -> new CustomException("Customer not found with id : " + id));
			} else if (customer.getRole() == Role.USER && customer.getId().equals(id)) {
				return customerRepo.findById(id)
						.orElseThrow(() -> new CustomException("Customer not found with id : " + id));
			}
		}
		throw new CustomException("Customer not found");
	}

	@Override
	public Customer updateCustomer(CustomerDTO customerDto) {
		Customer customer=customerRepo.findByEmail(customerDto.getEmail());
		if(customer!=null) {
			customer.setName(customerDto.getName()!=null?customerDto.getName():customer.getName());
			customer.setPhoneNumber(customerDto.getPhoneNumber()!=null?customerDto.getPhoneNumber():customer.getPhoneNumber());
		    customer.setRole(customerDto.getRole()!=null?customerDto.getRole():customer.getRole());
			return customerRepo.save(customer);
		}
		throw new CustomException("Customer not found with email id : "+customerDto.getEmail());
	}

	@Override
	public Customer deleteCustomerById(Long id) {
		return customerRepo.findById(id).orElseThrow(()->new CustomException("Customer not found with id : "+id));
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	@Override
	public Customer addNewAdmin() {
		if(customerRepo.findByEmail("admin@gmail.com")!=null) {
			throw new CustomException("Admin is already created.");
		}
		Customer admin=new Customer();
		admin.setEmail("admin@gmail.com");
		admin.setPassword("admin");
		admin.setCreatedAt(LocalDateTime.now());
		return customerRepo.save(admin);
	}

}
