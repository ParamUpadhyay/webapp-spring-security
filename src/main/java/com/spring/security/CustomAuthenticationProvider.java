package com.spring.security;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
    private CustomUserDetailsService customUserDetailsService;

    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
    	String emailId = authentication.getName();
        String password = authentication.getCredentials().toString();
        LOGGER.info("Authentication attempt for username: " + authentication.getName());

        UserDetails user = customUserDetailsService.loadUserByUsername(emailId);

        if (user != null && Objects.equals(password, user.getPassword())) {
            LOGGER.info("Authentication successful for user: " + user.getUsername());
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        }
        
        LOGGER.warn("Authentication failed for user: " + authentication.getName());
        throw new UsernameNotFoundException("Invalid email or password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        LOGGER.debug("Checking if authentication class is supported: " + authentication.getName());
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
	