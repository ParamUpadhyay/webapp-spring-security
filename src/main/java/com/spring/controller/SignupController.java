package com.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Import the Logger and LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spring.dao.UserDao;
import com.spring.model.UserBasicDetails;

@Controller
public class SignupController {

    @Autowired
    private UserDao dao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class); // Create a Logger instance

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserBasicDetails user) {
        try {
            int result = dao.save(user);
            if (result > 0) {
                LOGGER.info("User successfully signed up with email: " + user.getEmailId());
                return ResponseEntity.ok().body("{\"success\": true}");
            } else {
                LOGGER.warn("Failed to sign up user with email: " + user.getEmailId());
                return ResponseEntity.badRequest().body("{\"success\": false}");
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred during user signup", e);
            return ResponseEntity.status(500).body("{\"success\": false}");
        }
    }
}
