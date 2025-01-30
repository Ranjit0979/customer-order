package com.multigenesys.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.multigenesys.entities.Customer;
import com.multigenesys.entities.Order;
import com.multigenesys.entities.Role;
import com.multigenesys.entities.dto.OrderDTO;
import com.multigenesys.exceptions.CustomException;
import com.multigenesys.repositories.CustomerRepo;
import com.multigenesys.repositories.OrderRepo;


@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public Order addNewOrder(OrderDTO order) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer=customerRepo.findByEmail(authentication.getName());
		Order newOrder=new Order();
		newOrder.setOrderAmount(order.getOrderAmount()!=null?order.getOrderAmount():null);
		newOrder.setStatus(order.getStatus()!=null?order.getStatus():null);
		newOrder.setCustomer(customer);
		newOrder.setOrderDate(LocalDate.now());
		return orderRepo.save(newOrder);
	}

	@Override
	public Order updateOrder(OrderDTO order) {
		if(order.getId()==null)
			throw new CustomException("Order id cannot be null");
		
		Order orderData=orderRepo.findById(order.getId())
				                 .orElseThrow(()->new CustomException("Order not found for id :"+order.getId()));
		orderData.setStatus(order.getStatus()!=null?order.getStatus():orderData.getStatus());
		return orderRepo.save(orderData);
	}

	@Override
	public List<Order> getAllOrders() {
		return orderRepo.findAll();
	}

	@Override
	public Order getOrderById(Long id) {
		if(id==null)
			throw new CustomException("Order id cannot be null");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer=customerRepo.findByEmail(authentication.getName());
		return customer.getOrders()
				                 .stream()
				                 .filter(s-> s.getId().equals(id))
				                 .findFirst()
				                 .orElseThrow(() -> new CustomException("Order not found for this customer with order id: "+id));

	}

	@Override
	public List<Order> getOrdersWithinDateRange(LocalDate startDate, LocalDate endDate) {
		return orderRepo.findByOrderDateBetween(startDate, endDate);
	}

	@Override
	public Double getTotalAmountSpent(Long customerId) {
		if(customerId==null)
			throw new CustomException("customer id cannot be null");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer customer=customerRepo.findByEmail(authentication.getName());
		
		if (!customer.getRole().equals(Role.ADMIN) && !customer.getId().equals(customerId)) {
            throw new CustomException("Only Admins or the Customer can access this data.");
        }
		return orderRepo.getTotalAmountSpent(customerId);
	} 
}
