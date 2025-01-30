package com.multigenesys.entities.dto;

import com.multigenesys.entities.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class CustomerDTO {
	private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
