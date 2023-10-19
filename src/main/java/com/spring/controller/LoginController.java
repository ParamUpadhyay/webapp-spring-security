package com.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.model.UserBasicDetails;
import com.spring.security.CustomAuthenticationProvider;

@Controller
public class LoginController {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class); // Create a Logger instance

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody UserBasicDetails loginRequest) {

        try {
            Authentication authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmailId(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            LOGGER.info("Login successful for user: " + loginRequest.getEmailId()); // Log successful login

            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            LOGGER.warn("Invalid email or password for user: " + loginRequest.getEmailId()); // Log failed login attempt
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
}
