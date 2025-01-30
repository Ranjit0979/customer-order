package com.multigenesys.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import com.multigenesys.entities.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
    List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
   
    @Query("select IFNULL(SUM(o.orderAmount),0.0) from Order o where o.customer.id = :customer_id")
	public Double getTotalAmountSpent(@Param("customer_id") Long customerid);
}
