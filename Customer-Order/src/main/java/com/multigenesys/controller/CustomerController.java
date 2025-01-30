package com.multigenesys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.multigenesys.entities.Customer;
import com.multigenesys.entities.dto.CustomerDTO;
import com.multigenesys.entities.dto.LoginResponseDTO;
import com.multigenesys.services.CustomerService;
import com.multigenesys.util.CustomerUserDetailsServiceImpl;
import com.multigenesys.util.JwtUtil;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomerUserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/add-default-admin")
	public ResponseEntity<Customer> addNewAdmin(){
		return new ResponseEntity<Customer>(customerService.addNewAdmin(),HttpStatus.CREATED);
	}
	
	@GetMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody Customer user) {
		LoginResponseDTO loginResDTO=new LoginResponseDTO();
        try{
            authenticationManager.authenticate(
            		new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword())
            		);
            UserDetails userDetails=userDetailsService.loadUserByUsername(user.getEmail());
            String jwtToken=jwtUtil.generateToken(userDetails.getUsername());
            loginResDTO.setToken(jwtToken);
            loginResDTO.setMessage("Login Successful");
            return new ResponseEntity<LoginResponseDTO>(loginResDTO,HttpStatus.OK);
        }catch (Exception e){
        	loginResDTO.setToken("");
            loginResDTO.setMessage("Invalid credentials");
            return new ResponseEntity<>(loginResDTO, HttpStatus.BAD_REQUEST);
        }
    }

	@PostMapping("/add")
	public ResponseEntity<Customer> addNewCustomer(@RequestBody Customer customer){
		return new ResponseEntity<Customer>(customerService.addNewCustomer(customer),HttpStatus.CREATED);
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerDTO customerDto){
		return new ResponseEntity<Customer>(customerService.updateCustomer(customerDto),HttpStatus.OK) ;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id){
		return new ResponseEntity<Customer>(customerService.deleteCustomerById(id),HttpStatus.OK) ;
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return new ResponseEntity<Customer>(customerService.getCustomerById(authentication.getName(),id),HttpStatus.OK) ;
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		return new ResponseEntity<List<Customer>>(customerService.getAllCustomers(),HttpStatus.OK) ;
	}

}
