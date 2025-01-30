package com.multigenesys.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.multigenesys.exceptions.CustomException;
import com.multigenesys.util.CustomerUserDetailsServiceImpl;
import com.multigenesys.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private CustomerUserDetailsServiceImpl userDetailService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
		String authorizationHeader=request.getHeader("Authorization");
		String userName=null;
		String jwtToken=null;
		if(authorizationHeader!=null && !authorizationHeader.isBlank() && authorizationHeader.startsWith("Bearer ")) {
			jwtToken=authorizationHeader.substring(7);
			userName=jwtUtil.extractUsername(jwtToken);
		}
		
		if(userName!=null && !userName.isBlank()) {
			UserDetails userDetails=userDetailService.loadUserByUsername(userName);
			if(userDetails!=null) {
				if(jwtUtil.validateToken(jwtToken)) {
					UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				    SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		}
		filterChain.doFilter(request, response);
		}catch(ExpiredJwtException ex) {
			handleException(response, new CustomException("JWT token has expired"));
		}
		
		
	}
	
	private void handleException(HttpServletResponse response, CustomException ex) throws IOException, java.io.IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write("{ \"message\": \"" + ex.getMessage() + "\", \"timestamp\": \"" + LocalDateTime.now() + "\" }");
    }

}
