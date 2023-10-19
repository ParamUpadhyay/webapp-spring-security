package com.spring.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.spring.model.UserBasicDetails;

@Repository
public class UserDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

    @Autowired
    private JdbcTemplate template;

    private class UserRowMapper implements RowMapper<UserBasicDetails> {
        @Override
        public UserBasicDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserBasicDetails userdetails = new UserBasicDetails();
            userdetails.setEmailId(rs.getString("emailId"));
            userdetails.setPassword(rs.getString("password"));
            return userdetails;
        }
    }

    public int save(UserBasicDetails userdetails) {
        String SQL = "INSERT INTO signup_details (firstName, lastName, dateOfBirth, gender, emailId, password) VALUES (?,?,?,?,?,?)";
        try {
        	LOGGER.debug("User saved successfully!");
            return template.update(SQL, userdetails.getFirstName(), userdetails.getLastName(),
                    userdetails.getDateOfBirth(), userdetails.getGender(), userdetails.getEmailId(),
                    userdetails.getPassword());
        } catch (DataAccessException e) {
            LOGGER.error("Error saving user: " + e.getMessage());
            return 0;
        }
    }

    public UserBasicDetails getUserByEmailId(String emailId) throws EmptyResultDataAccessException {
        if (emailId == null) {
        	LOGGER.warn("EmailId is null in getUserByEmailId method");
            throw new IllegalArgumentException("Email cannot be null");
        }
        
        try {
            String SQL = "SELECT * FROM signup_details WHERE emailId=?";
            UserBasicDetails user = template.queryForObject(SQL, new UserRowMapper(), emailId);
            LOGGER.debug("User retrieved by email: " + emailId);
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("User not found with email: " + emailId);
            throw new UsernameNotFoundException("User not found with this Email: " + emailId);
        } catch (Exception e) {
            LOGGER.error("Error while retrieving user by email: " + emailId, e);
            throw new UsernameNotFoundException("Error occurred while retrieving user by email", e);
        }
    }
}