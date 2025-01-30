package com.multigenesys.services;

import java.time.LocalDate;
import java.util.List;

import com.multigenesys.entities.Order;
import com.multigenesys.entities.dto.OrderDTO;

public interface OrderService {
	
    public Order addNewOrder(OrderDTO order);
    
    public Order updateOrder(OrderDTO order);
    
    public List<Order> getAllOrders();
	
	public Order getOrderById(Long id);

	public List<Order> getOrdersWithinDateRange(LocalDate startDate, LocalDate endDate);
	
	public Double getTotalAmountSpent(Long customerId);

}
