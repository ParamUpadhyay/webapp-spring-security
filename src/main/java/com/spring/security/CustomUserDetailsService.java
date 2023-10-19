package com.spring.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.spring.dao.UserDao;
import com.spring.model.UserBasicDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserDao userDao;

    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	try {
            UserBasicDetails userdetails = userDao.getUserByEmailId(username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("user"));
            return new User(userdetails.getEmailId(), userdetails.getPassword(), authorities);
        } catch (DataAccessException e) {
            LOGGER.error("Error accessing data source while loading user by email", e);
            throw new UsernameNotFoundException("Error occurred while loading user by email", e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred while loading user by email", e);
            throw new UsernameNotFoundException("Error occurred while loading user by email", e);
        }
    }
}

