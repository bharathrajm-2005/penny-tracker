package com.expensemanager.service;

import com.expensemanager.dto.LoginDto;
import com.expensemanager.dto.RegisterDto;

/**
 * Service interface defining authentication and user registration operations.
 */
public interface AuthService {
    /**
     * Authenticates a user and generates a JWT access token.
     * 
     * @param loginDto Contains the user's login credentials (username/email and password).
     * @return A JWT access token string if authentication is successful.
     */
    String login(LoginDto loginDto);

    /**
     * Registers a new user account in the system.
     * 
     * @param registerDto Contains the necessary details to create a new user profile.
     * @return A success message indicating registration outcome.
     */
    String register(RegisterDto registerDto);
}
