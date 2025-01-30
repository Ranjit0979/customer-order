package com.multigenesys.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.multigenesys.entities.Order;
import com.multigenesys.entities.dto.OrderDTO;
import com.multigenesys.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
    @Autowired
    private OrderService orderService;
	
	@PostMapping("/add")
	public ResponseEntity<Order> addNewOrder(@RequestBody OrderDTO order){
		return new ResponseEntity<Order>(orderService.addNewOrder(order),HttpStatus.CREATED);	
	}
	
	@PutMapping("/update")
	public ResponseEntity<Order> updateOrder(@RequestBody OrderDTO order){
		return new ResponseEntity<Order>(orderService.updateOrder(order),HttpStatus.OK);	
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Order>> getAllOrder(){
		return new ResponseEntity<List<Order>>(orderService.getAllOrders(),HttpStatus.OK);	
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long id){
		return new ResponseEntity<Order>(orderService.getOrderById(id),HttpStatus.OK);	
	}
	
//	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/range")
    public ResponseEntity<List<Order>> getOrdersWithinDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return new ResponseEntity<List<Order>>(orderService.getOrdersWithinDateRange(startDate, endDate),HttpStatus.OK);
    }
    
    @GetMapping("/total-spent/{customerId}")
    public Map<String, Double> getTotalAmountSpent(@PathVariable Long customerId) {
        Double totalAmount = orderService.getTotalAmountSpent(customerId);
        return Collections.singletonMap("totalAmount", totalAmount);
    }

}
