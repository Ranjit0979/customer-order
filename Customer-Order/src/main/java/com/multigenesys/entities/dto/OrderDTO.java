package com.multigenesys.entities.dto;

import java.time.LocalDateTime;

import com.multigenesys.entities.OrderStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class OrderDTO {
	private Long id;
	private LocalDateTime orderDate;
	private Double orderAmount;
	@Enumerated(EnumType.STRING)
    private OrderStatus status;
	private Long customerId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
