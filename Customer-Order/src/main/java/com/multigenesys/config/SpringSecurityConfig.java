package com.multigenesys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.multigenesys.filter.JwtAccessDeniedHandler;
import com.multigenesys.filter.JwtAuthenticationEntryPoint;
import com.multigenesys.filter.JwtFilter;
import com.multigenesys.util.CustomerUserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:config.properties")
public class SpringSecurityConfig {
	
	@Autowired
    private CustomerUserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(req -> req
                .requestMatchers("/customer/login/**","/customer/get/**","/customer/add-default-admin").permitAll()
                .requestMatchers("/order/get/**","order/total-spent/**").hasAnyRole("ADMIN","USER")
                .requestMatchers("/order/update/**","/order/getAll/**","/order/range/**").hasRole("ADMIN")
                .requestMatchers("/customer/**").hasRole("ADMIN")
                .requestMatchers("/order/**").hasRole("USER")
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler) 
                )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		.build();
    }
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		return authenticationManagerBuilder.build();
	}
	
	/**
	 * @return BCryptPasswordEncoder for encrypting password.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
