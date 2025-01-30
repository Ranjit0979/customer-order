package com.multigenesys.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		 response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        response.setContentType("application/json");
	        response.getWriter().write("{ \"error\": \"Unauthorized\", \"message\": \"You need to log in to access this resource.\" }");
	    
		
	}

}
