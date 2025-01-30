package com.multigenesys.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.multigenesys.entities.Customer;
import com.multigenesys.repositories.CustomerRepo;

@Service
public class CustomerUserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private CustomerRepo customerRepo;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer user=customerRepo.findByEmail(username);
		if(user!=null) {
			return org.springframework.security.core.userdetails.User.builder()
					  .username(user.getEmail())
					  .password(user.getPassword())
					  .roles(user.getRole().toString()).build();
			
		}
		throw new UsernameNotFoundException("Customer not found with email: " + username);
	}

}
