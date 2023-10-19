package com.spring.controller;

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
	UserDao dao;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UserBasicDetails user) {
	        int result = dao.save(user);
        if (result > 0) {
            return ResponseEntity.ok().body("{\"success\": true}");
        } else {
            return ResponseEntity.badRequest().body("{\"success\": false}");
        }
    }
}

